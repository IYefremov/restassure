  package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

  import com.cyberiansoft.test.ios10_client.utils.Helpers;
  import io.appium.java_client.AppiumDriver;
  import io.appium.java_client.MobileBy;
  import io.appium.java_client.MobileElement;
  import io.appium.java_client.TouchAction;
  import io.appium.java_client.pagefactory.AppiumFieldDecorator;
  import org.openqa.selenium.By;
  import org.openqa.selenium.WebDriver;
  import org.openqa.selenium.support.PageFactory;
  import org.openqa.selenium.support.ui.ExpectedConditions;
  import org.openqa.selenium.support.ui.FluentWait;
  import org.openqa.selenium.support.ui.WebDriverWait;

  import java.time.Duration;
  import java.util.List;
  import java.util.concurrent.TimeUnit;

public class HomeScreen extends iOSHDBaseScreen {
	
	/*@iOSFindBy(accessibility  = "Customers")
    private IOSElement customersbtn;
	
	@iOSFindBy(accessibility  = "My Inspections")
    private IOSElement myinspectionsbtn;
	
	@iOSFindBy(accessibility  = "Team Inspections")
    private IOSElement teaminspectionsbtn;
	
	@iOSFindBy(accessibility  = "My Work Orders")
    private IOSElement myworkordersbtn;
	
	@iOSFindBy(accessibility  = "Car History")
    private IOSElement carhistorybtn;
	
	@iOSFindBy(accessibility  = "My Invoices")
    private IOSElement myinvoicesbtn;
	
	@iOSFindBy(accessibility  = "Team Invoices")
    private IOSElement teaminvoicesbtn;
	
	@iOSFindBy(accessibility  = "Service Requests")
    private IOSElement servicerequestsbtn;
	
	@iOSFindBy(accessibility  = "Status")
    private IOSElement statustsbtn;
	
	@iOSFindBy(accessibility  = "Team Work Orders")
    private IOSElement temworkorderstsbtn;
	
	@iOSFindBy(accessibility  = "Settings")
    private IOSElement settingstsbtn;
	
	@iOSFindBy(accessibility  = "logout")
    private IOSElement logoutbtn;*/
	
	public HomeScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Home")));
	}

	public CustomersScreen clickCustomersButton() {	
		appiumdriver.findElementByAccessibilityId("Customers").click();
		return new CustomersScreen(appiumdriver);
	}

	public MyInspectionsScreen clickMyInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Inspections")));
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(myinspectionsbtn).waitAction(300).release().perform();
		appiumdriver.findElementByAccessibilityId("Inspections").click();
		MyInspectionsScreen inspectionsscreen = new MyInspectionsScreen(appiumdriver);
		inspectionsscreen.switchToMyInspectionsView();
		return inspectionsscreen;
	}
	
	public TeamInspectionsScreen clickTeamInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Inspections")));
		appiumdriver.findElementByAccessibilityId("Inspections").click();
		MyInspectionsScreen inspectionsscreen = new MyInspectionsScreen(appiumdriver);
		inspectionsscreen.switchToTeamInspectionsView();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		return new TeamInspectionsScreen(appiumdriver);
	}

	public MyWorkOrdersScreen clickMyWorkOrdersButton() throws InterruptedException {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Work Orders"))).click(); 
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.switchToLocalMyOrdersView();
		return myworkordersscreen;		
	}
	
	public TeamWorkOrdersScreen clickTeamWorkordersButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Work Orders"))).click(); 
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.switchToTeamWorkOrdersView();		
		return new TeamWorkOrdersScreen(appiumdriver);
	}
	
	public CarHistoryScreen clickCarHistoryButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Car History")));
		appiumdriver.findElementByAccessibilityId("Car History").click();
		return new CarHistoryScreen(appiumdriver);
	}

	public MyInvoicesScreen clickMyInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Invoices")));
		appiumdriver.findElementByAccessibilityId("Invoices").click();
		MyInvoicesScreen myinvoicesscreen = new MyInvoicesScreen(appiumdriver);
		myinvoicesscreen.switchToMyInvoicesView();
		return myinvoicesscreen;
	}
	
	public TeamInvoicesScreen clickTeamInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Invoices")));
		appiumdriver.findElementByAccessibilityId("Invoices").click();
		MyInvoicesScreen myinvoicesscreen = new MyInvoicesScreen(appiumdriver);
		myinvoicesscreen.switchToTeameInvoicesView();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		return new TeamInvoicesScreen(appiumdriver);
	}

	public ServiceRequestsScreen clickServiceRequestsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Service Requests")));
		appiumdriver.findElementByAccessibilityId("Service Requests").click();
		/*if (appiumdriver.findElementsByAccessibilityId("Loading service requests").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading service requests")));
		}
		Helpers.waitABit(500);*/
		return new ServiceRequestsScreen(appiumdriver);
	}
	
	public void clickStatusButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Status")));
		appiumdriver.findElementByAccessibilityId("Status").click();
	}

	public SettingsScreen clickSettingsButton() {
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		//wait.until(ExpectedConditions.elementToBeClickable(servicerequestsbtn));
		//swipeScreenUp();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Settings")));
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId("Settings")).waitAction(Duration.ofSeconds(1)).release().perform();
		return new SettingsScreen(appiumdriver);
	}	
	
	public void updateDatabase() {
        MobileElement toolbar = null;
	    List<MobileElement> toolbars = appiumdriver.findElementsByAccessibilityId("Toolbar");
	    for (MobileElement tlb : toolbars)
	        if (tlb.isDisplayed()) {
                toolbar = tlb;
                break;
            }
        toolbar.findElementByClassName("XCUIElementTypeButton").click();
		Helpers.setTimeOut(180);
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}
	
	public void updateVIN() {
        MobileElement toolbar = null;
        List<MobileElement> toolbars = appiumdriver.findElementsByAccessibilityId("Toolbar");
        for (MobileElement tlb : toolbars)
            if (tlb.isDisplayed()) {
                toolbar = tlb;
                break;
            }
        toolbar.findElementsByClassName("XCUIElementTypeButton").get(1).click();
	    Helpers.setTimeOut(60);
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}

	public MainScreen clickLogoutButton() {
		appiumdriver.findElementByAccessibilityId("logout").click();
		return new MainScreen(appiumdriver);
	}

}
