package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.service.SvcProduct;
import com.product.exception.ApiException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Product", description = "Administración de productos")
@RestController
@RequestMapping("/product")
public class CtrlProduct {

    @Autowired
    SvcProduct svc;

    @Operation(summary = "Consultar productos", description = "Lista todos los productos registrados en el sistema")
    @GetMapping
    public ResponseEntity<List<DtoProductListOut>> getProducts() {
        return svc.getProducts();
    }

    @Operation(summary = "Consultar producto", description = "Obtiene el detalle de un producto por su id")
    @GetMapping("/{id}")
    public ResponseEntity<DtoProductListOut> getProduct(
            @Parameter(description = "ID del producto") @PathVariable Integer id) {
        return svc.getProduct(id);
    }

    @Operation(summary = "Registrar producto", description = "Crea un nuevo producto en el sistema")
    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody DtoProductIn in,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        return svc.createProduct(in);
    }

    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente por su id")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @Parameter(description = "ID del producto") @PathVariable Integer id,
            @Valid @RequestBody DtoProductIn in,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        return svc.updateProduct(id, in);
    }

    @Operation(summary = "Activar producto", description = "Cambia el estatus de un producto a activo por su id")
    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enableProduct(
            @Parameter(description = "ID del producto") @PathVariable Integer id) {
        return svc.enableProduct(id);
    }

    @Operation(summary = "Desactivar producto", description = "Cambia el estatus de un producto a inactivo por su id")
    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disableProduct(
            @Parameter(description = "ID del producto") @PathVariable Integer id) {
        return svc.disableProduct(id);
    }
}
