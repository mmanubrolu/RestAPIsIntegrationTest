package com.rest.api.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RestClient {
	
	public CloseableHttpResponse getWithoutHeader(String url) throws ClientProtocolException, IOException {
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
	
	public CloseableHttpResponse postWithHeader(String url, String requestPayLoad, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
		
		System.out.println("url   ::::: " + url);
		System.out.println("requestPayLoad  :::: " + requestPayLoad);
		System.out.println("header  :::: " + headerMap);
		
		CloseableHttpClient closableHttpClient =  HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity( new StringEntity(requestPayLoad));
		
		Iterator<Entry<String, String>> iterator = headerMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, String> item = iterator.next();
			System.out.println("item.getKey() ::: " + item.getKey());
			System.out.println("item.getValue()  ::::" + item.getValue());
			httpPost.addHeader(item.getKey(), item.getValue());
		}
		
		CloseableHttpResponse  closeableHttpResponse = closableHttpClient.execute(httpPost);
		return closeableHttpResponse;
		
	}
	
	public CloseableHttpResponse putWithHeader(String url , String payLoad, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		
		System.out.println("url   ::::: " + url);
		System.out.println("payLoad  :::: " + payLoad);
		System.out.println("header  :::: " + headerMap);
		
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		
		HttpPut httpPut  = new HttpPut(url);
		HttpEntity httpEntity = new StringEntity(payLoad);
		httpPut.setEntity(httpEntity);
		
		Iterator<Entry<String, String >> iterator = headerMap.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<String, String> item = iterator.next();
			System.out.println("item.getKey() ::: " + item.getKey());
			System.out.println("item.getValue()  ::::" + item.getValue());
			httpPut.addHeader(item.getKey(), item.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPut);
		
		return closeableHttpResponse;
	}
}
