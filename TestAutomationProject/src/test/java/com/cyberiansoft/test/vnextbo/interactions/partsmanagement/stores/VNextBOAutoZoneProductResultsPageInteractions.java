package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores.VNextBOAutoZoneProductResultsPage;
import org.openqa.selenium.WebElement;

public class VNextBOAutoZoneProductResultsPageInteractions {

    private static VNextBOAutoZoneProductResultsPage productResultsPage;

    static {
        productResultsPage = new VNextBOAutoZoneProductResultsPage();
    }

    public static String getCorePriceValue() {
        final WebElement corePrice = productResultsPage.getCorePrice();
        WaitUtilsWebDriver.elementShouldBeVisible(corePrice, true, 10);
        return Utils.getText(corePrice).trim().replace("$", "").replace("Core\n", "");
    }

    public static String getPriceValue() {
        final WebElement price = productResultsPage.getPrice();
        WaitUtilsWebDriver.elementShouldBeVisible(price, true, 10);
        return Utils.getText(price).trim().replace("$", "");
    }

    public static void clickAddToQuoteButton() {
        Utils.clickElement(productResultsPage.getAddToQuoteButton());
    }

    public static void clickViewQuoteButton() {
        final WebElement viewQuoteButton = productResultsPage.getViewQuoteButton();
        WaitUtilsWebDriver.elementShouldBeVisible(viewQuoteButton, true, 10);
        Utils.clickElement(viewQuoteButton);
    }
}
