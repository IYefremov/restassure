package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInspectionServicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[contains(@class, 'page inspections-service hide-searchbar page-on-center')]")
	private WebElement servicesscreen;
	
	@FindBy(xpath="//i[@action='add']")
	private WebElement addservicesbtn;
	
	@FindBy(xpath="//i[@action='back']")
	private WebElement backbtn;
	
	@FindBy(xpath="//div[@class='list-block services-added']")
	private WebElement addedserviceslist;
	
	@FindBy(xpath="//div[@class='estimation-number']/span")
	private WebElement inspectionnumber;
	
	public VNextInspectionServicesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(addservicesbtn));
	}
	
	public VNextSelectServicesScreen clickAddServicesButton() {
		tap(addservicesbtn);
		testReporter.log(LogStatus.INFO, "Tap Add Services button");
		return new VNextSelectServicesScreen(appiumdriver);
	}
	
	public boolean isServiceAdded(String servicename) {
		return addedserviceslist.findElements(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")).size() > 0;
	}
	
	public VNextInspectionsScreen saveInspectionfromFirstScreen() {
		VNextVehicleInfoScreen vehicleinfoscreen = goToVehicleInfoScreen();
		vehicleinfoscreen.setVIN("1FMCU0DG4BK830800");
		vehicleinfoscreen.selectType("New");
		return saveInspectionfromVehicleInfoScreen();
	}
	
	public VNextInspectionsScreen saveInspectionfromVehicleInfoScreen() {
		swipeScreenLeft();
		swipeScreenLeft();
		swipeScreenLeft();
		swipeScreenLeft();
		swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickSaveInspectionButton();
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen goToVehicleInfoScreen() {
		waitABit(4000);
		swipeScreenLeft();
		swipeScreenLeft(); 
		swipeScreenLeft();
		swipeScreenLeft();
		swipeScreenLeft();
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public VNextInspectionsScreen clickBackButtonAndCancelInspection() {
		tap(backbtn);
		testReporter.log(LogStatus.INFO, "Tap Inspection Services screen Back button");
		VNextInformationDialog errordialog = new VNextInformationDialog(appiumdriver);
		String msg = errordialog.clickInformationDialogYesButtonAndGetMessage();
		Assert.assertEquals(msg, "Are you sure you want to cancel inspection?");
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public String getNewInspectionNumber() {
		return inspectionnumber.getText();
	}
}
