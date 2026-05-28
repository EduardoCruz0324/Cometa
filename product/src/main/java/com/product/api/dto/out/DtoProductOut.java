package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoProductOut {

	@JsonProperty("product_id")
	private Integer product_id;

	@JsonProperty("gtin")
	private String gtin;

	@JsonProperty("product")
	private String product;

	@JsonProperty("description")
	private String description;

	@JsonProperty("price")
	private Float price;

	@JsonProperty("stock")
	private Integer stock;

	@JsonProperty("category_id")
	private Integer category_id;

	@JsonProperty("status")
	private Integer status;

	@JsonProperty("images")
	private String[] images;

	public DtoProductOut(Integer product_id, String gtin, String product, String description,
			Float price, Integer stock, Integer category_id, Integer status) {
		this.product_id = product_id;
		this.gtin = gtin;
		this.product = product;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.category_id = category_id;
		this.status = status;
	}

	public Integer getProduct_id() { return product_id; }
	public void setProduct_id(Integer product_id) { this.product_id = product_id; }

	public String getGtin() { return gtin; }
	public void setGtin(String gtin) { this.gtin = gtin; }

	public String getProduct() { return product; }
	public void setProduct(String product) { this.product = product; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public Float getPrice() { return price; }
	public void setPrice(Float price) { this.price = price; }

	public Integer getStock() { return stock; }
	public void setStock(Integer stock) { this.stock = stock; }

	public Integer getCategory_id() { return category_id; }
	public void setCategory_id(Integer category_id) { this.category_id = category_id; }

	public Integer getStatus() { return status; }
	public void setStatus(Integer status) { this.status = status; }

	public String[] getImages() { return images; }
	public void setImages(String[] images) { this.images = images; }
}
