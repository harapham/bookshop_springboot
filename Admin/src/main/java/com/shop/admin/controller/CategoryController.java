package com.shop.admin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.library.dto.ProductDto;
import com.shop.library.model.Category;
import com.shop.library.service.CategoryService;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/categories")
	public String categories(Model model, Principal principal) {
		if(principal ==null) {
			return "redirect:/login";
		}
		
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories",categories);
		model.addAttribute("size", categories.size());
		model.addAttribute("categoryNew", new Category());
		return "categories";
	}
	
	@PostMapping("/add-category")
	public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes) {
		try {
			
			categoryService.save(category);
			attributes.addFlashAttribute("success", "added successfully");
		}
		catch(DataIntegrityViolationException e) {
			attributes.addFlashAttribute("failed","failed (duplicate)");
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			attributes.addFlashAttribute("failed","Failed");
		}
		return "redirect:/categories";
	}
	
	@RequestMapping(value = "/getById/", method = {RequestMethod.PUT, RequestMethod.GET})
	@ResponseBody
	public Category getById( Long id) {
		return categoryService.findById(id);
	}

	@GetMapping("/update-category")
	public String update(Category category, RedirectAttributes attributes) {
		try {
			categoryService.update(category);
			attributes.addFlashAttribute("success","update successfully");
		} catch(DataIntegrityViolationException e) {
			attributes.addFlashAttribute("failed","failed (duplicate)");
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			attributes.addFlashAttribute("failed","Failed");
		}
		return "redirect:/categories";
	}
	
	@RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
	public String delete(Long id, RedirectAttributes attributes) {
		try {
			categoryService.deleteById(id);
			attributes.addFlashAttribute("success","deleted successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			attributes.addFlashAttribute("failed","Failed");
		}
		return "redirect:/categories";
	}
	
	@RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
	public String enable(Long id, RedirectAttributes attributes) {
		try {
			categoryService.enableById(id);
			attributes.addFlashAttribute("success","enabled successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			attributes.addFlashAttribute("failed","Failed");
		}
		return "redirect:/categories";
	}
	@GetMapping("/catetest")
    @ResponseBody
    public List<Category> test() {
    	return categoryService.getTop5Sale();
    }
}
