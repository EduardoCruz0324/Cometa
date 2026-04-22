package com.product.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Schema(description = "Producto de la tienda")
@Entity
@Table(name = "product")
public class Product {

	@Schema(description = "ID del producto", example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;

	@Schema(description = "Código GTIN/EAN del producto", example = "7501234567890")
	@Column(name = "gtin")
	private String gtin;

	@Schema(description = "Nombre del producto", example = "Laptop Lenovo IdeaPad")
	@Column(name = "product_name")
	private String product_name;

	@Schema(description = "Descripción del producto", example = "Laptop 15.6 pulgadas, 16GB RAM, 512GB SSD")
	@Column(name = "description")
	private String description;

	@Schema(description = "Precio del producto", example = "12999.99")
	@Column(name = "price")
	private Float price;

	@Schema(description = "Cantidad en existencia", example = "50")
	@Column(name = "stock")
	private Integer stock;

	@Schema(description = "ID de la categoría", example = "1")
	@Column(name = "category_id")
	private Integer category_id;

	@Schema(description = "Estatus del producto (1=activo, 0=inactivo)", example = "1")
	@Column(name = "status")
	private Integer status;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer product_id) {
		this.productId = product_id;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	//string
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product) {
		this.product_name = product;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductImage> images;

}