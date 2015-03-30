package com.restbucks.client;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.*;

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

		System.out.println("Creating an Order.");
		String response = createOrder(endpoint, xml);

		Document document = createDocument(response);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();

		printDocument(document);

		String paymentUri = xpath.evaluate(
				"//*[@rel=\"http://relations.restbucks.com/payment\"]/@uri",
				document);
		// TODO read correct payment endpoint

		String payment = "<ns3:payment xmlns=\"http://schemas.restbucks.com\" xmlns:ns3=\"http://schemas.restbucks.com/payment\">"
				+ "<amount>5.00</amount>"
				+ "<cardHolderName>Michael Faraday</cardHolderName>"
				+ "<cardNumber>11223344</cardNumber>"
				+ "<expiryMonth>12</expiryMonth>"
				+ "<expiryYear>12</expiryYear>" + "</ns3:payment>";

		System.out.println("Paying for Order.");
		String paymentResponse = payForOrder(paymentUri, payment);

		Document prd = createDocument(paymentResponse);
		printDocument(prd);


		String receipt = xpath.evaluate(
				"//*[@rel=\"http://relations.restbucks.com/receipt\"]/@uri", prd);
		
		System.out.println("Getting the Receipt.");
		String receiptResponse = callGet(receipt);
		
		Document rrd = createDocument(receiptResponse);
		printDocument(rrd);
		
		
		String orderUri = xpath.evaluate(
				"//*[@rel=\"http://relations.restbucks.com/order\"]/@uri", rrd);

		System.out.println("Reading the Order.");
		String orderResponse = callGet(orderUri);
		Document ord = createDocument(orderResponse);
		printDocument(ord);

		Document ready = waitForOrderReady(xpath, ord);
		
		String receiptUri = xpath.evaluate(
				"//*[@rel=\"http://relations.restbucks.com/receipt\"]/@uri", ready);
		
		String takeResponse = takeOrder(receiptUri);

		Document trd = createDocument(takeResponse);
		printDocument(trd);
	}

	private static Document waitForOrderReady(XPath xpath, Document ord)
			throws InterruptedException, XPathExpressionException, Exception,
			ParserConfigurationException, SAXException, IOException,
			TransformerConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		Thread.sleep(2000);

		String orderUri1 = xpath.evaluate("//*[@rel=\"self\"]/@uri", ord);
		//System.out.println(orderUri1);
		if (orderUri1 != null && !"".equals(orderUri1.trim())) {
			String orderResponse1 = callGet(orderUri1);
			Document ord1 = createDocument(orderResponse1);
			printDocument(ord1);
			return waitForOrderReady(xpath, ord1);
		}
		return ord;
	}

	private static Document createDocument(String response)
			throws ParserConfigurationException, SAXException, IOException {
		InputSource source = new InputSource(new StringReader(response));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(source);
		return document;
	}

	private static void printDocument(Document document)
			throws TransformerConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(document);
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		System.out.println(xmlString);
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
		String responseBody = EntityUtils.toString(response.getEntity());
		// System.out.println("response body: " + responseBody);

		if (responseCode == 201) {
			String orderUri = response.getHeaders("Location")[0].getValue();
			System.out.println("Location: " + orderUri);
		}

		printIfError(responseCode);

		post.releaseConnection();
		return responseBody;
	}

	private static String callGet(String endpoint) throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(endpoint);
		get.setHeader("Content-Type", MEDIA_TYPE);

		HttpResponse response = client.execute(get);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("response code: " + responseCode);
		String responseBody = EntityUtils.toString(response.getEntity());
		// System.out.println("response body: " + responseBody);

		printIfError(responseCode);

		get.releaseConnection();
		return responseBody;
	}

	private static String payForOrder(String orderUri, String paymentXml)
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
		// System.out.println("response body: " + result);

		printIfError(responseCode);

		put.releaseConnection();
		return result;
	}
	
	
	private static String takeOrder(String endpoint) throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete delete = new HttpDelete(endpoint);
		delete.setHeader("Content-Type", MEDIA_TYPE);

		HttpResponse response = client.execute(delete);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("response code: " + responseCode);
		String responseBody = EntityUtils.toString(response.getEntity());
		// System.out.println("response body: " + responseBody);

		printIfError(responseCode);

		delete.releaseConnection();
		return responseBody;
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
