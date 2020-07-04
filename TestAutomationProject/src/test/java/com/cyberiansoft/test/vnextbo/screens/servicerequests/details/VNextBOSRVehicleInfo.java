package com.cyberiansoft.test.vnextbo.screens.servicerequests.details;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOSRVehicleInfo extends VNextBOSRDetailsPage {

    @FindBy(xpath = "//span[contains(text(), 'Make:')]/parent::div")
    private WebElement make;

    @FindBy(xpath = "//span[contains(text(), 'Model:')]/parent::div")
    private WebElement model;

    @FindBy(xpath = "//span[contains(text(), 'Year:')]/parent::div")
    private WebElement year;

    @FindBy(xpath = "//span[contains(text(), 'VIN#:')]/parent::div")
    private WebElement vin;

    @FindBy(xpath = "//span[contains(text(), 'Mileage:')]/parent::div")
    private WebElement mileage;

    public VNextBOSRVehicleInfo() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
