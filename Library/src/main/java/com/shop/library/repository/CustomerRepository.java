package com.shop.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.library.model.Customer;
import com.shop.library.model.Product;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    
    @Query("select c from Customer c where c.firstName like %?1% or c.lastName like %?1% or c.username like %?1% or c.phoneNumber like %?1%")
    List<Customer> searchCustomersList(String keyword);

}