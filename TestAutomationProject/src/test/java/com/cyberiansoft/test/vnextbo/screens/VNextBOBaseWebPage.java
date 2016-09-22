package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class VNextBOBaseWebPage {
	
	public WebDriver driver;
	
	private static final long SLEEP_TIMEOUT_IN_SEC = 10;

	public VNextBOBaseWebPage(WebDriver driver) {
		this.driver = driver;
		new WebDriverWait(driver, SLEEP_TIMEOUT_IN_SEC);
	}
	
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
    	String selected = element.getAttribute("checked");
		if (selected !=null) {
			if (selected.equals("true"))
				result = true;
		}
		return result;
    }

}
