package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores.VNextBOAutoZoneQuoteDetailsPage;

public class VNextBOAutoZoneQuoteDetailsPageInteractions {

    private static VNextBOAutoZoneQuoteDetailsPage quoteDetailsPage;

    static {
        quoteDetailsPage = new VNextBOAutoZoneQuoteDetailsPage();
    }

    public static String getTotalPriceValue() {
        return Utils.getText(quoteDetailsPage.getTotalPrice()).trim().replace("$", "");
    }

    public static void clickTransferCartButton() {
        Utils.clickElement(quoteDetailsPage.getTransferCartButton());
    }
}
