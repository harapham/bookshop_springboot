package com.shop.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.library.model.Customer;
import com.shop.library.model.Order;
import com.shop.library.model.Product;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("select o from Order o where o.customer.firstName like %?1% or o.customer.lastName like %?1% ")
    List<Order> searchOrdersList(String keyword);

	@Query("select o from Order o where o.orderStatus = 'Success'")
    List<Order> getOrdersSuccess();
	
	@Query("SELECT SUM(o.totalPrice) FROM Order o WHERE orderStatus ='Success'" )
	Double sumTotal();
	
	
	@Query("SELECT o.customer.username, SUM(o.totalPrice) FROM Order o WHERE o.orderStatus='Success'"
			+ "GROUP BY o.customer.username ORDER BY SUM(o.totalPrice) DESC")
	List<Object[]> getTopCustomer();
}
