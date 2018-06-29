package com.cyberiansoft.test.driverutils;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IEConfiguration {

    public DesiredCapabilities getInternetExplorerCapabilities() {

        DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();

//			DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();
//	         System.setProperty("webdriver.ie.driver", PATH_TO_IE_DRIVER);

        IEDesiredCapabilities.setCapability("nativeEvents", false);
        IEDesiredCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
        IEDesiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
        IEDesiredCapabilities.setCapability("disable-popup-blocking", true);
        IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        IEDesiredCapabilities.setCapability("ignoreZoomSetting", true);
        IEDesiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        IEDesiredCapabilities.setJavascriptEnabled(true);
        IEDesiredCapabilities.setCapability("requireWindowFocus", false);
        IEDesiredCapabilities.setCapability("enablePersistentHover", false);
        return IEDesiredCapabilities;
    }
}
