package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;


public class VNextFeedbackScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='feedback-form']")
	private WebElement feedbackscreen;
	
	@FindBy(xpath="//*[@action='type']/input[2]")
	private WebElement feedbacktypefld;
	
	@FindBy(xpath="//*[@data-page='feedback-types']")
	private WebElement typeslist;
	
	@FindBy(xpath="//*[@action='sections']/input[2]")
	private WebElement areafld;
	
	@FindBy(xpath="//*[@data-page='feedback-sections']")
	private WebElement areaslist;
	
	@FindBy(id="FeedbackSubject")
	private WebElement subjectfld;
	
	@FindBy(id="FeedbackDescription")
	private WebElement descfld;
	
	@FindBy(xpath="//*[@action='send']")
	private WebElement sendbtn;

    public VNextFeedbackScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		BaseUtils.waitABit(3000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(feedbackscreen));
	}
	
	public void selectFeedbackType(String feedbackType) {
		tap(feedbacktypefld);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(typeslist));
		tap(typeslist.findElement(By.xpath(".//a[contains(text(), '" + feedbackType + "')]")));
	}
	
	public void selectArea(String feedbackArea, String subArea) {
		tap(areafld);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(areaslist));
		tap(areaslist.findElement(By.xpath(".//a[@data-id='" + feedbackArea + "']")));
		tap(areaslist.findElement(By.xpath(".//a[@data-id='" + subArea + "']")));
	}
	
	public void setFeedbackSubject(String subject) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(subjectfld));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("FeedbackSubject")));
		subjectfld.clear();
		subjectfld.sendKeys(subject);
	}
	
	public void setFeedbackDescription(String description) {
		descfld.clear();
		descfld.sendKeys(description);
	}
	
	public VNextStatusScreen clickSendButton() {
		List<WebElement> sendbtns = feedbackscreen.findElements(By.xpath(".//*[@action='send']"));
		for (WebElement btn : sendbtns)
			if (btn.isDisplayed())
				tap(btn);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdlg.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.YOUR_FEEDBACK_HAS_BEEN_SENT);
		return new VNextStatusScreen(appiumdriver);
	}

}
