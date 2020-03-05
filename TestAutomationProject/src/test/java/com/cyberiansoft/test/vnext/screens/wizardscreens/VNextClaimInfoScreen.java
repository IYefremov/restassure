package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextCustomKeyboard;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextClaimInfoScreen extends VNextBaseWizardScreen {

	@FindBy(xpath="//div[@class='pages']/div[@data-page='claim']")
	private WebElement claimScreen;
	
	@FindBy(name="Estimations.PolicyNumber")
	private WebElement policyFld;
	
	@FindBy(xpath="//*[@for='estimationsInsuranceId']")
	private WebElement insuranceCompanyFld;
	
	@FindBy(name="Estimations.ClaimNumber")
	private WebElement claimFld;
	
	@FindBy(name="Estimations.Deductible")
	private WebElement deductibleFld;
	
	@FindBy(name="Estimations.AccidentDate")
	private WebElement accidentdateFld;

	@FindBy(xpath="//*[@data-autotests-id='companies-list']")
	private WebElement companiesList;

	public VNextClaimInfoScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}
	
	public void setDeductibleValue(String deductible) {
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-deductible']")));
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(deductibleFld.getAttribute("value"), deductible);
	}

	public void selectInsuranceCompany(String insuranceCompany) {
		companiesList.findElement(By.xpath(".//div[contains(text(), '" + insuranceCompany + "')]")).click();
	}
}
