package com.cyberiansoft.test.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AppiumIOSConfInfo {
	
private final Properties configProp = new Properties();
	
	private AppiumIOSConfInfo() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("appiumiosconf.properties");
	    try {
	        configProp.load(in);
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
