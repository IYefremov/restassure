package com.cyberiansoft.test.vnextbo.screens.clients.clientDetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOAddressBlock extends VNextBOClientsDetailsViewAccordion {

    @FindBy(xpath = "//div[@id='collapseAddressInfo']/div[@class='panel-body']/div")
    private WebElement addressInfoPanel;

    //ship to
    @FindBy(id = "clientEditForm-ship-to-address1")
    private WebElement address1ShipToInputField;

    @FindBy(id = "clientEditForm-ship-to-address2")
    private WebElement address2ShipToInputField;

    @FindBy(id = "clientEditForm-ship-to-city")
    private WebElement cityShipToInputField;

    @FindBy(id = "clientEditForm-ship-to-zip")
    private WebElement zipShipToInputField;

    @FindBy(xpath = "//input[@aria-owns='clientEditForm-ship-to-country_listbox']")
    private WebElement countryShipToInputField;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[@id='clientEditForm-ship-to-country-list']")
    private WebElement countryShipToDropDown;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@id='clientEditForm-ship-to-country_listbox']/li")
    private List<WebElement> countryShipToListBoxOptions;

    @FindBy(xpath = "//input[@aria-owns='clientEditForm-ship-to-state_listbox']")
    private WebElement stateProvinceShipToInputField;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[@id='clientEditForm-ship-to-state-list']")
    private WebElement stateProvinceShipToDropDown;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@id='clientEditForm-ship-to-state_listbox']/li")
    private List<WebElement> stateProvinceShipToListBoxOptions;

    public WebElement getCountryShipToListBoxOptionByText(String option) {

        return driver.findElement(By.xpath("//div[@class='k-animation-container']//ul[@id='clientEditForm-ship-to-country_listbox']/li[text()='"+
                option + "']"));
    }

    public WebElement getStateProvinceShipToListBoxOptionByText(String option) {

        return driver.findElement(By.xpath("//div[@class='k-animation-container']//ul[@id='clientEditForm-ship-to-state_listbox']/li[text()='"+
                option + "']"));
    }


    //bill to
    @FindBy(id = "ship-same-as-bill-to")
    private WebElement sameAsShipToCheckbox;

    @FindBy(id = "clientEditForm-bill-to-address1")
    private WebElement address1BillToInputField;

    @FindBy(id = "clientEditForm-bill-to-address2")
    private WebElement address2BillToInputField;

    @FindBy(id = "clientEditForm-bill-to-city")
    private WebElement cityBillToInputField;

    @FindBy(id = "clientEditForm-bill-to-zip")
    private WebElement zipBillToInputField;

    @FindBy(xpath = "//input[@aria-owns='clientEditForm-bill-to-country_listbox']")
    private WebElement countryBillToInputField;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[@id='clientEditForm-bill-to-country-list']")
    private WebElement countryBillToDropDown;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@id='clientEditForm-bill-to-country_listbox']/li")
    private List<WebElement> countryBillToListBoxOptions;

    @FindBy(xpath = "//input[@aria-owns='clientEditForm-bill-to-state_listbox']")
    private WebElement stateProvinceBillToInputField;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[@id='clientEditForm-bill-to-state-list']")
    private WebElement stateProvinceBillToDropDown;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@id='clientEditForm-bill-to-state_listbox']/li")
    private List<WebElement> stateProvinceBillToListBoxOptions;

    public WebElement getCountryBillToListBoxOptionByText(String option) {

        return driver.findElement(By.xpath("//div[@class='k-animation-container']//ul[@id='clientEditForm-bill-to-country_listbox']/li[text()='"+
                option + "']"));
    }

    public WebElement getStateProvinceBillToListBoxOptionByText(String option) {

        return driver.findElement(By.xpath("//div[@class='k-animation-container']//ul[@id='clientEditForm-bill-to-state_listbox']/li[text()='"+
                option + "']"));
    }

    public VNextBOAddressBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}