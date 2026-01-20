package com.fishgo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fishgo.model.Fish;
import com.fishgo.model.Order;
import com.fishgo.model.User;
import com.fishgo.repo.FishRepository;
import com.fishgo.repo.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository repo;
    private final FishRepository fishRepo;
    private final PayoutService payoutService;

    public OrderService(OrderRepository repo, FishRepository fishRepo, PayoutService payoutService) {
        this.repo = repo;
        this.fishRepo = fishRepo;
        this.payoutService = payoutService;
    }

    public Order createOrder(User customer, Long fishId, Integer quantity) {
        Fish fish = fishRepo.findById(fishId).orElseThrow();
        Order order = new Order();
        order.setCustomer(customer);
        order.setFish(fish);
        order.setQuantity(quantity);
        order.setStatus("PENDING");
        return repo.save(order);
    }

    public List<Order> listForCustomer(User customer) {
        return repo.findByCustomer(customer);
    }

    
    public Order updateStatus(Long id, String status) {
        Order o = repo.findById(id).orElseThrow();

        switch (status) {
            case "PAID" -> {
                o.setStatus("PAID");
                // you could add a paidAt timestamp if desired
            }
            case "PICKED_UP" -> {
                o.setStatus("PICKED_UP");
                o.setPickedUpAt(LocalDateTime.now());
            }
            case "DELIVERED" -> {
                o.setStatus("DELIVERED");
                o.setDeliveredAt(LocalDateTime.now());
                try {
                    payoutService.onOrderDelivered(id);
                } catch (Exception e) {
                    // log error instead of ignoring
                    e.printStackTrace();
                }
            }
            case "SETTLED" -> {
                o.setStatus("SETTLED");
                try {
                    payoutService.onOrderSettled(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            default -> o.setStatus(status);
        }

        return repo.save(o);
    }

}
