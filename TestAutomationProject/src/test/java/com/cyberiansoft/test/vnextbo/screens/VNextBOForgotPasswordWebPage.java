package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextBOForgotPasswordWebPage extends VNextBOBaseWebPage {

	@FindBy(xpath = "//strong[text()='Enter your Email address']")
	private WebElement enterEmailLabel;

	@FindBy(id = "confirmEmail")
	private TextField confirmmailfld;
	
	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement submitbtn;
	
	@FindBy(xpath = "//button[@class='btn btn-autofocus' and text()='OK']")
	private WebElement alertOKbtn;

	@FindBy(id = "loginLogin")
	private WebElement loginLink;
	
	public VNextBOForgotPasswordWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(submitbtn));
	}
	
	public VNextBOLoginScreenWebPage sendConfirmationMail(String usermail) {
		setConfirmationMailFieldValue(usermail);
		clickSubmitButton();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(alertOKbtn)).click();
        } catch (Exception e) {
            clickWithJS(alertOKbtn);
        }

        return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class);
	}
	
	public void setConfirmationMailFieldValue(String usermail) {
		confirmmailfld.clearAndType(usermail);
	}
	
	public boolean isConfirmationMailFieldDisplayed() {
		return confirmmailfld.isDisplayed();
	}

	public boolean isLoginLinkDisplayed() {
		return loginLink.isDisplayed();
	}

	public boolean isEnterEmailLLabelDisplayed()  { return enterEmailLabel.isDisplayed(); }

	public boolean isSubmitButtonDisplayed()  { return submitbtn.isDisplayed(); }
	
	public void clickSubmitButton() {
		submitbtn.click();
	}

	public VNextBOLoginScreenWebPage clickLoginLink() {
		loginLink.click();
		return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class);
	}
	
	public String getErrorMessageValue() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@data-bind, 'confirmEmail.hasError')]/p"))));
		return driver.findElement(By.xpath("//div[contains(@data-bind, 'confirmEmail.hasError')]/p")).getText();
	}

}
