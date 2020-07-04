package com.cyberiansoft.test.vnextbo.screens.services;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VNextBOServicesWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//a[@role='tab']//span[text()='All']")
    private WebElement allTab;

    @FindBy(xpath = "//a[@role='tab']//span[text()='Labor']")
    private WebElement laborTab;

    @FindBy(xpath = "//a[@role='tab']//span[text()='Parts']")
    private WebElement partsTab;

    @FindBy(xpath = "//button[@data-bind='click: onAddNewServiceClick']")
    private WebElement addNewServiceTab;

    @FindBy(xpath = "//div[@id='services-list-view']//table")
    private WebElement servicesTable;

    @FindBy(xpath = "//div[@id='services-list-view']//p[@data-bind='text: progressMessage']")
    private WebElement servicesNotFoundMessage;

    @FindBy(xpath = "//div[@id='services-list-view']//table/tbody/tr")
    private List<WebElement> servicesTableRecords;

    @FindBy(xpath = "//div[@id='services-list-view']//table//th[text()!='']")
    private List<WebElement> columnsTitlesList;

    private WebElement serviceRowByName(String serviceName) {

        return driver.findElement(By.xpath("//td[text()=' " + serviceName + "']/ancestor::tr"));
    }

    public WebElement editServiceButtonByName(String serviceName) {

        return serviceRowByName(serviceName).findElement(By.xpath(".//span[@data-bind='click: editService']"));
    }

    public WebElement deleteServiceButtonByName(String serviceName) {

        return serviceRowByName(serviceName).findElement(By.xpath(".//span[@class='icon-trash-bin']"));
    }

    public WebElement restoreServiceButtonByName(String serviceName) {

        return serviceRowByName(serviceName).findElement(By.xpath(".//span[@class='icon-rollback-arrow']"));
    }

    public WebElement orderNumberFieldByName(String serviceName) {

        return serviceRowByName(serviceName).findElement(By.xpath(".//td[@class='services-order-cell']/input"));
    }

    public WebElement serviceRecordSpecificColumnValue(String serviceName, String columnTitle) {

        int searchColumnIndex = columnsTitlesList.stream().map(WebElement::getText).collect(Collectors.toList()).indexOf(columnTitle) + 1;
        return serviceRowByName(serviceName).findElement(By.xpath(".//td[" + searchColumnIndex + "]"));
    }

    public WebElement serviceRecordPriceTypeIcon(String serviceName) {

        int searchColumnIndex = columnsTitlesList.stream().map(WebElement::getText).collect(Collectors.toList()).indexOf("Price Type") + 1;
        return serviceRowByName(serviceName).findElement(By.xpath(".//td[" + searchColumnIndex + "]/i"));
    }

    public VNextBOServicesWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
