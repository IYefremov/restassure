package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServiceRequestdetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.InspectionTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.ServiceRequestTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
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
	
	public ServiceRequestsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, Duration.ofSeconds(10)), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("ServiceRequestsPageTableLeft")));
		wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("ServiceRequestsPageTableLeft")));
	}

	public void clickRefreshButton() {
		appiumdriver.findElementByAccessibilityId("Refresh").click();
	}
	
	public void clickAddButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Add")));
		wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Add"))).click();
		BaseWizardScreen.typeContext =  SRCONTEXT;
	}

	public <T extends IBaseWizardScreen> T addServiceRequestWithSelectCustomer(String customerName, ServiceRequestTypes serviceRequestType) {
		clickAddButton();
		CustomersScreen customersscreen = new CustomersScreen();
		customersscreen.selectCustomer(customerName);
		ServiceRequestTypesPopup serviceRequestTypesPopup = new ServiceRequestTypesPopup();
		serviceRequestTypesPopup.selectServiceRequestType(serviceRequestType.getServiceRequestTypeName());
		return serviceRequestType.getFirstVizardScreen();
	}

	public <T extends IBaseWizardScreen> T  addServiceRequest(ServiceRequestTypes serviceRequestType) {
		clickAddButton();
		ServiceRequestTypesPopup serviceRequestTypesPopup = new ServiceRequestTypesPopup();
		serviceRequestTypesPopup.selectServiceRequestType(serviceRequestType.getServiceRequestTypeName());
		return serviceRequestType.getFirstVizardScreen();
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
		//Helpers.text(servicerequest).click();
	}

	public void createInspectionFromServiceReques(String serviceRequestNumber, String inspType) {
		selectServiceRequest(serviceRequestNumber);
		appiumdriver.findElementByAccessibilityId("Create Inspection").click();
		InspectionTypesPopup inspectionTypesPopup = new InspectionTypesPopup();
		inspectionTypesPopup.selectInspectionType(inspType);
	}
	
	public void selectCreateWorkOrderRequestAction() {
		appiumdriver.findElementByAccessibilityId("Create Work Order").click();
	}

	public void createWorkOrderFromServiceRequest(String serviceRequestNumber, String workOrderType) {
		selectServiceRequest(serviceRequestNumber);
		selectCreateWorkOrderRequestAction();
		WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup();
		workOrderTypesPopup.selectWorkOrderType(workOrderType);
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
		/*FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 25);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Loading service requests")));
		if (appiumdriver.findElementsByAccessibilityId("Loading service requests").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 25);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading service requests")));
		}*/
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
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
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
		System.out.println("+++" +  appiumdriver.findElementsByAccessibilityId("Add").size());
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
		//appiumdriver.findElementByAccessibilityId("From").click();
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
			
	public void selectDoneReason(String selectreason) throws InterruptedException {
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
	}

}
