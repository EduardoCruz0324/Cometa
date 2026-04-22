package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Datos de entrada para crear o actualizar un producto")
public class DtoProductIn {

	@Schema(description = "Código GTIN/EAN de 13 dígitos", example = "7501234567890")
	@JsonProperty("gtin")
	@Pattern(regexp = "^\\+?\\d{13}$", message = "El gtin tiene un formato inválido")
	@NotNull(message="El gtin es obligatorio")
	private String gtin;

	@Schema(description = "Nombre del producto", example = "Laptop Lenovo IdeaPad")
	@JsonProperty("product")
	@NotNull(message="El product es obligatorio")
	private String product;

	@Schema(description = "Descripción del producto", example = "Laptop 15.6 pulgadas, 16GB RAM, 512GB SSD")
	@JsonProperty("description")
	@NotNull(message="El description es obligatorio")
	private String description;

	@Schema(description = "Precio del producto", example = "12999.99")
	@JsonProperty("price")
	@Min(value = 0)
	@NotNull(message="El price es obligatorio")
	private Float price;

	@Schema(description = "Cantidad en existencia", example = "50")
	@JsonProperty("stock")
	@NotNull(message="El stock es obligatorio")
	private Integer stock;

	@Schema(description = "ID de la categoría a la que pertenece el producto", example = "1")
	@JsonProperty("category_id")
	@NotNull(message="El category_id es obligatorio")
	private Integer category_id;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
}
