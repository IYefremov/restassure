package com.cyberiansoft.test.bo.pageobjects.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class ServiceRequestTypesVehicleInfoSettingsPage extends BaseWebPage{

	public ServiceRequestTypesVehicleInfoSettingsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		}

	public void unselectCheckBox(String string) {
		WebElement checkBox = driver.findElement(By.xpath("//td[text()='"+string+"']")).findElement(By.xpath("..")).findElement(By.tagName("label"));
		if(checkBox.getAttribute("class").equals(" rfdCheckboxChecked")){
			checkBox.click();
		}
	}
	
	public void clickUpdateButton(){
		driver.findElement(By.id("ctl00_Content_btnUpdate")).click();	
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_lblMessage")));
	}

	public void selectCheckBox(String string) {
		WebElement checkBox = driver.findElement(By.xpath("//td[text()='"+string+"']")).findElement(By.xpath("..")).findElement(By.tagName("label"));
		if(checkBox.getAttribute("class").equals(" rfdCheckboxUnchecked")){
			checkBox.click();
		}
	}
}
