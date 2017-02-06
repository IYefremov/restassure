package com.cyberiansoft.test.core;

import java.io.InputStream;
import java.util.Properties;


public class IOSHDDeviceInfo {
	
	private static IOSHDDeviceInfo _instance = null;

    private Properties props = null;

    private IOSHDDeviceInfo() {
         props = new Properties();
    	try {
    		InputStream in = this.getClass().getClassLoader().getResourceAsStream("iosdevicehdconf.properties");
	    props.load(in);
    	}
    	catch (Exception e) {
    	    // catch Configuration Exception right here
    	}
    }

    public synchronized static IOSHDDeviceInfo getInstance() {
        if (_instance == null)
            _instance = new IOSHDDeviceInfo();
        return _instance;
    }		 
	
	
	public String getDeviceName() {
	      return props.getProperty("deviceName");
	}
	
	public String getDeviceBundleId() {
	      return props.getProperty("bundleID");
	}
	
	public String getAppDir() {
	      return props.getProperty("appDir");
	}
	
	public String getBuildFileName() {
	      return props.getProperty("buildFileName");
	}
	
	public String getDeviceUDID() {
	      return props.getProperty("udid");
	}
	
	public String getPlatformVersion() {
	      return props.getProperty("platformVersiom");
	}
	
	public String getAutomationName() {
	      return props.getProperty("automationName");
	}
	
	public String getNewCommandTimeout() {
	      return props.getProperty("newCommandTimeout");
	}

}
