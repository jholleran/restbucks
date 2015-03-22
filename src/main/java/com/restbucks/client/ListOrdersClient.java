package com.restbucks.client;

import java.io.StringWriter;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class ListOrdersClient {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(
				"http://localhost:8080/restbucks/web/api/order");


		HttpResponse response = client.execute(get);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("response code: " + responseCode);

		if (responseCode == 200) {
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("response body: " + result);
			
		} else if (responseCode == 404) {
			// throw new BadRequestException();
			System.out
					.println("No resources found");
		} else if (responseCode == 500 || responseCode == 503) {
			// throw new
			// ServerFailureException(post.getResponseHeader("Retry-After"));
			System.out
					.println("If we get a 5xx response, the caller may retry");
		}

		get.releaseConnection();
	}

}
