package com.cyberiansoft.test.vnextbo.steps.commonobjects;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOSearchPanel;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextBOSearchPanelSteps extends VNextBOBaseWebPageSteps {

    public static String getSearchFilterText() {

        return Utils.getText(new VNextBOSearchPanel().getFilterInfoText());
    }

    public static void clearSearchFilter() {

        Utils.clickElement(new VNextBOSearchPanel().getSearchXIcon());
        //WaitUtilsWebDriver.waitForLoading();
    }

    public static void openAdvancedSearchForm() {

        Utils.clickElement(new VNextBOSearchPanel().getAdvancedSearchCaret());
    }

    public static void searchByText(String searchText) {

        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        WaitUtilsWebDriver.waitForElementToBeClickable(searchPanel.getSearchInputField());
        Utils.clearAndType(searchPanel.getSearchInputField(), searchText);
        Utils.clickElement(searchPanel.getSearchLoupeIcon());
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(searchPanel.getSearchLoupeIcon()));
        BaseUtils.waitABit(1000);
    }
}