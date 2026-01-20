package com.fishgo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne @JoinColumn(name = "fish_id")
    private Fish fish;

    private Integer quantity;
    private String status; // PENDING, CONFIRMED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    private LocalDateTime createdAt = LocalDateTime.now();

    // Delivery tracking
    private String deliveryPartner;
    private String trackingStatus;   // CREATED, PICKED_UP, OUT_FOR_DELIVERY, DELIVERED
    private String trackingLocation;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

	private LocalDateTime  settledAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public Fish getFish() {
		return fish;
	}
	public void setFish(Fish fish) {
		this.fish = fish;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getDeliveryPartner() {
		return deliveryPartner;
	}
	public void setDeliveryPartner(String deliveryPartner) {
		this.deliveryPartner = deliveryPartner;
	}
	public String getTrackingStatus() {
		return trackingStatus;
	}
	public void setTrackingStatus(String trackingStatus) {
		this.trackingStatus = trackingStatus;
	}
	public String getTrackingLocation() {
		return trackingLocation;
	}
	public void setTrackingLocation(String trackingLocation) {
		this.trackingLocation = trackingLocation;
	}
	public LocalDateTime getPickedUpAt() {
		return pickedUpAt;
	}
	public void setPickedUpAt(LocalDateTime pickedUpAt) {
		this.pickedUpAt = pickedUpAt;
	}
	public LocalDateTime getDeliveredAt() {
		return deliveredAt;
	}
	public void setDeliveredAt(LocalDateTime deliveredAt) {
		this.deliveredAt = deliveredAt;
	}
	public void setSettledAt(LocalDateTime settledAt) {
		this.settledAt = settledAt;		
	}
	public LocalDateTime getSettledAt() {
		return settledAt;
	}
    
}
