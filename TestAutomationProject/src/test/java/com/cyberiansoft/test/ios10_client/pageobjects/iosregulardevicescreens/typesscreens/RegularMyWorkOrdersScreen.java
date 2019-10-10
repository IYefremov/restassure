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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularMyWorkOrdersScreen extends RegularBaseTypeScreenWithTabs {
	
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
		wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(mywotable));
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(mywotable.findElementByAccessibilityId(workOrderId))).click();
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
		mywotable.findElement(MobileBy.AccessibilityId(workOrderNumber))
				.findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}

	public void clickCreateInvoiceIconForWOs(List<String> workOrders) {
		for (String workOrderID : workOrders) {
			clickCreateInvoiceIconForWO(workOrderID);
		}
	}

	public void clickCreateInvoiceIconForWOs(String[] wos) {
		for (int i = 0; i < wos.length; i++) {
			clickCreateInvoiceIconForWO(wos[i]);

		}
	}
	
	public String getPriceValueForWO(String workOrderNumber) {
		waitMyWorkOrdersScreenLoaded();
		return mywotable.findElementByAccessibilityId(workOrderNumber).findElementByAccessibilityId("labelOrderAmount").getAttribute("label");
	}
	
		public boolean isWorkOrderPresent(String workOrderNumber) {
		waitMyWorkOrdersScreenLoaded();
		return appiumdriver.findElements(MobileBy.AccessibilityId(workOrderNumber)).size() > 0;	
	}

	public void clickInvoiceIcon() {
		appiumdriver.findElementByAccessibilityId("invoice new").click();
	}

	public void selectJob(String jobName) {
		appiumdriver.findElementByName(jobName).click();
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

	public boolean isApproveIconPresentForWorkOrder(String workOrderId) {
		waitMyWorkOrdersScreenLoaded();
		return mywotable.findElementByAccessibilityId(workOrderId).findElementsByAccessibilityId("EntityInfoButtonUnchecked, ButtonImageId_7").size() > 0;
	}

	public boolean isInvoiceIconPresentForWorkOrder(String workOrderId) {
		waitMyWorkOrdersScreenLoaded();
		return mywotable.findElementByAccessibilityId(workOrderId).findElementsByAccessibilityId("EntityInfoButtonUnchecked, ButtonImageId_8").size() > 0;
	}
}
