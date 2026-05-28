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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class CtrlProductImage {
     @Autowired
    private SvcProductImage service;

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

    @DeleteMapping("/{id}/image/{imageId}")
    public ResponseEntity<String> delteProductImage(@PathVariable Integer id,
                                                    @PathVariable Integer imageId){
        return service.deleteProductImage(imageId);
    }
    
    //////////    
    // GET imágenes de un producto
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