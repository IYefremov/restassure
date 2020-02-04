package com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPartsProvidersDialog extends VNextBOBaseWebPage {

    @FindBy(id = "parts-providers-modal")
    private WebElement partsProvidersModal;

    @FindBy(xpath = "//div[@id='parts-providers-modal']//button[@aria-label='Close']")
    private WebElement closeButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'withProviderName')]")
    private List<WebElement> providerNamesList;

    public VNextBOPartsProvidersDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getProviderByName(String provider) {
        return driver.findElement(By.xpath("//div[text()='" + provider + "']"));
    }

    public WebElement getGetNewQuoteButtonByProviderName(String provider) {
        return getProviderByName(provider).findElement(By.xpath(".//../..//button[text()='Get New Quote']"));
    }
}
