package com.cart.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.api.dto.DtoCartItemIn;
import com.cart.api.dto.DtoCartItemOut;
import com.cart.api.service.SvcCartItem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart-item")
@Tag(name = "Cart", description = "Carrito de compras")
public class CtrlCartItem {

	@Autowired
	private SvcCartItem svc;

	@GetMapping
	@Operation(summary = "Consultar carrito", description = "Devuelve los artículos del carrito del cliente autenticado")
	public ResponseEntity<List<DtoCartItemOut>> findAll() {
		return ResponseEntity.ok(svc.findAll());
	}

	@PostMapping
	@Operation(summary = "Agregar al carrito", description = "Agrega un producto al carrito. Si ya existe, actualiza la cantidad.")
	public ResponseEntity<String> save(@Valid @RequestBody DtoCartItemIn dto) {
		return ResponseEntity.ok(svc.save(dto));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar artículo", description = "Elimina un artículo del carrito por su id")
	public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(svc.deleteById(id));
	}

	@DeleteMapping
	@Operation(summary = "Vaciar carrito", description = "Elimina todos los artículos del carrito del cliente autenticado")
	public ResponseEntity<String> deleteAll() {
		return ResponseEntity.ok(svc.deleteAll());
	}
}
