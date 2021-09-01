package com.rest.api.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	public static Properties prop;
	
	public TestBase()  {
		
		try {
			prop = new Properties();
			FileInputStream inStream;
			inStream = new FileInputStream("C:\\Users\\malli\\workspace\\restapis\\RestAPIsTest\\src\\test\\resources\\com\\rest\\api\\config\\config.properties");
			prop.load(inStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
