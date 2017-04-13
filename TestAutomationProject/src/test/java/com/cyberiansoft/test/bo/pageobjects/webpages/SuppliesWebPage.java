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

public class SuppliesWebPage extends WebPageWithPagination {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable suppliestable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addsupplybtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl00_Card_tbName")
	private TextField newsupplynamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_BtnOk")
	private WebElement newsupplyOKbtn;

	public SuppliesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void verifySuppliesTableColumnsAreVisible() {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(suppliestable.getWrappedElement()));
		Assert.assertTrue(suppliestable.isTableColumnExists("Supply"));
	}
	
	public List<WebElement> getSuppliesTableRows() {
		return suppliestable.getTableRows();
	}
	
	public void clickAddSupplyButton() {
		clickAndWait(addsupplybtn);
	}
	
	public void createNewSupply(String supplyname) {
		clickAddSupplyButton();
		setSupplyName(supplyname);
	}
	
	public void setSupplyNewName(String supplyname, String newsupplyname) {
		clickEditButtonForSupply(supplyname);
		setSupplyName(newsupplyname);
	}
	
	public void setSupplyName(String newsupplyname) {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(newsupplynamefld.getWrappedElement()));
		clearAndType(newsupplynamefld, newsupplyname);
		clickAndWait(newsupplyOKbtn);
	}
	
	public void clickEditButtonForSupply(String supplyname) {
		List<WebElement> questionformsrows = getSuppliesTableRows();
		for (WebElement questionformsrow : questionformsrows) {
			if (questionformsrow.getText().contains(supplyname)) {
				questionformsrow.findElement(By.xpath(".//td/input[@title='Edit']")).click();
				waitUntilPageReloaded();
				break;
			}
		}
	}
	
	public void deleteSupply(String supplyname) {
		List<WebElement> questionformsrows = getSuppliesTableRows();
		for (WebElement questionformsrow : questionformsrows) {
			if (questionformsrow.getText().contains(supplyname)) {
				questionformsrow.findElement(By.xpath(".//td/input[@title='Delete']")).click();
				new WebDriverWait(driver, 10)
				  .until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				alert.accept();
				waitUntilPageReloaded();
				break;
			}
		}
	}
	
	public boolean isSupplyExists(String supplyname) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  suppliestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + supplyname + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}

}
