package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class VNextBOOtherDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@data-bind, 'isMenuUp') and not(@id)]")
    private WebElement otherDialog;

    public VNextBOOtherDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isOtherDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(otherDialog));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
