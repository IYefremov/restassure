package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
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

public class RegularServiceRequestsScreen extends RegularBaseTypeScreen {

	private final TypeScreenContext SRCONTEXT = TypeScreenContext.SERVICEREQUEST;
	
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
	
	public RegularServiceRequestsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsTable"))); 
	}

	public void clickRefreshButton()  {
		appiumdriver.findElementByAccessibilityId("Refresh").click();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsTable")));
	}
	
	public void clickAddButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Add")));
		appiumdriver.findElementByAccessibilityId("Add").click();
		RegularBaseWizardScreen.typeContext = SRCONTEXT;
	}
	
	public <T extends IBaseWizardScreen> T selectServiceRequestType(ServiceRequestTypes serviceRequestType) {
		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + serviceRequestType.getServiceRequestTypeName() + "']/..")));
		appiumdriver.findElementByAccessibilityId(serviceRequestType.getServiceRequestTypeName()).click();
		appiumdriver.findElementByAccessibilityId(serviceRequestType.getServiceRequestTypeName()).click();
		return serviceRequestType.getFirstVizardScreen();
	}

	public <T extends IBaseWizardScreen> T addServiceRequest(ServiceRequestTypes serviceRequestType) {
		clickAddButton();
		return selectServiceRequestType(serviceRequestType);
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
		RegularBaseWizardScreen.typeContext = SRCONTEXT;
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

	public <T extends IBaseWizardScreen> T selectInspectionType(IInspectionsTypes inspectionType) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
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
	
	public void setSubjectAppointmet(String _subject)  {
		appiumdriver.findElementByAccessibilityId("Subject").click();
		appiumdriver.getKeyboard().sendKeys(_subject+"\n");
		//Helpers.keyboadrType(_subject+"\n");
	}
	
	public void setAddressAppointmet(String _address)  {
		appiumdriver.findElementByAccessibilityId("Address").click();
		appiumdriver.getKeyboard().sendKeys(_address+"\n");
		//Helpers.keyboadrType(_address+"\n");
	}
	
	public void setCityAppointmet(String _city)  {
		appiumdriver.findElementByAccessibilityId("City").click();
		appiumdriver.getKeyboard().sendKeys(_city+"\n");
		//Helpers.keyboadrType(_city+"\n");
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Inspections")));
		appiumdriver.findElementByAccessibilityId("Inspections").click();
	}
	
	public void clickServiceRequestSummaryOrdersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Work Orders")));
		appiumdriver.findElementByAccessibilityId("Work Orders").click();
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
		new RegularServiceRequestsScreen();
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


	public void clickBackButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();
	}
}
