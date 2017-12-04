package com.cyberiansoft.test.monitorlite.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.cyberiansoft.test.vnext.config.VNextConfigInfo;

public class MonitorLiteConfigInfo {
		
		private static MonitorLiteConfigInfo _instance = null;

	    private Properties props = null;

	    private MonitorLiteConfigInfo() {
	    	props = new Properties();
	    	File file =
	                new File("src/test/java/com/cyberiansoft/test/monitorlite/config/monitorliteenvironment.properties");
	        try {
	        	FileInputStream fileInput = new FileInputStream(file);
				props.load(fileInput);
				fileInput.close();
			} catch (IOException e) {
				System.out.println("Can't load VNext environment properties");
				e.printStackTrace();
			}       
	    }

	    public synchronized static MonitorLiteConfigInfo getInstance() {
	        if (_instance == null)
	            _instance = new MonitorLiteConfigInfo();
	        return _instance;
	    }	
	    
	    public String getBackOfficeReconProURL() {
		      return props.getProperty("backofficereconpro.url");
		}
		
		public String getBackOfficeMonitorLiteURL() {
		      return props.getProperty("backofficemonitorlite.url");
		}
		
		public String getUserMonitorLiteMail() {
		      return props.getProperty("usermonitorlite.mail");
		}
		
		public String getUserMonitorLiteUserName() {
		      return props.getProperty("usermonitorlite.name");
		}
		
		public String getUserMonitorLiteUserPassword() {
		      return props.getProperty("usermonitorlite.password");
		}
}
