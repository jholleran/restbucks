package com.restbucks.domain;

import org.springframework.stereotype.Service;

@Service
public class FakePaymentProcessor implements PaymentProcessor {

	@Override
	public boolean process(Payment payment, Order order) {

		if (payment != null && payment.getAmount() != null
				&& payment.getCardHolderName() != null
				&& payment.getCardNumber() != null && payment.getExpiryYear() != null
				&& payment.getExpiryMonth() != null) {
			order.setStatus("preparing");
			return true;
		}
		return false;
	}

}
