package com.shop.customer.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.library.model.Customer;
import com.shop.library.model.Order;
import com.shop.library.model.OrderDetail;
import com.shop.library.model.ShoppingCart;
import com.shop.library.service.CustomerService;
import com.shop.library.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/check-out")
	public String checkout(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		if (customer.getPhoneNumber() == null || customer.getAddress() == null || customer.getCity() == null
				|| customer.getCountry() == null) {

			model.addAttribute("customer", customer);
			model.addAttribute("error", "You must fill the information after checkout!");
			return "account";
		} else {
			model.addAttribute("customer", customer);
			ShoppingCart cart = customer.getShoppingCart();
			model.addAttribute("cart", cart);
		}
		return "checkout";
	}

	@GetMapping("/order")
	public String order(Principal principal, Model model) {

		if (principal == null) {
			return "redirect:/login";
		}

		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		List<Order> orders = customer.getOrders();
		model.addAttribute("orders", orders);
		return "order";
	}

	@GetMapping("/save-order")
	public String saveOrder(Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}

		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		ShoppingCart cart = customer.getShoppingCart();
		orderService.saveOrder(cart);
		return "redirect:/order";

	}

	@GetMapping("/accept-order")
	public String acceptOrder(@RequestParam("id") Long id) {
		orderService.acceptOrderC(id);

		return "redirect:/order";
	}

	@GetMapping("/cancel-order")
	public String cancelOrder(@RequestParam("id") Long id) {
		orderService.cancelOrder(id);

		return "redirect:/order";
	}

	@GetMapping("/view-order/{id}")
	public String viewOrder(@PathVariable("id") Long id, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		model.addAttribute("customer", customer);
		Order order = orderService.findById(id).get();
		model.addAttribute("order",order);

		return "view-order";
	}
}