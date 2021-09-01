package com.rest.api.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
	
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient closableHttpClient = HttpClients.createDefault();  // create http conection
		
		HttpGet httpGet = new HttpGet(url); // create the request
		
		CloseableHttpResponse closableHttpResponse = closableHttpClient.execute(httpGet);  // get the entire response
		
		//Get the HTTP status code
		int httpStatusCode = closableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("httpStatusCode  :::: "+httpStatusCode);
		
		return closableHttpResponse;
		
	}
	
	public CloseableHttpResponse getWithHeaders(String url, HashMap<String, String> headerMap ) throws ClientProtocolException, IOException {
		CloseableHttpClient closableHttpClient= HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		
		for(Map.Entry<String, String> map : headerMap.entrySet()) {
			httpGet.addHeader(map.getKey(), map.getValue());
			
		}
		CloseableHttpResponse closeableHttpResponse = closableHttpClient.execute(httpGet);
		
		return closeableHttpResponse;
		
	}
}
