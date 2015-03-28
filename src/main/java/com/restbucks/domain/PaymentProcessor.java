package com.restbucks.domain;

public interface PaymentProcessor {

	boolean process(Payment payment, Order order);

}
