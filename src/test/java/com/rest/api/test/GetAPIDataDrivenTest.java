package com.rest.api.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.client.RestClient;
import com.rest.api.model.GetRequest;
import com.rest.api.model.GetResponse;
import com.rest.api.testbase.TestBase;
import com.rest.api.util.XLSDataProvider;

public class GetAPIDataDrivenTest extends TestBase{
	RestClient restClient;
	String url;
	
	GetAPIDataDrivenTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		restClient = new RestClient();
	}
	
	@DataProvider
	public Iterator<Object[]> getTestData() {
		ArrayList<Object[]> obj = XLSDataProvider.getData("C:\\Users\\malli\\workspace\\restapis\\RestAPIsTest\\src\\test\\resources\\com\\rest\\api\\testdata\\restapistestdata.xlsx");
		//Iterator<GetRequest> iterator = obj.iterator();
		
		return obj.iterator();
	}
	@Test(dataProvider="getTestData")
	public void getAPIDataDriverTest(GetRequest request) throws ClientProtocolException, IOException {
		url= request.getWebURL() + request.getServiceURL()+request.getCustomerId();
		//url= webURL + serviceURL + customerId;
		if(!url.isEmpty()) {
			CloseableHttpResponse  closeableHttpResponse = restClient.getWithoutHeader(url);
			
			//get http status code
			int httpcode = closeableHttpResponse.getStatusLine().getStatusCode();
			System.out.println("httpcode ::: " + httpcode);
			
			//get reponse paylaod
			String responsePayloadInString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			System.out.println("responsePayloadInString" + responsePayloadInString);
			
			JSONObject jsonObjec = new JSONObject(responsePayloadInString);
			System.out.println("jsonObjec" + jsonObjec);
			ObjectMapper objMapper = new ObjectMapper();
			GetResponse getReponseObj= objMapper.readValue(responsePayloadInString, GetResponse.class);
			System.out.println("getReponseObj" + getReponseObj.toString());
			
			// get headers
			Header[] headersArray = closeableHttpResponse.getAllHeaders();
			HashMap<String,String> headersMap = new HashMap<String,String>();
			
			for(Header header : headersArray ) {
				headersMap.put(header.getName(), header.getValue());
			}
			
			
			Iterator<Entry<String,String>> iterator = headersMap.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<String,String> item = iterator.next();
				System.out.println("Key --->> " + item.getKey() +"            Value ------>>>> " + item.getValue());
			}
		
		}
		else
			System.out.println("url is empty");
		
		//String tcID, String priority, String description, String webURL, String serviceURL, String customerId
	}
} 
