package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class VNextBOPartsManagementSearchPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@id='parts-view']//div[@class='pull-right custom-search']")
    private WebElement searchPanel;

    public VNextBOPartsManagementSearchPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isPartsManagementSearchPanelDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchPanel));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}