package com.cyberiansoft.test.vnextbo.screens.partsmanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPartsOrdersListPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']")
    private WebElement listPanel;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li")
    private List<WebElement> listOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[contains(@class, 'item__title')]")
    private List<WebElement> customerNamesListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//div[@class='entity-list__item__description']//b")
    private List<WebElement> wONumberListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//span[text()='Phase:']/..")
    private List<WebElement> phasesListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//div[contains(@class, 'item__description')]/div[1]")
    private List<WebElement> woDataListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//span[text()='Stock#:']/..")
    private List<WebElement> stockNumbersListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//div[@class='entity-list__item__description']/div[1]")
    private List<WebElement> datesListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//span[text()='VIN:']/..")
    private List<WebElement> vinNumbersListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[text()='Vendor:']/parent::div")
    private List<WebElement> vendorNumOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[text()='RO#:']/parent::div")
    private List<WebElement> roNumOptions;

    @FindBy(xpath = "//div[@class='progress-message']/a")
    private WebElement ordersListEmptyState;

    public VNextBOPartsOrdersListPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}