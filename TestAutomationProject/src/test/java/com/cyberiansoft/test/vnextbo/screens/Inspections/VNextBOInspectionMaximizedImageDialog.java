package com.cyberiansoft.test.vnextbo.screens.Inspections;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextBOInspectionMaximizedImageDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@data-bind, 'modalDialogHeight')]")
    public WebElement dialogContainer;

    @FindBy(xpath = "//div[@class='image-notes__preview--modal' and contains(@style, 'url')]")
    public WebElement maximizedImage;

    @FindBy(xpath = "//div[contains(@class, 'notes__images')]//button[@class='close']")
    public WebElement closeDialogButton;

    public VNextBOInspectionMaximizedImageDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(closeDialogButton));
    }
}
