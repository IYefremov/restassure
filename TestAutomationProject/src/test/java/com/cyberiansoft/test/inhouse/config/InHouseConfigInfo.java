package com.cyberiansoft.test.inhouse.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class InHouseConfigInfo {

	private static InHouseConfigInfo instance = null;

    private Properties props;

    private InHouseConfigInfo() {
    	props = new Properties();
    	File file =
                new File("src/test/java/com/cyberiansoft/test/inhouse/config/inhouse.properties");
        try {
        	FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			System.out.println("Can't load InHouse environment properties");
			e.printStackTrace();
		}
    }

    public synchronized static InHouseConfigInfo getInstance() {
        if (instance == null)
            instance = new InHouseConfigInfo();
        return instance;
    }		 
	
	public String getInHouseURL() {
	      return props.getProperty("inhouse.url");
	}

	public String getUserName() {
	      return props.getProperty("user.name");
	}

	public String getUserEmail() {
	      return props.getProperty("user.mail");
	}

	public String getUserPassword() {
	      return props.getProperty("user.password");
	}

	public String getDefaultBrowser() {
		return props.getProperty("default.browser");
	}
}
