package com.shop.library.service;

import java.util.List;

import com.shop.library.dto.CustomerDto;
import com.shop.library.model.Customer;

public interface CustomerService {

    CustomerDto save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer saveInfor(Customer customer);

	List<Customer> findAll();

	Customer findById(Long id);

	Customer update(Customer customer);

	List<Customer> searchCustomers(String keyword);
}
