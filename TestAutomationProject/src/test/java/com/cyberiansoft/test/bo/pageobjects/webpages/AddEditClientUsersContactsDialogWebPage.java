package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class AddEditClientUsersContactsDialogWebPage extends BaseWebPage{
	
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(buttoncnsl));
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
		selectComboboxValue(contactcountrycmb, contactcountrydd, country);
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
	
	
	public boolean isNotificationVisible(){	
		boolean isVisible = false;
		if (notificatfld.size() > 0)
			isVisible = true;
		return isVisible;
	}
	
	public String getNotification() {
		return notificatfld.get(0).getText();
	}
	
	
	
	public boolean isSkipValidationChkboxChecked(){
		return isCheckboxChecked(skipvalidatchkbox);
	}
	
	
	public void clickSkipValidationChkbox(){
		if (!isSkipValidationChkboxChecked()){
			clickAndWait(skipvalidatchkbox);
		}
	}
	
	public void clickButtonOk(){
		clickAndWait(buttonok);
	}
	
	public void clickButtonCancel(){
		click(buttoncnsl);
	}
	
	
    public ClientContactsWebPage closeContactsDialogWebPageWithoutEdit(){
		
		clickButtonCancel();
 
		return PageFactory.initElements(
				driver, ClientContactsWebPage.class);
	}
    
    
    public ClientContactsWebPage createContactWithRequiredField(String contactfirstname, 
    		String contactlastname, String contactemail){
		
    	setContactFirstName(contactfirstname);
		setContactLastName(contactlastname);
		setContactEmail(contactemail);
		clickButtonOk();
		
		return PageFactory.initElements(
				driver, ClientContactsWebPage.class);
	}
    
    public ClientContactsWebPage createContactWithRequiredFieldAndSkipValidation(String contactfirstname, 
    		String contactlastname, String contactemail){
		
    	setContactFirstName(contactfirstname);
		setContactLastName(contactlastname);
		setContactEmail(contactemail);
		clickSkipValidationChkbox();
		clickButtonOk();
		
		return PageFactory.initElements(
				driver, ClientContactsWebPage.class);
	}
    
    public ClientContactsWebPage createContactWithAllFields(String contactfirstname, 
    		String contactlastname, String contactemail, String contactcompany, String contactphone,
    		String contactadress, String contactadress2, String contactcity, String contactcountry,
    		String contactstateprovince, String contactzippostcode){
		
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
		
		return PageFactory.initElements(
				driver, ClientContactsWebPage.class);
	}
    
    

}
