package com.cyberiansoft.test.vnextbo.interactions.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsOrdersListPanel;

public class VNextBOPartsOrdersListPanelInteractions {

    private static VNextBOPartsOrdersListPanel ordersListPanel;

    static {
        ordersListPanel = new VNextBOPartsOrdersListPanel();
    }

    public static String getFirstOrderVinNumber() {
        return Utils.getText(ordersListPanel.getVinNumbersListOptions().get(0)).replace("VIN: ", "");
    }
}
