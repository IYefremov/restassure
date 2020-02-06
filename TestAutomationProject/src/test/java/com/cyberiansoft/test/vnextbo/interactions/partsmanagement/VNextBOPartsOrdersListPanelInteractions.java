package com.cyberiansoft.test.vnextbo.interactions.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsOrdersListPanel;

public class VNextBOPartsOrdersListPanelInteractions {

    public static String getFirstOrderVinNumber() {
        return Utils.getText(new VNextBOPartsOrdersListPanel().getVinNumbersListOptions().get(0))
                .replace("VIN: ", "");
    }
}
