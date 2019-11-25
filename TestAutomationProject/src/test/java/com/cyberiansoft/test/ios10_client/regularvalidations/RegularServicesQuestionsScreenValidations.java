package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularServicesQuestionsScreen;
import org.testng.Assert;

public class RegularServicesQuestionsScreenValidations {

    public static void verifyServiceDeclinedOrSkipped(String serviceName) {
        RegularServicesQuestionsScreen servicesQuestionsScreen = new RegularServicesQuestionsScreen();
        Assert.assertTrue(servicesQuestionsScreen.isServiceDeclinedSkipped(serviceName));
    }

    public static void verifyServiceApproved(String serviceName) {
        RegularServicesQuestionsScreen servicesQuestionsScreen = new RegularServicesQuestionsScreen();
        Assert.assertTrue(servicesQuestionsScreen.isServiceApproved(serviceName));
    }
}
