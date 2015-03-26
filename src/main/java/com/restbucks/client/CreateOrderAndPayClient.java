package com.restbucks.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class CreateOrderAndPayClient {

	private static final String MEDIA_TYPE = "application/vnd.restbucks+xml";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String xml = "<order xmlns=\"http://schemas.restbucks.com\">"
				+ "<items>" + "<item>" + "<milk>whole</milk>"
				+ "<name>latte</name>" + "<quantity>1</quantity>"
				+ "<size>small</size>" + "</item>" + "<item>"
				+ "<kind>chocolate-chip</kind>" + "<name>cookie</name>"
				+ "<quantity>2</quantity>" + "</item>" + "</items>"
				+ "<location>takeaway</location>" + "<status>pending</status>"
				+ "</order>";

		String endpoint = "http://localhost:8080/restbucks/web/api/order";

		String orderUri = createOrder(endpoint, xml);
		//TODO read correct payment endpoint

		String payment = "<ns3:payment xmlns=\"http://schemas.restbucks.com\" xmlns:ns3=\"http://schemas.restbucks.com/payment\">"
				+ "<amount>5.00</amount>"
				+ "<cardHolderName>Michael Faraday</cardHolderName>"
				+ "<cardNumber>11223344</cardNumber>"
				+ "<expiryMonth>12</expiryMonth>"
				+ "<expiryYear>12</expiryYear>"
				+ "</ns3:payment>";

		payForOrder(orderUri, payment);

	}

	private static String createOrder(String endpoint, String orderXml)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(endpoint);
		post.setHeader("Content-Type", MEDIA_TYPE);

		HttpEntity entity = new ByteArrayEntity(orderXml.getBytes("UTF-8"));
		post.setEntity(entity);
		HttpResponse response = client.execute(post);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("response code: " + responseCode);
		String result = EntityUtils.toString(response.getEntity());
		System.out.println("response body: " + result);

		String orderUri = "";
		if (responseCode == 201) {
			orderUri = response.getHeaders("Location")[0].getValue();
			System.out.println("Location: " + orderUri);
		} 
		
		printIfError(responseCode);

		post.releaseConnection();
		return orderUri;
	}


	private static void payForOrder(String orderUri, String paymentXml)
			throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(orderUri);
		put.setHeader("Content-Type", MEDIA_TYPE);

		HttpEntity entity = new ByteArrayEntity(paymentXml.getBytes("UTF-8"));
		put.setEntity(entity);
		HttpResponse response = client.execute(put);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("response code: " + responseCode);
		String result = EntityUtils.toString(response.getEntity());
		System.out.println("response body: " + result);

		printIfError(responseCode);

		put.releaseConnection();
	}

	private static void printIfError(int responseCode) {
		if (responseCode == 400) {
			// throw new BadRequestException();
			System.out
			.println("If we get a 400 response, the caller's gone wrong");
		} else if (responseCode == 500 || responseCode == 503) {
			// throw new
			// ServerFailureException(post.getResponseHeader("Retry-After"));
			System.out
			.println("If we get a 5xx response, the caller may retry");
		}
	}
}
