package com.fishgo.controller;

import com.fishgo.model.Order;
import com.fishgo.service.DeliveryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
@CrossOrigin(origins = "http://localhost:3000")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService ds) {
        this.deliveryService = ds;
    }

    @PostMapping("/{orderId}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public Order assign(@PathVariable Long orderId, @RequestParam String partner) {
        return deliveryService.assignPartner(orderId, partner);
    }

    @PostMapping("/{orderId}/picked-up")
    public Order pickedUp(@PathVariable Long orderId,
                          @RequestParam(required = false) String location) {
        return deliveryService.markPickedUp(orderId, location);
    }

    @PostMapping("/{orderId}/out-for-delivery")
    public Order outForDelivery(@PathVariable Long orderId,
                                @RequestParam(required = false) String location) {
        return deliveryService.markOutForDelivery(orderId, location);
    }

    @PostMapping("/{orderId}/delivered")
    public Order delivered(@PathVariable Long orderId,
                           @RequestParam(required = false) String location) {
        return deliveryService.markDelivered(orderId, location);
    }
}
