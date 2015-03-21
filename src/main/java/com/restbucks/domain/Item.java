package com.restbucks.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class Item {

	private String name;
	private String quantity;
	private String milk;
	private String size;
	private String kind;

	public Item(String name, String quantity, String milk, String size,
			String kind) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.milk = milk;
		this.size = size;
		this.kind = kind;
	}
	
	public Item() {
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	@XmlElement
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMilk() {
		return milk;
	}

	@XmlElement
	public void setMilk(String milk) {
		this.milk = milk;
	}

	public String getSize() {
		return size;
	}

	@XmlElement
	public void setSize(String size) {
		this.size = size;
	}

	public String getKind() {
		return kind;
	}

	@XmlElement
	public void setKind(String kind) {
		this.kind = kind;
	}

}
