package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularClaimScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Accident Date\"]/UIAButton[@name=\"custom detail button\"]")
    private IOSElement accidentdateselectbtn;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Insurance Company\"]/UIAButton[@name=\"custom detail button\"]")
    private IOSElement insurancecompanyselectbtn;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Claim#\"]")
    private IOSElement claimfld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Deductible\"]/UIATextField[1]")
    private IOSElement deductiblefld;
	
	@iOSFindBy(name = "Done")
    private IOSElement donebtn;
	
	public RegularClaimScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectInsuranceCompany(String insurancecompany) {
		insurancecompanyselectbtn.click();
		Helpers.scroolTo(insurancecompany);
		appiumdriver.findElementByName(insurancecompany).click();
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
		return deductiblefld.getAttribute("value");
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
