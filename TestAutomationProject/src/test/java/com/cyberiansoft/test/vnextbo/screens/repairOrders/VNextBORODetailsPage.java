package com.cyberiansoft.test.vnextbo.screens.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class VNextBORODetailsPage extends VNextBOBaseWebPage {

	@FindBy(xpath = "//*[@data-bind='click: startRO']")
	private WebElement startorderbtn;

	@FindBy(xpath = "//div[@class='order-info-details']/div")
	private WebElement orderDetails;

	@FindBy(id = "phaseName")
	private WebElement orderDetailsPhaseName;

	@FindBy(id = "orderServices")
	private WebElement orderservicestable;

	@FindBy(id = "reconmonitordetails-view")
	private WebElement roDetailsSection;

	@FindBy(xpath = "//div[@class='status']//span[@class='k-widget k-dropdown k-header']//span[@class='k-input']")
	private WebElement roStatusElement;

	@FindBy(xpath = "//div[@class='order-info-details']/div[contains(@class, 'on-hold')]")
	private WebElement onHoldValue;

	@FindBy(xpath = "//input[contains(@data-bind, 'data.stockNo')]")
	private WebElement stockNumInputField;

	@FindBy(xpath = "//input[contains(@data-bind, 'data.roNo')]")
	private WebElement roNumInputField;

	@FindBy(xpath = "//span[text()='Phase']")
	private WebElement phaseTextElement;

	@FindBy(className = "breadcrumbs")
	private WebElement mainBreadCrumbsLink;

	@FindBy(xpath = "//strong[contains(@data-bind, 'breadcrumb.last')]")
	private WebElement lastBreadCrumb;

	@FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
	private WebElement locationExpanded;

	@FindBy(xpath = "//div[@class='k-animation-container']")
	private WebElement dropDownContainer;

	@FindBy(id = "reconmonitor-details-status-list")
	private WebElement statusDropDownContainer;

	@FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']")
	private WebElement statusDropDown;

	@FindBy(xpath = "//input[@data-automation-id='reconmonitor-details-status']/../span")
	private WebElement statusListBox;

	@FindBy(xpath = "//div[contains(@class, 'priority')]//span[@title]")
	private WebElement priorityListBox;

	@FindBy(id = "reconmonitordetails-view-add-order-button")
	private WebElement addNewServiceButton;

	@FindBy(xpath = "//i[@class='switchTable icon-arrow-down5']")
	private WebElement servicesExpandArrow;

	@FindBy(xpath = "//div[@id='order']//i[contains(@class, 'icon-flag')]")
	private WebElement flagIcon;

	@FindBy(xpath = "//div[@id='order']//div[@class='drop flags']")
	private WebElement flagsDropDown;

	@FindBy(xpath = "//button[@title='Log Info']")
	private WebElement logInfoButton;

	@FindBy(xpath = "//div[@class='order-info-details']//span[text()='More information']")
	private WebElement moreInformationLink;

	@FindBy(xpath = "//div[@class='order-info-details']//span[text()='Less information']")
	private WebElement lessInformationLink;

	@FindBy(xpath = "//div[@class='orderInfoWrapper secondLine clearfix']")
	private WebElement moreInformationBlock;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_2']")
	private WebElement phaseName;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_3_1']/span")
	private WebElement phaseVendorPrice;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_4']/div")
	private WebElement phaseVendorTechnician;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_5']//div[contains(@data-bind, 'statusText')]")
	private WebElement phaseStatus;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]")
	private WebElement phaseActionsTrigger;

	@FindBy(xpath = "//span[contains(@class, 'location-name')]")
	private WebElement locationElement;

	@FindBy(xpath = "//div[@data-template='order-service-item-template']//div[@class='clmn_5']//span[@title]")
	private List<WebElement> servicesStatusWidgetList;

	@FindBy(xpath = "//div[@class='serviceRow theader clearfix']/div")
	private List<WebElement> servicesTableColumns;

	@FindBy(xpath = "//div[@class='row order-info-content']//p/span[text()]")
	private List<WebElement> moreInformationFields;

	@FindBy(xpath = "//strong[contains(@data-bind, 'amountField.totalAmountF')]")
	private WebElement totalServicePrice;

	@FindBy(xpath = "//div[contains(@data-bind, 'cachedServices')]")
	private WebElement toBeAddedLaterServiceNotification;

	@FindBy(xpath = "//div[contains(@class, 'innerTable')]//div[@class='clmn_3_1']/span")
	private List<WebElement> vendorPricesList;

	@FindBy(xpath = "//tbody[contains(@data-template, 'part-list') and not (contains(@data-bind, 'Cached'))]/tr")
	private List<WebElement> partsList;

	@FindBy(xpath = "//div[contains(@data-bind, 'partActionsVisible')]")
	private List<WebElement> partsActions;

	@FindBy(xpath = "//div[@id='reconmonitordetails-parts']//b")
	private List<WebElement> partsNames;

	@FindBy(xpath = "//div[@id='reconmonitordetails-parts']//td[@class='grid__centered'][4]/div")
	private List<WebElement> partsOrderedFromTableValues;

	@FindBy(xpath = "//ul[@class='k-list k-reset' and @aria-hidden='false']/li")
	private List<WebElement> listBoxOptions;


	public VNextBORODetailsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public List<String> getVendorPricesValuesList() {
		waitABit(1000);
		for (WebElement vendorPrice : vendorPricesList) {
			setAttributeWithJS(vendorPrice, "style", "display: block");
		}
		wait.until(ExpectedConditions.visibilityOfAllElements(vendorPricesList));
		final List<String> vendorPricesValuesList = vendorPricesList.stream()
				.map(WebElement::getText)
				.peek(System.out::println)
				.collect(Collectors.toList());

		for (WebElement vendorPrice : vendorPricesList) {
			setAttributeWithJS(vendorPrice, "style", "display: none");
		}
		return vendorPricesValuesList;
	}

	public boolean isRoDetailsSectionDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(roDetailsSection));
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	public String getRoStatus() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(roStatusElement)).getText();
		} catch (Exception ignored) {
			return "";
		}
	}

	public boolean isImageOnHoldStatusDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(onHoldValue));
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	public VNextBORODetailsPage clickFlagIcon() {
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

	public VNextBORODetailsPage selectFlagColor(String color) {
		final WebElement flagColorElement =
				driver.findElement(By.xpath("//div[@class='drop flags']//span[contains(@title, '" + color + "')]"));
		try {
			Utils.clickElement(flagColorElement);
			WaitUtilsWebDriver.waitForAttributeToBe(flagColorElement, "class", "active");
			WaitUtilsWebDriver.waitForInvisibility(flagsDropDown);
		} catch (Exception e) {
			Assert.fail("The flag " + color + " hasn't been selected", e);
		}
		return this;
	}

	public String getRepairOrderActivePhaseStatus() {
		return wait.until(ExpectedConditions.visibilityOf(orderDetails.findElement(By.id("serviceName")))).getText().trim();
	}

	public String getRepairOrderCompletedValue() {
		return wait.until(ExpectedConditions.visibilityOf(orderDetails.findElement(By.id("progressBarText")))).getText().trim();
	}

	public void expandRepairOrderServiceDetailsTable() {
		if (orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).isDisplayed()) {
			orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).click();
			waitLong.until(ExpectedConditions.visibilityOf(orderservicestable
					.findElement(By.xpath(".//i[@class='switchTable icon-arrow-up5']"))));
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
				pp.findElement(By.xpath("./li[text()='" + newStatus + "']")).click();

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

	public void clickRepairOrdersBackwardsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(mainBreadCrumbsLink)).click();
		waitForLoading();
	}

	public void typeStockNumber(String stockNumber) {
		clearAndTypeOrderNumber(stockNumInputField, stockNumber);
	}

	public void typeRoNumber(String roNumber) {
		clearAndTypeOrderNumber(roNumInputField, roNumber);
	}

	public boolean isToBeAddedLaterServiceNotificationDisplayed() {
		return Utils.isElementDisplayed(toBeAddedLaterServiceNotification, 10);
	}

	public boolean isServiceNotificationToBeAddedLaterDisplayed() {
		return Utils.isElementDisplayed(toBeAddedLaterServiceNotification, 10);
	}

	private void clearAndTypeOrderNumber(WebElement inputField, String roNumber) {
		clearAndType(inputField, roNumber);
		wait.until(ExpectedConditions.elementToBeClickable(phaseTextElement)).click();
	}

	public WebElement getDropDownContainer() {
		return WaitUtilsWebDriver.waitForVisibility(dropDownContainer, 7);
	}

	public WebElement getStatusDropDownContainer() {
		return WaitUtilsWebDriver.waitForVisibility(statusDropDownContainer, 7);
	}

	public VNextBORODetailsPage setStatus(String status) {
		clickStatusBox();
		selectStatus(status);
		return this;
	}

	private void clickStatusBox() {
		waitForLoading();
		Utils.clickElement(statusListBox);
	}

	private void selectStatus(String status) {
		final List<WebElement> statusListBoxOptions = getDropDownContainer()
				.findElements(By.xpath("//ul[@data-role='staticlist']/li"));
		selectOptionInDropDown(statusListBoxOptions.get(0), statusListBoxOptions, status);
	}

	public void setVendor(String serviceId, String vendor) {
		clickVendorBox(serviceId);
		selectVendor(vendor);
	}

	private void clickVendorBox(String serviceId) {
		waitForLoading();
		WebElement element = driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
				+ "']//span[contains(@class, 'team-dropdown')]"));
		actions.moveToElement(element).build().perform();
		Utils.clickElement(element);
	}

	private void selectVendor(String vendor) {
		selectOptionInDropDown(listBoxOptions.get(0), listBoxOptions, vendor);
	}

	public void setTechnician(String serviceId, String technician) {
		clickTechnicianBox(serviceId);
		selectTechnician(technician);
	}

	private void clickTechnicianBox(String serviceId) {
		waitForLoading();
		WebElement element = driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
				+ "']//span[contains(@class, 'technician-dropdown')]"));
		actions.moveToElement(element).build().perform();
		Utils.clickElement(element);
	}

	private void selectTechnician(String technician) {
		selectOptionInDropDown(listBoxOptions.get(0), listBoxOptions, technician);
	}

	public void setPriority(String priority) {
		clickPriorityBox();
		selectPriority(priority);
	}

	private void clickPriorityBox() {
		waitForLoading();
		wait.until(ExpectedConditions.elementToBeClickable(priorityListBox)).click();
	}

	private void selectPriority(String priority) {
		final List<WebElement> priorityListBoxOptions = getDropDownContainer()
				.findElements(By.xpath("//ul[@data-role='staticlist']/li"));
		selectOptionInDropDown(priorityListBoxOptions.get(0), priorityListBoxOptions, priority);
	}

	public void expandServicesTable() {
		Utils.clickElement(servicesExpandArrow);
		WaitUtilsWebDriver.waitForInvisibility(servicesExpandArrow);
		WaitUtilsWebDriver.waitForLoading();
	}

	public int getNumberOfVendorTechnicianOptionsByName(String name) {
		try {
			return wait
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-template='" +
							"order-service-item-template']//div[@class='clmn_4']//span[@class='k-input' and text()='" +
							name + "']"))).size();
		} catch (Exception ignored) {
			return 0;
		}
	}

	public String getServiceDescription(String serviceId) {
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='serviceRow' and @data-order-service-id='"
						+ serviceId + "']/div[@class='clmn_2']/div[@title]")))
				.getText();
	}

	public String getServiceQuantity(String serviceId) {
		System.out.println(serviceId);
		return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".000");
	}

	public String getServiceLaborTime(String serviceId) {
		return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".00 hr");
	}

	public String getServicePrice(String serviceId) {
		String result = getTextValue(serviceId, "/div[@class='clmn_3 grid__number']/span");
		return StringUtils.substringBefore(result, ",");
	}

	public String getServiceVendorPrice(String serviceId) {
		return getTextValue(serviceId, "/div[@class='clmn_3_1']/span", ".00")
				.replace("$", "");
	}

	public void setServiceVendorPrice(String serviceId, String serviceDescription, String newValue) {
		setTextValue(serviceId, serviceDescription, "/div[@class='clmn_3_1']/input", newValue);
	}

	public void setServiceVendor(String serviceId, String serviceDescription, String newValue) { //todo!!!
		setTextValue(serviceId, serviceDescription, "/div[@class='clmn_3_1']/input", newValue);
	}

	public void setServiceQuantity(String serviceId, String serviceDescription, String newValue) {
//		try { toto delete?
			setTextValue(serviceId, serviceDescription, "//div[@class='clmn_2_1 grid__number']/input", newValue);
//		} catch (TimeoutException e) {
//			((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
//
//			List<String> tabs = new ArrayList<>(driver.getWindowHandles());
//			driver.switchTo().window(tabs.get(1));
//
//			WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURLMain());
//			BackOfficeLoginWebPage loginPage = PageFactory.initElements(driver, BackOfficeLoginWebPage.class);
//			BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(driver, BackOfficeHeaderPanel.class);
//			loginPage.userLogin(BOConfigInfo.getInstance().getUserNadaName(), BOConfigInfo.getInstance().getUserNadaPassword());
//
//			ServicesWebPage servicesPage = new ServicesWebPage(this.driver);
//			backOfficeHeader.clickCompanyLink();
//			CompanyWebPage companyWebPage = new CompanyWebPage(this.driver);
//			companyWebPage.clickServicesLink();
//
//			servicesPage.setServiceSearchCriteria(serviceDescription);
//			servicesPage.clickFindButton();
//			servicesPage.clickEditService(serviceDescription);
//			NewServiceDialogWebPage newServiceDialogWebPage = new NewServiceDialogWebPage(this.driver);
//			newServiceDialogWebPage.clickMultipleCheckbox();
//			newServiceDialogWebPage.clickOKButton();
//			newServiceDialogWebPage.closeNewTab(tabs.get(0));
//			refreshPage();
//			expandServicesTable();
//			setTextValue(serviceId, serviceDescription, "//div[@class='clmn_2_1 grid__number']/input", newValue);
//		}
	}

	public void setServicePrice(String serviceId, String serviceDescription, String newValue) {
		setTextValue(serviceId, serviceDescription, "//div[@class='clmn_3 grid__number']/input", newValue);
	}

	private String getTextValue(String serviceId, String xpath, String replacement) {
		final WebElement element = getElementInServicesTable(serviceId, xpath);
		Utils.setAttributeWithJS(element, "style", "display: block;");
		final String text = WaitUtilsWebDriver.waitForVisibility(element).getText().replace(replacement, "");
		Utils.setAttributeWithJS(element, "style", "display: none;");
		return text;
	}

	private String getTextValue(String serviceId, String xpath) {
		final WebElement element = getElementInServicesTable(serviceId, xpath);
		Utils.setAttributeWithJS(element, "style", "display: block;");
		final String text = WaitUtilsWebDriver.waitForVisibility(element).getText();
		Utils.setAttributeWithJS(element, "style", "display: none;");
		return text;
	}

	private void setTextValue(String serviceId, String serviceDescription, String xpath, String newValue) {
		final WebElement element = getElementInServicesTable(serviceId, xpath);
		Utils.clearAndType(element, newValue);
		clickServiceDescriptionName(serviceDescription);
		WaitUtilsWebDriver.waitABit(500);
	}

	private void clickServiceDescriptionName(String serviceDescription) {
		Objects.requireNonNull(WaitUtilsWebDriver
				.waitForElementToBeClickable(getServiceByName(serviceDescription)))
				.click();
	}

	public WebElement getElementInServicesTable(String serviceId, String xpath) {
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
			try {
				final WebElement element = driver.findElement(By.xpath("//div[contains(text(), '" + service + "')]"));
				wait.until(ExpectedConditions.visibilityOf(element));
				return element.getText().trim().equals(service) ? element : null;
			} catch (NoSuchElementException ignore) {
			}
			return null;
		}
	}

	public void clickAddNewServiceButton() {
		wait.until(ExpectedConditions.elementToBeClickable(addNewServiceButton)).click();
		waitForLoading();
	}

//    public VNextBOAddNewServiceMonitorDialog selectServiceStatusForFirstService() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(servicesStatusWidgetList));
//        final WebElement serviceStatus = servicesStatusWidgetList.get(0);
//        wait.until(ExpectedConditions.elementToBeClickable(serviceStatus)).click();
//        wait.until(ExpectedConditions.attributeContains(serviceStatus, "aria-expanded", "true"));
//    }

	public void setServiceStatusForService(String serviceId, String status) {
		clickServiceStatusBox(serviceId);
		selectServiceStatus(status);
	}

	public void setServiceStatusForService(int order, String status) {
		clickServiceStatusBox(order);
		selectServiceStatus(status);
	}

	private void clickServiceStatusBox(int order) {
		waitForLoading();
		final WebElement serviceStatus = servicesStatusWidgetList.get(order);
		wait.until(ExpectedConditions.elementToBeClickable(serviceStatus)).click();
	}

	private void clickServiceStatusBox(String serviceId) {
		waitForLoading();
		final WebElement service = driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
				+ "']//div[contains(@data-bind, 'orderServiceStatusName')]/../span[@title]"));
		actions.moveToElement(service).build().perform();
		Utils.clickElement(service);
	}

	private void selectServiceStatus(String status) {
		final List<WebElement> serviceStatusListBoxOptions = driver
				.findElements(By.xpath("//div[@aria-hidden='false']//ul[@class='k-list k-reset']/li"));
		selectOptionInDropDown(serviceStatusListBoxOptions.get(0), serviceStatusListBoxOptions, status, true);
	}

	public List<String> getServicesTableHeaderValues() {
		WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(servicesTableColumns);
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

	public void clickLogInfoButton() {
		wait.until(ExpectedConditions.elementToBeClickable(logInfoButton)).click();
		waitForLoading();
	}

	private WebElement clickActionsIcon(String serviceId) {
		final WebElement actionsIcon = driver.findElement(By
				.xpath("//div[@class='serviceRow' and @data-order-service-id='" +
						serviceId + "']//div[@class='clmn_7']/div[contains(@class, 'order-service-menu')]"));
		actions.moveToElement(actionsIcon);
		Utils.clickElement(actionsIcon);
		WaitUtilsWebDriver.waitForVisibility(actionsIcon.findElement(By.xpath("./div[@class='drop checkout']")));
		return actionsIcon;
	}

	public void openNotesDialog(String serviceId) {
		clickActionsIcon(serviceId);
		Utils.clickElement(By
				.xpath("//div[@class='serviceRow' and @data-order-service-id='" + serviceId
						+ "']//div[@class='clmn_7']/div[contains(@class, 'order-service-menu')]//label[text()='Notes']"));
		waitForLoading();
	}

	public void openMoreInformation() {
		clickMoreInformationLink();
		wait.until(ExpectedConditions.visibilityOf(moreInformationBlock));
	}

	public void closeMoreInformation() {
		clickLessInformationLink();
		wait.until(ExpectedConditions.invisibilityOf(moreInformationBlock));
	}

	private void clickMoreInformationLink() {
		wait.until(ExpectedConditions.elementToBeClickable(moreInformationLink)).click();
	}

	private void clickLessInformationLink() {
		wait.until(ExpectedConditions.elementToBeClickable(lessInformationLink)).click();
	}

	public List<String> getMoreInformationFieldsText() {
		wait.until(ExpectedConditions.visibilityOfAllElements(moreInformationFields));
		return moreInformationFields
				.stream()
				.map(WebElement::getText)
				.peek(System.out::println)
				.collect(Collectors.toList());
	}

	public String getServiceStartedDate(String serviceId) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-order-service-id='" + serviceId
					+ "']//div[@class='clmn_6']//span[text()][1]"))).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getServiceCompletedDate(String serviceId) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-order-service-id='" + serviceId
					+ "']//div[@class='clmn_6']//span[text()][2]"))).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public void hoverOverServiceHelperIcon(String serviceId) {
		try {
//            final WebElement helpInfo = driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
//                    + "']//span[@class='helpInfo']/.."));
			final WebElement helpInfo = driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
					+ "']//i[@class='help']"));
			wait.until(ExpectedConditions.visibilityOf(helpInfo));
			actions.moveToElement(helpInfo).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickPhaseVendorTechnicianLink() {
		Utils.clickElement(phaseVendorTechnician);
	}

	public boolean isHelpInfoDialogDisplayed(String serviceId, String status) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
					.xpath("//div[@data-order-service-id='" + serviceId
							+ "']//i[@class='help']/span[text()='" + status + "']"))))
					.isDisplayed();
		} catch (TimeoutException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getPhaseNameValue() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(phaseName)).getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getPhaseVendorPriceValue() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(phaseVendorPrice)).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getPhaseVendorTechnicianValue() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(phaseVendorTechnician)).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getPhaseStatusValue() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(phaseStatus)).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean isPhaseActionsTriggerDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(phaseActionsTrigger)).isDisplayed();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isServiceCompletedDateDisplayed(String serviceId) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-order-service-id='" + serviceId
					+ "']//div[@class='clmn_6']//span[text()][2]"))).getText();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getOrderCurrentPhase() {
		try {
			actions
					.moveToElement(WaitUtilsWebDriver.waitForVisibility(orderDetailsPhaseName))
					.build()
					.perform();
			return orderDetailsPhaseName.getText();
		} catch (TimeoutException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getTotalServicesPrice() {
		try {
		    WaitUtilsWebDriver.waitForVisibility(totalServicePrice);
		    return Utils.moveToElement(totalServicePrice).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public void updateTotalServicePrice(String totalPrice) {
		try {
            WaitUtilsWebDriver.waitForVisibility(totalServicePrice);
            Utils.moveToElement(totalServicePrice);
            WaitUtilsWebDriver.getWebDriverWait(20)
                    .until((ExpectedCondition<Boolean>) driver -> !totalPrice.equals(getTotalServicesPrice()));
		} catch (Exception ignored) {}
	}

	public String getFirstPartIdFromPartsList() {
		wait.until(ExpectedConditions.visibilityOfAllElements(partsList));
		return partsList.get(0).getAttribute("data-order-service-id");
	}

	private WebElement getPartActionElement(int index) {
		try {
			final WebElement partAction = partsActions.get(index);
			System.out.println("element");
			System.out.println(partsActions.get(index));
			Utils.clickElement(partAction);
			return WaitUtilsWebDriver.waitForVisibility(partAction.findElement(By.xpath("./div[@class='drop checkout']")));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public WebElement clickPartActionsIconForPart(String part) {
		try {
			WaitUtilsWebDriver.waitForVisibilityOfAllOptions(partsNames, 10);
		} catch (Exception e) {
			Assert.fail("The Parts section is empty", e);
		}
		try {
			final List<String> partsList = partsNames
					.stream()
					.map(WebElement::getText)
					.collect(Collectors.toList());
			return getPartActionElement(partsList.indexOf(part));
		} catch (Exception e) {
			Assert.fail("The Part hasn't been displayed");
		}
		return null;
	}

	public void openNotesDialogForPart(WebElement partsAction) {
		Utils.clickElement(partsAction.findElement(By.xpath(".//label[text()='Notes']")));
		waitForLoading();
	}

	public List<String> getPartsOrderedFromTableValues() {
		wait.until(ExpectedConditions.visibilityOfAllElements(partsOrderedFromTableValues));
		return partsOrderedFromTableValues
				.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
	}
}