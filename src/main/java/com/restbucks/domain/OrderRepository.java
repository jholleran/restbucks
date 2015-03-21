package com.restbucks.domain;

import java.util.List;


public interface OrderRepository {

	public Order getById(Long id);
	
	public List<Order> getAll();

	public void save(Order order);
	
	public void update(Long id, Order order);

}
