package com.cyberiansoft.test.vnextbo.steps.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.services.VNextBOServiceData;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.services.VNextBOServiceDialog;
import com.cyberiansoft.test.vnextbo.screens.services.VNextBOServicesWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOServicesWebPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickAddNewServiceButton() {

        Utils.clickElement(new VNextBOServicesWebPage().getAddNewServiceTab());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void openAllTab() {

        Utils.clickElement(new VNextBOServicesWebPage().getAllTab());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void openLaborTab() {

        Utils.clickElement(new VNextBOServicesWebPage().getLaborTab());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void openPartsTab() {

        Utils.clickElement(new VNextBOServicesWebPage().getPartsTab());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static String getServiceRecordSpecificColumnValue(String serviceName, String columnTitle) {
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.visibilityOfAllElements(new VNextBOServicesWebPage().serviceRecordSpecificColumnValue(serviceName, columnTitle)));
        return Utils.getText(new VNextBOServicesWebPage().serviceRecordSpecificColumnValue(serviceName, columnTitle));
    }

    public static String getServiceRecordPriceTypeIconTitle(String serviceName) {

        return new VNextBOServicesWebPage().serviceRecordPriceTypeIcon(serviceName).getAttribute("title");
    }

    public static void clickEditButtonForService(String serviceName) {
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.elementToBeClickable(new VNextBOServicesWebPage().editServiceButtonByName(serviceName)));
        Utils.clickElement(new VNextBOServicesWebPage().editServiceButtonByName(serviceName));
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.elementToBeClickable(new VNextBOServiceDialog().getServiceNameField()));
    }

    public static void clickDeleteButtonForService(String serviceName) {
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.elementToBeClickable(new VNextBOServicesWebPage().deleteServiceButtonByName(serviceName)));
        Utils.clickElement(new VNextBOServicesWebPage().deleteServiceButtonByName(serviceName));
    }

    public static void clickRestoreButtonForService(String serviceName) {
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.elementToBeClickable(new VNextBOServicesWebPage().restoreServiceButtonByName(serviceName)));
        Utils.clickElement(new VNextBOServicesWebPage().restoreServiceButtonByName(serviceName));
    }

    public static void changeOrderNumberByServiceName(String serviceName, String newOrderNumber) {

        VNextBOServicesWebPage servicesWebPage = new VNextBOServicesWebPage();
        Utils.clickElement(servicesWebPage.orderNumberFieldByName(serviceName));
        Utils.sendKeysWithJS(servicesWebPage.orderNumberFieldByName(serviceName), newOrderNumber);
        Utils.clickElement(servicesWebPage.getAllTab());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
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
    }

    public static void addBasePartService(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setBaseServiceData(serviceData, true);
        VNextBOServiceDialogSteps.clickSaveButton();
    }

    public static void addPartServiceWithAllData(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setAllPartServiceData(serviceData);
        VNextBOServiceDialogSteps.clickSaveButton();
    }

    public static void editLaborService(String serviceName, VNextBOServiceData newServiceData) {

        clickEditButtonForService(serviceName);
        VNextBOServiceDialogSteps.setLaborServiceData(newServiceData);
        VNextBOServiceDialogSteps.clickSaveButton();
    }

    public static void editPartServiceWithAllData(String serviceName, VNextBOServiceData newServiceData) {

        clickEditButtonForService(serviceName);
        VNextBOServiceDialogSteps.editAllPartServiceData(newServiceData);
        VNextBOServiceDialogSteps.clickSaveButton();
    }

    public static void setLaborServiceDataAndCancel(VNextBOServiceData serviceData) {

        clickAddNewServiceButton();
        VNextBOServiceDialogSteps.setLaborServiceData(serviceData);
        VNextBOServiceDialogSteps.closeServiceDialog();
    }
}