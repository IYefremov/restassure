package com.cyberiansoft.test.vnextbo.steps.leftmenupanel;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;

public class VNextBOLeftMenuSteps {

    public static void openRepairOrdersMenuInNewTab() {
        Utils.openNewTab(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
    }

    public static void openPartsManagementMenuInNewTab() {
        Utils.openNewTab(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }
}
