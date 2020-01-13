package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOHomeWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//a[text()='Access Client Portal']")
    private WebElement accessClientPortalLink;

    @FindBy(xpath = "//a[text()='Access ReconPro BackOffice']")
    private WebElement accessReconProBOLink;

    @FindBy(xpath = "//div[@class='support-buttons-row']/a[contains(@href, 'back-office')]")
    private WebElement supportForBOButton;

    @FindBy(xpath = "//div[@class='support-buttons-row']/a[contains(@href, 'mobile-app')]")
    private WebElement supportForMobileAppButton;

    public VNextBOHomeWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
