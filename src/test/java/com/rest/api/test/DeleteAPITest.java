package com.rest.api.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rest.api.client.RestClient;
import com.rest.api.testbase.TestBase;
import com.rest.api.util.Constantants;

public class DeleteAPITest extends TestBase {
	
	String webURL;
	String serviceURL;
	String customerId;
	String url;
	RestClient restClient;
	
	public DeleteAPITest( ) {
		super();
	}
	
	@BeforeMethod
	public void setUp( ) {
		webURL = prop.getProperty("webURL");
		serviceURL = prop.getProperty("serviceURL");
		customerId = prop.getProperty("customerID");
		url = webURL + serviceURL + customerId;
		
		restClient = new RestClient();
	}
	
	
	@Test
	public void deleteAPITest() throws IOException {
		
		//create headers
		HashMap<String, String> requestHeaderMap = new HashMap<String, String>();
		requestHeaderMap.put("Content-Type", "application/json");
		
		//invoke restClient method to invoke actual delete operation
		CloseableHttpResponse closeableHttpResponse =  restClient.deleteWithHeader(url, requestHeaderMap);
		
		//1. Get http status code
		int httpStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("httpStatusCode  ---->>>   " + httpStatusCode);
		Assert.assertEquals(httpStatusCode, Constantants.HTTP_STATUS_CODE_204, "Revise response code is not 204");
		
		
		//2. response body
		//no response body for delete call
		
		//3 get reponse headers
		Header[] reponseHeaderList = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> reponseHeaderMap = new HashMap<String, String>();
		
		for(Header header : reponseHeaderList) {
			reponseHeaderMap.put(header.getName(), header.getValue());
		}
		
		Iterator<Entry<String, String>> iterator = reponseHeaderMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, String> item = iterator.next();
			System.out.println(item.getKey()  + "----->" + item.getValue());
		}
		
	}

}
