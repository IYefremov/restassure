package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextBasicMenuScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[contains(@class,'actions-menu-layout')]")
    private WebElement menuscreen;

    @FindBy(xpath = "//div[contains(@class,'actions-menu-close')]")
    private WebElement closebtn;

    public VNextBasicMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(menuscreen));
    }

    public VNextBasicMenuScreen(){}

    void clickMenuItem(WebElement menuItem) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(menuItem));
        tap(menuItem);
    }

    public void clickCloseMenuButton() {
        tap(menuscreen.findElement(By.xpath("//*[@class='actions-menu-close']")));
    }
}
