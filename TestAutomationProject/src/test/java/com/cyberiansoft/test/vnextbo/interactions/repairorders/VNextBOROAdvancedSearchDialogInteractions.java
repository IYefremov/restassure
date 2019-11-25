package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROAdvancedSearchDialog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Arrays;
import java.util.List;

public class VNextBOROAdvancedSearchDialogInteractions {

    public static void setTimeFrame(String timeFrame) {
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = new VNextBOROAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getTimeFrameListBox());
        Utils.selectOptionInDropDown(advancedSearchDialog.getTimeFrameDropDown(),
                advancedSearchDialog.getTimeFrameListBoxOptions(), timeFrame, true);
    }

    public static void setPhase(String phase) {
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = new VNextBOROAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getPhaseListBox());
        Utils.selectOptionInDropDown(
                advancedSearchDialog.getPhaseDropDown(), advancedSearchDialog.getPhaseListBoxOptions(), phase);
    }

    public static void setPhaseStatus(String phaseStatus) {
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = new VNextBOROAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getPhaseStatusListBox());
        Utils.selectOptionInDropDown(advancedSearchDialog.getPhaseStatusDropDown(),
                advancedSearchDialog.getPhaseStatusListBoxOptions(), phaseStatus);
    }

    public static void clickSearchButton() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getSearchButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void closeAdvancedSearchDialog() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getAdvancedSearchCloseButton());
    }

    public static String getCustomerInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getCustomerInputField());
    }

    public static String getEmployeeInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getEmployeeInputField());
    }

    public static String getPhaseInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getPhaseSelection());
    }

    public static String getPhaseStatusInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getPhaseStatusSelection());
    }

    public static String getDepartmentInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getDepartmentSelection());
    }

    public static String getWoTypeInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getWoTypeSelection());
    }

    public static String getWoNumInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getWoInputField());
    }

    public static String getRoNumInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getRoInputField());
    }

    public static String getStockInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getStockInputField());
    }

    public static String getVinInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getVinInputField());
    }

    public static String getTimeFrameInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getTimeFrameSelection());
    }

    public static String getRepairStatusInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getRepairStatusSelection());
    }

    public static String getDaysInProcessInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getDaysInProcessSelection());
    }

    public static String getDaysInPhaseInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getDaysInPhaseSelection());
    }

    public static String getFlagInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getFlagSelection());
    }

    public static String getSortByInputFieldValue() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getSortBySelection());
    }

    public static String getSearchNameInputFieldValue() {
        return Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialog().getSearchNameInputField());
    }
    public static void setWoNum(String woNum) {
        Utils.clearAndType(new VNextBOROAdvancedSearchDialog().getWoInputField(), woNum);
    }

    public static void setRoNum(String roNum) {
        Utils.clearAndType(new VNextBOROAdvancedSearchDialog().getRoInputField(), roNum);
    }

    public static void setStockNum(String stockNum) {
        Utils.clearAndType(new VNextBOROAdvancedSearchDialog().getStockInputField(), stockNum);
    }

    public static void setVinNum(String vinNum) {
        Utils.clearAndType(new VNextBOROAdvancedSearchDialog().getVinInputField(), vinNum);
    }

    public static void setSearchName(String searchName) {
        Utils.clearAndType(new VNextBOROAdvancedSearchDialog().getSearchNameInputField(), searchName);
    }

    public static void selectCustomerNameFromBoxList(String customer) {
        Utils.selectDataFromBoxList(new VNextBOROAdvancedSearchDialog().getCustomerListBoxOptions(),
                new VNextBOROAdvancedSearchDialog().getCustomerAutoCompleteList(), customer);
    }

    public static void selectEmployeeNameFromBoxList(String employee) {
        Utils.selectDataFromBoxList(new VNextBOROAdvancedSearchDialog().getEmployeeListBoxOptions(),
                new VNextBOROAdvancedSearchDialog().getEmployeeAutoCompleteList(), employee);
    }

    public static void typeEmployeeName(String employee) {
        Utils.clearAndType(new VNextBOROAdvancedSearchDialog().getEmployeeInputField(), employee);
    }

    public static void typeCustomerName(String customer) {
        Utils.clearAndType(new VNextBOROAdvancedSearchDialog().getCustomerInputField(), customer);
    }

    public static void setCustomer(String customer) {
        typeCustomerName(customer);
        selectCustomerNameFromBoxList(customer);
    }

    public static void setEmployee(String employee) {
        typeEmployeeName(employee);
        selectEmployeeNameFromBoxList(employee);
    }

    public static void setDepartment(String department) {
        clickDepartmentBox();
        selectDepartment(department);
    }

    public static void setWoType(String woType) {
        clickWoTypeBox();
        selectWoType(woType);
    }

    public static void setDaysInPhase(String daysInPhase) {
        clickDaysInPhaseBox();
        selectDaysInPhase(daysInPhase);
    }

    public static void setFlag(String flag) {
        clickFlagBox();
        selectFlag(flag);
    }

    public static String getFlagSelected() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getFlagDisplayed());
    }

    public static void setDaysInProcess(String daysInProcess) {
        clickDaysInProcessBox();
        selectDaysInProcess(daysInProcess);
    }

    public static void setRepairStatus(String repairStatus) {
        clickRepairStatusBox();
        selectRepairStatus(repairStatus);
    }

    public static void typeNumberOfDaysForDaysInPhaseFromInput(String days) {
        typeDays(new VNextBOROAdvancedSearchDialog().getDaysInPhaseFromInput(), days);
    }

    public static void typeNumberOfDaysForDaysInPhaseToInput(String days) {
        typeDays(new VNextBOROAdvancedSearchDialog().getDaysInPhaseToInput(), days);
    }

    public static void typeNumberOfDaysForDaysInProcessFromInput(String days) {
        typeDays(new VNextBOROAdvancedSearchDialog().getDaysInProcessFromInput(), days);
    }

    public static void typeNumberOfDaysForDaysInProcessToInput(String days) {
        typeDays(new VNextBOROAdvancedSearchDialog().getDaysInProcessToInput(), days);
    }

    private static void typeDays(WebElement daysInPhaseToInput, String days) {
        Utils.clearAndType(daysInPhaseToInput, days);
    }

    public static void clickFromDateButton() {
        openCalendarWidget(new VNextBOROAdvancedSearchDialog().getFromDateButton());
    }

    public static void clickToDateButton() {
        openCalendarWidget(new VNextBOROAdvancedSearchDialog().getToDateButton());
    }

    private static void openCalendarWidget(WebElement button) {
        Utils.clickElement(button);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void clickSaveButton() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getSaveButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickClearButton() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getClearButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickDeleteButton() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getDeleteButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    private static void clickPhaseBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getPhaseListBox());
    }

    private static void clickDepartmentBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getDepartmentListBox());
    }

    private static void clickWoTypeBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getWoTypeListBox());
    }

    private static void clickDaysInPhaseBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getDaysInPhaseListBox());
    }

    private static void clickFlagBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getFlagsListBox());
    }

    private static void clickDaysInProcessBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getDaysInProcessListBox());
    }

    private static void clickTimeFrameBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getTimeFrameListBox());
    }

    private static void clickRepairStatusBox() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getRepairStatusListBox());
    }

    private static void selectPhase(String phase) {
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getPhaseDropDown(),
                new VNextBOROAdvancedSearchDialog().getPhaseListBoxOptions(), phase);
    }

    private static void selectDepartment(String phase) {
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getDepartmentDropDown(),
                new VNextBOROAdvancedSearchDialog().getDepartmentListBoxOptions(), phase);
    }

    private static void selectWoType(String woType) {
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getWoTypeDropDown(),
                new VNextBOROAdvancedSearchDialog().getWoTypeListBoxOptions(), woType);
    }

    private static void selectDaysInPhase(String daysInPhase) {
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getDaysInPhaseDropDown(),
                new VNextBOROAdvancedSearchDialog().getDaysInPhaseListBoxOptions(), daysInPhase);
    }

    private static void selectFlag(String flag) {
//        wait.until(ExpectedConditions.visibilityOf(flagDropDown));
//        final Select select = new Select(flagDropDown);
//        select.selectByVisibleText(flag);
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getFlagDropDown(),
                new VNextBOROAdvancedSearchDialog().getFlagsListBoxOptions(), flag, true);
    }

    public static void clickAdvancedSearchCloseButton() {
        Utils.clickElement(new VNextBOROAdvancedSearchDialog().getAdvancedSearchCloseButton());
    }

    private static void selectDaysInProcess(String daysInProcess) {
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getDaysInProcessDropDown(),
                new VNextBOROAdvancedSearchDialog().getDaysInProcessListBoxOptions(), daysInProcess);
    }

    private static void selectTimeFrame(String timeFrame) {
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getTimeFrameDropDown(),
                new VNextBOROAdvancedSearchDialog().getTimeFrameListBoxOptions(), timeFrame, true);
    }

    private static void selectRepairStatus(String repairStatus) {
        Utils.selectOptionInDropDown(new VNextBOROAdvancedSearchDialog().getRepairStatusDropDown(),
                new VNextBOROAdvancedSearchDialog().getRepairStatusListBoxOptions(), repairStatus);
    }

    public static String getRepairStatusOption() {
        return Utils.getText(new VNextBOROAdvancedSearchDialog().getRepairStatusValue());
    }

    public static List<String> getAdvancedSearchDialogElementsValues() {
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = new VNextBOROAdvancedSearchDialog();
        final WebElement customerSelection = advancedSearchDialog.getCustomerSelection();
        final String text = Utils.getText(customerSelection);
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver
                    -> !text.equals(customerSelection.getText()));
        } catch (Exception ignored) {}
        return Arrays.asList(customerSelection.getText(),
                advancedSearchDialog.getEmployeeSelection().getText(),
                advancedSearchDialog.getPhaseSelection().getText(),
                advancedSearchDialog.getTimeFrameSelection().getText(),
                advancedSearchDialog.getDepartmentSelection().getText(),
                advancedSearchDialog.getRepairStatusSelection().getText(),
                advancedSearchDialog.getDaysInProcessSelection().getText(),
                advancedSearchDialog.getDaysInPhaseSelection().getText(),
                advancedSearchDialog.getFlagSelection().getText());
    }
}