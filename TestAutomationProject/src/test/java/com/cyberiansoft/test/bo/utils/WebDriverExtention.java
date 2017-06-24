package com.cyberiansoft.test.bo.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;

public class WebDriverExtention extends BaseWebPage{

	public WebDriverExtention(WebDriver driver) {
		super(driver);
	}

	public void waitForLoadingOnPage(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}
}
