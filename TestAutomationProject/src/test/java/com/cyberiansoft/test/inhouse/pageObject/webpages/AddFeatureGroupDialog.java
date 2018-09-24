package com.cyberiansoft.test.inhouse.pageObject.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class AddFeatureGroupDialog extends FeatureGroupDialog {

    @FindBy(xpath = "//div[@class='dropdown open']//ul[@class='dropdown-menu dropdown-menu-feature-group-button']")
    private WebElement featureGroupDropDown;

    public AddFeatureGroupDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        try {
            wait.until(ExpectedConditions.visibilityOf(featureGroupDropDown));
        } catch (Exception e) {
            Assert.fail("The feature group drop down has not been opened", e);
        }
    }
}