package com.cyberiansoft.test.vnextbo.screens.clients.clientDetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOClientsDetailsViewAccordion extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@id='client-details-view']")
    private WebElement clientsDetailsView;

    @FindBy(xpath = "//div[@aria-controls='collapseClientInfo']")
    private WebElement clientsInfo;

    @FindBy(xpath = "//div[@aria-controls='collapseAccountInfo']")
    private WebElement accountInfo;

    @FindBy(xpath = "//div[@aria-controls='collapseAddressInfo']")
    private WebElement address;

    @FindBy(xpath = "//div[@aria-controls='collapseEmailOptions']")
    private WebElement emailOptions;

    @FindBy(xpath = "//div[@aria-controls='collapsePreferences']")
    private WebElement preferences;

    @FindBy(xpath = "//div[@id='client-details-view-headingMisc']//div[contains(text(), 'Miscellaneous')]")
    private WebElement miscellaneous;

    @FindBy(xpath = "//div[@id='client-details-view-headingMisc']//div[contains(text(), 'Services')]")
    private WebElement services;

    public VNextBOClientsDetailsViewAccordion() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}