package com.cyberiansoft.test.vnextbo.screens.Inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
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
	public WebElement inspectionsList;

	@FindBy(xpath = "//div[@class='entity-details__content' and @data-bind='visible: isDetailsVisible']")
	public WebElement inspectionDetailsPanel;

	@FindBy(xpath = "//table[@data-automation-id='inspectionsDetailsServicesList']")
	private WebElement inspectionServicesList;

	@FindBy(xpath = "//ul[@data-automation-id='inspectionsDetailsDamagesList']")
	private WebElement imageLegend;

	@FindBy(xpath = "//span[@data-automation-id='inspectionsDetailsPrintButton']")
	public WebElement printInspectionIcon;

	@FindBy(xpath = "//button[@data-automation-id='inspectionsDetailsApproveButton']")
	public WebElement approveInspectionIcon;

	@FindBy(id = "inspectiontypes-search")
	private WebElement searchInspectionsPanel;

	@FindBy(xpath = "//input[@id='advSearchEstimation-freeText']")
	public WebElement searchFld;

	@FindBy(xpath = "//section[@class='view']//*[@data-bind='text: filterInfoString']")
	public WebElement filterInfoText;

	@FindBy(xpath = "//i[@id='advSearchEstimation-search']")
	private WebElement searchFilterBtn;

	@FindBy(xpath = "//section[@class='view']//i[contains(@data-bind, 'click: clear,')]")
	public WebElement clearFilterBtn;

	@FindBy(xpath = "//button[contains(text(), 'Approve and Complete')]")
	private WebElement approveAndCompleteButton;

	@FindBy(xpath = "//div[contains(@class, 'status__label')]")
	private List<WebElement> inspectionStatusLabels;

	@FindBy(xpath = "//a[@data-bind='click: showTermsAndConditions']")
	public WebElement termsAndConditionsLink;

	@FindBy(xpath = "//a[@data-bind='click: showPrivacyPolicy']")
	public WebElement privacyPolicyLink;

	@FindBy(xpath = "//iframe[@name='intercom-messenger-frame']")
	public WebElement intercomMessengerFrame;

	@FindBy(xpath = "//div[contains(@class,'intercom-messenger-new-conversation')]")
	public WebElement intercomNewConversionSpace;

	@FindBy(xpath = "//iframe[@name='intercom-launcher-frame']")
	public WebElement intercomLauncherFrame;

	@FindBy(xpath = "//div[contains(@class, 'intercom-launcher')]")
	public WebElement openCloseIntercomButton;

	@FindBy(xpath = "//i[@id='advSearchEstimation-caret']")
	public WebElement searchFieldAdvancedSearchCaret;

	@FindBy(xpath = "//div[@id='advSearchEstimation-savedSearchList']//i[@class='icon-gear']")
	public WebElement searchFieldAdvancedSearchIconGear;

	@FindBy(xpath = "//i[@data-automation-id='inspectionsDetailsEnlargeVisualForm']")
	public WebElement inspectionImageZoomIcon;

	@FindBy(xpath = "(//i[@class='icon-comment' and not(contains(@style, 'display: none'))])[1]")
	public WebElement inspectionNotesIcon;

	@FindBy(xpath = "//span[@data-automation-id='inspectionsDetailsPrintSupplementButton']")
	public WebElement printSupplementIcon;

	@FindBy(xpath = "//span[contains(@class, 'close-multi-archive-reasons')]//span[contains(@class, 'archive')]")
	public WebElement archiveIcon;

	@FindBy(xpath = "//span[@data-automation-id='inspectionsSingleUnArchiveButton']")
	public WebElement unArchiveIcon;

	@FindBy(xpath = "//i[@id='advSearchEstimation-editIcon']")
	public WebElement editAdvancedSearchIcon;

	@FindBy(xpath = "//span[@class='entity-list__item__title']")
	public List<WebElement> inspectionsNamesElementsList;

	@FindBy(id = "advSearchEstimation-savedSearchList")
	public WebElement savedSearchListForm;

	@FindBy(xpath = "//div[@id='advSearchEstimation-savedSearchList']//div/span")
	public List<WebElement> savedSearchesList;

	@FindBy(xpath = "//a[@class='link--underlined']")
	public WebElement howToCreateInspectionLink;

	@FindBy(xpath = ".//span[@data-bind='text:clientName']")
	public WebElement selectedInspectionCustomerName;

	@FindBy(xpath = "//div[@class='entity-list__item__status__label']")
	public List<WebElement> inspectionStatusesList;

	@FindBy(xpath = "//div[@class='entity-list__item__description']//b")
	public List<WebElement> inspectionNumbersList;

	@FindBy(xpath = "//div[@class='text-ellipsis' and @data-bind='visible: isVisibleArchivedReasonInDetails']")
	public WebElement selectedInspectionArchivedReason;

	public WebElement selectedInspectionFieldValueByName(String fieldLabel) {
		return driver.findElement(By.xpath("//div[@class='text-ellipsis' and contains(text(), '" + fieldLabel + "')]/span"));
	}

	public WebElement archivingReasonByName(String reason) {
		return driver.findElement(By.xpath("//div[@class='archive-reasons-title' and text()='" + reason + "']"));
	}

	public WebElement inspectionStatusByInspectionNumber(String inspectionNumber) {
		return driver.findElement(By.xpath("//b[text()='" +inspectionNumber + "']/ancestor::" +
				"div[@class='entity-list__item entity-list__item--active']//div[@class='entity-list__item__status__label']"));
	}

	public VNextBOInspectionsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public boolean isInspectionApproveButtonVisible() {	return Utils.isElementDisplayed(approveInspectionIcon);	}

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

	public void approveInspection(String approveNotes) {
		String parentHandle = driver.getWindowHandle();
		VNextBOInspectionsPageSteps.clickInspectionApproveButton();
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
		VNextBOInspectionsPageSteps.clickInspectionApproveButton();
		new VNextBOConfirmationDialog().clickYesButton();
		waitForNewTab();
		String newWindow = Utils.getNewTab(parentHandle);
		driver.switchTo().window(newWindow);
		waitForLoading();
	}

	public void declineInspection(String declineNotes) {
		String parentHandle = driver.getWindowHandle();
		VNextBOInspectionsPageSteps.clickInspectionApproveButton();
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

	public VNextBOInspectionAdvancedSearchForm openSavedAdvancedSearchFilter(String filterName) {
		waitABit(2000);
		Actions act = new Actions(driver);
		act.moveToElement(savedSearchListForm.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i"))).perform();

		savedSearchListForm.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i")).click();
		return PageFactory.initElements(
				driver, VNextBOInspectionAdvancedSearchForm.class);
	}

	public void setSearchFieldValue(String searchText) {
		Utils.clearAndType(searchFld, searchText);
	}

	public void clickSearchFilterButton() {
		Utils.clickElement(searchFilterBtn);
		WaitUtils.isElementPresent(clearFilterBtn);
	}

	public String getFirstInspectionStatus() {
		try {
			return Utils.getText(inspectionStatusLabels.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}