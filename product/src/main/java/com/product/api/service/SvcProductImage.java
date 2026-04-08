package com.product.api.service;

import java.util.List;
import com.product.api.entity.ProductImage;

public interface SvcProductImage {

    void create(Integer productId, String image);

    List<ProductImage> getByProduct(Integer productId);
    
    void delete(Long imageId);
}