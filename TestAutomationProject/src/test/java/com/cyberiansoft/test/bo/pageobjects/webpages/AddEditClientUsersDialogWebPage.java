package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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

public class AddEditClientUsersDialogWebPage extends BaseWebPage{
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbEmail']")
	private TextField useremailfld;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbFirstName']")
	private TextField userfstnamefld;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbLastName']")
	private TextField userlstnamefld;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbCompany']")
	private TextField usercompanyfld;
	
	@FindBy(xpath = "//textarea[@id='ctl00_Content_ctl01_ctl01_Card_tbAddress']")
	private TextField useradressfld;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbCity']")
	private TextField usercityfld;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_countryState_ddlCountry_Input']")	
	private ComboBox usercountrycmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlCountry_DropDown')]")
	private DropDown usercountrydd;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_countryState_ddlState_Input']")	
	private ComboBox userstateprovincecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlState_DropDown')]")                
	private DropDown userstateprovincedd;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbZip']")
	private TextField userzippostcodefld;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbPhone']")
	private TextField userphonefld;
	
	@FindBy(xpath = "//input[@id='ctl00_Content_ctl01_ctl01_Card_tbAccountingID']")
	private TextField useraccountidfld;
	
//	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_cbAllowSupportTickets")
	@FindBy(xpath = "//label[text()='Allow creating support tickets']")
	private WebElement allowcreatchkbox;
	
//	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_cblRoles_0")
	@FindBy(xpath = "//label[text()='Client']")
	private WebElement clientchkbox;
	
//	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_cblRoles_1")
	@FindBy(xpath = "//label[text()='ClientAccountant']")
	private WebElement clientaccountchkbox;
	
//	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_cblRoles_2")
	@FindBy(xpath = "//label[text()='Client Monitor Manager']")
	private WebElement clientmonmanagchkbox;
	
//	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_cblRoles_3")
	@FindBy(xpath = "//label[text()='Client Inspector']")
	private WebElement clientinspectchkbox;
	
//	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_cblRoles_4")
	@FindBy(xpath = "//label[text()='SalesPerson']")
	private WebElement salespersonchkbox;
	
//	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_cblRoles_5")
	@FindBy(xpath = "//label[text()='Sales Person Monitor Manager']")
	private WebElement salespersmonmanagchkbox;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
	private WebElement buttonok;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnCancel")
	private WebElement buttoncnsl;
	
	public AddEditClientUsersDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		wait.until(ExpectedConditions.visibilityOf(clientchkbox));
	}
	
	public void setUserEmail(String useremail) {
		clearAndType(useremailfld, useremail);
	}
	
	public String getUserEmail() {
		return useremailfld.getValue();
	}
	
	public void setUserFirstName(String userfirstname) {
		clearAndType(userfstnamefld, userfirstname);
	}
	
	public String getUserFirstName() {
		return userfstnamefld.getValue();
	}
	
	public void setUserLastName(String userlastname) {
		clearAndType(userlstnamefld, userlastname);
	}
	
	public String getUserLastName() {
		return userlstnamefld.getValue();
	}
	
	public void setUserCompanyName(String usercompany) {
		clearAndType(usercompanyfld, usercompany);
	}
	
	public String getUserCompanyName() {
		return usercompanyfld.getValue();
	}
	
	public void setUserAddress(String useraddress) {
		clearAndType(useradressfld, useraddress);
	}
	
	public String getUserAddress() {
		return useradressfld.getValue();
	}
	
	
	public void setUserCity(String usercity) {
		clearAndType(usercityfld, usercity);
	}
	
	public String getUserCity() {
		return usercityfld.getValue();
	}
	
	public void selectCountry(String country) {
		selectComboboxValue(usercountrycmb, usercountrydd, country);
	}
	
	public String getCountry() {
		return usercountrycmb.getSelectedValue();
	}
	
	public void selectStateProvince(String stateprovince) {
		selectComboboxValue(userstateprovincecmb, userstateprovincedd, stateprovince);
	}
	
	public String getStateProvince() {
		return userstateprovincecmb.getSelectedValue();
	}
	
	public void setUserZipPostCode(String userzippostcode) {
		clearAndType(userzippostcodefld, userzippostcode);
	}
	
	public String getUserZipPostCode() {
		return userzippostcodefld.getValue();
	}
	
	public void setUserPhone(String userphone) {
		clearAndType(userphonefld, userphone);
	}
	
	public String getUserPhone() {
		return userphonefld.getValue();
	}
	
	public void setUserAccountId(String useraccountid) {
		clearAndType(useraccountidfld, useraccountid);
	}
	
	public String getUserAccountId() {
		return useraccountidfld.getValue();
	}
	
	public boolean isAllowCreatChkboxChecked(){
		return isCheckboxChecked(allowcreatchkbox);
	}
	
	public boolean isClientChkboxChecked(){
		return isCheckboxChecked(clientchkbox);
	}
	
	public boolean isClientAccountChkboxChecked(){
		return isCheckboxChecked(clientaccountchkbox);
	}
	
	public boolean isClientMonManagChkboxChecked(){
		return isCheckboxChecked(clientmonmanagchkbox);
	}
	
	public boolean isClientInspectChkboxChecked(){
		return isCheckboxChecked(clientinspectchkbox);
	}
	
	public boolean isSalesPersonChkboxChecked(){
		return isCheckboxChecked(salespersonchkbox);
	}
	
	public boolean isSalesPersonMonManagChkboxChecked(){
		return isCheckboxChecked(salespersmonmanagchkbox);
	}
 
	public void clickAllowCreatChkbox(){
		if (!isAllowCreatChkboxChecked()){
			clickAndWait(allowcreatchkbox);
		}
	}
	
	public void clickClientChkbox(){
		if (!isClientChkboxChecked()){
			clickAndWait(clientchkbox);
		}
	}
	
	public void clickClientAccountChkbox(){
		if (!isClientAccountChkboxChecked()){
			clickAndWait(clientaccountchkbox);
		}
	}
	
	public void clickClientMonManagChkbox(){
		if (!isClientMonManagChkboxChecked()){
			clickAndWait(clientmonmanagchkbox);
		}
	}
	
	public void clickClientInspectChkbox(){
		if (!isClientInspectChkboxChecked()){
			clickAndWait(clientinspectchkbox);
		}
	}
	

	public void clickSalesPersonChkbox(){
		if (!isSalesPersonChkboxChecked()){
			clickAndWait(salespersonchkbox);
		}
	}
	
	public void clickSalesPersonMonManagChkbox(){
		if (!isSalesPersonMonManagChkboxChecked()){
			clickAndWait(salespersmonmanagchkbox);	
		}
	}
	

	public void clickButtonOk(){
		clickAndWait(buttonok);
	}
	
	public void clickButtonCancel(){
		click(buttoncnsl);
	}
	
	
	public ClientUsersWebPage createUserWithRequiredFields(String useremail,  String userfstname, 
			String userlstname){
		
		setUserEmail(useremail);
		setUserFirstName(userfstname);
		setUserLastName(userlstname);
		clickClientChkbox();
		clickButtonOk();
 
		return PageFactory.initElements(
				driver, ClientUsersWebPage.class);
	}
	
	
	public ClientUsersWebPage editUserWithAlldFields(String userfstname, 
			String userlstname, String usercompany, String useraddress, String usercity, String usercountry,
			String userstateprovince, String userzippostcode, String userphone, String useraccountid){
 
		setUserFirstName(userfstname);
		setUserLastName(userlstname);
		setUserCompanyName(usercompany);
		setUserAddress(useraddress);
		setUserCity(usercity);
		selectCountry(usercountry);
		selectStateProvince(userstateprovince);
		setUserZipPostCode(userzippostcode);
		setUserPhone(userphone);
		setUserAccountId(useraccountid);
		
		clickAllowCreatChkbox();
		clickClientChkbox();
		clickClientAccountChkbox();
		clickClientMonManagChkbox();
		clickClientInspectChkbox();
		clickSalesPersonChkbox();
		clickSalesPersonMonManagChkbox();
		
		clickButtonOk();
 
		return PageFactory.initElements(
				driver, ClientUsersWebPage.class);
	}
	
	
	public ClientUsersWebPage closeUsersDialogWebPageWithoutEdit(){
		
		clickButtonCancel();
 
		return PageFactory.initElements(
				driver, ClientUsersWebPage.class);
	}
	
	
	public boolean checkAddUserPopUp() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbEmail")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbFirstName")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbLastName")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbCompany")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbAddress")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbCity")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_countryState_ddlCountry_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_countryState_ddlState_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbZip")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbPhone")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbAccountingID")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_cbAllowSupportTickets")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbComments")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_cblRoles")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkAllPossibleValidators() {
		
		return false;
	}
	
	

}
