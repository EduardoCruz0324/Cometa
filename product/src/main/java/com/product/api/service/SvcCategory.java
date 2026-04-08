package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

//import com.product.api.controller.DtoCategoryIn;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;

import jakarta.validation.Valid;

public interface SvcCategory {
	// muestra la lista de categorias resgistradas []
	public List<Category> findAll();
	// muestra la lista de categorias activas []
	public List<Category> findActive();
	// crea/registra una categoria
	public void create(DtoCategoryIn in);
	// actualiza una categoria
	public void update(DtoCategoryIn in, Integer id);
	// habilita una categoria
	public void enable(Integer id);
	// desabilita o elimina una categoria
	public void disable(Integer id);
	
	void validateCategoryId(Integer id);
    

}
