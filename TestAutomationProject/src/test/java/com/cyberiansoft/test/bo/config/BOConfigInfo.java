package com.cyberiansoft.test.bo.config;

import com.cyberiansoft.test.baseutils.OsUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BOConfigInfo {

	private static BOConfigInfo _instance = null;

	private Properties properties;

	private BOConfigInfo() {
		properties = new Properties();
		File file =
				new File(OsUtils.getOSSafePath("src/test/java/com/cyberiansoft/test/bo/config/backofice.properties"));
		try {
			FileInputStream fileInput = new FileInputStream(file);
			properties.load(fileInput);
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
		return properties.getProperty("backoffice.url");
	}

	public String getBackOfficeURLMain() {
		return properties.getProperty("backoffice.url.main");
	}

	public String getBackOfficeDemoURL() {
		return properties.getProperty("backofficedemo.url");
	}

	public String getUserName() {
		return properties.getProperty("user.name");
	}

	public String getUserNadaName() {
		return properties.getProperty("user.nada.name");
	}

	public String getAlternativeUserName() {
		return properties.getProperty("alternative.user.name");
	}

	public String getUserDemoName() {
		return properties.getProperty("userdemo.name");
	}

	public String getUserPassword() {
		return properties.getProperty("user.password");
	}

	public String getUserNadaPassword() {
		return properties.getProperty("user.nada.password");
	}

	public String getAlternativeUserPassword() {
		return properties.getProperty("alternative.user.password");
	}

	public String getUserDemoPassword() {
		return properties.getProperty("userdemo.psw");
	}

	public String getOutlookMail() {
		return properties.getProperty("outlook.mail");
	}

	public String getOutlookPassword() {
		return properties.getProperty("outlook.password");
	}
}
