package com.restbucks.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order", namespace = "http://schemas.restbucks.com/order")
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
	
	@XmlElementWrapper(name="items")
	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getLocation() {
		return location;
	}
	
	@XmlElement
	public void setLocation(String location) {
		this.location = location;
	}

}
