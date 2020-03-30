package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOAddNewServiceDialog;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOAddNewServiceDialogValidations;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOAddNewServiceDialogSteps {

    private static void setPriceType(String priceType) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getPriceTypeDropDown());
        Utils.clickWithJS(addNewServiceDialog.priceTypeDropDownOption(priceType));
    }

    private static void setService(String service) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getServiceDropDownArrow());
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(addNewServiceDialog.dropDownOption(service)));
        Utils.clickWithJS(addNewServiceDialog.dropDownOption(service));
    }

    private static void setDescription(String description) {

        Utils.clearAndType(new VNextBOAddNewServiceDialog().getDescriptionTextArea(), description);
    }

    private static void setPrice(String price) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getPriceVisibleInputField());
        Utils.clear(addNewServiceDialog.getPriceInvisibleInputField());
        WaitUtilsWebDriver.waitForElementToBeClickable(addNewServiceDialog.getPriceVisibleInputField()).sendKeys(price);
        Utils.clickElement(addNewServiceDialog.getDescriptionTextArea());
    }

    private static void setQuantity(String quantity) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getQuantityVisibleInputField());
        Utils.clear(addNewServiceDialog.getQuantityInvisibleInputField());
        WaitUtilsWebDriver.waitForElementToBeClickable(addNewServiceDialog.getQuantityVisibleInputField()).sendKeys(quantity);
        Utils.clickElement(addNewServiceDialog.getDescriptionTextArea());
    }

    private static void setLaborRate(String rate) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getLaborRateVisibleInputField());
        Utils.clear(addNewServiceDialog.getLaborRateInvisibleInputField());
        WaitUtilsWebDriver.waitForElementToBeClickable(addNewServiceDialog.getLaborRateVisibleInputField()).sendKeys(rate);
        Utils.clickElement(addNewServiceDialog.getDescriptionTextArea());
    }

    private static void setLaborTime(String time) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getLaborTimeVisibleInputField());
        Utils.clear(addNewServiceDialog.getLaborTimeInvisibleInputField());
        WaitUtilsWebDriver.waitForElementToBeClickable(addNewServiceDialog.getLaborTimeVisibleInputField()).sendKeys(time);
        Utils.clickElement(addNewServiceDialog.getDescriptionTextArea());
    }

    private static void submitService() {

        Utils.clickElement(new VNextBOAddNewServiceDialog().getSubmitButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void closeDialogWithXIcon() {

        Utils.clickElement(new VNextBOAddNewServiceDialog().getCloseXIconButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    private static void cancelServiceAdding() {

        Utils.clickElement(new VNextBOAddNewServiceDialog().getCancelButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    private static void setCategory(String category) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getCategoryDropDownField());
        Utils.clickWithJS(addNewServiceDialog.dropDownOption(category));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    private static void setSubCategory(String subCategory) {

        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog();
        Utils.clickElement(addNewServiceDialog.getSubCategoryDropDownField());
        Utils.clickWithJS(addNewServiceDialog.dropDownOption(subCategory));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    private static void addPartFromTheList(String partName) {

        Utils.clickElement(new VNextBOAddNewServiceDialog().partListItem(partName));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    private static void allServicesTypesSteps(VNextBOMonitorData serviceData) {

        setPriceType(serviceData.getPriceType());
        setService(serviceData.getService());
        setDescription(serviceData.getServiceDescription());
    }

    public static void addService(VNextBOMonitorData serviceData) {

        allServicesTypesSteps(serviceData);
        setPrice(serviceData.getServicePrice());
        setQuantity(serviceData.getServiceQuantity());
        VNextBOAddNewServiceDialogValidations.verifyAllFieldsAreSetCorrectly(serviceData);
        submitService();
    }

    public static void populateServiceDataAndClickXIcon(VNextBOMonitorData serviceData) {

        allServicesTypesSteps(serviceData);
        setPrice(serviceData.getServicePrice());
        setQuantity(serviceData.getServiceQuantity());
        closeDialogWithXIcon();
    }

    public static void populateServiceDataAndClickCancelButton(VNextBOMonitorData serviceData) {

        allServicesTypesSteps(serviceData);
        setPrice(serviceData.getServicePrice());
        setQuantity(serviceData.getServiceQuantity());
        cancelServiceAdding();
    }

    public static void addLaborService(VNextBOMonitorData serviceData) {

        allServicesTypesSteps(serviceData);
        setLaborRate(serviceData.getServiceLaborRate());
        setLaborTime(serviceData.getServiceLaborTime());
        VNextBOAddNewServiceDialogValidations.verifyLaborServiceFieldsAreSetCorrectly(serviceData);
        submitService();
    }

    public static void addPartService(VNextBOMonitorData serviceData) {

        allServicesTypesSteps(serviceData);
        setCategory(serviceData.getServiceCategory());
        setSubCategory(serviceData.getServiceSubcategory());
        addPartFromTheList(serviceData.getServicePart());
        VNextBOAddNewServiceDialogValidations.verifyPartServiceFieldsAreSetCorrectly(serviceData);
        submitService();
    }
}
