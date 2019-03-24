package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBORepairOrderDetailsPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//*[@data-bind='click: startRO']")
	private WebElement startorderbtn;
	
	@FindBy(xpath = "//div[@class='order-info-details']/div")
	private WebElement orderdetails;
	
	@FindBy(id = "orderServices")
	private WebElement orderservicestable;

    @FindBy(id = "reconmonitordetails-view")
    private WebElement roDetailsSection;

    @FindBy(xpath = "//input[contains(@data-bind, 'data.stockNo')]")
    private WebElement stockNumInputField;

    @FindBy(xpath = "//input[contains(@data-bind, 'data.roNo')]")
    private WebElement roNumInputField;

    @FindBy(xpath = "//span[text()='Phase']")
    private WebElement phaseTextElement;

    @FindBy(xpath = "//h5[@id='breadcrumb']//strong[text()='Repair Orders']")
    private WebElement repairOrdersBackwardsLink;

//    @FindBy(xpath = "//div[@class='status']//span[@title]")
//    private WebElement statusDropDown; //todo use the locator instead of statusListBoxOptions.get(0)
                                        // todo after the developers add the unique identifiers;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']")
    private WebElement statusDropDown;

    @FindBy(xpath = "//div[@class='status']//span[@title]")
    private WebElement statusListBox;

    @FindBy(xpath = "//div[contains(@class, 'priority')]//span[@title]")
    private WebElement priorityListBox;

    @FindBy(id = "reconmonitordetails-view-add-order-button")
    private WebElement addNewServiceButton;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> statusListBoxOptions; //todo add unique identifiers

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> vendorListBoxOptions; //todo add unique identifiers

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> technicianListBoxOptions; //todo add unique identifiers

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> priorityListBoxOptions; //todo add unique identifiers

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> serviceStatusListBoxOptions;

    @FindBy(xpath = "//i[@class='switchTable icon-arrow-down5']")
    private WebElement servicesExpandArrow;

    @FindBy(xpath = "//div[@id='order']//i[contains(@class, 'icon-flag')]")
    private WebElement flagIcon;

    @FindBy(xpath = "//div[@id='order']//div[@class='drop flags']")
    private WebElement flagsDropDown;

    @FindBy(xpath = "//button[@title='Log Info']")
    private WebElement logInfoButton;

    @FindBy(xpath = "//div[@data-template='order-service-item-template']//div[@class='clmn_5']//span[@title]")
    private List<WebElement> servicesStatusWidgetList;

    @FindBy(xpath = "//div[@class='serviceRow theader clearfix']/div")
    private List<WebElement> servicesTableColumns;

    public VNextBORepairOrderDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        wait.until(ExpectedConditions.visibilityOf(startorderbtn));
    }

    public boolean isRoDetailsSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(roDetailsSection));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public VNextBORepairOrderDetailsPage clickFlagIcon() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(flagIcon)).click();
        return this;
    }

    public boolean isFlagsDropDownOpened() {
        try {
            waitShort.until(ExpectedConditions.visibilityOf(flagsDropDown));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public VNextBORepairOrderDetailsPage selectFlagColor(String color) {
        final WebElement flagColorElement =
                driver.findElement(By.xpath("//div[@class='drop flags']//span[contains(@data-bind, '" + color + "')]"));
        try {
            wait.until(ExpectedConditions
                    .elementToBeClickable(flagColorElement)).click();
            wait.until(ExpectedConditions.attributeToBe(flagColorElement, "class", "active"));
            wait.until(ExpectedConditions.invisibilityOf(flagsDropDown));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        return this;
    }
	
	public String getRepairOrderActivePhaseStatus() {
		return orderdetails.findElement(By.id("phaseName")).getText().trim();
	}
	
	public String getRepairOrderCompletedValue() {
		return orderdetails.findElement(By.id("progressBarText")).getText().trim();
	}
	
	public void expandRepairOrderServiceDetailsTable() {
		if (orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).isDisplayed()) {
			orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).click();
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.visibilityOf(orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-up5']"))));
		}
	}
	
	public void clickStartOrderButton() {
		startorderbtn.click();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.invisibilityOf(startorderbtn));
		waitABit(1000);
	}
	
	public boolean isStartOrderButtonVisible() {
		return startorderbtn.isDisplayed();
	}
	
	public String getRepairOrderServicesPhaseStatus() {
		return orderservicestable.findElement(By.xpath(".//div[@class='clmn_5']/div/div")).getText().trim();
	}
	
	public String getRepairOrderServicesStatus(String serviceName) {
		WebElement servicerow = getRepairOrderServiceColumn(serviceName);
		return servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div")).getText().trim();
	}
	
	public void changeStatusForrepairorderService(String serviceName, String newStatus) {
		waitABit(2000);
		WebElement servicerow = getRepairOrderServiceColumn(serviceName);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div/span/span")))).click();
		servicerow = getRepairOrderServiceColumn(serviceName);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div/span/span")))).click();
		waitABit(2000);
		List<WebElement> popups = driver.findElements(By.xpath("//div[@class='k-list-scroller']/ul[@data-role='staticlist']"));
		for (WebElement pp : popups)
			if (pp.isDisplayed()) {
				pp.findElement(By.xpath("./li[text()='" +  newStatus + "']")).click();
				
			}
		WebDriverWait wait = new WebDriverWait(driver, 15, 1);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='k-loading-mask']")));
		waitABit(1000);
	}
	
	public WebElement getRepairOrderServiceColumn(String serviceName) {
		WebElement servicerow = null;
		List<WebElement> servicesrows = orderservicestable.findElement(By.xpath(".//div[@class='innerTable']"))
				.findElements(By.xpath("./div[@class='serviceRow']"));
		for (WebElement srvrow : servicesrows)
			if (srvrow.findElement(By.xpath(".//div[@class='clmn_2']/strong")).getText().trim().equals(serviceName)) {
				servicerow = srvrow;
				break;
			}
		return servicerow;
	}

	public VNextBORepairOrdersWebPage clickBackwardsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(repairOrdersBackwardsLink)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrdersWebPage.class);
    }

	public VNextBORepairOrderDetailsPage typeStockNumber(String stockNumber) {
        clearAndTypeOrderNumber(stockNumInputField, stockNumber);
        return this;
    }

	public VNextBORepairOrderDetailsPage typeRoNumber(String roNumber) {
        clearAndTypeOrderNumber(roNumInputField, roNumber);
        return this;
    }

    private void clearAndTypeOrderNumber(WebElement inputField, String roNumber) {
        clearAndType(inputField, roNumber);
        wait.until(ExpectedConditions.elementToBeClickable(phaseTextElement)).click();
    }

    public VNextBORepairOrderDetailsPage setStatus(String status) {
        clickStatusBox();
        selectStatus(status);
        return this;
    }

    private VNextBORepairOrderDetailsPage clickStatusBox() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(statusListBox)).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage selectStatus(String status) {
//        selectOptionInDropDown(statusDropDown, statusListBoxOptions, status); todo use this line after unique identifiers will be implemented
        selectOptionInDropDown(statusListBoxOptions.get(0), statusListBoxOptions, status);
        return this;
    }

    public VNextBORepairOrderDetailsPage setVendor(String serviceId, String vendor) {
        clickVendorBox(serviceId);
        selectVendor(vendor);
        return this;
    }

    private VNextBORepairOrderDetailsPage clickVendorBox(String serviceId) {
        waitForLoading();
//        wait.until(ExpectedConditions.elementToBeClickable(statusListBox)).click();
        WebElement element = driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
                + "']//span[contains(@class, 'team-dropdown')]"));
        actions.moveToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage selectVendor(String vendor) {
//        selectOptionInDropDown(vendorDropDown, vendorListBoxOptions, vendor); todo use this line after unique identifiers will be implemented
        selectOptionInDropDown(vendorListBoxOptions.get(0), vendorListBoxOptions, vendor);
        return this;
    }

    public VNextBORepairOrderDetailsPage setTechnician(String serviceId, String technician) {
        clickTechnicianBox(serviceId);
        selectTechnician(technician);
        return this;
    }

    private VNextBORepairOrderDetailsPage clickTechnicianBox(String serviceId) {
        waitForLoading();
//        wait.until(ExpectedConditions.elementToBeClickable(statusListBox)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-order-service-id='" + serviceId
                + "']//span[contains(@class, 'technician-dropdown')]"))).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage selectTechnician(String technician) {
//        selectOptionInDropDown(vendorDropDown, vendorListBoxOptions, vendor); todo use this line after unique identifiers will be implemented
        selectOptionInDropDown(technicianListBoxOptions.get(0), technicianListBoxOptions, technician);
        return this;
    }

    public VNextBORepairOrderDetailsPage setPriority(String priority) {
        clickPriorityBox();
        selectPriority(priority);
        return this;
    }

    private VNextBORepairOrderDetailsPage clickPriorityBox() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(priorityListBox)).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage selectPriority(String priority) {
//        selectOptionInDropDown(priorityDropDown, priorityListBoxOptions, priority); todo use this line after unique identifiers will be implemented
        selectOptionInDropDown(priorityListBoxOptions.get(0), priorityListBoxOptions, priority);
        return this;
    }

    public VNextBORepairOrderDetailsPage expandServicesTable() {
        wait.until(ExpectedConditions.elementToBeClickable(servicesExpandArrow)).click();
        wait.until(ExpectedConditions.invisibilityOf(servicesExpandArrow));
        return this;
    }

    public String getServiceDescription(String serviceId) {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@class='serviceRow' and @data-order-service-id='"
                        + serviceId + "']/div[@class='clmn_2']/div[@title]")))
                .getText();
    }

    public String getServiceQuantity(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".000");
    }

    public String getServiceLaborTime(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".00 hr");
    }

    public String getServicePrice(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_3 grid__number']/span", ".00")
                .replace("$", "");
    }

    public String getServiceVendorPrice(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_3_1']/span", ".00")
                .replace("$", "");
    }

    public VNextBORepairOrderDetailsPage setServiceVendorPrice(String serviceId, String serviceDescription, String newValue) {
        setTextValue(serviceId, serviceDescription, "/div[@class='clmn_3_1']/input", newValue);
        return this;
    }

    public VNextBORepairOrderDetailsPage setServiceVendor(String serviceId, String serviceDescription, String newValue) { //todo!!!
        setTextValue(serviceId, serviceDescription, "/div[@class='clmn_3_1']/input", newValue);
        return this;
    }

    private String getTextValue(String serviceId, String xpath, String replacement) {
        final WebElement element = getElementInServicesTable(serviceId, xpath);
        setAttributeWithJS(element, "style", "display: block;");
        final String text = wait.until(ExpectedConditions
                .visibilityOf(element)).getText().replace(replacement, "");
        setAttributeWithJS(element, "style", "display: none;");
        return text;
    }

    private void setTextValue(String serviceId, String serviceDescription, String xpath, String newValue) {
        final WebElement element = getElementInServicesTable(serviceId, xpath);
        scrollToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        actions.sendKeys(element, Keys.DELETE);
        element.sendKeys(newValue);
        clickServiceDescriptionName(serviceDescription);
    }

    private void clickServiceDescriptionName(String serviceDescription) {
        Objects.requireNonNull(wait.until(ExpectedConditions
                .elementToBeClickable(getServiceByName(serviceDescription))))
                .click();
    }

    private WebElement getElementInServicesTable(String serviceId, String xpath) {
        return driver.findElement(By.xpath("//div[@class='serviceRow' and @data-order-service-id='"
                    + serviceId + "']" + xpath));
    }

    public String getServiceId(String description) {
        final WebElement serviceElement = getServiceByName(description);
        if (serviceElement != null) {
            final String id = wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(serviceElement))
                    .findElement(By.xpath(".//../..")).getAttribute("data-order-service-id");
            System.out.println(id);
            return id;
        }
            return "";
    }

    @Nullable
    private WebElement getServiceByName(String service) {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//div[text()='" + service + "']"))));
        } catch (NoSuchElementException ignored) {
            return null;
        }
    }

    public VNextBOAddNewServiceMonitorDialog clickAddNewServiceButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewServiceButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOAddNewServiceMonitorDialog.class);
    }

//    public VNextBOAddNewServiceMonitorDialog selectServiceStatusForFirstService() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(servicesStatusWidgetList));
//        final WebElement serviceStatus = servicesStatusWidgetList.get(0);
//        wait.until(ExpectedConditions.elementToBeClickable(serviceStatus)).click();
//        wait.until(ExpectedConditions.attributeContains(serviceStatus, "aria-expanded", "true"));
//    }


    public VNextBORepairOrderDetailsPage setServiceStatusForService(String serviceId, String status) {
        clickServiceStatusBox(serviceId);
        selectServiceStatus(status);
        return this;
    }
    public VNextBORepairOrderDetailsPage setServiceStatusForService(int order, String status) {
        clickServiceStatusBox(order);
        selectServiceStatus(status);
        return this;
    }

    private VNextBORepairOrderDetailsPage clickServiceStatusBox(int order) {
        waitForLoading();
        final WebElement serviceStatus = servicesStatusWidgetList.get(order);
        wait.until(ExpectedConditions.elementToBeClickable(serviceStatus)).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage clickServiceStatusBox(String serviceId) {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-order-service-id='" + serviceId
                + "']//div[contains(@data-bind, 'orderServiceStatusName')]/../span[@title]"))).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage selectServiceStatus(String status) {
//        selectOptionInDropDown(statusDropDown, statusListBoxOptions, status); todo use this line after unique identifiers will be implemented
        selectOptionInDropDown(serviceStatusListBoxOptions.get(0), serviceStatusListBoxOptions, status);
        return this;
    }

    public List<String> getServicesTableHeaderValues() {
        wait.until(ExpectedConditions.visibilityOfAllElements(servicesTableColumns));
        final List<String> stringCollection = servicesTableColumns
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        return stringCollection.subList(1, stringCollection.size());
    }

    public String getServiceStatusValue(String serviceId) {
        final WebElement serviceStatusValue = driver.findElement(By.xpath(
                "//div[@data-order-service-id='" + serviceId + "']//div[contains(@data-bind, 'orderServiceStatusName')]"));
        setAttributeWithJS(serviceStatusValue, "style", "display: block;");
        final String value = serviceStatusValue.getText();
        setAttributeWithJS(serviceStatusValue, "style", "display: none;");
        return value;
    }

    public VNextBOAuditLogDialog clickLogInfoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logInfoButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOAuditLogDialog.class);
    }
}