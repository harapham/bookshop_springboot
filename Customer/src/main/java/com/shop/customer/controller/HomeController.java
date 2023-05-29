package com.shop.customer.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shop.library.dto.ProductDto;
import com.shop.library.model.Category;
import com.shop.library.model.Customer;
import com.shop.library.model.ShoppingCart;
import com.shop.library.service.CategoryService;
import com.shop.library.service.CustomerService;
import com.shop.library.service.ProductService;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = {"/index", "/","/home"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getShoppingCart();
            if (cart == null) {
            	cart = new ShoppingCart();
            }
            session.setAttribute("totalItems", cart.getTotalItems());
        }else{
            session.removeAttribute("username");
        }
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "index";
    }

//    @GetMapping("/home")
//    public String index(Model model){
//        List<Category> categories = categoryService.findAll();
//        List<ProductDto> productDtos = productService.findAll();
//        model.addAttribute("categories", categories);
//        model.addAttribute("products", productDtos);
//        return "index";
//    }
}
