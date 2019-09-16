package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.IServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.UATServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class RegularServiceRequestSteps {

    public static void saveServiceRequest() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickSave();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.waitForServiceRequestScreenLoad();
    }

    public static void waitServiceRequestScreenLoaded() {
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.waitForServiceRequestScreenLoad();
    }

    public static void startCreatingServicerequest(AppCustomer appCustomer, IServiceRequestTypes serviceRequestTypes) {
        waitServiceRequestScreenLoaded();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.clickAddButton();
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
        RegularServiceRequestTypesSteps.selectServiceRequestType(UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
    }

    public static void startCreatingServicerequest(IServiceRequestTypes serviceRequestTypes) {
        waitServiceRequestScreenLoaded();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.clickAddButton();
        RegularServiceRequestTypesSteps.selectServiceRequestType(UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
    }

    public static void saveServiceRequestWithAppointment() {
        RegularWizardScreensSteps.clickSaveButton();
        FluentWait<WebDriver> wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeAlert")));
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        Assert.assertEquals(alert.getText(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        alert.accept();
    }

    public static String getFirstServiceRequestNumber() {
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        return serviceRequestsScreen.getFirstServiceRequestNumber();
    }

    public static void selectServiceRequest(String serviceRequestNumber) {
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
    }

    public static void startCreatingInspectionFromServiceRequest(String serviceRequestNumber, IInspectionsTypes inspectionType) {
        selectServiceRequest(serviceRequestNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_INPECTION);
        RegularInspectionTypesSteps.selectInspectionType(inspectionType);
    }

    public static void startCreatingWorkOrderFromServiceRequest(String serviceRequestNumber, IWorkOrdersTypes workOrdersType) {
        selectServiceRequest(serviceRequestNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_WORKORDER);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void openServiceRequestSummary(String serviceRequestNumber) {
        selectServiceRequest(serviceRequestNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SUMMARY);
    }

    public static void openServiceRequestDetails(String serviceRequestNumber) {
        selectServiceRequest(serviceRequestNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.DETAILS);
    }

    public static void clickServiceRequestCheckInAction(String serviceRequestNumber) {
        selectServiceRequest(serviceRequestNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHECK_IN);
        waitServiceRequestScreenLoaded();
    }
    public static void clickServiceRequestUndoCheckInAction(String serviceRequestNumber) {
        selectServiceRequest(serviceRequestNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.UNDO_CHECK_IN);
        waitServiceRequestScreenLoaded();
    }
}
