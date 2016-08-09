package com.cyberiansoft.test.vnextbo.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.VNextWebTable;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;

public class VNexBOUsersWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//div[@id='users-form-popup-view']/button")
	private WebElement adduserbtn;
	
	@FindBy(xpath = "//div[@id='users-list']/table")
	private VNextWebTable userstable;
	
	@FindBy(xpath = "//div[@id='pagingPanel']/button[contains(text(), 'Next')]")
	private WebElement nextpagebtn;
	
	public VNexBOUsersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(userstable.getWrappedElement()));
	}
	
	public VNexBOAddNewUserDialog clickAddUserButton() {
		adduserbtn.click();
		return PageFactory.initElements(
				driver, VNexBOAddNewUserDialog.class);
	}
	
	public List<WebElement>  getUsersTableRows() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(userstable.getWrappedElement()));
		return userstable.getTableRows();
	}
	
	public int getUsersTableRowCount() {
		return getUsersTableRows().size();
	}
	
	private WebElement getTableRowWithUserMail(String usermail) {
		waitABit(300);
		WebElement userrow = null;
		List<WebElement> rows = getUsersTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath("./td[" + userstable.getTableColumnIndex("Email") + "]")).getText().equals(usermail)) {
				userrow = row;
				break;
			}			
		} 
		return userrow;
	}
	
	public boolean isUserPresentOnCurrentPageByUserEmail(String usermail) {
		boolean founded = false;
		WebElement row = getTableRowWithUserMail(usermail);
		if (row != null)
			founded = true;
		return founded;
	}
	
	public boolean findUserInTableByUserEmail(String usermail) {
		boolean founded = false;
		boolean nextpage = true;
		while (nextpage) {
			if (nextpagebtn.getAttribute("disabled") != null) {
				founded = isUserPresentOnCurrentPageByUserEmail(usermail);
				nextpage = false;
			} else if (!isUserPresentOnCurrentPageByUserEmail(usermail)) {
				new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.elementToBeClickable(nextpagebtn)).click();
				waitABit(1000);
			} else  {
				founded = true;
				break;
			}
		}
		return founded;
	}
	
	public boolean isRedWarningTrianglePresentForUser(String usermail) {
		boolean trianglexists = false;
		WebElement row = getTableRowWithUserMail(usermail);
		if (row != null)
			trianglexists = row.findElement(By.xpath(".//i[@class='icon-warning text-red']")).isDisplayed();
		else
			Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
		return trianglexists;
	}
	
	public VNexBOAddNewUserDialog clickEditButtonForUser(String usermail) {
		WebElement row = getTableRowWithUserMail(usermail);
		if (row != null) {
			Actions act = new Actions(driver);
			act.moveToElement(row.findElement(By.xpath(".//td[@class='grid__actions']"))).perform();
			act.click(row.findElement(By.xpath(".//td[@class='grid__actions']/span[@data-bind='click: edit']"))).perform();
		} else
			Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
		return PageFactory.initElements(
				driver, VNexBOAddNewUserDialog.class);
	}
	
	public VNextConfirmationDialog clickResendButtonForUser(String usermail) {
		WebElement row = getTableRowWithUserMail(usermail);
		if (row != null) {
			row.findElement(By.xpath(".//button[@data-bind='click: resendConfirmation']")).click();
		} else
			Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
		return PageFactory.initElements(
				driver, VNextConfirmationDialog.class);
	}
	
	public void clickUserResendButtonAndAgree(String usermail) {
		VNextConfirmationDialog confirmsg = clickResendButtonForUser(usermail);
		final String msg = confirmsg.clickYesAndGetConfirmationDialogMessage();
		Assert.assertEquals(msg, VNextBOAlertMessages.USER_DIDNT_CONFIRM_REGISTRATION);
	}
	
	public void clickUserResendButtonAndDisagree(String usermail) {
		VNextConfirmationDialog confirmsg = clickResendButtonForUser(usermail);
		final String msg = confirmsg.clickNoAndGetConfirmationDialogMessage();
		Assert.assertEquals(msg, VNextBOAlertMessages.USER_DIDNT_CONFIRM_REGISTRATION);
	}
	
	public String getUserName(String usermail) {
		String username = "";
		WebElement row = getTableRowWithUserMail(usermail);
		if (row != null) {
			username = row.findElement(By.xpath("./td[" + userstable.getTableColumnIndex("Name") + "]")).getText();
		} else
			Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
		return username;
	}
	
	public String getUserPhone(String usermail) {
		String userphone = "";
		WebElement row = getTableRowWithUserMail(usermail);
		if (row != null) {
			userphone = row.findElement(By.xpath("./td[" + userstable.getTableColumnIndex("Phone") + "]")).getText();
		} else
			Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
		return userphone;
	}

}
