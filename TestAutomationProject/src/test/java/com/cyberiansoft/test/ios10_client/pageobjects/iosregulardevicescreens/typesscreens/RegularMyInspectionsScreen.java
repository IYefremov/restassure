package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
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


public class RegularMyInspectionsScreen extends RegularBaseTypeScreenWithTabs {

	private final TypeScreenContext INSPECTIONCONTEXT = TypeScreenContext.INSPECTION;

	@iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addinspbtn;

	@iOSXCUITFindBy(accessibility = "Create\nWO")
	private IOSElement createwopopupmenu;
	
	/*
	
	@iOSXCUITFindBy(accessibility = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSXCUITFindBy(accessibility ="Send\nEmail")
    private IOSElement sendmailpopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Copy")
    private IOSElement copypopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Archive")
    private IOSElement archivepopupmenu;
	
	@iOSXCUITFindBy(accessibility ="Notes")
    private IOSElement notespopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Change\nCustomer")
    private IOSElement changecustomerpopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Show\nWOs")
    private IOSElement showwospopupmenu;
	

	
	@iOSXCUITFindBy(accessibility = "Status Reason")
    private IOSElement statusreasonbtn;
	
	@iOSXCUITFindBy(accessibility = "Summary")
    private IOSElement summarybtn;
	
	@iOSXCUITFindBy(accessibility = "Assign")
    private IOSElement assignbtn;
	
	@iOSXCUITFindBy(accessibility = "Done")
    private IOSElement popupdonebtn;
	
	@iOSXCUITFindBy(accessibility = "Close")
    private IOSElement closeflterpopupbtn;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement saveflterpopupbtn;
	
	@iOSXCUITFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;
	
	@iOSXCUITFindBy(accessibility = "Search")
    private IOSElement searchbtn;*/

	@iOSXCUITFindBy(accessibility = "InspectionsTable")
	private IOSElement inspectionsTable;
	
	public RegularMyInspectionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitMyInspectionsScreenLoaded() {
		FluentWait<WebDriver>  wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(MobileBy.AccessibilityId("InspectionsTable")));
	}

	public void clickAddInspectionButton() {
		appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(By.name("Add")).click();
		if (elementExists("Discard")) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		}
	}

	public void clickOnInspection(String inspectionNumber) {
		waitMyInspectionsScreenLoaded();
		inspectionsTable.findElement(MobileBy.AccessibilityId(inspectionNumber)).click();
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
	
	public void clickCreateWOButton() {
		createwopopupmenu.click();
	}

	public void clickArchiveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Archive").click();
		waitForAlert();
		acceptAlert();
	}

	public void archiveInspection(String inspectionNumber, String reason) {
		clickOnInspection(inspectionNumber);
		clickArchiveInspectionButton();
		selectReasonToArchive(reason);

	}

	public void selectReasonToArchive(String reason) {
		selectUIAPickerValue(reason);
		if (appiumdriver.findElements(MobileBy.name("Done")).size() > 1)
			((WebElement) appiumdriver.findElements(MobileBy.name("Done")).get(1)).click();
		else
			appiumdriver.findElement(MobileBy.name("Done")).click();
	}

	public <T extends IBaseWizardScreen> T selectInspectionType(IInspectionsTypes inspectionType) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("InspectionTypeSelector")));
		IOSElement insptypetable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'InspectionTypeSelector' and type = 'XCUIElementTypeTable'"));
		if (!insptypetable.findElementByAccessibilityId(inspectionType.getInspectionTypeName()).isDisplayed()) {
			swipeToElement(insptypetable.findElementByAccessibilityId(inspectionType.getInspectionTypeName()));
		}
		appiumdriver.findElement(MobileBy.AccessibilityId(inspectionType.getInspectionTypeName())).click();
		return inspectionType.getFirstVizardScreen();
	}
	
	public String getInspectionPriceValue(String inspectionnumber) {
		return inspectionsTable.findElement(MobileBy.
				AccessibilityId(inspectionnumber)).findElement(MobileBy.
						AccessibilityId("labelInspectionAmount")).getAttribute("label");
	}
	
	public String getInspectionApprovedPriceValue(String inspectionnumber) {
		return inspectionsTable.findElement(MobileBy.
				AccessibilityId(inspectionnumber)).findElement(MobileBy.
						AccessibilityId("labelInspectionApprovedAmount")).getAttribute("label");
	}

	public boolean checkInspectionDoesntExists(String inspection)  {
		return appiumdriver.findElementsByName(inspection).size() < 1;
	}

	public boolean checkInspectionExists(String inspection) {
		waitMyInspectionsScreenLoaded();
		return inspectionsTable.findElements(MobileBy.AccessibilityId(inspection)).size() > 0;
	}

	public void clickActionButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Share"))).click();
	}

	public void clickFilterButton() {
		appiumdriver.findElementByAccessibilityId("filter").click();
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
		appiumdriver.findElementByAccessibilityId("Status").click();
	}

	public boolean checkFilterStatusIsSelected(String filterstatus) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable[@name='StringSelector']/XCUIElementTypeCell[@name='"
								+ filterstatus + "_Checked" + "']").size() > 0;
	}

	public void clickFilterStatus(String filterstatus) {
		appiumdriver.findElementByAccessibilityId(filterstatus).click();
	}

	public void clickSaveFilterDialogButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
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

	public void selectInspectionForAction(String inspectionNumber) {
		waitMyInspectionsScreenLoaded();
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(inspectionNumber))).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}

	public boolean isInspectionIsApproved(String inspectionNumber) {
		return inspectionsTable.findElement(MobileBy.
				AccessibilityId(inspectionNumber)).findElement(MobileBy.className("XCUIElementTypeOther")).getAttribute("name").equals("EntityInfoButtonUnchecked");
	}
	
	public boolean isNotesIconPresentForInspection(String inspectionNumber) {
		waitMyInspectionsScreenLoaded();
		WaitUtils.waitUntilElementIsClickable(appiumdriver.findElementByAccessibilityId(inspectionNumber));
		return appiumdriver.findElementByAccessibilityId(inspectionNumber).findElements(MobileBy.AccessibilityId("ESTIMATION_NOTES")).size() > 0;
	}
	
	public boolean isDraftIconPresentForInspection(String inspectionNumber) {
		waitMyInspectionsScreenLoaded();
		return inspectionsTable.findElement(MobileBy.
				AccessibilityId(inspectionNumber)).findElements(MobileBy.AccessibilityId("ESTIMATION_DRAFT")).size() > 0;
	}
	
	public <T extends IBaseWizardScreen> T selectWorkOrderType(IWorkOrdersTypes workordertype) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("OrderTypeSelector")));
		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).isDisplayed()) {
			swipeToElement(wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()));
		}
		wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).click();
		return workordertype.getFirstVizardScreen();
	}

	public void clickBackButton()  {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();
	}
	
}
