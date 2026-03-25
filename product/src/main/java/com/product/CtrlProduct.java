package com.product;

import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CtrlProduct {

	@GetMapping
	public List<Category> getCategories(){

	    List<Category> categories = new ArrayList<Category>();

	    categories.add(new Category(1,"lentes","LTS",1));
	    
	    Category relojes = new Category(1,"relojes","Rljs",1);
	    categories.add(relojes);

	    // agregar 3er categoria

	    return categories;
	}
}
