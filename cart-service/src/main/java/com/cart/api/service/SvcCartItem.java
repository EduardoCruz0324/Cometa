package com.cart.api.service;

import java.util.List;

import com.cart.api.dto.DtoCartItemIn;
import com.cart.api.dto.DtoCartItemOut;

public interface SvcCartItem {

	List<DtoCartItemOut> findAll();

	String save(DtoCartItemIn dto);

	String deleteById(Integer id);

	String deleteAll();
}
