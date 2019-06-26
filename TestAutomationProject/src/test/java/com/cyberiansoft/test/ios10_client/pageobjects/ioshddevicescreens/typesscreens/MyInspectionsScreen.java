package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.EmailScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.NotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectEmployeePopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.InspectionTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyInspectionsScreen extends BaseTypeScreenWithTabs {

	private final TypeScreenContext INSPECTIONCONTEXT = TypeScreenContext.INSPECTION;
	
	final String firstinspxpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]";
	private By discardbtnxpath = By.name("Discard");
	
	/*@iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addinspbtn;
	
	@iOSXCUITFindBy(accessibility  = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Create Work Order")
    private IOSElement createwopopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Send Email")
    private IOSElement sendmailpopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Copy")
    private IOSElement copypopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Notes")
    private IOSElement notespopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Archive")
    private IOSElement archivepopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Assign")
    private IOSElement assignpopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Change Customer")
    private IOSElement changecustomerpopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Service Request")
    private IOSElement backservicerequestsbtn;
	
	@iOSXCUITFindBy(accessibility  = "Show Work Orders")
    private IOSElement showwospopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Status Reason")
    private IOSElement statusreasonbtn;*/
	
	/*@iOSXCUITFindBy(xpath = firstinspxpath)
    private IOSElement firstinspection;
	
	@iOSXCUITFindBy(xpath = firstinspxpath + "/XCUIElementTypeStaticText[1]")
    private IOSElement firstinspectionnumber;
	
	@iOSXCUITFindBy(xpath = firstinspxpath + "/XCUIElementTypeStaticText[3]")
    private IOSElement firstinspectionprice;
	
	@iOSXCUITFindBy(xpath = firstinspxpath + "/XCUIElementTypeStaticText[4]")
    private IOSElement firstinspectiontotalprice;*/
	
	/*@iOSXCUITFindBy(accessibility = "Close")
    private IOSElement closeflterpopupbtn;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement saveflterpopupbtn;
	
	@iOSXCUITFindBy(accessibility  = "Done")
    private IOSElement toolbardonebtn;*/
	
	public MyInspectionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("InspectionsPageTableLeft")));
	}

	public void clickAddInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Add").click();
		if (appiumdriver.findElementsByAccessibilityId("Discard").size() > 0) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		}
		BaseWizardScreen.typeContext = INSPECTIONCONTEXT;
	}

	public <T extends IBaseWizardScreen> T addInspection(IInspectionsTypes inspType) {
		clickAddInspectionButton();
		InspectionTypesPopup inspectionTypesPopup = new InspectionTypesPopup();
		inspectionTypesPopup.selectInspectionType(inspType);
		return inspType.getFirstVizardScreen();
	}

	public <T extends IBaseWizardScreen> T addOInspectionWithSelectCustomer(String customerName, IInspectionsTypes inspType) {
		clickAddInspectionButton();
		CustomersScreen customersscreen = new CustomersScreen();
		customersscreen.selectCustomer(customerName);
		InspectionTypesPopup inspectionTypesPopup = new InspectionTypesPopup();
		inspectionTypesPopup.selectInspectionType(inspType);
		return inspType.getFirstVizardScreen();
	}

	public void clickEditInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
		BaseWizardScreen.typeContext = INSPECTIONCONTEXT;
	}
	
	public void selectInspectionForEdit(String inspnumber)  {
		selectInspectionInTable(inspnumber);
		clickEditInspectionButton();
	}
	
	public <T extends IBaseWizardScreen> T createWOFromInspection(String inspNumber, WorkOrdersTypes workOrderType) {
		selectInspectionInTable(inspNumber);
		clickCreateWOButton();
		WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup();
		workOrderTypesPopup.selectWorkOrderType(workOrderType.getWorkOrderTypeName());
		return workOrderType.getFirstVizardScreen();
	}

	public void selectInspectionForApprove(String inspNumber) {
		selectInspectionInTable(inspNumber);
		clickApproveInspectionButton();
	}

	public MyInspectionsScreen approveInspectionWithSignature(String inspNumber) {
		selectInspectionForApprove(inspNumber);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.clickApproveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneButton();
		return this;
	}

	public MyInspectionsScreen approveInspectionAllServices(String inspNumber, String employee, String pwd) {
		selectInspectionForApprove(inspNumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.approveInspectionApproveAllAndSignature(inspNumber);
		return this;
	}
	
	public void selectInspectionToAssign(String inspnumber) {
		selectInspectionInTable(inspnumber);
		appiumdriver.findElementByAccessibilityId("Assign").click();
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectInspectionForCopy(String inspnumber) {
		selectInspectionInTable(inspnumber);
		clickCopyInspection();
	}
	
	protected void clickApproveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}

	public void clickCreateWOButton() {
		appiumdriver.findElementByAccessibilityId("Create Work Order").click();
		BaseWizardScreen.typeContext = INSPECTIONCONTEXT;
	}
	
	public EmailScreen clickSendEmail() {
		appiumdriver.findElementByAccessibilityId("Send Email").click();
		return new EmailScreen();
	}
	
	public void clickCopyInspection() {
		appiumdriver.findElementByAccessibilityId("Copy").click();
		if (appiumdriver.findElementsByAccessibilityId("Synchronizing with Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Synchronizing with Back Office")));
		}
	}

	public boolean isCreateWOButtonDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Create Work Order").isDisplayed();
	}

	public MyInspectionsScreen clickArchiveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Archive").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Yes")));
		appiumdriver.findElementByAccessibilityId("Yes").click();
		return this;
	}
	
	public void clickOnInspection(String inspnumber) {
		appiumdriver.findElementByAccessibilityId(inspnumber).click();
	}

	public void archiveInspection(String inpectionNumber, String reason) {
		selectInspectionInTable(inpectionNumber);
		clickArchiveInspectionButton();
		selectReasonToArchive(reason);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId(inpectionNumber), 1)  );
	}

	public void archiveInspections(ArrayList<String> inspections, String reason)  {
		clickActionButton();
		for (String inspNumber : inspections) {
			selectInspectionForAction(inspNumber);
		}
		clickArchiveInspections();
		selectReasonToArchive(reason);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId("Status Reason"), 1));
		new MyInspectionsScreen();
	}

	public void selectReasonToArchive(String reason) {

		selectUIAPickerValue(reason);
		appiumdriver.findElementByAccessibilityId("Status Reason").click();
		if (appiumdriver.findElementsByAccessibilityId("Done").size() > 1)
			((IOSElement) appiumdriver.findElementsByAccessibilityId("Done").get(1)).click();
		else
			appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public VehicleScreen selectDefaultInspectionType() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Default inspection type")));
		appiumdriver.findElementByAccessibilityId("Default inspection type").click();
		return new VehicleScreen();
	}

	public void selectInspectionInTable(String inspectionnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(inspectionnumber)));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspectionnumber))).click();
	}
	
	public void selectFirstInspection() {
		appiumdriver.findElementByXPath(firstinspxpath).click();
	}
	
	public String getInspectionApprovedPriceValue(String inspnumber) {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']")));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']").findElement(MobileBy.name("labelInspectionApprovedAmount")).getAttribute("value");
	}

	
	public String getInspectionTotalPriceValue(String inspnumber) {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']")));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']").findElement(MobileBy.name("labelInspectionAmount")).getAttribute("value");
	}

	public void clickActionButton() {
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
	}

	public boolean isInspectionExists(String inspection) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("InspectionsPageTableLeft")));
		return elementExists(inspection);
	}

	public void clickFilterButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("filter"))).click();
	}

	public boolean isFilterIsApplied() {
		return appiumdriver.findElementsByAccessibilityId("filter pressed").size() > 0;
	}

	public void clearFilter() {
		appiumdriver.findElementByAccessibilityId("filter pressed").click();
		//appiumdriver.findElementByAccessibilityId("Clear ").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeButton[@name='Clear']").click();
	}

	public void clickStatusFilter() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Status'] "))).click();
	}

	public boolean isFilterStatusSelected(String filterstatus) {
		return appiumdriver.findElements(
				By.xpath("//XCUIElementTypeTable[@name='StringSelector']/XCUIElementTypeCell[@name='" + filterstatus + "_Checked" + "']")).size() > 0;
	}

	public void clickFilterStatus(String filterstatus) {
		appiumdriver.findElementByAccessibilityId(filterstatus).click();
	}
	
	public void showWorkOrdersForInspection(String inpection) {
		selectInspectionInTable(inpection);
		clickShowWorkOrdersButton();
	}
	
	public void clickShowWorkOrdersButton() {
		appiumdriver.findElementByAccessibilityId("Show Work Orders").click();
	}

	public void clickCloseFilterDialogButton() {
		appiumdriver.findElementByAccessibilityId("Close").click();
	}

	public MyInspectionsScreen clickSaveFilterDialogButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		return this;
	}
	
	public void clickChangeCustomerpopupMenu() {
		appiumdriver.findElementByAccessibilityId("Change Customer").click();
	}
	
	public void selectCustomer(String customer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("CustomerSelectorTable")));
		MobileElement customersTable = (MobileElement) appiumdriver.findElementByAccessibilityId("CustomerSelectorTable");
		if (!customersTable.findElementByAccessibilityId(customer).isDisplayed()) {
			swipeTableUp(customersTable.findElementByXPath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + customer + "']/.."),
					customersTable);
			customersTable.findElementByAccessibilityId(customer).click();
		}
		customersTable.findElementByAccessibilityId(customer).click();
		
		//TouchAction tap = new TouchAction(appiumdriver).tap(appiumdriver.findElementByAccessibilityId(customer));              
        //tap.perform();
		//Helpers.waitABit(1000);
	}
	
	public void changeCustomerForInspection(String inspection, String customer) {
		selectInspectionInTable(inspection);
		clickChangeCustomerpopupMenu();
		selectCustomer(customer);
	}
	
	public void customersPopupSwitchToWholesailMode()  {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Customers")));
		if (elementExists(By.name("btnRetail"))) {
			appiumdriver.findElementByName("btnRetail").click();
		}
	}
	
	public void customersPopupSwitchToRetailMode()  {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Customers")));
		if (elementExists(By.name("btnWholesale"))) {
			appiumdriver.findElementByName("btnWholesale").click();
		}
	}
	
	public boolean isWorkOrderForInspectionExists(String wonuber) {
		boolean result = selectUIAPickerValue(wonuber);
		appiumdriver.findElementByAccessibilityId("StringPicker_Cancel").click();
		return result;
	}

	public void clickDoneButton() {
		if (elementExists("Actions"))
			appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		new MyInspectionsScreen();
	}

	public void clickApproveInspections() {
		clickActionButton();
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}
	
	public boolean isApproveInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Approve").size() > 0;
	}
	
	public boolean isCreateWOInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Create Work Order").size() > 0;
	}
	
	public boolean isCreateServiceRequestInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Create Service Request").size() > 0;
	}
	
	public boolean isCopyInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Copy").size() > 0;
	}
	
	public boolean isSendEmailInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Send Email").size() > 0;
	}

	public void clickArchiveInspections() {
		clickActionButton();
		clickArchiveInspectionButton();
	}

	public void selectInspectionForAction(String inspnumber) {
		appiumdriver.findElementByAccessibilityId(inspnumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}

	public boolean isInspectionApproved(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(inspnumber)));
		wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspnumber)));
		return appiumdriver.findElementByAccessibilityId(inspnumber).findElements(MobileBy.AccessibilityId("EntityInfoButtonUnchecked")).size() > 0;
		//Assert.assertTrue(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspnumber + "']/XCUIElementTypeOther")).getAttribute("name").equals("EntityInfoButtonUnchecked"));
	}
	
	public boolean isInspectionApproveButtonExists(String inspnumber) {
		
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.elementToBeClickable(By
		  //      .xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name=\"" + inspnumber + "\"]")));
		return appiumdriver.findElementByAccessibilityId(inspnumber).findElements(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).size() > 0;
		//return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name=\"" + inspnumber
		//		+ "\"]/XCUIElementTypeOther[contains(@name, \"EntityInfoButtonUnchecked\")] ").size() > 0;
	}
	
	public boolean isNotesIconPresentForInspection(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);

		IOSElement insptable  = (IOSElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("InspectionsPageTableLeft")));
		return insptable.findElementByAccessibilityId(inspnumber).findElements(MobileBy.AccessibilityId("ESTIMATION_NOTES"))
				.size() > 0;
	}
	
	public boolean isDraftIconPresentForInspection(String inspnumber) {
		return appiumdriver.findElementByAccessibilityId(inspnumber).findElements(MobileBy.AccessibilityId("ESTIMATION_DRAFT"))
				.size() > 0;
	}
	
	public boolean isWOIconPresentForInspection(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .AccessibilityId(inspnumber)));
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspnumber
				+ "']/XCUIElementTypeImage[@name='ESTIMATION_WO_CREATED']")).size() > 0;
	}
	
	public boolean isAssignButtonExists() {
		return appiumdriver.findElements(MobileBy.AccessibilityId("Assign")).size() > 0;
	}
	
	public NotesScreen openInspectionNotesScreen(String inspnumber) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name=\"" + inspnumber
				+ "\"]").click();
		appiumdriver.findElementByAccessibilityId("Notes").click();
		return new NotesScreen();
	}
	
	public String getInspectionPriceValue(String inspectionnumber) {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspectionnumber + "']/XCUIElementTypeStaticText[@name='labelInspectionAmount']")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}
	
	public String getInspectionTypeValue(String inspectionnumber) {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspectionnumber + "']/XCUIElementTypeStaticText[@name='labelInfo2']")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}
	
	public int getNumberOfRowsInTeamInspectionsTable() {		
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell")).size();
	}

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
        wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
    }
}
