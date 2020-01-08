package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextUpdateWorkScreen extends VNextBaseScreen {

    @FindBy(id="vehicleInfo")
    private WebElement vehicleInfoFld;

    @FindBy(xpath="//*[contains(@class,'vehicle-info-search-button')]")
    private WebElement vehicleInfoSearchButton;

    public VNextUpdateWorkScreen() {
        PageFactory.initElements(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), this);
    }

    public void setSearchVehicleInfo(String searchText) {
        vehicleInfoFld.sendKeys(searchText);
    }

    public void clickSearchButton() {
        vehicleInfoSearchButton.click();
    }
}
