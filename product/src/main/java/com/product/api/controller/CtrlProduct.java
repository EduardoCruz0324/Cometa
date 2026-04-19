package com.product.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product", description = "Administración de productos")
@RestController
@RequestMapping("/product")
public class CtrlProduct {

    @Operation(summary = "Consultar producto", description = "Obtiene el detalle de un producto por su id")
    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id) {
        return "Producto " + id;
    }
}
