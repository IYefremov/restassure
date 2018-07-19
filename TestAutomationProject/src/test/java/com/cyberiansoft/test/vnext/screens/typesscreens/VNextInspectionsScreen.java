package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextInspectionsScreen extends VNextBasicTypeScreen {

	@FindBy(xpath="//div[contains(@class, 'page inspections-list')]")
	private WebElement inspectionsscreen;
	
	@FindBy(xpath="//*[@data-autotests-id='inspections-list']")
	private WebElement inspectionslist;

	@FindBy(xpath="//*[@action='multiselect-actions-approve']")
	private WebElement multiselectinspapprovebtn;
	
	@FindBy(xpath="//*[@data-automation-id='search-icon']")
	private WebElement searchbtn;
	
	@FindBy(xpath="//*[@data-autotests-id='search-input']")
	private WebElement searchfld;
	
	@FindBy(xpath="//*[@data-autotests-id='search-cancel']")
	private WebElement cancelsearchbtn;

	@FindBy(xpath="//*[@data-automation-id='search-clear']")
	private WebElement clearsearchicon;
	
	final public static int MAX_NUMBER_OF_INPECTIONS = 50;
	
	public VNextInspectionsScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		BaseUtils.waitABit(2000);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='inspections-list']")));
		wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(inspectionsscreen));
		if (elementExists("//div[@class='intercom-chat-dismiss-button-mobile']"))
			tap(appiumdriver.findElementByXPath("//div[@class='intercom-chat-dismiss-button-mobile']"));
		if (cancelsearchbtn.isDisplayed()) {
			tap(clearsearchicon);
			clickCancelSearchButton();
		}

		if (searchbtn.findElement(By.xpath(".//span[contains(@class, 'icon-has-query')]")).isDisplayed()) {
			tap(searchbtn);
			if (searchfld.getAttribute("value").length() > 1) {
				tap(clearsearchicon);
				WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspections']"));
			}
			clickCancelSearchButton();
		}
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
		return getListCell(inspectionslist, inspectionnumber);
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
		wait.until(ExpectedConditions.elementToBeClickable(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspnumber + "']"))));
		tap(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspnumber + "']")));
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
		return inspectionslist.findElements(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspnumber + "']")).size() > 0;
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
		clickSearchButton();
		setSearchText(searchtext);
	}
	
	public void clickSearchButton() {
		tap(searchbtn);
	}
	
	public void setSearchText(String searchtext) {
		tap(searchfld);
		searchfld.clear();
		appiumdriver.getKeyboard().sendKeys(searchtext);
		appiumdriver.hideKeyboard();
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		((AndroidDriver<MobileElement>) appiumdriver).pressKeyCode(66);
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		clickCancelSearchButton();
	}
	
	public void clickCancelSearchButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='search-cancel']")));
		tap(cancelsearchbtn);
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspections']"));
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
}
