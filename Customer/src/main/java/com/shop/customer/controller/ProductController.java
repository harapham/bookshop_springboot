package com.shop.customer.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.library.dto.CategoryDto;
import com.shop.library.dto.ProductDto;
import com.shop.library.model.Category;
import com.shop.library.model.Product;
import com.shop.library.service.CategoryService;
import com.shop.library.service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model){
        Product product = productService.getProductById(id);
        Long categoryId = product.getCategory().getId();
        List<Product>  products = productService.getRelatedProducts(categoryId);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "product-detail";
    }

    @GetMapping("/products-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Long categoryId ,Model model){
        Category category = categoryService.findById(categoryId);
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getProductsInCategory(categoryId);
        model.addAttribute("category",category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "products-in-category";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model){
        List<Category> categories = categoryService.findAllByActivated();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.filterHighPrice();
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "filter-high-price";
    }


    @GetMapping("/low-price")
    public String filterLowPrice(Model model){
        List<Category> categories = categoryService.findAllByActivated();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.filterLowPrice();
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "filter-low-price";
    }

    
    @GetMapping("/search-result")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model){
        
        List<Product> products = productService.searchProducts(keyword);
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("products", products);
        return "search-products";
    }
}