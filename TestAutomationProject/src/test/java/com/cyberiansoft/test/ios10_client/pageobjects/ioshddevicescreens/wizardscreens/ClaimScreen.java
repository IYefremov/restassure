package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClaimScreen extends BaseWizardScreen {
	
	
	/*@iOSXCUITFindByclassName = "XCUIElementTypeSearchField")
    private IOSElement insurancecompanysearchbar;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Claim#\"]")
    private IOSElement claimfld;
	
	@iOSXCUITFindBy(accessibility = "Deductible")
    private IOSElement deductiblefld;
	
	@iOSXCUITFindBy(accessibility  = "Done")
    private IOSElement donebtn;*/

	@iOSXCUITFindBy(accessibility = "ClaimInfoCellTypePolicyNumber")
	private IOSElement policyNumberCell;
	
	public ClaimScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitClaimScreenLoad() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'ClaimInfoForm'")));
	}

	public void selectInsuranceCompany(String insurancecompany) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ClaimInfoViewTable")));
		IOSElement insurancecompanytable = (IOSElement) appiumdriver.findElementByAccessibilityId("ClaimInfoViewTable");
		WebElement par = insurancecompanytable.findElement(MobileBy.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@value='Insurance Company']/.."));
		par.findElement(MobileBy.name("custom detail button")).click();
		((IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeSearchField")).setValue(insurancecompany);
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = '"+ insurancecompany + "' and type = 'XCUIElementTypeCell'")).click();
	}

	public void setClaim(String claim) {
		appiumdriver.findElementByAccessibilityId("Claim#").click();
		appiumdriver.findElementByAccessibilityId("Claim#").sendKeys(claim + "\n");
		Helpers.waitABit(500);

	}
	
	public void selectInsuranceCompanyAndSetClaim(String insurancecompany, String claim)  {
		selectInsuranceCompany(insurancecompany);
		setClaim(claim);
	}

	public void setPolicy(String policyNumber) {
		policyNumberCell.findElementByClassName("XCUIElementTypeTextField").sendKeys(policyNumber + "\n");
	}

	public void setDeductible(String deductible) {

		appiumdriver.findElementByAccessibilityId("Deductible").click();
		appiumdriver.findElementByAccessibilityId("Deductible").sendKeys(deductible + "\n");
		Helpers.waitABit(500);
	}

	public String getDeductibleValue() {
		return appiumdriver.
				findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[4]/XCUIElementTypeStaticText[2]").getAttribute("value");
	}

	public void setAccidentDate() {
		IOSElement insurancecompanytable = (IOSElement) appiumdriver.findElementByAccessibilityId("ClaimInfoViewTable");
		//WebElement par = insurancecompanytable.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='ClaimInfoViewTable']/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='Insurance Company']/.."));		
		//par.findElement(MobileBy.xpath("//XCUIElementTypeButton[@name='custom detail button'] ")).click();
		WebElement par = insurancecompanytable.findElement(MobileBy.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@value='Accident date']/.."));		
		par.findElement(MobileBy.name("custom detail button")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String clickSaveWithAlert() {
		clickSave();
		//appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		return Helpers.getAlertTextAndAccept();
	}

}
