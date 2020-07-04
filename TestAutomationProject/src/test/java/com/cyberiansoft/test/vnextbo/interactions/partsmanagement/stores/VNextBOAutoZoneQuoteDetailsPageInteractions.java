package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores.VNextBOAutoZoneQuoteDetailsPage;

public class VNextBOAutoZoneQuoteDetailsPageInteractions {

    public static String getTotalPriceValue() {
        return Utils.getText(new VNextBOAutoZoneQuoteDetailsPage().getTotalPrice()).trim().replace("$", "");
    }

    public static void clickTransferCartButton() {
        Utils.clickElement(new VNextBOAutoZoneQuoteDetailsPage().getTransferCartButton());
    }
}
