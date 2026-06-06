package com.cart.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;

	private String gtin;

	@Column(name = "product_name")
	private String productName;

	private Float price;

	private Integer stock;

	private Integer status;

	public Product() {
	}

	public Integer getProductId() { return productId; }
	public void setProductId(Integer productId) { this.productId = productId; }

	public String getGtin() { return gtin; }
	public void setGtin(String gtin) { this.gtin = gtin; }

	public String getProductName() { return productName; }
	public void setProductName(String productName) { this.productName = productName; }

	public Float getPrice() { return price; }
	public void setPrice(Float price) { this.price = price; }

	public Integer getStock() { return stock; }
	public void setStock(Integer stock) { this.stock = stock; }

	public Integer getStatus() { return status; }
	public void setStatus(Integer status) { this.status = status; }
}
