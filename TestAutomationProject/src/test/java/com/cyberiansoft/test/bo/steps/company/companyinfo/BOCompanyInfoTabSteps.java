package com.cyberiansoft.test.bo.steps.company.companyinfo;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.companyinfo.BOCompanyInfoTab;

public class BOCompanyInfoTabSteps {

    public static void openTab(com.cyberiansoft.test.bo.enums.companyinfo.CompanyInfoTab tab) {
        final BOCompanyInfoTab companyInfoTab = new BOCompanyInfoTab();
        WaitUtilsWebDriver.elementShouldBeVisible(companyInfoTab.getTab(), true, 5);
        Utils.clickElement(companyInfoTab.getTabByName(tab.getTab()));
        WaitUtilsWebDriver.elementShouldBeVisible(companyInfoTab.getOpenedTabByName(tab.getTab()), true, 4);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }
}
