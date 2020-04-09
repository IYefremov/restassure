package com.cyberiansoft.test.bo.steps.company.companyinfo;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.companyinfo.BOIntegrationSettingsBlock;

public class BOIntegrationSettingsBlockSteps {

    public static void openPartProvidersSettingsDialog(String parentWindow) {
        Utils.clickElement(new BOIntegrationSettingsBlock().getPartsProvidersConfigureButton());
        Utils.getNewTab(parentWindow);
    }
}
