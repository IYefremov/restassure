package com.cyberiansoft.test.driverutils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

public class SafariConfiguration {
    public DesiredCapabilities getSafariCapabilities(SafariOptions safariOpts) {
        DesiredCapabilities safariCapabilities = DesiredCapabilities.safari();
        safariCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        safariCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "dismiss");
        safariCapabilities.setCapability(SafariOptions.CAPABILITY, safariOpts);
        safariCapabilities.setBrowserName("safari");
        safariCapabilities.setPlatform(Platform.MAC);
        return safariCapabilities;
    }
}
