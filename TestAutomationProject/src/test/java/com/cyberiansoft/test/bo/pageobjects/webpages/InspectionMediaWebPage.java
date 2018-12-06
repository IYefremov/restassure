package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InspectionMediaWebPage extends BaseWebPage {

    @FindBy(id = "repeater")
    private WebElement imgspanel;

    public InspectionMediaWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean isImageNoteExistsForInspection() {
        return imgspanel.findElements(By.xpath(".//div[@class='mlContentWrapper image']/img")).size() > 0;
    }
}
