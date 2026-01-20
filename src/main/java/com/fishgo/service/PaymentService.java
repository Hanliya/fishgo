package com.fishgo.service;

import com.fishgo.model.Order;
import com.fishgo.model.Payment;
import com.fishgo.repo.OrderRepository;
import com.fishgo.repo.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository repo;
    private final OrderRepository orderRepo;

    public PaymentService(PaymentRepository repo, OrderRepository orderRepo) {
        this.repo = repo;
        this.orderRepo = orderRepo;
    }

    public Payment initiate(Long orderId, Double amount, Double commission) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        Payment p = new Payment();
        p.setOrder(order);
        p.setAmount(amount);
        p.setCommission(commission);
        p.setStatus("INITIATED");
        p.setProviderRef(null);
        return repo.save(p);
    }

    public Payment markSuccess(Long paymentId) {
        Payment p = repo.findById(paymentId).orElseThrow();
        p.setStatus("SUCCESS");
        return repo.save(p);
    }
}
