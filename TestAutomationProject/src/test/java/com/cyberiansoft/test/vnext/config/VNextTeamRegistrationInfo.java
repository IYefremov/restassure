package com.cyberiansoft.test.vnext.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextTeamRegistrationInfo {
	
	private static VNextTeamRegistrationInfo _instance = null;

    private Properties props = null;

    private VNextTeamRegistrationInfo() {
    	props = new Properties();
    	File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnextteamreginfo.properties");
        try {
        	FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			System.out.println("Can't load VNext environment properties");
			e.printStackTrace();
		}       
    }

    public synchronized static VNextTeamRegistrationInfo getInstance() {
        if (_instance == null)
            _instance = new VNextTeamRegistrationInfo();
        return _instance;
    }		 
	
	public String getBackOfficeStagingURL() {
	      return props.getProperty("backofficestage.url");
	}
	
	public String getBackOfficeStagingUserName() {
	      return props.getProperty("userstage.name");
	}
	
	public String getBackOfficeStagingUserPassword() {
	      return props.getProperty("userstage.password");
	}
	
	public String getDeviceEmployeeName() {
	      return props.getProperty("employee.name");
	}
	
	public String getDeviceEmployeePassword() {
	      return props.getProperty("employee.password");
	}
	
	public String getDeviceDefaultLicenseName() {
	      return props.getProperty("defaultlicense.name");
	}
	
	public String getDeviceEmployeeSearchLicenseName() {
	      return props.getProperty("employeesearchlicense.name");
	}

}
