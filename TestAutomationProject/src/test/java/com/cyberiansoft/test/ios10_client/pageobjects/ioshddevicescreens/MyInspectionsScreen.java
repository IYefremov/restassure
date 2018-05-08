package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.InspectionTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.BaseTypeScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyInspectionsScreen extends BaseTypeScreen {
	
	final String firstinspxpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]";
	private By discardbtnxpath = By.name("Discard");
	
	/*@iOSFindBy(accessibility = "Add")
    private IOSElement addinspbtn;
	
	@iOSFindBy(accessibility  = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(accessibility  = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSFindBy(accessibility  = "Create Work Order")
    private IOSElement createwopopupmenu;
	
	@iOSFindBy(accessibility  = "Send Email")
    private IOSElement sendmailpopupmenu;
	
	@iOSFindBy(accessibility  = "Copy")
    private IOSElement copypopupmenu;
	
	@iOSFindBy(accessibility = "Notes")
    private IOSElement notespopupmenu;
	
	@iOSFindBy(accessibility  = "Archive")
    private IOSElement archivepopupmenu;
	
	@iOSFindBy(accessibility  = "Assign")
    private IOSElement assignpopupmenu;
	
	@iOSFindBy(accessibility  = "Change Customer")
    private IOSElement changecustomerpopupmenu;
	
	@iOSFindBy(accessibility  = "Service Request")
    private IOSElement backservicerequestsbtn;
	
	@iOSFindBy(accessibility  = "Show Work Orders")
    private IOSElement showwospopupmenu;
	
	@iOSFindBy(accessibility = "Status Reason")
    private IOSElement statusreasonbtn;*/
	
	/*@iOSFindBy(xpath = firstinspxpath)
    private IOSElement firstinspection;
	
	@iOSFindBy(xpath = firstinspxpath + "/XCUIElementTypeStaticText[1]")
    private IOSElement firstinspectionnumber;
	
	@iOSFindBy(xpath = firstinspxpath + "/XCUIElementTypeStaticText[3]")
    private IOSElement firstinspectionprice;
	
	@iOSFindBy(xpath = firstinspxpath + "/XCUIElementTypeStaticText[4]")
    private IOSElement firstinspectiontotalprice;*/
	
	/*@iOSFindBy(accessibility = "Close")
    private IOSElement closeflterpopupbtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement saveflterpopupbtn;
	
	@iOSFindBy(accessibility  = "Done")
    private IOSElement toolbardonebtn;*/
	
	public MyInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("InspectionsPageTableLeft")));
	}

	public void clickAddInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Add").click();
		if (appiumdriver.findElementsByAccessibilityId("Discard").size() > 0) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		}
	}

	public void addInspection(String inspType) {
		clickAddInspectionButton();
		InspectionTypesPopup inspectionTypesPopup = new InspectionTypesPopup(appiumdriver);
		inspectionTypesPopup.selectInspectionType(inspType);
	}

	public void addOInspectionWithSelectCustomer(String customerName, String inspType) {
		clickAddInspectionButton();
		CustomersScreen customersscreen = new CustomersScreen(appiumdriver);
		customersscreen.selectCustomer(customerName);
		InspectionTypesPopup inspectionTypesPopup = new InspectionTypesPopup(appiumdriver);
		inspectionTypesPopup.selectInspectionType(inspType);
	}

	public void clickEditInspectionButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId("Edit")).waitAction(Duration.ofSeconds(1)).release().perform();
		//editpopupmenu.click();
		//return new VehicleScreen(appiumdriver);
	}
	
	public void selectInspectionForEdit(String inspnumber)  {
		selectInspectionInTable(inspnumber);
		clickEditInspectionButton();
	}
	
	public void createWOFromInspection(String inspNumber, String workOrderType) {
		selectInspectionInTable(inspNumber);
		clickCreateWOButton();
		WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup(appiumdriver);
		workOrderTypesPopup.selectWorkOrderType(workOrderType);
	}

	public void selectInspectionForApprove(String inspnumber) {
		selectInspectionInTable(inspnumber);
		clickApproveInspectionButton();
	}
	
	public void selectInspectionToAssign(String inspnumber) {
		selectInspectionInTable(inspnumber);
		appiumdriver.findElementByAccessibilityId("Assign").click();
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectInspectionForCopy(String inspnumber) throws InterruptedException {
		selectInspectionInTable(inspnumber);
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
		return new EmailScreen(appiumdriver);
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

	public void clickArchiveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Archive").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Yes")));
		appiumdriver.findElementByAccessibilityId("Yes").click();
		//waitForAlert();
		//acceptAlert();
	}
	
	public void clickOnInspection(String inspnumber) {
		appiumdriver.findElementByAccessibilityId(inspnumber).click();
	}

	public void archiveInspection(String inpectionNumber, String reason)
			throws InterruptedException {
		selectInspectionInTable(inpectionNumber);
		clickArchiveInspectionButton();
		selectReasonToArchive(reason);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElementByAccessibilityId(inpectionNumber)));
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
		new MyInspectionsScreen(appiumdriver);
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
		return new VehicleScreen(appiumdriver);
	}

	public void selectInspectionInTable(String inspectionnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspectionnumber))).click();
	}
	
	public void selectFirstInspection() {
		appiumdriver.findElementByXPath(firstinspxpath).click();
	}

	public String getFirstInspectionNumberValue() {		
		return appiumdriver.findElementByXPath(firstinspxpath + "/XCUIElementTypeStaticText[1]").getAttribute("label");
	}
	
	public String getFirstInspectionPriceValue() {		
		return appiumdriver.findElementByXPath(firstinspxpath + "/XCUIElementTypeStaticText[3]").getAttribute("label");
	}
	
	public String getInspectionApprovedPriceValue(String inspnumber) {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']")));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']").findElement(MobileBy.name("labelInspectionApprovedAmount")).getAttribute("value");
	}
	
	public String getFirstInspectionTotalPriceValue() {		
		return appiumdriver.findElementByXPath(firstinspxpath + "/XCUIElementTypeStaticText[4]").getAttribute("label");
	}
	
	public String getInspectionTotalPriceValue(String inspnumber) {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']")));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']").findElement(MobileBy.name("labelInspectionAmount")).getAttribute("value");
	}

	public ApproveInspectionsScreen selectFirstInspectionToApprove() {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAButton[contains(@name, \"EntityInfoButtonUnchecked\")] ").click();
		return new ApproveInspectionsScreen(appiumdriver);
	}

	public void clickActionButton() {
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
		
		/*List<WebElement> btns = appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElements(MobileBy.className("XCUIElementTypeButton"));
		for (WebElement btn : btns) {
			if (btn.getAttribute("name").equals("Share")) {
				btn.click();
				break;
			}
		}*/
	}

	public boolean isInspectionExists(String inspection) {
		return elementExists(inspection);
	}

	public void clickFilterButton() {
		appiumdriver.findElementByAccessibilityId("filter").click();
	}

	public boolean isFilterIsApplied() {
		return appiumdriver.findElementsByAccessibilityId("filter pressed").size() > 0;
	}

	public void clearFilter() {
		appiumdriver.findElementByAccessibilityId("filter pressed").click();
		Helpers.waitABit(500);
		//appiumdriver.findElementByAccessibilityId("Clear ").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeButton[@name='Clear']").click();
	}

	public void clickStatusFilter() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
		        .xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Status'] "))).click();
		Helpers.waitABit(500);
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

	public void clickSaveFilterDialogButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
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
	
	public void changeCustomerForInspection(String inspection, String customer) throws InterruptedException {
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
		new MyInspectionsScreen(appiumdriver);
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
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(inspnumber)));
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
		return appiumdriver.findElementByAccessibilityId(inspnumber).findElements(MobileBy.AccessibilityId("ESTIMATION_NOTES"))
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
		return new NotesScreen(appiumdriver);
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

	public void switchToMyInspectionsView() {
		appiumdriver.findElementByAccessibilityId("My").click();		
	}
	
	public void switchToTeamInspectionsView() {
		appiumdriver.findElementByAccessibilityId("Team").click();		
	}

}
