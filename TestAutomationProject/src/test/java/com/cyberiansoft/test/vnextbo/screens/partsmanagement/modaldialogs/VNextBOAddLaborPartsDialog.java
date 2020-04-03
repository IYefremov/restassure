package com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAddLaborPartsDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='parts-labor-service-form']//div[contains(@class,'modal-content')]")
    private WebElement dialogContent;

    @FindBy(xpath = "//input[@aria-owns='parts-labor-service-form-labors_listbox']")
    private WebElement selectLaborServiceField;

    @FindBy(id = "parts-labor-service-form-labors_listbox")
    private WebElement laborListBox;

    @FindBy(id = "parts-labor-service-form-labors-list")
    private WebElement laborServicesDropDown;

    @FindBy(xpath = "//button[@data-automation-id='parts-labor-service-form-submit']")
    private WebElement addLaborButton;

    @FindBy(xpath = "//button[@data-automation-id='parts-labor-service-form-cancel']")
    private WebElement cancelAddingLaborButton;

    @FindBy(xpath = "//div[@id='parts-labor-service-form']//button[@aria-label='Close']")
    private WebElement xIconCloseButton;

    @FindBy(xpath = "//div[@id='parts-labor-service-form']//span[@title='clear']")
    private WebElement clearServiceFieldIcon;

    public WebElement serviceDropDownOption(String optionName) {

        return laborListBox.findElement(By.xpath(".//li[text()='" + optionName + "']"));
    }

    public VNextBOAddLaborPartsDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}