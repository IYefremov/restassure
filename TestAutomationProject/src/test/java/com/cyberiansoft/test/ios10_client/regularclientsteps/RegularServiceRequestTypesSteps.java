package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.IServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularServiceRequestTypesSteps {

    public static void selectServiceRequestType(IServiceRequestTypes serviceRequestTypes) {
        WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ServiceRequestTypeSelector")));
        IOSElement wostable = (IOSElement) DriverBuilder.getInstance().getAppiumDriver().findElement(MobileBy.iOSNsPredicateString("name = 'ServiceRequestTypeSelector' and type = 'XCUIElementTypeTable'"));

        if (!wostable.findElementByAccessibilityId(serviceRequestTypes.getServiceRequestTypeName()).isDisplayed()) {
            SwipeUtils.swipeToElement(wostable.findElementByAccessibilityId(serviceRequestTypes.getServiceRequestTypeName()));
        }
        wostable.findElementByAccessibilityId(serviceRequestTypes.getServiceRequestTypeName()).click();
    }
}
