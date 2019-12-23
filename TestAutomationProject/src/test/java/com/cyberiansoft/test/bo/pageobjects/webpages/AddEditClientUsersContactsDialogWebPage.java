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

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class AddEditClientUsersContactsDialogWebPage extends BaseWebPage {

	@FindBy(xpath = "//input[contains(@id, 'Card_tbFirstName')]")
	private TextField contactfstnamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbLastName')]")
	private TextField contactlstnamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbEmail')]")
	private TextField contactemailfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbCompany')]")
	private TextField contactcompanyfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbPhone')]")
	private TextField contactphonefld;

	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbAddress')]")
	private TextField contactadressfld;

	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbAddress2')]")
	private TextField contactadress2fld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbCity')]")
	private TextField contactcityfld;

	@FindBy(xpath = "//span[contains(@id, 'Card_countryState_labelCountry')]")
	private WebElement country;

	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_countryState_ddlCountry_Input']")
	private ComboBox contactcountrycmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlCountry_DropDown')]")
	private DropDown contactcountrydd;

	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_countryState_ddlState_Input']")
	private ComboBox contactstateprovincecmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlState_DropDown')]")
	private DropDown contactstateprovincedd;

	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbZip']")
	private TextField contactzippostcodefld;

	@FindBy(xpath = "//div[contains(@id, 'Card_pnlValidationMessage')]")
	private List<WebElement> notificatfld;

	@FindBy(xpath = "//label[text()='Skip validation']")
	private WebElement skipvalidatchkbox;

	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
	private WebElement buttonok;

	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnCancel")
	private WebElement buttoncnsl;

	public AddEditClientUsersContactsDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}


	public void setContactFirstName(String contactfirstname) {
		clearAndType(contactfstnamefld, contactfirstname);
	}

	public String getContactFirstName() {
		return contactfstnamefld.getValue();
	}

	public void setContactLastName(String contactlastname) {
		clearAndType(contactlstnamefld, contactlastname);
	}

	public String getContactLastName() {
		return contactlstnamefld.getValue();
	}

	public void setContactEmail(String contactemail) {
		clearAndType(contactemailfld, contactemail);
	}

	public String getContactEmail() {
		return contactemailfld.getValue();
	}

	public void setContactCompany(String contactcompany) {
		clearAndType(contactcompanyfld, contactcompany);
	}

	public String getContactCompany() {
		return contactcompanyfld.getValue();
	}

	public void setContactPhone(String contactphone) {
		clearAndType(contactphonefld, contactphone);
	}

	public String getContactPhone() {
		return contactphonefld.getValue();
	}

	public void setContactAdress(String contactadress) {
		clearAndType(contactadressfld, contactadress);
	}

	public String getContactAdress() {
		return contactadressfld.getValue();
	}

	public void setContactAdress2(String contactadress) {
		clearAndType(contactadress2fld, contactadress);
	}

	public String getContactAdress2() {
		return contactadress2fld.getValue();
	}

	public void setContactCity(String contactcity) {
		clearAndType(contactcityfld, contactcity);
	}

	public String getContactCity() {
		return contactcityfld.getValue();
	}

	public void selectCountry(String country) {
		if (!getBrowserType().contains("Edge")) {
			selectComboboxValue(contactcountrycmb, contactcountrydd, country);
		} else {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(contactcountrycmb.getWrappedElement())).click();
			} catch (WebDriverException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", contactcountrycmb.getWrappedElement());
			}
			try {
				wait.until(ExpectedConditions.visibilityOf(contactcountrydd.getWrappedElement()));
			} catch (Exception e) {
				Assert.fail("The droplist has not been displayed!", e);
			}
			try {
				List<WebElement> items = contactcountrydd.getWrappedElement().findElements(By.tagName("li"));
				wait.until(ExpectedConditions.visibilityOfAllElements(items));
				items.stream().filter(w -> w.getText().equals(country)).findFirst().ifPresent(WebElement::click);
			} catch (Exception e) {
				System.err.println("The value has not been found! " + e);
			}
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
						contactcountrycmb.getWrappedElement(), "display", "none");
				wait.until(ExpectedConditions.attributeContains(contactcountrydd.getWrappedElement(), "display", "none"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public String getCountry() {
		return contactcountrycmb.getSelectedValue();
	}

	public void selectStateProvince(String stateprovince) {
		selectComboboxValue(contactstateprovincecmb, contactstateprovincedd, stateprovince);
	}

	public String getStateProvince() {
		return contactstateprovincecmb.getSelectedValue();
	}

	public void setContactZipPostCode(String contactzippostcode) {
		clearAndType(contactzippostcodefld, contactzippostcode);
	}

	public String getContactZipPostCode() {
		return contactzippostcodefld.getValue();
	}


	public boolean isNotificationVisible() {
		boolean isVisible = false;
		if (notificatfld.size() > 0)
			isVisible = true;
		return isVisible;
	}

	public String getNotification() {
		return notificatfld.get(0).getText();
	}


	public boolean isSkipValidationChkboxChecked() {
		return isCheckboxChecked(skipvalidatchkbox);
	}


	public void clickSkipValidationChkbox() {
		if (!isSkipValidationChkboxChecked()) {
			clickAndWait(skipvalidatchkbox);
		}
	}

	public void clickButtonOk() {
		clickAndWait(buttonok);
	}

	public void clickButtonCancel() {
		click(buttoncnsl);
	}


	public void closeContactsDialogWebPageWithoutEdit() {
		clickButtonCancel();
	}


	public void createContactWithRequiredField(String contactfirstname,
											   String contactlastname, String contactemail) {

		setContactFirstName(contactfirstname);
		setContactLastName(contactlastname);
		setContactEmail(contactemail);
		clickButtonOk();
	}

	public void createContactWithRequiredFieldAndSkipValidation(String contactfirstname,
																String contactlastname, String contactemail) {

		setContactFirstName(contactfirstname);
		setContactLastName(contactlastname);
		setContactEmail(contactemail);
		clickSkipValidationChkbox();
		clickButtonOk();

	}

	public void createContactWithAllFields(String contactfirstname,
										   String contactlastname, String contactemail, String contactcompany, String contactphone,
										   String contactadress, String contactadress2, String contactcity, String contactcountry,
										   String contactstateprovince, String contactzippostcode) {

		setContactFirstName(contactfirstname);
		setContactLastName(contactlastname);
		setContactEmail(contactemail);
		setContactCompany(contactcompany);
		setContactPhone(contactphone);
		setContactCity(contactcity);
		setContactAdress(contactadress);
		setContactAdress2(contactadress2);
		selectCountry(contactcountry);
		selectStateProvince(contactstateprovince);
		setContactZipPostCode(contactzippostcode);
		clickButtonOk();

	}


}
