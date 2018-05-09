package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.iOSInternalProjectConstants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MyWorkOrdersScreen extends BaseTypeScreenWithTabs {

	private By autosavedworkorder = By.name("EntityInfoButtonUnchecked, AutoSaved");
	
	private By btnwholesale = By.name("btnWholesale");
	private By btnretail = By.name("btnRetail");
	
	/*@iOSFindBy(accessibility = "Add")
    private IOSElement addinspbtn;
	
	@iOSFindBy(accessibility  = "Discard")
    private IOSElement discardbtn;
	
	@iOSFindBy(accessibility  = "Copy Vehicle")
    private IOSElement copyvehiclemenu;
	
	@iOSFindBy(accessibility  = "Copy Services")
    private IOSElement copyservicesmenu;
	
	@iOSFindBy(accessibility  = "Change Customer")
    private IOSElement changecustomermenu;
	
	@iOSFindBy(accessibility  = "Details")
    private IOSElement detailsmenu;
	
	@iOSFindBy(accessibility  = "New Inspection")
    private IOSElement newinspectionmenu;
	
	@iOSFindBy(accessibility  = "Continue")
    private IOSElement continuemenu;
	
	@iOSFindBy(accessibility  = "Discard")
    private IOSElement discardmenu;
	
	@iOSFindBy(accessibility  = "Notes")
    private IOSElement notesmenu;*/
	
	//@iOSFindBy(xpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")
    //private IOSElement firswointable;
	
	/*@iOSFindBy(accessibility = "Share")
    private IOSElement sharebtn;
	
	@iOSFindBy(accessibility = "filter")
    private IOSElement filterbtn;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement donebtn;
	
	@iOSFindBy(accessibility  = "invoice new")
    private IOSElement newinvoice;
	
	@iOSFindBy(accessibility  = "Delete")
    private IOSElement deletebtn;
	
	@iOSFindBy(accessibility  = "Edit")
    private IOSElement editbtn;
	
	@iOSFindBy(accessibility  = "Tech Revenue")
    private IOSElement techrevenuebtn;
	
	@iOSFindBy(accessibility  = "Technicians")
    private IOSElement techniciansmenubtn;
	
	@iOSFindBy(accessibility  = "Service Request")
    private IOSElement servicerequestbtn;*/

	@iOSFindBy(accessibility = "Add")
	private IOSElement addorderbtn;

	public MyWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Orders")));
	}

	public void clickAddOrderButton() {
		addorderbtn.click();
		if (elementExists("Discard"))
			appiumdriver.findElementByAccessibilityId("Discard").click();
	}


	public void addOrderWithSelectCustomer(String customerName, String workOrderType) {
		clickAddOrderButton();
		selectCustomerAndWorkOrderType(customerName, workOrderType);
	}

	private void selectCustomerAndWorkOrderType(String customerName, String workOrderType) {
		CustomersScreen customersscreen = new CustomersScreen(appiumdriver);
		customersscreen.selectCustomer(customerName);
		WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup(appiumdriver);
		workOrderTypesPopup.selectWorkOrderType(workOrderType);
	}

	public void addWorkOrder(String workOrderType) {
		clickAddOrderButton();
		WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup(appiumdriver);
		workOrderTypesPopup.selectWorkOrderType(workOrderType);
	}

	public void addWorkWithJobOrder(String workOrderType, String jobName) {
		clickAddOrderButton();
		WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup(appiumdriver);
		workOrderTypesPopup.selectWorkOrderType(workOrderType);
		selectWorkOrderJob(jobName);
	}

	public void selectFirstOrder() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
	}

	public void selectCopyServices() {
		appiumdriver.findElementByAccessibilityId("Copy Services").click();
	}

	public void copyServicesForWorkOrder(String workOrderNumber, String customerName, String workOrderType) {
		selectWorkOrder(workOrderNumber);
		selectCopyServices();
		selectCustomerAndWorkOrderType(customerName, workOrderType);
	}

	public void copyServicesForWorkOrder(String workOrderNumber, String workOrderType) {
		selectWorkOrder(workOrderNumber);
		selectCopyServices();
		WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup(appiumdriver);
		workOrderTypesPopup.selectWorkOrderType(workOrderType);
	}

	public void copyVehicleForWorkOrder(String workOrderNumber, String customerName, String workOrderType) {
		selectWorkOrder(workOrderNumber);
		selectCopyVehicle();
		selectCustomerAndWorkOrderType(customerName, workOrderType);
	}
	
	public void selectCopyVehicle() {
		appiumdriver.findElementByAccessibilityId("Copy Vehicle").click();
	}
	
	public void clickChangeCustomerPopupMenu() {
		appiumdriver.findElementByAccessibilityId("Change Customer").click();
	}
	
	public void clickDetailspopupMenu() {
		appiumdriver.findElementByAccessibilityId("Details").click();
	}
	
	public void selectCustomer(String customer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		TouchAction tap = new TouchAction(appiumdriver).tap(appiumdriver.findElementByAccessibilityId(customer));              
        tap.perform();
		if (appiumdriver.findElementsByAccessibilityId("Customer changing...").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Customer changing...")));
		}
	}
	
	public void changeCustomerForWorkOrder(String wonumber, String customer) {
		selectWorkOrder(wonumber);
		clickChangeCustomerPopupMenu();
		selectCustomer(customer);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId("Customers"), 1));
		if (appiumdriver.findElementsByAccessibilityId("Customer changing...").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Customer changing...")));
		}
	}
	
	public void customersPopupSwitchToWholesailMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		if (elementExists(btnretail)) {
			appiumdriver.findElement(btnretail).click();
		}
	}
	
	public void customersPopupSwitchToRetailMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		if (elementExists(btnwholesale)) {
			appiumdriver.findElement(btnwholesale).click();
		}
	}
	
	public void openWorkOrderDetails(String wonumber) {
		selectWorkOrder(wonumber);
		clickDetailspopupMenu();
	}
	
	public void deleteWorkOrderViaAction(String wo) {
		clickActionButton();
		selectWorkOrderForAction(wo);
		clickActionButton();
		clickDeleteButton();
		Helpers.acceptAlert();
		clickDoneButton();
	}
	
	public void clickActionButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable (appiumdriver.findElementByAccessibilityId("Share")));
		appiumdriver.findElementByAccessibilityId("Share").click();		
	}
	
	public void clickFilterButton() {
		if (appiumdriver.findElementsByAccessibilityId("filter").size() < 1)
			appiumdriver.findElementByAccessibilityId("filter pressed").click();
		else
			appiumdriver.findElementByAccessibilityId("filter").click();		
		//filterbtn.click();		
	}
	
	public void setFilterLocation(String _location)  {
		appiumdriver.findElementByAccessibilityId("Location").click();
		appiumdriver.findElementByAccessibilityId(_location).click();
	}
	
	public void clickDoneButton() {
		appiumdriver.findElementByAccessibilityId("Done").click();		
	}
	
	public void clickDeleteButton() {
		appiumdriver.findElementByAccessibilityId("Delete").click();
	}
	
	public boolean isAutosavedWorkOrderExists() {
		Helpers.waitABit(1000);
		return elementExists(autosavedworkorder);
	}
	
	public void selectWorkOrderForAction(String woNumber) {
		appiumdriver.findElementByAccessibilityId(woNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}
	
	public SelectEmployeePopup clickWorkOrderForApproveButton(String woNumber) {
		appiumdriver.findElementByAccessibilityId(woNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
		return new SelectEmployeePopup(appiumdriver);
	}
	
	public MyWorkOrdersScreen approveWorkOrderWithoutSignature(String wo, String employee, String pwd) {
		SelectEmployeePopup selectemployeepopup = clickWorkOrderForApproveButton(wo);
		selectemployeepopup.selectEmployeeAndTypePassword(employee, pwd);
		ApproveSummaryPopup approvepopup = new ApproveSummaryPopup(appiumdriver);
		approvepopup.clickApproveButton();

		return this;
	}
	
	public void selectWorkOrder(String wonumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(wonumber)));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(wonumber))).click();
	}
	
	public void selectWorkOrderForEidt(String wonumber) {		
		selectWorkOrder(wonumber);
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public TechRevenueScreen selectWorkOrderTechRevenueMenuItem(String wonumber) {
		selectWorkOrder(wonumber);
		if (!appiumdriver.findElementByAccessibilityId("Tech Revenue").isDisplayed()) {
			swipeTableUp(appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Tech Revenue']/.."),
					appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Tech Revenue']/../.."));
			//appiumdriver.findElementByAccessibilityId("Tech Revenue").click();
		}
		appiumdriver.findElementByAccessibilityId("Tech Revenue").click();

		return new TechRevenueScreen(appiumdriver);
	}
	
	public SelectedServiceDetailsScreen selectWorkOrderTechniciansMenuItem(String wo) {
		selectWorkOrder(wo);
		if (!appiumdriver.findElementByAccessibilityId("Technicians").isDisplayed()) {
			swipeTableUp(appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Technicians']/.."),
					appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Technicians']/../.."));
			//appiumdriver.findElementByAccessibilityId("Technicians").click();
		}
		appiumdriver.findElementByAccessibilityId("Technicians").click();
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void selectWorkOrderForCopyVehicle(String wonumber) {
		selectWorkOrder(wonumber);
		appiumdriver.findElementByAccessibilityId("Copy Vehicle").click();
	}
	
	public void selectWorkOrderForAddingNotes(String wonumber) {
		selectWorkOrder(wonumber);
		appiumdriver.findElementByAccessibilityId("Notes").click();
	}
	
	public void selectWorkOrderForCopyServices(String wo) {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Copy Services").click();
	}
	
	public void selectWorkOrderNewInspection(String wo) {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("New Inspection").click();
	}
	
	public void selectContinueWorkOrder(String wo) {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Continue").click();
	}
	
	public void selectDiscardWorkOrder(String wo) {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Discard").click();
	}

	public void clickCreateInvoiceIconForWO(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).findElement(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).click();
		
		//appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, \"EntityInfoButtonUnchecked\")]")).click();
		//Helpers.waitABit(1000);
		//appiumdriver.findElement(MobileBy.xpath("//UIATableView[@name=\"MyWorkOrdersTable\"]/UIATableCell[@name = \"" + wo + "\"]/UIAButton[@name=\"EntityInfoButtonUnchecked\"]")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyWorkOrdersTable\"].cells()[\"" + wo + "\"].buttons()[\"EntityInfoButtonUnchecked\"]")).click();
	}
	
	public String getPriceValueForWO(String wonumber) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeStaticText[@name='labelOrderAmount']").getAttribute("value");
	}

	public boolean woExists(String wonumber) {
		return elementExists(wonumber);
	}

	public InvoiceInfoScreen clickInvoiceIcon() {
		appiumdriver.findElementByAccessibilityId("invoice new").click();
		return new InvoiceInfoScreen(appiumdriver, TypeScreenContext.WORKORDER);
	}

	public InvoiceInfoScreen selectInvoiceType(String invoicetype) {
		/*appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView/UIATableCell[contains(@name, \""
						+ invoicetype + "\")]/UIAStaticText[1]").click();*/
		appiumdriver.findElementByAccessibilityId(invoicetype).click();
		return new InvoiceInfoScreen(appiumdriver, TypeScreenContext.WORKORDER);
	}

	public String selectInvoiceTypeAndAcceptAlert(String invoicetype) throws InterruptedException {
		selectInvoiceType(invoicetype);
		return Helpers.getAlertTextAndAccept();
	}

	public  void selectWorkOrderJob(String jobName) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(jobName)));
		appiumdriver.findElementByName(jobName).click();
	}
	
	public void setFilterBilling(String billing)  {
		appiumdriver.findElementByName("Billing").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + billing + "']").click();
	}
	
	public void clickSaveFilter() {
		appiumdriver.findElementByAccessibilityId("Save").click();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId("Save"), 1));
	}
	
	public boolean isMenuItemForSelectedWOExists(String menuitem) {
		return elementExists(By.name(menuitem));
	}
	
	public void clickServiceRequestButton() {
		appiumdriver.findElementByAccessibilityId("Service Request").click();
	}
	
	public boolean isWorkOrderHasApproveIcon(String wonumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_37')]")).size() > 0;
	}
	
	public boolean isWorkOrderHasActionIcon(String wonumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_38')]")).size() > 0;
	}
	
	public void selectWorkOrderForApprove(String wonumber) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'EntityInfoButtonUnchecked')]")).click();
	}
	
	public void approveWorkOrder(String wo, String employee, String pwd) {
		selectWorkOrderForApprove(wo);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.clickApproveButton();
	}
	
	public VehicleScreen selectJob(String workorderjob) {
		appiumdriver.findElementByName(workorderjob).click();
		return new VehicleScreen(appiumdriver);
	}
	
	public String getFirstWorkOrderNumberValue() {		
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelOrderNumber']")).getAttribute("label");
	}
}
