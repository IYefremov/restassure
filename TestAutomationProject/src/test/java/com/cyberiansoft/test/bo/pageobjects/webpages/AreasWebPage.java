package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class AreasWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable areastable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addareabtn;
	
	//New area
	@FindBy(xpath = "//input[contains(@id, 'Card_tbName')]")
	private TextField areanamefld;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbDesc')]")
	private TextField areadescfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newareaOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newareacancelbtn;
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;

	public AreasWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickAddAreaButton() {
		clickAndWait(addareabtn);
	}
	
	public void setNewAreaName(String areaname) {
		clearAndType(areanamefld, areaname);		
	}
	
	public void setNewAreaDescription(String areadesc) {
		clearAndType(areadescfld, areadesc);		
	}

	public void clickNewAreaOKButton() {
		clickAndWait(newareaOKbtn);
	}
	
	public void clickNewAreaCancelButton() {
		click(newareacancelbtn);
	}
	
	public void createNewArea(String areaname) {
		setNewAreaName(areaname);
		clickNewAreaOKButton();
	}
	
	public void createNewArea(String areaname, String areadesc) {
		setNewAreaName(areaname);
		setNewAreaDescription(areadesc);
		clickNewAreaOKButton();
	}
	
	public List<WebElement>  getAreasTableRows() {
		return areastable.getTableRows();
	}
	
	public WebElement getTableRowWithArea(String areaname) {
		List<WebElement> rows = getAreasTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[2]")).getText().equals(areaname)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTableAreaDescription(String areaname) {
		String areadesc = "";
		WebElement row = getTableRowWithArea(areaname);
		if (row != null) {
			areadesc = row.findElement(By.xpath(".//td[3]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + areaname + " area");
		return areadesc;
	}
	
	public boolean isAreaExists(String areaname) {
		boolean exists =  areastable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + areaname + "']")).size() > 0;
		return exists;
	}
	
	public void clickEditArea(String areaname) {
		WebElement row = getTableRowWithArea(areaname);
		if (row != null) {
			row.findElement(By.xpath(".//*[@title='Edit']")).click();
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
			//Thread.sleep(300);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else 
			Assert.assertTrue(false, "Can't find " + areaname + " area");
	}
	
	public void deleteArea(String areaname) {
		WebElement row = getTableRowWithArea(areaname);
		if (row != null) {
			row.findElement(By.xpath(".//td/input[@title='Delete']")).click();
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
			//Thread.sleep(1000);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
			//Thread.sleep(300);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else {
			Assert.assertTrue(false, "Can't find " + areaname + " area");	
		}
	}
	
	public void deleteAreaAndCancelDeleting(String areaname) {
		WebElement row = getTableRowWithArea(areaname);
		if (row != null) {
			row.findElement(By.xpath(".//td/input[@title='Delete']")).click();
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
			//Thread.sleep(1000);
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
			//Thread.sleep(300);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else {
			Assert.assertTrue(false, "Can't find " + areaname + " area");	
		}
	}
}
