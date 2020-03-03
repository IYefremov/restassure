package com.cyberiansoft.test.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class IOSRegularDeviceInfo {
	
	private static IOSRegularDeviceInfo _instance = null;

    private Properties props = null;

    private IOSRegularDeviceInfo() {
         props = new Properties();
    	File file =
				new File("src/test/java/com/cyberiansoft/test/core/iosdeviceregularconf.properties");
		try {
			FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (Exception e) {
    	    // catch Configuration Exception right here
    	}
    }

    public synchronized static IOSRegularDeviceInfo getInstance() {
        if (_instance == null)
            _instance = new IOSRegularDeviceInfo();
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
