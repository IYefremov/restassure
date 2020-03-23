package com.cyberiansoft.test.vnextbo.screens.addons;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAddOnsPage extends VNextBOBaseWebPage {

    @FindBy(id = "addons-view")
    private WebElement addonsBlock;

    public VNextBOAddOnsPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getAddOnByName(String addOn) {
        return addonsBlock.findElement(By.xpath(".//td[text()='" + addOn + "']/.."));
    }

    public WebElement getAddOnsTurnOnButton(String addOn) {
        return getAddOnByName(addOn).findElement(By.xpath(".//button[contains(text(), 'Turn On')]"));
    }

    public WebElement getAddOnsTurnOffButton(String addOn) {
        return getAddOnByName(addOn).findElement(By.xpath(".//button[contains(text(), 'Turn Off')]"));
    }

    public WebElement getIntegrationStatus(String addOn) {
        return getAddOnByName(addOn).findElement(By.xpath(".//span[@class='integration-status']"));
    }
}
