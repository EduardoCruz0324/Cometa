package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.product.api.entity.ProductImage;
import com.product.api.service.SvcProductImage;

@RestController
@RequestMapping("/product/{id}/image")
public class CtrlProductImage {

    @Autowired
    private SvcProductImage service;

    @GetMapping
    public List<ProductImage> getImages(@PathVariable Integer id) {
        return service.getByProduct(id);
    }

    @PostMapping
    public String create(@PathVariable Integer id, @RequestBody ProductImage body) {
        service.create(id, body.getImage());
        return "La imagen ha sido registrada";
    }

    @DeleteMapping("/{imageId}")
    public String delete(@PathVariable Long imageId) {
        service.delete(imageId);
        return "La imagen ha sido eliminada";
    }
}