package com.fishgo.service;

import com.fishgo.model.Payment;
import com.fishgo.repo.PaymentRepository;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class RazorpayService {

    private final PaymentRepository paymentRepository;
    private final String keyId;
    private final String keySecret;
    private final String currency;
    private final boolean autoCapture;

    public RazorpayService(PaymentRepository paymentRepository,
                           @Value("${razorpay.key_id}") String keyId,
                           @Value("${razorpay.key_secret}") String keySecret,
                           @Value("${razorpay.currency}") String currency,
                           @Value("${razorpay.capture}") boolean autoCapture) {
        this.paymentRepository = paymentRepository;
        this.keyId = keyId;
        this.keySecret = keySecret;
        this.currency = currency;
        this.autoCapture = autoCapture;
    }

    private RazorpayClient client() {
        try { return new RazorpayClient(keyId, keySecret); }
        catch (RazorpayException e) { throw new RuntimeException(e); }
    }

    public Order createRazorpayOrder(Long paymentId) {
        try {
            Payment p = paymentRepository.findById(paymentId).orElseThrow();
            int amountPaise = (int) Math.round(p.getAmount() * 100);
            JSONObject req = new JSONObject();
            req.put("amount", amountPaise);
            req.put("currency", currency);
            req.put("receipt", "rcpt_" + paymentId);
            req.put("payment_capture", autoCapture ? 1 : 0);

            Order order = client().orders.create(req);
            p.setProviderRef(order.get("id"));
            paymentRepository.save(p);
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    public boolean verifySignature(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(keySecret.getBytes(), "HmacSHA256"));
            byte[] digest = mac.doFinal(payload.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) hex.append(String.format("%02x", b));
            return hex.toString().equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

    public Payment markSuccessByOrder(String razorpayOrderId) {
        Payment p = paymentRepository.findAll().stream()
                .filter(pay -> razorpayOrderId.equals(pay.getProviderRef()))
                .findFirst()
                .orElseThrow();
        p.setStatus("SUCCESS");
        return paymentRepository.save(p);
    }
}
