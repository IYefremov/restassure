package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularServiceRequestsScreen extends iOSRegularBaseScreen {
	
	/*@iOSFindBy(accessibility = "Add")
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
    private IOSElement savebtn;*/
	
	public RegularServiceRequestsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsTable"))); 
	}

	public void clickRefreshButton()  {
		appiumdriver.findElementByAccessibilityId("Refresh").click();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsTable")));
	}
	
	public void clickAddButton() {
		appiumdriver.findElementByAccessibilityId("Add").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
	}
	
	public void selectServiceRequestType(String srtype) {
		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + srtype + "']/..")));
		appiumdriver.findElementByAccessibilityId(srtype).click();
		appiumdriver.findElementByAccessibilityId(srtype).click();
	}

	public void selectServiceRequest(String srnumber) {
		if (!appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']").isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']")));
			//appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']").click();
		}
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']").click();
	}

	public void selectCreateInspectionRequestAction() {
		appiumdriver.findElementByAccessibilityId("Create\nInspection").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Synchronizing with Back Office")));
	}
	
	public void selectEditServiceRequestAction() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Synchronizing with Back Office")));
	}
	
	public void selectCreateWorkOrderRequestAction() {
		appiumdriver.findElementByAccessibilityId("Create\nWO").click();
	}
	
	public void selectAppointmentRequestAction() {
		appiumdriver.findElementByAccessibilityId("Appointments").click();
	}
	
	public void selectCheckInAction() {
		appiumdriver.findElementByAccessibilityId("Check In").click();
	}
	
	public boolean isUndoCheckInActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Undo\nCheck In").size() > 0;
	}
	
	public void selectRejectAction() {
		appiumdriver.findElementByAccessibilityId("Reject").click();
	}
	
	public void selectCloseAction() {
		appiumdriver.findElementByAccessibilityId("Close").click();
	}
	
	public void selectAcceptAction() {
		appiumdriver.findElementByAccessibilityId("Accept").click();
	}
	
	public void selectDeclineAction() {
		appiumdriver.findElementByAccessibilityId("Decline").click();
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
		appiumdriver.findElementByAccessibilityId("Cancel").click();
	}

	public void selectInspectionType(String inspectiontype) {
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectiontype + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectiontype + "']/..")));
			appiumdriver.findElementByAccessibilityId(inspectiontype).click();
		}
		if (elementExists(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectiontype + "']")))
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
		MobileElement srTable = (MobileElement) appiumdriver.findElementByClassName("XCUIElementTypeTable");
		return srTable.findElementByAccessibilityId(srnumber).findElementByAccessibilityId("labelServiceRequestEmployee").getAttribute("value");
	}
	
	public String getServiceRequestVehicleInfo(String srnumber) {
		MobileElement srTable = (MobileElement) appiumdriver.findElementByClassName("XCUIElementTypeTable");
		return srTable.findElementByAccessibilityId(srnumber).findElementByAccessibilityId("libelServiceRequestVehicle").getAttribute("value");
	}
	
	public String getServiceRequestDetails(String srnumber) {
		MobileElement srTable = (MobileElement) appiumdriver.findElementByClassName("XCUIElementTypeTable");
		return srTable.findElementByAccessibilityId(srnumber).findElementByAccessibilityId("labelServiceRequestDetails").getAttribute("value");
	}
	
	public String getServiceRequestStatus(String srnumber) {
		MobileElement srTable = (MobileElement) appiumdriver.findElementByClassName("XCUIElementTypeTable");
		return srTable.findElementByAccessibilityId(srnumber).findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
		//return
		//		findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']/XCUIElementTypeStaticText[1]").getAttribute("value");
	}
	
	public String getFirstServiceRequestNumber() {
		List<WebElement> ws = appiumdriver.findElementByClassName("XCUIElementTypeTable").findElements (MobileBy.AccessibilityId("labelServiceRequestNumber"));
		List<String> nmbr = new ArrayList();
 		for (WebElement el : ws ) {
			nmbr.add(el.getAttribute("value"));
 		}
 		Collections.sort(nmbr);
		return nmbr.get(nmbr.size()-1);
	}
	
	//Appointmets
	
	
	public void selectTodayFromAppointmet() {
		appiumdriver.findElementByAccessibilityId("From").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getFromAppointmetValue() {
		WebElement par = getSRTableParentNode("From");		
		return par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void selectTodayToAppointmet() {
		appiumdriver.findElementByAccessibilityId("To").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getToAppointmetValue() {
		WebElement par = getSRTableParentNode("To");		
		return par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
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
	
	public void selectSummaryRequestAction() {
		appiumdriver.findElementByAccessibilityId("Summary").click();
	}
	
	public void selectDetailsRequestAction() {
		appiumdriver.findElementByAccessibilityId("Details").click();
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
	}
	
	public void clickServiceRequestSummaryOrdersButton() {
		appiumdriver.findElementByAccessibilityId("Work Orders").click();
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
	
	public void selectDoneReason(String selectreason) {
		selectUIAPickerValue(selectreason);
		clickDoneCloseReasonDialog();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsTable")));
	}
	
	public void clickCloseSR() {
		appiumdriver.findElement(MobileBy.name("Close SR")).click();
		new RegularServiceRequestsScreen(appiumdriver);
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
