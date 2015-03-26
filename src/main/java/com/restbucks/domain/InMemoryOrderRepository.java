package com.restbucks.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class InMemoryOrderRepository implements OrderRepository {

	private final Map<Long, Order> orders = new HashMap<Long, Order>();
	private Long idCounter = 0L;
	
	public InMemoryOrderRepository() {
		Item largeLatte = new Item("latte", "1", "whole", "large", null);
		List<Item> order1Items = new ArrayList<Item>();
		order1Items.add(largeLatte);
		
		save(nextId(), new Order(order1Items, "takeaway", "payment-expected", "1.50"));
		
		Item latte = new Item("latte", "1", "whole", "small", null);
		Item cookie = new Item("cookie", "2", null, null, "chocolate-chip");
		List<Item> order2Items = new ArrayList<Item>();
		order2Items.add(latte);
		order2Items.add(cookie);
		
		save(nextId(), new Order(order2Items, "takeaway", "preparing", "2.50"));
		
		Item mocha = new Item("mocha", "3", "skimmed", "large", null);
		Item cheeseCake = new Item("cake", "2", null, null, "cheese-cake");
		List<Item> order3Items = new ArrayList<Item>();
		order3Items.add(mocha);
		order3Items.add(cheeseCake);
		
		save(nextId(), new Order(order3Items, "instore", "ready", "4.00"));
		
		Item mocha2 = new Item("mocha", "2", "skimmed", "large", null);
		List<Item> order4Items = new ArrayList<Item>();
		order4Items.add(mocha2);
		
		save(nextId(), new Order(order4Items, "instore", "taken", "4.00"));
	}

	@Override
	public Order getById(Long id) {
		return orders.get(id);
	}

	@Override
	public List<Order> getAll() {
		return new ArrayList<Order>(orders.values());
	}

	@Override
	public boolean save(Long id, Order order) {
		orders.put(id, order);
		return false;
	}

	@Override
	public boolean delete(Long id) {
		orders.remove(id);
		return true;
	}

	@Override
	public Long nextId() {
		return idCounter++;
	}

}
