package com.shop.library.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.util.Pair;
import com.shop.library.model.CartItem;
import com.shop.library.model.Category;
import com.shop.library.model.Customer;
import com.shop.library.model.Order;
import com.shop.library.model.OrderDetail;
import com.shop.library.model.Product;
import com.shop.library.model.ShoppingCart;
import com.shop.library.repository.CartItemRepository;
import com.shop.library.repository.CategoryRepository;
import com.shop.library.repository.OrderDetailRepository;
import com.shop.library.repository.OrderRepository;
import com.shop.library.repository.ProductRepository;
import com.shop.library.repository.ShoppingCartRepository;
import com.shop.library.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public void saveOrder(ShoppingCart cart) {
		// TODO Auto-generated method stub
		Order order = new Order();
		order.setOrderStatus("Pending");
		order.setOrderDate(new Date());
		order.setCustomer(cart.getCustomer());
		order.setTotalPrice(cart.getTotalPrices());
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (CartItem item : cart.getCartItem()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setProduct(item.getProduct());
			orderDetail.setUnitPrice(item.getProduct().getCostPrice());
			orderDetailRepository.save(orderDetail);
			orderDetails.add(orderDetail);
			cartItemRepository.delete(item);
		}
		
		order.setOrderDetailList(orderDetails);
		cart.setCartItem(new HashSet<>());
		cart.setTotalItems(0);
		cart.setTotalPrices(0);
		shoppingCartRepository.save(cart);
		orderRepository.save(order);
	}

	@Override
	public void acceptOrderA(Long id) {
		Order order = orderRepository.findById(id).get();
		order.setDeliveryDate(new Date());
		order.setOrderStatus("Shipping");
		orderRepository.save(order);
	}
	@Override
	public void acceptOrderC(Long id) {
		Order order = orderRepository.findById(id).get();
		order.setDeliveryDate(new Date());
		order.setOrderStatus("Success");
		for(OrderDetail orderDetail: order.getOrderDetailList()) {
			Product product = orderDetail.getProduct();
			product.setSale(product.getSale()+orderDetail.getQuantity());
			product.setCurrentQuantity(product.getCurrentQuantity()-orderDetail.getQuantity());
			productRepository.save(product);
			
			Category category= product.getCategory();
			category.setSale(category.getSale()+orderDetail.getQuantity());
			categoryRepository.save(category);
		}
		orderRepository.save(order);
	}

	@Override
	public void cancelOrder(Long id) {
		// TODO Auto-generated method stub
		orderRepository.deleteById(id);
		
	}

	@Override
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	 @Override
	    public List<Order> searchOrders(String keyword) {
	        List<Order> orders = orderRepository.searchOrdersList(keyword);
	        
	        return orders;
	    }

	@Override
	public List<Order> getOrdersSuccess() {
		return orderRepository.getOrdersSuccess();
	}

	@Override
	public Double sumTotal() {
		return orderRepository.sumTotal();
	}

	@Override
	public List<Object[]> getTopCustomer() {
		return orderRepository.getTopCustomer();
	}

	
}
