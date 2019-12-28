package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VNextBOUsersPageSteps extends VNextBOBaseWebPageSteps {

    public static void resendConfirmationEmail() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.reSendButton);
        VNextBOModalDialogSteps.clickYesButton();
    }

    public static void clickAddNewUserButton() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.addNewUserBtn);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(new VNexBOAddNewUserDialog().firstNameFld));
    }

    public static void openAdvancedSearchForm() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.searchFieldAdvancedSearchCaret);
    }

    public static void openUserDataForEdit() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.editUserButton);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(new VNexBOAddNewUserDialog().firstNameFld));
    }

    public static void searchUserByEmail(String email) {
        openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setEmailField(email);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
    }

    public static void searchUserByPhone(String phone) {
        openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setPhoneField(phone);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
    }

    public static void searchUserByName(String userName) {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clearAndType(vNexBOUsersWebPage.searchField, userName);
        Utils.clickElement(vNexBOUsersWebPage.searchIcon);
    }

    public static List<WebElement> getUsersTableRows() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return vNexBOUsersWebPage.usersTable.getTableRows();
    }

    public static int getUsersTableRowsCount() {
        return getUsersTableRows().size();
    }

    public static WebElement getTableRowWithText(String searchText) {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return vNexBOUsersWebPage.tableRowByText(searchText);
    }

    public static String getUsersNotFoundMessage() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.getText(vNexBOUsersWebPage.noItemsFoundMessage);
    }

    public static void waitUntilPageIsOpened() {
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(new VNexBOUsersWebPage().addNewUserBtn));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }
}