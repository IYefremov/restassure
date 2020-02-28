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
public class VNextBORODetailsWebPageNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@id='reconmonitordetails-view']")
    private WebElement orderDetailsSection;

    @FindBy(xpath = "//div[@data-name!='']//div[@class='clmn_2']")
    private List<WebElement> phasesList;

    @FindBy(xpath = "//div[@id='orderServices']//div[@data-item-id]/div[@class='clmn_2']/div[1]")
    private List<WebElement> serviceAndTaskDescriptionsList;

    @FindBy(xpath = "//div[@class='serviceRow theader clearfix']/div[text() != '']")
    private List<WebElement> servicesTableColumnsTitles;

    @FindBy(xpath = "//div[@id='reconmonitordetails-parts']//th")
    private List<WebElement> partsTableColumnsTitles;

    @FindBy(xpath = "//div[@class='clmn_1']/*[@class='switchTable icon-arrow-down5']")
    private List<WebElement> phaseExpanderList;

    @FindBy(xpath = "//div[contains(@data-bind,'phaseReportProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement reportProblemForPhaseActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'resolveProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement resolveProblemForPhaseActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'completePhase') and not(contains(@style,'display: none'))]")
    private WebElement completeCurrentPhaseActionButton;

    @FindBy(xpath = "//div[@class='drop checkout']/div[contains(@data-bind,'phaseCheckIn') and not(contains(@style,'display: none'))]")
    private WebElement checkInActionButton;

    @FindBy(xpath = "//div[@class='drop checkout']/div[contains(@data-bind,'phaseCheckOut') and not(contains(@style,'display: none'))]")
    private WebElement checkOutActionButton;

    @FindBy(xpath = "//div[@class='drop checkout']//div[contains(@data-bind,'serviceNotes') and not(contains(@style,'display: none'))]")
    private WebElement notesActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'phaseStart') and not(contains(@style,'display: none'))]")
    private WebElement startServicesActionButton;

    @FindBy(xpath = "//div[@class='drop checkout']//div[contains(@data-bind,'reportProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement reportProblemForServiceActionButton;

    @FindBy(xpath = "//div[@class='drop checkout']//div[contains(@data-bind,'resolveProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement resolveProblemForServiceActionButton;

    @FindBy(xpath = "//span[@aria-owns='reconmonitor-details-status_listbox']//span[@class='k-input']")
    private WebElement orderStatusDropDown;

    @FindBy(xpath = "//div[@class='reason']//span[@class='k-input']")
    private WebElement orderCloseReason;

    @FindBy(xpath = "//button[@title='Reopen RO']")
    private WebElement reopenOrderButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'PriorityToolbar')]//span[@class='k-input']")
    private WebElement priorityDropDown;

    @FindBy(xpath = "//button[@id='reconmonitordetails-view-add-order-button']")
    private WebElement addServiceButton;

    @FindBy(xpath = "//div[@title='Flag']/i[@class='icon-flag']")
    private WebElement flagIcon;

    @FindBy(xpath = "//div[@class='row moreInfo-content']//span[@data-bind='text: panelText']")
    private WebElement moreInfoSection;

    @FindBy(xpath = "//div[@class='row order-info-content']//p/span[text()!='']")
    private List<WebElement> moreInfoFields;

    @FindBy(xpath = "//tbody[@data-template='repair-order-part-list-item-template']//b")
    private List<WebElement> partServicesNamesList;

    @FindBy(xpath = "//div[@class='linked-items-info__content']//span[@class='pull-left']")
    private List<WebElement> inspectionsList;

    @FindBy(xpath = "//button[@data-bind='click: showLogWindow']")
    private WebElement logInfoButton;

    public WebElement phaseRow(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']"));
    }

    public WebElement actionsMenuButtonForPhase(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//div[not(contains(@style,'display: none;'))]/i[@class='icon-list menu-trigger']"));
    }

    public WebElement expandPhaseButton(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//div[@class='clmn_1']/*[@class='switchTable icon-arrow-down5']"));
    }

    public WebElement collapsePhaseButton(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//div[@class='clmn_1']/*[@class='switchTable icon-arrow-up5']"));
    }

    public WebElement problemIndicatorByPhase(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//i[@class='icon-problem-indicator']"));
    }

    public WebElement phaseTotalPrice(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//span[contains(@data-bind,'vendorPriceTotalF')]"));
    }

    public WebElement phaseStatusDropDownByPhase(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//span[contains(@class,'group-status-dropdown')]//span[@class='k-input']"));
    }

    public WebElement phaseStatusTextByPhase(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//div[contains(@data-bind, 'statusText')]"));
    }

    public WebElement changeTechnicianForPhase(String phase) {

        return phaseRow(phase).findElement(By.xpath(".//div[contains(@data-bind,'showChangeTechnician')]"));
    }

    public WebElement serviceRowByName(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow'])[1]"));
    }

    public WebElement actionsMenuButtonForService(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//i[@class='icon-list menu-trigger']"));
    }

    public WebElement startServiceButtonForService(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//button[contains(@data-bind, 'serviceStart')]"));
    }

    public WebElement serviceDescription(String serviceDescription) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@class='clmn_2']//div[contains(.,'" + serviceDescription + "')]"));
    }

    public WebElement problemIndicatorByService(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//i[@class='icon-problem-indicator' and not(contains(@style,'display: none;'))]"));
    }

    public WebElement serviceIcon(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//i[@class='help']/i"));
    }

    public WebElement serviceNameWebElement(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='clmn_2'])[1]"));
    }

    public WebElement serviceQtyInputField(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//input[contains(@data-bind,'canEditQuantity')]"));
    }

    public WebElement servicePriceInputField(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//input[contains(@data-bind,'canEditPrice')]"));
    }

    public WebElement serviceVendorPriceInputField(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//input[contains(@data-bind,'isVendorPriceEditable')]"));
    }

    public WebElement serviceStatusDropDownByService(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//span[contains(@class,'service-status-dropdown')]//span[@class='k-input']"));
    }

    public WebElement serviceStartedDate(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//p[@data-bind='invisible: showStart']/span[1]"));
    }

    public WebElement serviceCompletedDate(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//p[@data-bind='invisible: showStart']/span[3]"));
    }

    public WebElement serviceHelpIcon(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//i[@class='help']"));
    }

    public WebElement serviceHelpIconHelpInfo(String service) {

        return serviceRowByName(service).findElement(By.xpath(".//span[@class='helpInfo']"));
    }

    public WebElement serviceVendorDropDown(String service) {

        return serviceRowByName(service).findElement(By.xpath("(.//div[@class='clmn_4']//span[@class='k-input'])[1]"));
    }

    public WebElement serviceTechnicianDropDown(String service) {

        return serviceRowByName(service).findElement(By.xpath("(.//div[@class='clmn_4']//span[@class='k-input'])[2]"));
    }

    public WebElement dropDownOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//span[text()='" + optionName + "']"));
    }

    public WebElement flagColorIconByFlagTitle(String title) {

        return driver.findElement(By.xpath("//div[@data-template='reconmonitordetails-flagsContent']/span[@title='" + title + "']"));
    }

    public VNextBORODetailsWebPageNew() {

        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
