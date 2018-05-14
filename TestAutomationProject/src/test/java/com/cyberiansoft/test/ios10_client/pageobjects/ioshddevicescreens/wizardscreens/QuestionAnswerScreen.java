package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class QuestionAnswerScreen extends BaseWizardScreen {

    public QuestionAnswerScreen(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("QuestionAnswerServicesView")));
    }
}
