package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularServiceRequestsScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement addbtn;
	
	@iOSFindBy(accessibility = "Refresh")
    private IOSElement refreshbtn;
	
	@iOSFindBy(accessibility = "Create\nInspection")
    private IOSElement createinspectionmenu;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editmenu;
	
	@iOSFindBy(accessibility = "Accept")
    private IOSElement acceptsrmenu;
	
	@iOSFindBy(accessibility = "Decline")
    private IOSElement declinesrmenu;
	
	@iOSFindBy(accessibility = "Create\nWO")
    private IOSElement createworkordermenu;
	
	@iOSFindBy(accessibility = "Appointments")
    private IOSElement appointmentmenu;
	
	@iOSFindBy(accessibility = "Check In")
    private IOSElement checkinmenu;
	
	@iOSFindBy(accessibility = "Close")
    private IOSElement closemenu;
	
	@iOSFindBy(accessibility = "Reject")
    private IOSElement rejectmenu;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelmenu;
	
	//Appointment
	@iOSFindBy(accessibility = "From")
    private IOSElement fromfld;
	
	@iOSFindBy(accessibility = "To")
    private IOSElement tofld;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement donebtn;
	
	@iOSFindBy(accessibility = "Subject")
    private IOSElement subjectfld;
	
	@iOSFindBy(accessibility = "Address")
    private IOSElement addressfld;
	
	@iOSFindBy(accessibility = "City")
    private IOSElement cityfld;
	
	@iOSFindBy(accessibility = "Summary")
    private IOSElement summarybtn;
	
	@iOSFindBy(accessibility = "ServiceRequestSummaryInspectionsButton")
    private IOSElement srsummaryinspectionsbtn;
	
	@iOSFindBy(accessibility = "Work Orders")
    private IOSElement srsummaryordrersbtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	public RegularServiceRequestsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsTable"))); 
	}

	public void clickRefreshButton() throws InterruptedException {
		refreshbtn.click();
		Thread.sleep(3000);
	}
	
	public void clickAddButton() {
		addbtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
	}
	
	public void selectServiceRequestType (String srtype) {
		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + srtype + "']/..")));
		appiumdriver.findElementByAccessibilityId(srtype).click();
		appiumdriver.findElementByAccessibilityId(srtype).click();
	}

	public void selectServiceRequest(String srnumber) {
		Helpers.waitABit(6000);
		if (!appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']").isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']")));
			appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']").click();
		}
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']").click();
		Helpers.waitABit(1500);
	}

	public void selectCreateInspectionRequestAction() {
		createinspectionmenu.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Synchronizing with Back Office")));
		Helpers.waitABit(1500);
	}
	
	public void selectEditServiceRequestAction() {
		editmenu.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Synchronizing with Back Office")));
		Helpers.waitABit(1500);
	}
	
	public void selectCreateWorkOrderRequestAction() {
		createworkordermenu.click();
	}
	
	public void selectAppointmentRequestAction() {
		appointmentmenu.click();
		Helpers.waitABit(1000);
	}
	
	public void selectCheckInAction() {
		checkinmenu.click();
		Helpers.waitABit(1000);
	}
	
	public boolean isUndoCheckInActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Undo\nCheck In").size() > 0;
	}
	
	public void selectRejectAction() {
		rejectmenu.click();
	}
	
	public void selectCloseAction() {
		closemenu.click();
	}
	
	public void selectAcceptAction() {
		acceptsrmenu.click();
	}
	
	public void selectDeclineAction() {
		declinesrmenu.click();
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
	
	public boolean isAcceptAppointmentRequestActionExists() {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeButton[@name='approve little']").size() > 0;
	}
	
	public boolean isDeclineAppointmentRequestActionExists() {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeButton[@name='decline little']").size() > 0;
	}
	
	public boolean isServiceRequestExists(String srnumber) {
		return Helpers.elementExists(MobileBy.name(srnumber));
	}
	
	public void selectCancelAction() {
		cancelmenu.click();
	}

	public void selectInspectionType(String inspectiontype) {
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectiontype + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectiontype + "']/..")));
			appiumdriver.findElementByAccessibilityId(inspectiontype).click();
		}
		appiumdriver.findElementByAccessibilityId(inspectiontype).click();
	}
	
	public String getWorkOrderNumber() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[contains(@name,\"O-02\")]").getText();
	}
	
	public String getInspectionNumber() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[contains(@name,\"E-00\")]").getText();
	}
	
	public String getServiceRequestClient(String srnumber) {
		return appiumdriver.
				findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']/XCUIElementTypeStaticText[@name='labelServiceRequestClient']")
				.getAttribute("value");
	}
	
	public String getServiceRequestEmployee(String srnumber) {
		return appiumdriver.
				findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']/XCUIElementTypeStaticText[@name='labelServiceRequestEmployee']").
				getAttribute("value");
	}
	
	public String getServiceRequestVehicleInfo(String srnumber) {
		return appiumdriver.
				findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']/XCUIElementTypeStaticText[@name='libelServiceRequestVehicle']").
				getAttribute("value");
	}
	
	public String getServiceRequestDetails(String srnumber) {
		return appiumdriver.
				findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']/XCUIElementTypeStaticText[@name='labelServiceRequestDetails']").
				getAttribute("value");
	}
	
	public String getServiceRequestStatus(String srnumber) {
		return appiumdriver.
				findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']/XCUIElementTypeStaticText[1]").getAttribute("value");
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
	
	
	public void selectTodayFromAppointmet() {
		fromfld.click();
		donebtn.click();
	}
	
	public String getFromAppointmetValue() {
		WebElement par = getSRTableParentNode("From");		
		return par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void selectTodayToAppointmet() {
		tofld.click();
		donebtn.click();
	}
	
	public String getToAppointmetValue() {
		WebElement par = getSRTableParentNode("To");		
		return par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void setSubjectAppointmet(String _subject) throws InterruptedException {
		subjectfld.click();
		Helpers.keyboadrType(_subject+"\n");
	}
	
	public void setAddressAppointmet(String _address) throws InterruptedException {
		addressfld.click();
		Helpers.keyboadrType(_address+"\n");
	}
	
	public void setCityAppointmet(String _city) throws InterruptedException {
		cityfld.click();
		Helpers.keyboadrType(_city+"\n");
	}
	
	public void selectSummaryRequestAction() {
		summarybtn.click();
		Helpers.waitABit(1000);
	}

	public String getSummaryAppointmentsInformation() {
		WebElement par = getSRTableParentNode("Appointments");		
		return par.findElement(MobileBy.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public boolean isSRSummaryAppointmentsInformation() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name=\"ServiceRequestSummaryTable\"]/XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Appointments\"]").isDisplayed();
	}
	
	public void clickServiceRequestSummaryInspectionsButton() {
		new TouchAction(appiumdriver).tap(appiumdriver.findElementByAccessibilityId("Inspections")).perform() ;
		//new TouchAction(appiumdriver).tap(srsummaryinspectionsbtn).perform() ;
		//srsummaryinspectionsbtn.click();
	}
	
	public void clickServiceRequestSummaryOrdersButton() {
		srsummaryordrersbtn.click();
	}
	
	public void saveAppointment() {
		savebtn.click();
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
	
	public WebElement getSRTableParentNode(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + cellname + "']/.."));
	}
	
	public boolean isServiceRequestProposed(String srnumber) {
		WebElement srtable = appiumdriver.findElementByAccessibilityId("ServiceRequestsTable");
		return srtable.findElements(By.xpath("//XCUIElementTypeCell[@name='" + srnumber 
				+ "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_109')]")).size() > 0;
	}
	
	public boolean isServiceRequestOnHold(String srnumber) {
		WebElement srtable = appiumdriver.findElementByAccessibilityId("ServiceRequestsTable");
		return srtable.findElements(By.xpath("//XCUIElementTypeCell[@name='" + srnumber 
				+ "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_90')]")).size() > 0;
	}

}
