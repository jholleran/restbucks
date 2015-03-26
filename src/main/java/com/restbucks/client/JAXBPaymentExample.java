package com.restbucks.client;

import java.util.*;

import javax.xml.bind.*;

import com.restbucks.domain.*;

public class JAXBPaymentExample {

	public static void main(String[] args) {

		Payment payment = new Payment();
		payment.setAmount("100");
		payment.setCardHolderName("John-Joe");
		payment.setCardNumber("123456");
		payment.setExpiryMonth("Jan");
		payment.setExpiryYear("2017");
		List<Link> links = new ArrayList<Link>();
		links.add(new Link("localhost:8080/restbucks/web/api/receipt/2", "application/vnd.restbucks+xm", "self"));
		
		payment.setLinks(links);

		try {

			//File file = new File("C:\\file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Payment.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(payment, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
