package com.restbucks.web;

import static com.restbucks.web.OrderController.MEDIA_TYPE;

import java.net.URI;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.restbucks.domain.*;

@Controller
@RequestMapping(value = "api", consumes = { MEDIA_TYPE }, produces = { MEDIA_TYPE })
public class OrderController {

	public static final String MEDIA_TYPE = "application/vnd.restbucks+xml";

	private OrderRepository repository;
	private PaymentProcessor paymentProcessor;
	private Barista barista;

	@Autowired
	public OrderController(OrderRepository repository,
			PaymentProcessor paymentProcessor, Barista barista) {
		this.repository = repository;
		this.paymentProcessor = paymentProcessor;
		this.barista = barista;
	}

	@RequestMapping("order/{id}")
	@ResponseBody
	public Order getById(@PathVariable Long id) {
		Order order = repository.getById(id);
		if (order == null) {
			throw new OrderNotFoundException(id);
		}

		if (order.getStatus().equals("ready")) {
			List<Link> links = new ArrayList<Link>();
			String receiptUri = buildUri(id, "/api/receipt/{id}").toString();
			links.add(new Link(receiptUri, MEDIA_TYPE,
					"http://relations.restbucks.com/receipt"));
			order.setLinks(links);
		}

		// TODO handle Internal Server error
		return order;
	}

	@RequestMapping("order")
	@ResponseBody
	public Orders getAll() {
		// TODO handle Internal Server error

		return new Orders(repository.getAll());
	}

	@RequestMapping(value = "order", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Order> save(@RequestBody Order order) {
		// TODO handle Bad Request
		// TODO handle Internal Server error

		// TODO yeah this doesn't belong here
		order.setStatus("payment-expected");
		order.setCost("5.00");

		Long id = repository.nextId();

		String orderUri = buildUri(id, "/api/order/{id}").toString();
		String paymentUri = buildUri(id, "/api/payment/{id}").toString();

		List<Link> links = new ArrayList<Link>();
		links.add(new Link(orderUri, MEDIA_TYPE,
				"http://relations.restbucks.com/cancel"));
		links.add(new Link(orderUri, MEDIA_TYPE,
				"http://relations.restbucks.com/update"));
		links.add(new Link(paymentUri, MEDIA_TYPE,
				"http://relations.restbucks.com/payment"));
		order.setLinks(links);

		repository.save(id, order);

		final URI location = buildUri(id, "/api/order/{id}").toUri();

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);

		return new ResponseEntity<Order>(order, headers, HttpStatus.CREATED);
	}

	private UriComponents buildUri(Long id, String uriTemplate) {
		return ServletUriComponentsBuilder.fromCurrentServletMapping()
				.path(uriTemplate).build().expand(id);
	}

	@RequestMapping(value = "order/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Order> update(@PathVariable Long id,
			@RequestBody Order order) {
		Order savedOrder = repository.getById(id);
		if (savedOrder == null) {
			throw new OrderNotFoundException(id);
		}
		// need to handle a conflict if it occurs
		boolean conflict = repository.save(id, order);
		if (conflict) {
			Order latest = repository.getById(id);
			return new ResponseEntity<Order>(latest, HttpStatus.CONFLICT);
		}

		// TODO handle Internal Server error

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@RequestMapping(value = "order/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Order order = repository.getById(id);
		if (order == null) {
			throw new OrderNotFoundException(id);
		}

		boolean canDelete = repository.delete(id);
		if (!canDelete) {
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}

		// TODO handle Internal Server error
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "payment/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Payment> payment(@PathVariable Long id,
			@RequestBody Payment payment) {
		Order order = repository.getById(id);
		if (order == null) {
			throw new OrderNotFoundException(id);
		}

		boolean paid = paymentProcessor.process(payment, order);
		if (paid) {

			String orderUri = buildUri(id, "/api/order/{id}").toString();

			List<Link> orderLinks = new ArrayList<Link>();
			orderLinks.add(new Link(orderUri, MEDIA_TYPE, "self"));
			order.setLinks(orderLinks);

			repository.save(id, order);
			barista.process(order);

			String receiptUri = buildUri(id, "/api/receipt/{id}").toString();
			List<Link> links = new ArrayList<Link>();
			links.add(new Link(orderUri, MEDIA_TYPE,
					"http://relations.restbucks.com/order"));
			links.add(new Link(receiptUri, MEDIA_TYPE,
					"http://relations.restbucks.com/receipt"));
			payment.setLinks(links);
			return new ResponseEntity<Payment>(payment, HttpStatus.OK);
		}

		return new ResponseEntity<Payment>(payment, HttpStatus.PAYMENT_REQUIRED);
		// TODO handle Internal Server error
	}
	
	@RequestMapping(value = "receipt/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Order> deleteReceipt(@PathVariable Long id) {
		Order order = repository.getById(id);
		if (order == null) {
			throw new OrderNotFoundException(id);
		}

		order.setStatus("taken");
		order.setLinks(new ArrayList<Link>());
		
		repository.save(id, order);

		// TODO handle Internal Server error

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
