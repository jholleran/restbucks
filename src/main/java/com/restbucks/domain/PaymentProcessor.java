package com.restbucks.domain;

public interface PaymentProcessor {

	void process(Payment payment, Order order);

}
