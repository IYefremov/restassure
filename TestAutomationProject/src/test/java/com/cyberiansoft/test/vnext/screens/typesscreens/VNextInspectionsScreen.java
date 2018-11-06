package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextInspectionsScreen extends VNextBaseTypeScreen {

	@FindBy(xpath="//div[contains(@class, 'page inspections-list')]")
	private WebElement inspectionsscreen;
	
	@FindBy(xpath="//*[@data-autotests-id='inspections-list']")
	private WebElement inspectionslist;

	@FindBy(xpath="//*[@action='multiselect-actions-approve']")
	private WebElement multiselectinspapprovebtn;
	
	final public static int MAX_NUMBER_OF_INPECTIONS = 50;
	
	public VNextInspectionsScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		BaseUtils.waitABit(2000);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='inspections-list']")));
		wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(inspectionsscreen));
		if (elementExists("//div[@class='intercom-chat-dismiss-button-mobile']"))
			tap(appiumdriver.findElementByXPath("//div[@class='intercom-chat-dismiss-button-mobile']"));
        clearSearchField();
	}
	
	public VNextCustomersScreen clickAddInspectionButton() {
        clickAddButton();
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public boolean isAddInspectionButtonVisible() {
		return inspectionsscreen.findElement(By.xpath(".//*[@action='add']")).isDisplayed();
	}
	
	public VNextHomeScreen clickBackButton() {
		clickScreenBackButton();
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextInspectionsScreen createSimpleInspection() {	
		VNextCustomersScreen customersscreen = clickAddInspectionButton();
		customersscreen.selectCustomer(new RetailCustomer("Retail", "Automation"));
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN("TESTVINN");
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		return claiminfoscreen.saveInspectionViaMenu();
	}
	
	public String getFirstInspectionNumber() {
		return inspectionslist.findElement(By.xpath(".//div[@action='select']/div[contains(@class, 'checkbox-item-title')]")).getText();
	}
	
	public String getInspectionNumberValue(WebElement inspcell) {
		return inspcell.findElement(By.xpath(".//div[@action='select']/div[contains(@class, 'checkbox-item-title')]")).getText();
	}
	
	public String getInspectionCustomerValue(String inspectionnumber) {
		WebElement inspcell = getInspectionCell(inspectionnumber);
		return inspcell.findElement(By.xpath(".//div[@action='select' and @class='entity-item-title']")).getText();
	}
	
	public String getInspectionCustomerValue(WebElement inspcell) {
		return inspcell.findElement(By.xpath(".//div[@action='select' and @class='entity-item-title']")).getText();
	}
	
	public String getInspectionStatusValue(String inspectionnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(inspectionslist, By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionnumber + "']")));
		WebElement inspcell = getInspectionCell(inspectionnumber);
		return inspcell.findElement(By.xpath(".//div[@action='select']/div/*[contains(@class, 'entity-item-status-')]")).getText();
	}
	
	public VNextInspectionsMenuScreen clickOnFirstInspectionWithStatus(String inspStatus) {
		tap(inspectionslist.findElement(By.xpath(".//*[contains(@class, 'entity-item-status-') and text()='" + inspStatus + "']")));
		return new VNextInspectionsMenuScreen(appiumdriver);
	}
	
	public String getFirstInspectionPrice() {
		return inspectionslist.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
	}
	
	public String getInspectionPriceValue(String inspectionnumber) {
		WebElement inspcell = getInspectionCell(inspectionnumber);
		return inspcell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
	}
	
	public String getInspectionApprovedPriceValue(String inspectionnumber) {
		WebElement inspcell = getInspectionCell(inspectionnumber);
		return inspcell.findElement(By.xpath(".//div[@class='entity-item-approved-amount']")).getText();
	}
	
	public WebElement getInspectionCell(String inspectionnumber) {
		waitForInspectionsListIsVisibile();
		return getListCell(inspectionslist, inspectionnumber);
	}

	public void waitForInspectionsListIsVisibile() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.visibilityOf(inspectionslist));
	}
	
	public boolean isNotesIconPresentForInspection(String inspectionnumber) {
		WebElement inspcell = getInspectionCell(inspectionnumber);
		return inspcell.findElements(By.xpath(".//*[@data-autotests-id='estimation_notes']")).size() > 0;
	}
	
	public boolean isEmailSentIconPresentForInspection(String inspectionnumber) {
		WebElement inspcell = getInspectionCell(inspectionnumber);
		return inspcell.findElements(By.xpath(".//*[@data-autotests-id='estimation_email_sent']")).size() > 0;
	}
	
	public VNextInspectionsMenuScreen clickOnInspectionByInspNumber(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(inspectionslist, By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspnumber + "']")));
		wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspnumber + "']")))).click();
		//tap(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspnumber + "']")));
		return new VNextInspectionsMenuScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen clickOpenInspectionToEdit(String inspnumber) {
		VNextInspectionsMenuScreen inspmenulist = clickOnInspectionByInspNumber(inspnumber);
		return inspmenulist.clickEditInspectionMenuItem();
	}
	
	public VNextEmailScreen clickOnInspectionToEmail(String inspnumber) {
		VNextInspectionsMenuScreen inspmenulist = clickOnInspectionByInspNumber(inspnumber);
		return inspmenulist.clickEmailInspectionMenuItem();
	}
	
	public VNextNotesScreen openInspectionNotes(String inspnumber) {
		VNextInspectionsMenuScreen inspmenulist = clickOnInspectionByInspNumber(inspnumber);
		return inspmenulist.clickNotesInspectionMenuItem();
	}
	
	public List<WebElement> getInspectionsList() {
		return inspectionslist.findElements(By.xpath("./div[@class='entity-item accordion-item']"));
	}
	
	public int getNumberOfInspectionsOnTheScreen() {
		return getInspectionsList().size();
	}
	
	public VNextInspectionsScreen archiveInspection(String inspnumber) {
		VNextInspectionsMenuScreen inspmenulist = clickOnInspectionByInspNumber(inspnumber);
		return inspmenulist.archiveInspection();
	}
	
	public boolean isInspectionExists(String inspnumber) {
		if (elementExists("//div[@class='searchlist-nothing-found']"))
			return false;
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.visibilityOf(inspectionslist));
		wait = new WebDriverWait(appiumdriver, 30);
		//return !wait.until(ExpectedConditions.invisibilityOf(inspectionslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + inspnumber + "']"))));
		return inspectionslist.findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + inspnumber + "']")).size() > 0;
	}
	
	public void switchToTeamInspectionsView() {
        switchToTeamView();
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspections']"));
	}
	
	public boolean isTeamInspectionsViewActive() {
		return isTeamViewActive();
	}
	
	public void switchToMyInspectionsView() {
		switchToMyView();
	}
	
	public boolean isMyInspectionsViewActive() {
		return isMyViewActive();
	}
	
	public void searchInpectionByFreeText(String searchtext) {
        searchByFreeText(searchtext);
	}

	public void selectInspection(String inspectionNumber) {
		WebElement workordercell = getInspectionCell(inspectionNumber);
		if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
		    tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
	}

	public void unselectInspection(String inspectionNumber) {
		WebElement workordercell = getInspectionCell(inspectionNumber);
		if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") != null)
		    tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
	}

	public VNextApproveInspectionsScreen clickMultiselectInspectionsApproveButton() {
		tap(multiselectinspapprovebtn);
		return new VNextApproveInspectionsScreen(appiumdriver);
	}

	public VNextApproveInspectionsScreen clickMultiselectInspectionsApproveButtonAndSelectCustomer(AppCustomer customer) {
		tap(multiselectinspapprovebtn);
		VNextCustomersScreen customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(customer);
		return new VNextApproveInspectionsScreen(appiumdriver);
	}

	public VNextInspectionsScreen changeCustomerForInspection(String inspectionNumber, AppCustomer newCustomer) {
		VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
		VNextCustomersScreen customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.selectCustomer(newCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Inspection customer...']"));
		return this;
	}

	public VNextInspectionsScreen changeCustomerForWorkOrderViaSearch(String inspectionNumber, AppCustomer newCustomer) {
		VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
		VNextCustomersScreen customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.switchToRetailMode();
		customersscreen.searchCustomerByName(newCustomer.getFullName());
		customersscreen.selectCustomer(newCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Inspection customer...']"));
		return this;
	}

	public VNextInspectionsScreen changeCustomerToWholesailForInspection(String inspectionNumber, AppCustomer newWholesailCustomer) {
		VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
		VNextCustomersScreen customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(newWholesailCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		return this;
	}
}
