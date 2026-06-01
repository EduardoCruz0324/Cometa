package com.invoice.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.CartItem;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.InvoiceItem;
import com.invoice.api.entity.Product;
import com.invoice.api.repository.RepoCartItem;
import com.invoice.api.repository.RepoInvoice;
import com.invoice.api.repository.RepoProduct;
import com.invoice.commons.mapper.MapperInvoice;
import com.invoice.commons.util.JwtDecoder;
import com.invoice.exception.ApiException;
import com.invoice.exception.DBAccessException;

@Service
public class SvcInvoiceImp implements SvcInvoice {

	@Autowired
	private RepoInvoice repo;

	@Autowired
	private RepoCartItem repoCartItem;

	@Autowired
	private RepoProduct repoProduct;

	@Autowired
	private JwtDecoder jwtDecoder;

	@Autowired
	MapperInvoice mapper;

	@Override
	public List<DtoInvoiceList> findAll() {
		try {
			if (jwtDecoder.isAdmin()) {
				return mapper.toDtoList(repo.findAll());
			} else {
				Integer user_id = jwtDecoder.getUserId();
				return mapper.toDtoList(repo.findAllByUserId(user_id));
			}
		} catch (DataAccessException e) {
			throw new DBAccessException();
		}
	}

	@Override
	public Invoice findById(Integer id) {
		try {
			Invoice invoice = repo.findById(id).get();
			if (!jwtDecoder.isAdmin()) {
				Integer user_id = jwtDecoder.getUserId();
				if (!invoice.getUser_id().equals(user_id)) {
					throw new ApiException(HttpStatus.FORBIDDEN, "El token no es válido para consultar esta factura");
				}
			}
			return invoice;
		} catch (DataAccessException e) {
			throw new DBAccessException();
		} catch (NoSuchElementException e) {
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de la factura no existe");
		}
	}

	@Override
	@Transactional
	public ApiResponse create() {
		try {
			Integer userId = jwtDecoder.getUserId();

			// 1. Obtener artículos activos del carrito
			List<CartItem> cartItems = repoCartItem.findActiveByUserId(userId);
			if (cartItems.isEmpty()) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "El carrito está vacío");
			}

			// 2. Validar stock y construir invoice_items
			double invoiceTotal = 0.0;
			List<InvoiceItem> invoiceItems = new ArrayList<>();

			for (CartItem cartItem : cartItems) {
				Product product = repoProduct.findByGtin(cartItem.getGtin())
						.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
								"Producto no encontrado: " + cartItem.getGtin()));

				if (product.getStock() < cartItem.getQuantity()) {
					throw new ApiException(HttpStatus.BAD_REQUEST,
							"Stock insuficiente para: " + product.getProductName());
				}

				double unitPrice = product.getPrice().doubleValue();
				int qty = cartItem.getQuantity();
				double itemTotal = unitPrice * qty;
				double itemTaxes = itemTotal * 0.16;
				double itemSubtotal = itemTotal - itemTaxes;

				InvoiceItem item = new InvoiceItem();
				item.setGtin(cartItem.getGtin());
				item.setQuantity(qty);
				item.setUnit_price(unitPrice);
				item.setTotal(itemTotal);
				item.setTaxes(itemTaxes);
				item.setSubtotal(itemSubtotal);
				item.setStatus(1);

				invoiceItems.add(item);
				invoiceTotal += itemTotal;
			}

			// 3. Calcular totales de la factura
			double invoiceTaxes = invoiceTotal * 0.16;
			double invoiceSubtotal = invoiceTotal - invoiceTaxes;

			// 4. Guardar factura con sus items (relación bidireccional: cada item apunta al invoice)
			Invoice invoice = new Invoice();
			invoice.setUser_id(userId);
			invoice.setCreated_at(LocalDate.now());
			invoice.setTotal(invoiceTotal);
			invoice.setTaxes(invoiceTaxes);
			invoice.setSubtotal(invoiceSubtotal);
			invoice.setStatus(1);
			invoice.setItems(invoiceItems);

			for (InvoiceItem item : invoiceItems) {
				item.setInvoice(invoice);
			}

			repo.save(invoice);

			// 5. Restar stock de productos comprados
			for (CartItem cartItem : cartItems) {
				repoProduct.findByGtin(cartItem.getGtin()).ifPresent(product -> {
					product.setStock(product.getStock() - cartItem.getQuantity());
					repoProduct.save(product);
				});
			}

			// 6. Vaciar carrito del cliente
			repoCartItem.emptyCartByUserId(userId);

			return new ApiResponse("La factura ha sido registrada");

		} catch (ApiException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new DBAccessException();
		}
	}
}
