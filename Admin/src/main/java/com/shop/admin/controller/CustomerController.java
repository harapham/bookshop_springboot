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
import com.shop.library.service.CategoryService;
import com.shop.library.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("/customers")
	public String customers(Model model, Principal principal) {
		if(principal ==null) {
			return "redirect:/login";
		}
		
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers",customers);
		model.addAttribute("size", customers.size());
		model.addAttribute("customerNew", new Customer());
		return "customers";
	}
	

	@GetMapping("/update-customer/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
//        List<Category> categories = categoryService.findAllByActivated();
        Customer customer= customerService.findById(id);
        
        model.addAttribute("customer", customer);
        return "update-customer";
    }


    @PostMapping("/update-customer/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("customer") Customer customer,
                                RedirectAttributes attributes
                                ){
        try {
            customerService.update(customer);
            attributes.addFlashAttribute("success", "Update successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/customers";

    }
    
    
    @GetMapping("/search-customers")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model,
                                 Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Customer> customers = customerService.searchCustomers(keyword);
        
        model.addAttribute("customers", customers);
       
        return "result-customers";
    }
}
