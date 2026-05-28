package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.service.SvcProductImage;
import com.product.exception.ApiException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Product Image", description = "Carga de imágenes de productos")
@RestController
@RequestMapping("/product")
public class CtrlProductImage {
    @Autowired
    private SvcProductImage service;

    @Operation(summary = "Registrar imagen", description = "Agrega una nueva imagen a un producto por su id")
    @PostMapping("/{id}/image")
    public ResponseEntity<String> createProductImage(@PathVariable Integer id,
                                                     @Valid @RequestBody DtoProductImageIn in,
                                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        in.setProductId(id);
        return service.uploadProductImage(in);
    }

    @Operation(summary = "Eliminar imagen", description = "Elimina una imagen de un producto por su id e imageId")
    @DeleteMapping("/{id}/image/{imageId}")
    public ResponseEntity<String> delteProductImage(@PathVariable Integer id,
                                                    @PathVariable Integer imageId){
        return service.deleteProductImage(imageId);
    }

    @Operation(summary = "Consultar imágenes", description = "Lista todas las imágenes asociadas a un producto por su id")
    @GetMapping("/{id}/image")
    public ResponseEntity<List<ProductImage>> getImages(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getByProduct(id));
    }

    // // POST agregar imagen
    // @PostMapping("/{id}/image")
    // public ResponseEntity<String> create(@PathVariable Integer id,
    //                                      @RequestBody DtoProductImageIn in) {
    //     service.create(id, in);
    //     return ResponseEntity.ok("La imagen ha sido registrada");
    // }

    // // DELETE imagen
    // @DeleteMapping("/{id}/image/{imageId}")
    // public ResponseEntity<String> delete(@PathVariable Long imageId) {
    //     service.delete(imageId);
    //     return ResponseEntity.ok("La imagen ha sido eliminada");
    // }
}