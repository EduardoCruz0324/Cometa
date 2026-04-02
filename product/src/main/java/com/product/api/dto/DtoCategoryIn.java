package com.product.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class DtoCategoryIn{
    @JsonProperty("category")
    @NotNull(message="El nombre de la categoria es obligatorio")
    private String category;

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