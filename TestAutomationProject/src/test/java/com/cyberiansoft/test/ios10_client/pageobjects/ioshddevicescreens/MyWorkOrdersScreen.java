package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.iOSInternalProjectConstants;

public class MyWorkOrdersScreen extends iOSHDBaseScreen {

	private By autosavedworkorder = By.name("EntityInfoButtonUnchecked, AutoSaved");
	
	private By btnwholesale = By.name("btnWholesale");
	private By btnretail = By.name("btnRetail");
	
	@iOSFindBy(accessibility = "Add")
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
    private IOSElement notesmenu;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")
    private IOSElement firswointable;
	
	@iOSFindBy(accessibility = "Share")
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
	
	@iOSFindBy(accessibility  = "Service Request")
    private IOSElement servicerequestbtn;

	public MyWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickAddOrderButton() {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(addinspbtn));
		addinspbtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Discard").size() > 0)
			appiumdriver.findElementByAccessibilityId("Discard").click();
		Helpers.waitABit(1000);
	}
	
	public IOSElement getAddOrderButton() {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(addinspbtn));
		return addinspbtn;
	}

	public void selectFirstOrder() {
		firswointable.click();
	}

	public void selectCopyServices() {
		copyservicesmenu.click();
	}
	
	public void selectCopyVehicle() {
		copyvehiclemenu.click();
	}
	
	public void clickChangeCustomerPopupMenu() {
		changecustomermenu.click();
	}
	
	public void clickDetailspopupMenu() {
		detailsmenu.click();
		Helpers.waitABit(1000);
	}
	
	public void selectCustomer(String customer) {
		appiumdriver.tap(1, appiumdriver.findElementByAccessibilityId(customer), 200);
	}
	
	public void changeCustomerForWorkOrder(String wonumber, String customer) {
		selectWorkOrder(wonumber);
		clickChangeCustomerPopupMenu();
		selectCustomer(customer);
		Helpers.waitABit(1000);
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
		sharebtn.click();		
	}
	
	public void clickFilterButton() {
		if (appiumdriver.findElementsByAccessibilityId("filter").size() < 1)
			appiumdriver.findElementByAccessibilityId("filter pressed").click();
		else
			filterbtn.click();		
		//filterbtn.click();		
	}
	
	public void setFilterLocation(String _location)  {
		appiumdriver.findElementByAccessibilityId("Location").click();
		Helpers.waitABit(500);
		appiumdriver.findElementByAccessibilityId(_location).click();
	}
	
	public void clickDoneButton() {
		donebtn.click();		
	}
	
	public void clickDeleteButton() {
		deletebtn.click();
	}
	
	public boolean isAutosavedWorkOrderExists() {	
		return elementExists(autosavedworkorder);
	}
	
	public void selectWorkOrderForAction(String wo) {
		appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name='" + wo
						+ "']/XCUIElementTypeOther").click();
	}
	
	public SelectEmployeePopup clickWorkOrderForApproveButton(String wo) {
		appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name='" + wo
						+ "']/XCUIElementTypeOther").click();
		return new SelectEmployeePopup(appiumdriver);
	}
	
	public void approveWorkOrderWithoutSignature(String wo, String employee, String pwd) {
		SelectEmployeePopup selectemployeepopup = clickWorkOrderForApproveButton(wo);
		selectemployeepopup.selectEmployeeAndTypePassword(employee, pwd);
		ApproveSummaryPopup approvepopup = new ApproveSummaryPopup(appiumdriver);
		approvepopup.clickApproveButton();
	}
	
	public void selectWorkOrder(String wo) {
		appiumdriver.findElementByAccessibilityId(wo).click();
	}
	
	public void selectWorkOrderForEidt(String wo) throws InterruptedException {		
		selectWorkOrder(wo);
		editbtn.click();
		Helpers.waitABit(1000);
	}
	
	public void selectWorkOrderForCopyVehicle(String wo) {
		selectWorkOrder(wo);
		copyvehiclemenu.click();
	}
	
	public void selectWorkOrderForAddingNotes(String wo) {
		selectWorkOrder(wo);
		notesmenu.click();
	}
	
	public void selectWorkOrderForCopyServices(String wo) {
		selectWorkOrder(wo);
		copyservicesmenu.click();
	}
	
	public void selectWorkOrderNewInspection(String wo) {
		selectWorkOrder(wo);
		newinspectionmenu.click();
		Helpers.waitABit(500);
	}
	
	public void selectContinueWorkOrder(String wo) {
		selectWorkOrder(wo);
		continuemenu.click();
	}
	
	public void selectDiscardWorkOrder(String wo) {
		selectWorkOrder(wo);
		discardmenu.click();
	}

	public void clickCreateInvoiceIconForWO(String wonumber) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, \"EntityInfoButtonUnchecked\")]")).click();
		//appiumdriver.findElement(MobileBy.xpath("//UIATableView[@name=\"MyWorkOrdersTable\"]/UIATableCell[@name = \"" + wo + "\"]/UIAButton[@name=\"EntityInfoButtonUnchecked\"]")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyWorkOrdersTable\"].cells()[\"" + wo + "\"].buttons()[\"EntityInfoButtonUnchecked\"]")).click();
	}
	
	public String getPriceValueForWO(String wonumber) {
		Helpers.waitABit(500);
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeStaticText[@name='labelOrderAmount']").getAttribute("value");
	}

	public boolean woExists(String wonumber) {
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return appiumdriver.findElementsByAccessibilityId(wonumber).size() > 0;
	}

	public void clickInvoiceIcon() {
		newinvoice.click();
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

	public VehicleScreen selectWorkOrderType(String workordertype) {

		if (appiumdriver.findElementsByAccessibilityId("Discard").size() > 0)
			appiumdriver.findElementByAccessibilityId("Discard").click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByName(workordertype)).waitAction(300).release().perform();
		//appiumdriver.findElementByName(workordertype).click();
		return new VehicleScreen(appiumdriver);
	}

	public  void selectWorkOrderJob(String job) {
		appiumdriver.findElementByName(job).click();
	}
	
	public void setFilterBilling(String billing)  {
		appiumdriver.findElementByName("Billing").click();
		Helpers.waitABit(500);
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + billing + "']").click();
	}
	
	public void clickSaveFilter() {
		clickSaveButton();
	}
	
	public boolean isMenuItemForSelectedWOExists(String menuitem) {
		return elementExists(By.name(menuitem));
	}
	
	public void clickServiceRequestButton() {
		servicerequestbtn.click();
	}
	
	public boolean isWorkOrderHasApproveIcon(String wonumber) {
		return appiumdriver.findElementByClassName("XCUIElementTypeTable").
				findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_37')]")).size() > 0;
	}
	
	public boolean isWorkOrderHasActionIcon(String wonumber) {
		return appiumdriver.findElementByClassName("XCUIElementTypeTable").
				findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_38')]")).size() > 0;
	}
	
	public void selectWorkOrderForApprove(String wonumber) {
		appiumdriver.findElementByClassName("XCUIElementTypeTable").
		findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'EntityInfoButtonUnchecked')]")).click();
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
}
