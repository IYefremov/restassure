package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
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

@Getter
public class VNextBOInspectionsWebPage extends VNextBOBaseWebPage {

	@FindBy(xpath = "//ul[@data-automation-id='inspectionsList']")
	private WebElement inspectionsList;

	@FindBy(xpath = "//div[@class='entity-details__content' and @data-bind='visible: isDetailsVisible']")
	private WebElement inspectionDetailsPanel;

	@FindBy(xpath = "//table[@data-automation-id='inspectionsDetailsServicesList']")
	private WebElement inspectionServicesList;

	@FindBy(xpath = "//ul[@data-automation-id='inspectionsDetailsDamagesList']")
	private WebElement imageLegend;

	@FindBy(xpath = "//span[@data-automation-id='inspectionsDetailsPrintButton']")
	private WebElement printInspectionIcon;

	@FindBy(xpath = "//button[@data-automation-id='inspectionsDetailsApproveButton']")
	private WebElement approveInspectionIcon;

	@FindBy(id = "inspectiontypes-search")
	private WebElement searchInspectionsPanel;

	@FindBy(id = "advSearchEstimation-freeText")
	private WebElement searchFld;

	@FindBy(xpath = "//*[@data-bind='text: filterInfoString']")
	private WebElement filterInfoText;

	@FindBy(id = "advSearchEstimation-search")
	private WebElement searchFilterBtn;

	@FindBy(xpath = "//i[contains(@data-bind, 'click: clear,')]")
	private WebElement clearFilterBtn;

	@FindBy(xpath = "//button[contains(text(), 'Approve and Complete')]")
	private WebElement approveAndCompleteButton;

	@FindBy(xpath = "//div[contains(@class, 'status__label')]")
	private List<WebElement> inspectionStatusLabels;

	@FindBy(xpath = "//a[@data-bind='click: showTermsAndConditions']")
	private WebElement termsAndConditionsLink;

	@FindBy(xpath = "//a[@data-bind='click: showPrivacyPolicy']")
	private WebElement privacyPolicyLink;

	@FindBy(xpath = "//iframe[@name='intercom-messenger-frame']")
	private WebElement intercomMessengerFrame;

	@FindBy(xpath = "//div[contains(@class,'intercom-messenger-new-conversation')]")
	private WebElement intercomNewConversionSpace;

	@FindBy(xpath = "//iframe[@name='intercom-launcher-frame']")
	private WebElement intercomLauncherFrame;

	@FindBy(xpath = "//div[contains(@class, 'intercom-launcher')]")
	private WebElement openCloseIntercomButton;

	@FindBy(xpath = "//i[@id='advSearchEstimation-caret']")
	private WebElement searchFieldAdvancedSearchCaret;

	@FindBy(xpath = "//div[@id='advSearchEstimation-savedSearchList']//i[@class='icon-gear']")
	private WebElement searchFieldAdvancedSearchIconGear;

	@FindBy(xpath = "//i[@data-automation-id='inspectionsDetailsEnlargeVisualForm']")
	private WebElement inspectionImageZoomIcon;

	@FindBy(xpath = "(//i[@class='icon-comment' and not(contains(@style, 'display: none'))])[1]")
	private WebElement inspectionNotesIcon;

	@FindBy(xpath = "//span[@data-automation-id='inspectionsDetailsPrintSupplementButton']")
	private WebElement printSupplementIcon;

	@FindBy(xpath = "//span[contains(@class, 'close-multi-archive-reasons')]//span[contains(@class, 'archive')]")
	private WebElement archiveIcon;

	@FindBy(xpath = "//span[@data-automation-id=\"inspectionsSingleUnArchiveButton\"]")
	private WebElement unArchiveIcon;

	public VNextBOInspectionsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public boolean isInspectionApproveButtonVisible() {	return Utils.isElementDisplayed(approveInspectionIcon);	}

	public boolean isInspectionsListDisplayed() {
		return Utils.isElementDisplayed(inspectionsList);
	}

	public boolean isInspectionDetailsPanelDisplayed() {
		return Utils.isElementDisplayed(inspectionDetailsPanel);
	}

	public boolean isSearchFieldDisplayed() {
		return Utils.isElementDisplayed(searchFld);
	}

	public boolean isTermsAndConditionsLinkDisplayed() {
		return Utils.isElementDisplayed(termsAndConditionsLink);
	}

	public boolean isPrivacyPolicyLinkDisplayed() {
		return Utils.isElementDisplayed(privacyPolicyLink);
	}

	public boolean isInspectionImageZoomIconDisplayed() {return Utils.isElementDisplayed(inspectionImageZoomIcon); }

	public boolean isInspectionNotesIconDisplayed() {return Utils.isElementDisplayed(inspectionNotesIcon); }

	public boolean isPrintSupplementButtonDisplayed() {return Utils.isElementDisplayed(printSupplementIcon); }

	public boolean isPrintInspectionButtonDisplayed() {return Utils.isElementDisplayed(printInspectionIcon); }

	public boolean isArchiveIconDisplayed() {return Utils.isElementDisplayed(archiveIcon); }

	public boolean isUnArchiveIconDisplayed() {return Utils.isElementDisplayed(unArchiveIcon); }

	public void clickInspectionImageZoomIcon() {Utils.clickElement(inspectionImageZoomIcon); }

	public void clickInspectionNotesIcon() {Utils.clickElement(inspectionNotesIcon); }

	public void clickPrintSupplementButton() {Utils.clickElement(printSupplementIcon); }

	public void clickPrintInspectionButton() {Utils.clickElement(printInspectionIcon); }

	public void clickArchiveIcon() {Utils.clickElement(archiveIcon); }

	public void clickUnArchiveIcon() {Utils.clickElement(unArchiveIcon); }

	public boolean isIntercomButtonDisplayed() {
		driver.switchTo().frame(intercomLauncherFrame);
		boolean isIntercomButtonDisplayed = isElementDisplayed(openCloseIntercomButton);
		driver.switchTo().defaultContent();
		return isIntercomButtonDisplayed;
	}

	public void clickTermsAndConditionsLink() {
		Utils.clickElement(termsAndConditionsLink);
	}

	public void clickPrivacyPolicyLink() {
		Utils.clickElement(privacyPolicyLink);
	}

	public void openIntercomMessenger() {
		driver.switchTo().frame(intercomLauncherFrame);
		Utils.clickElement(openCloseIntercomButton);
		driver.switchTo().defaultContent();
	}

	public boolean isIntercomMessengerOpened() {
		driver.switchTo().frame(intercomMessengerFrame);
		boolean isMessengerOpened =  isElementDisplayed(intercomNewConversionSpace);
		driver.switchTo().defaultContent();
		return isMessengerOpened;
	}

	public void closeIntercom() {
		driver.switchTo().frame(intercomLauncherFrame);
		Utils.clickElement(openCloseIntercomButton);
		driver.switchTo().defaultContent();
	}

	public void openAdvancedSearchForm()
	{
		Utils.clickElement(searchFieldAdvancedSearchCaret);
		Utils.clickElement(searchFieldAdvancedSearchIconGear);
	}

	public boolean isPrintWindowOpened()
	{
		String parentHandle = Utils.getParentTab();
		waitForNewTab();
		String newWindow = Utils.getNewTab(parentHandle);
		driver.switchTo().window(parentHandle);
		if (parentHandle.equals(newWindow)) return false;
		return true;
	}

	public void selectArchiveReason(String reason)
	{
		Utils.clickWithJS(
				driver.findElement(By.xpath("//div[@class='archive-reasons-title' and text()='" + reason + "']")));
	}

	public void selectInspectionInTheList(String inspectionNumber) {
		Utils.clickElement(driver.findElement(By.xpath("//div[@class='entity-list__item__description']/div/b[text()='" + inspectionNumber + "']")));
		waitABit(4000);
	}

	private WebElement getInspectionCell(String inspectionNumber) {
		WebElement inspectionCell = null;
		List<WebElement> inspectionCells = inspectionsList.findElements(By.xpath("./li[@role='option']"));
		for (WebElement cell : inspectionCells)
			if (cell.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b")).getText().trim().equals(inspectionNumber)) {
				inspectionCell = cell;
				break;
			}
		return inspectionCell;
	}

	public String getInspectionStatus(String inspectionNumber) {
		String inspectionStatus = "";
		WebElement inspectionCell = getInspectionCell(inspectionNumber);
		if (inspectionCell != null)
			inspectionStatus = inspectionCell.findElement(By.xpath(".//div[@class='entity-list__item__status__label']")).getText().trim();
		else
			Assert.assertTrue(false, "Can't find inspection: " + inspectionNumber);
		return inspectionStatus;
	}

	public boolean isServicePresentForSelectedInspection(String serviceName) {
		return inspectionServicesList.findElements(By.xpath("./tbody/tr/td[text()='" + serviceName + "']")).size() > 0;
	}

	public boolean isServiceNotesIconDisplayed(String serviceName) {
		WebElement serviceRow = inspectionServicesList.findElement(By.xpath("./tbody/tr/td[text()='" + serviceName + "']/.."));
		return serviceRow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).isDisplayed();
	}

	public boolean isMatrixServiceExists(String matrixServiceName) {
		WebElement matrixServiceRow = inspectionServicesList.findElement(By.xpath(".//tr[@class='entity-details__matrix']"));
		return matrixServiceRow.findElement(By.xpath("./td[contains(text(), '" +  matrixServiceName + "')]")).isDisplayed();
	}

	public List<WebElement> getAllMatrixServicesRows() {
		return inspectionServicesList.findElements(By.xpath(".//tr[@class='entity-details__matrix']"));
	}

	public boolean isImageExistsForMatrixServiceNotes(WebElement matrixServiceRow) {
		boolean exists = false;
		matrixServiceRow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).click();
		WebElement notesModalDialog = new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("notesViewer"))));
		exists = notesModalDialog.findElement(By.xpath("//div[@class='image-notes__preview--modal']")).isDisplayed();
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(notesModalDialog.findElement(By.xpath(".//button[@class='close']")))).click();
		waitABit(500);
		return exists;
	}

	public void clickServiceNotesIcon(String serviceName) {
		WebElement serviceRow = inspectionServicesList.findElement(By.xpath("./tbody/tr/td[text()='" + serviceName + "']/.."));
		serviceRow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).click();
	}

	public boolean isImageExistsForServiceNote(String serviceName) {
		boolean exists;
		clickServiceNotesIcon(serviceName);
		WebElement notesModalDialog = new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("notesViewer"))));
		exists = notesModalDialog.findElement(By.xpath("//div[@class='image-notes__preview--modal']")).isDisplayed();
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(notesModalDialog.findElement(By.xpath(".//button[@class='close']")))).click();
		waitABit(500);
		return exists;
	}

	public void clickInspectionApproveButton() {
		Utils.clickElement(approveInspectionIcon);
	}



	public void approveInspection(String approveNotes) {
		String parentHandle = driver.getWindowHandle();
		clickInspectionApproveButton();
		VNextBOConfirmationDialog confirmDialog = new VNextBOConfirmationDialog();
		confirmDialog.clickYesButton();
		waitForNewTab();
		String newWindow = Utils.getNewTab(parentHandle);
		driver.switchTo().window(newWindow);
		driver.findElement(By.xpath("//p/button[@type='submit' and @class='btn icon ok']")).click();
		waitLong.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("txtAreaNotes"))));
		driver.findElement(By.name("txtAreaNotes")).sendKeys(approveNotes);
		driver.findElement(By.xpath("//button[@id='btnApprove']")).click();
		waitABit(5000);
		driver.close();
		driver.switchTo().window(parentHandle);
		driver.navigate().refresh();
	}

	public void openApproveInspectionWindow() {
		String parentHandle = driver.getWindowHandle();
		clickInspectionApproveButton();
		new VNextBOConfirmationDialog().clickYesButton();
		waitForNewTab();
		String newWindow = Utils.getNewTab(parentHandle);
		driver.switchTo().window(newWindow);
		waitForLoading();
	}

	public void declineInspection(String declineNotes) {
		String parentHandle = driver.getWindowHandle();
		clickInspectionApproveButton();
		VNextBOConfirmationDialog confirmDialog = new VNextBOConfirmationDialog();
		confirmDialog.clickYesButton();
		waitForNewTab();
		String newWindow = Utils.getNewTab(parentHandle);
		driver.switchTo().window(newWindow);
		driver.findElement(By.xpath("//p/button[@type='submit' and @class='btn icon cancel']")).click();
		new WebDriverWait(driver, 60)
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("txtDeclineNotes"))));
		driver.findElement(By.name("txtDeclineNotes")).sendKeys(declineNotes);
		driver.findElement(By.xpath("//button[@class='btn icon cancel' and @id='btnDecline']")).click();
		waitABit(5000);
		driver.close();
		driver.switchTo().window(parentHandle);
		driver.navigate().refresh();
	}

	public boolean isImageLegendContainsBreakageIcon(String brackageicontype) {
		return imageLegend.findElements(By.xpath("./li[contains(text(), '" + brackageicontype + "')]")).size() > 0;
	}

	public String getSelectedInspectionCustomerName() {
		return Utils.getText(inspectionDetailsPanel.findElement(By.xpath(".//span[@data-bind='text:clientName']")));
	}

	public String getSelectedInspectionTotalAmountValue() {
		return Utils.getText(inspectionDetailsPanel.findElement(By.xpath(".//th[@data-bind='text: amount']")));
	}

	public VNextBOInspectionInfoWebPage clickSelectedInspectionPrintIcon() {
		String mainWindowHandle = driver.getWindowHandle();
		printInspectionIcon.click();
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

	public void clickExpandAdvancedSearchPanel() {
		Utils.clickElement(driver.findElement(By.id("advSearchEstimation-caret")));
	}

	public boolean isSavedAdvancedSearchFilterExists(String filterName) {
		boolean exists = false;
		if (driver.findElements(By.id("advSearchEstimation-savedSearchList")).size() > 0)
			exists = driver.findElement(By.id("advSearchEstimation-savedSearchList"))
					.findElements(By.xpath(".//div/span[text()='" + filterName + "']")).size() > 0;
		return exists;
	}

	public String getCustomSearchInfoTextValue() {
		return filterInfoText.getText();
	}

	public void deleteSavedAdvancedSearchFilter(String filterName) {
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = openSavedAdvancedSearchFilter(filterName);
		advancedSearchDialog.deleteSavedSearchFilter();
	}

	public VNextBOInspectionAdvancedSearchForm openSavedAdvancedSearchFilter(String filterName) {
		waitABit(2000);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.id("advSearchEstimation-savedSearchList"))
				.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i"))).perform();

		driver.findElement(By.id("advSearchEstimation-savedSearchList"))
				.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i")).click();
		return PageFactory.initElements(
				driver, VNextBOInspectionAdvancedSearchForm.class);
	}

	public void advancedSearchInspectionByCustomer(String customerName) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = new VNextBOInspectionAdvancedSearchForm(driver);
		advancedSearchDialog.setAdvSearchAutocompleteField("Customer", customerName);
		advancedSearchDialog.clickSearchButton();
	}


	public void advancedSearchInspectionByStatusAndInspectionNumber(String inspectionNumber, String statusSearch) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = new VNextBOInspectionAdvancedSearchForm(driver);
		advancedSearchDialog.setAdvSearchTextField("Inspection#",inspectionNumber);
		advancedSearchDialog.setAdvSearchDropDownField("Status", statusSearch);
		advancedSearchDialog.clickSearchButton();
	}

	public void advancedSearchInspectionByStatus(String statusSearch) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = new VNextBOInspectionAdvancedSearchForm(driver);
		advancedSearchDialog.setAdvSearchDropDownField("Status", statusSearch);
		advancedSearchDialog.clickSearchButton();
	}

	public void advancedSearchInspectionByStockNumber(String stockNumber) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = new VNextBOInspectionAdvancedSearchForm(driver);
		advancedSearchDialog.setAdvSearchTextField("Stock#", stockNumber);
		advancedSearchDialog.clickSearchButton();
	}

	public void advancedSearchInspectionByPONumber(String poNumber) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = new VNextBOInspectionAdvancedSearchForm(driver);
		advancedSearchDialog.setAdvSearchTextField("PO#", poNumber);
		advancedSearchDialog.clickSearchButton();
	}

	public void advancedSearchInspectionByRONumber(String roNumber) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = new VNextBOInspectionAdvancedSearchForm(driver);
		advancedSearchDialog.setAdvSearchTextField("RO#", roNumber);
		advancedSearchDialog.clickSearchButton();
	}

	public void advancedSearchInspectionByVIN(String VIN) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm advancedSearchDialog = new VNextBOInspectionAdvancedSearchForm(driver);
		advancedSearchDialog.setAdvSearchTextField("VIN", VIN);
		advancedSearchDialog.clickSearchButton();
	}

	public void searchInspectionByText(String searchText) {
		setSearchFieldValue(searchText);
		clickSearchFilterButton();
	}

	public void findInspectionByCustomTimeFrameAndNumber(String inspectionId, String fromDate, String toDate) {
		openAdvancedSearchForm();
		VNextBOInspectionAdvancedSearchForm vNextBOInspectionAdvancedSearchForm =
				new VNextBOInspectionAdvancedSearchForm(driver);
		vNextBOInspectionAdvancedSearchForm.setAdvSearchTextField("Inspection#", inspectionId);
		vNextBOInspectionAdvancedSearchForm.setAdvSearchDropDownField("Timeframe", "Custom");
		vNextBOInspectionAdvancedSearchForm.setAdvSearchTextField("From", fromDate);
		vNextBOInspectionAdvancedSearchForm.setAdvSearchTextField("To", toDate);
		vNextBOInspectionAdvancedSearchForm.clickSearchButton();
	}

	public String getSearchFieldValue() {
		return searchFld.getAttribute("value");
	}

	public void setSearchFieldValue(String searchText) {
		Utils.setData(searchFld, searchText);
	}

	public void clickSearchFilterButton() {
		Utils.clickElement(searchFilterBtn);
		wait.until(ExpectedConditions.visibilityOf(clearFilterBtn));
	}

	public void clickClearFilterIcon() {
		Utils.clickElement(clearFilterBtn);
	}

	public String getFirstInspectionStatus() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(inspectionStatusLabels.get(0))).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getSelectedInspectionArchivingReason() {
		return Utils.getText(
				inspectionDetailsPanel.findElement(By.xpath(".//div[@class='text-ellipsis' and @data-bind='visible: isVisibleArchivedReasonInDetails']")));
	}
}