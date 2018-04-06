package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextEmailScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-name='to']")
	private WebElement toemailpanel;
	
	@FindBy(xpath="//div[@data-name='cc']")
	private WebElement ccemailpanel;
	
	@FindBy(xpath="//div[@data-name='bcc']")
	private WebElement bccemailpanel;
	
	@FindBy(xpath="//*[@action='send']")
	private WebElement sendbtn;
	
	@FindBy(xpath="//div[@data-page='email']")
	private WebElement emailscreen;
	
	final String toemailxpath = ".//input[@name='address-field']";
	final String addmorebtn = ".//*[@action='add']";
	final String removemailbtn = ".//*[@action='remove']";
	
	public VNextEmailScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='email']")));
		BaseUtils.waitABit(1000);
	}
	
	public void clickToEmailAddressRemoveButton() {
		if (toemailpanel.findElement(By.xpath(removemailbtn)).isDisplayed())
			tap(toemailpanel.findElement(By.xpath(removemailbtn)));
		BaseUtils.waitABit(1000);
	}
	
	public void sentToEmailAddress(String emailaddress) {
		clickToEmailAddressRemoveButton();
		toemailpanel.findElement(By.xpath(toemailxpath)).clear();
		toemailpanel.findElement(By.xpath(toemailxpath)).sendKeys(emailaddress);
	}
	
	public void sentToCCEmailAddress(String ccemailaddress) {
		if (ccemailpanel.findElement(By.xpath(removemailbtn)).isDisplayed())
			tap(ccemailpanel.findElement(By.xpath(removemailbtn)));
		BaseUtils.waitABit(1000);
		ccemailpanel.findElement(By.xpath(toemailxpath)).clear();
		ccemailpanel.findElement(By.xpath(toemailxpath)).sendKeys(ccemailaddress);
	}
	
	public void sentToBCCEmailAddress(String bccemailaddress) {
		if (bccemailpanel.findElement(By.xpath(removemailbtn)).isDisplayed())
			tap(bccemailpanel.findElement(By.xpath(removemailbtn)));
		BaseUtils.waitABit(1000);
		bccemailpanel.findElement(By.xpath(toemailxpath)).clear();
		bccemailpanel.findElement(By.xpath(toemailxpath)).sendKeys(bccemailaddress);
	}
	
	public void addToEmailAddress(String emailaddress) {
		clickAddMoreToEmailsButton();
		toemailpanel.findElements(By.xpath(toemailxpath)).get(1).clear();
		toemailpanel.findElements(By.xpath(toemailxpath)).get(1).sendKeys(emailaddress);
	}
	
	public String getToEmailFieldValue() {
		return toemailpanel.findElement(By.xpath(toemailxpath)).getAttribute("value");
	}
	
	public void clickAddMoreToEmailsButton() {
		tap(toemailpanel.findElement(By.xpath(addmorebtn)));
	}
	
	public void clickSendEmailsButton() {
		tap(sendbtn);
		BaseUtils.waitABit(2000);
		try {
			tap(sendbtn);
		} catch (WebDriverException e) {
			//do nothing
		}
	}
	
	public String sendEmail() {
		clickSendEmailsButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		return informationdlg.clickInformationDialogOKButtonAndGetMessage();
		
	}

}
