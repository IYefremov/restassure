package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public abstract class BaseWizardScreen extends iOSHDBaseScreen {

    public static TypeScreenContext typeContext;

    public BaseWizardScreen(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public <T extends BaseWizardScreen> T selectNextScreen(String screenname, Class<T> type) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
        appiumdriver.findElementByAccessibilityId(screenname).click();

        if (type == InvoiceInfoScreen.class)
            return (T) new InvoiceInfoScreen(appiumdriver);
        else if (type == VehicleScreen.class)
            return (T) new VehicleScreen(appiumdriver);
        else if (type == ServicesScreen.class)
            return (T) new ServicesScreen(appiumdriver);
        else if (type == OrderSummaryScreen.class)
            return (T) new OrderSummaryScreen(appiumdriver);
        else if (type == ClaimScreen.class)
            return (T) new ClaimScreen(appiumdriver);
        else if (type == QuestionsScreen.class)
            return (T) new QuestionsScreen(appiumdriver);
        else if (type == QuestionAnswerScreen.class)
            return (T) new QuestionAnswerScreen(appiumdriver);
        else if (type == EnterpriseBeforeDamageScreen.class)
            return (T) new EnterpriseBeforeDamageScreen(appiumdriver);
        else if (type == PriceMatrixScreen.class)
            return (T) new PriceMatrixScreen(appiumdriver);
        else if (type == VisualInteriorScreen.class)
            return (T) new VisualInteriorScreen(appiumdriver);
        return null;
    }

    public void clickSave() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
        appiumdriver.findElementByAccessibilityId("Save").click();
    }

    public <T extends BaseTypeScreen> T saveWizard() {
        clickSave();
        return getTypeScreenFromContext();
    }

    public <T extends BaseTypeScreen> T cancelWizard() {
        clickCancelButton();
        acceptAlert();
        return getTypeScreenFromContext();
    }

    public <T extends BaseTypeScreen> T getTypeScreenFromContext()  {
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

