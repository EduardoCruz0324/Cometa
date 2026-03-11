package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

	/*
	@GetMapping
	public List<Category> getCategories(){

	    List<Category> categories = new ArrayList<Category>();

	    categories.add(new Category(1,"lentes","LTS",1));
	    
	    Category relojes = new Category(2,"relojes","Rljs",1);
	    categories.add(relojes);
	    
	    categories.add(new Category(3,"Zapatos","Charol",1));
	    

	    // agregar 3er categoria

	    return categories;
	}
	*/
	
	@Autowired
	SvcCategory svc;
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategories(){
		return svc.getCategories();
	}
	
}
