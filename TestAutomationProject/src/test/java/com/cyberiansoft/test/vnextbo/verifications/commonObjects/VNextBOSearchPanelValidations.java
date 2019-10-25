package com.cyberiansoft.test.vnextbo.verifications.commonObjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.commonObjects.VNextBOSearchPanel;
import com.cyberiansoft.test.vnextbo.steps.commonObjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOSearchPanelValidations extends VNextBOBaseWebPageValidations {

    public static void isSearchPanelDisplayed() {

        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        Assert.assertTrue(Utils.isElementDisplayed(searchPanel.getSearchPanel()),
                "Search panel hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(searchPanel.getSearchInputField()),
                "Search input field hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(searchPanel.getSearchLoupeIcon()),
                "Search loupe icon hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(searchPanel.getAdvancedSearchCaret()),
                "Advanced search drop down caret hasn't been displayed.");
    }

    public static void isSearchFilterTextCorrect(String text) {

        Assert.assertTrue(VNextBOSearchPanelSteps.getSearchFilterText().contains(text),
                "Search option under Search field hasn't been correct");
    }
}