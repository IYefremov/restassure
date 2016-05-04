package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextVehicleInfoScreen extends VNextBaseScreen {
	
	@FindBy(name="Vehicle.VIN")
	private WebElement vinfld;
	
	@FindBy(name="Vehicle.Make")
	private WebElement makefld;
	
	@FindBy(name="Vehicle.Model")
	private WebElement modelfld;
	
	@FindBy(name="Vehicle.VehicleTypeId")
	private WebElement typefld;
	
	@FindBy(name="Vehicle.Milage")
	private WebElement milagefld;
	
	@FindBy(name="Estimations.StockNo")
	private WebElement stockfld;
	
	@FindBy(name="Estimations.RONo")
	private WebElement rofld;
	
	@FindBy(name="Vehicle.Color")
	private WebElement colorfld;
		
	@FindBy(name="Vehicle.Year")
	private WebElement yearfld;
	
	@FindBy(name="Estimations.EmployeeId")
	private WebElement techfld;
	
	public VNextVehicleInfoScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(vinfld));
	}
	
	public void setVIN (String vinnumber) {
		vinfld.clear();
		vinfld.sendKeys(vinnumber);
		testReporter.log(LogStatus.INFO, "Set VIN: " + vinnumber);
	}
	
	public String getVINFieldValue() {
		return vinfld.getAttribute("value");
	}
	
	public void selectType (String vehicletype) {
		tap(typefld);
		VNextVehicleTypeScreen vehicletypescreen = new VNextVehicleTypeScreen(appiumdriver);
		vehicletypescreen.selectType(vehicletype);
		testReporter.log(LogStatus.INFO, "Select Vehicle Type: " + vehicletype);
	}

}
