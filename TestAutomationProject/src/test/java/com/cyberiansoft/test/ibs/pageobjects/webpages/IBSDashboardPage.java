package com.cyberiansoft.test.ibs.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IBSDashboardPage extends BasePage {
	
	@FindBy(xpath = "//div[@class='dashboard-page container']")
	private WebElement dashboardpage;

	@FindBy(xpath = "//a[@class='link pull-right btn-log-off']")
	private WebElement logoutButton;
	
	public IBSDashboardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public IBSLoginWebPage clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        waitForLoading();
	    wait.until(ExpectedConditions.titleContains("Login"));
	    return PageFactory.initElements(driver, IBSLoginWebPage.class);
    }
}
