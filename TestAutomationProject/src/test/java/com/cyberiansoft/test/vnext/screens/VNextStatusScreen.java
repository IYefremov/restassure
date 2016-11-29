package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextStatusScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@action='update-main-db']")
	private WebElement updatemaindbbtn;
	
	@FindBy(xpath="//a[@class='link icon-only back']")
	private WebElement backbtn;
	
	public VNextStatusScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(updatemaindbbtn));
	}
	
	public void updateMainDB() {
		tap(updatemaindbbtn);
		log(LogStatus.INFO, "Tap Update Main DB button");
		waitABit(30000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		testReporter.log(LogStatus.INFO, "Tap Back button");
		return new VNextHomeScreen(appiumdriver);
	}

}
