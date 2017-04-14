package com.cyberiansoft.test.bo.pageobjects.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.ArrayList;
import java.util.List;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;


public class InspectionTypesVehicleInfoSettingsWebPage extends BaseWebPage {
	
	@FindBy(id = "displayedColumnsList")
	private WebElement displayedcolumns;
	
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
	private WebElement modelrequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_ColorVisible")
	private WebElement colorvisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_ColorRequired")
	private WebElement colorrequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_YearVisible")
	private WebElement yearvisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_YearRequired")
	private WebElement yearrequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_StockVisible")
	private WebElement stockvisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_StockRequired")
	private WebElement stockrequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_ROVisible")
	private WebElement rovisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_RORequired")
	private WebElement rorequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_MileageVisible")
	private WebElement mileagevisiblecheckbox;
	
	@FindBy(id = "ctl00_Content_MileageRequired")
	private WebElement mileagerequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_btnUpdate")
	private WebElement updatebtn;
	
	public InspectionTypesVehicleInfoSettingsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void selectVINVisible() {
		checkboxSelect(vinvisiblecheckbox);
	}
	
	public void selectVINRequired() {
		if (!isCheckboxChecked(makerequiredcheckbox))
			vinrequiredcheckbox.click();
	}
	
	public void unselectVINVisible() {
		checkboxUnselect(vinvisiblecheckbox);
	}
	
	public void selectMakeVisible() {
		checkboxSelect(makevisiblecheckbox);
	}
	
	public void selectMakeRequired() {
		if (!isCheckboxChecked(makerequiredcheckbox))
			makerequiredcheckbox.click();
	}
	
	public void unselectMakeVisible() {
		checkboxUnselect(makevisiblecheckbox);
	}
	
	public void selectModelVisible() {
		checkboxSelect(modelvisiblecheckbox);
	}
	
	public void selectModelRequired() {
		if (!isCheckboxChecked(modelrequiredcheckbox))
			modelrequiredcheckbox.click();
	}
	
	public void unselectModelVisible() {
		checkboxUnselect(modelvisiblecheckbox);
	}
	
	public void selectColorVisible() {
		checkboxSelect(colorvisiblecheckbox);
	}
	
	public void unselectColorVisible() {
		checkboxUnselect(colorvisiblecheckbox);
	}
	
	public void selectYearVisible() {
		checkboxSelect(yearvisiblecheckbox);
	}
	
	public void unselectYearVisible() {
		checkboxUnselect(yearvisiblecheckbox);
	}
	
	public void selectStockVisible() {
		checkboxSelect(stockvisiblecheckbox);
	}
	
	public void unselectStockVisible() {
		checkboxUnselect(stockvisiblecheckbox);
	}
	
	public void selectROVisible() {
		checkboxSelect(rovisiblecheckbox);
	}
	
	public void unselectROVisible() {
		checkboxUnselect(rovisiblecheckbox);
	}
	
	public void selectMileageVisible() {
		checkboxSelect(mileagevisiblecheckbox);
	}
	
	public void unselectMileageVisible() {
		checkboxUnselect(mileagevisiblecheckbox);
	}
	
	public void clickUpdateButton() {
		clickAndWait(updatebtn);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicle Info Settings have been saved']")));
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
    
    public List<WebElement> getDisplayedColumnsListItems() {
    	return displayedcolumns.findElements(By.xpath("./li[@class='column-item ']"));
    }

}
