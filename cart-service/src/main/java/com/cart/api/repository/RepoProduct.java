package com.cart.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.api.entity.Product;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {

	Optional<Product> findByGtin(String gtin);
}
