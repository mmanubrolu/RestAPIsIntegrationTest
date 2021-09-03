package com.rest.api.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.client.RestClient;
import com.rest.api.model.PostRequest;
import com.rest.api.model.PostResponse;
import com.rest.api.testbase.TestBase;
import com.rest.api.util.Constantants;



public class PostAPITest extends TestBase{
	String serviceURL;
	String webURL;
	String url;
	RestClient restClient;
	
	public PostAPITest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		
		webURL = prop.getProperty("webURL");
		serviceURL = prop.getProperty("serviceURL");
		url = webURL + serviceURL;
		restClient = new RestClient();
		
	}
	
	@Test
	public void createUser() throws ClientProtocolException, IOException {
		
		// create payload
		PostRequest request = new PostRequest("Malli", "Staff engineer");
		
		ObjectMapper objMapper = new ObjectMapper();
		String requestPayLoad = objMapper.writeValueAsString(request);
		System.out.println("requestPayLoad  :::: " + requestPayLoad);
		
		HashMap<String , String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		System.out.println("url   ::::: " + url);
		System.out.println("requestPayLoad  :::: " + requestPayLoad);
		System.out.println("header  :::: " + headerMap);
		// service calll
		CloseableHttpResponse  closeableHttpResponse =  restClient.postWithHeader(url, requestPayLoad, headerMap);
		
		//1. get response HTTP code
		int  htttpStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		
		Assert.assertEquals(htttpStatusCode, Constantants.HTTP_STATUS_CODE_201);
		
		
		//2. get response payload
		String reposneInString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println("reposneInString   :::: " + reposneInString);
		
		JSONObject responseInJSON = new JSONObject(reposneInString);
		System.out.println("responseInJSON   :::: " + responseInJSON);
		
		ObjectMapper objMapperRes = new ObjectMapper();
		PostResponse response = objMapperRes.readValue(reposneInString, PostResponse.class);
		
		System.out.println(" response.getName()   ::: "  + response.getName());
		System.out.println(" response.getJob()   ::: "  + response.getJob());
		System.out.println(" response.getId() :::: "  + response.getId());
		System.out.println(" response.getCreatedAt()  ::: "  + response.getCreatedAt());
		
		Assert.assertEquals(response.getName(), request.getName());
		Assert.assertEquals(response.getJob(), request.getJob());
		
		
		//3. get headers
		Header[] headerList = closeableHttpResponse.getAllHeaders();
		HashMap<String , String> responseHeaderMap = new HashMap<String, String>();
		for(Header header : headerList) {
			responseHeaderMap.put(header.getName(), header.getValue());
		}
		
		Iterator<Entry<String, String>> iterator = responseHeaderMap.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<String, String> item = iterator.next();
			System.out.println(item.getKey() + "---->" + item.getValue());
		}
		
		//Assert.assertEquals(responseHeaderMap.get("CF-RAY"), "688b66fbdb1f1d91-BLR", "CF-RAY header valie us not match");
		
	}

}
