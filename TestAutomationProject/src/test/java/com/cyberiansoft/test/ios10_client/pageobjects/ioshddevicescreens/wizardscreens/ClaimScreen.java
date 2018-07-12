package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class ClaimScreen extends BaseWizardScreen {
	
	
	/*@iOSFindBy(className = "XCUIElementTypeSearchField")
    private IOSElement insurancecompanysearchbar;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Claim#\"]")
    private IOSElement claimfld;
	
	@iOSFindBy(accessibility = "Deductible")
    private IOSElement deductiblefld;
	
	@iOSFindBy(accessibility  = "Done")
    private IOSElement donebtn;*/
	
	public ClaimScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void selectInsuranceCompany(String insurancecompany) {
		IOSElement insurancecompanytable = (IOSElement) appiumdriver.findElementByAccessibilityId("ClaimInfoViewTable");
		//WebElement par = insurancecompanytable.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='ClaimInfoViewTable']/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='Insurance Company']/.."));		
		//par.findElement(MobileBy.xpath("//XCUIElementTypeButton[@name='custom detail button'] ")).click();
		WebElement par = insurancecompanytable.findElement(MobileBy.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@value='Insurance Company']/.."));		
		par.findElement(MobileBy.name("custom detail button")).click();
		((IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeSearchField")).setValue(insurancecompany);
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = '"+ insurancecompany + "' and type = 'XCUIElementTypeCell'")).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name=\""+ insurancecompany + "\"]").click();
	}

	public void setClaim(String claim) throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Claim#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(claim);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);

	}
	
	public void selectInsuranceCompanyAndSetClaim(String insurancecompany, String claim) throws InterruptedException {
		selectInsuranceCompany(insurancecompany);
		setClaim(claim);
	}
	

	public void setDeductible(String deductible)
			throws InterruptedException {

		appiumdriver.findElementByAccessibilityId("Deductible").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(deductible);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
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
