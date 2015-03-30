package com.restbucks.domain;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(namespace = "http://schemas.restbucks.com/receipt")
public class Receipt {

	private String amount;
	private Date paid;
	private List<Link> links;

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the paid
	 */
	public Date getPaid() {
		return paid;
	}

	/**
	 * @param paid
	 *            the paid to set
	 */
	public void setPaid(Date paid) {
		this.paid = paid;
	}

	public List<Link> getLinks() {
		return links;
	}

	@XmlElement(name = "link", namespace = "http://schemas.restbucks.com/dap")
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
