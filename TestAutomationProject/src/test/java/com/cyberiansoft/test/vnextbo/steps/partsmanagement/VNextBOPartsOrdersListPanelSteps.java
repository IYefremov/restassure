package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsOrdersListPanel;

public class VNextBOPartsOrdersListPanelSteps {

    public static void openPartOrderDetailsByNumberInList(int orderNumberInList) {

        Utils.clickElement(new VNextBOPartsOrdersListPanel().getListOptions().get(orderNumberInList));
    }
}
