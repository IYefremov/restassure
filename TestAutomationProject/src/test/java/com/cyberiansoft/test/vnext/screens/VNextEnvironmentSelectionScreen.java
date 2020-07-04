package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import com.cyberiansoft.test.vnext.utils.VNextClientEnvironmentUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextEnvironmentSelectionScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-page='env-list']")
    private WebElement environmentlistscreeen;

    @FindBy(xpath="//*[@data-autotests-id='env-list']")
    private WebElement envlist;

    public VNextEnvironmentSelectionScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public void selectEnvironment(EnvironmentType environmentType) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-env-name='" + VNextClientEnvironmentUtils.getEnvironmentString(environmentType) + "']")));
        tap(envlist.findElement(By.xpath(".//div[@data-env-name='" + VNextClientEnvironmentUtils.getEnvironmentString(environmentType) + "']")));
    }
}
