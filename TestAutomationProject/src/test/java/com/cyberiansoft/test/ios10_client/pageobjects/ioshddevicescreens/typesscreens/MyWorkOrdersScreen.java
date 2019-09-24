package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
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

public class MyWorkOrdersScreen extends BaseTypeScreenWithTabs {

	private By autosavedworkorder = By.name("EntityInfoButtonUnchecked, AutoSaved");
	
	private By btnwholesale = By.name("btnWholesale");
	private By btnretail = By.name("btnRetail");
	
	/*@iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addinspbtn;
	
	@iOSXCUITFindBy(accessibility  = "Discard")
    private IOSElement discardbtn;
	
	@iOSXCUITFindBy(accessibility  = "Copy Services")
    private IOSElement copyservicesmenu;
	
	@iOSXCUITFindBy(accessibility  = "New Inspection")
    private IOSElement newinspectionmenu;
	
	@iOSXCUITFindBy(accessibility  = "Continue")
    private IOSElement continuemenu;
	
	@iOSXCUITFindBy(accessibility  = "Discard")
    private IOSElement discardmenu;
	
	@iOSXCUITFindBy(accessibility  = "Notes")
    private IOSElement notesmenu;*/
	
	//@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")
    //private IOSElement firswointable;
	
	/*@iOSXCUITFindBy(accessibility = "Share")
    private IOSElement sharebtn;
	
	@iOSXCUITFindBy(accessibility = "filter")
    private IOSElement filterbtn;
	
	@iOSXCUITFindBy(accessibility = "Done")
    private IOSElement donebtn;
	
	@iOSXCUITFindBy(accessibility  = "invoice new")
    private IOSElement newinvoice;
	
	@iOSXCUITFindBy(accessibility  = "Delete")
    private IOSElement deletebtn;
	
	@iOSXCUITFindBy(accessibility  = "Edit")
    private IOSElement editbtn;
	
	@iOSXCUITFindBy(accessibility  = "Tech Revenue")
    private IOSElement techrevenuebtn;
	
	@iOSXCUITFindBy(accessibility  = "Technicians")
    private IOSElement techniciansmenubtn;
	
	@iOSXCUITFindBy(accessibility  = "Service Request")
    private IOSElement servicerequestbtn;*/

	@iOSXCUITFindBy(accessibility = "Add")
	private IOSElement addorderbtn;

    @iOSXCUITFindBy(accessibility  = "Change Customer")
    private IOSElement changecustomermenu;

    @iOSXCUITFindBy(accessibility  = "Details")
    private IOSElement detailsmenu;

	@iOSXCUITFindBy(accessibility  = "OrdersPageTableLeft")
	private IOSElement workOrdersTable;

	public MyWorkOrdersScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitWorkOrdersScreenLoaded() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 40);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("OrdersPageTableLeft")));
		wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("OrdersPageTableLeft")));
	}

	public void clickAddOrderButton() {
		addorderbtn.click();
		if (elementExists("Discard"))
			appiumdriver.findElementByAccessibilityId("Discard").click();
	}


	public String getMyWorkOrdersSelectedCustomerValue() {
		return appiumdriver.findElementByAccessibilityId("Toolbar").
				findElements(MobileBy.className("XCUIElementTypeButton")).get(2).getAttribute("label");
	}
	
	public void clickChangeCustomerPopupMenu() {
        changecustomermenu.click();
	}
	
	public void clickDetailspopupMenu() {
        detailsmenu.click();
	}
	
	public void selectCustomer(String customer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		appiumdriver.findElementByAccessibilityId(customer).click();
		if (appiumdriver.findElementsByAccessibilityId("Customer changing...").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 20);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Customer changing...")));
		}
	}
	
	public MyWorkOrdersScreen changeCustomerForWorkOrder(String workOrderNumber, String customer) {
		selectWorkOrder(workOrderNumber);
		clickChangeCustomerPopupMenu();
		selectCustomer(customer);
		return this;
	}
	
	public void customersPopupSwitchToRetailMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		if (elementExists(btnwholesale)) {
			appiumdriver.findElement(btnwholesale).click();
		}
	}
	
	public void openWorkOrderDetails(String workOrderNumber) {
		selectWorkOrder(workOrderNumber);
		clickDetailspopupMenu();
	}
	
	public MyWorkOrdersScreen deleteWorkOrderViaAction(String workOrderNumber) {
		clickActionButton();
		selectWorkOrderForAction(workOrderNumber);
		clickActionButton();
		clickDeleteButton();
		Helpers.acceptAlert();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId(workOrderNumber), 1));
		return this;
	}
	
	public void clickActionButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Share")));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable (MobileBy.AccessibilityId("Share")));
		appiumdriver.findElementByAccessibilityId("Share").click();		
	}
	
	public void clickFilterButton() {
		waitWorkOrdersScreenLoaded();
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
	
	public void selectWorkOrderForAction(String workOrderNumber) {
		appiumdriver.findElementByAccessibilityId(workOrderNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}
	
	public SelectEmployeePopup clickWorkOrderForApproveButton(String workOrderNumber) {
		appiumdriver.findElementByAccessibilityId(workOrderNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
		return new SelectEmployeePopup();
	}
	
	public MyWorkOrdersScreen approveWorkOrderWithoutSignature(String workOrderNumber, String employee, String password) {
		waitWorkOrdersScreenLoaded();
		SelectEmployeePopup selectemployeepopup = clickWorkOrderForApproveButton(workOrderNumber);
		selectemployeepopup.selectEmployeeAndTypePassword(employee, password);
		ApproveSummaryPopup approvepopup = new ApproveSummaryPopup();
		approvepopup.clickApproveButton();

		return this;
	}
	
	public void selectWorkOrder(String workOrderNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("label = '" +
				workOrderNumber + "' and type = 'XCUIElementTypeStaticText'")));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("label = '" +
				workOrderNumber + "' and type = 'XCUIElementTypeStaticText'")));
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("label = '" +
				workOrderNumber + "' and type = 'XCUIElementTypeStaticText'")).click();
	}
	
	public void selectWorkOrderForEidt(String workOrderNumber) {		
		selectWorkOrder(workOrderNumber);
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public TechRevenueScreen selectWorkOrderTechRevenueMenuItem(String workOrderNumber) {
		selectWorkOrder(workOrderNumber);
		if (!appiumdriver.findElementByAccessibilityId("Tech Revenue").isDisplayed()) {
			swipeTableUp(appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Tech Revenue']/.."),
					appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Tech Revenue']/../.."));
			//appiumdriver.findElementByAccessibilityId("Tech Revenue").click();
		}
		appiumdriver.findElementByAccessibilityId("Tech Revenue").click();

		return new TechRevenueScreen();
	}
	
	public TechniciansPopup selectWorkOrderTechniciansMenuItem(String wo) {
		selectWorkOrder(wo);
		if (!appiumdriver.findElementByAccessibilityId("Technicians").isDisplayed()) {
			swipeTableUp(appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Technicians']/.."),
					appiumdriver.
					findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Technicians']/../.."));
			//appiumdriver.findElementByAccessibilityId("Technicians").click();
		}
		appiumdriver.findElementByAccessibilityId("Technicians").click();
		return new TechniciansPopup();
	}
	
	public void selectWorkOrderForCopyVehicle(String workOrderNumber) {
		selectWorkOrder(workOrderNumber);
		appiumdriver.findElementByAccessibilityId("Copy Vehicle").click();
	}
	
	public void selectWorkOrderForAddingNotes(String workOrderNumber) {
		selectWorkOrder(workOrderNumber);
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
	
	public void selectContinueWorkOrder() {
		selectWorkOrder("Auto Save");
		appiumdriver.findElementByAccessibilityId("Continue").click();
	}
	
	public void selectDiscardWorkOrder() {
		selectWorkOrder("Auto Save");
		appiumdriver.findElementByAccessibilityId("Discard").click();
	}

	public void clickCreateInvoiceIconForWO(String workOrderId) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(workOrderId)));
		appiumdriver.findElementByAccessibilityId(workOrderId).findElement(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).click();
	}
	
	public String getPriceValueForWO(String workOrderNumber) {
		waitWorkOrdersScreenLoaded();
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + workOrderNumber + "']/XCUIElementTypeStaticText[@name='labelOrderAmount']").getAttribute("value");
	}

	public boolean woExists(String workOrderNumber) {
		waitWorkOrdersScreenLoaded();
		return elementExists(workOrderNumber);
	}

	public void clickInvoiceIcon() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("invoice new")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("invoice new"))).click();
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
	
	public boolean isWorkOrderHasApproveIcon(String workOrderNumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + workOrderNumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_37')]")).size() > 0;
	}
	
	public boolean isWorkOrderHasActionIcon(String workOrderNumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + workOrderNumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_38')]")).size() > 0;
	}
	
	public void selectWorkOrderForApprove(String workOrderNumber) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + workOrderNumber + "']/XCUIElementTypeOther[contains(@name, 'EntityInfoButtonUnchecked')]")).click();
	}
	
	public MyWorkOrdersScreen approveWorkOrder(String wo, String employee, String pwd) {
		selectWorkOrderForApprove(wo);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		return this;
	}
	
	public String getFirstWorkOrderNumberValue() {		
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelOrderNumber']")).getAttribute("label");
	}

    public CarHistoryScreen clickBackToCarHystoryScreen() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
        appiumdriver.findElementByAccessibilityId("Back").click();
        return new CarHistoryScreen();
    }

    public TeamWorkOrdersScreen switchToTeamWorkOrdersView() {
		waitWorkOrdersScreenLoaded();
		switchToTeamView();
		return new TeamWorkOrdersScreen();
	}
}
