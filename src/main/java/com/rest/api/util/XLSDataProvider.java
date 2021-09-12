package com.rest.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.rest.api.model.GetRequest;

public class XLSDataProvider {

	public static String sheetName = "request";
	public static ArrayList<Object[]> getData(String filePath) {
		XLSUtil xlsUtil = new XLSUtil(filePath);
		
		int rowCount = xlsUtil.getRowCount(sheetName);
		int colCount = xlsUtil.getColumnCount(sheetName);
		Object[][] obj = new Object[rowCount-1][colCount];
		System.out.println("rowCount" + rowCount);
		System.out.println("colCount" + colCount);
		ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
		
		int k=0;
		for(int i=2; i<=rowCount; i++) {
			GetRequest request= new GetRequest();
			Object[] obj1 = new Object[1];
			for(int j=0; j<colCount; j++) {
				
				obj[k][j] = xlsUtil.getCellData(sheetName, j, i);
				System.out.println(xlsUtil.getCellData(sheetName, j, i));
				if(j==0) {
					request.setTcID(xlsUtil.getCellData(sheetName, j, i));
				} else if(j==1) {
					request.setPriority(xlsUtil.getCellData(sheetName, j, i));
				} else if(j==2) {
					request.setDescription(xlsUtil.getCellData(sheetName, j, i));
				} else if (j==3) {
					request.setWebURL(xlsUtil.getCellData(sheetName, j, i));
				} else if(j==4) {
					request.setServiceURL(xlsUtil.getCellData(sheetName, j, i));
				} else if(j==5) {
					request.setCustomerId(xlsUtil.getCellData(sheetName, j, i));
				}
			}
			Object temp = (Object) request;
			obj1[k] = temp;
			arrayList.add(obj1);
			
			
		}
		
		System.out.println("array list size :: " + arrayList.size());
		//return obj;
		return arrayList;
		//return obj1;
		
	}
}
