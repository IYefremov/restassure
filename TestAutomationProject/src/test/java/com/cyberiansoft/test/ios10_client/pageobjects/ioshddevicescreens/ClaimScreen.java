package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class ClaimScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Accident date\"]/UIAButton[@name=\"custom detail button\"]")
    private IOSElement accidentdateselectbtn;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Insurance Company\"]/UIAButton[@name=\"custom detail button\"]")
    private IOSElement insurancecompanyselectbtn;
	
	@iOSFindBy(xpath = "//UIAElement[@name=\"InsuranceSelectorVCSearchBar\"]/UIASearchBar[1]")
    private IOSElement insurancecompanysearchbar;
	
	@iOSFindBy(xpath = "//UIATableView[@name=\"ClaimInfoViewTable\"]/UIATableCell[@name=\"Claim#\"]/UIAStaticText[@name=\"Claim#\"]")
    private IOSElement claimfld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Deductible\"]/UIAStaticText[2]")
    private IOSElement deductiblefld;
	
	@iOSFindBy(accessibility  = "Done")
    private IOSElement donebtn;
	
	public ClaimScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void selectInsuranceCompany(String insurancecompany) {
		insurancecompanyselectbtn.click();
		insurancecompanysearchbar.setValue(insurancecompany);
		appiumdriver.findElementByXPath("//UIATableView[@name=\"InsuranceSelectorVCTable\"]/UIATableCell[@name=\""+ insurancecompany + "\"]").click();
	}

	public void setClaim(String claim) throws InterruptedException {
		claimfld.click();
		Helpers.keyboadrType(claim + "\n");

	}
	
	public void selectInsuranceCompanyAndSetClaim(String insurancecompany, String claim) throws InterruptedException {
		selectInsuranceCompany(insurancecompany);
		setClaim(claim);
	}
	

	public void setDeductible(String deductible)
			throws InterruptedException {

		deductiblefld.click();
		Helpers.keyboadrType(deductible + "\n");
	}

	public String getDeductibleValue() {
		return deductiblefld.getAttribute("name");
	}

	public void setAccidentDate() {
		accidentdateselectbtn.click();
		donebtn.click();
	}
	
	public String clickSaveWithAlert() {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}

}
