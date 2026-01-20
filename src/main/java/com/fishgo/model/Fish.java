package com.fishgo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "fishes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Fish {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Double price;
    private Double weight;
    private String freshnessStatus;
    private String status;

    @ManyToOne @JoinColumn(name = "vendor_id")
    private User vendor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getFreshnessStatus() {
		return freshnessStatus;
	}

	public void setFreshnessStatus(String freshnessStatus) {
		this.freshnessStatus = freshnessStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getVendor() {
		return vendor;
	}

	public void setVendor(User vendor) {
		this.vendor = vendor;
	}
    
}
