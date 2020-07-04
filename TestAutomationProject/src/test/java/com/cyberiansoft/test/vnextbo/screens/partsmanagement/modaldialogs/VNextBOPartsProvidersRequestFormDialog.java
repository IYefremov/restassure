package com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPartsProvidersRequestFormDialog extends VNextBOBaseWebPage {

    @FindBy(id = "parts-providers-request-form-modal")
    private WebElement requestFormDialog;

    @FindBy(xpath = "//div[@id='parts-providers-request-form-modal']//div[contains(@class, 'message--info')]")
    private WebElement requestFormDialogMessage;

    @FindBy(xpath = "//div[@id='parts-providers-request-form-modal']//h4")
    private WebElement title;

    @FindBy(xpath = "//div[@id='parts-providers-request-form-modal']//button[text()='Request Quote']")
    private WebElement requestQuoteButton;

    @FindBy(xpath = "//div[@id='parts-providers-request-form-modal']//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@data-template='parts-providers-request-form-item-template']//div[contains(@data-bind, 'quantity')]")
    private List<WebElement> partQuantityFields;

    @FindBy(xpath = "//div[@data-template='parts-providers-request-form-item-template']//input[@type='checkbox']")
    private List<WebElement> partCheckboxes;

    @FindBy(xpath = "//div[@id='parts-providers-request-form-modal']//div[contains(@data-bind, 'serviceName')]")
    private List<WebElement> partNames;

    public VNextBOPartsProvidersRequestFormDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
