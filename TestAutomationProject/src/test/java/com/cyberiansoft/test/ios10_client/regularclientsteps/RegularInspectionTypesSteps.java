package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularInspectionTypesSteps {

    public static void selectInspectionType(IInspectionsTypes inspectionType) {
        FluentWait<WebDriver> wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("InspectionTypeSelector")));
        IOSElement insptypetable = (IOSElement) DriverBuilder.getInstance().getAppiumDriver().findElement(MobileBy.iOSNsPredicateString("name = 'InspectionTypeSelector' and type = 'XCUIElementTypeTable'"));
        if (!insptypetable.findElementByAccessibilityId(inspectionType.getInspectionTypeName()).isDisplayed()) {
            SwipeUtils.swipeToElement(insptypetable.findElementByAccessibilityId(inspectionType.getInspectionTypeName()));
        }
        DriverBuilder.getInstance().getAppiumDriver().findElement(MobileBy.AccessibilityId(inspectionType.getInspectionTypeName())).click();
    }
}
