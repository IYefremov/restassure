package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class VNextBORepairOrderDetailsWebPage extends VNextBOBaseWebPage {

    @FindBy(id = "reconmonitordetails-view")
    private WebElement roDetailsSection;

    public VNextBORepairOrderDetailsWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isRoDetailsSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(roDetailsSection));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
