package com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAutoZoneQuoteDetailsPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='totalRow']/span[contains(@class, 'costValue')]")
    private WebElement totalPrice;

    @FindBy(id = "submit-order-button-transfer")
    private WebElement transferCartButton;

    public VNextBOAutoZoneQuoteDetailsPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
