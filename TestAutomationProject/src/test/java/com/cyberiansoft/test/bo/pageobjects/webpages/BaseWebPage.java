package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

//import com.cyberiansoft.test.bo.utils.WebDriverExt;
//import lombok.experimental.ExtensionMethod;
//
//@ExtensionMethod(WebDriverExt.class)
public abstract class BaseWebPage {

	public WebDriver driver;
	public static WebDriverWait wait;
	public WebDriverWait updateWait;

	private static final long SLEEP_TIMEOUT_IN_SEC = 15;

	public BaseWebPage(WebDriver driver) {
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(SLEEP_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(SLEEP_TIMEOUT_IN_SEC*4, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(SLEEP_TIMEOUT_IN_SEC*2, TimeUnit.SECONDS);

		wait = new WebDriverWait(driver, 15);
		updateWait = new WebDriverWait(driver , 40 , 1);
	}

	protected WebElement waitUntilElementIsClickable(final WebElement parent, final By locator) {
		final WebElement childElement = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver input) {
				return parent.findElement(locator);
			}
		});

		moveToElement(childElement);

		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return childElement.isEnabled();
			}
		});

		return childElement;
	}

	protected WebElement waitUntilElementIsClickable(final WebElement element) {
		final WebElement childElement = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver input) {
				return element;
			}
		});
		moveToElement(element);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return childElement.isEnabled();
			}
		});

		return childElement;
	}

	public String getBrowserType() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		return cap.getBrowserName().toLowerCase();
	}

	public void closeNewTab(String mainWindowHandle) {
		driver.close();
		driver.switchTo().window(mainWindowHandle);
	}

	private void moveToElement(WebElement webElement) {
		Actions actions = new Actions(driver);
		actions.moveToElement(webElement);
		actions.perform();
	}

	public void waitForNewTab() {
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return (d.getWindowHandles().size() != 1);
			}
		});
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

	public void clickDeleteTableRow(WebElement row) {
		row.findElement(By.xpath(".//td/input[@title='Delete']")).click();
	waitABit(1000);
	}

	public void clickArchiveTableRow(WebElement row) {
		row.findElement(By.xpath(".//*[@title='Archive']")).click();
		waitABit(1000);
	}

	public void clickRestoreTableRow(WebElement row) {
		row.findElement(By.xpath(".//*[@title='Restore']")).click();
		waitABit(1000);
	}

	public void clickApproveMarkerTableRow(WebElement row) {
		row.findElement(By.xpath(".//img[@alt='New' and @title='New']")).click();
		waitABit(1000);
	}

	public void acceptAlertAndWait() {
		acceptAlert();
		waitUntilPageReloaded();
	}

	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public void dismissAlertAndWait() {
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		waitUntilPageReloaded();
	}

	public void waitUntilPageReloaded() {
		waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public void deleteTableRow(WebElement row) {
		clickDeleteTableRow(row);
		try{
		acceptAlertAndWait();
		}catch(NoAlertPresentException e){}
	}

	public void archiveTableRow(WebElement row) {
		clickArchiveTableRow(row);
		acceptAlertAndWait();
	}

	public void restoreTableRow(WebElement row) {
		clickRestoreTableRow(row);
		acceptAlertAndWait();
	}

	public void cancelDeletingTableRow(WebElement row) {
		clickDeleteTableRow(row);
		dismissAlertAndWait();
	}

	public void clickEditTableRow(WebElement row) {
		row.findElement(By.xpath(".//*[@title='Edit']")).click();
		waitUntilPageReloaded();
	}

	public void checkboxSelect(String checkboxvalue) {
		waitABit(3000);
		wait.until(ExpectedConditions.elementToBeClickable(
				(WebElement) driver.findElement(By.xpath("//label[text()='" + checkboxvalue + "']")))).click();
	}

	public void checkboxSelect(WebElement checkbox) {
		if (!isCheckboxChecked(checkbox))
			checkbox.click();
	}

	public void checkboxUnselect(WebElement checkbox) {
		if (isCheckboxChecked(checkbox))
			checkbox.click();
	}

	public boolean isCheckboxChecked(WebElement element) {
		boolean result = false;
		WebElement parent = element.findElement(By.xpath("parent::*"));

		String selected = parent.findElement(By.xpath(".//input")).getAttribute("checked");
		if (selected != null) {
			if (selected.equals("true"))
				result = true;
		}
		return result;
	}

	public void labeledCheckBoxSelect(WebElement checkbox) {
		if (!isCheckboxChecked(checkbox))
			if (getBrowserType().contains("firefox")) {
				WebElement parent = checkbox.findElement(By.xpath("parent::*"));
				parent.findElement(By.xpath("./label")).click();
			} else {
				checkbox.click();
			}

	}

}
