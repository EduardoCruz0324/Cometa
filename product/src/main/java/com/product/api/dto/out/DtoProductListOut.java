package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de salida de un producto")
public class DtoProductListOut {

	@Schema(description = "ID del producto", example = "1")
	@JsonProperty("product_id")
	private Integer product_id;

	@Schema(description = "Código GTIN/EAN del producto", example = "7501234567890")
	@JsonProperty("gtin")
	private String gtin;

	@Schema(description = "Nombre del producto", example = "Laptop Lenovo IdeaPad")
	@JsonProperty("product")
	private String product;

	@Schema(description = "Precio del producto", example = "12999.99")
	@JsonProperty("price")
	private Float price;

	@Schema(description = "Estatus del producto (1=activo, 0=inactivo)", example = "1")
	@JsonProperty("status")
	private Integer status;

	@Schema(description = "Imágenes del producto en Base64")
	@JsonProperty("images")
	private String[] images;

	public DtoProductListOut(Integer product_id, String gtin, String product, Float price, Integer status) {
		super();
		this.product_id = product_id;
		this.gtin = gtin;
		this.product = product;
		this.price = price;
		this.status = status;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

}
