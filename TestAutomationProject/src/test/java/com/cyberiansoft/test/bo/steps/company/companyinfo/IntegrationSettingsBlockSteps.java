package com.cyberiansoft.test.bo.steps.company.companyinfo;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.companyinfo.IntegrationSettingsBlock;

public class IntegrationSettingsBlockSteps {

    public static void openPartProvidersSettingsDialog(String parentWindow) {
        Utils.clickElement(new IntegrationSettingsBlock().getPartsProvidersConfigureButton());
        Utils.getNewTab(parentWindow);
    }
}
