package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOInspectionsWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//ul[@data-automation-id='inspectionsList']")
	private WebElement inspectionslist;
	
	@FindBy(xpath = "//div[@class='entity-details__content']")
	private WebElement inspectiondetailspanel;
	
	@FindBy(xpath = "//table[@data-automation-id='inspectionsDetailsServicesList']")
	private WebElement inspectionserviceslist;
	
	@FindBy(xpath = "//ul[@data-automation-id='inspectionsDetailsDamagesList']")
	private WebElement imagelegend;
	
	@FindBy(xpath = "//button[@data-automation-id='inspectionsDetailsPrintButton']")
	private WebElement printinspectionicon;
	
	@FindBy(xpath = "//button[@data-automation-id='inspectionsDetailsApproveButton']")
	private WebElement approveinspectionicon;
	
	@FindBy(id = "inspectiontypes-search")
	private WebElement searchinspectionspanel;
	
	@FindBy(id = "advSearchEstimation-freeText")
	private WebElement searchfld;
	
	@FindBy(xpath = "//*[@data-bind='text: filterInfoString']")
	private WebElement filterinfotext;
	
	@FindBy(id = "advSearchEstimation-search")
	private WebElement searchfilterbtn;
	
	@FindBy(xpath = "//i[contains(@data-bind, 'click: clear,')]")
	private WebElement clearfilterbtn;
	
	public VNextBOInspectionsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(inspectionslist));
	}
	
	public void selectInspectionInTheList(String inspnumber) {
		new WebDriverWait(driver, 15)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='entity-list__item__description']/div/b[text()='" + inspnumber + "']"))).click();
		//inspectionslist.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b[text()='" + inspnumber + "']")).click();
		waitABit(4000);
	}
	
	private WebElement getInspectionCell(String inspnumber) {
		WebElement inspcell = null;
		List<WebElement> inspcells = inspectionslist.findElements(By.xpath("./li[@role='option']"));
		for (WebElement cell : inspcells)
			if (cell.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b")).getText().trim().equals(inspnumber)) {
				inspcell = cell;
				break;
			}
		return inspcell;
	}
	
	public String getInspectionStatus(String inspnumber) {
		String inspstatus = "";
		WebElement inspcell = getInspectionCell(inspnumber);
		if (inspcell != null)
			inspstatus = inspcell.findElement(By.xpath(".//div[@class='entity-list__item__status__label']")).getText().trim(); 
		else
			Assert.assertTrue(false, "Can't find inpection: " + inspnumber);
		return inspstatus;
	}
		
	public boolean isServicePresentForSelectedInspection(String servicename) {
		return inspectionserviceslist.findElements(By.xpath("./tbody/tr/td[text()='" + servicename + "']")).size() > 0;
	}
	
	public boolean isServiceNotesIconDisplayed(String servicename) {
		WebElement sepviserow = inspectionserviceslist.findElement(By.xpath("./tbody/tr/td[text()='" + servicename + "']/.."));
		return sepviserow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).isDisplayed();
	}
	
	public boolean isMatrixServiceExists(String matrixservicename) {
		WebElement matrixsepviserow = inspectionserviceslist.findElement(By.xpath(".//tr[@class='entity-details__matrix']"));
		return matrixsepviserow.findElement(By.xpath("./td[contains(text(), '" +  matrixservicename + "')]")).isDisplayed();
	}

	public List<WebElement> getAllMatrixServicesRows(String matrixservicename) {
		return inspectionserviceslist.findElements(By.xpath(".//tr[@class='entity-details__matrix']"));
	}

	
	public boolean isImageExistsForMatrixServiceNotes(WebElement matrixsepviserow) {
		boolean exists = false;
		matrixsepviserow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).click();
		WebElement notesmodaldlg = new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("notesViewer"))));
		exists = notesmodaldlg.findElement(By.xpath("//div[@class='image-notes__preview--modal']")).isDisplayed();
		new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.elementToBeClickable(notesmodaldlg.findElement(By.xpath(".//button[@class='close']")))).click();
		waitABit(500);
		return exists;	
	}
	
	public void clickServiceNotesIcon(String servicename) {
		WebElement sepviserow = inspectionserviceslist.findElement(By.xpath("./tbody/tr/td[text()='" + servicename + "']/.."));
		sepviserow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).click();
	}
	
	public boolean isImageExistsForServiceNote(String servicename) {
		boolean exists = false;
		clickServiceNotesIcon(servicename);
		WebElement notesmodaldlg = new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("notesViewer"))));
		exists = notesmodaldlg.findElement(By.xpath("//div[@class='image-notes__preview--modal']")).isDisplayed();
		new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.elementToBeClickable(notesmodaldlg.findElement(By.xpath(".//button[@class='close']")))).click();
		waitABit(500);
		return exists;		
	}
	
	public void clickInspectionApproveButton() {
		wait.until(ExpectedConditions.visibilityOf(approveinspectionicon));
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(approveinspectionicon));
		approveinspectionicon.click();
	}
	
	public boolean isInspectionApproveButtonVisible() {
		return approveinspectionicon.isDisplayed();
	}
	
	public void approveInspection(String approveNotes) {
		String parent = driver.getWindowHandle();
		clickInspectionApproveButton();
		VNextConfirmationDialog confirmdialog = new VNextConfirmationDialog(driver);
		confirmdialog.clickYesButton();
		waitForNewTab();
		String newwin = "";
		for(String window:driver.getWindowHandles()){
			if(!window.equals(parent)){
				newwin = window;
			}
		}
		driver.switchTo().window(newwin);
		new WebDriverWait(driver, 60)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.name("txtAreaNotes2"))));
		driver.findElement(By.name("txtAreaNotes2")).sendKeys(approveNotes);
		driver.findElement(By.xpath("//div/button[@class='btn icon ok middle' and @value='4']")).click();
		waitABit(5000);
		driver.close();
		driver.switchTo().window(parent);
		driver.navigate().refresh();
	}
	
	public void declineInspection(String declineNotes) {
		String parent = driver.getWindowHandle();
		clickInspectionApproveButton();
		VNextConfirmationDialog confirmdialog = new VNextConfirmationDialog(driver);
		confirmdialog.clickYesButton();
		waitForNewTab();
		String newwin = "";
		for(String window:driver.getWindowHandles()){
			if(!window.equals(parent)){
				newwin = window;
			}
		}
		driver.switchTo().window(newwin);
		new WebDriverWait(driver, 60)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.name("txtAreaNotes2"))));
		List<WebElement> serviceschkboxes = driver.findElements(By.name("cbService"));
		for (WebElement serviceschkbox : serviceschkboxes)
			serviceschkbox.click();
		driver.findElement(By.xpath("//div/button[@class='btn icon cancel middle' and @value='2']")).click();
		driver.findElement(By.name("txtAreaNotes2")).sendKeys(declineNotes);
		driver.findElement(By.xpath("//button[@id='btnGeneralApprove']")).click();
		waitABit(5000);
		driver.close();
		driver.switchTo().window(parent);
		driver.navigate().refresh();
	}
	
	public boolean isImageLegendContainsBreakageIcon(String brackageicontype) {
		return imagelegend.findElements(By.xpath("./li[contains(text(), '" + brackageicontype + "')]")).size() > 0;
	}
	
	public String getSelectedInspectionCustomerName() {
		return inspectiondetailspanel.findElement(By.xpath(".//span[@data-bind='text:clientName']")).getText();
	}
	
	public String getSelectedInspectionTotalAmauntValue() {
		return inspectiondetailspanel.findElement(By.xpath(".//th[@data-bind='text: amount']")).getText();
	}
	
	public VNextBOInspectionInfoWebPage clickSelectedInspectionPrintIcon() {
		String mainWindowHandle = driver.getWindowHandle();
		printinspectionicon.click();
		waitForNewTab();
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(
			driver, VNextBOInspectionInfoWebPage.class);
	}
	
	public VNextBOAdvancedSearchInspectionDialog openAdvancedSearchPanel() {
		clickExpandAdvancedSearchPanel();
		if (driver.findElement(By.xpath("//div[@data-bind='click: showDefaultAdvancedSearch']")).isDisplayed())
			driver.findElement(By.xpath("//div[@data-bind='click: showDefaultAdvancedSearch']")).click();
		return PageFactory.initElements(
				driver, VNextBOAdvancedSearchInspectionDialog.class);
	}
	
	public void clickExpandAdvancedSearchPanel() {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("advSearchEstimation-caret")))).click();
	}
	
	public boolean isSavedAdvancedSearchFilterExists(String filterName) {
		boolean exists = false;
		if (driver.findElements(By.id("advSearchEstimation-savedSearchList")).size() > 0)
			exists = driver.findElement(By.id("advSearchEstimation-savedSearchList"))
			.findElements(By.xpath(".//div/span[text()='" + filterName + "']")).size() > 0;
		return exists;
	}
	
	public String getCustomSearchInfoTextValue() {
		return filterinfotext.getText();
	}
	
	public void deleteSavedAdvancedSearchFilter(String filterName) {		
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openSavedAdvancedSearchFilter(filterName);		
		advancedserchdialog.deleteSavedSearchFilter();
	}
	
	public VNextBOAdvancedSearchInspectionDialog openSavedAdvancedSearchFilter(String filterName) {
		waitABit(2000);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.id("advSearchEstimation-savedSearchList"))
			.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i"))).perform();
		
		driver.findElement(By.id("advSearchEstimation-savedSearchList"))
			.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i")).click();
		return PageFactory.initElements(
				driver, VNextBOAdvancedSearchInspectionDialog.class);
	}
	
	public void advancedSearchInspectionByCustomer(String customername) {
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openAdvancedSearchPanel();
		advancedserchdialog.selectAdvancedSearchByCustomer(customername);
		advancedserchdialog.clickSearchButton();
	}
	
	
	
	public void advancedSearchInspectionByStatusAndInspectionNumber(String inspNumber, String statussearch) {
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openAdvancedSearchPanel();
		advancedserchdialog.setAdvancedSearchInspectionByStatusAndInspectionNumber(inspNumber, statussearch);
		advancedserchdialog.clickSearchButton();
	}
	
	public void advancedSearchInspectionByStatus(String statussearch) {
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openAdvancedSearchPanel();
		advancedserchdialog.selectAdvancedSearchByStatus(statussearch);
		advancedserchdialog.clickSearchButton();
	}
	
	public void advancedSearchInspectionByStockNumber(String stocknumber) {
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openAdvancedSearchPanel();
		advancedserchdialog.setAdvancedSearchByStockNumber(stocknumber);
		advancedserchdialog.clickSearchButton();
	}
	
	public void advancedSearchInspectionByPONumber(String ponumber) {
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openAdvancedSearchPanel();
		advancedserchdialog.setAdvancedSearchByPONumber(ponumber);
		advancedserchdialog.clickSearchButton();
	}
	
	public void advancedSearchInspectionByRONumber(String ronumber) {
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openAdvancedSearchPanel();
		advancedserchdialog.setAdvancedSearchByRONumber(ronumber);
		advancedserchdialog.clickSearchButton();
	}
	
	public void advancedSearchInspectionByVIN(String VIN) {
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = openAdvancedSearchPanel();		
		advancedserchdialog.setAdvencedSearchVINValue(VIN);
		advancedserchdialog.clickSearchButton();
	}
	
	public void searchInspectionByText(String searchtext) {
		setSearchFieldValue(searchtext);
		clickSearchFilterButton();
	}
	
	public String getSearchFieldValue() {
		return searchfld.getAttribute("value");
	}
	
	public void setSearchFieldValue(String searchtext) {
		searchfld.clear();
		searchfld.sendKeys(searchtext);
	}
	
	public void clickSearchFilterButton() {
		searchfilterbtn.click();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clearfilterbtn));
	}
	
	public void clickClearFilterIcon() {
		clearfilterbtn.click();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.invisibilityOf(clearfilterbtn));
	}

}
