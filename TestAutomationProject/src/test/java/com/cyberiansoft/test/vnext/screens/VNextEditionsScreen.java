package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextEditionsScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-page='editions-list']")
    private WebElement editionslistscreeen;

    @FindBy(xpath="//*[@data-autotests-id='editions-list']")
    private WebElement editionslist;

    public VNextEditionsScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.visibilityOf(editionslistscreeen));
    }

    public VNextEnvironmentSelectionScreen selectEdition(String editionName) {
        tap(editionslist.findElement(By.xpath(".//div[contains(text(), '" + editionName + "')]")));
        return new VNextEnvironmentSelectionScreen(appiumdriver);
    }
}
