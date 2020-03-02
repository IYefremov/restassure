package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.EmailScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.NotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectEmployeePopup;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
public class MyInspectionsScreen extends BaseTypeScreenWithTabs {
	
	final String firstinspxpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]";
	private By discardbtnxpath = By.name("Discard");
	
	/*@iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addinspbtn;
	
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

	@iOSXCUITFindBy(accessibility  = "Edit")
	private IOSElement editpopupmenu;

	@iOSXCUITFindBy(accessibility = "InspectionsPageTableLeft")
	private IOSElement inspectionsTable;
	
	public MyInspectionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitInspectionsScreenLoaded() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("InspectionsPageTableLeft")));
	}

	public void clickAddInspectionButton() {

		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);

		wait.until(ExpectedConditions.elementToBeClickable(inspectionsTable));
		appiumdriver.findElementByAccessibilityId("Add").click();
		if (appiumdriver.findElementsByAccessibilityId("Discard").size() > 0) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		}
	}

	public void clickEditInspectionButton() {
		editpopupmenu.click();
	}
	
	public void selectInspectionForEdit(String inspectionNumber)  {
		selectInspectionInTable(inspectionNumber);
		clickEditInspectionButton();
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
	
	public void selectInspectionToAssign(String inspectionNumber) {
		selectInspectionInTable(inspectionNumber);
		appiumdriver.findElementByAccessibilityId("Assign").click();
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectInspectionForCopy(String inspectionNumber) {
		selectInspectionInTable(inspectionNumber);
		clickCopyInspection();
	}
	
	protected void clickApproveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}

	public void clickCreateWOButton() {
		appiumdriver.findElementByAccessibilityId("Create Work Order").click();
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

	public MyInspectionsScreen clickArchiveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Archive").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Yes")));
		appiumdriver.findElementByAccessibilityId("Yes").click();
		return this;
	}

	public void archiveInspection(String inpectionNumber, String reason) {
		selectInspectionInTable(inpectionNumber);
		clickArchiveInspectionButton();
		selectReasonToArchive(reason);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId(inpectionNumber), 1)  );
	}

	public void archiveInspections(List<String> inspections, String reason)  {
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

	public void selectInspectionInTable(String inspectionnumber) {
		waitInspectionsScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(inspectionsTable));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspectionnumber))).click();
	}

	public String getInspectionApprovedPriceValue(String inspectionNumber) {
		waitInspectionsScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .AccessibilityId(inspectionNumber)));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspectionNumber + "']").findElement(MobileBy.name("labelInspectionApprovedAmount")).getAttribute("value");
	}

	
	public String getInspectionTotalPriceValue(String inspectionNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspectionNumber + "']")));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspectionNumber + "']").findElement(MobileBy.name("labelInspectionAmount")).getAttribute("value");
	}

	public void clickActionButton() {
		waitInspectionsScreenLoaded();
		try {
			FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")));
		} catch (TimeoutException e) {
			appiumdriver.findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
		}
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
	}

	public boolean isInspectionExists(String inspection) {
		waitInspectionsScreenLoaded();
		return elementExists(inspection);
	}

	public void clickFilterButton() {
		waitInspectionsScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("filter"))).click();
	}

	public boolean isFilterIsApplied() {
		return appiumdriver.findElementsByAccessibilityId("filter pressed").size() > 0;
	}

	public void clearFilter() {
		appiumdriver.findElementByAccessibilityId("filter pressed").click();
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
	}
	
	public void changeCustomerForInspection(String inspection, String customer) {
		selectInspectionInTable(inspection);
		clickChangeCustomerpopupMenu();
		selectCustomer(customer);
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

	public void selectInspectionForAction(String inspectionNumber) {
		waitInspectionsScreenLoaded();
		inspectionsTable.findElementByAccessibilityId(inspectionNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}

	public boolean isInspectionApproved(String inspectionNumber) {
		waitInspectionsScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspectionNumber)));
		return appiumdriver.findElementByAccessibilityId(inspectionNumber).findElements(MobileBy.AccessibilityId("EntityInfoButtonUnchecked")).size() > 0;
 	}
	
	public boolean isNotesIconPresentForInspection(String inspectionNumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);

		IOSElement insptable  = (IOSElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("InspectionsPageTableLeft")));
		return insptable.findElementByAccessibilityId(inspectionNumber).findElements(MobileBy.AccessibilityId("ESTIMATION_NOTES"))
				.size() > 0;
	}
	
	public boolean isDraftIconPresentForInspection(String inspectionNumber) {
		waitInspectionsScreenLoaded();
		return appiumdriver.findElementByAccessibilityId(inspectionNumber).findElements(MobileBy.AccessibilityId("ESTIMATION_DRAFT"))
				.size() > 0;
	}
	
	public boolean isWOIconPresentForInspection(String inspectionNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .AccessibilityId(inspectionNumber)));
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspectionNumber
				+ "']/XCUIElementTypeImage[@name='ESTIMATION_WO_CREATED']")).size() > 0;
	}
	
	public boolean isAssignButtonExists() {
		return appiumdriver.findElements(MobileBy.AccessibilityId("Assign")).size() > 0;
	}
	
	public NotesScreen openInspectionNotesScreen(String inspectionNumber) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name=\"" + inspectionNumber
				+ "\"]").click();
		appiumdriver.findElementByAccessibilityId("Notes").click();
		return new NotesScreen();
	}
	
	public String getInspectionPriceValue(String inspectionnumber) {
		waitInspectionsScreenLoaded();
		return inspectionsTable.findElementByAccessibilityId(inspectionnumber).findElementByAccessibilityId( "labelInspectionAmount").getAttribute("label");
	}

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
        wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
    }
}
