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
import com.rest.api.model.PutRequest;
import com.rest.api.model.PutResponse;
import com.rest.api.testbase.TestBase;
import com.rest.api.util.Constantants;

public class PutAPITest extends TestBase{
	String webURL;
	String serviceURL;
	String customerID;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	public PutAPITest( ) {
		super();
	}

	
	@BeforeMethod
	public void setUp() {
		webURL = prop.getProperty("webURL");
		serviceURL = prop.getProperty("serviceURL");
		customerID= prop.getProperty("customerID");
		url= webURL + serviceURL +customerID;
		restClient = new RestClient();
	}
	
	@Test
	public void updateUser() throws ClientProtocolException, IOException {

		// create put request class object with constructor
		PutRequest putRequestObj = new PutRequest("Mallikaruna", "Sr Software Enginner");
		ObjectMapper objectMapperRequest = new ObjectMapper();
		// conver the put request class object instance as string
		String putPayLoadInString = objectMapperRequest.writeValueAsString(putRequestObj);
		
		//create header for put request
		HashMap<String, String> requestHeaderMap = new HashMap<String,String>();
		requestHeaderMap.put("Content-Type", "application/json");
		
		System.out.println("url   ::::: " + url);
		System.out.println("requestPayLoad  :::: " + putPayLoadInString);
		System.out.println("header  :::: " + requestHeaderMap);
		closeableHttpResponse = restClient.putWithHeader(url, putPayLoadInString, requestHeaderMap);
		
		
		//1 get service resposne code 
		int httpStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("httpStatusCode  ---> " +httpStatusCode);
		Assert.assertEquals(httpStatusCode, Constantants.HTTP_STATUS_CODE_200, "HTT reponse code is not 200");
		
		
		//2 get the response payload
		String responsePayloadInString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println("responsePayloadInString   ----> " + responsePayloadInString);
		
		JSONObject reposnePayloadInJson = new JSONObject(responsePayloadInString);
		System.out.println("reposnePayloadInJson   ----> " + reposnePayloadInJson);
		
		ObjectMapper reponseObjectMapper = new ObjectMapper();
		PutResponse putResponseObj = reponseObjectMapper.readValue(responsePayloadInString, PutResponse.class);
		System.out.println(" response.getName()   ::: "  + putResponseObj.getName());
		System.out.println(" response.getJob()   ::: "  + putResponseObj.getJob());
		//System.out.println(" response.getId() :::: "  + putResponseObj.getId());
		System.out.println(" response.getCreatedAt()  ::: "  + putResponseObj.getUpdatedAt());
		
		
		Assert.assertEquals(putRequestObj.getName(), putResponseObj.getName(), "Name was not matched");;
		Assert.assertEquals(putRequestObj.getJob(), putResponseObj.getJob(), "Job was not matched");
		
		
		//3 get headers
		Header[] hedersArray= closeableHttpResponse.getAllHeaders();
		HashMap<String,String> reponseHeaderMap = new HashMap<String,String>();
		
		for(Header header:hedersArray ) {
			reponseHeaderMap.put(header.getName(), header.getValue());
		}
		
		Iterator<Entry<String, String>> iterator = reponseHeaderMap.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<String,String> item = iterator.next();
			System.out.println(item.getKey() + " ----->>>> " + item.getValue());
		}
		
	}
}
