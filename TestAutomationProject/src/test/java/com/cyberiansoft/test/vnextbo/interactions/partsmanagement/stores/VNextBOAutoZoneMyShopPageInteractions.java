package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores.VNextBOAutoZoneMyShopPage;
import org.openqa.selenium.WebElement;

public class VNextBOAutoZoneMyShopPageInteractions {

    private static VNextBOAutoZoneMyShopPage myShopPage;

    static {
        myShopPage = new VNextBOAutoZoneMyShopPage();
    }

    public static void clickBatteriesCablesCheckbox() {
        Utils.clickElement(myShopPage.getBatteriesCablesCheckbox());
    }

    public static void clickLookupPartsButton() {
        final WebElement lookupPartsButton = myShopPage.getLookupPartsButton();
        WaitUtilsWebDriver.elementShouldBeClickable(lookupPartsButton, true, 3);
        Utils.clickElement(lookupPartsButton);
    }
}
