package com.restbucks.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class InMemoryOrderRepository implements OrderRepository {

	private final Map<Long, Order> orders = new HashMap<Long, Order>();

	public InMemoryOrderRepository() {
		Item latte = new Item("latte", "1", "whole", "small", null);
		Item cookie = new Item("cookie", "2", null, null, "chocolate-chip");
		List<Item> order1Items = new ArrayList<Item>();
		order1Items.add(latte);
		order1Items.add(cookie);
		
		save(new Order(order1Items, "takeaway", "pending"));
		
		Item mocha = new Item("mocha", "3", "skimmed", "large", null);
		Item cheeseCake = new Item("cake", "2", null, null, "cheese-cake");
		List<Item> order2Items = new ArrayList<Item>();
		order2Items.add(mocha);
		order2Items.add(cheeseCake);
		
		save(new Order(order2Items, "instore", "served"));
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
	public void save(Order animal) {
		orders.put(new Long(orders.size()), animal);
	}

}
