package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;


import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TeamWorkOrdersScreen extends BaseTypeScreenWithTabs {

	/*@iOSXCUITFindBy(accessibility  = "Monitor")
    private IOSElement womonitor;

	@iOSXCUITFindBy(accessibility = "Search")
    private IOSElement searchbtn;

	@iOSXCUITFindBy(accessibility = "Edit")
    private IOSElement editmanu;

	@iOSXCUITFindBy(accessibility = "invoice new")
    private IOSElement invoicenewbtn;

	@iOSXCUITFindBy(accessibility = "Location")
    private IOSElement locationfld;

	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement searccsavebtn;*/

	@iOSXCUITFindBy(accessibility = "TeamOrdersPageTableLeft")
	private IOSElement teamOrdersPageTable;

	@iOSXCUITFindBy(accessibility = "Add")
	private IOSElement addorderbtn;

	public TeamWorkOrdersScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);

	}

	public void waitTeamWorkOrdersScreenLoaded() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 35);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("TeamOrdersPageTableLeft")));
	}

	public void clickOnWO(String wonumber) {
		waitTeamWorkOrdersScreenLoaded();
		appiumdriver.findElementByAccessibilityId(wonumber).click();
	}

	public OrderMonitorScreen selectWOMonitor() {
		appiumdriver.findElementByAccessibilityId("Monitor").click();
		return new OrderMonitorScreen();
	}

	public void selectEditWO() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}

	public InvoiceInfoScreen selectWOInvoiceType(IInvoicesTypes invoiceType) {
		appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).click();
		return new InvoiceInfoScreen();
	}

	public boolean isCreateInvoiceActivated(String wonumber) {
		return appiumdriver.findElementsByAccessibilityId("invoice new").size() > 0;
	}

	public void clickiCreateInvoiceButton()  {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("invoice new")));
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("invoice new")).click();
	}

	public String getFirstWorkOrderNumberValue() {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelOrderNumber']")).getAttribute("label");
	}

	public void selectWorkOrder(String wonumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(wonumber)));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(wonumber))).click();
	}

	public void selectWorkOrderForEidt(String wonumber) {
		waitTeamWorkOrdersScreenLoaded();
		selectWorkOrder(wonumber);
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}

	public void clickSearchButton() {
		appiumdriver.findElementByAccessibilityId("Search").click();
	}

	public void clickSearchSaveButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		new TeamWorkOrdersScreen();
	}

	public void selectSearchLocation(String _location) {
		appiumdriver.findElementByAccessibilityId("Location").click();
		appiumdriver.findElementByName(_location).click();
	}

	public void selectSearchStatus(String orderStatus) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Status']").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(orderStatus)));
		appiumdriver.findElementByName(orderStatus).click();
	}

	public void setSearchWorkOrderNumber(String woNumber) {
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(woNumber);
	}

	public void setSearchType(String workordertype)  {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Type']").click();
		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype).isDisplayed()) {
			swipeTableUp(wostable.findElementByAccessibilityId(workordertype),
					wostable);
			wostable.findElementByAccessibilityId(workordertype).click();
		}
	}

	public void clickCreateInvoiceIconForWO(String wonumber) {
		waitTeamWorkOrdersScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(wonumber)));
		appiumdriver.findElementByAccessibilityId(wonumber).findElement(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).click();
	}

	public void selectWorkOrderForApprove(String wonumber) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'EntityInfoButtonUnchecked')]")).click();
	}

	public TeamWorkOrdersScreen clickCreateInvoiceIconForTeamWO(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).findElement(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).click();
		return this;
	}

	public boolean isWorkOrderHasApproveIcon(String wonumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_37')]")).size() > 0;
	}

	public TeamWorkOrdersScreen approveWorkOrder(String wo, String employee, String pwd) {
		selectWorkOrderForApprove(wo);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		return this;
	}

	public boolean isWorkOrderHasActionIcon(String wonumber) {
		waitTeamWorkOrdersScreenLoaded();
		return teamOrdersPageTable.findElement(MobileBy.AccessibilityId(wonumber)).findElements(MobileBy.iOSNsPredicateString("name contains 'ButtonImageId_38'")).size() > 0;
		//return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_38')]")).size() > 0;
	}

	public String getPriceValueForWO(String wonumber) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeStaticText[@name='labelOrderAmount']").getAttribute("value");
	}


	public boolean woExists(String wonumber) {
		waitTeamWorkOrdersScreenLoaded();
		return elementExists(wonumber);
	}

	public void clickServiceRequestButton() {
		appiumdriver.findElementByAccessibilityId("Service Request").click();
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = new ServiceRequestdetailsScreen();
		serviceRequestdetailsScreen.waitServiceRequestdetailsScreenLoad();
	}

	public void clickInvoiceIcon() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("invoice new")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("invoice new"))).click();
	}

	public void clickInvoiceType(IInvoicesTypes invoiceType) {
		appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).click();
	}

	public void approveWorkOrderWithoutSignature(String workOrderNumber, String employee, String password) {
		waitTeamWorkOrdersScreenLoaded();
		selectWorkOrderForApprove(workOrderNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(employee, password);
		ApproveSummaryPopup approveSummaryPopup = new ApproveSummaryPopup();
		approveSummaryPopup.clickApproveButton();
	}

}
