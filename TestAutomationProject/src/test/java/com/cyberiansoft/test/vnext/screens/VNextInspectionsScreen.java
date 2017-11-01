package com.cyberiansoft.test.vnext.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInspectionsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[contains(@class, 'page inspections-list')]")
	private WebElement inspectionsscreen;
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addinspectionbtn;
	
	@FindBy(xpath="//*[@data-autotests-id='inspections-list']")
	private WebElement inspectionslist;
	
	@FindBy(xpath="//*[@action='my']")
	private WebElement myinspectiontab;
	
	@FindBy(xpath="//*[@action='team']")
	private WebElement teaminspectiontab;
	
	@FindBy(xpath="//*[@data-automation-id='search-icon']")
	private WebElement searchbtn;
	
	@FindBy(xpath="//*[@data-automation-id='search-input']")
	private WebElement searchfld;
	
	@FindBy(xpath="//*[@data-automation-id='search-cancel']")
	private WebElement cancelsearchbtn;
	
	final public static int MAX_NUMBER_OF_INPECTIONS = 50;
	
	public VNextInspectionsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(inspectionslist));
		waitABit(1000);
	}
	
	public VNextCustomersScreen clickAddInspectionButton() {	
		tap(inspectionsscreen.findElement(By.xpath(".//a[@action='add']/i")));
		waitABit(1000);
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
		if (inspectionsscreen.findElements(By.xpath(".//a[@action='add']/i")).size() > 0)
			if (inspectionsscreen.findElement(By.xpath(".//a[@action='add']/i")).isDisplayed())
				tap(addinspectionbtn);
		} catch (NoSuchElementException e) {
			
		}
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log(LogStatus.INFO, "Tap Add inspection button");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public boolean isAddInspectionButtonVisible() {
		return inspectionsscreen.findElement(By.xpath(".//a[@action='add']/i")).isDisplayed();
	}
	
	public VNextHomeScreen clickBackButton() {
		clickScreenBackButton();
		log(LogStatus.INFO, "Tap Inspections Screen Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextInspectionsScreen createSimpleInspection() {	
		VNextCustomersScreen customersscreen = clickAddInspectionButton();
		customersscreen.selectCustomer("Retail Automation");
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN("TESTVINN");
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		return claiminfoscreen.saveInspectionViaMenu();
	}
	
	public String getFirstInspectionNumber() {
		return inspectionslist.findElement(By.xpath(".//div[contains(@class, 'entity-item-name')]")).getText();
	}
	
	public String getInspectionNumberValue(WebElement inspcell) {
		return inspcell.findElement(By.xpath(".//div[contains(@class, 'entity-item-name')]")).getText();
	}
	
	public String getInspectionCustomerValue(String inspectionnumber) {
		String inspcustomer = null;
		WebElement inspcell = getInspectionCell(inspectionnumber);
		if (inspcell != null)
			inspcustomer = inspcell.findElement(By.xpath(".//div[@action='select' and @class='entity-item-title']")).getText();
		else
			Assert.assertTrue(false, "Can't find inspection: " + inspectionnumber);
		return inspcustomer;
	}
	
	public String getInspectionCustomerValue(WebElement inspcell) {
		return inspcell.findElement(By.xpath(".//div[@action='select' and @class='entity-item-title']")).getText();
	}
	
	public String getInspectionStatusValue(String inspectionnumber) {
		String inspstatus = null;
		WebElement inspcell = getInspectionCell(inspectionnumber);
		if (inspcell != null)
			inspstatus = inspcell.findElement(By.xpath(".//div[contains(@class, 'entity-item-status')]")).getText();
		else
			Assert.assertTrue(false, "Can't find inspection: " + inspectionnumber);
		return inspstatus;
	}
	
	public String getFirstInspectionPrice() {
		return inspectionslist.findElement(By.xpath(".//div[@class='entity-item-currency']")).getText();
	}
	
	public String getInspectionPriceValue(String inspectionnumber) {
		String inspprice = null;
		WebElement inspcell = getInspectionCell(inspectionnumber);
		if (inspcell != null)
			inspprice = inspcell.findElement(By.xpath(".//div[@class='entity-item-currency']")).getText();
		else
			Assert.assertTrue(false, "Can't find inspection: " + inspectionnumber);
		return inspprice;	
	}
	
	public WebElement getInspectionCell(String inspectionnumber) {
		WebElement inspcell = null;
		List<WebElement> inspections = inspectionslist.findElements(By.xpath(".//div[@class='entity-item accordion-item']"));
		for (WebElement invcell : inspections) {
			if (invcell.findElement(By.xpath(".//div[contains(@class, 'entity-item-name')]")).getText().equals(inspectionnumber)) {
				inspcell = invcell;
				break;
			}
		}
		return inspcell;
	}
	
	public boolean isNotesIconPresentForInspection(String inspectionnumber) {
		WebElement inspcell = getInspectionCell(inspectionnumber);
		return inspcell.findElements(By.xpath(".//*[@data-autotests-id='estimation_notes']")).size() > 0;
	}
	
	
	public VNextInspectionsMenuScreen clickOnInspectionByInspNumber(String inspnumber) {
		tap(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'entity-item-name') and text()='" + inspnumber + "']")));
		log(LogStatus.INFO, "Tap on Inspection: " + inspnumber);
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
		return inspectionslist.findElements(By.xpath(".//div[contains(@class, 'entity-item-name') and text()='" + inspnumber + "']")).size() > 0;
	}
	
	public void switchToTeamInspectionsView() {
		tap(teaminspectiontab);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElement(By.xpath("//*[text()='Loading inspections']"))));
		log(LogStatus.INFO, "Switch to Team Inspections view");
	}
	
	public boolean isTeamInspectionsViewActive() {
		return teaminspectiontab.getAttribute("class").contains("active");
	}
	
	public void switchToMyInspectionsView() {
		tap(myinspectiontab);
		log(LogStatus.INFO, "Switch to My Inspections view");
	}
	
	public boolean isMyInspectionsViewActive() {
		return myinspectiontab.getAttribute("class").contains("active");
	}
	
	public void searchInpectionByFreeText(String searchtext) {
		clickSearchButton();
		setSearchText(searchtext);
		
	}
	
	public void clickSearchButton() {
		tap(searchbtn);
		log(LogStatus.INFO, "Click Search Button");
	}
	
	public void setSearchText(String searchtext) {
		tap(searchfld);
		searchfld.clear();
		appiumdriver.getKeyboard().sendKeys(searchtext);
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.pressKeyCode(66);
		switchToWebViewContext();
		log(LogStatus.INFO, "Set Search Text: " + searchtext);
	}
	
	public void clickCancelSearchButton() {
		tap(cancelsearchbtn);
		log(LogStatus.INFO, "Click Cancel Search Button");
	}
}
