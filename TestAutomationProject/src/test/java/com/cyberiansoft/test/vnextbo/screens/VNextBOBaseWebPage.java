package com.cyberiansoft.test.vnextbo.screens;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public abstract class VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@class='k-loading-mask']")
    private WebElement loadingProcess;
	
	public WebDriver driver;
    public static WebDriverWait wait;
    public static WebDriverWait waitLong;
    public static WebDriverWait waitShort;

	public VNextBOBaseWebPage(WebDriver driver) {
		this.driver = driver;
        wait = new WebDriverWait(driver, 15, 1);
        waitShort = new WebDriverWait(driver, 5, 1);
        waitLong = new WebDriverWait(driver, 30, 1);
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
    	if (isCheckboxChecked(checkbox)) {
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
        }
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
    
    public void waitForNewTab() { 
		  waitLong.until(new ExpectedCondition<Boolean>(){
			  @Override
	            public Boolean apply(WebDriver d) {
	                return (d.getWindowHandles().size() != 1);
	            }
		  });
	}
    
    public void closeNewTab(String mainWindowHandle) {
		driver.close();
		driver.switchTo().window(mainWindowHandle);
	}

	public void waitForLoading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loadingProcess));
        } catch (Exception ignored) {}
        try {
            wait.until(ExpectedConditions.invisibilityOf(loadingProcess));
        } catch (Exception ignored) {
            waitABit(2000);
        }
    }

    public void executeJsForAddOnSettings() {
        ((JavascriptExecutor) driver).executeScript("localStorage.setItem('addons_selfSubscribedFeatures', JSON.stringify([{\"featureID\":17,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Labor Times\",\"reconProFeatureName\":\"LaborHoursByEmployeeReport\",\"pricePerMonth\":1,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:37:05.983\"},{\"featureID\":41,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Basic Services ($$, %%, Discounts, Taxes)\",\"reconProFeatureName\":\"BasicParts\",\"pricePerMonth\":0.99,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:40:25.92\"},{\"featureID\":37,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Question Forms\",\"reconProFeatureName\":\"AccessClinetInfo\",\"pricePerMonth\":99999999.22,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T03:39:25.673\"},{\"featureID\":38,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Configurable email templates for inspections/estimates and invoices +1234566 test long name long name\",\"reconProFeatureName\":\"WorkOrders\",\"pricePerMonth\":1.55,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-30T08:31:18.337\"},{\"featureID\":80,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"QuickBooks Online Accounting System (Additional) test test\",\"reconProFeatureName\":\"QuickBooksAccounts\",\"pricePerMonth\":25,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":271,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Basic Parts\",\"reconProFeatureName\":\"BasicParts\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:37:12.797\"},{\"featureID\":273,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Labor services\",\"reconProFeatureName\":\"LaborPriceType\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:40:02.247\"},{\"featureID\":272,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Inspection Supplements\",\"reconProFeatureName\":\"Supplements\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":297,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Splat Screens\",\"reconProFeatureName\":\"SplatScreen\",\"pricePerMonth\":15,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":388,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Parts Management - Basic\",\"reconProFeatureName\":\"Parts Management - Basic\",\"pricePerMonth\":0,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:36:59.123\"}]))");
    }

    VNextBOBaseWebPage clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        return this;
    }

    public void setAttributeWithJS(WebElement element, String attribute, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attribute, value);
        wait.until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    public void clearAndType(WebElement textField, String name) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(textField)).clear();
        } catch (Exception e) {
            Assert.fail("The text field has not been displayed", e);
        }
        wait.until(ExpectedConditions.elementToBeClickable(textField)).sendKeys(name);
    }
}
