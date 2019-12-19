package com.cyberiansoft.test.vnextbo.screens.clients.clientdetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOClientsDetailsViewAccordion extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@id='client-details-view']")
    private WebElement clientsDetailsView;

    @FindBy(xpath = "//div[@aria-controls='collapseClientInfo']")
    private WebElement clientsInfo;

    @FindBy(xpath = "//div[@id='collapseClientInfo']")
    private WebElement clientsInfoPanel;

    @FindBy(xpath = "//div[@aria-controls='collapseAccountInfo']")
    private WebElement accountInfo;

    @FindBy(xpath = "//div[@id='collapseAccountInfo']")
    private WebElement accountInfoPanel;

    @FindBy(xpath = "//div[@aria-controls='collapseAddressInfo']")
    private WebElement address;

    @FindBy(xpath = "//div[@id='collapseAddressInfo']")
    private WebElement addressPanel;

    @FindBy(xpath = "//div[@aria-controls='collapseEmailOptions']")
    private WebElement emailOptions;

    @FindBy(xpath = "//div[@id='collapseEmailOptions']")
    private WebElement emailOptionsPanel;

    @FindBy(xpath = "//div[@aria-controls='collapsePreferences']")
    private WebElement preferences;

    @FindBy(xpath = "//div[@id='collapsePreferences']")
    private WebElement preferencesPanel;

    @FindBy(xpath = "//div[@id='client-details-view-headingMisc']//div[contains(text(), 'Miscellaneous')]")
    private WebElement miscellaneous;

    @FindBy(xpath = "//div[@id='collapseMisc']")
    private WebElement miscellaneousPanel;

    @FindBy(xpath = "//div[@data-bind='click: onServicesClick']")
    private WebElement services;

    @FindBy(xpath = "//button[@data-bind='click: onCancelClick']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[@data-bind='click: onSaveClientClick']")
    private WebElement okButton;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[contains(@id, 'clientEditForm')]//li[text()='" + optionName +"']"));
    }

    public VNextBOClientsDetailsViewAccordion() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}