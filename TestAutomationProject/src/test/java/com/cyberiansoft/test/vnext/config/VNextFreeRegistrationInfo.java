package com.cyberiansoft.test.vnext.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextFreeRegistrationInfo {
	
	private static VNextFreeRegistrationInfo _instance = null;

    private Properties props = null;

    private VNextFreeRegistrationInfo() {
    	props = new Properties();
    	File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnextfreereginfo.properties");
        try {
        	FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			System.out.println("Can't load VNext environment properties");
			e.printStackTrace();
		}       
    }

    public synchronized static VNextFreeRegistrationInfo getInstance() {
        if (_instance == null)
            _instance = new VNextFreeRegistrationInfo();
        return _instance;
    }

	public String getR360BackOfficeStagingURL() {
		return props.getProperty("r360backoffice.development");
	}

	public String getR360BackOfficeIntegrationURL() {
		return props.getProperty("r360backoffice.integration");
	}


	public String getR360BackOfficeSettingsStagingURL() {

    	return props.getProperty("r360backofficesettings.development");
	}

	public String getR360BackOfficeSettingsIntegrationURL() {
		return props.getProperty("r360backofficesettings.integration");
	}

	public String getAPIStagingURL() {

		return props.getProperty("api.development");
	}

	public String getAPIIntegrationURL() {
		return props.getProperty("api.integration");
	}

	public String getAPIProductionURL() {
		return props.getProperty("api.prod");
	}

	public String getR360UserMail() {
		return props.getProperty("r360usermail.mail");
	}

	public String getR360OutlookMail() {
		return props.getProperty("r360outlook.mail");
	}

	public String getR360UserPassword() {
		return props.getProperty("r360user.password");
	}

	public String getR360UserUserName() {
		return props.getProperty("r360user.name");
	}

	public String getUserVnextDevUserName() {
	      return props.getProperty("uservnextdev.name");
	}
	
	public String getUserVnextDevUserPassword() {
	      return props.getProperty("uservnextdev.password");
	}

}
