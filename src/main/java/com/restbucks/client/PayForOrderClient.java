package com.restbucks.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class PayForOrderClient {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(
				"http://localhost:8080/restbucks/web/api/payment/4");
		put.setHeader("Content-Type", "application/xml");

		String xml = "<ns2:payment xmlns:ns2=\"http://schemas.restbucks.com/payment\">"
				+ "<amount>5.00</amount>"
				+ "<cardholderName>Michael Faraday</cardholderName>"
				+ "<cardNumber>11223344</cardNumber>"
				+ "<expiryMonth>12</expiryMonth>"
				+ "<expiryYear>12</expiryYear>"
				+ "</ns2:payment>";

		HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
		put.setEntity(entity);
		HttpResponse response = client.execute(put);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("response code: " + responseCode);
		String result = EntityUtils.toString(response.getEntity());
		System.out.println("response body: " + result);

		if (responseCode == 201) {
			System.out.println("Location: " + response.getHeaders("Location")[0].getValue());
		} else if (responseCode == 400) {
			// throw new BadRequestException();
			System.out
					.println("If we get a 400 response, the caller's gone wrong");
		} else if (responseCode == 500 || responseCode == 503) {
			// throw new
			// ServerFailureException(post.getResponseHeader("Retry-After"));
			System.out
					.println("If we get a 5xx response, the caller may retry");
		}

		put.releaseConnection();
	}

}
