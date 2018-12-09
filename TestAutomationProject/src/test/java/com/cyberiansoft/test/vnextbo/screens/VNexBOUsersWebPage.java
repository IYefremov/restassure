package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.VNextWebTable;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNexBOUsersWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@id='users-form-popup-view']/button")
	private WebElement adduserbtn;
	
	@FindBy(xpath = "//div[@id='users-list']/table")
	private VNextWebTable userstable;
	
	@FindBy(xpath = "//div[@id='pagingPanel']/button[@data-automation-id='usersListNext']")
	private WebElement nextpagebtn;
	
	@FindBy(xpath = "//div[@id='pagingPanel']/button[@data-automation-id='usersListPrev']")
	private WebElement previouspagebtn;

	@FindBy(id = "advSearchUsers-freeText")
	private WebElement searchField;
	
	public VNexBOUsersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(userstable.getWrappedElement()));
	}
	
	public VNexBOAddNewUserDialog clickAddUserButton() {
		wait.until(ExpectedConditions.elementToBeClickable(adduserbtn)).click();
		return PageFactory.initElements(
				driver, VNexBOAddNewUserDialog.class);
	}
	
	public List<WebElement>  getUsersTableRows() {
		waitLong
                .ignoring(StaleElementReferenceException.class)
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
            if (
                    wait
                            .ignoring(StaleElementReferenceException.class)
                            .until(ExpectedConditions.visibilityOf(row.findElement(By.xpath("./td[" +
                            userstable.getTableColumnIndex("Email") + "]"))))
                    .getText()
                    .equals(usermail)) {
				userrow = row;
				break;
			}
		}
		return userrow;
	}

    public boolean isUserPresentOnCurrentPageByUserEmail(String usermail) {
		boolean found = false;
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(getTableRowWithUserMail(usermail))));
        } catch (NullPointerException ignored) {}
		WebElement row = getTableRowWithUserMail(usermail);
		if (row != null)
			found = true;
		return found;
	}
	
	public boolean findUserInTableByUserEmail(String usermail) {
		boolean found = false;
		boolean nextpage = true;
		while (nextpage) {
			if (nextpagebtn.getAttribute("disabled") != null) {
				found = isUserPresentOnCurrentPageByUserEmail(usermail);
				nextpage = false;
			} else if (!isUserPresentOnCurrentPageByUserEmail(usermail)) {
				wait.until(ExpectedConditions.elementToBeClickable(nextpagebtn));
				clickWithJS(nextpagebtn);
				waitABit(2500);
			} else  {
				found = true;
				break;
			}
		}
		return found;
	}

	public boolean findUserBySearch(String usenFirstName, String userMail) {
        wait.until(ExpectedConditions.elementToBeClickable(searchField)).click();
        searchField.sendKeys(usenFirstName);
        searchField.sendKeys(Keys.ENTER);
        waitForLoading();
        return isUserPresentOnCurrentPageByUserEmail(userMail);
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
