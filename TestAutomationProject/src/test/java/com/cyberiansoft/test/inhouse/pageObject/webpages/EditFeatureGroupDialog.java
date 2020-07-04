package com.cyberiansoft.test.inhouse.pageObject.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class EditFeatureGroupDialog extends FeatureGroupDialog {

    public EditFeatureGroupDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
