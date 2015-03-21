package com.restbucks.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restbucks.domain.Order;
import com.restbucks.domain.OrderRepository;
import com.restbucks.domain.Orders;

@Controller
@RequestMapping("api")
public class OrderController {

	private OrderRepository repository;
	
	@Autowired
	public OrderController(OrderRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping("order/{id}")
	@ResponseBody
	public Order getById(@PathVariable Long id) {
		Order order = repository.getById(id);
		if(order == null) {
			throw new OrderNotFoundException(id);
		}
		return order;
	}
	
	@RequestMapping("order")
	@ResponseBody
	public Orders getAll() {
		return new Orders(repository.getAll());
	}
	
	@RequestMapping(value="order", method=RequestMethod.POST)
	@ResponseBody
	public String save(@RequestBody Order order) {
		repository.save(order);
		return "Saved order: ";
	}
	
	@RequestMapping(value="order/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public Order update(@PathVariable Long id, @RequestBody Order order) {
		Order savedOrder = repository.getById(id);
		if(savedOrder == null) {			
			throw new OrderNotFoundException(id);
		}
		repository.update(id, order);
		return order;
	}
}
