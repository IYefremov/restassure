package com.cyberiansoft.test.vnextbo.screens.clients.clientdetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOAccountInfoBlock extends VNextBOClientsDetailsViewAccordion {

    @FindBy(xpath = "//div[@id='client-details-view-headingAccountInfo']/div/div")
    private WebElement accountInfoPanel;

    @FindBy(id = "clientEditForm-client-accounting-id")
    private WebElement accountingId;

    @FindBy(id = "clientEditForm-client-accounting-id2")
    private WebElement accountingId2;

    @FindBy(xpath = "//span[@aria-controls='clientEditForm-client-export-as_listbox']/span[contains(@class, 'k-icon')]")
    private WebElement exportAsArrow;

    @FindBy(xpath = "//input[@aria-owns='clientEditForm-client-export-as_listbox']")
    private WebElement exportAsInputField;

    @FindBy(id = "clientEditForm-client-export-as_listbox")
    private WebElement exportAsDropDown;

    @FindBy(xpath = "//ul[@id='clientEditForm-client-export-as_listbox']/li")
    private List<WebElement> exportAsListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='clientEditForm-client-class_listbox']//span[contains(@class, 'k-input')]")
    private WebElement classField;

    @FindBy(xpath = "//span[@aria-owns='clientEditForm-client-class_listbox']//span[contains(@class, 'k-icon')]")
    private WebElement classArrow;

    @FindBy(id = "clientEditForm-client-class-list")
    private WebElement classDropDown;

    @FindBy(xpath = "//div[@id='clientEditForm-client-class-list']//div[contains(text(), 'No data found')]")
    private WebElement classDropDownNoDataFound;

    @FindBy(xpath = "//ul[@id='clientEditForm-client-class_listbox']/li")
    private List<WebElement> classListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='clientEditForm-client-qb-account_listbox']//span[contains(@class, 'k-input')]")
    private WebElement qbAccountField;

    @FindBy(xpath = "//span[@aria-owns='clientEditForm-client-qb-account_listbox']//span[contains(@class, 'k-icon')]")
    private WebElement qbAccountArrow;

    @FindBy(id = "clientEditForm-client-qb-account-list")
    private WebElement qbAccountDropDown;

    @FindBy(xpath = "//ul[@id='clientEditForm-client-qb-account_listbox']/li")
    private List<WebElement> qbAccountListBoxOptions;

    @FindBy(id = "poNoRequired")
    private WebElement poNumberRequiredCheckbox;

    @FindBy(id = "poUpfrontRequired")
    private WebElement poNumberUpfrontRequiredCheckbox;


    public WebElement getExportAsListBoxOptionByText(String option) {

        return driver.findElement(By.xpath("//ul[@id='clientEditForm-client-export-as_listbox']/li[text()='" + option + "']"));
    }

    public WebElement getQbAccountListBoxOptionByText(String option) {

        return driver.findElement(By.xpath("//ul[@id='clientEditForm-client-qb-account_listbox']/li[text()='" + option + "']"));
    }

    public VNextBOAccountInfoBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}