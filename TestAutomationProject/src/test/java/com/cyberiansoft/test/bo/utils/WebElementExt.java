package com.cyberiansoft.test.bo.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;

public class WebElementExt extends BaseWebPage{
	
    private WebElementExt(WebDriver driver) {
		super(driver);
	}
    
	public static void clickAndWaitForLoading(WebElement el) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}
}
