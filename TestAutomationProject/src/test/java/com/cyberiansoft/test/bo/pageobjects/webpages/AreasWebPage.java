package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

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

	public AreasWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  areastable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + areaname + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public void clickEditArea(String areaname) throws InterruptedException {
		WebElement row = getTableRowWithArea(areaname);
		if (row != null) {
			row.findElement(By.xpath(".//*[@title='Edit']")).click();
			Thread.sleep(300);
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else 
			Assert.assertTrue(false, "Can't find " + areaname + " area");
	}
	
	public void deleteArea(String areaname) throws InterruptedException {
		WebElement row = getTableRowWithArea(areaname);
		if (row != null) {
			row.findElement(By.xpath(".//td/input[@title='Delete']")).click();
			Thread.sleep(1000);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Thread.sleep(300);
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else {
			Assert.assertTrue(false, "Can't find " + areaname + " area");	
		}
	}
	
	public void deleteAreaAndCancelDeleting(String areaname) throws InterruptedException {
		WebElement row = getTableRowWithArea(areaname);
		if (row != null) {
			row.findElement(By.xpath(".//td/input[@title='Delete']")).click();
			Thread.sleep(1000);
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			Thread.sleep(300);
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else {
			Assert.assertTrue(false, "Can't find " + areaname + " area");	
		}
	}
}
