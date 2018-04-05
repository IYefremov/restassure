package com.cyberiansoft.test.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class IOSHDDeviceInfo {
	
	private static IOSHDDeviceInfo _instance = null;

    private Properties props = null;

    private IOSHDDeviceInfo() {
         props = new Properties();
		File file =
				new File("src/test/java/com/cyberiansoft/test/core/iosdevicehdconf.properties");
		try {
			FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
    	}
    	catch (IOException e) {
    	    System.out.println("Can't load device properties");
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
	
	public String getDeviceUDID() {
	      return props.getProperty("udid");
	}
	
	public String getPlatformVersion() {
	      return props.getProperty("platformVersion");
	}
	
	public String getAutomationName() {
	      return props.getProperty("automationName");
	}
	
	public String getNewCommandTimeout() {
	      return props.getProperty("newCommandTimeout");
	}

}
