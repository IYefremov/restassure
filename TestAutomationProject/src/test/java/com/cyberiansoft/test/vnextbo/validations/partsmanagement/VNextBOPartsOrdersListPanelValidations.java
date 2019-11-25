package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsOrdersListPanel;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOPartsOrdersListPanelValidations {

    public static void verifyOrdersListPanelIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsOrdersListPanel().getListPanel()), "WO orders list panel hasn't been displayed");
    }

    public static void verifyOrdersAreDisplayed() {

        Assert.assertTrue(new VNextBOPartsOrdersListPanel().getWoNumsListOptions().size() > 0, "WO orders hasn't been displayed");
    }

    public static void verifyWoNumberIsCorrectForAllOrders(String expectedWoNumber) {

        for (WebElement woNumber : new VNextBOPartsOrdersListPanel().getWoNumsListOptions()) {
            Assert.assertEquals(woNumber.getText(), expectedWoNumber, "WO order number hasn't been correct");
        }
    }
}
