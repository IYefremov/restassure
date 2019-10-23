package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonObjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOUsersPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickAddNewUserButton()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(vNexBOUsersWebPage.addNewUserBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void resendConfirmationEmail()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(vNexBOUsersWebPage.reSendButton);
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openUserDataForEdit()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(vNexBOUsersWebPage.editUserButton);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByEmail(String email)
    {
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setEmailField(email);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByPhone(String phone)
    {
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setPhoneField(phone);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static List<WebElement> getUsersTableRows()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        return vNexBOUsersWebPage.usersTable.getTableRows();
    }

    public static int getUsersTableRowsCount()
    {
        return getUsersTableRows().size();
    }

    public static WebElement getTableRowWithText(String userMail)
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        return vNexBOUsersWebPage.tableRowByText(userMail);
    }

    public static String getUsersNotFoundMessage()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.getText(vNexBOUsersWebPage.noItemsFoundMessage);
    }
}