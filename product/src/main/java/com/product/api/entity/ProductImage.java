package com.product.api.entity;

import jakarta.persistence.*;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;

    private String image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Long getProductImageId(){
        return productImageId;
    }    

    public void setProductImageId(Long productImageId){        
        this.productImageId = productImageId;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }
    
    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }
}