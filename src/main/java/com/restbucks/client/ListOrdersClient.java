package com.restbucks.client;

import java.io.*;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.parsers.*;
import javax.xml.transform.*;
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
import org.w3c.dom.Document;
import org.xml.sax.*;


public class ListOrdersClient {

	private static final String MEDIA_TYPE = "application/vnd.restbucks+xml";
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// HttpClient
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(
				"http://localhost:8080/restbucks/web/api/order");
		get.setHeader("Content-Type", MEDIA_TYPE);


		HttpResponse response = client.execute(get);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("response code: " + responseCode);
		
		if (responseCode == 200) {
			String result = EntityUtils.toString(response.getEntity());
			//System.out.println("response body: " + result);
			printDocument(createDocument(result));
			
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
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(document);
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		System.out.println(xmlString);
	}

}
