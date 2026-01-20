package com.fishgo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "payouts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Payout {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "vendor_id")
    private User vendor;

    @OneToOne @JoinColumn(name = "order_id")
    private Order order;

    private Double grossAmount;
    private Double commission;
    private Double netAmount;

    private String status;         // PENDING, SCHEDULED, PAID, FAILED
    private String settlementRef;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime settledAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getVendor() {
		return vendor;
	}
	public void setVendor(User vendor) {
		this.vendor = vendor;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSettlementRef() {
		return settlementRef;
	}
	public void setSettlementRef(String settlementRef) {
		this.settlementRef = settlementRef;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getSettledAt() {
		return settledAt;
	}
	public void setSettledAt(LocalDateTime settledAt) {
		this.settledAt = settledAt;
	}
    
}
