package com.restbucks.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restbucks.domain.Order;
import com.restbucks.domain.OrderRepository;

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
		return repository.getById(id);
	}
	
	@RequestMapping("order")
	@ResponseBody
	public List<Order> getAll() {
		return repository.getAll();
	}
	
	@RequestMapping(value="order", method=RequestMethod.POST)
	@ResponseBody
	public String save(Order order) {
		repository.save(order);
		return "Saved order: ";
	}
}
