package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextBOInspectionsWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//ul[@data-automation-id='inspectionsList']")
	private WebElement inspectionslist;
	
	@FindBy(xpath = "//div[@class='entity-details__content']")
	private WebElement inspectiondetailspanel;
	
	@FindBy(xpath = "//table[@data-automation-id='inspectionsDetailsServicesList']")
	private WebElement inspectionserviceslist;
	
	@FindBy(xpath = "//ul[@data-automation-id='inspectionsDetailsDamagesList']")
	private WebElement imagelegend;
	
	@FindBy(xpath = "//button[@data-automation-id='inspectionsDetailsPrintButton']")
	private WebElement printinspectionicon;
	
	public VNextBOInspectionsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(inspectionslist));
	}
	
	public void selectInspectionInTheList(String inspnumber) {
		inspectionslist.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b[text()='" + inspnumber + "']")).click();
		waitABit(4000);
	}
		
	public boolean isServicePresentForSelectedInspection(String servicename) {
		return inspectionserviceslist.findElements(By.xpath("./tbody/tr/td[text()='" + servicename + "']")).size() > 0;
	}
	
	public boolean isImageLegendContainsBreakageIcon(String brackageicontype) {
		return imagelegend.findElements(By.xpath("./li[contains(text(), '" + brackageicontype + "')]")).size() > 0;
	}
	
	public String getSelectedInspectionCustomerName() {
		return inspectiondetailspanel.findElement(By.xpath(".//span[@data-bind='text:clientName']")).getText();
	}
	
	public String getSelectedInspectionTotalAmauntValue() {
		return inspectiondetailspanel.findElement(By.xpath(".//th[@data-bind='text: amount']")).getText();
	}
	
	public VNextBOInspectionInfoWebPage clickSelectedInspectionPrintIcon() {
		String mainWindowHandle = driver.getWindowHandle();
		printinspectionicon.click();
		waitForNewTab();
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(
			driver, VNextBOInspectionInfoWebPage.class);
	}
	

}
