package com.cyberiansoft.test.vnext.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextConfigInfo {
	
	private static VNextConfigInfo _instance = null;

    private Properties props = null;

    private VNextConfigInfo() {
    	props = new Properties();
    	File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnextenvironment.properties");
        try {
        	FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			System.out.println("Can't load VNext environment properties");
			e.printStackTrace();
		}       
    }

    public synchronized static VNextConfigInfo getInstance() {
        if (_instance == null)
            _instance = new VNextConfigInfo();
        return _instance;
    }		 
	
	public String getBackOfficeCapiURL() {
	      return props.getProperty("backofficecapi.url");
	}
	
	public String getUserCapiMail() {
	      return props.getProperty("usercapi.mail");
	}

	public String getOutlookMail() {
		return props.getProperty("outlook.mail");
	}

	public String getUserCapiMailPassword() {
		return props.getProperty("usercapimail.password");
	}
	
	public String getUserCapiUserName() {
	      return props.getProperty("usercapi.name");
	}
	
	public String getUserCapiUserPassword() {
	      return props.getProperty("usercapi.password");
	}
	
	public String getBackOfficeVnextDevURL() {
	      return props.getProperty("backofficevnextdev.url");
	}
	
	public String getUserVnextDevUserName() {
	      return props.getProperty("uservnextdev.name");
	}
	
	public String getUserVnextDevUserPassword() {
	      return props.getProperty("uservnextdev.password");
	}
	
	public String getBuildProductionAttribute() {
	      return props.getProperty("build.production");
	}
	
	public String geReportFolderPath() {
	      return props.getProperty("report.folder");
	}
	
	public String geReportFileName() { return props.getProperty("report.filename"); }

	public boolean installNewBuild() {
    	boolean installNewBuild = false;
		String newBuild= props.getProperty("new.build");
		if (newBuild.equalsIgnoreCase("true"))
			installNewBuild = true;
		return installNewBuild;
	}
}
