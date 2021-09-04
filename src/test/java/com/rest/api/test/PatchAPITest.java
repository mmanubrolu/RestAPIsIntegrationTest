package com.rest.api.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.client.RestClient;
import com.rest.api.model.PatchRequest;
import com.rest.api.model.PatchResponse;
import com.rest.api.testbase.TestBase;
import com.rest.api.util.Constantants;

public class PatchAPITest extends TestBase {
	String webURL;
	String serviceURL;
	String customerID;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	PatchAPITest(){
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		webURL = prop.getProperty("webURL");
		serviceURL = prop.getProperty("serviceURL");
		customerID = prop.getProperty("customerId");
		
		url = webURL + serviceURL + customerID ;
		restClient = new RestClient();
	}
	
	@Test
	public void validatePatchAPITest() throws ClientProtocolException, IOException {
		
		// create patch request object with name and job
		PatchRequest patchEntityRequest = new PatchRequest("Mallikarjua", "Sr Staff engineer");
		ObjectMapper requestObjMapper = new ObjectMapper();
		String entityPayloadInString = requestObjMapper.writeValueAsString(patchEntityRequest);
		
		//create header for put request
		HashMap<String, String> requestHeaderMap = new HashMap<String,String>();
		requestHeaderMap.put("Content-Type", "application/json");
		
		System.out.println("url   ::::: " + url);
		System.out.println("requestPayLoad  :::: " + entityPayloadInString);
		System.out.println("header  :::: " + requestHeaderMap);
		
		//invoke patch api
		 closeableHttpResponse =  restClient.patchWithHeader(url, entityPayloadInString, requestHeaderMap);
		
		 
		 // 1. get https code
		 int httpStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		 System.out.println("httpStatusCode   --- > " + httpStatusCode);
		 Assert.assertEquals(httpStatusCode, Constantants.HTTP_STATUS_CODE_200, "Patch service response is notTHHP 200");
		 
		 // 2. get the response payload
		 String responseInString = EntityUtils.toString(closeableHttpResponse.getEntity());
		 System.out.println("responseInString -----> " + responseInString);
		 ObjectMapper reposneObjMapper = new ObjectMapper();
		 PatchResponse patchEntityResponse =  reposneObjMapper.readValue(responseInString, PatchResponse.class);
		 System.out.println(" response.getName()   ::: "  + patchEntityResponse.getName());
		 System.out.println(" response.getJob()   ::: "  + patchEntityResponse.getJob());
		 System.out.println(" response.getUpdatedAt()  ::: "  + patchEntityResponse.getUpdatedAt());
		 
		 Assert.assertEquals( patchEntityResponse.getName(), patchEntityRequest.getName(), "Name was not matched");
		 Assert.assertEquals(patchEntityResponse.getJob(), patchEntityRequest.getJob(), "Job was not matched");
		
		 
		 //3 get all headers
		 Header[] responseHeadersList = closeableHttpResponse.getAllHeaders();
		 HashMap<String, String> reponseHeaderMap = new HashMap<String, String>();
		 
		 for(Header header : responseHeadersList) {
			 reponseHeaderMap.put(header.getName(), header.getValue());
		 }
		 
		 Iterator<Entry<String, String>> iterator = reponseHeaderMap.entrySet().iterator();
		 while(iterator.hasNext()) {
			 Entry<String, String> item = iterator.next();
			 System.out.println(item.getKey() + " ------->>>>" + item.getValue());
		 }
		 
	}

}
