package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import org.springframework.http.HttpStatus;

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

    // @Override
    // public void create(Integer productId, DtoProductImageIn in) {
    //     try{
    //         //validamos producto
    //         Product product = repoProduct.findById(productId).orElseThrow(() -> new RuntimeException("Producto no existe"));            
    //         //limpiamos base64
    //         if (in.getImage().startsWith("data:image")) {
    //             int commaIndex = in.getImage().indexOf(",");
    //             in.setImage(in.getImage().substring(commaIndex + 1));
    //         }
    //         //decode
    //         byte[] imageBytes = Base64.getDecoder().decode(in.getImage());     
    //         //nombramos
    //         String fileName = UUID.randomUUID().toString() + ".png";       
    //         // ruta            
    //         Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);            
    //         Files.createDirectories(imagePath.getParent());
    //         Files.write(imagePath, imageBytes);
    //         // guardamos en BD
    //         ProductImage img = new ProductImage();
    //         img.setImage("/product/" + fileName);
    //         img.setProduct(product);

    //         repo.save(img);            
    //     }catch(IOException e){
    //         throw new RuntimeException("error guardando imagen");
    //     }
    //     // Product product = repoProduct.findById(productId).orElseThrow();

    //     // ProductImage pi = new ProductImage();
    //     // pi.setImage(image);
    //     // pi.setProduct(product);

    //     // repo.save(pi);
    // }

    @Override
    public List<ProductImage> getByProduct(Integer productId) {
        // return repo.findByProduct_ProductId(productId);
        return repo.findByProductId(productId);
    }

    public void delete(Integer imageId) {
        repo.deleteById(imageId);
    }

    public void create(Integer productId, String image) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public ResponseEntity<String> uploadProductImage(DtoProductImageIn in){
        try{
            //eliminar el prefijo "data:image/png;base64" si existe
            if(in.getImage().startsWith("data:image")){
                int commaIndex = in.getImage().indexOf(",");
                if(commaIndex != -1){
                    in.setImage(in.getImage().substring(commaIndex + 1));
                }
            }
            //decodifica la cadena a bytes
            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
            //genera un nombre
            String fileName = UUID.randomUUID().toString() +".png";

            Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);

            // Asegurarse de que el directorio exista
            Files.createDirectories(imagePath.getParent());
            // Escribir el archivo en el sistema de archivos
            Files.write(imagePath, imageBytes);

            // Validamos que exista el producto
            Product product = repoProduct.findById(in.getProductId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El producto no existe"));

            // Guardar la ruta en la base de datos
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setImage("/" + uploadDir + "/" + uploadImages + "/product/" + fileName);
            productImage.setStatus(1);

            repo.save(productImage);

            return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.OK);

        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        }
    }

    @Override
    public ResponseEntity<String> deleteProductImage(Integer id){
        try{
            repo.disableProductImage(id);
            return new ResponseEntity<>("La imagen ha sido eliminada", HttpStatus.OK);            
        }catch(DataAccessException e){
            throw new DBAccessException(e);
        }
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