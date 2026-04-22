package com.product.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos de entrada para crear o actualizar una categoría")
public class DtoCategoryIn{
    @Schema(description = "Nombre de la categoría", example = "Electrónica")
    @JsonProperty("category")
    @NotNull(message="El nombre de la categoria es obligatorio")
    private String category;

    @Schema(description = "Etiqueta corta de la categoría", example = "electronica")
    @JsonProperty("tag")
    @NotNull(message="El tag de la categoria es obligatorio")
    private String tag;

    public String getCategory(){ //revisar
        return category;
    }

    public String getTag(){ //revisar
        return tag;
    }
}   