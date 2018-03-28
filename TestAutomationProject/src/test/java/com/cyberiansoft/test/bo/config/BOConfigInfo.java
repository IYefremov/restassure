package com.cyberiansoft.test.bo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BOConfigInfo {
	
	private static BOConfigInfo _instance = null;

    private Properties props;

    private BOConfigInfo() {
    	props = new Properties();
    	File file =
                new File("src/test/java/com/cyberiansoft/test/bo/config/backofice.properties");
        try {
        	FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			System.out.println("Can't load VNext environment properties");
			e.printStackTrace();
		}       
    }

    public synchronized static BOConfigInfo getInstance() {
        if (_instance == null)
            _instance = new BOConfigInfo();
        return _instance;
    }		 
	
	public String getBackOfficeURL() {
	      return props.getProperty("backoffice.url");
	}
	
	public String getUserMail() {
	      return props.getProperty("user.mail");
	}
	
	public String getUserName() {
	      return props.getProperty("user.name");
	}
	
	public String getUserPassword() {
	      return props.getProperty("user.password");
	}

	public String getDefaultBrowser() {
		return props.getProperty("default.browser");
	}
}
