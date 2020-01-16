package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOAdvancedSearchDialogSteps {

    public static void closeAdvancedSearchForm() {
        WebElement closeButton = new VNextBOAdvancedSearchDialog().getCloseButton();
        Utils.clickElement(closeButton);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.invisibilityOf(closeButton));
    }

    public static void setCustomerField(String customer) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Utils.clearAndType(advancedSearchDialog.getCustomerField(), customer);
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getCustomerFieldDropDown(),
                advancedSearchDialog.dropDownFieldOption(customer));
    }

    public static void setPhaseField(String phase) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getPhaseField());
        WaitUtilsWebDriver.waitABit(2000);
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getPhaseFieldDropDown(),
                advancedSearchDialog.dropDownFieldOption(phase));
    }

    public static void setWOTypeField(String wOType) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getWoTypeField());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getWoTypeFieldDropDown(),
                advancedSearchDialog.dropDownFieldOption(wOType));
    }

    public static void setRepairStatusField(String repairStatus) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getRepairStatusField());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getRepairStatusFieldDropDown(),
                advancedSearchDialog.dropDownFieldOption(repairStatus));
    }

    public static void setWONumberField(String wONumber) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getWoNumberField(), wONumber);
    }

    public static void setStockNumberField(String stockNumber) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getStockNumberField(), stockNumber);
    }

    public static void setEtaFromField(String etaFromDate) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getEtaFromDateField(), etaFromDate);
    }

    public static void setEtaToField(String etaToDate) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getEtaToDateField(), etaToDate);
    }

    public static void setVINField(String vinNumber) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getVinField(), vinNumber);
    }

    public static void setPartNumberField(String partNumber) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getPartNumberField(), partNumber);
    }

    public static void setNotesField(String notes) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getNotesField(), notes);
    }

    public static void setFromField(String fromDate) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getFromDateField(), fromDate);
    }

    public static void setToField(String toDate) {

        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getToDateField(), toDate);
    }

    public static void setOrderedFromField(String orderedFrom) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getOrderedFromField());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getOrderedFromFieldDropDown(),
                advancedSearchDialog.dropDownFieldOption(orderedFrom));
    }

    public static void setCorePriceCheckbox() {

        Utils.clickElement(new VNextBOAdvancedSearchDialog().getCorePriceCheckBox());
    }

    public static void setLaborCreditCheckbox() {

        Utils.clickElement(new VNextBOAdvancedSearchDialog().getLaborCreditCheckBox());
    }

    public static void setCoreStatusField(String coreStatus) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getCoreStatusField());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getCoreStatusFieldDropDown(),
                advancedSearchDialog.dropDownFieldOption(coreStatus));
    }

    public static void setSearchName(String searchName) {

        Utils.clickElement(new VNextBOAdvancedSearchDialog().getNotesField());
        Utils.clearAndType(new VNextBOAdvancedSearchDialog().getSearchNameField(), searchName);
    }

    public static void clickSearchButton() {

        Utils.clickElement(new VNextBOAdvancedSearchDialog().getSearchButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clearAllFields() {

        Utils.clickElement(new VNextBOAdvancedSearchDialog().getClearButton());
    }

    public static void saveSearch() {

        Utils.clickElement(new VNextBOAdvancedSearchDialog().getSaveButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clickDeleteButton() {

        Utils.clickElement(new VNextBOAdvancedSearchDialog().getDeleteButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void setAllFields(VNextBOPartsManagementSearchData searchData) {

        setCustomerField(searchData.getCustomer());
        if (!searchData.getPhase().equals("All")) setPhaseField(searchData.getPhase());
        if (!searchData.getWoType().equals("All")) setWOTypeField(searchData.getWoType());
        if (!searchData.getRepairStatus().equals("All")) setRepairStatusField(searchData.getRepairStatus());
        setWONumberField(searchData.getWoNum());
        setStockNumberField(searchData.getStockNum());
        setEtaFromField(searchData.getEtaFromDate());
        setEtaToField(searchData.getEtaToDate());
        setVINField(searchData.getVinNum());
        setPartNumberField(searchData.getPartNum());
        setNotesField(searchData.getNotes());
        setFromField(searchData.getFromDate());
        setToField(searchData.getToDate());
        setOrderedFromField(searchData.getOrderedFrom());
        setSearchName(searchData.getSearchName());
    }

    public static void deleteSavedSearch() {

        clickDeleteButton();
        VNextBOModalDialogSteps.clickYesButton();
        Utils.refreshPage();
    }
}
