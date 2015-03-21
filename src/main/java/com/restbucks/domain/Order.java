package com.restbucks.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order", namespace = "http://schemas.restbucks.com/order")
public class Order {

	private List<Item> items;
	private String location;
	private String status;
	
	public Order() {
	}
	
	public Order(List<Item> items, String location, String status) {
		this.items= items;
		this.location = location;
		this.status = status;
	}
	
	@XmlElement(name="item")
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

	public String getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}

}
