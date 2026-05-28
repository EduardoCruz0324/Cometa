package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.common.mapper.MapperProduct;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImp implements SvcProduct, SvcProductImage {

	@Autowired
	RepoProduct repo;

	@Autowired
	RepoProductImage repoProductImage;

	@Autowired
	MapperProduct mapper;

	@Value("${app.upload.dir}")
	private String uploadDir;

	@Value("${app.upload.images}")
	private String uploadImages;

	@Override
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		try {
			List<Product> products = repo.findAll();
			return new ResponseEntity<>(mapper.fromProductList(products), HttpStatus.OK);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<DtoProductOut> getProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			DtoProductOut out = new DtoProductOut(
				product.getProductId(),
				product.getGtin(),
				product.getProduct(),
				product.getDescription(),
				product.getPrice(),
				product.getStock(),
				product.getCategory_id(),
				product.getStatus()
			);
			out.setImages(readProductImagesFiles(id));
			return new ResponseEntity<>(out, HttpStatus.OK);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<String> createProduct(DtoProductIn in) {
		try {
			Product product = mapper.fromDto(in);
			repo.save(product);
			return new ResponseEntity<>("El producto ha sido registrado", HttpStatus.CREATED);
		} catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<String> updateProduct(Integer id, DtoProductIn in) {
		try {
			validateProductId(id);
			Product product = mapper.fromDto(id, in);
			repo.save(product);
			return new ResponseEntity<>("El producto ha sido actualizado", HttpStatus.OK);
		} catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<String> enableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(1);
			repo.save(product);
			return new ResponseEntity<>("El producto ha sido activado", HttpStatus.OK);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<String> disableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(0);
			repo.save(product);
			return new ResponseEntity<>("El producto ha sido desactivado", HttpStatus.OK);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public List<ProductImage> getByProduct(Integer productId) {
		return repoProductImage.findByProductId(productId);
	}

	@Override
	public ResponseEntity<String> uploadProductImage(DtoProductImageIn in) {
		try {
			if (in.getImage().startsWith("data:image")) {
				int commaIndex = in.getImage().indexOf(",");
				if (commaIndex != -1) {
					in.setImage(in.getImage().substring(commaIndex + 1));
				}
			}
			byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
			String fileName = UUID.randomUUID().toString() + ".png";
			Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);
			Files.createDirectories(imagePath.getParent());
			Files.write(imagePath, imageBytes);

			Product product = repo.findById(in.getProductId())
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El producto no existe"));

			ProductImage productImage = new ProductImage();
			productImage.setProduct(product);
			productImage.setImage("/" + uploadDir + "/" + uploadImages + "/product/" + fileName);
			productImage.setStatus(1);
			repoProductImage.save(productImage);

			return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.OK);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
		}
	}

	@Override
	public ResponseEntity<String> deleteProductImage(Integer id) {
		try {
			repoProductImage.disableProductImage(id);
			return new ResponseEntity<>("La imagen ha sido eliminada", HttpStatus.OK);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	private String[] readProductImagesFiles(Integer product_id) {
		try {
			List<ProductImage> productImages = repoProductImage.findByProduct_ProductId(product_id);
			if (productImages == null || productImages.isEmpty()) {
				return new String[0];
			}
			String[] images = new String[productImages.size()];
			for (int i = 0; i < productImages.size(); i++) {
				String imageUrl = productImages.get(i).getImage();
				if (imageUrl == null || imageUrl.isEmpty()) {
					images[i] = "";
					continue;
				}
				if (imageUrl.startsWith("/")) {
					imageUrl = imageUrl.substring(1);
				}
				Path imagePath = Paths.get(imageUrl);
				if (!Files.exists(imagePath)) {
					images[i] = "";
					continue;
				}
				byte[] imageBytes = Files.readAllBytes(imagePath);
				images[i] = Base64.getEncoder().encodeToString(imageBytes);
			}
			return images;
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer las imágenes");
		}
	}

	private void validateProductId(Integer id) {
		try {
			if (repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}
}
