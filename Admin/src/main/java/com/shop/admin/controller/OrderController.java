package com.shop.admin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.library.dto.ProductDto;
import com.shop.library.model.Category;
import com.shop.library.model.Customer;
import com.shop.library.model.Order;
import com.shop.library.service.CategoryService;
import com.shop.library.service.CustomerService;
import com.shop.library.service.OrderService;
import com.shop.library.utils.CusTop;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public String orders(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}

		List<Order> orders = orderService.findAll();
		model.addAttribute("orders", orders);
		model.addAttribute("size", orders.size());
		return "orders";
	}

	@GetMapping("/order-view/{id}")
	public String orderView(@PathVariable("id") Long id, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		Order order = orderService.findById(id).get();

		model.addAttribute("order", order);
		return "order-view";
	}

	@PostMapping("/order-view/{id}")
	public String processUpdate(@PathVariable("id") Long id, 
			@ModelAttribute("order") Order order,
			RedirectAttributes attributes) {
		try {
			orderService.acceptOrderA(id);
			attributes.addFlashAttribute("success", "Update successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "Failed to update!");
		}
		return "redirect:/orders";

	}

    
    @GetMapping("/search-orders")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model,
                                 Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Order> orders = orderService.searchOrders(keyword);
        
        model.addAttribute("orders", orders);
       
        return "result-orders";
    }
    
//    @GetMapping("/ordertest")
//    @ResponseBody
//    public List<CusTop> test() {
//    	return orderService.getTopCustomer();
//    }
}
