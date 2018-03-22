package com.cyberiansoft.test.vnext.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextToolsInfo {
	
	private static VNextToolsInfo _instance = null;

    private Properties props = null;

    private VNextToolsInfo() {
    	props = new Properties();
    	File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnexttools.properties");
        try {
        	FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			System.out.println("Can't load VNext environment properties");
			e.printStackTrace();
		}       
    }

    public synchronized static VNextToolsInfo getInstance() {
        if (_instance == null)
            _instance = new VNextToolsInfo();
        return _instance;
    }		 
	
	public String getDefaultBrowser() {
	      return props.getProperty("def.browser");
	}
	
	public String getDefaultPlatform() {
	      return props.getProperty("def.platform");
	}

}
