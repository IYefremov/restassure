package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class NewClientDialogWebPage extends BaseWebPage {

	@FindBy(xpath = "//input[contains(@id, 'Card_tbCompany')]")
	private TextField companynamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbFirstName')]")
	private TextField companyfirstnamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbLastName')]")
	private TextField companylastnamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbEmail')]")
	private TextField companyemailfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbPhone')]")
	private TextField companyphonefld;

	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbNotes')]")
	private TextField notesttxtarea;

	@FindBy(xpath = "//input[contains(@id, 'Card_rtpMondayStart_dateInput') and @class]")
	private TextField workhourstart;

	@FindBy(xpath = "//input[contains(@id, 'Card_rtpMondayFinish_dateInput') and @class]")
	private TextField workhourfinish;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingId')]")
	private TextField companyaccidfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingId2')]")
	private TextField companyaccid2fld;

	@FindBy(xpath = "//input[contains(@id, 'Card_comboAreas_Input')]")
	private ComboBox companydefareacmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_comboAreas_DropDown')]")
	private DropDown companydefareadd;

	// Ship To
	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbAddress')]")
	private TextField companyshiptoaddress;

	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbAddress2')]")
	private TextField companyshiptoaddress2;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbCity')]")
	private TextField companyshiptocity;

	@FindBy(xpath = "//input[contains(@id, 'Card_countryState_ddlCountry_Input')]")
	private ComboBox companyshiptocountrycmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlCountry_DropDown')]")
	private DropDown companyshiptocountrydd;

	@FindBy(xpath = "//input[contains(@id, 'Card_countryState_ddlState_Input')]")
	private ComboBox companyshiptostatecmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlState_DropDown')]")
	private DropDown companyshiptostatedd;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbZip')]")
	private TextField companyshiptozip;

	@FindBy(xpath = "//input[contains(@id, 'Card_btnCopyShipToBill')]")
	private WebElement copytobilltobtn;

	// BillTo
	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbBillingAddress')]")
	private TextField companybilltoaddress;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbBillingCity')]")
	private TextField companybilltocity;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbBillingZip')]")
	private TextField companybilltozip;

	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl05_ctl02_BtnOk')]")
	private WebElement OKbtn;

	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl05_ctl02_BtnCancel')]")
	private WebElement cancelbtn;

	@FindBy(className = "ModalDialog")
	private WebElement modalDialog;

	@FindBy(xpath = "//span[text()='Other']")
	private WebElement otherTab;

	@FindBy(xpath = "//input[contains(@id, 'cbUseSingleWOType')]")
	private WebElement singleWOtypeCheckbox;

	@FindBy(xpath = "//div[contains(@id, 'Card_pnlOrderType')]")
	private WebElement singleWOtype;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl05_ctl01_Card_comboOrderType")
	private ComboBox singleWOtypeCombobox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl05_ctl01_Card_comboOrderType_DropDown")
	private DropDown singleWOtypeDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl05_ctl01_Card_comboOrderType_Input")
	private WebElement singleWOtypeInput;

	public NewClientDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void createRetailClient(String firstname, String lastname) {
		switchToRetailCustomer();
		setClientFirstName(firstname);
		setClientLastName(lastname);
		clickOKButton();
	}

	public void createWholesaleClient(String clientname) {
		switchToWholesaleCustomer();
		setCompanyName(clientname);
		clickOKButton();
	}

	public void switchToRetailCustomer() {
		checkboxSelect("Retail");
	}

	public boolean isCompanyRetail() {
		waitForLoading();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='Retail']")));
		return isCheckboxChecked(driver.findElement(By.xpath("//label[text()='Retail']")));
	}

	public void switchToWholesaleCustomer() {
		checkboxSelect("Wholesale");
	}

	public boolean isCompanyWholesale() {
		return isCheckboxChecked(driver.findElement(By.xpath("//label[text()='Wholesale']")));
	}

	public void setCompanyName(String companyname) {
		clearAndType(companynamefld, companyname);
	}

	public String getCompanyName() {
		return companynamefld.getValue();
	}

	public void setClientFirstName(String firstname) {
		clearAndType(companyfirstnamefld, firstname);
	}

	public String getClientFirstName() {
		return companyfirstnamefld.getValue();
	}

	public void setClientLastName(String lastname) {
		clearAndType(companylastnamefld, lastname);
	}

	public String getClientLastName() {
		return companylastnamefld.getValue();
	}

	public void setCompanyMail(String companymail) {
		clearAndType(companyemailfld, companymail);
	}

	public String getCompanyMail() {
		return companyemailfld.getValue();
	}

	public void setCompanyPhone(String companyphone) {
		clearAndType(companyphonefld, companyphone);
	}

	public String getCompanyPhone() {
		return companyphonefld.getValue();
	}

	public void setCompanyNotes(String notes) {
		clearAndType(notesttxtarea, notes);
	}

	public String getCompanyNotes() {
		return notesttxtarea.getValue();
	}

	public void setWorkHoursStart(String hours) {
		clearAndType(workhourstart, hours);
	}

	public String getWorkHoursStart() {
		return workhourstart.getValue();
	}

	public boolean isWorkHoursStartVisible() {
//		Thread.sleep(5000);
//		return workhourstart.isDisplayed();
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Card_rtpWorkDayStart_dateInput")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void setWorkHoursFinish(String hours) {
		clearAndType(workhourfinish, hours);
	}

	public String getWorkHoursFinish() {
		return workhourfinish.getValue();
	}

	public boolean isWorkHoursFinishtVisible() {
//		return workhourfinish.isDisplayed();
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Card_rtpWorkDayFinish_dateInput")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void setCompanyAccountingID(String companyaccid) {
		clearAndType(companyaccidfld, companyaccid);
	}

	public String getCompanyAccountingID() {
		return companyaccidfld.getValue();
	}

	public void setCompanyAccountingID2(String companyaccid2) {
		clearAndType(companyaccid2fld, companyaccid2);
	}

	public String getCompanyAccountingID2() {
		return companyaccid2fld.getValue();
	}

	public void selectCompanyDefaultArea(String defarea) {
		selectComboboxValue(companydefareacmb, companydefareadd, defarea);
	}

	public void selectShipToAddress() {
		click(driver.findElement(By.xpath("//span[@class='rtsTxt' and text()='Ship To']")));
	}

	public void setCompanyShipToAddress(String shiptoaddress) {
		clearAndType(companyshiptoaddress, shiptoaddress);
	}

	public void setCompanyShipToAddress2(String shiptoaddress2) {
		clearAndType(companyshiptoaddress2, shiptoaddress2);
	}

	public String getCompanyShipToAddress() {
		return companyshiptoaddress.getValue();
	}

	public void setCompanyShipToCity(String shiptocity) {
		clearAndType(companyshiptocity, shiptocity);
	}

	public String getCompanyShipToCity() {
		return companyshiptocity.getValue();
	}

	public void selectCompanyShipToCountry(String shiptocountry) {
		selectComboboxValue(companyshiptocountrycmb, companyshiptocountrydd, shiptocountry);
		waitForLoading();
	}

	public void selectCompanyShipToState(String shiptostate) {
		selectComboboxValue(companyshiptostatecmb, companyshiptostatedd, shiptostate);
	}

	public void setCompanyShipToZip(String shiptozip) {
		clearAndType(companyshiptozip, shiptozip);
	}

	public String getCompanyShipToZip() {
		return companyshiptozip.getValue();
	}

	public void clickCopyToBillToButton() {
		clickAndWait(copytobilltobtn);
	}

	public void selectBillToAddress() {
		click(driver.findElement(By.xpath("//span[@class='rtsTxt' and text()='Bill To']")));
	}

	public void setCompanyBillToAddress(String billtoaddress) {
		clearAndType(companybilltoaddress, billtoaddress);
	}

	public String getCompanyBillToAddress() {
		return companybilltoaddress.getValue();
	}

	public void setCompanyBillToCity(String billtocity) {
		clearAndType(companybilltocity, billtocity);
	}

	public String getCompanyBillToCity() {
		return companybilltocity.getValue();
	}

	public void setCompanyBillToZip(String billtozip) {
		clearAndType(companybilltozip, billtozip);
	}

	public String getCompanyBillToZip() {
		return companybilltozip.getValue();
	}

	public boolean isCompanyOptionChecked(String optionname) {
		return isCheckboxChecked(driver.findElement(By.xpath("//label[text()='" + optionname + "']")));
	}

	public void clickOKButton() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(OKbtn)).click();
		} catch (Exception e) {
			Assert.fail("The New Client Dialog 'OK' button has not been clicked");
		}
		waitForLoading();
	}

	public void clickCancelButton() {
		click(cancelbtn);
		wait.until(ExpectedConditions.titleContains("Customers"));
	}

	public void clickToLinkInClientsEdit(String name) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(name))).click();
	}

	public void clickOtherTab() {
		try {
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(otherTab))
					.click();
			wait.until(ExpectedConditions.visibilityOf(singleWOtypeCheckbox));
		} catch (Exception e) {
			Assert.fail("The 'Other' tab has not been clicked", e);
		}
	}

	public void clickSingleWOtypeCheckbox() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(singleWOtypeCheckbox)).click();
			wait.until(ExpectedConditions.attributeContains(singleWOtype, "display", "block"));
		} catch (Exception e) {
			Assert.fail("The 'Single WO type checkbox' has not been checked", e);
		}
	}

	public void selectRandomSingleWOtype() {
		selectRandomComboboxValue(singleWOtypeCombobox, singleWOtypeDropDown);
	}

	public String getSingleWOtype() {
		return wait.until(ExpectedConditions.visibilityOf(singleWOtypeInput)).getAttribute("value");
	}

	public boolean isSundayStartTimeVisible() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl05_ctl01_Card_rtpSundayStart_dateInput")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isSundayFinishTimeVisible() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl05_ctl01_Card_rtpSundayFinish_dateInput")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

}
