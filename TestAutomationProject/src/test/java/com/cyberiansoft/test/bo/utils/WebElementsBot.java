package com.cyberiansoft.test.bo.utils;

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

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WebElementsBot {
	
	@FindBy(className = "updateProcess")
	private static WebElement updateProcess;
	
	public static void click(WebElement element) {
	    try {
	    	new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50)
	    	.until(ExpectedConditions.elementToBeClickable(element));
	        element.click();	        
	    } catch (StaleElementReferenceException sere) {
	        waitABit(4000);
	        // simply retry finding the element in the refreshed DOM
	    	element.click();
	    }
	    catch (TimeoutException toe) {
//	        test.log(logStatus.Error, "Element identified by " + by.toString() + " was not clickable after 10 seconds");
	    }
	}
	
	public static void clickAndWait(WebElement element) {
	    new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(element));
	    element.click();
	    waitUntilPageReloaded();
	}
	
	public static void selectComboboxValue(ComboBox combobox, DropDown droplist, String value){
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		try{
		combobox.click();
		}catch(Exception e){}
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		waitABit(1000);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
		droplist.getWrappedElement().findElements(By.tagName("li")).stream().map(WebElement::getText).forEach(System.out::println);
		List<WebElement> items = droplist.getWrappedElement().findElements(By.tagName("li"));
		items.stream().filter(w -> w.getText().equals(value)).findFirst().get().click();
		waitABit(1500);
		//WebDriverInstansiator.getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
//	public static void selectTimeSheetComboboxValue(ComboBox combobox, DropDown droplist, String value) {
//		WebDriverInstansiator.getWait().until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
//		try{
//		combobox.click();
//		}catch(Exception e){}
//		WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
//		waitABit(300);
//		List<WebElement> items = droplist.getWrappedElement().findElements(By.className("rcbItem"));
//		items.get(1).click();
//	//	WebDriverInstansiator.getWait().until(ExpectedConditions.visibilityOf(droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
//	//	droplist.selectByVisibleText(value);
//		WebDriverInstansiator.getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
//	}
	
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
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(combobox.getWrappedElement()));
		Actions act = new Actions(DriverBuilder.getInstance().getDriver());
		act.click(combobox.getWrappedElement()).perform();
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		combobox.click();
		combobox.clear();
		combobox.typeValue(value);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable(droplist.getWrappedElement()));
		waitABit(4000);
		droplist.selectByVisibleText(value);
		waitABit(7000);
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
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.visibilityOf(droplist.getWrappedElement()));
		WebElement comboitem = new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable((WebElement) droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']"))));

		//waitUntilSelectOptionsLoaded(value);
		waitABit(500);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.elementToBeClickable((WebElement) droplist.getWrappedElement().findElement(By.xpath(".//li[text()='" + value + "']")))).click();
		//droplist.selectByVisibleText(value);
		new WebDriverWait(DriverBuilder.getInstance().getDriver() , 50).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(droplist.getWrappedElement())));
	}
	
	private static void waitUntilSelectOptionsLoaded(final String value) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(80))
                .ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, Boolean>() {
             @Override
             public Boolean apply(WebDriver driver) {
            	    return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul/li[@class='rcbHovered']"))).getText().trim().equals(value.trim());
            	  }
            	});
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
