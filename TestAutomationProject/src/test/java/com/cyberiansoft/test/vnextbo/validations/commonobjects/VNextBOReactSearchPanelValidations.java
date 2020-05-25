package com.cyberiansoft.test.vnextbo.validations.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOReactSearchPanel;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOReactSearchPanelSteps;
import org.testng.Assert;

public class VNextBOReactSearchPanelValidations {

    public static void verifyClearSearchIconIsDisplayed() {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOReactSearchPanel().getSearchXIcon()));
    }

    public static void verifyFilterInfoTextIsDisplayed() {
        Assert.assertEquals(Utils.getText(new VNextBOReactSearchPanel().getFilterInfoText()),
                "Timeframe: " + TimeFrameValues.TIMEFRAME_30_DAYS.getName() + ";",
                "The default filter info text hasn't been displayed");
    }

    public static void verifyFilterInfoTextIsDisplayed(String text) {
        Assert.assertEquals(Utils.getText(new VNextBOReactSearchPanel().getFilterInfoText()), text,
                "The filter info text hasn't been displayed");
    }

    public static void verifySearchInputFieldIsEmpty() {
        Assert.assertEquals(VNextBOReactSearchPanelSteps.getSearchInputFieldValue(), "",
                "The search input field isn't empty");
    }
}
