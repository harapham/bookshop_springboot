package com.shop.library.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.library.dto.CategoryDto;
import com.shop.library.model.Category;
import com.shop.library.repository.CategoryRepository;
import com.shop.library.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	
	@Autowired
	public CategoryRepository categoryRepository;
	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
			Category categorySave = new Category(category.getName());
			return categoryRepository.save(categorySave);		
	}


	

	
	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		Category categoryUpdate = null;
		try {
			categoryUpdate = categoryRepository.findById(category.getId()).get();
			categoryUpdate.setName(category.getName());
//			categoryUpdate.set_activated(category.is_activated());
//			categoryUpdate.set_deleted(category.is_deleted());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return categoryRepository.save(categoryUpdate);
	}
	@Override
	public Category findById(Long id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		Category category =categoryRepository.findById(id).get();
		category.set_deleted(true);
		category.set_activated(false);
		categoryRepository.save(category);
		
	}

	@Override
	public void enableById(Long id) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(id).get();
		category.set_activated(true);
		category.set_deleted(false);
		categoryRepository.save(category);
		
	}

	@Override
	public List<Category> findAllByActivated() {
		// TODO Auto-generated method stub
		return categoryRepository.findAllByActivated();
	}

	@Override
    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepository.getCategoryAndProduct();
    }
	
	@Override
	public List<Category> getTop5Sale(){
		return categoryRepository.getTop5Sale();
	}
}
