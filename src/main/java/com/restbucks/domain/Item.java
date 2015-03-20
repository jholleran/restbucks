package com.restbucks.domain;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMilk() {
		return milk;
	}

	public void setMilk(String milk) {
		this.milk = milk;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}
