package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores.VNextBOAutoZoneMyShopPage;
import org.openqa.selenium.WebElement;

public class VNextBOAutoZoneMyShopPageInteractions {

    public static void clickBatteriesCablesCheckbox() {
        Utils.clickElement(new VNextBOAutoZoneMyShopPage().getBatteriesCablesCheckbox());
    }

    public static void clickLookupPartsButton() {
        final WebElement lookupPartsButton = new VNextBOAutoZoneMyShopPage().getLookupPartsButton();
        WaitUtilsWebDriver.elementShouldBeClickable(lookupPartsButton, true, 3);
        Utils.clickElement(lookupPartsButton);
    }
}
