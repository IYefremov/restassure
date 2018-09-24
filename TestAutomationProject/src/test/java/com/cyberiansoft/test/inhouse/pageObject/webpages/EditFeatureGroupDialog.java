package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditFeatureGroupDialog extends FeatureGroupDialog {

    public EditFeatureGroupDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
