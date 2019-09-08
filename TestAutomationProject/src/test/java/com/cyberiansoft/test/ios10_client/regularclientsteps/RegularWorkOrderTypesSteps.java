package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularWorkOrderTypesSteps {

    public static void selectWorkOrderType(IWorkOrdersTypes workordertype) {
        WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 10);
        IOSElement woTypeTable = (IOSElement)  wait.until(ExpectedConditions.
                presenceOfElementLocated(MobileBy.iOSNsPredicateString("type='XCUIElementTypeTable' and name='OrderTypeSelector'")));
        if (!woTypeTable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).isDisplayed()) {
            SwipeUtils.swipeToElement(woTypeTable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()));
        }
        woTypeTable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).click();
    }
}
