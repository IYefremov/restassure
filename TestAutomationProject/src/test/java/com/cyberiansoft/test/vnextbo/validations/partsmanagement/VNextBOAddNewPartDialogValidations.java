package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOAddNewPartDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddNewPartDialog;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

public class VNextBOAddNewPartDialogValidations {

    public static void verifyDialogIsDisplayed(boolean dialogDisplayed) {

        final VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        WaitUtilsWebDriver.elementShouldBeVisible(addNewPartDialog.getDialogContent(), dialogDisplayed, 5);
        Assert.assertEquals(Utils.isElementDisplayed(addNewPartDialog.getDialogContent()), dialogDisplayed,
                "Add new part dialog hasn't been displayed");
    }

    public static void verifyServiceFieldIsCorrect(String serviceName) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewPartDialog().getServiceField()), serviceName,
                "Service field has contained incorrect value");
    }

    public static void verifySelectedPartsCounterValueIsCorrect(String expectedCounterValue) {

        Assert.assertEquals(Utils.getText(new VNextBOAddNewPartDialog().getSelectedPartsCounter()), expectedCounterValue,
                "Selected parts counter value hasn't been correct");
    }

    public static void verifyPartsListSizeIsCorrect(int expectedPartsSize) {
        int size = 0;
        try {
            WaitUtilsWebDriver.getWebDriverWait(2).until((ExpectedCondition<Boolean>) driver ->
                            VNextBOAddNewPartDialogInteractions.getPartsListSize() == expectedPartsSize);
            size = VNextBOAddNewPartDialogInteractions.getPartsListSize();
        } catch (Exception ignored) {}
        Assert.assertEquals(size, expectedPartsSize, "The parts list size isn't correct");
    }
}
