package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextDeclineReasonScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='services-add']")
	private WebElement approveservicesscreen;

    public VNextDeclineReasonScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approveservicesscreen));
	}

	public VNextDeclineReasonScreen() {
		PageFactory.initElements(appiumdriver, this);
	}


	public void selectDeclineReason(String declineReason) {
		tap(approveservicesscreen.findElement(By.
				xpath(".//ul/li[@action='select-item']/span[text()='" + declineReason + "']")));
	}

}
