package com.restbucks.domain;

import org.springframework.stereotype.Service;

@Service
public class FakePaymentProcessor implements PaymentProcessor {

	@Override
	public void process(Payment payment, Order order) {

		order.setStatus("preparing");
	}

}
