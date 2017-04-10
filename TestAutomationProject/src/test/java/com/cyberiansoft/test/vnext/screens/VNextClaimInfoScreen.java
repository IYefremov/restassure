package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextClaimInfoScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(name="Estimations.PolicyNumber")
	private WebElement policyfld;
	
	@FindBy(name="Estimations.InsuranceId")
	private WebElement insurancecompanyfld;
	
	@FindBy(name="Estimations.ClaimNumber")
	private WebElement claimfld;
	
	public VNextClaimInfoScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(policyfld));
	}
	
	public void setPolicyNumber(String policynum) {
		policyfld.clear();
		policyfld.sendKeys(policynum);
		testReporter.log(LogStatus.INFO, "Set Policy Number : " + policynum);
	}
	
	public String getPolicyNumber() {
		return policyfld.getAttribute("value");
	}
	
	public void setClaimNumber(String claimnum) {
		claimfld.clear();
		claimfld.sendKeys(claimnum);
		testReporter.log(LogStatus.INFO, "Set Claim Number : " + claimnum);
	}
	
	public String getClaimNumber() {
		return claimfld.getAttribute("value");
	}
	
	public void selectInsuranceCompany (String insuranceCompany) {
		tap(insurancecompanyfld);
		VNextBaseScreenWithListSelection listscreen = new VNextBaseScreenWithListSelection(appiumdriver);
		listscreen.selectListItem(insuranceCompany);
		testReporter.log(LogStatus.INFO, "Select Insurance Company: " + insuranceCompany);
	}
	
	public String getInsuranceCompany () {
		return insurancecompanyfld.getAttribute("value");
	}
}
