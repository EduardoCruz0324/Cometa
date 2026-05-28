package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;

public interface SvcProductImage {

    ResponseEntity<String> uploadProductImage(DtoProductImageIn in);

    List<ProductImage> getByProduct(Integer productId);

    ResponseEntity<String> deleteProductImage(Integer id);
}
