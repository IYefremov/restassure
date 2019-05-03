package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class VNextBOPartsManagementROListPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']")
    private WebElement ROListPanel;

    public boolean isPartsManagementROListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(ROListPanel));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public VNextBOPartsManagementROListPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}