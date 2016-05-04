package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.VehicleScreen;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularMyInspectionsScreen extends iOSRegularBaseScreen {
	
	final String firstinspxpath = ".tableViews()[0].cells()[0]";

	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Add\"]")
    private IOSElement addinspbtn;
	
	@iOSFindBy(name = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(name = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSFindBy(xpath ="//UIAScrollView[2]/UIAButton[@name= \"Send\nEmail\"]")
    private IOSElement sendmailpopupmenu;
	
	@iOSFindBy(name = "Copy")
    private IOSElement copypopupmenu;
	
	@iOSFindBy(name = "Archive")
    private IOSElement archivepopupmenu;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAButton[@name=\"Change\nCustomer\"]")
    private IOSElement changecustomerpopupmenu;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAButton[7]")
    private IOSElement showwospopupmenu;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAButton[@name=\"Create\nWO\"]")
    private IOSElement createwopopupmenu;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Status Reason\"]")
    private IOSElement statusreasonbtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Done\"]")
    private IOSElement popupdonebtn;
	
	@iOSFindBy(uiAutomator = firstinspxpath)
    private IOSElement firstinspection;
	
	@iOSFindBy(uiAutomator = firstinspxpath + ".staticTexts()[0]")
    private IOSElement firstinspectionnumber;
	
	@iOSFindBy(uiAutomator = firstinspxpath + ".staticTexts()[6]")
    private IOSElement firstinspectionprice;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Close\"]")
    private IOSElement closeflterpopupbtn;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Save\"]")
    private IOSElement saveflterpopupbtn;
	
	@iOSFindBy(name = "Done")
    private IOSElement toolbardonebtn;
	
	public RegularMyInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickAddInspectionButton() throws InterruptedException {
		addinspbtn.click();
		if (isAlertExists()) {
			declineAlertByCoords();
			Thread.sleep(1000);
		}
	}

	public RegularVehicleScreen clickEditInspectionButton() {
		editpopupmenu.click();
		return new RegularVehicleScreen(appiumdriver);
	}
	
	public void selectInspectionForEdit(String inspnumber) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspnumber + "']")).click();
		clickEditInspectionButton();
	}

	public void selectInspectionForApprove(String inspnumber) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspnumber + "']")).click();
		clickApproveInspectionButton();
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByXPath("//UIASecureTextField[@value=\"Enter password here\"]")).setValue(password);
		Helpers.acceptAlert();
	}
	
	public void selectInspectionForCopy(String inspnumber) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspnumber + "']")).click();
		clickCopyInspection();
	}
	
	public void selectInspectionForCreatingWO(String inspnumber) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspnumber + "']")).click();
		clickCreateWOButton();
	}
	
	protected void clickApproveInspectionButton() {
		approvepopupmenu.click();
	}
	
	public void clickCreateWOButton() {
		createwopopupmenu.click();
	}
	
	public void clickSendEmail() {
		sendmailpopupmenu.click();
	}
	
	public void clickCopyInspection() {
		copypopupmenu.click();
	}

	public boolean isCreateWOButtonDisplayed() {
		return createwopopupmenu.isDisplayed();
	}

	public void clickArchive InspectionButton() {
		archivepopupmenu.click();
		waitForAlert();
		acceptAlert();
	}

	public void archive Inspection(String inspection, String reason)
			throws InterruptedException {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspection + "']")).click();
		clickArchive InspectionButton();
		selectReasonToArchive(reason);

	}

	public void selectReasonToArchive(String reason)
			throws InterruptedException {

		selectUIAPickerValue(reason);
		//statusreasonbtn.click();
		//Thread.sleep(1000);
		appiumdriver.findElement(MobileBy.IosUIAutomation(".toolbars()[1].buttons()['Done']")).click();
	}

	public RegularVehicleScreen selectDefaultInspectionType() {
		text("Default").click();
		return new RegularVehicleScreen(appiumdriver);
	}

	public void selectInspectionType (String inspectiontype) {
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspectiontype + "']")).click();
		Helpers.scroolTo(inspectiontype);
		Helpers.text_exact(inspectiontype).click();
	}

	public void selectFirstInspection() {
		firstinspection.click();
	}

	public String getFirstInspectionNumberValue() {		
		return firstinspectionnumber.getAttribute("label");
	}
	
	public String getFirstInspectionPriceValue() {
		return appiumdriver.findElement(By.xpath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[contains(@name, \"$\")]")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}

	public void assertInspectionDoesntExists(String inspection)  {
		Assert.assertTrue(appiumdriver.findElementsByName(inspection).size() < 1);
	}

	public void assertInspectionExists(String inspection) {
		Assert.assertTrue(appiumdriver.findElementsByName(inspection).size() > 0);
	}

	public RegularApproveInspectionsScreen selectFirstInspectionToApprove() {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAButton[contains(@name, \"EntityInfoButtonUnchecked\")] ").click();
		return new RegularApproveInspectionsScreen(appiumdriver);
	}
	
	public void selectInspectionToApprove(String inspection) {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[contains(@name,\""
						+ inspection
						+ "\")]/UIAButton[@name=\"approve disabled\"] ").click();
	}

	public void clickActionButton() {
		appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAButton[@visible=\"true\" and (contains(@name,\"Share\"))] ").click();
	}

	public void clickFilterButton() {
		appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAButton[@visible=\"true\" and (contains(@name,\"filter\"))] ").click();
	}

	public boolean assertFilterIsApplied() {
		return appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAButton[@visible=\"true\" and (contains(@name,\"filter\"))] ").getAttribute("name").equals("filter pressed");
	}

	public void clearFilter() {
		clickFilterButton();
		appiumdriver.findElementByXPath("//UIAButton[@visible=\"true\" and (contains(@name,\"Clear\"))] ").click();
	}

	public void clickStatusFilter() {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@visible=\"true\" and (contains(@name,\"Status\"))] ").click();
	}

	public void assertFilterStatusIsSelected(String filterstatus) {
		Assert.assertEquals(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
								+ filterstatus + "\"] ").getAttribute("value"),"1");
	}

	public void clickFilterStatus(String filterstatus) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ filterstatus + "\"] ").click();
	}
	
	public RegularVehicleScreen showWorkOrdersForInspection(String inspection) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspection + "']")).click();
		clickShowWorkOrdersButton();
		return new RegularVehicleScreen(appiumdriver);
		
	}
	
	public void clickShowWorkOrdersButton() {
		showwospopupmenu.click();
	}

	public void clickCloseFilterDialogButton() {
		closeflterpopupbtn.click();
	}

	public void clickSaveFilterDialogButton() {
		saveflterpopupbtn.click();
	}
	
	public void clickChangeCustomerpopupMenu() {
		changecustomerpopupmenu.click();
	}
	
	public void selectCustomer(String customer) {
		appiumdriver.findElementByName(customer).click();
	}
	
	public void changeCustomerForInspection(String inspection, String customer) throws InterruptedException {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspection + "']")).click();
		clickChangeCustomerpopupMenu();
		selectCustomer(customer);
	}
	
	public void customersPopupSwitchToWholesailMode() throws InterruptedException {
		if (elementExists(By.name("btnRetail"))) {
			appiumdriver.findElementByName("btnRetail").click();
		}
	}
	
	public void customersPopupSwitchToRetailMode() throws InterruptedException {
		if (elementExists(By.name("btnWholesale"))) {
			appiumdriver.findElementByName("btnWholesale").click();
		}
	}
	
	public int getNumberOfWorkOrdersForIspection() {
		return appiumdriver.findElementsByXPath("//UIAActionSheet[1]/UIACollectionView[1]/UIACollectionCell").size();
	}
	
	public boolean isWorkOrderForInspectionExists(String wonuber) {
		boolean result = Helpers.elementExists(By.xpath("//UIAActionSheet[1]/UIACollectionView[1]/UIACollectionCell[@name=\""
						+ wonuber + "\"]"));
		appiumdriver.findElementByXPath("//UIAActionSheet[1]/UIAButton[@name=\"Cancel\"]").click();
		return result;
	}
	
	public boolean selectUIAPickerWheelValue(MobileElement picker,
			MobileElement pickerwheel, String value) throws InterruptedException {
		int defaultwheelnumer = 10;
		int clicks = 0;
		boolean result = false;
		while (!(pickerwheel.getAttribute("name").contains(value))) {
			appiumdriver.tap(1, pickerwheel.getLocation().getX()
					+ picker.getSize().getWidth() - 50, pickerwheel
					.getLocation().getY() + picker.getSize().getHeight() + 10, 50);
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
		toolbardonebtn.click();
	}

	public void clickApproveInspections() {
		clickActionButton();
		approvepopupmenu.click();
	}

	public void clickArchiveInspections() {
		clickActionButton();
		clickArchive InspectionButton();
	}

	public void selectInspectionForAction(String inspection) {
		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + inspection
						+ "\"]/UIAButton[contains(@name, \"EntityInfoButtonUnchecked\")] ").click();
	}

	public void assertInspectionIsApproved(String inspection) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + inspection
				+ "\"]/UIAButton[contains(@name, \"EntityInfoButtonUnchecked\")] ").isDisplayed());
	}
}
