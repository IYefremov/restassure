package com.cyberiansoft.test.bo.utils;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebElementsBot {
	
	@FindBy(className = "updateProcess")
	private static WebElement updateProcess;
	
	public static void click(WebElement element) {
	    try {
	    	new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50)
	    	.until(ExpectedConditions.elementToBeClickable(element));
	    } catch (StaleElementReferenceException | TimeoutException e) {
            waitABit(5000);
	    }
        element.click();
	    waitABit(3500);
    }
	
	public static void clickAndWait(WebElement element) {
	    try {
            new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50)
                    .until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (TimeoutException e) {
	        e.printStackTrace();
        }
        waitUntilPageReloaded();
	    waitABit(4500);
	}
	
	public static void selectComboboxValue(ComboBox combobox, DropDown droplist, String value) {
        WebDriverWait wait = clickCombobox(combobox, droplist);
		try {
		    List<WebElement> items = droplist.getWrappedElement().findElements(By.tagName("li"));
            try {
                wait.until(ExpectedConditions.visibilityOfAllElements(items));
            } catch (Exception ignored) {}
            items.stream().filter(w -> w.getText().equals(value)).findFirst().ifPresent(WebElement::click);
        } catch (Exception e) {
		    System.out.println("The value has not been found! " + e);
        }
        waitUntilDropListDisappears(droplist, wait);
    }

	public static void selectRandomComboboxValue(ComboBox combobox, DropDown droplist) {
        WebDriverWait wait = clickCombobox(combobox, droplist);
		try {
		    List<WebElement> items = droplist.getWrappedElement().findElements(By.tagName("li"));
		    wait.until(ExpectedConditions.visibilityOfAllElements(items));
            Collections.shuffle(items);
            items.stream().findFirst().ifPresent(WebElement::click);
        } catch (Exception e) {
            System.err.println("The value has not been found! " + e);
        }
        waitUntilDropListDisappears(droplist, wait);
    }

    public static void selectComboboxValueAndWait(ComboBox combobox, DropDown droplist, String value) {
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(300);
		//new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		 // .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		droplist.selectByVisibleText(value);
		waitUntilPageReloaded();
	}
	
	public static void selectComboboxValueWithTyping(TextField combobox, DropDown droplist, String value) {
        Utils.clickElement(combobox.getWrappedElement());
        WaitUtilsWebDriver.elementShouldBeVisible(droplist.getWrappedElement(), true);
        combobox.clearAndType(value);
        waitABit(1000);
        combobox.sendKeys(Keys.ENTER);
        WaitUtilsWebDriver.elementShouldBeVisible(droplist.getWrappedElement(), false);
    }
	
	public static void selectComboboxValueWithTyping(TextField combobox, DropDown droplist, String typevalue, String selectvalue) {
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		combobox.clearAndType(typevalue);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(1000);
		droplist.selectByVisibleText(selectvalue);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	//TODO
	public static void doubleselectComboboxValueWithTyping(TextField combobox, DropDown droplist, String value) {
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		combobox.clearAndType(value);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 20).until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//li[text()='" + value + "']")));
		WebElement comboitem = new WebDriverWait(DriverBuilder.getInstance().getDriver() , 20).until(ExpectedConditions.elementToBeClickable(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']"))));

		//waitUntilSelectOptionsLoaded(value);
		waitABit(500);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		//droplist.selectByVisibleText(value);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	private static void waitUntilSelectOptionsLoaded(final String value) {
		Wait<WebDriver> wait = new FluentWait<>(DriverBuilder.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(80))
                .ignoring(NoSuchElementException.class);
		wait.until(driver -> wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul/li[@class='rcbHovered']"))).getText().trim().equals(value.trim()));
    }
	
	public static void clearAndType(TextField field, final String value) {
		field.clearAndType(value);
    }
	
	public static void waitUntilPageReloaded() {
    	try {
            new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50)
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
        } catch (TimeoutException ignored) {
    	    waitABit(5000);
        }
        waitABit(1000);
    }

	/* Wait For */
    public static void waitABit(int milliseconds) {
        if (milliseconds > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException ignored) {}
        }
    }

    private static void waitUntilDropListDisappears(DropDown droplist, WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(droplist.getWrappedElement()));
        } catch (Exception ignored) {}
    }

    private static void waitUntilDropListDisappears(ComboBox comboBox, DropDown droplist, WebDriverWait wait) {
        try {
            if (!DriverBuilder.getInstance().getBrowser().contains("Edge")) {
                waitUntilDropListDisappears(droplist, wait);
            } else {
                WebDriver driver = DriverBuilder.getInstance().getDriver();
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                        comboBox.getWrappedElement(), "display", 0);
                wait.until(ExpectedConditions.attributeContains(comboBox.getWrappedElement(), "display", "0"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WebDriverWait clickCombobox(ComboBox combobox, DropDown droplist) {
        Actions act = new Actions(DriverBuilder.getInstance().getDriver());
        act.click(combobox.getWrappedElement()).perform();
        WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getDriver() , 5);
        try {
            WaitUtilsWebDriver.waitForVisibility(droplist.getWrappedElement(), 7);
        } catch (Exception e) {
            Assert.fail("The droplist has not been displayed!", e);
        }
        return wait;
    }
}
