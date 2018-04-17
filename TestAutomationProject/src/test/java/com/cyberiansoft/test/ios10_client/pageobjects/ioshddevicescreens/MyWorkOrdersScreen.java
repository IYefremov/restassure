package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.iOSInternalProjectConstants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MyWorkOrdersScreen extends iOSHDBaseScreen {

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

	public MyWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickAddOrderButton() {			
		appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(MobileBy.AccessibilityId("Add")).click();
		if (elementExists("Discard"))
			appiumdriver.findElementByAccessibilityId("Discard").click();
	}
	
	public IOSElement getAddOrderButton() {	
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		return (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Add"))); 
	}

	public void selectFirstOrder() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
	}

	public void selectCopyServices() {
		appiumdriver.findElementByAccessibilityId("Copy Services").click();
	}
	
	public void selectCopyVehicle() {
		appiumdriver.findElementByAccessibilityId("Copy Vehicle").click();
	}
	
	public void clickChangeCustomerPopupMenu() {
		appiumdriver.findElementByAccessibilityId("Change Customer").click();
		Helpers.waitABit(1000);
	}
	
	public void clickDetailspopupMenu() {
		appiumdriver.findElementByAccessibilityId("Details").click();
	}
	
	public void selectCustomer(String customer) {
		TouchAction tap = new TouchAction(appiumdriver).tap(appiumdriver.findElementByAccessibilityId(customer));              
        tap.perform();
		if (appiumdriver.findElementsByAccessibilityId("Customer changing...").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Customer changing...")));
		}
	}
	
	public void changeCustomerForWorkOrder(String wonumber, String customer) {
		selectWorkOrder(wonumber);
		clickChangeCustomerPopupMenu();
		selectCustomer(customer);
		if (appiumdriver.findElementsByAccessibilityId("Customer changing...").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Customer changing...")));
		}
	}
	
	public void customersPopupSwitchToWholesailMode() throws InterruptedException {
		if (elementExists(btnretail)) {
			appiumdriver.findElement(btnretail).click();
		}
	}
	
	public void customersPopupSwitchToRetailMode() throws InterruptedException {
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
	
	public void deleteWorkOrderViaActionAndSearch(String wo) throws InterruptedException {
		IOSElement addbtn = getAddOrderButton();
		int xx = addbtn.getLocation().getX();

		int yy = addbtn.getLocation().getY();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[\"Search\"]")).click();
		Helpers.keyboadrType(wo.substring(6, wo.length()));
		appiumdriver.findElement(MobileBy.xpath("//UIAKeyboard[1]/UIAButton[@name=\"Search\"]")).click();
		
		clickActionButton();
		selectWorkOrderForAction(wo);
		clickActionButton();
		appiumdriver.findElementByXPath("//UIAScrollView[2]/UIAButton[4]").click();
		Helpers.acceptAlert();
		clickDoneButton();
		
		
		TouchAction action = new TouchAction(appiumdriver);
		action.press(xx + 10,yy + 10).release().perform();
	}
	
	public void clickActionButton() {
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
		Helpers.waitABit(500);
		appiumdriver.findElementByAccessibilityId(_location).click();
	}
	
	public void clickDoneButton() {
		appiumdriver.findElementByAccessibilityId("Done").click();		
	}
	
	public void clickDeleteButton() {
		appiumdriver.findElementByAccessibilityId("Delete").click();
	}
	
	public boolean isAutosavedWorkOrderExists() {
		Helpers.waitABit(1500);
		return elementExists(autosavedworkorder);
	}
	
	public void selectWorkOrderForAction(String woNumber) {
		appiumdriver.findElementByAccessibilityId(woNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}
	
	public SelectEmployeePopup clickWorkOrderForApproveButton(String woNumber) {
		appiumdriver.findElementByAccessibilityId(woNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
		return new SelectEmployeePopup(appiumdriver);
	}
	
	public void approveWorkOrderWithoutSignature(String wo, String employee, String pwd) {
		SelectEmployeePopup selectemployeepopup = clickWorkOrderForApproveButton(wo);
		selectemployeepopup.selectEmployeeAndTypePassword(employee, pwd);
		ApproveSummaryPopup approvepopup = new ApproveSummaryPopup(appiumdriver);
		approvepopup.clickApproveButton();
	}
	
	public void selectWorkOrder(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).click();
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
			appiumdriver.findElementByAccessibilityId("Tech Revenue").click();
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
			appiumdriver.findElementByAccessibilityId("Technicians").click();
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
		Helpers.waitABit(500);
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeStaticText[@name='labelOrderAmount']").getAttribute("value");
	}

	public boolean woExists(String wonumber) {
		return elementExists(wonumber);
	}

	public void clickInvoiceIcon() {
		appiumdriver.findElementByAccessibilityId("invoice new").click();
	}

	public InvoiceInfoScreen selectInvoiceType(String invoicetype) {
		/*appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView/UIATableCell[contains(@name, \""
						+ invoicetype + "\")]/UIAStaticText[1]").click();*/
		appiumdriver.findElementByAccessibilityId(invoicetype).click();
		return new InvoiceInfoScreen(appiumdriver);
	}

	public String selectInvoiceTypeAndAcceptAlert(String invoicetype) throws InterruptedException {
		selectInvoiceType(invoicetype);
		return Helpers.getAlertTextAndAccept();
	}

	public void selectWorkOrderType(String workordertype) {

		if (appiumdriver.findElementsByAccessibilityId("Discard").size() > 0)
			appiumdriver.findElementByAccessibilityId("Discard").click();
		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype).isDisplayed()) {
			swipeTableUp(wostable.findElementByAccessibilityId(workordertype),
					wostable);
			wostable.findElementByAccessibilityId(workordertype).click();
		}
		if (appiumdriver.findElementsByAccessibilityId(workordertype).size() > 0)
			appiumdriver.findElementByAccessibilityId(workordertype).click();
	}

	public  void selectWorkOrderJob(String job) {
		appiumdriver.findElementByName(job).click();
	}
	
	public void setFilterBilling(String billing)  {
		appiumdriver.findElementByName("Billing").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + billing + "']").click();
	}
	
	public void clickSaveFilter() {
		clickSaveButton();
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
		Helpers.waitABit(1000);
	}
	
	public VehicleScreen selectJob(String workorderjob) {
		appiumdriver.findElementByName(workorderjob).click();
		return new VehicleScreen(appiumdriver);
	}
	
	public String getFirstWorkOrderNumberValue() {		
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelOrderNumber']")).getAttribute("label");
	}
	
	public void switchToLocalMyOrdersView() {
		appiumdriver.findElementByAccessibilityId("My").click();		
	}
	
	public void switchToTeamWorkOrdersView() {
		appiumdriver.findElementByAccessibilityId("Team").click();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}
}
