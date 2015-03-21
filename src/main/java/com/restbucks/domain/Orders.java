package com.restbucks.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orders", namespace = "http://schemas.restbucks.com/order")
public class Orders {

	private List<Order> orders;

	public Orders(List<Order> orders) {
		super();
		this.orders = orders;
	}
	
	public Orders() {
		
	}

	@XmlElement(name="order")
	public List<Order> getOrders() {
		return orders;
	}
	
}
