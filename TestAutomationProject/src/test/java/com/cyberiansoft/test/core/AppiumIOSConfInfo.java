package com.cyberiansoft.test.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class AppiumIOSConfInfo {
	
private final Properties configProp = new Properties();
	private AppiumIOSConfInfo() {
		File file =
				new File("src/test/java/com/cyberiansoft/test/core/appiumiosconf.properties");
		try {
			FileInputStream fileInput = new FileInputStream(file);
			configProp.load(fileInput);
			fileInput.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
			 
	private static class LazyHolder {
	      private static final AppiumIOSConfInfo INSTANCE = new AppiumIOSConfInfo();
	}
	
	public static AppiumIOSConfInfo getInstance() {
	      return LazyHolder.INSTANCE;
	}
	
	public String getPlatformVersion() {
	      return configProp.getProperty("platformVersiom");
	}
	
	public String getAutomationName() {
	      return configProp.getProperty("automationName");
	}
	
	public String getNewCommandTimeout() {
	      return configProp.getProperty("newCommandTimeout");
	}

}
