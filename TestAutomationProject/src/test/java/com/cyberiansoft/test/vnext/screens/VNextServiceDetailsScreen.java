package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextServiceDetailsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//i[@action='apply']")
	private WebElement servicedtailsapplybtn;
	
	@FindBy(xpath="//i[@action='notes']")
	private WebElement notesbutton;
	
	@FindBy(xpath="//div[@class='page inspections-service inspections-service-details page-on-center']")
	private WebElement servicedetailssscreen;
	
	@FindBy(xpath="//a[@action='back']/i")
	private WebElement backbtn;
	
	public VNextServiceDetailsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(notesbutton));
	}
	
	public VNextNotesScreen clickServiceNotesOption() {
		tap(notesbutton);
		testReporter.log(LogStatus.INFO, "Click service Notes option");
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickServiceDetailsBackButton() {
		tap(backbtn);
		testReporter.log(LogStatus.INFO, "Clack Service Details Back button");
	}
	
	public VNextInspectionServicesScreen clickServiceDetailsDoneButton() {
		tap(servicedtailsapplybtn);
		testReporter.log(LogStatus.INFO, "Clack Service Details Done button");
		return new VNextInspectionServicesScreen(appiumdriver);
	}

}
