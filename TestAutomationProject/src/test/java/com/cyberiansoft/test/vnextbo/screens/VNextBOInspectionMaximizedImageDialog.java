package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
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
    private WebElement maximizedImageDialogContainer;

    @FindBy(xpath = "//div[@class='image-notes__preview--modal' and contains(@style, 'url')]")
    private WebElement maximizedImage;

    @FindBy(xpath = "//div[contains(@class, 'notes__images')]//button[@class='close']")
    private WebElement closeMaximizedImageDialogButton;

    public VNextBOInspectionMaximizedImageDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(closeMaximizedImageDialogButton));
    }

    public boolean isInspectionZoomedImageDisplayed() {return Utils.isElementDisplayed(maximizedImage); }

    public boolean isInspectionZoomedImageClosed() {return Utils.isElementDisplayed(maximizedImageDialogContainer); }

    public void closeInspectionMaximizedImageDialog() {Utils.clickElement(closeMaximizedImageDialogButton); }
}
