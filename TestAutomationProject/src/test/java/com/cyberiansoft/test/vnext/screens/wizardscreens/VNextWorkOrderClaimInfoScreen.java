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

public class VNextWorkOrderClaimInfoScreen extends VNextBaseWizardScreen {
	
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
	
	public VNextWorkOrderClaimInfoScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(claiminfoform));
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
		//appiumdriver.hideKeyboard();
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
		wait = new WebDriverWait(appiumdriver, 10);
		WebElement insuranceCompaniesList = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='companies-list']")));
		tap(insuranceCompaniesList.findElement(By.xpath(".//*[@data-name='" + insuranceCompany + "']")));
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
