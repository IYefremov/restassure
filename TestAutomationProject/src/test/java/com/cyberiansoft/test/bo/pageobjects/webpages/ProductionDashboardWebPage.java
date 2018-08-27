package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValueWithTyping;

public class ProductionDashboardWebPage extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_comboLocations")
    private ComboBox locationCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_comboLocations_DropDown")
    private DropDown locationDropDown;

    @FindBy(id = "ctl00_ctl00_Content_Main_comboCustomer_Input")
    private TextField customerCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_comboCustomer_DropDown")
    private DropDown customerDropDown;

    @FindBy(id = "ctl00_ctl00_Content_Main_BtnFind")
    private WebElement applyButton;

    @FindBy(className = "dashboard-table")
    private WebTable dashboardTable;

    public ProductionDashboardWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public ProductionDashboardWebPage selectLocation(String location) {
        selectComboboxValue(locationCombobox, locationDropDown, location);
        return this;
    }

    public ProductionDashboardWebPage selectCustomer(String customer) {
        selectComboboxValueWithTyping(customerCombobox, customerDropDown, customer);
        return this;
    }

    public ProductionDashboardWebPage clickApplyButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
        } catch (Exception e) {
            Assert.fail("The Apply button has not been clicked", e);
        }
        waitForLoading();
        return this;
    }

    public ProductionDashboardWebPage verifyDashboardTableColumnsAreVisible() {
        wait.until(ExpectedConditions.visibilityOf(dashboardTable.getWrappedElement()));
        Assert.assertTrue(dashboardTable.tableColumnExists("Phases"));
        Assert.assertTrue(dashboardTable.tableColumnExists("Active"));
        Assert.assertTrue(dashboardTable.tableColumnExists("Started"));
        Assert.assertTrue(dashboardTable.tableColumnExists("Pending"));
        Assert.assertTrue(dashboardTable.tableColumnExists("Past Due"));
        Assert.assertTrue(dashboardTable.tableColumnExists("Estimated Work"));
        return this;
    }
}