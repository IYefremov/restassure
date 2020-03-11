package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusScreenSteps {

    public static void updateMainDB() {
        VNextStatusScreen statusScreen = new VNextStatusScreen();
        WaitUtils.waitUntilElementIsClickable(statusScreen.getUpdateMainDBBtn());
        WaitUtils.getGeneralFluentWait().until(driver -> {
            statusScreen.getUpdateMainDBBtn().click();
            return true;
        });

        try {
            WaitUtils.waitUntilElementIsClickable(statusScreen.getStartSyncBtn()).click();
        } catch (TimeoutException e) {
            //do nothing
        }
        VNextInformationDialog informationDialog = new VNextInformationDialog();
        WaitUtils.getGeneralFluentWait(90, 500).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='OK']")));
        informationDialog.clickInformationDialogOKButtonAndGetMessage();
        GeneralSteps.logIn(BaseTestClass.getEmployee());
    }

    public static void openFeedBackScreen() {
        VNextStatusScreen statusScreen = new VNextStatusScreen();
        statusScreen.getFeedbackBtn().click();
    }
}
