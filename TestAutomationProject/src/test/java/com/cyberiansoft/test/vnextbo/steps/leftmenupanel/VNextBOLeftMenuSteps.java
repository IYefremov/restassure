package com.cyberiansoft.test.vnextbo.steps.leftmenupanel;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;

public class VNextBOLeftMenuSteps {

    public static void openRepairOrdersMenuInNewTab() {
        Utils.openNewTab(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
    }

    public static void openPartsManagementMenuInNewTab() {
        Utils.openNewTab(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }
}
