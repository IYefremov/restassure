package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextVisualServicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='visual-services']")
	private WebElement visualServicesScreen;

    public VNextVisualServicesScreen() {
	}
	
	public void selectCustomService(String servicename) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(visualServicesScreen.findElement(By.xpath(".//div[@class='service']/div[contains(text(), '" + servicename + "')]"))));
		tap(visualServicesScreen.findElement(By.xpath(".//div[@class='service']/div[contains(text(), '" + servicename + "')]")));
	}

}
