package com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAutoZoneMyShopPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//input[@id='Batteries / Cables']")
    private WebElement batteriesCablesCheckbox;

    @FindBy(id = "freqOrderedBtn")
    private WebElement lookupPartsButton;

    public VNextBOAutoZoneMyShopPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
