package com.fishgo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fishgo.model.Order;
import com.fishgo.model.Payment;
import com.fishgo.model.Payout;
import com.fishgo.model.User;
import com.fishgo.repo.OrderRepository;
import com.fishgo.repo.PaymentRepository;
import com.fishgo.repo.PayoutRepository;

@Service
public class PayoutService {

    private final PayoutRepository payoutRepo;
    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;

    public PayoutService(PayoutRepository pr, PaymentRepository payr, OrderRepository or) {
        this.payoutRepo = pr;
        this.paymentRepo = payr;
        this.orderRepo = or;
    }

    public Payout createPayoutForOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        if (!"DELIVERED".equals(order.getStatus())) throw new RuntimeException("Order not delivered");
        if (payoutRepo.existsByOrder_Id(orderId)) throw new RuntimeException("Payout exists");

        Payment payment = paymentRepo.findAll().stream()
                .filter(p -> p.getOrder().getId().equals(orderId) && "SUCCESS".equals(p.getStatus()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No successful payment"));

        User vendor = order.getFish().getVendor();

        Payout payout = new Payout();
        payout.setOrder(order);
        payout.setVendor(vendor);
        payout.setGrossAmount(payment.getAmount());
        payout.setCommission(payment.getCommission());
        payout.setNetAmount(payment.getAmount() - payment.getCommission());
        payout.setStatus("PENDING");
        payout.setCreatedAt(LocalDateTime.now());

        return payoutRepo.save(payout);
    }

    public Payout schedule(Long payoutId) {
        Payout p = payoutRepo.findById(payoutId).orElseThrow();
        p.setStatus("SCHEDULED");
        return payoutRepo.save(p);
    }

    public Payout markPaid(Long payoutId, String settlementRef) {
        Payout p = payoutRepo.findById(payoutId).orElseThrow();
        p.setStatus("PAID");
        p.setSettlementRef(settlementRef);
        p.setSettledAt(LocalDateTime.now());
        return payoutRepo.save(p);
    }

    public List<Payout> listPending() { return payoutRepo.findByStatus("PENDING"); }
    public List<Payout> listScheduled() { return payoutRepo.findByStatus("SCHEDULED"); }

    public Payout onOrderDelivered(Long orderId) { return createPayoutForOrder(orderId); }

	public Payout onOrderSettled(Long id) {
		return createPayoutForOrder(id);		
	}
}
