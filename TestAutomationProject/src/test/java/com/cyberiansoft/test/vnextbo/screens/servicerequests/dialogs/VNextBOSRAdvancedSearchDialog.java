package com.cyberiansoft.test.vnextbo.screens.servicerequests.dialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;

@Getter
public class VNextBOSRAdvancedSearchDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//form[@class='AdvancedSearch']")
    private WebElement advancedSearchForm;

    @FindBy(xpath = "//div[@class='AdvancedSearch-footer']//div[text()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='AdvancedSearch-footer']//span[text()='Clear']")
    private WebElement clearButton;

    @FindBy(xpath = "//div[@class='AdvancedSearch-headerIcon']")
    private WebElement closeButton;

    @FindBy(id = "FreeTextSearch")
    private WebElement hasThisTextField;

    @FindBy(xpath = "//input[@list='ClientId']")
    private WebElement customerField;

    @FindBy(id = "ClientId")
    private WebElement customerDropDown;

    @FindBy(xpath = "//div[@id='ClientId']/div")
    private List<WebElement> customerListBox;

    @FindBy(xpath = "//input[@list='OwnerId']")
    private WebElement ownerField;

    @FindBy(id = "OwnerId")
    private WebElement ownerDropDown;

    @FindBy(xpath = "//div[@id='OwnerId']/div")
    private List<WebElement> ownerListBox;

    @FindBy(xpath = "//input[@list='AdvisorId']")
    private WebElement advisorField;

    @FindBy(id = "AdvisorId")
    private WebElement advisorDropDown;

    @FindBy(xpath = "//div[@id='AdvisorId']/div")
    private List<WebElement> advisorListBox;

    @FindBy(xpath = "//input[@list='EmployeeId']")
    private WebElement employeeField;

    @FindBy(id = "EmployeeId")
    private WebElement employeeDropDown;

    @FindBy(xpath = "//div[@id='EmployeeId']/div")
    private List<WebElement> employeeListBox;

    @FindBy(id = "SRNumber")
    private WebElement srNumField;

    @FindBy(id = "TeamId")
    private WebElement teamField;

    @FindBy(id = "ServiceRequestTypeId")
    private WebElement srTypeField;

    @FindBy(id = "RONumber")
    private WebElement roNumField;

    @FindBy(id = "StockNumber")
    private WebElement stockNumField;

    @FindBy(id = "VIN")
    private WebElement vinNumField;

    @FindBy(id = "TimeFrameId")
    private WebElement timeFrameField;

    @FindBy(xpath = "//div[@id='FromDate']//input")
    private WebElement fromDateField;

    @FindBy(xpath = "//div[@id='ToDate']//input")
    private WebElement toDateField;

    @FindBy(id = "StatusId")
    private WebElement statusField;

    @FindBy(id = "SRPhaseId")
    private WebElement phaseField;

    @FindBy(id = "LocationId")
    private WebElement repairLocationField;

    @FindBy(id = "Tag")
    private WebElement tagNumField;

    @FindBy(xpath = "//input[@id='HasWarnings']/../span[@class='Checkbox-span']")
    private WebElement warningsCheckbox;

    @FindBy(xpath = "//input[@id='HasRecalls']/../span[@class='Checkbox-span']")
    private WebElement recallsCheckbox;

    @FindBy(xpath = "//input[@id='VIN']/following-sibling::span")
    private WebElement vinNumError;

    public HashMap<String, WebElement> getElementsWithPlaceholder() {
        return new HashMap<String, WebElement>() {{
            put("Has this text", hasThisTextField);
            put("Customer", customerField);
            put("Owner", ownerField);
            put("Advisor", advisorField);
            put("Employee", employeeField);
        }};
    }

    public VNextBOSRAdvancedSearchDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
