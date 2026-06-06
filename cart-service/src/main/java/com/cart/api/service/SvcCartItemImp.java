package com.cart.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cart.api.dto.DtoCartItemIn;
import com.cart.api.dto.DtoCartItemOut;
import com.cart.api.entity.CartItem;
import com.cart.api.entity.Product;
import com.cart.api.repository.RepoCartItem;
import com.cart.api.repository.RepoProduct;
import com.cart.commons.util.JwtDecoder;
import com.cart.exception.ApiException;
import com.cart.exception.DBAccessException;

@Service
public class SvcCartItemImp implements SvcCartItem {

	@Autowired
	private RepoCartItem repoCartItem;

	@Autowired
	private RepoProduct repoProduct;

	@Autowired
	private JwtDecoder jwtDecoder;

	@Override
	public List<DtoCartItemOut> findAll() {
		try {
			Integer userId = jwtDecoder.getUserId();
			List<CartItem> items = repoCartItem.findActiveByUserId(userId);

			return items.stream().map(item -> {
				Product product = repoProduct.findByGtin(item.getGtin()).orElse(null);
				String name = product != null ? product.getProductName() : item.getGtin();
				Double price = product != null ? product.getPrice().doubleValue() : 0.0;
				return new DtoCartItemOut(item.getCart_item_id(), item.getGtin(), name, price, item.getQuantity());
			}).toList();

		} catch (ApiException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new DBAccessException();
		}
	}

	@Override
	@Transactional
	public String save(DtoCartItemIn dto) {
		try {
			Integer userId = jwtDecoder.getUserId();

			// Validar que el producto existe
			Product product = repoProduct.findByGtin(dto.getGtin())
					.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
							"El producto con gtin " + dto.getGtin() + " no existe"));

			// Validar stock suficiente
			if (product.getStock() < dto.getQuantity()) {
				throw new ApiException(HttpStatus.BAD_REQUEST,
						"Stock insuficiente. Disponible: " + product.getStock());
			}

			// Si ya existe en el carrito, actualizar cantidad
			Optional<CartItem> existing = repoCartItem.findActiveByUserIdAndGtin(userId, dto.getGtin());
			if (existing.isPresent()) {
				CartItem item = existing.get();
				int newQty = item.getQuantity() + dto.getQuantity();
				if (product.getStock() < newQty) {
					throw new ApiException(HttpStatus.BAD_REQUEST,
							"Stock insuficiente para la cantidad total. Disponible: " + product.getStock());
				}
				item.setQuantity(newQty);
				repoCartItem.save(item);
				return "Cantidad actualizada en el carrito";
			}

			// Agregar nuevo item al carrito
			CartItem item = new CartItem();
			item.setUser_id(userId);
			item.setGtin(dto.getGtin());
			item.setQuantity(dto.getQuantity());
			item.setStatus(1);
			repoCartItem.save(item);

			return "Producto agregado al carrito";

		} catch (ApiException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new DBAccessException();
		}
	}

	@Override
	@Transactional
	public String deleteById(Integer id) {
		try {
			Integer userId = jwtDecoder.getUserId();
			CartItem item = repoCartItem.findById(id)
					.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El artículo no existe"));

			if (!item.getUser_id().equals(userId)) {
				throw new ApiException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar este artículo");
			}

			repoCartItem.deleteById(id);
			return "Artículo eliminado del carrito";

		} catch (ApiException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new DBAccessException();
		}
	}

	@Override
	@Transactional
	public String deleteAll() {
		try {
			Integer userId = jwtDecoder.getUserId();
			repoCartItem.emptyCartByUserId(userId);
			return "Carrito vaciado";

		} catch (ApiException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new DBAccessException();
		}
	}
}
