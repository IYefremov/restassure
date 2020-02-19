package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import org.testng.Assert;

public class InspectionsScreenValidation {

    public static void validateInspectionCustomerValueByInspectionNumber(String inspNumber, String retailCustomer1) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspNumber), retailCustomer1);
    }

}
