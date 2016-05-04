package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInspectionsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addinspectionbtn;
	
	@FindBy(xpath="//div[@class='list-block list-block-search searchbar-found virtual-list']")
	private WebElement inspectionslist;
	
	@FindBy(xpath="//a[@class='link icon-only back']/i")
	private WebElement backbtn;
	
	public VNextInspectionsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(addinspectionbtn));
	}
	
	public VNextCustomersScreen clickAddInspectionButton() {
		waitABit(2000);		
		tap(addinspectionbtn);
		testReporter.log(LogStatus.INFO, "Tap Add inspection button");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		testReporter.log(LogStatus.INFO, "Tap Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public void createSimpleInspection() {	
		VNextCustomersScreen customersscreen = clickAddInspectionButton();
		customersscreen.selectCustomer("Test 2");
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		waitABit(4000);
		inspservicesscreen.saveInspectionfromFirstScreen();
	}
	
	public String getFirstInspectionNumber() {
		return inspectionslist.findElement(By.xpath(".//div[@class='item-title']")).getText();
	}
	
	public VNextInspectionServicesScreen clickOnInspectionByInspNumber(String inspnumber) {
		tap(inspectionslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + inspnumber + "']")));
		testReporter.log(LogStatus.INFO, "Tap on Inspection: " + inspnumber);
		return new VNextInspectionServicesScreen(appiumdriver);
	}
}
