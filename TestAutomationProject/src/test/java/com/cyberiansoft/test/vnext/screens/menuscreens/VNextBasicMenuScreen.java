package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
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

    public VNextBasicMenuScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
        WaitUtils.getGeneralFluentWait().until((driver) -> menuscreen.isDisplayed());
    }

    public VNextBasicMenuScreen(){}

    void clickMenuItem(WebElement menuItem) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(menuItem));
        tap(menuItem);
    }

    public void clickCloseMenuButton() {
        WaitUtils.waitUntilElementIsClickable(closebtn).click();
    }
}
