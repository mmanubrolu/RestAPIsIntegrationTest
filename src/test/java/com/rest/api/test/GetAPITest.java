package com.rest.api.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rest.api.client.RestClient;
import com.rest.api.testbase.TestBase;
import com.rest.api.util.TestUtil;

public class GetAPITest extends TestBase {
	String webURL;
	String serviceURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closableHttpResponse;
	
	public GetAPITest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		webURL = prop.getProperty("webURL");
		serviceURL =prop.getProperty("serviceURL");
		url = webURL +  serviceURL;
		restClient = new RestClient();
	}
	
	@Test(priority=1)
	public void validateGETAPITest() throws ClientProtocolException, IOException {
		closableHttpResponse =  restClient.getWithoutHeader(url);
		
		// a. get the HTTp Code
		int httpStatusCode = closableHttpResponse.getStatusLine().getStatusCode();
		System.out.println(" httpStatusCode  :::: " + httpStatusCode);
		Assert.assertEquals(httpStatusCode, 200, "Http code was not 200 from service");
		
		//b. get the response payload
		String responsePayload = EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
		System.out.println( " responsePayload in string format :::: " + responsePayload);
		
		JSONObject responseJson = new JSONObject(responsePayload);
		System.out.println(" response payload in jsonObj ::::  " +responseJson);
		
		
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("value of per page is-->"+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		//total:
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("value of total is-->"+ totalValue);		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		//get the value from JSON ARRAY:
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		
		
		//c. get the headers
		Header[] headerArray = closableHttpResponse.getAllHeaders();
		HashMap<String, String> headersMap = new HashMap<String, String>();
		for(Header header : headerArray) {
			headersMap.put(header.getName(), header.getValue());
		}
		System.out.println("Headers information :: " + headersMap);
		Iterator<HashMap.Entry<String, String>> iterator = headersMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String,String> item = iterator.next();
			System.out.println("Key :::" + item.getKey() +"   Value : " + item.getValue());
		}
		
		String server = headersMap.get("Server");
		Assert.assertEquals(server, "cloudflare", "server is not matched");
		
	}
	
	
	@Test(priority=2)
	public void validateHetWithHeaderTest() throws ClientProtocolException, IOException {
		
		HashMap<String, String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		CloseableHttpResponse closeableHttpResponse = restClient.getWithHeaders(url, headerMap) ;
		
		
		//a. status code
		int httpStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Http status code ::::: " +httpStatusCode);
		
		Assert.assertEquals(httpStatusCode, 200, "HTTP return code is not 200");
		
		//b payload
		
		String reponsePayload = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println("reponsePayload in string :::: " + reponsePayload);
		
		JSONObject jsonObj = new JSONObject(reponsePayload);
		System.out.println("reponsePayload in json :::: " + jsonObj);
		
		
		// c get headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> headerMap1 = new HashMap<String, String>();
		
		for(Header header : headerArray) {
			headerMap1.put(header.getName(), header.getValue());
		}
		
		System.out.println("Header information :::: " + headerMap1);
	
	}

}
