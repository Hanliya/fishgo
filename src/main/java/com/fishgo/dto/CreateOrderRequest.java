package com.fishgo.dto;

import lombok.*;

@Getter @Setter
public class CreateOrderRequest {
    private Long fishId;
    private Integer quantity;
	public Long getFishId() {
		return fishId;
	}
	public void setFishId(Long fishId) {
		this.fishId = fishId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
