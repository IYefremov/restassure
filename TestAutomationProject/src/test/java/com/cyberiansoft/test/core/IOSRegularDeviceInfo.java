package com.cyberiansoft.test.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IOSRegularDeviceInfo {
	
	private final Properties configProp = new Properties();
	
	private IOSRegularDeviceInfo() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("iosdeviceregularconf.properties");
	    try {
	        configProp.load(in);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
			 
	private static class LazyHolder {
	      private static final IOSRegularDeviceInfo INSTANCE = new IOSRegularDeviceInfo();
	}
	
	public static IOSRegularDeviceInfo getInstance() {
	      return LazyHolder.INSTANCE;
	}
	
	public String getDeviceName() {
	      return configProp.getProperty("deviceName");
	}
	
	public String getDeviceBundleId() {
	      return configProp.getProperty("bundleID");
	}
	
	public String getAppDir() {
	      return configProp.getProperty("appDir");
	}
	
	public String getBuildFileName() {
	      return configProp.getProperty("buildFileName");
	}
	
	public String getDeviceUDID() {
	      return configProp.getProperty("bundleID");
	}

}
