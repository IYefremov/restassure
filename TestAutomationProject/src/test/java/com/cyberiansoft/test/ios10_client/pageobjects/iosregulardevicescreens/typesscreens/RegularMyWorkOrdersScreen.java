package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularTechRevenueScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularMyWorkOrdersScreen extends RegularBaseTypeScreenWithTabs {
	
	private By discardbtnxpath = By.name("Discard");
	
	private By autosavedworkorder = By.xpath("//XCUIElementTypeTable[@name='MyWorkOrdersTable']/XCUIElementTypeCell/XCUIElementTypeOther[@name='EntityInfoButtonUnchecked, AutoSaved']");

	@iOSXCUITFindBy(accessibility = "Done")
	private IOSElement donebtn;

	@iOSXCUITFindBy(accessibility = "Add")
	private IOSElement addinspbtn;

	@iOSXCUITFindBy(accessibility = "MyWorkOrdersTable")
	private IOSElement mywotable;

	public RegularMyWorkOrdersScreen() {

		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitMyWorkOrdersScreenLoaded() {
		FluentWait<WebDriver>  wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(MobileBy.AccessibilityId("MyWorkOrdersTable")));
	}

	public void clickAddOrderButton() {
		addinspbtn.click();
		if (elementExists("Discard")) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		};
	}

	public void selectFirstOrder() {
		mywotable.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")).click();
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
	
	public boolean isAutosavedWorkOrderExists() {	
		return elementExists(autosavedworkorder);
	}
	
	public void selectWorkOrderForAction(String workOrderID) {
		mywotable.findElement(MobileBy.AccessibilityId(workOrderID))
				.findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}
	
	public void selectWorkOrderForApprove(String workOrderID) {
		mywotable.findElement(MobileBy.AccessibilityId(workOrderID)).findElement(MobileBy.className("XCUIElementTypeOther")).click();
		
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
	
	public void selectWorkOrder(String workOrderId) {
		waitMyWorkOrdersScreenLoaded();
		WaitUtils.waitUntilElementIsClickable(mywotable);
		WaitUtils.waitUntilElementIsClickable(mywotable.findElementByAccessibilityId(workOrderId)).click();
	}
	
	public void selectWorkOrderForAddingNotes(String workOrderId)  {
		selectWorkOrder(workOrderId);
		appiumdriver.findElementByAccessibilityId("Notes").click();
	}
	
	public RegularTechRevenueScreen selectWorkOrderTechRevenueMenuItem(String workOrderId) {
		selectWorkOrder(workOrderId);
		appiumdriver.findElementByAccessibilityId("Tech Revenue").click();
		return new RegularTechRevenueScreen();
	}
	
	public RegularSelectedServiceDetailsScreen selectWorkOrderTechniciansMenuItem(String workOrderId) {
		selectWorkOrder(workOrderId);
		appiumdriver.findElementByAccessibilityId("Technicians").click();
		return new RegularSelectedServiceDetailsScreen();
	}
	
	public void selectContinueWorkOrder() {
		selectWorkOrder("Auto Save");
		appiumdriver.findElementByAccessibilityId("Continue").click();
	}
	
	public void selectDiscardWorkOrder() {
		selectWorkOrder("Auto Save");
		appiumdriver.findElementByAccessibilityId("Discard").click();
	}
	
	public void clickCreateInvoiceIconForWOViaSearch(String workOrderNumber) {
		clickCreateInvoiceIconForWO(workOrderNumber);
	}

	public void clickCreateInvoiceIconForWO(String workOrderNumber)  {
		new WebDriverWait(appiumdriver, 20)
		  .until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(workOrderNumber)));
		appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(workOrderNumber))
				.findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}
	
	public void clickCreateInvoiceIconForWOs(String[] wos) {
		for (int i = 0; i < wos.length; i++) {
			clickCreateInvoiceIconForWO(wos[i]);

		}
	}
	
	public String getPriceValueForWO(String workOrderNumber) {
		return mywotable.findElementByAccessibilityId(workOrderNumber).findElementByAccessibilityId("labelOrderAmount").getAttribute("label");
	}
	
	public boolean woExists(String workOrderNumber) {
		WaitUtils.waitUntilElementIsClickable(mywotable);
		return appiumdriver.findElements(MobileBy.AccessibilityId(workOrderNumber)).size() > 0;	
	}
	
	public boolean isWorkOrderHasApproveIcon(String workOrderNumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + workOrderNumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_78')]")).size() > 0;
	}
	
	public boolean isWorkOrderHasActionIcon(String workOrderNumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + workOrderNumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_79')]")).size() > 0;
	}

	public void clickInvoiceIcon() {
		appiumdriver.findElementByAccessibilityId("invoice new").click();
	}

	public <T extends IBaseWizardScreen> T selectInvoiceType(IInvoicesTypes invoiceType) {
		if (!appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).isDisplayed()) {
		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + invoiceType.getInvoiceTypeName() + "']/..")));
		}
		appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).click();
		return invoiceType.getFirstVizardScreen();
	}

	public String selectInvoiceTypeAndAcceptAlert(IInvoicesTypes invoiceType) {
		if (!appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).isDisplayed()) {
			swipeToElement(appiumdriver.
					findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + invoiceType.getInvoiceTypeName() + "']/..")));
		}
		appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).click();
		return Helpers.getAlertTextAndAccept();
	}

	public <T extends IBaseWizardScreen> T selectWorkOrderType(IWorkOrdersTypes workordertype) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		MobileElement woTypeTable = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'")));

		if (!woTypeTable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).isDisplayed()) {
			swipeToElement(woTypeTable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()));
		}
		woTypeTable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).click();
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

	public boolean isNotesIconPresentForWorkOrder(String workOrderId) {
		waitMyWorkOrdersScreenLoaded();
		return mywotable.findElementByAccessibilityId(workOrderId).findElementsByAccessibilityId("ORDER_NOTES").size() > 0;
	}
}
