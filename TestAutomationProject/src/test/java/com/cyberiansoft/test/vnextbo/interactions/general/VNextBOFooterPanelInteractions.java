package com.cyberiansoft.test.vnextbo.interactions.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOFooterPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOPrivacyPolicyDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOTermsAndConditionsDialog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VNextBOFooterPanelInteractions {

    public static void clickTermsAndConditionsLink() {
        clickFooterPanelElement(new VNextBOFooterPanel().getTermsAndConditions());
        WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOTermsAndConditionsDialog().getTermsAndConditionsDialog(), true, 5);
    }

    public static void clickPrivacyPolicyLink() {
        clickFooterPanelElement(new VNextBOFooterPanel().getPrivacyPolicy());
        WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPrivacyPolicyDialog().getPrivacyPolicyDialog(), true, 5);
    }

    private static void clickFooterPanelElement(WebElement element) {
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.elementShouldBeVisible(element, true, 5);
        Utils.scrollToElement(element);
        Utils.clickElement(element);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void closeIntercom() {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        final VNextBOFooterPanel footerPanel = new VNextBOFooterPanel();
        driver.switchTo().frame(footerPanel.getIntercomFrame());
        Utils.clickElement(footerPanel.getIntercom());
        driver.switchTo().defaultContent();
    }
}
