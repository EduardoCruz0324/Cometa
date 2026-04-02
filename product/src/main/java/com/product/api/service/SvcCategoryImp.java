package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service; //nuevo import

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcCategoryImp implements SvcCategory{
    
    
    @Autowired
    RepoCategory repo;

    @Override
    public List<Category> findAll(){
        try{
            return repo.findAll();
        }catch(DataAccessException e){
            throw new DBAccessException(e);
        }
    }

    public List<Category> findActive(){
        try{
            return repo.findByStatusOrderByCategory(1);
        }catch(DataAccessException e){
            throw new DBAccessException(e);
        }        
    }

    @Override
    public void create(DtoCategoryIn in){
        try{
            repo.create(in.getCategory(), in.getTag());
        }catch(DataAccessException e){
            if(e.getLocalizedMessage().contains("category.category"))
                throw new ApiException(HttpStatus.CONFLICT,"El nombre de la categoria ya esta registrado" );
            if(e.getLocalizedMessage().contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT,"El tag de la categoria ya esta registrado" );
            throw new DBAccessException(e);
        }                
    }

    @Override
    public void update(DtoCategoryIn in, Integer id){
        try{
            Category category = repo.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id de la categoria no existe"));
            
            category.setCategory(in.getCategory());
            category.setTag(in.getTag());
            
            repo.save(category);

        }catch(DataAccessException e){
            if(e.getLocalizedMessage().contains("category.category"))
                throw new ApiException(HttpStatus.CONFLICT,"El nombre de la categoria ya esta registrado");
            if(e.getLocalizedMessage().contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT,"El tag de la categoria ya esta registrado");

            throw new DBAccessException(e);
        }
    }


    @Override
    public void enable(Integer id){
        try{
            validateCategoryId(id);
            repo.updateCategoryStatus(id,1);            
        }catch(DataAccessException e){
            throw new DBAccessException(e);
        }
    }

    @Override
    public void disable(Integer id){
        try{
            validateCategoryId(id);
            repo.updateCategoryStatus(id,0);            
        }catch(DataAccessException e){
            throw new DBAccessException(e);
        }
    }
    
    public void validateCategoryId(Integer id){
        if(repo.findById(id).isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoria no existe");
    }
        

}