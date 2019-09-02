package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.IServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
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

import java.util.*;

public class RegularServiceRequestsScreen extends RegularBaseTypeScreen {

	private final TypeScreenContext SRCONTEXT = TypeScreenContext.SERVICEREQUEST;
	
	/*@iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addbtn;
	
	@iOSXCUITFindBy(accessibility = "Refresh")
    private IOSElement refreshbtn;
	
	@iOSXCUITFindBy(accessibility = "Create\nInspection")
    private IOSElement createinspectionmenu;
	
	@iOSXCUITFindBy(accessibility = "Edit")
    private IOSElement editmenu;
	
	@iOSXCUITFindBy(accessibility = "Accept")
    private IOSElement acceptsrmenu;
	
	@iOSXCUITFindBy(accessibility = "Decline")
    private IOSElement declinesrmenu;
	
	@iOSXCUITFindBy(accessibility = "Create\nWO")
    private IOSElement createworkordermenu;
	
	@iOSXCUITFindBy(accessibility = "Appointments")
    private IOSElement appointmentmenu;
	
	@iOSXCUITFindBy(accessibility = "Check In")
    private IOSElement checkinmenu;
	
	@iOSXCUITFindBy(accessibility = "Close")
    private IOSElement closemenu;
	
	@iOSXCUITFindBy(accessibility = "Reject")
    private IOSElement rejectmenu;
	
	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelmenu;
	
	//Appointment
	@iOSXCUITFindBy(accessibility = "From")
    private IOSElement fromfld;
	
	@iOSXCUITFindBy(accessibility = "To")
    private IOSElement tofld;
	
	@iOSXCUITFindBy(accessibility = "Subject")
    private IOSElement subjectfld;
	
	@iOSXCUITFindBy(accessibility = "Address")
    private IOSElement addressfld;
	
	@iOSXCUITFindBy(accessibility = "City")
    private IOSElement cityfld;
	
	@iOSXCUITFindBy(accessibility = "Summary")
    private IOSElement summarybtn;
	
	@iOSXCUITFindBy(accessibility = "ServiceRequestSummaryInspectionsButton")
    private IOSElement srsummaryinspectionsbtn;
	
	@iOSXCUITFindBy(accessibility = "Work Orders")
    private IOSElement srsummaryordrersbtn;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;*/

	@iOSXCUITFindBy(accessibility = "ServiceRequestsTable")
	private IOSElement serviceRequestsTable;

	@iOSXCUITFindBy(accessibility = "Done")
	private IOSElement donebtn;
	
	public RegularServiceRequestsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public WebElement waitForServiceRequestScreenLoad() {
		return WaitUtils.waitUntilElementIsClickable(serviceRequestsTable);
		//FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
		//wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsTable")));
	}

	public void clickRefreshButton()  {
		appiumdriver.findElementByAccessibilityId("Refresh").click();
		waitForServiceRequestScreenLoad();
	}
	
	public void clickAddButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Add"))).click();
	}
	
	public <T extends IBaseWizardScreen> T selectServiceRequestType(IServiceRequestTypes serviceRequestType) {
		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + serviceRequestType.getServiceRequestTypeName() + "']/..")));
		appiumdriver.findElementByAccessibilityId(serviceRequestType.getServiceRequestTypeName()).click();
		return serviceRequestType.getFirstVizardScreen();
	}

	public <T extends IBaseWizardScreen> T addServiceRequest(ServiceRequestTypes serviceRequestType) {
		clickAddButton();
		return selectServiceRequestType(serviceRequestType);
	}

	public void selectServiceRequest(String serviceRequestNumber) {
		waitForServiceRequestScreenLoad();
		if (!serviceRequestsTable.findElementByAccessibilityId(serviceRequestNumber).isDisplayed()) {
			swipeToElement(serviceRequestsTable.findElementByAccessibilityId(serviceRequestNumber));
		}
		serviceRequestsTable.findElementByAccessibilityId(serviceRequestNumber).click();
	}
	
	public void selectEditServiceRequestAction() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void selectCreateWorkOrderRequestAction() {
		appiumdriver.findElementByAccessibilityId("Create\nWO").click();
	}
	
	public void selectAppointmentRequestAction() {
		appiumdriver.findElementByAccessibilityId("Appointments").click();
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

	public <T extends IBaseWizardScreen> T selectInspectionType(IInspectionsTypes inspectionType) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(inspectionType.getInspectionTypeName())));
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectionType.getInspectionTypeName() + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectionType.getInspectionTypeName() + "']/..")));
			appiumdriver.findElementByAccessibilityId(inspectionType.getInspectionTypeName()).click();
		}
		if (elementExists(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectionType.getInspectionTypeName() + "']")))
			appiumdriver.findElementByAccessibilityId(inspectionType.getInspectionTypeName()).click();
		return inspectionType.getFirstVizardScreen();
	}

	public <T extends IBaseWizardScreen> T selectWorkOrderType(IWorkOrdersTypes workOrderType) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(workOrderType.getWorkOrderTypeName())));
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + workOrderType.getWorkOrderTypeName() + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
					findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + workOrderType.getWorkOrderTypeName() + "']/..")));
			appiumdriver.findElementByAccessibilityId(workOrderType.getWorkOrderTypeName()).click();
		}
		if (elementExists(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + workOrderType.getWorkOrderTypeName() + "']")))
			appiumdriver.findElementByAccessibilityId(workOrderType.getWorkOrderTypeName()).click();
		return workOrderType.getFirstVizardScreen();
	}
	
	public String getServiceRequestClient(String srnumber) {
		return appiumdriver.
				findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + srnumber + "']/XCUIElementTypeStaticText[@name='labelServiceRequestClient']")
				.getAttribute("value");
	}
	
	public String getServiceRequestDetails(String srnumber) {
		MobileElement srTable = (MobileElement) appiumdriver.findElementByClassName("XCUIElementTypeTable");
		return srTable.findElementByAccessibilityId(srnumber).findElementByAccessibilityId("labelServiceRequestDetails").getAttribute("value");
	}
	
	public String getServiceRequestStatus(String srnumber) {
		;
		return waitForServiceRequestScreenLoad().findElement(MobileBy.AccessibilityId(srnumber)).findElement(MobileBy.className("XCUIElementTypeStaticText")).getAttribute("value");
	}
	
	public String getFirstServiceRequestNumber() {
		WebElement srTable = waitForServiceRequestScreenLoad();
		Optional<WebElement> ss =srTable
				.findElements(MobileBy.AccessibilityId("labelServiceRequestNumber"))
				.stream().max(Comparator.comparing(o -> o.getAttribute("value")));
		return ss.get().getAttribute("value");
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
	
	public void setSubjectAppointmet(String _subject)  {
		appiumdriver.findElementByAccessibilityId("Subject").click();
		appiumdriver.findElementByAccessibilityId("Subject").sendKeys(_subject);

	}
	
	public void setAddressAppointmet(String _address)  {
		appiumdriver.findElementByAccessibilityId("Address").click();
		appiumdriver.findElementByAccessibilityId("Address").sendKeys(_address);
	}
	
	public void setCityAppointmet(String _city)  {
		appiumdriver.findElementByAccessibilityId("City").click();
		appiumdriver.findElementByAccessibilityId("City").sendKeys(_city);
	}
	
	public void selectDetailsRequestAction() {
		appiumdriver.findElementByAccessibilityId("Details").click();
	}

	public void saveAppointment() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Appointments")));
	}
	
	//Close reason UIAPicker
	public void clickCancelCloseReasonDialog() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Reasons")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}
	
	public void clickDoneCloseReasonDialog() {
		donebtn.click();
	}
	
	public void selectDoneReason(String selectreason) {
		selectUIAPickerValue(selectreason);
		clickDoneCloseReasonDialog();
		waitForServiceRequestScreenLoad();
	}
	
	public void clickCloseSR() {
		appiumdriver.findElement(MobileBy.name("Close SR")).click();
		new RegularServiceRequestsScreen();
	}
	
	public WebElement getSRTableParentNode(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + cellname + "']/.."));
	}
	
	public boolean isServiceRequestProposed(String serviceRequestNumber) {
		return serviceRequestsTable.findElements(By.xpath("//XCUIElementTypeCell[@name='" + serviceRequestNumber
				+ "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_109')]")).size() > 0;
	}
	
	public boolean isServiceRequestOnHold(String serviceRequestNumber) {
		return serviceRequestsTable.findElements(By.xpath("//XCUIElementTypeCell[@name='" + serviceRequestNumber
				+ "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_90')]")).size() > 0;
	}

	public boolean isInspectionIconPresentForServiceRequest(String serviceRequestNumber) {
		return serviceRequestsTable.findElementByAccessibilityId(serviceRequestNumber).findElementsByName("SR_INSPECTION_CREATED").size() > 0;
	}

	public boolean isWorkOrderIconPresentForServiceRequest(String serviceRequestNumber) {
		return serviceRequestsTable.findElementByAccessibilityId(serviceRequestNumber).findElementsByName("SR_WO_CREATED").size() > 0;
	}

	public void clickBackButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();
	}
}
