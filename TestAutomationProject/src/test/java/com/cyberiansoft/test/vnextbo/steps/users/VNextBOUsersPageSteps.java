package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonObjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOUsersPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickAddNewUserButton() {

        Utils.clickElement(new VNexBOUsersWebPage().addNewUserBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void resendConfirmationEmail() {

        Utils.clickElement(new VNexBOUsersWebPage().reSendButton);
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openUserDataForEdit() {

        Utils.clickElement(new VNexBOUsersWebPage().editUserButton);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByEmail(String email) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setEmailField(email);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByPhone(String phone) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setPhoneField(phone);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static List<WebElement> getUsersTableRows() {

        return new VNexBOUsersWebPage().usersTable.getTableRows();
    }

    public static int getUsersTableRowsCount() {

        return getUsersTableRows().size();
    }

    public static WebElement getTableRowWithText(String userMail) {

        return new VNexBOUsersWebPage().tableRowByText(userMail);
    }

    public static String getUsersNotFoundMessage() {

        return Utils.getText(new VNexBOUsersWebPage().noItemsFoundMessage);
    }
}