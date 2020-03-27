package com.cyberiansoft.test.bo.steps.company.companyinfo;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.companyinfo.CompanyInfoTab;

public class CompanyInfoTabSteps {

    public static void openTab(com.cyberiansoft.test.bo.enums.companyinfo.CompanyInfoTab tab) {
        final CompanyInfoTab companyInfoTab = new CompanyInfoTab();
        WaitUtilsWebDriver.elementShouldBeVisible(companyInfoTab.getTab(), true, 5);
        Utils.clickElement(companyInfoTab.getTabByName(tab.getTab()));
        WaitUtilsWebDriver.elementShouldBeVisible(companyInfoTab.getOpenedTabByName(tab.getTab()), true, 4);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }
}
