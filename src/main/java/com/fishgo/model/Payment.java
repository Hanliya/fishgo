package com.fishgo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "order_id")
    private Order order;

    private Double amount;
    private Double commission;
    private String status;     // INITIATED, SUCCESS, FAILED
    private String providerRef; // Razorpay orderId
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProviderRef() {
		return providerRef;
	}
	public void setProviderRef(String providerRef) {
		this.providerRef = providerRef;
	}
    
}
