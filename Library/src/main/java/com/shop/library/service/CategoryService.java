package com.shop.library.service;

import java.util.List;

import com.shop.library.dto.CategoryDto;
import com.shop.library.model.Category;

public interface CategoryService {
	List<Category> findAll();
	Category save(Category category);
	Category findById(Long id);
	Category update(Category category);
	void deleteById(Long id);
	void enableById(Long id);
	List<Category> findAllByActivated();
	List<Category> getTop5Sale();
	 /*Customer*/
    List<CategoryDto> getCategoryAndProduct();
	
}
