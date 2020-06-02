package com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRSearchData;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.dialogs.VNextBOSRAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOReactSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOReactSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.dialogs.VNextBOSRAdvancedSearchDialogValidations;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class VNextBOSRAdvancedSearchDialogSteps {

    public static void setHasThisText(String value) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getHasThisTextField(), value);
    }

    public static void setCustomer(String value) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getCustomerField(), value);
    }

    public static void setCustomerUsingAutoComplete(String shortCustomerName, String customer) {
        setCustomer(shortCustomerName);
        VNextBOSRAdvancedSearchDialogValidations.verifyCustomersOptionsContainText(shortCustomerName);
        selectCustomerFromDropDown(customer);
    }

    public static List<String> getCustomersList() {
        final VNextBOSRAdvancedSearchDialog advancedSearchDialog = new VNextBOSRAdvancedSearchDialog();
        WaitUtilsWebDriver.waitForVisibility(advancedSearchDialog.getCustomerDropDown(), 4);
        return Utils.getText(advancedSearchDialog.getCustomerListBox());
    }

    private static void selectCustomerFromDropDown(String value) {
        final VNextBOSRAdvancedSearchDialog advancedSearchDialog = new VNextBOSRAdvancedSearchDialog();
        Utils.clickMatchingOptionInDropDown(advancedSearchDialog.getCustomerListBox(), value);
    }

    public static void setOwner(String value) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getOwnerField(), value);
    }

    public static void setOwnerUsingAutoComplete(String shortOwnerName, String owner) {
        setOwner(shortOwnerName);
        VNextBOSRAdvancedSearchDialogValidations.verifyOwnersOptionsContainText(shortOwnerName);
        selectOwnerFromDropDown(owner);
    }

    public static List<String> getOwnersList() {
        final VNextBOSRAdvancedSearchDialog advancedSearchDialog = new VNextBOSRAdvancedSearchDialog();
        WaitUtilsWebDriver.waitForVisibility(advancedSearchDialog.getOwnerDropDown(), 4);
        return Utils.getText(advancedSearchDialog.getOwnerListBox());
    }

    private static void selectOwnerFromDropDown(String value) {
        Utils.clickMatchingOptionInDropDown(new VNextBOSRAdvancedSearchDialog().getOwnerListBox(), value);
    }

    public static void setAdvisor(String value) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getAdvisorField(), value);
    }

    public static void setAdvisorUsingAutoComplete(String shortAdvisorName, String advisor) {
        setAdvisor(shortAdvisorName);
        VNextBOSRAdvancedSearchDialogValidations.verifyAdvisorsOptionsContainText(shortAdvisorName);
        selectAdvisorFromDropDown(advisor);
    }

    public static List<String> getAdvisorsList() {
        final VNextBOSRAdvancedSearchDialog advancedSearchDialog = new VNextBOSRAdvancedSearchDialog();
        return Utils.getText(advancedSearchDialog.getAdvisorListBox());
    }

    private static void selectAdvisorFromDropDown(String value) {
        Utils.clickMatchingOptionInDropDown(new VNextBOSRAdvancedSearchDialog().getAdvisorListBox(), value);
    }

    public static void setEmployee(String value) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getEmployeeField(), value);
    }

    public static void setEmployeeUsingAutoComplete(String shortEmployeesName, String employee) {
        setEmployee(shortEmployeesName);
        VNextBOSRAdvancedSearchDialogValidations.verifyEmployeesOptionsContainText(shortEmployeesName);
        selectEmployeeFromDropDown(employee);
    }

    public static List<String> getEmployeesList() {
        return Utils.getText(new VNextBOSRAdvancedSearchDialog().getEmployeeListBox());
    }

    private static void selectEmployeeFromDropDown(String value) {
        Utils.clickMatchingOptionInDropDown(new VNextBOSRAdvancedSearchDialog().getEmployeeListBox(), value);
    }

    public static void setTeam(String firstLetterOfTheTeam, String team) {
        final WebElement teamField = new VNextBOSRAdvancedSearchDialog().getTeamField();
        final Select select = new Select(teamField);
        Utils.clickElement(teamField);
        Utils.sendKeys(teamField, firstLetterOfTheTeam);
        waitForSelectedValueToBeUpdated(firstLetterOfTheTeam, select);
        VNextBOSRAdvancedSearchDialogValidations.verifySelectedTeamStartsWithLetter(firstLetterOfTheTeam);
        select.selectByVisibleText(team);
    }

    public static void setTeam(String team) {
        final WebElement teamField = new VNextBOSRAdvancedSearchDialog().getTeamField();
        WaitUtilsWebDriver.waitForElementToBeClickable(teamField, 2);
        new Select(teamField).selectByVisibleText(team);
    }

    public static void setSrType(String firstLetterOfTheType, String srType) {
        final WebElement srTypeField = new VNextBOSRAdvancedSearchDialog().getSrTypeField();
        final Select select = new Select(srTypeField);
        Utils.clickElement(srTypeField);
        Utils.sendKeys(srTypeField, firstLetterOfTheType);
        waitForSelectedValueToBeUpdated(firstLetterOfTheType, select);
        VNextBOSRAdvancedSearchDialogValidations.verifySelectedSRTypeStartsWithLetter(firstLetterOfTheType);
        select.selectByVisibleText(srType);
    }

    public static void setSrType(String team) {
        final WebElement srTypeField = new VNextBOSRAdvancedSearchDialog().getSrTypeField();
        WaitUtilsWebDriver.waitForElementToBeClickable(srTypeField, 2);
        new Select(srTypeField).selectByVisibleText(team);
    }

    private static void waitForSelectedValueToBeUpdated(String firstLetter, Select select) {
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                    select.getFirstSelectedOption().getText().startsWith(firstLetter));
        } catch (Exception ignored) {}
    }

    public static void clearHasThisTextField() {
        Utils.clearUsingKeyboard(new VNextBOSRAdvancedSearchDialog().getHasThisTextField());
    }

    public static void setHasThisTextFieldAndVerifySearchInputField(String value) {
        setHasThisText(value);
        VNextBOReactSearchPanelSteps.updateSearchInputFieldValue(value);
        VNextBOReactSearchPanelValidations.verifySearchInputFieldValue(value);
    }

    public static void setTimeFrame(TimeFrameValues timeFrame) {
        final WebElement timeFrameField = new VNextBOSRAdvancedSearchDialog().getTimeFrameField();
        WaitUtilsWebDriver.waitForElementToBeClickable(timeFrameField, 2);
        new Select(timeFrameField).selectByVisibleText(timeFrame.getName());
    }

    public static void setCustomTimeFrame(VNextBOSRSearchData searchData) {
        setTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM);
        setFromDateField(searchData.getFromDate());
        setToDateField(searchData.getToDate());
    }

    public static void setFromDateField(String from) {
        final VNextBOSRAdvancedSearchDialog advancedSearchDialog = new VNextBOSRAdvancedSearchDialog();
        Utils.clearUsingKeyboard(advancedSearchDialog.getFromDateField());
        Utils.sendKeys(advancedSearchDialog.getFromDateField(), from);
    }

    public static void setToDateField(String to) {
        final VNextBOSRAdvancedSearchDialog advancedSearchDialog = new VNextBOSRAdvancedSearchDialog();
        Utils.clearUsingKeyboard(advancedSearchDialog.getToDateField());
        Utils.sendKeys(advancedSearchDialog.getToDateField(), to);
    }

    public static void setStatus(String status) {
        final WebElement statusField = new VNextBOSRAdvancedSearchDialog().getStatusField();
        WaitUtilsWebDriver.waitForElementToBeClickable(statusField, 2);
        new Select(statusField).selectByVisibleText(status);
    }

    public static void setRoNum(String roNum) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getRoNumField(), roNum);
    }

    public static void setStockNum(String stockNum) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getStockNumField(), stockNum);
    }

    public static void setVinNum(String vinNum) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getVinNumField(), vinNum);
    }

    public static void setSrNum(String vinNum) {
        Utils.clearAndType(new VNextBOSRAdvancedSearchDialog().getSrNumField(), vinNum);
    }

    public static void search() {
        clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void clickSearchButton() {
        Utils.clickElement(new VNextBOSRAdvancedSearchDialog().getSearchButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clear() {
        clickClearButton();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed();
    }

    public static String getVinErrorMessage() {
        return Utils.getText(new VNextBOSRAdvancedSearchDialog().getVinNumError());
    }

    public static void clickClearButton() {
        Utils.clickElement(new VNextBOSRAdvancedSearchDialog().getClearButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }
}
