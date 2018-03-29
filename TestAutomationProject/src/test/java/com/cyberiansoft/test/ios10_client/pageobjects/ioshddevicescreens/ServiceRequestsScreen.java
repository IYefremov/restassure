package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class ServiceRequestsScreen extends iOSHDBaseScreen {
	
	/*@iOSFindBy(accessibility  = "Add")
    private IOSElement addbtn;
	
	@iOSFindBy(accessibility  = "Refresh")
    private IOSElement refreshbtn;
	
	@iOSFindBy(accessibility  = "Create Inspection")
    private IOSElement createinspectionmenu;
	
	@iOSFindBy(accessibility  = "Create Work Order")
    private IOSElement createworkordermenu;
	
	@iOSFindBy(accessibility  = "Appointments")
    private IOSElement appointmentmenu;
	
	@iOSFindBy(accessibility  = "Check In") 
    private IOSElement checkinmenu;
	
	
	@iOSFindBy(accessibility  = "Undo Check In")
    private IOSElement undocheckinmenu;
	
	@iOSFindBy(accessibility  = "Close")
    private IOSElement closemenu;
	
	@iOSFindBy(accessibility  = "Reject")
    private IOSElement rejectmenu;
	
	@iOSFindBy(accessibility  = "Edit")
    private IOSElement editmenu;
	
	@iOSFindBy(accessibility = "Accept")
    private IOSElement acceptsrmenu;
	
	@iOSFindBy(accessibility = "Decline")
    private IOSElement declinesrmenu;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelmenu;
	
	@iOSFindBy(accessibility  = "Close")
    private IOSElement closebtn;
	
	//Appointment
	@iOSFindBy(xpath = "//XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeNavigationBar[@name='AppointmentsView']/XCUIElementTypeButton[@name='Add']")
    private IOSElement addappointmentbtn;
	
	@iOSFindBy(accessibility  = "From")
    private IOSElement fromfld;
	
	@iOSFindBy(accessibility  = "To")
    private IOSElement tofld;
	
	@iOSFindBy(accessibility  = "Done")
    private IOSElement donebtn;
	
	@iOSFindBy(accessibility  = "Subject")
    private IOSElement subjectfld;
	
	@iOSFindBy(accessibility  = "Address")
    private IOSElement addressfld;
	
	@iOSFindBy(accessibility  = "City")
    private IOSElement cityfld;
	
	@iOSFindBy(accessibility  = "Summary")
    private IOSElement summarybtn;
	
	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSFindBy(accessibility = "Not Checked In")
    private IOSElement notcheckedinfilteritem;
	
	@iOSFindBy(accessibility = "Delete")
    private IOSElement deletefilterbtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savefilterbtn;
	
	@iOSFindBy(accessibility  = "ServiceRequestSummaryInspectionsButton")
    private IOSElement srsummaryinspectionsbtn;
	
	@iOSFindBy(accessibility  = "Work Orders")
    private IOSElement srsummaryordrersbtn;
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;*/
	
	public ServiceRequestsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("ServiceRequestsPageTableLeft"))); 
	}

	public void clickRefreshButton() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Refresh").click();
		Thread.sleep(3000);
	}
	
	public void clickAddButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Add"))).click();
		//appiumdriver.findElementByAccessibilityId("Add").click();
	}
	
	public void clickSearchButton() {
		appiumdriver.findElementByAccessibilityId("Search").click();
	}
	
	public void clickNotCheckedInFilterItem() {
		appiumdriver.findElementByAccessibilityId("Not Checked In").click();
	}
	
	public void clickSaveFilter() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}
	
	public void applyNotCheckedInFilter() throws InterruptedException {
		clickSearchButton();
		clickNotCheckedInFilterItem();
		clickSaveFilter();
		Thread.sleep(3000);
	}
	
	public void resetFilter() throws InterruptedException {
		clickSearchButton();
		appiumdriver.findElementByAccessibilityId("Delete").click();
		clickSaveFilter();
		Thread.sleep(3000);
	}
	
	public void selectServiceRequestType(String srtype) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("ServiceRequestTypeSelector"))); 
		WebElement inptypetable = null;
		
		List<WebElement> tbls = appiumdriver.findElementsByAccessibilityId("ServiceRequestTypeSelector");
		for (WebElement tb : tbls) {
			if (tb.getAttribute("type").equals("XCUIElementTypeTable")) {
				inptypetable = tb;
				break;
			}
		}
		if (!appiumdriver.findElementByAccessibilityId(srtype).isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByAccessibilityId(srtype),
					inptypetable);
			appiumdriver.findElementByAccessibilityId(srtype).click();
		}
		appiumdriver.findElementByAccessibilityId(srtype).click();
		Helpers.waitABit(1000);
	}
	
	public void selectInspectionType(String insptype) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("InspectionTypeSelector"))); 
		WebElement inptypetable = null;
		
		List<WebElement> tbls = appiumdriver.findElementsByAccessibilityId("InspectionTypeSelector");
		for (WebElement tb : tbls) {
			if (tb.getAttribute("type").equals("XCUIElementTypeTable")) {
				inptypetable = tb;
				break;
			}
		}
		if (!appiumdriver.findElementByAccessibilityId(insptype).isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByAccessibilityId(insptype),
					inptypetable);
			appiumdriver.findElementByAccessibilityId(insptype).click();
		}
		appiumdriver.findElementByAccessibilityId(insptype).click();
		Helpers.waitABit(1000);
	}
	
	public void selectWOType(String wotype) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("OrderTypeSelector"))); 
		WebElement inptypetable = null;
		
		List<WebElement> tbls = appiumdriver.findElementsByAccessibilityId("OrderTypeSelector");
		for (WebElement tb : tbls) {
			if (tb.getAttribute("type").equals("XCUIElementTypeTable")) {
				inptypetable = tb;
				break;
			}
		}
		if (!appiumdriver.findElementByAccessibilityId(wotype).isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByAccessibilityId(wotype),
					inptypetable);
			appiumdriver.findElementByAccessibilityId(wotype).click();
		}
		appiumdriver.findElementByAccessibilityId(wotype).click();
		Helpers.waitABit(1000);
	}

	public void selectServiceRequest(String servicerequest) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicerequest + "']").click();
		//Helpers.text(servicerequest).click();
	}

	public void selectCreateInspectionRequestAction() {
		appiumdriver.findElementByAccessibilityId("Create Inspection").click();
	}
	
	public void selectCreateWorkOrderRequestAction() {
		appiumdriver.findElementByAccessibilityId("Create Work Order").click();
	}
	
	public void selectAppointmentRequestAction() {
		appiumdriver.findElementByAccessibilityId("Appointments").click();
	}
	
	public boolean isAcceptAppointmentRequestActionExists() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("E"))); 
		return appiumdriver.findElementsByAccessibilityId("approve little").size() > 0;
	}
	
	public boolean isDeclineAppointmentRequestActionExists() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("E"))); 
		return appiumdriver.findElementsByAccessibilityId("decline little").size() > 0;
	}
	
	public void selectCheckInMenu() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Check In").click();
		Thread.sleep(3000);
	}
	
	public void selectUndoCheckMenu() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Undo Check In").click();
		Thread.sleep(3000);
	}
	
	public void selectRejectAction() {
		appiumdriver.findElementByAccessibilityId("Reject").click();
	}
	
	public void selectAcceptAction() {
		appiumdriver.findElementByAccessibilityId("Accept").click();
	}
	
	public void selectDeclineAction() {
		appiumdriver.findElementByAccessibilityId("Decline").click();
	}
	
	public void selectCloseAction() {
		appiumdriver.findElementByAccessibilityId("Close").click();
	}
	
	public boolean isRejectActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Reject").size() > 0;
	}
	
	public boolean isCloseActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Close").size() > 0;
	}
	
	public boolean isAcceptActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Accept").size() > 0;
	}
	
	public boolean isDeclineActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Decline").size() > 0;
	}
	
	public boolean isServiceRequestExists(String srnumber) {
		return appiumdriver.findElementsByAccessibilityId(srnumber).size() > 0;
	}
	
	public void selectEditServiceRequestAction() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void selectCancelAction() {
		appiumdriver.findElementByAccessibilityId("Cancel").click();
	}
	
	public String getWorkOrderNumber() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeStaticText[3]")  .getAttribute("value");
	}
	
	public String getInspectionNumber() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeStaticText[3]")  .getAttribute("value");
		//return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[3]").getText();
	}
	
	public String getServiceRequestClient(String srnumber) {
		return ((MobileElement) appiumdriver.findElementByName(srnumber)).findElementByAccessibilityId("labelServiceRequestClient")
				.getAttribute("value");
	}
	
	public String getServiceRequestEmployee(String srnumber) {
		return ((MobileElement) appiumdriver.findElementByName(srnumber)).findElementByAccessibilityId("labelServiceRequestEmployee")
				.getAttribute("value");
	}
	
	public String getServiceRequestVehicleInfo(String srnumber) {
		return ((MobileElement) appiumdriver.findElementByName(srnumber)).findElementByAccessibilityId("labelServiceRequestVehicle")
				.getAttribute("value");
	}
	
	public String getServiceRequestStatus(String srnumber) {
		return ((MobileElement) appiumdriver.findElementByName(srnumber)).findElementByAccessibilityId("labelServiceRequestPhaseStatus")
				.getAttribute("value");
	}
	
	public String getFirstServiceRequestNumber() {
		List<WebElement> ws = appiumdriver.findElementsByAccessibilityId("labelServiceRequestNumber");
		List<String> nmbr = new ArrayList();
 		for (WebElement el : ws ) {
			nmbr.add(el.getAttribute("value"));
 		}
 		Collections.sort(nmbr);
		return nmbr.get(nmbr.size()-1);
	}
	
	//Appointmets
	public void clickAddAppointmentButton() {
		Helpers.waitABit(1000);
		//FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeNavigationBar[@name='AppointmentsView']/XCUIElementTypeButton[@name='Add']"))).click(); 
		List<WebElement> addds = appiumdriver.findElementsByAccessibilityId("Add");
		for(WebElement add : addds)
			if (add.isDisplayed()) {
				add.click();
				break;
			}
		//IOSElement appointmentview = (IOSElement) appiumdriver.findElementsByAccessibilityId("AppointmentsView").get(1);
		//appointmentview.findElementByAccessibilityId("Add").click();
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(appointmentview.findElementByAccessibilityId("Add")).waitAction(Duration.ofSeconds(1)).release().perform();
		//addappointmentbtn.click();
	}
	
	public void selectTodayFromAppointmet() {
		appiumdriver.findElementByAccessibilityId("From").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getFromAppointmetValue() {
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='From']/.."); 
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void selectTodayToAppointmet() {
		appiumdriver.findElementByAccessibilityId("To").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		Helpers.waitABit(500);
	}
	
	public String getToAppointmetValue() {
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='To']/.."); 
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void setSubjectAppointmet(String _subject) throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Subject").click();
		Helpers.keyboadrType(_subject+"\n");
	}
	
	public void setAddressAppointmet(String _address) throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Address").click();
		Helpers.keyboadrType(_address+"\n");
	}
	
	public void setCityAppointmet(String _city) throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("City").click();
		Helpers.keyboadrType(_city+"\n");
	}
	
	/*public void selectSummaryRequestAction() {
		appiumdriver.findElementByAccessibilityId("Summary").click();
	}*/
	
	public void selectDetailsRequestAction() {
		appiumdriver.findElementByAccessibilityId("Details").click();
	}

	public String getSummaryAppointmentsInformation() {
		return appiumdriver.findElementByXPath("//UIATableView[2]/UIATableCell[@name=\"Appointments\"]/UIAStaticText[2]").getAttribute("value");
	}
	
	public boolean isSRSummaryAppointmentsInformation() {
		return appiumdriver.findElementsByAccessibilityId("Appointments").size() > 0;
	}
	
	public void clickServiceRequestSummaryInspectionsButton() {
		appiumdriver.findElementByAccessibilityId("ServiceRequestSummaryInspectionsButton").click();
	}
	
	public MyWorkOrdersScreen clickServiceRequestSummaryOrdersButton() {
		appiumdriver.findElementByAccessibilityId("Work Orders").click();
		return new MyWorkOrdersScreen(appiumdriver);
	}
	
	public void saveAppointment() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}
	
	//Close reason UIAPicker
	public void clickCancelCloseReasonDialog() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}
			
	public void clickDoneCloseReasonDialog() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Done")).click();
	}
			
	public void selectDoneReason(String selectreason) throws InterruptedException {
		selectUIAPickerValue(selectreason);
		clickDoneCloseReasonDialog();
	}
			
	public void clickCloseSR() {
		appiumdriver.findElement(MobileBy.name("Close SR")).click();
		Helpers.waitABit(3000);
	}
	
	public boolean isUndoCheckInActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Undo Check In").size() > 0;
	}
	
	public void waitServiceRequestsScreenLoaded() {
		if (appiumdriver.findElementsByAccessibilityId("Loading service requests").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading service requests")));
		}
	}
	
	public boolean isServiceRequestProposed(String srnumber) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + srnumber 
				+ "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_75')]")).size() > 0;
	}
	
	public boolean isServiceRequestOnHold(String srnumber) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + srnumber 
				+ "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_46')]")).size() > 0;
	}
	
	public void clickCloseButton() {
		appiumdriver.findElementByAccessibilityId("Close").click();
		Helpers.waitABit(500);
	}
	
	public HomeScreen clickHomeButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Back"))).click();
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(appiumdriver.findElementByAccessibilityId("Back")).waitAction(waitOptions(ofSeconds(2))).release().perform();
		return new HomeScreen(appiumdriver);		
	}
}
