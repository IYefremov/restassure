package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularClaimScreen extends RegularBaseWizardScreen {

	@iOSXCUITFindBy(accessibility = "ClaimInfoView")
	private IOSElement claimInfoView;

	@iOSXCUITFindBy(accessibility = "ClaimInfoCellTypeInsurance")
	private IOSElement insuranceCompanyCell;

	@iOSXCUITFindBy(accessibility = "ClaimInfoCellTypeClaimNumber")
	private IOSElement claimNumberCell;

	@iOSXCUITFindBy(accessibility = "ClaimInfoCellTypePolicyNumber")
	private IOSElement policyNumberCell;

	@iOSXCUITFindBy(accessibility = "ClaimInfoCellTypeDeductible")
	private IOSElement deductibleCell;

	@iOSXCUITFindBy(accessibility = "ClaimInfoCellTypeAccidentDate")
	private IOSElement accidentDatCell;

	@iOSXCUITFindBy(accessibility = "InsuranceSelectorViewTable")
	private IOSElement insuranceSelectorViewTable;
	
	public RegularClaimScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitClaimScreenLoad() {
		WaitUtils.elementShouldBeVisible(claimInfoView, true);
	}
	
	public void selectInsuranceCompany(String insurancecompany) {
		insuranceCompanyCell.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		if (!insuranceSelectorViewTable.findElementByAccessibilityId(insurancecompany).isDisplayed())
			swipeToElement(insuranceSelectorViewTable.findElementByAccessibilityId(insurancecompany));
		insuranceSelectorViewTable.findElementByAccessibilityId(insurancecompany).click();
	}

	public void setClaim(String claim) {
		claimNumberCell.findElementByClassName("XCUIElementTypeTextField").sendKeys(claim + "\n");
	}

	public void setPolicy(String policyNumber) {
		policyNumberCell.findElementByClassName("XCUIElementTypeTextField").sendKeys(policyNumber + "\n");
	}
	
	public void selectInsuranceCompanyAndSetClaim(String insurancecompany, String claim) {
		selectInsuranceCompany(insurancecompany);
		setClaim(claim);
	}

	public void setDeductible(String deductible) {
		deductibleCell.findElementByClassName("XCUIElementTypeTextField").sendKeys(deductible + "\n");
	}

	public String getDeductibleValue() {
		return deductibleCell.findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}

	public void setAccidentDate() {
		accidentDatCell.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String clickSaveWithAlert() {
		clickSave();
		return Helpers.getAlertTextAndAccept();
	}
}
