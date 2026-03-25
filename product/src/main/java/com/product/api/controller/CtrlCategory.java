package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CtrlCategory {
	
	@Autowired
	SvcCategory svc;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		return ResponseEntity.ok(svc.findAll());
	}

	@GetMapping("/active")
	public ResponseEntity<List<Category>> findActive(){
		return ResponseEntity.ok(svc.findActive());
	}	

	@PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody DtoCategoryIn in){
		svc.create(in);
		System.out.println("La categoria ha sido registrada");
		System.out.println("Categoria: " + in.getCategory());
		System.out.println("Tag: "+in.getTag());
        return ResponseEntity.ok().build();
    }

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody DtoCategoryIn in, @PathVariable Integer id){
		svc.update(in, id);		
		System.out.println("Categoria: " + in.getCategory());
		System.out.println("Tag: "+in.getTag());
		System.out.println("Id: "+id);
        return ResponseEntity.ok().build();
    }

	@PatchMapping("/{id}/enable")
	public ResponseEntity<String> enable(@PathVariable Integer id){
		svc.enable(id);
		System.out.println("ID: "+id);
		return ResponseEntity.ok().body("Categoría activada");
	}

	@PatchMapping("/{id}/disable")
	public ResponseEntity<String> disable(@PathVariable Integer id){
		svc.disable(id);
		System.out.println("ID: "+id);
		return ResponseEntity.ok("Categoría desactivada.");
	}



	
}
