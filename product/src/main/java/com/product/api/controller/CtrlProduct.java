package com.product.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/product")
public class CtrlProduct {
    
    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id) {
        return "Producto " + id;
    }    
}
