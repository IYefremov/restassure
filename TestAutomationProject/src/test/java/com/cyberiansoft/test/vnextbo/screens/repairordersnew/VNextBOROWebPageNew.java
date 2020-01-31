package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOROWebPageNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='reconmonitor-orders']/table")
    private WebElement repairOrdersTable;

    @FindBy(xpath = "//div[@id='reconmonitor-orders']//p[@class='search-results text-red']")
    private WebElement noRecordsFoundMessage;

    @FindBy(xpath = "//div[@data-bind='text: savedSearch.value.search.name']")
    private WebElement savedSearchDropDownField;

    @FindBy(xpath = "//span[@data-bind='click: savedSearch.click, visible: savedSearch.isEditable']")
    private WebElement editSavedSearchPencilIcon;

    @FindBy(xpath = "//span[@class='savedSearch']")
    private List<WebElement> savedSearchDropDownList;

    @FindBy(xpath = "//div[@id='reconmonitor-orders']/table/tbody/tr")
    private List<WebElement> repairOrdersTableRowsList;

    @FindBy(xpath = "//strong[@data-bind='text: clientName']")
    private List<WebElement> ordersCustomersList;

    @FindBy(xpath = "//div[@data-bind='click: changeTechnicians']")
    private List<WebElement> ordersTechniciansList;

    @FindBy(xpath = "//i[@class='truncated' and @data-bind='click: phaseMenuClicked']")
    private List<WebElement> ordersPhasesList;

    @FindBy(xpath = "//div[@class='rel department']/i[contains(@data-bind,'departmentName')]")
    private List<WebElement> ordersDepartmentsList;

    @FindBy(xpath = "//span[@data-bind='text: orderTypeName']")
    private List<WebElement> woTypesList;

    @FindBy(xpath = "//strong[@data-bind='text: orderNo']")
    private List<WebElement> woNumbersList;

    @FindBy(xpath = "//input[contains(@data-bind, 'value: roNo')]")
    private List<WebElement> roNumbersList;

    @FindBy(xpath = "//input[contains(@data-bind, 'value: stockNo')]")
    private List<WebElement> stockNumbersList;

    @FindBy(xpath = "//td[@class='stockRo']/p/strong")
    private List<WebElement> vinNumbersList;

    @FindBy(xpath = "//i[@class='icon-problem-indicator']")
    private List<WebElement> problemIndicatorsList;

    @FindBy(xpath = "//b[contains(@data-bind,'text: orderDateF')]")
    private List<WebElement> startDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'red')]/ancestor::tr//b[contains(@data-bind, 'orderDateF')]")
    private List<WebElement> highPriorityOrdersStartDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'none')]/ancestor::tr//b[contains(@data-bind, 'orderDateF')]")
    private List<WebElement> normalPriorityOrdersStartDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'green')]/ancestor::tr//b[contains(@data-bind, 'orderDateF')]")
    private List<WebElement> lowPriorityOrdersStartDatesList;

    @FindBy(xpath = "//tr[@data-id]//strong[contains(@data-bind, 'arbitrationDateDisplay')]")
    private List<WebElement> arbitrationDatesList;

    public WebElement savedSearchOptionByName(String searchName) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@class='savedSearch' and text()='" + searchName + "']"));
    }

    public VNextBOROWebPageNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}