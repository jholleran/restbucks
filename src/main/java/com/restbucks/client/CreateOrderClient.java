package com.restbucks.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class CreateOrderClient {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(
				"http://localhost:8080/restbucks/web/api/order");
		post.setHeader("Content-Type", "application/vnd.restbucks+xml");

		String xml = "<order xmlns=\"http://schemas.restbucks.com\">"
				+ "<items>"
				+ "<item>"
				+ "<milk>whole</milk>"
				+ "<name>latte</name>"
				+ "<quantity>1</quantity>"
				+ "<size>small</size>"
				+ "</item>"
				+ "<item>"
				+ "<kind>chocolate-chip</kind>"
				+ "<name>cookie</name>"
				+ "<quantity>2</quantity>"
				+ "</item>"
				+ "</items>"
				+ "<location>takeaway</location>"
				+ "<status>pending</status>"
				+ "</order>";

		HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
		post.setEntity(entity);
		HttpResponse response = client.execute(post);

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

		post.releaseConnection();
	}

}
