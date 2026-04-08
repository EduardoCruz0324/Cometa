package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Category;

import jakarta.transaction.Transactional;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {


	@Query(value="SELECT * FROM category ORDER BY category", nativeQuery=true)
	List<Category> findAll();

	List<Category> findByStatusOrderByCategory(Integer status);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional	
	@Query(value = "INSERT INTO category(category,tag,status) VALUES(:category,:tag,1)", nativeQuery = true)
    void create(String category,String tag);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status=:status WHERE category_id=:category_id", nativeQuery = true)
    void updateCategoryStatus(@Param("category_id") Integer category_id, @Param("status") Integer status);
	
}
