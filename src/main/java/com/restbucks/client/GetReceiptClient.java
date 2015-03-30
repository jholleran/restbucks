package com.restbucks.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class GetReceiptClient {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(
				"http://localhost:8080/restbucks/web/api/receipt/2");
		get.setHeader("Content-Type", "application/vnd.restbucks+xml");

		HttpResponse response = client.execute(get);

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

		get.releaseConnection();
	}

}
