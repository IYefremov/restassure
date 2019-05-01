package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularTechRevenueScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularMyWorkOrdersScreen extends RegularBaseTypeScreenWithTabs {

	private final TypeScreenContext WOCONTEXT = TypeScreenContext.WORKORDER;
	
	private By discardbtnxpath = By.name("Discard");
	
	private By autosavedworkorder = By.xpath("//XCUIElementTypeTable[@name='MyWorkOrdersTable']/XCUIElementTypeCell/XCUIElementTypeOther[@name='EntityInfoButtonUnchecked, AutoSaved']");
	
	/*private By btnwholesale = By.name("btnWholesale");
	private By btnretail = By.name("btnRetail");
	
	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement addinspbtn;
	
	@iOSFindBy(accessibility = "Discard")
    private IOSElement discardbtn;

	@iOSFindBy(accessibility = "New\nInspection")
    private IOSElement newinspectionmenu;
	
	@iOSFindBy(accessibility = "Create\nInvoices")
    private IOSElement createinvoicesmenu;
	
	@iOSFindBy(accessibility = "Continue")
    private IOSElement continuemenu;
	
	@iOSFindBy(accessibility = "Discard")
    private IOSElement discardmenu;
	
	@iOSFindBy(accessibility = "Notes")
    private IOSElement notesmenu;
	
	@iOSFindBy(accessibility  = "Tech Revenue")
    private IOSElement techrevenuebtn;
	
	@iOSFindBy(accessibility  = "Technicians")
    private IOSElement techniciansmenubtn;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")
    private IOSElement firswointable;
	
	@iOSFindBy(accessibility = "Share")
    private IOSElement sharebtn;
	
	//@iOSFindBy(xpath = "//UIAButton[@visible=\"true\" and (contains(@name,\"filter\"))] ")
    //private IOSElement filterbtn;
	
	@iOSFindBy(accessibility = "filter")
    private IOSElement filterbtn;
	
	@iOSFindBy(accessibility = "invoice new")
    private IOSElement newinvoice;
	
	@iOSFindBy(accessibility = "Delete")
    private IOSElement deletebtn;
	
	@iOSFindBy(accessibility = "Approve")
    private IOSElement approvebtn;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editbtn;
	
	@iOSFindBy(accessibility = "Enter password here")
    private IOSElement securefld;
	
	@iOSFindBy(accessibility = "Service Request")
    private IOSElement servicerequestbtn;*/

	@iOSFindBy(accessibility = "Done")
	private IOSElement donebtn;

	@iOSFindBy(accessibility = "Add")
	private IOSElement addinspbtn;

	@iOSFindBy(accessibility = "Change\nCustomer")
	private IOSElement changecustomermenu;

	@iOSFindBy(accessibility = "Details")
	private IOSElement detailsmenu;

	@iOSFindBy(accessibility= "Copy\nVehicle")
	private IOSElement copyvehiclemenu;

	@iOSFindBy(accessibility = "Copy\nServices")
	private IOSElement copyservicesmenu;

	@iOSFindBy(accessibility = "MyWorkOrdersTable")
	private IOSElement mywotable;

	public RegularMyWorkOrdersScreen() {

		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver>  wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(MobileBy.AccessibilityId("MyWorkOrdersTable")));
	}

	public void clickAddOrderButton() {
		addinspbtn.click();
		//appiumdriver.findElementByAccessibilityId("Add").click();
		if (elementExists("Discard")) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		}
		RegularBaseWizardScreen.typeContext = WOCONTEXT;
	}
	
	public IOSElement getAddOrderButton() {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		return (IOSElement) wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Add")));
	}

	public void selectFirstOrder() {
		WebElement table = appiumdriver.findElementByAccessibilityId("MyWorkOrdersTable");
		table.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")).click();
		//firswointable.click();
	}

	public void selectCopyServices() {
		copyservicesmenu.click();
	}
	
	public RegularCustomersScreen selectCopyVehicle() {
		copyvehiclemenu.click();
		RegularBaseWizardScreen.typeContext = WOCONTEXT;
		return new RegularCustomersScreen();

	}
	
	public void clickChangeCustomerPopupMenu() {
		changecustomermenu.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
	}
	
	public void clickDetailspopupMenu() {
		detailsmenu.click();
	}
	
	public void selectCustomer(String customer) {
		/*appiumdriver.findElementByAccessibilityId("Search").click();
		Helpers.waitABit(1000);
		///XCUIElementTypeSearchField[@name="Search"]
		appiumdriver.getKeyboard().sendKeys(customer);*/
		appiumdriver.findElementByAccessibilityId(customer).click();
	}
	
	public void changeCustomerForWorkOrder(String wonumber, String customer) {
		selectWorkOrder(wonumber);
		clickChangeCustomerPopupMenu();
		selectCustomer(customer);
		FluentWait<WebDriver>  wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Customer changing...")));
		RegularMyWorkOrdersScreen workOrdersScreen = new RegularMyWorkOrdersScreen();
		//wait = new WebDriverWait(appiumdriver, 60);
		//wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("MyWorkOrdersTable")));
	}
	
	public void customersPopupSwitchToWholesailMode() {
		if (elementExists(MobileBy.AccessibilityId("btnRetail"))) {
			appiumdriver.findElementByAccessibilityId("btnRetail").click();
		}
	}
	
	public void customersPopupSwitchToRetailMode() {
		if (elementExists(MobileBy.AccessibilityId("btnWholesale"))) {
			appiumdriver.findElementByAccessibilityId("btnWholesale").click();
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
	
	public void clickSearchutton() {
		appiumdriver.findElementByAccessibilityId("Search").click();
	}
	
	public void deleteWorkOrderViaActionAndSearch(String wo) {
	
		clickActionButton();
		selectWorkOrderForAction(wo);
		clickActionButton();
		appiumdriver.findElementByAccessibilityId("Delete").click();
		Helpers.acceptAlert();
		clickDoneButton();
	}
	
	public void clickActionButton() {
		appiumdriver.findElementByAccessibilityId("Share").click();		
	}
	
	public void clickFilterButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name CONTAINS 'filter'")));
		if (appiumdriver.findElementsByAccessibilityId("filter").size() < 1)
			appiumdriver.findElementByAccessibilityId("filter pressed").click();
		else
			appiumdriver.findElementByAccessibilityId("filter").click();		
	}
	
	public void clickDoneButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(donebtn)).click();
	}
	
	public void clickDeleteButton() {
		appiumdriver.findElementByAccessibilityId("Delete").click();
	}
	
	public boolean isAutosavedWorkOrderExists() {	
		return elementExists(autosavedworkorder);
	}
	
	public void selectWorkOrderForAction(String wonumber) {
		appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(wonumber))
				.findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}
	
	public void selectWorkOrderForApprove(String wonumber) {

		mywotable.findElement(MobileBy.AccessibilityId(wonumber)).findElement(MobileBy.className("XCUIElementTypeOther")).click();
		
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByAccessibilityId("Enter password here")).setValue(password);
		Helpers.acceptAlert();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading team order")));
		}
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public RegularMyWorkOrdersScreen approveWorkOrder(String wo, String employee, String pwd) {
		selectWorkOrderForApprove(wo);
		selectEmployeeAndTypePassword(employee, pwd);
		clickApproveButton();
		return this;
	}
	
	public void clickApproveButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Approve")));
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}
	
	public void selectWorkOrder(String wonumber) {
		new WebDriverWait(appiumdriver, 10)
				  .until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId(wonumber)));
        mywotable.findElementByAccessibilityId(wonumber).click();
	}
	
	public void selectWorkOrderForEidt(String wo)  {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Edit").click();
		RegularBaseWizardScreen.typeContext = WOCONTEXT;
	}
	
	public void selectWorkOrderForCopyVehicle(String wo) {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Copy\nVehicle").click();
	}
	
	public void selectWorkOrderForAddingNotes(String wo)  {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Notes").click();
	}
	
	public void selectWorkOrderForCopyServices(String wo)  {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Copy\nServices").click();
	}
	
	public RegularTechRevenueScreen selectWorkOrderTechRevenueMenuItem(String wo) {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Tech Revenue").click();
		return new RegularTechRevenueScreen();
	}
	
	public RegularSelectedServiceDetailsScreen selectWorkOrderTechniciansMenuItem(String wo) {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Technicians").click();
		return new RegularSelectedServiceDetailsScreen();
	}
	
	public void selectWorkOrderNewInspection(String wonum) {
		selectWorkOrder(wonum);
		appiumdriver.findElementByAccessibilityId("New\nInspection").click();
	}
	
	public void selectContinueWorkOrder(String wonum) {
		selectWorkOrder(wonum);
		appiumdriver.findElementByAccessibilityId("Continue").click();
	}
	
	public void selectDiscardWorkOrder(String wonum) {
		selectWorkOrder(wonum);
		appiumdriver.findElementByAccessibilityId("Discard").click();
	}
	
	public void clickCreateInvoiceIconForWOViaSearch(String wonum) {
		clickCreateInvoiceIconForWO(wonum);
	}

	public void clickCreateInvoiceIconForWO(String wonumber)  {
		new WebDriverWait(appiumdriver, 20)
		  .until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(wonumber)));
		appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(wonumber))
				.findElement(MobileBy.className("XCUIElementTypeOther")).click();
		//appiumdriver.tap(1, appiumdriver.findElement(MobileBy.xpath("//UIATableView[@name=\"MyWorkOrdersTable\"]/UIATableCell[@name = \"" + wo + "\"]/UIAButton[contains(@name, \"EntityInfoButtonUnchecked\")]")), 200);
		//appiumdriver.findElement(MobileBy.xpath("//UIATableView[@name=\"MyWorkOrdersTable\"]/UIATableCell[@name = \"" + wo + "\"]/UIAButton[contains(@name, \"EntityInfoButtonUnchecked\")]")).click();
		//appiumdriver.findElement(MobileBy.xpath("//UIATableView[@name=\"MyWorkOrdersTable\"]/UIATableCell[@name = \"" + wo + "\"]/UIAButton[@name=\"EntityInfoButtonUnchecked\"]")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyWorkOrdersTable\"].cells()[\"" + wo + "\"].buttons()[\"EntityInfoButtonUnchecked\"]")).click();
	}
	
	public void clickCreateInvoiceIconForWOs(String[] wos) {
		for (int i = 0; i < wos.length; i++) {
			clickCreateInvoiceIconForWO(wos[i]);

		}
	}
	
	public String getPriceValueForWO(String wonumber) {
		return mywotable.findElementByAccessibilityId(wonumber).findElementByAccessibilityId("labelOrderAmount").getAttribute("label");
	}
	
	public boolean woExists(String wonumber) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(wonumber)).size() > 0;	
	}
	
	public boolean isWorkOrderHasApproveIcon(String wonumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_78')]")).size() > 0;
	}
	
	public boolean isWorkOrderHasActionIcon(String wonumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_79')]")).size() > 0;
	}
	
	/*public void clickCancelSearchButton() throws InterruptedException {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".elements()['OrdersSearchBar'].buttons()['Cancel']")).click();
	}
	
	public boolean searchWO(String wo) {
		searchbtn.click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".elements()['OrdersSearchBar'].searchBars()['Search']")).sendKeys(wo.substring(6, wo.length()));
		appiumdriver.findElement(MobileBy.xpath("//UIAKeyboard[1]/UIAButton[@name=\"Search\"]")).click();
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return appiumdriver.findElements(MobileBy.IosUIAutomation(".tableViews()['MyWorkOrdersTable'].cells()['" + wo + "']")).size() > 0;		
	}*/

	public void clickInvoiceIcon() {
		appiumdriver.findElementByAccessibilityId("invoice new").click();
		RegularBaseWizardScreen.typeContext = WOCONTEXT;
	}

	public <T extends IBaseWizardScreen> T selectInvoiceType(IInvoicesTypes invoiceType) {
		if (!appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).isDisplayed()) {
		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + invoiceType.getInvoiceTypeName() + "']/..")));
		}
		appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).click();
		RegularBaseWizardScreen.typeContext = WOCONTEXT;
		return invoiceType.getFirstVizardScreen();
	}

	public String selectInvoiceTypeAndAcceptAlert(IInvoicesTypes invoiceType) {
		if (!appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).isDisplayed()) {
			swipeToElement(appiumdriver.
					findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + invoiceType.getInvoiceTypeName() + "']/..")));
		}
		appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).click();
		RegularBaseWizardScreen.typeContext = WOCONTEXT;
		return Helpers.getAlertTextAndAccept();
	}

	public <T extends IBaseWizardScreen> T selectWorkOrderType(IWorkOrdersTypes workordertype) {

		if (Helpers.elementExists(discardbtnxpath)) {
			appiumdriver.findElement(discardbtnxpath).click();
		}
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("OrderTypeSelector")));
		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).isDisplayed()) {
			swipeToElement(wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()));
			//wostable.click();
		}
		wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).click();
		return workordertype.getFirstVizardScreen();
	}

	public  <T extends IBaseWizardScreen> T selectWorkOrderTypeWithJob(IWorkOrdersTypes workordertype, String job) {
		if (Helpers.elementExists(discardbtnxpath)) {
			appiumdriver.findElement(discardbtnxpath).click();
		}
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("OrderTypeSelector")));
		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).isDisplayed()) {
			swipeToElement(wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()));
			//wostable.click();
		}
		wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).click();
		appiumdriver.findElementByName(job).click();
		return workordertype.getFirstVizardScreen();
	}
	
	public void setFilterBilling(String billing)  {
		appiumdriver.findElementByName("Billing").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId("Filetr"), 1));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("StringSelector")));
		appiumdriver.findElementByAccessibilityId("StringSelector").findElement(MobileBy.AccessibilityId(billing)).click();
	}
	
	public void clickSaveFilter() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}
	
	public boolean isMenuItemForSelectedWOExists(String menuitem) {
		return elementExists(MobileBy.AccessibilityId(menuitem));
	}
	
	public void clickServiceRequestButton() {
		appiumdriver.findElementByAccessibilityId("Service Request").click();
	}
	
	public WebElement getWorkOrderTableParentNode(String wonumber) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + wonumber + "']/.."));
	}
	
	public RegularVehicleScreen selectJob(String workorderjob) {
		appiumdriver.findElementByName(workorderjob).click();
		return new RegularVehicleScreen();
	}
	
	public String getFirstWorkOrderNumberValue() {		
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelOrderNumber']")).getAttribute("label");
	}

	public void clickBackButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();
	}

	public RegularTeamWorkOrdersScreen switchToTeamWorkOrders() {
		switchToTeamView();
		return new RegularTeamWorkOrdersScreen();
	}
}
