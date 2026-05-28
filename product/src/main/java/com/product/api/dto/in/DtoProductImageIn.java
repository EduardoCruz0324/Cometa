package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos de entrada para registrar una imagen de producto")
public class DtoProductImageIn {

    @Schema(hidden = true)
    @JsonProperty("product_id")
    private Integer productId;

    @Schema(description = "Imagen codificada en Base64 (puede incluir el prefijo data:image/png;base64,...)", example = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==")
    @JsonProperty("image")
    @NotNull(message = "El image es obligatorio")
    private String image;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
