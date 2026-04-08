package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;

@Service
public class SvcProductImageImp implements SvcProductImage {

    @Autowired
    private RepoProductImage repo;

    @Autowired
    private RepoProduct repoProduct;

    @Override
    public void create(Integer productId, String image) {
        Product product = repoProduct.findById(productId).orElseThrow();

        ProductImage pi = new ProductImage();
        pi.setImage(image);
        pi.setProduct(product);

        repo.save(pi);
    }

    @Override
    public List<ProductImage> getByProduct(Integer productId) {
        return repo.findByProduct_ProductId(productId);
    }

    @Override
    public void delete(Long imageId) {
        repo.deleteById(imageId);
    }
}