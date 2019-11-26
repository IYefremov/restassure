package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsManagementWebPage;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOPartsManagementWebPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyPastDuePartsBoxIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsManagementWebPage().getPastDuePartsButton()),
                "\"Past Due Parts\" box hasn't been displayed");
    }

    public static void verifyInProgressBoxIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsManagementWebPage().getInProgressButton()),
                "\"In Progress\" box hasn't been displayed");
    }

    public static void verifyCompletedBoxIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsManagementWebPage().getCompletedButton()),
                "\"Completed\" box hasn't been displayed");
    }

    public static void verifyPastDuePartsBoxIsHighlighted() {

        Assert.assertTrue(new VNextBOPartsManagementWebPage().getPastDuePartsButton().getAttribute("style").equals("border-color: rgb(235, 87, 87);"),
                "\"Past Due Parts\" box hasn't been highlighted with red border");
    }

    public static void verifyInProgressBoxIsHighlighted() {

        Assert.assertTrue(new VNextBOPartsManagementWebPage().getInProgressButton().getAttribute("style").equals("border-color: rgb(111, 207, 151);"),
                "\"In Progress\" box hasn't been highlighted with green border");
    }

    public static void verifyCompletedBoxIsHighlighted() {

        Assert.assertTrue(new VNextBOPartsManagementWebPage().getCompletedButton().getAttribute("style").equals("border-color: rgb(111, 207, 151);"),
                "\"Completed\" box hasn't been highlighted with green border");
    }

}
