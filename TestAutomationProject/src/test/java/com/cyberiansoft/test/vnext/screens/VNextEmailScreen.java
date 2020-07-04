package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextEmailScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-name='to']")
	private WebElement toEmailPanel;
	
	@FindBy(xpath="//div[@data-name='cc']")
	private WebElement ccEmailPanel;
	
	@FindBy(xpath="//div[@data-name='bcc']")
	private WebElement bccEmailPanel;
	
	@FindBy(xpath="//*[@action='send']")
	private WebElement sendbtn;
	
	@FindBy(xpath="//div[@data-page='email']")
	private WebElement emailscreen;


	final By toEmailXpath = By.xpath(".//input[@name='address-field']");
	final By addMoreBtn = By.xpath(".//*[@action='add']");
	final By removeMailBtn = By.xpath(".//*[@action='remove']");

    public VNextEmailScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 150);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='email']")));
		BaseUtils.waitABit(1000);
	}

	public VNextEmailScreen() {
		PageFactory.initElements(appiumdriver, this);
	}
	
	public void clickToEmailAddressRemoveButton() {
		if (toEmailPanel.findElement(removeMailBtn).isDisplayed())
			tap(toEmailPanel.findElement(removeMailBtn));
		BaseUtils.waitABit(1000);
	}
	
	public void sentToEmailAddress(String emailaddress) {
		WaitUtils.elementShouldBeVisible(emailscreen, true);
		clickToEmailAddressRemoveButton();
		toEmailPanel.findElement(toEmailXpath).clear();
		toEmailPanel.findElement(toEmailXpath).sendKeys(emailaddress);
	}
	
	public void sentToCCEmailAddress(String ccemailaddress) {
		if (ccEmailPanel.findElement(removeMailBtn).isDisplayed())
			tap(ccEmailPanel.findElement(removeMailBtn));
		BaseUtils.waitABit(1000);
		ccEmailPanel.findElement(toEmailXpath).clear();
		ccEmailPanel.findElement(toEmailXpath).sendKeys(ccemailaddress);
	}
	
	public void sentToBCCEmailAddress(String bccemailaddress) {
		if (bccEmailPanel.findElement(removeMailBtn).isDisplayed())
			tap(bccEmailPanel.findElement(removeMailBtn));
		BaseUtils.waitABit(1000);
		bccEmailPanel.findElement(toEmailXpath).clear();
		bccEmailPanel.findElement(toEmailXpath).sendKeys(bccemailaddress);
	}
	
	public String getToEmailFieldValue() {
		return toEmailPanel.findElement(toEmailXpath).getAttribute("value");
	}

	public String getCCEmailFieldValue() {
		return ccEmailPanel.findElement(toEmailXpath).getAttribute("value");
	}

	public String getBCCEmailFieldValue() {
		return bccEmailPanel.findElement(toEmailXpath).getAttribute("value");
	}
	
	public void clickSendEmailsButton() {
		WaitUtils.getGeneralFluentWait().until(driver -> {
			sendbtn.click();
			return true;
		});
	}
	
	public String sendEmail() {
		clickSendEmailsButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		return informationdlg.clickInformationDialogOKButtonAndGetMessage();
	}

}
