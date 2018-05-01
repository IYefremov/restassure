package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.EmailScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
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
import java.util.concurrent.TimeUnit;

public class RegularMyInspectionsScreen extends iOSRegularBaseScreen {

	/*@iOSFindBy(accessibility = "Add")
    private IOSElement addinspbtn;
	
	@iOSFindBy(accessibility = "MyInspectionsTable")
	private IOSElement inspectiontable;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(accessibility = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSFindBy(accessibility ="Send\nEmail")
    private IOSElement sendmailpopupmenu;
	
	@iOSFindBy(accessibility = "Copy")
    private IOSElement copypopupmenu;
	
	@iOSFindBy(accessibility = "Archive")
    private IOSElement archivepopupmenu;
	
	@iOSFindBy(accessibility ="Notes")
    private IOSElement notespopupmenu;
	
	@iOSFindBy(accessibility = "Change\nCustomer")
    private IOSElement changecustomerpopupmenu;
	
	@iOSFindBy(accessibility = "Show\nWOs")
    private IOSElement showwospopupmenu;
	
	@iOSFindBy(accessibility = "Create\nWO")
    private IOSElement createwopopupmenu;
	
	@iOSFindBy(accessibility = "Status Reason")
    private IOSElement statusreasonbtn;
	
	@iOSFindBy(accessibility = "Summary")
    private IOSElement summarybtn;
	
	@iOSFindBy(accessibility = "Assign")
    private IOSElement assignbtn;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement popupdonebtn;
	
	@iOSFindBy(accessibility = "Close")
    private IOSElement closeflterpopupbtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement saveflterpopupbtn;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;
	
	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;*/
	
	public RegularMyInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("InspectionsTable")));
	}

	public void clickAddInspectionButton() throws InterruptedException {
		appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(By.name("Add")).click();
		if (elementExists("Discard")) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		}
	}

	public void clickEditInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
		//return new RegularVehicleScreen(appiumdriver);
	}
	
	public void clickOnInspection(String inspnumber) {
		appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.AccessibilityId(inspnumber)).click();
	}
	
	public void selectInspectionForEdit(String inspnumber) {
		clickOnInspection(inspnumber);
		clickEditInspectionButton();
	}

	public void selectInspectionForApprove(String inspnumber) {
		clickOnInspection(inspnumber);
		clickApproveInspectionButton();
	}
	
	public void openInspectionSummary(String inspnumber) {
		clickOnInspection(inspnumber);
		appiumdriver.findElementByAccessibilityId("Summary").click();
	}
	
	public void selectEmployee(String employee) {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(employee)));
		appiumdriver.findElementByAccessibilityId(employee).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByAccessibilityId("Enter password here")).setValue(password);
		Helpers.acceptAlert();
	}

	
	public void selectInspectionForCopy(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("InspectionsTable"))); 
		clickOnInspection(inspnumber);
		clickCopyInspection();
	}
	
	public void selectInspectionForCreatingWO(String inspnumber) {
		clickOnInspection(inspnumber);
		clickCreateWOButton();
	}
	
	protected void clickApproveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}
	
	public void clickCreateWOButton() {
		appiumdriver.findElementByAccessibilityId("Create\nWO").click();
	}
	
	public EmailScreen clickSendEmail() {
		appiumdriver.findElementByAccessibilityId("Send\nEmail").click();
		return new EmailScreen(appiumdriver);
	}
	
	public void clickCopyInspection() {
		appiumdriver.findElementByAccessibilityId("Copy").click();
	}

	public boolean isCreateWOButtonDisplayed() {
		return appiumdriver.findElementsByAccessibilityId("Create\nWO").size() > 0;
	}

	public void clickArchiveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Archive").click();
		waitForAlert();
		acceptAlert();
	}

	public void archiveInspection(String inspnumber, String reason)
			throws InterruptedException {
		clickOnInspection(inspnumber);
		clickArchiveInspectionButton();
		selectReasonToArchive(reason);

	}

	public void selectReasonToArchive(String reason)
			throws InterruptedException {

		selectUIAPickerValue(reason);
		//statusreasonbtn.click();
		if (appiumdriver.findElements(MobileBy.name("Done")).size() > 1)
			((WebElement) appiumdriver.findElements(MobileBy.name("Done")).get(1)).click();
		else
			appiumdriver.findElement(MobileBy.name("Done")).click();
	}

	public RegularVehicleScreen selectDefaultInspectionType() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Default")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Default")).click();
		return new RegularVehicleScreen(appiumdriver);
	}

	public void selectInspectionType(String inspectiontype) {
		if (!appiumdriver.findElementByAccessibilityId(inspectiontype).isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectiontype + "']/..")));
			appiumdriver.findElement(MobileBy.AccessibilityId(inspectiontype)).click();
			//appiumdriver.findElement(MobileBy.AccessibilityId("Inspection Types")).click();
		}
		if (elementExists(inspectiontype))
			appiumdriver.findElement(MobileBy.AccessibilityId(inspectiontype)).click();
		/*TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspectiontype + "']")).waitAction(1000).release().perform();
		*/
		//appiumdriver.findElement(MobileBy.AccessibilityId(inspectiontype)).click();
	}

	public void selectFirstInspection() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='InspectionsTable']/XCUIElementTypeCell[1]"))).click();;
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='MyInspectionsTable']/XCUIElementTypeCell[1]").click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='MyInspectionsTable']/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionNumber']").click();
		//firstinspection.click();
	}

	public String getFirstInspectionNumberValue() {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("InspectionsTable")));
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionNumber']")).getAttribute("label");
	}
	
	public String getFirstInspectionPriceValue() {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionAmount']")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}
	
	public String getInspectionPriceValue(String inspectionnumber) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspectionnumber)).findElement(MobileBy.
						AccessibilityId("labelInspectionAmount")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}
	
	public String getInspectionApprovedPriceValue(String inspectionnumber) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspectionnumber)).findElement(MobileBy.
						AccessibilityId("labelInspectionApprovedAmount")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}
	
	public String getInspectionTypeValue(String inspectionnumber) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspectionnumber)).findElement(MobileBy.
						AccessibilityId("labelInfo2")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}
	
	public String getFirstInspectionAprovedPriceValue() {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionApprovedAmount']")).getAttribute("label");	
	}

	public boolean checkInspectionDoesntExists(String inspection)  {
		return appiumdriver.findElementsByName(inspection).size() < 1;
	}

	public boolean checkInspectionExists(String inspection) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElements(MobileBy.AccessibilityId(inspection)).size() > 0;
	}

	public RegularApproveInspectionsScreen selectFirstInspectionToApprove() {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAButton[contains(@name, \"EntityInfoButtonUnchecked\")] ").click();
		return new RegularApproveInspectionsScreen(appiumdriver);
	}
	
	public void selectInspectionToApprove(String inspection) {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[contains(@name,\""
						+ inspection
						+ "\")]/UIAButton[@name=\"approve disabled\"]").click();
	}

	public void clickActionButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Share"))).click();
	}

	public void clickFilterButton() {
		appiumdriver.findElementByAccessibilityId("filter").click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeToolbar/XCUIElementTypeButton[contains(@name,'filter')]").click();
	}

	public boolean checkFilterIsApplied() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");		
		return toolbar.findElementByXPath("//XCUIElementTypeButton[contains(@name,'filter')]").getAttribute("name").equals("filter pressed");
	}

	public void clearFilter() {
		appiumdriver.findElementByAccessibilityId("filter pressed").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeButton[contains(@name,'Clear')]").click();
	}

	public void clickStatusFilter() {
		//WebElement par = getParentNode("Status");
		appiumdriver.findElementByAccessibilityId("Status").click();
		//appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@visible=\"true\" and (contains(@name,\"Status\"))]").click();
	}

	public boolean checkFilterStatusIsSelected(String filterstatus) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable[@name='StringSelector']/XCUIElementTypeCell[@name='"
								+ filterstatus + "_Checked" + "']").size() > 0;
	}

	public void clickFilterStatus(String filterstatus) {
		appiumdriver.findElementByAccessibilityId(filterstatus).click();
	}
	
	public void showWorkOrdersForInspection(String inspection) {
		appiumdriver.findElement(MobileBy.AccessibilityId(inspection)).click();
		clickShowWorkOrdersButton();
		//return new RegularVehicleScreen(appiumdriver);
		
	}
	
	public void clickShowWorkOrdersButton() {
		appiumdriver.findElementByAccessibilityId("Show\nWOs").click();
	}

	public void clickCloseFilterDialogButton() {
		appiumdriver.findElementByAccessibilityId("Close").click();
	}

	public void clickSaveFilterDialogButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}
	
	public void clickChangeCustomerpopupMenu() {
		appiumdriver.findElementByAccessibilityId("Change\nCustomer").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
	}
	
	public void selectCustomer(String customer) {
		appiumdriver.findElementByAccessibilityId(customer).click();
	}
	
	public void changeCustomerForInspection(String inspection, String customer) {
		appiumdriver.findElement(MobileBy.AccessibilityId(inspection)).click();
		clickChangeCustomerpopupMenu();
		selectCustomer(customer);
	}
	
	public void customersPopupSwitchToWholesailMode() {
		if (elementExists(MobileBy.AccessibilityId("btnRetail"))) {
			appiumdriver.findElementByAccessibilityId("btnRetail").click();
		}
	}
	
	public void customersPopupSwitchToRetailMode() {
		if (elementExists(MobileBy.AccessibilityId("btnWholesale"))) {
			appiumdriver.findElementByAccessibilityId("btnWholesale").click();
		}
	}
	
	public int getNumberOfWorkOrdersForIspection() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSheet")));
		IOSElement typesheet = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeSheet");
		return typesheet.findElementsByClassName("XCUIElementTypeButton").size()-1;
	}
	
	public boolean isWorkOrderForInspectionExists(String wonuber) {
		IOSElement typesheet = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeSheet");
		boolean result = typesheet.findElementsByXPath("//XCUIElementTypeButton[@name='" + wonuber + "']").size() > 0;
		typesheet.findElementByXPath("//XCUIElementTypeButton[@name='Cancel']").click();
		return result;
	}
	
	public boolean selectUIAPickerWheelValue(MobileElement picker,
			MobileElement pickerwheel, String value) throws InterruptedException {
		int defaultwheelnumer = 10;
		int clicks = 0;
		boolean result = false;
		while (!(pickerwheel.getAttribute("name").contains(value))) {
			TouchAction tap = new TouchAction(appiumdriver).tap(pickerwheel.getLocation().getX()
					+ picker.getSize().getWidth() - 50, pickerwheel
					.getLocation().getY() + picker.getSize().getHeight() + 10);              
	        tap.perform();
			/*appiumdriver.tap(1, pickerwheel.getLocation().getX()
					+ picker.getSize().getWidth() - 50, pickerwheel
					.getLocation().getY() + picker.getSize().getHeight() + 10, 50);*/
			Thread.sleep(1000);
			if (pickerwheel.getAttribute("name").contains(value)) {
				result = true;
			}
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				return false;
		}
		return result;
	}
	

	public void clickDoneButton() {
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public void clickApproveInspections() {
		clickActionButton();
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}

	public void clickArchiveInspections() {
		clickActionButton();
		clickArchiveInspectionButton();
	}

	public void selectInspectionForAction(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(inspnumber))).findElement(MobileBy.className("XCUIElementTypeOther")).click();
		//appiumdriver.findElementByAccessibilityId("MyInspectionsTable").findElement(MobileBy.
		//		AccessibilityId(inspnumber)).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}
	
	public void selectInspection(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		IOSElement inptable = (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.name(inspnumber))); 
		appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspnumber)).click();
	}

	public boolean isInspectionIsApproved(String inspnumber) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspnumber)).findElement(MobileBy.className("XCUIElementTypeOther")).getAttribute("name").equals("EntityInfoButtonUnchecked");
	}
	
	public boolean isInspectionIsApproveButtonExists(String inspnumber) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspnumber)).findElements(MobileBy.AccessibilityId("EntityInfoButtonUnchecked, ButtonImageId_76")).size() > 0;
	}
	
	public boolean isNotesIconPresentForInspection(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.name(inspnumber))).findElements(MobileBy.AccessibilityId("ESTIMATION_NOTES")).size() > 0; 
	}
	
	public boolean isDraftIconPresentForInspection(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspnumber)));
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspnumber)).findElements(MobileBy.AccessibilityId("ESTIMATION_DRAFT")).size() > 0;
	}
	
	public boolean isWOIconPresentForInspection(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspnumber)));
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspnumber)).findElements(MobileBy.AccessibilityId("ESTIMATION_WO_CREATED")).size() > 0;
	}
	
	public RegularNotesScreen openInspectionNotesScreen(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(inspnumber))).click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId("Notes")).waitAction(Duration.ofSeconds(1)).release().perform();
		//notespopupmenu.click();
		return new RegularNotesScreen(appiumdriver);
	}
	
	public void selectInspectionToAssign(String inspnumber) {
		clickOnInspection(inspnumber);
		appiumdriver.findElementByAccessibilityId("Assign").click();
	}
	
	public boolean isAssignButtonExists() {
		return appiumdriver.findElementsByAccessibilityId("Assign").size() > 0;
	}
	
	public boolean isApproveInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Approve").size() > 0;
	}
	
	public boolean isSendEmailInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Send\nEmail").size() > 0;
	}
	
	public boolean isCreateWOInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Create\nWO").size() > 0;
	}

	public boolean isCreateServiceRequestInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Create\nService Request").size() > 0;
	}
	
	public boolean isCopyInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Copy").size() > 0;
	}
	
	public int getNumberOfRowsInTeamInspectionsTable() {		
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[@name='TeamInspectionsTable']/XCUIElementTypeCell")).size();
	}
	
	public void selectWorkOrderType(String workordertype) {

		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype).isDisplayed()) {
			swipeToElement(wostable.findElementByAccessibilityId(workordertype));
			wostable.findElementByAccessibilityId(workordertype).click();
		}
		if (elementExists("OrderTypeSelector"))
			wostable.findElementByAccessibilityId(workordertype).click();
	}
	
	public void switchToLocalInspectionsView() {
		appiumdriver.findElementByAccessibilityId("My").click();
	}
	
	public void switchToOnlineInspectionsView() {
		appiumdriver.findElementByAccessibilityId("Team").click();
	}

	public void clickBackButton()  {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();
	}
	
}
