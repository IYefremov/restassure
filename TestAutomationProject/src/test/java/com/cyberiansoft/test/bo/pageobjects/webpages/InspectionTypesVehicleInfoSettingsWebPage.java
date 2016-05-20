package com.cyberiansoft.test.bo.pageobjects.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class InspectionTypesVehicleInfoSettingsWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_VinVisible")
	private WebElement vinvisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_VinRequired")
	private WebElement vinrequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_MakeVisible")
	private WebElement makevisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_MakeRequired")
	private WebElement makerequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_ModelVisible")
	private WebElement modelvisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_ModelRequired")
	private WebElement modelquiredcheckbox;
	
	@FindBy(id = "ctl00_Content_btnUpdate")
	private WebElement updatebtn;
	
	public InspectionTypesVehicleInfoSettingsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectVINVisible() {
		checkboxSelect(vinvisiblecheckbox);
	}
	
	public void selectVINRequired() {
		checkboxSelect(vinrequiredcheckbox);
	}
	
	public void unselectVINVisible() {
		checkboxUnselect(vinvisiblecheckbox);
	}
	
	public void selectMakeVisible() {
		checkboxSelect(makevisiblecheckbox);
	}
	
	public void selectMakeRequired() {
		checkboxSelect(makerequiredcheckbox);
	}
	
	public void unselectMakeVisible() {
		checkboxUnselect(makevisiblecheckbox);
	}
	
	public void selectModelVisible() {
		checkboxSelect(modelvisiblecheckbox);
	}
	
	public void selectModelRequired() {
		checkboxSelect(modelvisiblecheckbox);
	}
	
	public void unselectModelVisible() {
		checkboxUnselect(modelvisiblecheckbox);
	}
	
	public void clickUpdateButton() {
		clickAndWait(updatebtn);
		new WebDriverWait(driver, 15)
		  .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicle Info Settings have been saved']")));
	}
	
	public void checkboxSelect(WebElement checkbox) {
    	if (!isCheckboxChecked(checkbox))
    		if (getBrowserType().contains("firefox")) {
    			WebElement parent = checkbox.findElement(By.xpath("parent::*"));   			
    			parent.findElement(By.xpath(".//label")).click();
    		} else {
    			checkbox.click();
    		}
    	
    }
    
    public void checkboxUnselect(WebElement checkbox) {
    	if (isCheckboxChecked(checkbox)) 
    		if (getBrowserType().contains("firefox")) {
    			WebElement parent = checkbox.findElement(By.xpath("parent::*"));   			
    			parent.findElement(By.xpath(".//label")).click();    			
    		} else {
    			checkbox.click();
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

}
