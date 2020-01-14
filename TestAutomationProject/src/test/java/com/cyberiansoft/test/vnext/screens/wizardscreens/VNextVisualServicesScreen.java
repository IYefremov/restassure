package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextVisualServicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='visual-services']")
	private WebElement visualservicesscreen;

    public VNextVisualServicesScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(visualservicesscreen));
	}
	
	public VNextVisualScreen selectCustomService(String servicename) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(visualservicesscreen.findElement(By.xpath(".//div[@class='service']/div[contains(text(), '" + servicename + "')]"))));
		tap(visualservicesscreen.findElement(By.xpath(".//div[@class='service']/div[contains(text(), '" + servicename + "')]")));
		return new VNextVisualScreen(appiumdriver);
	}

}
