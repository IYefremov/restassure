package com.cyberiansoft.test.ibs.pageobjects.webpages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    @FindBy(id="ctl00_ContentPlaceHolder1_upMain")
    private WebElement updateIbsBoProcess;

	public WebDriver driver;
	public static WebDriverWait wait;

	private static final long SLEEP_TIMEOUT_IN_SEC = 15;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(SLEEP_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(SLEEP_TIMEOUT_IN_SEC*4, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(SLEEP_TIMEOUT_IN_SEC*2, TimeUnit.SECONDS);

		wait = new WebDriverWait(driver, 15, 1);
	}

    WebElement waitForElementNotToMove(final WebElement element) {
        wait.until((ExpectedCondition<Boolean>) wDriver -> {
            Point loc = element.getLocation();
            waitABit(10000);
            return element.getLocation().equals(loc);
        });
        return element;
    }

	public String getBrowserType() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		return cap.getBrowserName().toLowerCase();
	}

	public void closeNewTab(String mainWindowHandle) {
		driver.close();
		driver.switchTo().window(mainWindowHandle);
	}

	public void moveToElement(WebElement webElement) {
		Actions actions = new Actions(driver);
		actions.moveToElement(webElement);
		actions.perform();
	}

	public void waitForNewTab() {
	    try {
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        } catch (TimeoutException e) {
	        waitABit(5000);
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        }
		waitABit(5000);
		wait.until((ExpectedCondition<Boolean>) d -> (d.getWindowHandles().size() != 1));
	}

	// Bot actions

	/* Wait For */
	public void waitABit(int milliseconds) {
		if (milliseconds > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(milliseconds);
			} catch (InterruptedException ex) {
				// Swallow exception
				ex.printStackTrace();
			}
		}
	}

    public void waitForLoading() {
        try {
            //todo
        } catch(TimeoutException ignored){
            waitABit(3000);
        }
    }

    public void waitIbsBoForUpdate() {
	    try {
            wait.until(ExpectedConditions.attributeContains(updateIbsBoProcess, "updated", "false"));
            wait.until(ExpectedConditions.attributeContains(updateIbsBoProcess, "updated", "true"));
        } catch (Exception ignored) {}
    }

    public BasePage scrollDownToText(String text) {
        WebElement element = driver.findElement(By.xpath("//*[text()='" + text + "']"));
        scrollToElement(element);
        waitABit(500);
        return this;
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public BasePage handleAlert() {
        driver.switchTo().alert().accept();
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        } catch (Exception ignored) {}
        return this;
    }
}