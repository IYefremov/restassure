package com.cyberiansoft.test.vnextbo.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class VNexBOAddNewUserDialog extends VNextBOBaseWebPage {
	
	@FindBy(id = "users-firstName")
	private TextField firstnamefld;
	
	@FindBy(id = "users-lastName")
	private TextField lastnamefld;
	
	@FindBy(id = "users-email")
	private TextField usermailfld;
	
	@FindBy(id = "users-phone")
	private TextField userphonefld;
	
	@FindBy(id = "users-countryPhoneCodes")
	private WebElement usercountrycodecbmx;
	
	@FindBy(xpath = "//input[@data-bind='checked: data.webAccess']")
	private WebElement webaccesschkbox;
	
	@FindBy(xpath = "//button[@type='submit' and text()='Save']")
	private WebElement savebtn;
	
	@FindBy(xpath = "//div[@class='modal-header']/button[@aria-label='Close']")
	private WebElement closedialogbtn;
	
	public VNexBOAddNewUserDialog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(firstnamefld.getWrappedElement()));
	}
	
	public void createNewUser(String firstname, String lastname, String usermail, String phonenumber) {
		setUserFirstName(firstname);
		setUserLastName(lastname);
		setUserEmail(usermail);
		setUserPhone(phonenumber);
		clickSaveButton();
	}
	
	public VNexBOUsersWebPage createNewUser(String firstname, String lastname, String usermail, String phonenumber, boolean webaccess) {
		setUserFirstName(firstname);
		setUserLastName(lastname);
		setUserEmail(usermail);
		setUserPhone(phonenumber);
		if (webaccess)
			selectWebAccessCheckbox();
		return clickSaveButtonAndWait();
	}
	
	public void setUserFirstName(String firstname) {
		firstnamefld.clearAndType(firstname);
	}
	
	public void setUserLastName(String lastname) {
		lastnamefld.clearAndType(lastname);
	}
	
	public void setUserEmail(String usermail) {
		usermailfld.clearAndType(usermail);
	}
	
	public void setUserPhone(String userphone) {
		userphonefld.clearAndType(userphone);
	}
	
	public void selectWebAccessCheckbox() {
		String selected = webaccesschkbox.getAttribute("checked");
		if (selected ==null)
			webaccesschkbox.click();
	}
	
	public void unselectWebAccessCheckbox() {
		String selected = webaccesschkbox.getAttribute("checked");
		if (selected !=null)
			webaccesschkbox.click();
	}
	
	public void clickSaveButton() {
		List<WebElement> savebtns = driver.findElements(By.xpath("//button[@type='submit' and text()='Save']"));
		for (WebElement btnsave : savebtns)
			if (btnsave.isDisplayed())
				btnsave.click();
		
		//savebtn.click();
	}
	
	public VNexBOUsersWebPage clickSaveButtonAndWait() {
		clickSaveButton();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.invisibilityOfElementLocated(By.id("users-form-popup"))); 
		waitABit(1000);
		return PageFactory.initElements(
				driver, VNexBOUsersWebPage.class);
	}
	
	public boolean isEmailFieldDisabled() {
		return usermailfld.getWrappedElement().getAttribute("disabled") != null;
	}
	
	public String getSelectedUserPhoneCountryCode() {
		Select sel = new Select(usercountrycodecbmx);
		return sel.getFirstSelectedOption().getAttribute("value");
	}
	
	public boolean isErrorMessageShown(String errormsg) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
	        driver.findElement(By.xpath("//div[@class='text-red' and contains(text(), '" + errormsg + "')]"));
	     } catch (NoSuchElementException e) {
	    	 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	         return false;
	    }
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver.findElement(By.xpath("//div[@class='text-red' and contains(text(), '" + errormsg + "')]")).isDisplayed();
	}
	
	public WebElement getAddUserDialogSaveButton() {
		WebElement savebtn = null;
		List<WebElement> savebtns = driver.findElements(By.xpath("//button[@type='submit' and text()='Save']"));
		for (WebElement btnsave : savebtns)
			if (btnsave.isDisplayed())
				savebtn = btnsave;
		return savebtn;
	}
	
	public VNexBOUsersWebPage closeadduserDialog() {
		getAddUserDialogSaveButton().sendKeys(Keys.ESCAPE);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.invisibilityOfElementLocated(By.id("users-form-popup"))); 
		waitABit(1000);
		return PageFactory.initElements(
				driver, VNexBOUsersWebPage.class);
	}
	
}
