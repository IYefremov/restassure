package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularServiceRequestsScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(name = "Add")
    private IOSElement addbtn;
	
	@iOSFindBy(name = "Refresh")
    private IOSElement refreshbtn;
	
	@iOSFindBy(name = "Create\\nInspection")
    private IOSElement createinspectionmenu;
	
	@iOSFindBy(name = "Edit")
    private IOSElement editmenu;
	
	@iOSFindBy(name = "Create\\nWork Order")
    private IOSElement createworkordermenu;
	
	@iOSFindBy(name = "Appointment")
    private IOSElement appointmentmenu;
	
	@iOSFindBy(name = "Close")
    private IOSElement closemenu;
	
	@iOSFindBy(name = "Reject")
    private IOSElement rejectmenu;
	
	@iOSFindBy(name = "Cancel")
    private IOSElement cancelmenu;
	
	//Appointment
	@iOSFindBy(name = "From")
    private IOSElement fromfld;
	
	@iOSFindBy(name = "To")
    private IOSElement tofld;
	
	@iOSFindBy(name = "Done")
    private IOSElement donebtn;
	
	@iOSFindBy(name = "Subject")
    private IOSElement subjectfld;
	
	@iOSFindBy(name = "Address")
    private IOSElement addressfld;
	
	@iOSFindBy(name = "City")
    private IOSElement cityfld;
	
	@iOSFindBy(name = "Summary")
    private IOSElement summarybtn;
	
	@iOSFindBy(name = "ServiceRequestSummaryInspectionsButton")
    private IOSElement srsummaryinspectionsbtn;
	
	@iOSFindBy(name = "Work Orders")
    private IOSElement srsummaryordrersbtn;
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	public RegularServiceRequestsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickRefreshButton() throws InterruptedException {
		refreshbtn.click();
		Thread.sleep(3000);
	}
	
	public void clickAddButton() {
		addbtn.click();
	}
	
	public void selectServiceRequestType (String srtype)
			throws InterruptedException {
		Helpers.scroolTo(srtype);
		Helpers.text_exact(srtype).click();
	}

	public void selectServiceRequest(String servicerequest) {
		Helpers.waitABit(3000);
		Helpers.text(servicerequest).click();
	}

	public void selectCreateInspectionRequestAction() {
		createinspectionmenu.click();
	}
	
	public void selectEditServiceRequestAction() {
		editmenu.click();
	}
	
	public void selectCreateWorkOrderRequestAction() {
		createworkordermenu.click();
	}
	
	public void selectAppointmentRequestAction() {
		appointmentmenu.click();
	}
	
	public void selectRejectAction() {
		rejectmenu.click();
	}
	
	public void selectCloseAction() {
		closemenu.click();
	}
	
	public boolean isRejectActionExists() {
		return Helpers.elementExists(MobileBy.name("Reject"));
	}
	
	public boolean isCloseActionExists() {
		return Helpers.elementExists(MobileBy.name("Close"));
	}
	
	public boolean isServiceRequestExists(String srnumber) {
		return Helpers.elementExists(MobileBy.name(srnumber));
	}
	
	public void selectCancelAction() {
		cancelmenu.click();
	}

	public void selectInspectionType(String inspectiontype) {
		//Helpers.scroolTo(inspectiontype);
		//Helpers.text_exact(inspectiontype).click();
		//Helpers.scroolTo(inspectiontype);
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspectiontype + "']")).click();
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ inspectiontype + "\"]").click();
	}
	
	public String getWorkOrderNumber() {
		return appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[3]").getText();
	}
	
	public String getInspectionNumber() {
		return appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[contains(@name,\"E-00\")]").getText();
	}
	
	public boolean isFirstServiceRequestOnHold() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[@name=\"On Hold\"]").isDisplayed();
	}
	
	public boolean isFirstServiceRequestContainsCorrectCompany(String companyname) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[@name=\""
						+ companyname + "\"]").isDisplayed();
	}
	
	public boolean isFirstServiceRequestContainsCorrectEmployee(String employee) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[contains(@name,\""
						+ employee + "\")]").isDisplayed();
	}
	
	public boolean isFirstServiceRequestContainsCorrectVIN(String VIN) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[contains(@name,\""
						+ VIN + "\")]").isDisplayed();
	}
	
	public String getFirstServiceRequestStatus() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[1]").getAttribute("name");
	}
	
	public String getServiceRequestStatus(String srnumber) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell['" + srnumber + "']/UIAStaticText[1]").getAttribute("name");
	}
	
	public String getFirstServiceRequestOnHoldNumber() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\"On Hold\"]/UIAStaticText[2]").getAttribute("name");
	}
	
	public String getFirstServiceRequestNumber() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[2]").getAttribute("name");
	}
	
	//Appointmets
	public void selectTodayFromAppointmet() {
		fromfld.click();
		donebtn.click();
	}
	
	public String getFromAppointmetValue() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\"From\"]/UIATextField[1]").getAttribute("value");
	}
	
	public void selectTodayToAppointmet() {
		tofld.click();
		donebtn.click();
	}
	
	public String getToAppointmetValue() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\"To\"]/UIATextField[1]").getAttribute("value");
	}
	
	public void setSubjectAppointmet(String _subject) throws InterruptedException {
		subjectfld.click();
		Helpers.keyboadrType(_subject+"\n");
	}
	
	public void setAddressAppointmet(String _address) throws InterruptedException {
		addressfld.click();
		Helpers.keyboadrType(_address+"\n");
	}
	
	public void setCityAppointmet(String _city) throws InterruptedException {
		cityfld.click();
		Helpers.keyboadrType(_city+"\n");
	}
	
	public void selectSummaryRequestAction() {
		summarybtn.click();
	}

	public String getSummaryAppointmentsInformation() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\"Appointments\"]/UIAStaticText[2]").getAttribute("value");
	}
	
	public boolean isSRSummaryAppointmentsInformation() {
		return appiumdriver.findElementByXPath("//UIATableView[@name=\"ServiceRequestSummaryTable\"]/UIATableCell[@name=\"Appointments\"]").isDisplayed();
	}
	
	public void clickServiceRequestSummaryInspectionsButton() {
		Helpers.scroolToByXpath("//UIATableView[@name=\"ServiceRequestSummaryTable\"]/UIATableCell[@name=\"Inspections\"]");
		srsummaryinspectionsbtn.click();
	}
	
	public void clickServiceRequestSummaryOrdersButton() {
		Helpers.scroolTo("Work Orders");
		srsummaryordrersbtn.click();
	}
	
	public void saveAppointment() {
		savebtn.click();
	}
	
	//Close reason UIAPicker
	public void clickCancelCloseReasonDialog() {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".toolbars()[1].buttons()['Cancel']")).click();
	}
	
	public void clickDoneCloseReasonDialog() {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".toolbars()[1].buttons()['Done']")).click();
	}
	
	public void selectDoneReason(String selectreason) throws InterruptedException {
		selectUIAPickerValue(selectreason);
		clickDoneCloseReasonDialog();
	}
	
	public void clickCloseSR() {
		appiumdriver.findElement(MobileBy.name("Close SR")).click();
		Helpers.waitABit(3000);
	}
	

}
