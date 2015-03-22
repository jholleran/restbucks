package com.restbucks.domain;

import java.util.List;


public interface OrderRepository {

	public Order getById(Long id);
	
	public List<Order> getAll();

	public Long save(Order order);
	
	public boolean update(Long id, Order order);

	public boolean delete(Long id);

}
