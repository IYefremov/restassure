package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.ListSelectPage;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class GeneralListValidations {

    public static void elementShouldBePresent(String serviceName, boolean shouldBePresent) {
        ListSelectPage listPage = new ListSelectPage();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(listPage.getItemList().stream().anyMatch(elem -> elem.getText().contains(serviceName)), shouldBePresent);
            return true;
        });
    }
}
