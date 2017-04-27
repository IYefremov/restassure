package com.cyberiansoft.test.bo.utils;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.TextField;

public class WebElementsBot {
	
	@FindBy(className = "updateProcess")
	private static WebElement updateProcess;
	
	public static void click(WebElement element) {
	    try {
	    	WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(element));
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
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(element));
	    element.click();
	    waitUntilPageReloaded();
	}
	
	public static void selectComboboxValue(ComboBox combobox, DropDown droplist, String value) {
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(300);
		//new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		 // .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		droplist.selectByVisibleText(value);
		//WebDriverInstansiator.getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	public static void selectComboboxValueAndWait(ComboBox combobox, DropDown droplist, String value) {
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(300);
		//new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		 // .until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		droplist.selectByVisibleText(value);
		waitUntilPageReloaded();
	}
	
	public static void selectComboboxValueWithTyping(TextField combobox, DropDown droplist, String value) {
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		Actions act = new Actions(WebDriverInstansiator.getDriver());
		act.click(combobox.getWrappedElement()).perform();
		WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		combobox.click();
		combobox.clear();
		combobox.typeValue(value);
		//combobox.clearAndType(value);
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(droplist.getWrappedElement()));
		waitABit(2000);
		droplist.selectByVisibleText(value);
		WebDriverInstansiator.getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	public static void selectComboboxValueWithTyping(TextField combobox, DropDown droplist, String typevalue, String selectvalue) {
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		combobox.clearAndType(typevalue);
		WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(1000);
		//new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
		  //.until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		droplist.selectByVisibleText(selectvalue);
		WebDriverInstansiator.getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	//TODO
	public static void doubleselectComboboxValueWithTyping(TextField combobox, DropDown droplist, String value) {
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		combobox.click();
		combobox.clearAndType(value);
		WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable((WebElement) droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']"))));
//		WebDriverInstansiator.getUpdateWait().until(ExpectedConditions.visibilityOf(updateProcess));
//		WebDriverInstansiator.getUpdateWait().until(ExpectedConditions.invisibilityOf(updateProcess));
		//waitABit(1000);
		waitUntilSelectOptionsLoaded(droplist.getWrappedElement());
		droplist.selectByVisibleText(value);
		droplist.selectByVisibleText(value);
		WebDriverInstansiator.getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	private static void waitUntilSelectOptionsLoaded(final WebElement element) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(WebDriverInstansiator.getDriver())
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(80, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, Boolean>() {
             @Override
             public Boolean apply(WebDriver driver) {
            	    return wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("b"))).getText().equals("1");
            	  }
            	});
    }
	
	public static void clearAndType(TextField field, final String value) {
		field.clearAndType(value);
    }
	
	public static void waitUntilPageReloaded() {
    	waitABit(300);
		WebDriverInstansiator.getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
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
