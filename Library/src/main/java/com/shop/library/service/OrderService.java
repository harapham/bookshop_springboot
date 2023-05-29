package com.shop.library.service;

import java.util.List;
import java.util.Optional;

import com.nimbusds.jose.util.Pair;
import com.shop.library.model.Order;
import com.shop.library.model.ShoppingCart;

public interface OrderService {

	void saveOrder(ShoppingCart cart);
	
//	void acceptOrder(Long id);
	
	void cancelOrder(Long id);

	Optional<Order> findById(Long id);

	List<Order> findAll();

	void acceptOrderC(Long id);

	void acceptOrderA(Long id);

	List<Order> searchOrders(String keyword);

	List<Order> getOrdersSuccess();

	Double sumTotal();

	List<Object[]> getTopCustomer();

}
