package com.cyberiansoft.test.bo.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.google.common.base.Predicate;

public class WebElementsBot {
	
	public static void click(WebElement element) {
	    try {
	        (new WebDriverWait(WebDriverInstansiator.getDriver(), 10)).until(ExpectedConditions.elementToBeClickable(element));
	        element.click();	        
	    } catch (StaleElementReferenceException sere) {
	        // simply retry finding the element in the refreshed DOM
	    	element.click();
	    }
	    catch (TimeoutException toe) {
	        //test.log(logStatus.Error, "Element identified by " + by.toString() + " was not clickable after 10 seconds");
	    }
	}
	
	public static void clickAndWait(WebElement element) {
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10).until(ExpectedConditions.elementToBeClickable(element));
	    element.click();
	    waitUntilPageReloaded();
	}
	
	public static void selectComboboxValue(ComboBox combobox, DropDown droplist, String value) {
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(300);
		//new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		 // .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		droplist.selectByVisibleText(value);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	public static void selectComboboxValueAndWait(ComboBox combobox, DropDown droplist, String value) {
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(300);
		//new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		 // .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		droplist.selectByVisibleText(value);
		waitUntilPageReloaded();
	}
	
	public static void selectComboboxValueWithTyping(TextField combobox, DropDown droplist, String value) {
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		Actions act = new Actions(WebDriverInstansiator.getDriver());
		act.click(combobox.getWrappedElement()).perform();
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		combobox.clearAndType(value);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(1000);
		droplist.selectByVisibleText(value);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	public static void selectComboboxValueWithTyping(TextField combobox, DropDown droplist, String typevalue, String selectvalue) {
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		combobox.clearAndType(typevalue);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(1000);
		//new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  //.until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		droplist.selectByVisibleText(selectvalue);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	public static void doubleselectComboboxValueWithTyping(TextField combobox, DropDown droplist, String value) {
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		combobox.clearAndType(value);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
		  .until(ExpectedConditions.elementToBeClickable((WebElement) droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']"))));
		//waitABit(1000);
		waitUntilSelectOptionsLoaded(droplist.getWrappedElement());
		droplist.selectByVisibleText(value);
		droplist.selectByVisibleText(value);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  .until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	private static void waitUntilSelectOptionsLoaded(final WebElement element) {
        new FluentWait<WebDriver>(WebDriverInstansiator.getDriver())
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(10, TimeUnit.MILLISECONDS)
                .until(new Predicate<WebDriver>() {
                    public boolean apply(WebDriver d) {
                        return (element.findElements(By.xpath(".//li")).size() == 1);
                    }
                });
    }
	
	public static void clearAndType(TextField field, final String value) {
		field.clearAndType(value);
    }
	
	public static void waitUntilPageReloaded() {
    	waitABit(300);
		new WebDriverWait(WebDriverInstansiator.getDriver(), 30)
		  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
    }
	
	/* Wait For */
    public static void waitABit(int milliseconds) {
        if (milliseconds > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException ex) {
                // Swallow exception
                ex.printStackTrace();
            }
        }
    }
}
