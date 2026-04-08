package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;

import java.nio.file.Path;

@Service
public class SvcProductImageImp implements SvcProductImage {

    @Autowired
    private RepoProductImage repo;

    @Autowired
    private RepoProduct repoProduct;

    @Value("${app.upload.dir}")
    private String uploadDir;
    
    @Value("${app.upload.images}")
    private String uploadImages;        

    @Override
    public void create(Integer productId, DtoProductImageIn in) {
        try{
            //validamos producto
            Product product = repoProduct.findById(productId).orElseThrow(() -> new RuntimeException("Producto no existe"));            
            //limpiamos base64
            if (in.getImage().startsWith("data:image")) {
                int commaIndex = in.getImage().indexOf(",");
                in.setImage(in.getImage().substring(commaIndex + 1));
            }
            //decode
            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());     
            //nombramos
            String fileName = UUID.randomUUID().toString() + ".png";       
            // ruta            
            Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);            
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageBytes);
            // guardamos en BD
            ProductImage img = new ProductImage();
            img.setImage("/product/" + fileName);
            img.setProduct(product);

            repo.save(img);            
        }catch(IOException e){
            throw new RuntimeException("error guardando imagen");
        }
        // Product product = repoProduct.findById(productId).orElseThrow();

        // ProductImage pi = new ProductImage();
        // pi.setImage(image);
        // pi.setProduct(product);

        // repo.save(pi);
    }

    @Override
    public List<ProductImage> getByProduct(Integer productId) {
        return repo.findByProduct_ProductId(productId);
    }

    @Override
    public void delete(Long imageId) {
        repo.deleteById(imageId);
    }

    @Override
    public void create(Integer productId, String image) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    // @Override
    // public List<ProductImage> getByProduct(Integer productId) {
    //     return repo.findByProduct_ProductId(productId);
    // }

    // @Override
    // public void delete(Long imageId) {
    //     repo.deleteById(imageId);
    // }
}