package com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAutoZoneLoginPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//table[@id='eProcPINs']//tr[contains(@class, 'odd')]")
    private WebElement firstLocationTableRow;

    @FindBy(id = "btn-go")
    private WebElement loginButton;

    public VNextBOAutoZoneLoginPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
