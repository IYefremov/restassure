package com.cyberiansoft.test.vnextbo.steps.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.services.VNextBOServiceData;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.services.VNextBOServiceDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOServiceDialogSteps extends VNextBOBaseWebPageSteps {

    public static void clickSaveButton() {

        Utils.clickElement(new VNextBOServiceDialog().getSaveButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void closeServiceDialog() {

        Utils.clickElement(new VNextBOServiceDialog().getCloseButton());
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.invisibilityOf(new VNextBOServiceDialog().getCloseButton()));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void setServiceName(String serviceName) {

        Utils.clearAndType(new VNextBOServiceDialog().getServiceNameField(), serviceName);
    }

    private static void setServiceType(String serviceType) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getServiceTypeDropDownField());
        Utils.selectOptionInDropDownWithJs(addNewServiceDialog.getServiceTypeDropDown(),
                addNewServiceDialog.dropDownFieldOption(serviceType));
    }

    private static void setServiceDescription(String serviceDescription) {

        Utils.clearAndType(new VNextBOServiceDialog().getDescriptionTextArea(), serviceDescription);
    }

    private static void setPriceType(String priceType) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getPriceTypeField());
        Utils.selectOptionInDropDownWithJs(addNewServiceDialog.getPriceTypeDropDown(),
                addNewServiceDialog.dropDownFieldOption(priceType));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    private static void setMoneyServicePrice(String price) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getMoneyPriceField());
        Utils.sendKeysWithJS(addNewServiceDialog.getMoneyPriceFieldToBeEdited(), price);
        Utils.clickElement(addNewServiceDialog.getPriceFieldLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewServiceDialog.getMoneyPriceField(), price);
    }

    private static void setPercentageServicePrice(String price) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getPercentagePriceField());
        Utils.sendKeysWithJS(addNewServiceDialog.getPercentagePriceFieldToBeEdited(), price);
        Utils.clickElement(addNewServiceDialog.getPriceFieldLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewServiceDialog.getPercentagePriceField(), price);
    }

    private static void setClarification(String clarification) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getClarificationField());
        Utils.selectOptionInDropDownWithJs(addNewServiceDialog.getClarificationFieldDropDown(),
                addNewServiceDialog.dropDownFieldOption(clarification));
    }

    private static void setClarificationPrefix(String clarificationPrefix) {

        Utils.clearAndType(new VNextBOServiceDialog().getClarificationPrefixField(), clarificationPrefix);
    }

    private static void setCategory(String category) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getCategoryField());
        Utils.selectOptionInDropDownWithJs(addNewServiceDialog.getCategoryFieldDropDown(),
                addNewServiceDialog.dropDownFieldOption(category));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    private static void setSubCategory(String subCategory) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getSubCategoryField());
        Utils.selectOptionInDropDownWithJs(addNewServiceDialog.getSubCategoryFieldDropDown(),
                addNewServiceDialog.dropDownFieldOption(subCategory));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    private static void setPartName(String partName) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getPartNameField());
        Utils.selectOptionInDropDownWithJs(addNewServiceDialog.getPartNameFieldDropDown(),
                addNewServiceDialog.dropDownFieldOption(partName));
    }

    private static void setLaborRate(String laborRate) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getLaborRateField());
        Utils.sendKeysWithJS(addNewServiceDialog.getLaborRateFieldToBeEdited(), laborRate);
        Utils.clickElement(addNewServiceDialog.getLaborRateFieldLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewServiceDialog.getLaborRateField(), laborRate);
    }

    private static void setDefaultLaborTime(String defaultLaborTime) {

        VNextBOServiceDialog addNewServiceDialog = new VNextBOServiceDialog();
        Utils.clickElement(addNewServiceDialog.getDefaultLaborTimeField());
        Utils.sendKeysWithJS(addNewServiceDialog.getDefaultLaborTimeFieldToBeEdited(), defaultLaborTime);
        Utils.clickElement(addNewServiceDialog.getLaborRateFieldLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewServiceDialog.getDefaultLaborTimeField(), defaultLaborTime);
    }

    public static void clickUseLaborTimesCheckBox() {

        Utils.clickElement(new VNextBOServiceDialog().getUseLaborTimesCheckbox());
    }

    private static void setClarificationFields(VNextBOServiceData serviceData) {

        if (serviceData.getServiceClarification() != null) {
            setClarification(serviceData.getServiceClarification());
            if (serviceData.getServiceClarificationPrefix() != null) {
                setClarificationPrefix(serviceData.getServiceClarificationPrefix());
            }
        }
    }

    public static void clickAddButton() {

        Utils.clickElement(new VNextBOServiceDialog().getAddButton());
    }

    public static void setBaseServiceData(VNextBOServiceData serviceData, boolean newService) {

        if (newService) setPriceType(serviceData.getServicePriceType());
        setServiceName(serviceData.getServiceName());
        setServiceType(serviceData.getServiceType());
        setServiceDescription(serviceData.getServiceDescription());
    }

    public static void setNewMoneyOrPercentageServiceData(VNextBOServiceData serviceData) {

        setBaseServiceData(serviceData, true);
        if (serviceData.getServicePriceType().equals("Money")) setMoneyServicePrice(serviceData.getServicePrice());
        if (serviceData.getServicePriceType().equals("Percentage")) setPercentageServicePrice(serviceData.getServicePrice());
        setClarificationFields(serviceData);
        clickSaveButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void setLaborServiceData(VNextBOServiceData serviceData) {

        setBaseServiceData(serviceData, true);
        setLaborRate(serviceData.getServiceLaborRate());
        setDefaultLaborTime(serviceData.getServiceDefaultLaborTime());
        if (serviceData.getServiceUseSelectedLaborTimes().equals("true")) clickUseLaborTimesCheckBox();
    }

    public static void setAllPartServiceData(VNextBOServiceData serviceData) {

        setBaseServiceData(serviceData, true);
        setCategory(serviceData.getServiceCategory());
        setSubCategory(serviceData.getServiceSubCategory());
        setPartName(serviceData.getServicePartName());
        clickAddButton();
    }

    public static void editMoneyOrPercentageServiceData(VNextBOServiceData serviceData) {

        setBaseServiceData(serviceData, false);
        if (serviceData.getServicePriceType().equals("Money")) setMoneyServicePrice(serviceData.getServicePrice());
        if (serviceData.getServicePriceType().equals("Percentage")) setPercentageServicePrice(serviceData.getServicePrice());
        setClarificationFields(serviceData);
        clickSaveButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void editAllPartServiceData(VNextBOServiceData serviceData) {

        setBaseServiceData(serviceData, false);
        setCategory(serviceData.getServiceCategory());
        setSubCategory(serviceData.getServiceSubCategory());
        setPartName(serviceData.getServicePartName());
        clickAddButton();
    }
}