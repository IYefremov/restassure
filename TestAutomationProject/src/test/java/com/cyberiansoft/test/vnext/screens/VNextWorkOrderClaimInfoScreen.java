package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextWorkOrderClaimInfoScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//*[@data-autotests-id='orders-claim-info-form']")
	private WebElement claiminfoform;
	
	@FindBy(name="Orders.PolicyNumber")
	private WebElement policyfld;
	
	@FindBy(name="Orders.OtherInsuranceName")
	private WebElement insurancecompanyfld;
	
	@FindBy(name="Orders.ClaimNumber")
	private WebElement claimfld;
	
	@FindBy(name="Orders.Deductible")
	private WebElement deductiblefld;
	
	@FindBy(name="oOrders.AccidentDate")
	private WebElement accidentdatefld;
	
	public VNextWorkOrderClaimInfoScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(claiminfoform));
	}
	
	public void setPolicyNumber(String policynum) {
		tap(policyfld);
		appiumdriver.getKeyboard().sendKeys(policynum);
		appiumdriver.hideKeyboard();
		log(LogStatus.INFO, "Set Policy Number : " + policynum);
	}
	
	public String getPolicyNumber() {
		return policyfld.getAttribute("value");
	}
	
	public boolean isPolicyNumberFieldVisible() {
		return policyfld.isDisplayed();
	}
	
	public void setClaimNumber(String claimnum) {
		tap(claimfld);
		appiumdriver.getKeyboard().sendKeys(claimnum);
		appiumdriver.hideKeyboard();
		log(LogStatus.INFO, "Set Claim Number : " + claimnum);
	}
	
	public String getClaimNumber() {
		return claimfld.getAttribute("value");
	}
	
	public boolean isClaimNumberFieldVisible() {
		return claimfld.isDisplayed();
	}	
	
	public void selectInsuranceCompany (String insuranceCompany) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(insurancecompanyfld));
		tap(appiumdriver.findElement(By.xpath("//*[@action='company']/a")));
		VNextBaseScreenWithListSelection listscreen = new VNextBaseScreenWithListSelection(appiumdriver);
		listscreen.selectListItem(insuranceCompany);
		log(LogStatus.INFO, "Select Insurance Company: " + insuranceCompany);
	}
	
	public String getInsuranceCompany() {
		return insurancecompanyfld.getAttribute("value");
	}
	
	public boolean isInsuranceCompanyFieldVisible() {
		return insurancecompanyfld.isDisplayed();
	}
	
	public void setDeductibleValue(String deductiblevalue) {
		tap(deductiblefld);
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(deductiblefld.getAttribute("value"), deductiblevalue);
		log(LogStatus.INFO, "Set Deductible value: " + deductiblevalue);
	}
	
	public String getDeductibleValue() {
		return deductiblefld.getAttribute("value");
	}
	
	public boolean isDeductibleFieldVisible() {
		return deductiblefld.isDisplayed();
	}
	
	public String getAccidentDateValue() {
		return accidentdatefld.getAttribute("value");
	}

}
