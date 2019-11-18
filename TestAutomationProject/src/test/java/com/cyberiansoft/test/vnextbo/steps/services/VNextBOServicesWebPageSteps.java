package com.cyberiansoft.test.vnextbo.steps.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.services.VNextBOServiceData;
import com.cyberiansoft.test.vnextbo.screens.services.VNextBOServicesWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;

public class VNextBOServicesWebPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickAddNewServiceButton() {

        Utils.clickElement(new VNextBOServicesWebPage().getAddNewServiceTab());
    }

    public static void openAllTab() {

        Utils.clickElement(new VNextBOServicesWebPage().getAllTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openLaborTab() {

        Utils.clickElement(new VNextBOServicesWebPage().getLaborTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openPartsTab() {

        Utils.clickElement(new VNextBOServicesWebPage().getPartsTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getServiceRecordSpecificColumnValue(String serviceName, String columnTitle) {

        return Utils.getText(new VNextBOServicesWebPage().serviceRecordSpecificColumnValue(serviceName, columnTitle));
    }

    public static String getServiceRecordPriceTypeIconTitle(String serviceName) {

        return new VNextBOServicesWebPage().serviceRecordPriceTypeIcon(serviceName).getAttribute("title");
    }

    public static void clickEditButtonForService(String serviceName) {

        Utils.clickElement(new VNextBOServicesWebPage().editServiceButtonByName(serviceName));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickDeleteButtonForService(String serviceName) {

        Utils.clickElement(new VNextBOServicesWebPage().deleteServiceButtonByName(serviceName));
    }

    public static void clickRestoreButtonForService(String serviceName) {

        Utils.clickElement(new VNextBOServicesWebPage().restoreServiceButtonByName(serviceName));
    }

    public static void changeOrderNumberByServiceName(String serviceName, String newOrderNumber) {

        VNextBOServicesWebPage servicesWebPage = new VNextBOServicesWebPage();
        Utils.clickElement(servicesWebPage.orderNumberFieldByName(serviceName));
        Utils.sendKeysWithJS(servicesWebPage.orderNumberFieldByName(serviceName), newOrderNumber);
        Utils.clickElement(servicesWebPage.getAllTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void deleteServiceByName(String serviceName) {

        clickDeleteButtonForService(serviceName);
        VNextBOModalDialogSteps.clickOkButton();
    }

    public static void restoreServiceByName(String serviceName) {

        clickRestoreButtonForService(serviceName);
        VNextBOModalDialogSteps.clickOkButton();
    }

    public static void addMoneyOrPercentageService(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setNewMoneyOrPercentageServiceData(serviceData);
    }

    public static void editMoneyOrPercentageService(String serviceName, VNextBOServiceData newServiceData) {

        clickEditButtonForService(serviceName);
        VNextBOServiceDialogSteps.editMoneyOrPercentageServiceData(newServiceData);
    }

    public static void addLaborService(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setLaborServiceData(serviceData);
        VNextBOServiceDialogSteps.clickSaveButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void addBasePartService(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setBaseServiceData(serviceData, true);
        VNextBOServiceDialogSteps.clickSaveButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void addPartServiceWithAllData(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setAllPartServiceData(serviceData);
        VNextBOServiceDialogSteps.clickSaveButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void editLaborService(String serviceName, VNextBOServiceData newServiceData) {

        clickEditButtonForService(serviceName);
        VNextBOServiceDialogSteps.setLaborServiceData(newServiceData);
        VNextBOServiceDialogSteps.clickSaveButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void editPartServiceWithAllData(String serviceName, VNextBOServiceData newServiceData) {

        clickEditButtonForService(serviceName);
        VNextBOServiceDialogSteps.editAllPartServiceData(newServiceData);
        VNextBOServiceDialogSteps.clickSaveButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setLaborServiceDataAndCancel(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setLaborServiceData(serviceData);
        VNextBOServiceDialogSteps.closeServiceDialog();
        WaitUtilsWebDriver.waitForLoading();
    }
}