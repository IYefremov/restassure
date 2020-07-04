package com.cyberiansoft.test.vnextbo.screens.clients.clientdetails;

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
public class VNextBOClientsClientServicesPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='clientServicesListTable-wrapper']/table")
    private WebElement servicesTable;

    @FindBy(xpath = "//span[@aria-owns='client-services-list-view__packages-dropdown_listbox']//span[@class='k-input']")
    private WebElement servicesPackageDropDownField;

    @FindBy(xpath = "//ul[@id='client-services-list-view__packages-dropdown_listbox']/li")
    private List<WebElement> servicesPackageDropDownOptions;

    @FindBy(xpath = "//i[@class='icon-arrow-left3 navigation-icon']")
    private WebElement clientServicesBackButton;

    @FindBy(xpath = "//tbody[@data-template='client-services-view-row-template']/tr")
    private List<WebElement> servicesRecords;

    @FindBy(xpath = "//div[@id='client-services-list-view']//p[@class='search-results text-red']")
    private WebElement noRecordsFoundMessage;

    @FindBy(xpath = "//div[@id='clientServicesListTable-wrapper']/table[@class='grid']//th")
    private List<WebElement> columnsTitles;

    @FindBy(xpath = "(//select[contains(@data-bind, 'value: clientService_IsRequired')])[1]")
    private WebElement firstLineRequiredDropDownField;

    @FindBy(xpath = "(//span[contains(@class, 'client-services-list-view__client-tech-types')]//span[@class='k-input'])[1]")
    private WebElement firstLineTechnicianDropDownField;

    @FindBy(xpath = "(//input[contains(@data-bind, 'effectiveDate')])[1]")
    private WebElement firstLineEffectiveDateField;

    @FindBy(xpath = "(//span[@class='k-icon k-i-calendar'])[1]")
    private WebElement firstLineEffectiveDateCalendarIcon;

    @FindBy(xpath = "(//td[contains(@class, 'k-today')])[1]")
    private WebElement calendarCurrentDayButton;

    @FindBy(xpath = "(//input[@title='Effective Price'])[1]")
    private WebElement firstLineEffectivePriceField;

    public List<WebElement> columnTextCellsByTitle(String columnTitle) {

        int searchColumnIndex = columnsTitles.stream().map(WebElement::getText).collect(Collectors.toList()).indexOf(columnTitle) + 1;
        return driver.findElements(By.xpath("//div[@id='clientServicesListTable-wrapper']/table[@class='grid']//tr[@role='option']/td[" + searchColumnIndex + "]"));
    }

    public WebElement technicianOptionByName(String option) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//li[text()='" + option + "']"));
    }

    public VNextBOClientsClientServicesPage() {

        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}