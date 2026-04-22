package com.product.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(description = "Imagen asociada a un producto")
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Schema(description = "ID de la imagen", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Integer productImageId;

    @Schema(description = "Ruta de la imagen almacenada en el servidor", example = "/uploads/img/product/6fdf9718-38f1-4c45-a356-79184a0aff60.png")
    @Column(name = "image")
    private String image;

    @Schema(description = "Estatus de la imagen (1=activo, 0=inactivo)", example = "1")
    @Column(name = "status")
    private Integer status;

    @Schema(hidden = true)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Integer getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(Integer productImageId) {
        this.productImageId = productImageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
