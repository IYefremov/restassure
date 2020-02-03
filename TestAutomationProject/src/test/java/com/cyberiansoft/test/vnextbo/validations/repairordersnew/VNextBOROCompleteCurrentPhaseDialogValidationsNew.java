package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROCompleteCurrentPhaseDialogNew;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOROCompleteCurrentPhaseDialogValidationsNew {

    public static void verifyDialogIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed)
            Assert.assertTrue(Utils.isElementDisplayed(new VNextBOROCompleteCurrentPhaseDialogNew().getCompleteCurrentPhaseDialog()),
                "Complete current phase dialog hasn't been displayed");
        else {
            try {
                Assert.assertFalse(Utils.isElementDisplayed(new VNextBOROCompleteCurrentPhaseDialogNew().getCompleteCurrentPhaseDialog()),
                        "Complete current phase dialog hasn't been closed");
            } catch (NoSuchElementException ex) {

            }
        }
    }

    public static void verifyColumnsTitlesAreCorrect() {

        List<String> correctTitlesList = Arrays.asList("Service Name", "Problem Reason", "Problem Description", "Action");
        List<String> actualTitlesList = new VNextBOROCompleteCurrentPhaseDialogNew().getServicesTableColumnsTitles().stream().
                map(WebElement::getText).collect(Collectors.toList());
        Assert.assertEquals(actualTitlesList, correctTitlesList, "Table with services has had incorrect columns.");
    }

    public static void verifyCancelButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOROCompleteCurrentPhaseDialogNew().getCancelButton()),
                "Cancel button hasn't been displayed.");
    }

    public static void verifyCompleteCurrentPhaseButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOROCompleteCurrentPhaseDialogNew().getCompleteCurrentPhaseButton()),
                "Complete current phase button hasn't been displayed.");
    }

    public static void verifyCompleteCurrentPhaseButtonIsClickable(boolean shouldBeClickable) {

        if (shouldBeClickable)
            Assert.assertTrue(Utils.isElementClickable(new VNextBOROCompleteCurrentPhaseDialogNew().getCompleteCurrentPhaseButton()),
                "Complete current phase button hasn't been clickable.");
        else
            Assert.assertFalse(Utils.isElementClickable(new VNextBOROCompleteCurrentPhaseDialogNew().getCompleteCurrentPhaseButton()),
                    "Complete current phase button has been clickable.");
    }

    public static void verifyServiceIsPresentedInTheTable(String service) {

        List<String> servicesList = new VNextBOROCompleteCurrentPhaseDialogNew().getServicesNamesList().stream().
                map(WebElement::getText).collect(Collectors.toList());
        Assert.assertTrue(servicesList.contains(service), "Table with services hasn't contained service " + service);
    }

    public static void verifyResolveButtonIsDisplayedForService(String service) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOROCompleteCurrentPhaseDialogNew().resolveButtonByServiceName(service)),
                "Resolve button hasn't been displaye for the service " + service);
    }
}
