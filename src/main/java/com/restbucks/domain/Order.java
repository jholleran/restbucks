package com.restbucks.domain;

import java.util.List;

public class Order {

	private List<Item> items;
	private String location;
	
	public Order() {
	}
	
	public Order(List<Item> items, String location) {
		this.items= items;
		this.location = location;
	}
	
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
