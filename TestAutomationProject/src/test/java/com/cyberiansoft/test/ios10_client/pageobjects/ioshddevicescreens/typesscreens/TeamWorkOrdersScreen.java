package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.OrderMonitorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectEmployeePopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServiceRequestdetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TeamWorkOrdersScreen extends BaseTypeScreenWithTabs {

	private final TypeScreenContext TEAMWOCONTEXT = TypeScreenContext.TEAMWORKORDER;

	/*@iOSFindBy(accessibility  = "Monitor")
    private IOSElement womonitor;

	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;

	@iOSFindBy(accessibility = "Edit")
    private IOSElement editmanu;

	@iOSFindBy(accessibility = "invoice new")
    private IOSElement invoicenewbtn;

	@iOSFindBy(accessibility = "Location")
    private IOSElement locationfld;

	@iOSFindBy(accessibility = "Save")
    private IOSElement searccsavebtn;*/

	@iOSFindBy(accessibility = "Add")
	private IOSElement addorderbtn;

	public TeamWorkOrdersScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 35);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("TeamOrdersPageTableLeft")));
	}

	public void clickAddOrderButton() {
		addorderbtn.click();
		if (elementExists("Discard"))
			appiumdriver.findElementByAccessibilityId("Discard").click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
	}

	public void clickOnWO(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).click();
	}

	public OrderMonitorScreen selectWOMonitor() {
		appiumdriver.findElementByAccessibilityId("Monitor").click();
		return new OrderMonitorScreen();
	}

	public void selectEditWO() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
	}

	public InvoiceInfoScreen selectWOInvoiceType(IInvoicesTypes invoiceType) {
		appiumdriver.findElementByAccessibilityId(invoiceType.getInvoiceTypeName()).click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
		return new InvoiceInfoScreen();
	}

	public boolean isCreateInvoiceActivated(String wonumber) {
		return appiumdriver.findElementsByAccessibilityId("invoice new").size() > 0;
	}

	public void clickiCreateInvoiceButton()  {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("invoice new")));
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("invoice new")).click();
		//wait = new WebDriverWait(appiumdriver, 5);
		//wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("invoice new"))).click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
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
		selectWorkOrder(wonumber);
		appiumdriver.findElementByAccessibilityId("Edit").click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(wonumber)));
		appiumdriver.findElementByAccessibilityId(wonumber).findElement(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
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
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_38')]")).size() > 0;
	}

	public String getPriceValueForWO(String wonumber) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeStaticText[@name='labelOrderAmount']").getAttribute("value");
	}


	public boolean woExists(String wonumber) {
		return elementExists(wonumber);
	}

	public ServiceRequestdetailsScreen clickServiceRequestButton() {
		appiumdriver.findElementByAccessibilityId("Service Request").click();
		return new ServiceRequestdetailsScreen();
	}

}
