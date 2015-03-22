package com.restbucks.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		if (order == null) {
			throw new OrderNotFoundException(id);
		}
		
		//TODO handle Internal Server error
		return order;
	}

	@RequestMapping("order")
	@ResponseBody
	public Orders getAll() {
		//TODO handle Internal Server error
		
		return new Orders(repository.getAll());
	}

	@RequestMapping(value = "order", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Order> save(@RequestBody Order order) {
		//TODO handle Bad Request
		//TODO handle Internal Server error
		
		Long id = repository.save(order);

		final URI location = ServletUriComponentsBuilder
				.fromCurrentServletMapping().path("/api/order/{id}").build()
				.expand(id).toUri();

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);

		return new ResponseEntity<Order>(order, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "order/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Order update(@PathVariable Long id, @RequestBody Order order) {
		Order savedOrder = repository.getById(id);
		if (savedOrder == null) {
			throw new OrderNotFoundException(id);
		}
		// need to handle a conflict if it occurs
		boolean conflict = repository.update(id, order);
		if (conflict) {
			throw new ConflictException(id);
		}
		
		//TODO handle Internal Server error
		
		return order;
	}

	@RequestMapping(value = "order/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Long id) {
		Order order = repository.getById(id);
		if (order == null) {
			throw new OrderNotFoundException(id);
		}

		boolean canDelete = repository.delete(id);
		if (!canDelete) {
			throw new MethodNotAllowedException(id);
		}
		
		//TODO handle Internal Server error
	}
}
