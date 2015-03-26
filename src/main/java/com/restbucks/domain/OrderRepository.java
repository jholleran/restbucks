package com.restbucks.domain;

import java.util.List;


public interface OrderRepository {

	public Order getById(Long id);
	
	public List<Order> getAll();
	
	public boolean save(Long id, Order order);

	public boolean delete(Long id);
	
	public Long nextId();

}
