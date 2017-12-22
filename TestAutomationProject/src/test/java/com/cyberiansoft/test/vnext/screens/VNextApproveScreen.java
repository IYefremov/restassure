package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.TouchAction;

public class VNextApproveScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-automations-id='approve']")
	private WebElement approcescreen;
	
	@FindBy(id="approve-signature-canvas")
	private WebElement drawcanvas;
	
	@FindBy(xpath="//*[@action='clear']")
	private WebElement clearsignaturebtn;
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement savebtn;
	
	public VNextApproveScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approcescreen));
	}
	
	public void clickSignatureCanvas() {
		tap(drawcanvas);
	}
	
	public void clickClearSignatureButton() {
		tap(clearsignaturebtn);
		log(LogStatus.INFO, "Tap on Clear signature Button");
	}
	
	public void drawSignature() {
		clickSignatureCanvas();
		int xx = drawcanvas.getLocation().getX();

		int yy = drawcanvas.getLocation().getY();
		/**TouchActions action = new TouchActions(appiumdriver);
		try {
		action.down(xx + 100,yy + 100).perform();
		} catch (NullPointerException e) {
			//do nothing
		}*/

		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(xx + 100,yy + 100).waitAction(300).moveTo(xx + 200, yy + 200).release().perform();
		
	}
	
	public void clickSaveButton() {
		tap(savebtn);
		log(LogStatus.INFO, "Tap on Save Button");
	}
	
	public void saveApprovedInspection() {
		clickSaveButton();
	}
	
	public boolean isClearButtonVisible() {
		return clearsignaturebtn.isDisplayed();
	}

}
