package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextDownloadDataScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@data-page='update']")
	private WebElement downloaddatapage;

    public VNextDownloadDataScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
	}
	
	public void waitUntilDatabasesDownloaded() {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 600);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" +
				VNextAlertMessages.DATA_HAS_BEEN_DOWNLOADED_SECCESSFULY + "']")));
	}

}
