package com.cart.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cart.api.entity.CartItem;

@Repository
public interface RepoCartItem extends JpaRepository<CartItem, Integer> {

	@Query("SELECT c FROM CartItem c WHERE c.user_id = :userId AND c.status = 1")
	List<CartItem> findActiveByUserId(@Param("userId") Integer userId);

	@Query("SELECT c FROM CartItem c WHERE c.user_id = :userId AND c.gtin = :gtin AND c.status = 1")
	Optional<CartItem> findActiveByUserIdAndGtin(@Param("userId") Integer userId, @Param("gtin") String gtin);

	@Modifying
	@Query("UPDATE CartItem c SET c.status = 0 WHERE c.user_id = :userId AND c.status = 1")
	void emptyCartByUserId(@Param("userId") Integer userId);
}
