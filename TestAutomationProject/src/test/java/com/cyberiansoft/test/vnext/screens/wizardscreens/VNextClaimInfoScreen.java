package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.vnext.screens.VNextCustomKeyboard;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextClaimInfoScreen extends VNextBaseWizardScreen {
	
	@FindBy(name="Estimations.PolicyNumber")
	private WebElement policyfld;
	
	@FindBy(name="Estimations.OtherInsuranceName")
	private WebElement insurancecompanyfld;
	
	@FindBy(name="Estimations.ClaimNumber")
	private WebElement claimfld;
	
	@FindBy(name="Estimations.Deductible")
	private WebElement deductiblefld;
	
	@FindBy(name="Estimations.AccidentDate")
	private WebElement accidentdatefld;
	
	public VNextClaimInfoScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(policyfld));
	}
	
	public void setPolicyNumber(String policynum) {
		tap(policyfld);
		policyfld.sendKeys(policynum);
		//appiumdriver.hideKeyboard();
	}
	
	public String getPolicyNumber() {
		return policyfld.getAttribute("value");
	}
	
	public boolean isPolicyNumberFieldVisible() {
		return policyfld.isDisplayed();
	}
	
	public void setClaimNumber(String claimnum) {
		tap(claimfld);
		claimfld.sendKeys(claimnum);
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
		insurancecompanyfld.sendKeys(insuranceCompany);

	}
	
	public String getInsuranceCompany() {
		return insurancecompanyfld.getAttribute("value");
	}
	
	public boolean isInsuranceCompanyFieldVisible() {
		return insurancecompanyfld.isDisplayed();
	}
	
	public void setDeductibleValue(String deductiblevalue) {
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-deductible']")));
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-deductible']")));
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(deductiblefld.getAttribute("value"), deductiblevalue);
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
