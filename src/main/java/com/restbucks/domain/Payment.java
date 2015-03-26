package com.restbucks.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "payment", namespace = "http://schemas.restbucks.com/payment")
public class Payment {

	private String amount;
	private String cardHolderName;
	private String cardNumber;
	private String expiryMonth;
	private String expiryYear;
	private String status;
	private List<Link> links;
	
	public String getAmount() {
		return amount;
	}
	
	@XmlElement
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCardHolderName() {
		return cardHolderName;
	}
	
	@XmlElement
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	@XmlElement
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getExpiryMonth() {
		return expiryMonth;
	}
	
	@XmlElement
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	
	public String getExpiryYear() {
		return expiryYear;
	}
	
	@XmlElement
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<Link> getLinks() {
		return links;
	}

	@XmlElement(name="link", namespace="http://schemas.restbucks.com/dap")
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
