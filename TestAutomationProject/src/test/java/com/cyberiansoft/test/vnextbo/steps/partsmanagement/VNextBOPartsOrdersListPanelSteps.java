package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsOrdersListPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOPartsOrdersListPanelSteps {

    public static void openPartOrderDetailsByNumberInList(int orderNumberInList) {

        Utils.clickElement(new VNextBOPartsOrdersListPanel().getListOptions().get(orderNumberInList));
    }

    static void openPartOrderDetails(WebElement order) {
        final boolean active = Utils.isElementWithAttributeContainingValueDisplayed(
                order.findElement(By.xpath(".//div[1]")), "class", "active", 0);

        if (!active) {
            Utils.clickElement(order);
            WaitUtilsWebDriver.waitForPageToBeLoaded(3);
        }
    }

    public static List<String> getCustomerNamesListOptions() {
        return Utils.getText(new VNextBOPartsOrdersListPanel().getCustomerNamesListOptions());
    }
}
