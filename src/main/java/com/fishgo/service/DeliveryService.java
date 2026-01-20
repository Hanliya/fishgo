package com.fishgo.service;

import com.fishgo.model.Order;
import com.fishgo.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeliveryService {

    private final OrderRepository orderRepo;
    private final PayoutService payoutService;

    public DeliveryService(OrderRepository orderRepo, PayoutService payoutService) {
        this.orderRepo = orderRepo;
        this.payoutService = payoutService;
    }

    public Order assignPartner(Long orderId, String partnerName) {
        Order o = orderRepo.findById(orderId).orElseThrow();
        o.setDeliveryPartner(partnerName);
        o.setTrackingStatus("CREATED");
        return orderRepo.save(o);
    }

    public Order markPickedUp(Long orderId, String location) {
        Order o = orderRepo.findById(orderId).orElseThrow();
        o.setTrackingStatus("PICKED_UP");
        o.setTrackingLocation(location);
        o.setPickedUpAt(LocalDateTime.now());
        o.setStatus("CONFIRMED");
        return orderRepo.save(o);
    }

    public Order markOutForDelivery(Long orderId, String location) {
        Order o = orderRepo.findById(orderId).orElseThrow();
        o.setTrackingStatus("OUT_FOR_DELIVERY");
        o.setTrackingLocation(location);
        o.setStatus("OUT_FOR_DELIVERY");
        return orderRepo.save(o);
    }

    public Order markDelivered(Long orderId, String location) {
        Order o = orderRepo.findById(orderId).orElseThrow();
        o.setTrackingStatus("DELIVERED");
        o.setTrackingLocation(location);
        o.setDeliveredAt(LocalDateTime.now());
        o.setStatus("DELIVERED");
        Order saved = orderRepo.save(o);
        try { payoutService.onOrderDelivered(orderId); } catch (Exception ignored) {}
        return saved;
    }
}
