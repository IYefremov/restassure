package com.cyberiansoft.test.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class IOSHDDeviceInfo {
	
private final Properties configProp = new Properties();
	
	private IOSHDDeviceInfo() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("iosdevicehdconf.properties");
	    try {
	        configProp.load(in);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
			 
	private static class LazyHolder {
	      private static final IOSHDDeviceInfo INSTANCE = new IOSHDDeviceInfo();
	}
	
	public static IOSHDDeviceInfo getInstance() {
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
