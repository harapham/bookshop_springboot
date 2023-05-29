package com.shop.library.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.library.dto.CustomerDto;
import com.shop.library.dto.ProductDto;
import com.shop.library.model.Category;
import com.shop.library.model.Customer;
import com.shop.library.model.Product;
import com.shop.library.repository.CustomerRepository;
import com.shop.library.repository.RoleRepository;
import com.shop.library.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDto save(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRoles(Arrays.asList(repository.findByName("CUSTOMER")));

        Customer customerSave = customerRepository.save(customer);
        return mapperDTO(customerSave);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer saveInfor(Customer customer) {
        Customer customer1 = customerRepository.findByUsername(customer.getUsername());
        customer1.setAddress(customer.getAddress());
        customer1.setCity(customer.getCity());
        customer1.setCountry(customer.getCountry());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        return customerRepository.save(customer1);
    }

    private CustomerDto mapperDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPassword(customer.getPassword());
        customerDto.setUsername(customer.getUsername());
        return customerDto;
    }

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer findById(Long id) {
		return customerRepository.findById(id).get();
	}
    
	@Override
	public Customer update(Customer customer) {
		// TODO Auto-generated method stub
		Customer customerUpdate = null;
		try {
			customerUpdate = customerRepository.findById(customer.getId()).get();
			customerUpdate.setFirstName(customer.getFirstName());
			customerUpdate.setLastName(customer.getLastName());
			customerUpdate.setAddress(customer.getAddress());
			customerUpdate.setCity(customer.getCity());
			customerUpdate.setCountry(customer.getCountry());
			customerUpdate.setPhoneNumber(customer.getPhoneNumber());
			customerUpdate.setUsername(customer.getUsername());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return customerRepository.save(customerUpdate);
	}
	
	 @Override
	    public List<Customer> searchCustomers(String keyword) {
	        List<Customer> customers = customerRepository.searchCustomersList(keyword);
	        
	        return customers;
	    }
	 
	 
}