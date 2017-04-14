package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;


public class RepairLocationUserSettingsTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_btnUpdate")
	private WebElement updatesettingsbtn;
	
	public RepairLocationUserSettingsTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public RepairLocationUserSettingsTabWebPage clickUpdateSettingButton() {
		clickAndWait(updatesettingsbtn);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='User settings have been updated for this location.']"))));
		return PageFactory.initElements(
				driver, RepairLocationUserSettingsTabWebPage.class);
	}
	
	public List<WebElement> getAllUserSettingsCheckboxes() {
		return driver.findElements(By.xpath("//label[contains(@for, 'ctl00_Content_dlSettings')]"));
	}
	
	public void verifyAllUserSettingsCheckboxesChecked() {
		List<WebElement> checkboxes = getAllUserSettingsCheckboxes();
		for (WebElement chkbox : checkboxes) {
			Assert.assertTrue(isCheckboxChecked(chkbox));
		}
	}
	
	public void verifyAllUserSettingsCheckboxesUnchecked() {
		List<WebElement> checkboxes = getAllUserSettingsCheckboxes();
		for (WebElement chkbox : checkboxes) {
			Assert.assertFalse(isCheckboxChecked(chkbox));
		}
	}
	
	public RepairLocationUserSettingsTabWebPage checkAllUserSettingsCheckboxes() {
		List<WebElement> checkboxes = getAllUserSettingsCheckboxes();
		for (WebElement chkbox : checkboxes) {
			checkboxSelect(chkbox);
		}
		return PageFactory.initElements(
				driver, RepairLocationUserSettingsTabWebPage.class);
	}
	
	public RepairLocationUserSettingsTabWebPage uncheckAllUserSettingsCheckboxes() {
		List<WebElement> checkboxes = getAllUserSettingsCheckboxes();
		for (WebElement chkbox : checkboxes) {
			checkboxUnselect(chkbox);
		}
		return PageFactory.initElements(
				driver, RepairLocationUserSettingsTabWebPage.class);
	}

}
