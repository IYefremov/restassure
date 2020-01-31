package com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAutoZoneProductResultsPage extends VNextBOBaseWebPage {

    @FindBy(className = "core-price")
    private WebElement corePrice;

    @FindBy(xpath = "//a[contains(@onclick, 'Floating Cart')]")
    private WebElement viewQuoteButton;

    public WebElement getPrice() {
        return corePrice.findElement(By.xpath(".//../..//td[@data-title='Cost:']"));
    }

    public WebElement getAddToQuoteButton() {
        return corePrice.findElement(By.xpath(".//../..//button[text()='Add To Quote']"));
    }

    public VNextBOAutoZoneProductResultsPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
