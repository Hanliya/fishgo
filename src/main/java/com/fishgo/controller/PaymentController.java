package com.fishgo.controller;

import com.fishgo.model.Payment;
import com.fishgo.service.PaymentService;
import com.fishgo.service.RazorpayService;
import com.razorpay.Order;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final PaymentService paymentService;
    private final RazorpayService razorpayService;

    public PaymentController(PaymentService ps, RazorpayService rs) {
        this.paymentService = ps;
        this.razorpayService = rs;
    }

    @PostMapping("/initiate")
    public Payment initiate(@RequestParam Long orderId,
                            @RequestParam Double amount,
                            @RequestParam Double commission) {
        return paymentService.initiate(orderId, amount, commission);
    }

    @PostMapping("/{paymentId}/razorpay-order")
    public Map<String, Object> createRazorpayOrder(@PathVariable Long paymentId) {
        Order order = razorpayService.createRazorpayOrder(paymentId);
        return Map.of(
                "orderId", order.get("id"),
                "amount", order.get("amount"),
                "currency", order.get("currency"),
                "keyId", "use_frontend_env_key"
        );
    }

    @PostMapping("/verify")
    public Payment verify(@RequestParam String razorpay_order_id,
                          @RequestParam String razorpay_payment_id,
                          @RequestParam String razorpay_signature) {
        boolean ok = razorpayService.verifySignature(razorpay_order_id, razorpay_payment_id, razorpay_signature);
        if (!ok) throw new RuntimeException("Signature verification failed");
        return razorpayService.markSuccessByOrder(razorpay_order_id);
    }
}
