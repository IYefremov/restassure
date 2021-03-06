package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROAdvancedSearchDialogNew;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;

import java.util.Optional;

public class VNextBOROAdvancedSearchDialogStepsNew {

    public static void clickSearchButton() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getSearchButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void saveSearch() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getSaveButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clearAllEnteredValues() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getClearButton());
    }

    public static void deleteSavedSearch() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getDeleteButton());
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void deleteSavedSearchWithoutSaveNoButton() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getDeleteButton());
        VNextBOModalDialogSteps.clickNoButton();
    }

    public static void deleteSavedSearchWithoutSaveXIcon() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getDeleteButton());
        VNextBOModalDialogSteps.clickCloseButton();
    }

    public static void setHasThisTextField(String text) {

        Utils.clearAndType(new VNextBOROAdvancedSearchDialogNew().getHasThisTextInputField(), text);
    }

    public static void setCustomerField(String customer) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clearAndType(advancedSearchDialog.getCustomerInputField(), customer);
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getCustomerAutoCompleteList(),
                advancedSearchDialog.dropDownFieldOption(customer));
    }

    public static void setEmployeeField(String employee) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clearAndType(advancedSearchDialog.getEmployeeInputField(), employee);
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getEmployeeAutoCompleteList(),
                advancedSearchDialog.dropDownFieldOption(employee));
    }

    public static void setPhaseField(String phase) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getPhaseDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getPhaseDropDownList(),
                advancedSearchDialog.dropDownFieldOption(phase));
    }

    public static void setPhaseStatusField(String phaseStatus) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getPhaseStatusDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getPhaseStatusDropDownList(),
                advancedSearchDialog.dropDownFieldOption(phaseStatus));
    }

    public static void setTaskField(String task) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getTaskDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getTaskDropDownList(),
                advancedSearchDialog.dropDownFieldOption(task));
    }

    public static void setTaskStatusField(String taskStatus) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getTaskStatusDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getTaskStatusDropDownList(),
                advancedSearchDialog.dropDownFieldOption(taskStatus));
    }

    public static void setDepartmentField(String department) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getDepartmentDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getDepartmentDropDownList(),
                advancedSearchDialog.dropDownFieldOption(department));
    }

    public static void setWoTypeField(String woType) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getWoTypeDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getWoTypeDropDownList(),
                advancedSearchDialog.dropDownFieldOption(woType));
    }

    public static void setWoNumberField(String woNumber) {

        Utils.clearAndType(new VNextBOROAdvancedSearchDialogNew().getWoNumberInputField(), woNumber);
    }

    public static void setRoNumberField(String roNumber) {

        Utils.clearAndType(new VNextBOROAdvancedSearchDialogNew().getRoNumberInputField(), roNumber);
    }

    public static void setStockNumberField(String stockNumber) {

        Utils.clearAndType(new VNextBOROAdvancedSearchDialogNew().getStockNumberInputField(), stockNumber);
    }

    public static void setVinNumberField(String vinNumber) {

        Utils.clearAndType(new VNextBOROAdvancedSearchDialogNew().getVinInputField(), vinNumber);
    }

    public static void setTimeFrameField(String timeFrame) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getTimeFrameDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getTimeFrameDropDownList(),
                advancedSearchDialog.dropDownFieldOption(timeFrame));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void setFromField(String fromDate) {

        Utils.sendKeysWithJS(new VNextBOROAdvancedSearchDialogNew().getFromInputField(), fromDate);
    }

    public static void setToField(String toDate) {

        Utils.sendKeysWithJS(new VNextBOROAdvancedSearchDialogNew().getToInputField(), toDate);
    }

    public static void setRepairStatusField(String repairStatus) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getRepairStatusDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getRepairStatusDropDownList(),
                advancedSearchDialog.dropDownFieldOption(repairStatus));
    }

    public static void setDaysInPhase(String daysInPhaseCondition, String daysFromValue, Optional<String> daysToValue) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getDaysInPhaseDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getDaysInPhaseDropDownList(),
                advancedSearchDialog.dropDownFieldOption(daysInPhaseCondition));
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clearAndType(advancedSearchDialog.getDaysInPhaseFromValue(), daysFromValue);
        WaitUtilsWebDriver.waitABit(1000);
        if (daysToValue != null)
            Utils.sendKeysWithJS(advancedSearchDialog.getDaysInPhaseToValue(), String.valueOf(daysToValue));
    }

    public static void setDaysInProcess(String daysInProcessCondition, String daysFromValue, Optional<String> daysToValue) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getDaysInProcessDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getDaysInProcessDropDownList(),
                advancedSearchDialog.dropDownFieldOption(daysInProcessCondition));
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clearAndType(advancedSearchDialog.getDaysInProcessFromValue(), daysFromValue);
        WaitUtilsWebDriver.waitABit(1000);
        if (daysToValue != null)
            Utils.sendKeysWithJS(advancedSearchDialog.getDaysInProcessToValue(), String.valueOf(daysToValue));
    }

    public static void setFlagField(String flag) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getFlagDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getFlagDropDownList(),
                advancedSearchDialog.flagDropDownFieldOption(flag));
    }

    public static void setSortByField(String sortBy) {

        VNextBOROAdvancedSearchDialogNew advancedSearchDialog = new VNextBOROAdvancedSearchDialogNew();
        Utils.clickElement(advancedSearchDialog.getSortByDropDown());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getSortByDropDownList(),
                advancedSearchDialog.dropDownFieldOption(sortBy));
    }

    public static void setSearchNameField(String searchName) {

        Utils.clearAndType(new VNextBOROAdvancedSearchDialogNew().getSearchNameInputField(), searchName);
    }

    public static void clickHasProblemCheckBox() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getHasProblemCheckBox());
    }

    public static void closeDialog() {

        Utils.clickElement(new VNextBOROAdvancedSearchDialogNew().getCloseButton());
    }

    public static void setCustomTimeFrame(String fromDate, String toDate) {

        setTimeFrameField("Custom");
        setFromField(fromDate);
        setToField(toDate);
    }

    public static void setAllFields(VNextBOMonitorData data) {

        setHasThisTextField(data.getHasThisText());
        setCustomerField(data.getCustomer());
        setEmployeeField(data.getEmployee());
        setPhaseField(data.getPhase());
        setPhaseStatusField(data.getPhaseStatus());
        setTaskField(data.getTask());
        setTaskStatusField(data.getTaskStatus());
        setDepartmentField(data.getDepartment());
        setWoTypeField(data.getWoType());
        setWoNumberField(data.getWoNum());
        setRoNumberField(data.getRoNum());
        setStockNumberField(data.getStockNum());
        setVinNumberField(data.getVinNum());
        setTimeFrameField(data.getTimeFrame());
        setRepairStatusField(data.getRepairStatus());
        setDaysInProcess(data.getDaysInProcess(), data.getDaysNumStart(), java.util.Optional.ofNullable(data.getDaysNum()));
        setDaysInPhase(data.getDaysInPhase(), data.getDaysNumStart(), java.util.Optional.ofNullable(data.getDaysNum()));
        setFlagField(data.getFlag());
        setSortByField(data.getSortBy());
        clickHasProblemCheckBox();
        setSearchNameField(data.getSearchName());
    }
}
