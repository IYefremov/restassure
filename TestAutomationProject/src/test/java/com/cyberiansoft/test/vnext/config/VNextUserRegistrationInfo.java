package com.cyberiansoft.test.vnext.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextUserRegistrationInfo {
	
	private static VNextUserRegistrationInfo _instance = null;

    private Properties props = null;

    private VNextUserRegistrationInfo() {
    	props = new Properties();
    	File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnextuserreginfo.properties");
        try {
        	FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			System.out.println("Can't load VNext environment properties");
			e.printStackTrace();
		}       
    }

    public synchronized static VNextUserRegistrationInfo getInstance() {
        if (_instance == null)
            _instance = new VNextUserRegistrationInfo();
        return _instance;
    }		 
	
	public String getDeviceRegistrationUserMail() {
	      return props.getProperty("user.regMail");
	}
	
	public String getDeviceRegistrationUserFirstName() {
	      return props.getProperty("user.regFirstName");
	}
	
	public String getDeviceRegistrationUserLastName() {
	      return props.getProperty("user.regLastName");
	}
	
	public String getDeviceRegistrationUserPhoneCountryCode() {
	      return props.getProperty("user.regCountryCode");
	}

	public String getDeviceRegistrationUserPhoneNumber() {
	      return props.getProperty("user.regPhoneNumber");
	}

}
