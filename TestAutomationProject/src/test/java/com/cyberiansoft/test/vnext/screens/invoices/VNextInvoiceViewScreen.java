package com.cyberiansoft.test.vnext.screens.invoices;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextInvoiceViewScreen extends VNextBaseScreen {

    @FindBy(xpath = "//iframe[@class='printing-viewer']")
    private WebElement pageFrame;

    @FindBy(xpath = "//div[@class='print-page']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@class='custom-signature-div signature-div']/img")
    private WebElement signature;

    @FindBy(xpath = "//div[@id='customerText']")
    private WebElement customerName;

    @FindBy(xpath = "//td[@id='customerAddress']")
    private WebElement customerAddress;

    @FindBy(xpath = "(//table[contains(@class,'invoice-summary-table')]//td[@class='centered-content'])[1]")
    private WebElement invoiceNumber;

    @FindBy(xpath = "//td[@class='editable-field date-field']")
    private WebElement invoiceDate;

    @FindBy(xpath = "//span[@class='service-name']")
    private List<WebElement> servicesList;

    @FindBy(xpath = "//td[@data-path='jsonData.Vehicle.VIN']")
    private WebElement vinNumber;

    @FindBy(xpath = "//td[@data-path='jsonData.Vehicle.Make']")
    private WebElement make;

    @FindBy(xpath = "//td[@data-path='jsonData.Vehicle.Model']")
    private WebElement model;

    @FindBy(xpath = "//td[@data-path='jsonData.Vehicle.Year']")
    private WebElement year;

    public VNextInvoiceViewScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public WebElement serviceCellByServiceName(String serviceName) {

        return ChromeDriverProvider.INSTANCE.getMobileChromeDriver()
                .findElement(By.xpath("//span[@class='service-name' and contains(text(), '" + serviceName + "')]"));
    }
}
