package com.cart.api.dto;

public class DtoCartItemOut {

	private Integer cart_item_id;
	private String gtin;
	private String productName;
	private Double unitPrice;
	private Integer quantity;

	public DtoCartItemOut() {
	}

	public DtoCartItemOut(Integer cart_item_id, String gtin, String productName, Double unitPrice, Integer quantity) {
		this.cart_item_id = cart_item_id;
		this.gtin = gtin;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public Integer getCart_item_id() { return cart_item_id; }
	public void setCart_item_id(Integer cart_item_id) { this.cart_item_id = cart_item_id; }

	public String getGtin() { return gtin; }
	public void setGtin(String gtin) { this.gtin = gtin; }

	public String getProductName() { return productName; }
	public void setProductName(String productName) { this.productName = productName; }

	public Double getUnitPrice() { return unitPrice; }
	public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
