package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.customers.CountriesListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextNewContactScreen extends VNextBaseScreen {

    @FindBy(xpath = "//form[@data-autotests-id='contact-form']")
    private WebElement newContactScreen;

    @FindBy(id = "contactDetailsFirstName")
    private WebElement firstNameField;

    @FindBy(id = "contactDetailsLastName")
    private WebElement lastNameField;

    @FindBy(id = "contactDetailsCompanyName")
    private WebElement companyNameField;

    @FindBy(id = "contactDetailsEmail")
    private WebElement emailField;

    @FindBy(id = "contactDetailsPhone")
    private WebElement phoneField;

    @FindBy(id = "contactDetailsAddress")
    private WebElement address1Field;

    @FindBy(id = "contactDetailsAddress2")
    private WebElement address2Field;

    @FindBy(id = "contactDetailsCity")
    private WebElement cityField;

    @FindBy(id = "contactDetailsCountry")
    private WebElement countryField;

    @FindBy(xpath = "//div[@action='state']")
    private WebElement stateField;

    @FindBy(id = "contactDetailsState")
    private WebElement stateFieldValue;

    @FindBy(id = "contactDetailsZip")
    private WebElement zipField;

    @FindBy(xpath = "//div[contains(@class,'countries-list')]")
    private WebElement countriesScreen;

    @FindBy(xpath = "//div[contains(@class,'states-list')]")
    private WebElement statesScreen;

    @FindBy(xpath = "//div[@data-autotests-id='countries-list']/div")
    private List<CountriesListElement> countriesRecordsList;

    public WebElement countryRecord(String country) {

        return countriesScreen.findElement(By.xpath(".//div[contains(text(),'" + country + "')]"));
    }

    public WebElement stateRecord(String state) {

        return statesScreen.findElement(By.xpath(".//div[contains(text(),'" + state + "')]"));
    }

    public VNextNewContactScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}
