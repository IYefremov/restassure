package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServiceRequestdetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.InspectionTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.ServiceRequestTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceRequestsScreen extends BaseTypeScreen {

	private final TypeScreenContext SRCONTEXT = TypeScreenContext.SERVICEREQUEST;
	
	/*@iOSXCUITFindBy(accessibility  = "Add")
    private IOSElement addbtn;
	
	@iOSXCUITFindBy(accessibility  = "Refresh")
    private IOSElement refreshbtn;
	
	@iOSXCUITFindBy(accessibility  = "Create Work Order")
    private IOSElement createworkordermenu;
	
	@iOSXCUITFindBy(accessibility  = "Appointments")
    private IOSElement appointmentmenu;
	
	@iOSXCUITFindBy(accessibility  = "Check In")
    private IOSElement checkinmenu;

	@iOSXCUITFindBy(accessibility  = "Undo Check In")
    private IOSElement undocheckinmenu;
	
	@iOSXCUITFindBy(accessibility  = "Close")
    private IOSElement closemenu;
	
	@iOSXCUITFindBy(accessibility  = "Reject")
    private IOSElement rejectmenu;
	
	@iOSXCUITFindBy(accessibility  = "Edit")
    private IOSElement editmenu;
	
	@iOSXCUITFindBy(accessibility = "Accept")
    private IOSElement acceptsrmenu;
	
	@iOSXCUITFindBy(accessibility = "Decline")
    private IOSElement declinesrmenu;
	
	@iOSXCUITFindBy(accessibility  = "Cancel")
    private IOSElement cancelmenu;
	
	@iOSXCUITFindBy(accessibility  = "Close")
    private IOSElement closebtn;
	
	//Appointment
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeNavigationBar[@name='AppointmentsView']/XCUIElementTypeButton[@name='Add']")
    private IOSElement addappointmentbtn;
	
	@iOSXCUITFindBy(accessibility  = "From")
    private IOSElement fromfld;
	
	@iOSXCUITFindBy(accessibility  = "To")
    private IOSElement tofld;
	
	@iOSXCUITFindBy(accessibility  = "Done")
    private IOSElement donebtn;
	
	@iOSXCUITFindBy(accessibility  = "Subject")
    private IOSElement subjectfld;
	
	@iOSXCUITFindBy(accessibility  = "Address")
    private IOSElement addressfld;
	
	@iOSXCUITFindBy(accessibility  = "City")
    private IOSElement cityfld;
	
	@iOSXCUITFindBy(accessibility  = "Summary")
    private IOSElement summarybtn;
	
	@iOSXCUITFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSXCUITFindBy(accessibility = "Not Checked In")
    private IOSElement notcheckedinfilteritem;
	
	@iOSXCUITFindBy(accessibility = "Delete")
    private IOSElement deletefilterbtn;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savefilterbtn;
	
	@iOSXCUITFindBy(accessibility  = "ServiceRequestSummaryInspectionsButton")
    private IOSElement srsummaryinspectionsbtn;
	
	@iOSXCUITFindBy(accessibility  = "Work Orders")
    private IOSElement srsummaryordrersbtn;
	
	@iOSXCUITFindBy(accessibility  = "Save")
    private IOSElement savebtn;*/

	@iOSXCUITFindBy(accessibility  = "Create Inspection")
	private IOSElement createinspectionmenu;
	
	public ServiceRequestsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, Duration.ofSeconds(10)), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("ServiceRequestsPageTableLeft")));
		wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("ServiceRequestsPageTableLeft")));
	}

	public void clickAddButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Add"))).click();
		BaseWizardScreen.typeContext =  SRCONTEXT;
	}
	
	public void clickSearchButton() {
		appiumdriver.findElementByAccessibilityId("Search").click();
	}
	
	public void clickNotCheckedInFilterItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Not Checked In")));
		appiumdriver.findElementByAccessibilityId("Not Checked In").click();
	}
	
	public void clickSaveFilter() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}
	
	public void applyNotCheckedInFilter() {
		clickSearchButton();
		clickNotCheckedInFilterItem();
		clickSaveFilter();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsPageTableLeft")));
	}
	
	public void resetFilter() {
		clickSearchButton();
		appiumdriver.findElementByAccessibilityId("Delete").click();
		clickSaveFilter();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsPageTableLeft")));
	}

	public void selectServiceRequest(String servicerequest) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(MobileBy.AccessibilityId("labelServiceRequestNumber"), 1));
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicerequest + "']").click();
	}
	
	public void selectCreateWorkOrderRequestAction() {
		appiumdriver.findElementByAccessibilityId("Create Work Order").click();
	}

	public boolean isCreateWorkOrderActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Create Work Order").size() > 0;
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
	
	public ServiceRequestsScreen selectCheckInMenu()  {
		appiumdriver.findElementByAccessibilityId("Check In").click();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsPageTableLeft")));
		return this;
	}
	
	public ServiceRequestsScreen selectUndoCheckMenu() {
		appiumdriver.findElementByAccessibilityId("Undo Check In").click();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("ServiceRequestsPageTableLeft")));
		return this;
	}

	public ServiceRequestsScreen rejectServiceRequest(String srNumber) {
		selectServiceRequest(srNumber);
		selectRejectAction();
		Helpers.acceptAlert();
		return this;
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
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Close")));
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(MobileBy.AccessibilityId("labelServiceRequestNumber"), 1));
		return appiumdriver.findElementsByAccessibilityId(srnumber).size() > 0;
	}
	
	public void selectEditServiceRequestAction() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
		BaseWizardScreen.typeContext = SRCONTEXT;
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("ServiceRequestsPageTableLeft")));
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
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Appointments")));
		List<WebElement> addds = appiumdriver.findElementsByAccessibilityId("Add");
		for(WebElement add : addds)
			if (add.isDisplayed()) {
				add.click();
				break;
			}

	}
	
	public void selectTodayFromAppointmet() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("From"))).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getFromAppointmetValue() {
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='From']/.."); 
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void selectTodayToAppointmet() {
		appiumdriver.findElementByAccessibilityId("To").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getToAppointmetValue() {
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='To']/.."); 
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void setSubjectAppointmet(String _subject)  {
		appiumdriver.findElementByAccessibilityId("Subject").click();
		appiumdriver.findElementByAccessibilityId("Subject").sendKeys(_subject+"\n");
	}
	
	public void setAddressAppointmet(String _address)  {
		appiumdriver.findElementByAccessibilityId("Address").click();
		appiumdriver.findElementByAccessibilityId("Address").sendKeys(_address+"\n");
	}
	
	public void setCityAppointmet(String _city)  {
		appiumdriver.findElementByAccessibilityId("City").click();
		appiumdriver.findElementByAccessibilityId("City").sendKeys(_city+"\n");
	}
	
	/*public void selectSummaryRequestAction() {
		appiumdriver.findElementByAccessibilityId("Summary").click();
	}*/
	
	public ServiceRequestdetailsScreen selectDetailsRequestAction() {
		appiumdriver.findElementByAccessibilityId("Details").click();
		return new ServiceRequestdetailsScreen();
	}

	public String getSummaryAppointmentsInformation() {
		return appiumdriver.findElementByXPath("//UIATableView[2]/UIATableCell[@name=\"Appointments\"]/UIAStaticText[2]").getAttribute("value");
	}
	
	public boolean isSRSummaryAppointmentsInformation() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("General Info")));
		return appiumdriver.findElementsByAccessibilityId("Appointments").size() > 0;
	}
	
	public ServiceRequestsScreen saveAppointment() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		return this;
	}
	
	//Close reason UIAPicker
	public void clickCancelCloseReasonDialog() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Reasons")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}
			
	public void clickDoneCloseReasonDialog() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Reasons")));
        clickDoneButton();
		wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("ServiceRequestsPageTableLeft")) );
	}

    public void clickDoneButton() {
        appiumdriver.findElement(MobileBy.AccessibilityId("Done")).click();
    }
			
	public void selectDoneReason(String selectreason) {
		selectUIAPickerValue(selectreason);
		clickDoneCloseReasonDialog();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Closing service request")));
		}
	}
			
	public ServiceRequestsScreen clickCloseSR() {
		appiumdriver.findElement(MobileBy.name("Close SR")).click();
		return this;
	}
	
	public boolean isUndoCheckInActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Undo Check In").size() > 0;
	}
	
	public void waitServiceRequestsScreenLoaded() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("ServiceRequestsPageTableLeft")));
		wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("ServiceRequestsPageTableLeft")));
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
	}

}
