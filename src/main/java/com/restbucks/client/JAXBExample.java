package com.restbucks.client;

import javax.xml.bind.*;

import com.restbucks.domain.*;

public class JAXBExample {

	public static void main(String[] args) {

		Order order = new Order();
		order.setCost("100");
		order.setStatus("payment-expected");
		order.setLink(new Link("localhost:8080/restbucks/web/api/payment/2", "application/vnd.restbucks+xm", "http://relations.restbucks.com/payment"));

		try {

			//File file = new File("C:\\file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(order, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
