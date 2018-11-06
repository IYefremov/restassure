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
			System.out.println("Can't load BO environment properties");
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

	public String getBackOfficeDemoURL() {
	      return props.getProperty("backofficedemo.url");
	}

	public String getUserName() {
	      return props.getProperty("user.name");
	}

	public String getUserNadaName() {
	      return props.getProperty("user.nada.name");
	}

	public String getAlternativeUserName() {
	      return props.getProperty("alternative.user.name");
	}

	public String getUserDemoName() {
	      return props.getProperty("userdemo.name");
	}

	public String getUserPassword() {
	      return props.getProperty("user.password");
	}

	public String getUserNadaPassword() {
	      return props.getProperty("user.nada.password");
	}

	public String getAlternativeUserPassword() {
	      return props.getProperty("alternative.user.password");
	}

	public String getUserDemoPassword() {
	      return props.getProperty("userdemo.psw");
	}

	public String getDefaultBrowser() {
		return props.getProperty("default.browser");
	}

    public String getOutlookMail() {
        return props.getProperty("outlook.mail");
    }

    public String getOutlookPassword() {
        return props.getProperty("outlook.password");
    }
}
