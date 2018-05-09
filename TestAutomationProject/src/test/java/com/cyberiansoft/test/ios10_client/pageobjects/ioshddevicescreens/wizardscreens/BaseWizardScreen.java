package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.appcontexts.WizardTypes;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public abstract class BaseWizardScreen extends iOSHDBaseScreen {

    protected static TypeScreenContext typeContext;

    public BaseWizardScreen(AppiumDriver driver, TypeScreenContext typeScreenContext) {
        super(driver);
        typeContext = typeScreenContext;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public <T extends BaseWizardScreen> T selectNextScreen(String screenname, WizardTypes wizardScreenType) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
        appiumdriver.findElementByAccessibilityId(screenname).click();
        switch (wizardScreenType) {
            case INVOICEINFO:
                return (T) new InvoiceInfoScreen(appiumdriver, typeContext);
        }
        return null;
    }

    public <T extends BaseTypeScreen> T getTypePageFromContext()  {
        switch (typeContext) {
            case WORKORDER:
                return (T) new MyWorkOrdersScreen(appiumdriver);
            case INSPECTION:
                return (T) new MyInspectionsScreen(appiumdriver);
            case INVOICE:
                return (T) new MyInvoicesScreen(appiumdriver);
            case SERVICEREQUEST:
                return (T) new ServiceRequestsScreen(appiumdriver);
            case TEAMWORKORDER:
                return (T) new TeamWorkOrdersScreen(appiumdriver);
            case TEAMINSPECTION:
                return (T) new TeamInspectionsScreen(appiumdriver);
        }
        return null;
    }

}

