package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.OrderMonitorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
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

import java.util.concurrent.TimeUnit;

public class TeamWorkOrdersScreen extends MyWorkOrdersScreen {

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

	public TeamWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 25);
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
		return new OrderMonitorScreen(appiumdriver);
	}
	
	public void selectEditWO() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
	}
	
	public InvoiceInfoScreen selectWOInvoiceType(String invoicetype) {
		appiumdriver.findElementByAccessibilityId(invoicetype).click();
		BaseWizardScreen.typeContext = TEAMWOCONTEXT;
		return new InvoiceInfoScreen(appiumdriver);
	}
	
	public boolean isCreateInvoiceActivated(String wonumber) {
		return appiumdriver.findElementsByAccessibilityId("invoice new").size() > 0;
	}
	
	public void clickiCreateInvoiceButton()  {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("invoice new")));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("invoice new"))).click();
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
		new TeamWorkOrdersScreen(appiumdriver);
	}
	
	public void selectSearchLocation(String _location) {
		appiumdriver.findElementByAccessibilityId("Location").click();
		appiumdriver.findElementByName(_location).click();
	}
	
	public void setSearchType(String workordertype)  {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Type']").click();
		Helpers.waitABit(500);
		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype).isDisplayed()) {
			swipeTableUp(wostable.findElementByAccessibilityId(workordertype),
					wostable);
			wostable.findElementByAccessibilityId(workordertype).click();
		}
		//if (appiumdriver.findElementsByAccessibilityId(workordertype).size() > 0)
		//	appiumdriver.findElementByAccessibilityId(workordertype).click();
	}

	public TeamWorkOrdersScreen clickCreateInvoiceIconForTeamWO(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).findElement(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).click();
		return this;
	}

}
