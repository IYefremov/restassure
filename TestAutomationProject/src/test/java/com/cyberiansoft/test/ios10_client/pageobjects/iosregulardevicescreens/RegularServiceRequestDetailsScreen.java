package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularServiceRequestDetailsScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility = "ServiceRequestSummaryTable")
    private IOSElement serviceRequestSummaryTable;

    public RegularServiceRequestDetailsScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void waitForServiceRequestDetailsScreenLoaded() {
        WaitUtils.elementShouldBeVisible(serviceRequestSummaryTable, true);
    }

    public boolean isSRSummaryAppointmentsInformationExists() {
        return serviceRequestSummaryTable.findElementByAccessibilityId("Appointments").isDisplayed();
    }

    public boolean isSRSheduledAppointmentExists() {
        return serviceRequestSummaryTable.findElementByAccessibilityId(" SCHEDULED").isDisplayed();
    }

    public void clickServiceRequestSummaryInspectionsButton() {
        waitForServiceRequestDetailsScreenLoaded();
        serviceRequestSummaryTable.findElementByAccessibilityId("ServiceRequestSummaryInspectionsButton").click();
    }

    public void clickServiceRequestSummaryOrdersButton() {
        waitForServiceRequestDetailsScreenLoaded();
        serviceRequestSummaryTable.findElementByAccessibilityId("ServiceRequestSummaryOrdersButton").click();
    }

    public void clickServiceRequestSummaryInvoicesButton() {
        waitForServiceRequestDetailsScreenLoaded();
        serviceRequestSummaryTable.findElementByAccessibilityId("ServiceRequestSummaryInvoicesButton").click();
    }


}
