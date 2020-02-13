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

    @FindBy(xpath = "//div[@id='orderServices']//div[@data-item-id]/div[@class='clmn_2']/div[1]")
    private List<WebElement> serviceAndTaskDescriptionsList;

    @FindBy(xpath = "//div[@class='clmn_1']/*[@class='switchTable icon-arrow-down5']")
    private List<WebElement> phaseExpanderList;

    @FindBy(xpath = "//div[contains(@data-bind,'phaseReportProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement reportProblemForPhaseActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'resolveProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement resolveProblemForPhaseActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'completePhase') and not(contains(@style,'display: none'))]")
    private WebElement completeCurrentPhaseActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'phaseStart') and not(contains(@style,'display: none'))]")
    private WebElement startServicesActionButton;

    @FindBy(xpath = "//div[@class='drop checkout']//div[contains(@data-bind,'reportProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement reportProblemForServiceActionButton;

    @FindBy(xpath = "//div[@class='drop checkout']//div[contains(@data-bind,'resolveProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement resolveProblemForServiceActionButton;

    @FindBy(xpath = "//span[@aria-owns='reconmonitor-details-status_listbox']//span[@class='k-input']")
    private WebElement orderStatusDropDown;

    @FindBy(xpath = "//button[@title='Reopen RO']")
    private WebElement reopenOrderButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'PriorityToolbar')]//span[@class='k-input']")
    private WebElement priorityDropDown;

    @FindBy(xpath = "//button[@id='reconmonitordetails-view-add-order-button']")
    private WebElement addServiceButton;

    @FindBy(xpath = "//div[@title='Flag']/i[@class='icon-flag']")
    private WebElement flagIcon;

    @FindBy(xpath = "//tbody[@data-template='repair-order-part-list-item-template']//b")
    private List<WebElement> partServicesNamesList;

    public WebElement actionsMenuButtonForPhase(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//div[not(contains(@style,'display: none;'))]/i[@class='icon-list menu-trigger']"));
    }

    public WebElement actionsMenuButtonForService(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//i[@class='icon-list menu-trigger'])[1]"));
    }

    public WebElement startServiceButtonForService(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//button[contains(@data-bind, 'serviceStart')])[1]"));
    }

    public WebElement serviceDescription(String serviceDescription) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@class='clmn_2']//div[contains(.,'" + serviceDescription + "')]"));
    }

    public WebElement expandPhaseButton(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//div[@class='clmn_1']/*[@class='switchTable icon-arrow-down5']"));
    }

    public WebElement collapsePhaseButton(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//div[@class='clmn_1']/*[@class='switchTable icon-arrow-up5']"));
    }

    public WebElement problemIndicatorByPhase(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//i[@class='icon-problem-indicator']"));
    }

    public WebElement problemIndicatorByService(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='clmn_2']//i[@class='icon-problem-indicator' and not(contains(@style,'display: none;'))])[1]"));
    }

    public WebElement serviceNameWebElement(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='clmn_2'])[1]"));
    }

    public WebElement serviceQtyInputField(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//input[contains(@data-bind,'canEditQuantity')])[1]"));
    }

    public WebElement serviceQtyDisplayedText(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//span[contains(@data-bind,'canEditQuantity')])[1]"));
    }

    public WebElement servicePriceInputField(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//input[contains(@data-bind,'canEditPrice')])[1]"));
    }

    public WebElement servicePriceDisplayedText(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//span[contains(@data-bind,'canEditPrice')])[1]"));
    }

    public WebElement serviceVendorPriceInputField(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//input[contains(@data-bind,'isVendorPriceEditable')])[1]"));
    }

    public WebElement serviceVendorPriceDisplayedText(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//span[contains(@data-bind,'isVendorPriceEditable')])[1]"));
    }

    public WebElement phaseTotalPrice(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//span[contains(@data-bind,'vendorPriceTotalF')]"));
    }

    public WebElement phaseStatusDropDownByPhase(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//span[contains(@class,'group-status-dropdown')]//span[@class='k-input']"));
    }

    public WebElement phaseStatusTextByPhase(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//div[contains(@data-bind, 'statusText')]"));
    }

    public WebElement serviceStatusDropDownByService(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("(//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//span[contains(@class,'service-status-dropdown')]//span[@class='k-input'])[1]"));
    }

    public WebElement serviceStartedDate(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//p[@data-bind='invisible: showStart']/span[1]"));
    }

    public WebElement serviceCompletedDate(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//p[@data-bind='invisible: showStart']/span[3]"));
    }

    public WebElement serviceHelpIcon(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//i[@class='help']"));
    }

    public WebElement serviceHelpIconHelpInfo(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@class='clmn_2']//div[contains(.,'" + service + "')]/ancestor::div[@class='serviceRow']//span[@class='helpInfo']"));
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
