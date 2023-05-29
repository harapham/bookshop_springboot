package com.shop.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nimbusds.jose.util.Pair;
import com.shop.library.dto.AdminDto;
import com.shop.library.dto.ProductDto;
import com.shop.library.model.Admin;
import com.shop.library.model.Category;
import com.shop.library.model.Customer;
import com.shop.library.model.Order;
import com.shop.library.model.Product;
import com.shop.library.service.AdminService;
import com.shop.library.service.CategoryService;
import com.shop.library.service.CustomerService;
import com.shop.library.service.OrderService;
import com.shop.library.service.ProductService;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/login")
	public String loginForm() {
		
		return "login";
	}
	
	@RequestMapping("/index")
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication==null|| authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		}
		List<Category> categories = categoryService.findAll();
		model.addAttribute("sizeCategory",categories.size());
		List<Category> categoriesActivated = categoryService.findAllByActivated();
		model.addAttribute("sizeCategoryActivated",categoriesActivated.size());
		
		List<ProductDto> products= productService.findAll();
		model.addAttribute("sizeProducts",products.size());
		List<Product> productsActivated = productService.getAllProducts();
		model.addAttribute("sizeProductActivated",productsActivated.size());
		
		List<Order> orders = orderService.findAll();
		model.addAttribute("sizeOrders", orders.size());
		List<Order> ordersSuccess = orderService.getOrdersSuccess();
		model.addAttribute("sizeOrdersSuccess", ordersSuccess.size());
		
		
		List<Customer> customers = customerService.findAll();
		model.addAttribute("sizeCustomers", customers.size());
		
		Double sumtotal = orderService.sumTotal();
		model.addAttribute("totalRevenue", sumtotal);
		
		List<Category> categoriesTop = categoryService.getTop5Sale();
		model.addAttribute("categoriesTop", categoriesTop);
		
		List<Product> productsTo = productService.getTop5Sale();
		model.addAttribute("productsTo", productsTo);
		
		List<Object[]> orderTop = orderService.getTopCustomer();
		
		if(orderTop.size()>=5) {
			List<Object[]> orderTop2 = null;
			for(int i = 0;i<5;i++) {
				orderTop2.add(orderTop.get(i));
				
			}
			model.addAttribute("orderTop",orderTop2);
		}
		else {
			model.addAttribute("orderTop",orderTop);
		}
		
				
		return "index";
		
		
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("adminDto",new AdminDto());
		return "register";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "forgot-password";
	}
	
	@PostMapping("/register-new")
	public String addNewAdmin(@Valid@ModelAttribute("adminDto")AdminDto adminDto, 
			BindingResult result, Model model) {
		
		try {

			if(result.hasErrors()) {
				model.addAttribute("adminDto",adminDto);
				return "register";
			}
			String username= adminDto.getUsername();
			Admin admin = adminService.findByUsername(username);
			if(admin!=null) {
				model.addAttribute("adminDto",adminDto);
				model.addAttribute("emailError","email đã tồn tại");
				return "register";
			}
			if(adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
				adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
				adminService.save(adminDto);
				model.addAttribute("adminDto",adminDto);
				model.addAttribute("success","tạo tài khoản thành công");
			}
			else {
				model.addAttribute("adminDto",adminDto);
				model.addAttribute("passwordError", "mật khẩu không khớp");
				return "register"; 
			}
//			adminService.save(adminDto);
//			model.addAttribute("adminDto", adminDto);
//			session.setAttribute("message","tạo tài khoản thành công");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("errors","không thể tạo tài khoản do lỗi server");
		}
		
		return "register";
	}
}
