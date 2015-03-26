package com.restbucks.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {

	private List<Item> items;
	private String location;
	private String status;
	private String cost;
	private String payment;
	
	public Order() {
	}
	
	public Order(List<Item> items, String location, String status, String cost) {
		this.items= items;
		this.location = location;
		this.status = status;
		this.cost = cost;
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

	public String getCost() {
		return cost;
	}

	@XmlElement
	public void setCost(String cost) {
		this.cost = cost;
	}

	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	@XmlElement(namespace="http://schemas.restbucks.com/dap")
	public void setPayment(String payment) {
		this.payment = payment;
	}

}
