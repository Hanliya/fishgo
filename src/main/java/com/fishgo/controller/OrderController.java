package com.fishgo.controller;

import com.fishgo.dto.CreateOrderRequest;
import com.fishgo.model.Order;
import com.fishgo.model.User;
import com.fishgo.repo.UserRepository;
import com.fishgo.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService os, UserRepository ur) {
        this.orderService = os;
        this.userRepository = ur;
    }

    // ✅ Only Customers can create orders
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public Order create(@AuthenticationPrincipal UserDetails ud,
                        @RequestBody CreateOrderRequest req) {
        User customer = userRepository.findByEmail(ud.getUsername()).orElseThrow();
        return orderService.createOrder(customer, req.getFishId(), req.getQuantity());
    }

    // ✅ Only Customers can view their own orders
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public List<Order> myOrders(@AuthenticationPrincipal UserDetails ud) {
        User customer = userRepository.findByEmail(ud.getUsername()).orElseThrow();
        return orderService.listForCustomer(customer);
    }

    // ✅ Admin or Fisherman can update order status
    @PreAuthorize("hasAnyRole('ADMIN','FISHERMAN')")
    @PostMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateStatus(id, status);
    }
}
