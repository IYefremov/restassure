package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextStatusScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@action='update-main-db']")
	private WebElement updatemaindbbtn;
	
	@FindBy(xpath="//*[@action='feedback']")
	private WebElement feedbackbtn;
	
	@FindBy(xpath="//*[@action='back-office']")
	private WebElement gotoBObtn;
	
	public VNextStatusScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(updatemaindbbtn));
	}
	
	public void updateMainDB() {
		tap(updatemaindbbtn);
		log(LogStatus.INFO, "Tap Update Main DB button");
		waitABit(10000);
		/*WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.invisibilityOf(
				appiumdriver.findElement(By.xpath("//*[text()='Waiting for application data']"))));
		wait = new WebDriverWait(appiumdriver, 60);
		waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOf(
				appiumdriver.findElement(By.xpath("//*[text()='Downloading application data']"))));*/
		//waitABit(30000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}
	
	public VNextHomeScreen clickBackButton() {
		clickScreenBackButton();
		log(LogStatus.INFO, "Click Status screen Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextFeedbackScreen clickFeedbackButton() {
		tap(feedbackbtn);
		log(LogStatus.INFO, "Click Status screen Feedback button");
		return new VNextFeedbackScreen(appiumdriver);
	}
	
	public VNextEmailVerificationScreen goToBackOfficeButton() {
		tap(gotoBObtn);
		log(LogStatus.INFO, "Click Status screen Go To Back Office button");
		return new VNextEmailVerificationScreen(appiumdriver);
	}

}
