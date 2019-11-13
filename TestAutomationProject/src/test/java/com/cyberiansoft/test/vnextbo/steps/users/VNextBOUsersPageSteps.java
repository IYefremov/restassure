package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOUsersPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickNextPageButton() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.nextPageBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickPreviousPageButton() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.previousPageBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickLastPageButton() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.lastPageBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFirstPageButton() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.firstPageBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void resendConfirmationEmail() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.reSendButton);
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickAddNewUserButton() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.addNewUserBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openPageByNumber(int pageNumber) {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.specificPageButton(pageNumber));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getActivePageNumberFromTopPagingElement() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.getText(vNexBOUsersWebPage.activePageTopPagingElement);
    }

    public static String getActivePageNumberFromBottomPagingElement() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.getText(vNexBOUsersWebPage.activePageBottomPagingElement);
    }

    public static void changeItemsPerPage(String itemsPerPage) {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.topItemsPerPageField);
        Utils.clickWithJS(vNexBOUsersWebPage.itemsPerPageOption(itemsPerPage));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getItemsPageNumberFromTopElement() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.getText(vNexBOUsersWebPage.topItemsPerPageField);
    }

    public static String getItemsPageNumberFromBottomElement() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.getText(vNexBOUsersWebPage.bottomItemsPerPageField);
    }

    public static String getSearchFilterText() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.getText(vNexBOUsersWebPage.filterInfoText);
    }

    public static void clearSearchFilter() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.clearSearchIcon);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openAdvancedSearchForm() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.searchFieldAdvancedSearchCaret);
    }

    public static void openUserDataForEdit() {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clickElement(vNexBOUsersWebPage.editUserButton);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByEmail(String email) {
        openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setEmailField(email);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByPhone(String phone) {
        openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.setPhoneField(phone);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByName(String userName) {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Utils.clearAndType(vNexBOUsersWebPage.searchField, userName);
        Utils.clickElement(vNexBOUsersWebPage.searchIcon);
        WaitUtilsWebDriver.waitForLoading();
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
}