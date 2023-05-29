package com.shop.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.library.dto.CategoryDto;
import com.shop.library.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query("SELECT c FROM Category c WHERE c.is_activated = true AND c.is_deleted = false ")
	List<Category> findAllByActivated();
	
	@Query(value="SELECT * FROM categories ORDER BY sale DESC Limit 0, 5", nativeQuery=true)
	List<Category> getTop5Sale();
	 /*Customer*/ 
    @Query("select new com.shop.library.dto.CategoryDto(c.id, c.name, count(p.category.id)) from Category c inner join Product p on p.category.id = c.id " +
            " where c.is_activated = true and c.is_deleted = false group by c.id")
    List<CategoryDto> getCategoryAndProduct();
}
