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

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

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
	}
	
	public void verifySuppliesTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(suppliestable.getWrappedElement()));
		Assert.assertTrue(suppliestable.tableColumnExists("Supply"));
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
		wait.until(ExpectedConditions.visibilityOf(newsupplynamefld.getWrappedElement()));
		clearAndType(newsupplynamefld, newsupplyname);
		clickAndWait(newsupplyOKbtn);
        waitABit(3000);
    }
	
	public void clickEditButtonForSupply(String supplyname) {
		List<WebElement> questionformsrows = getSuppliesTableRows();
		for (WebElement questionformsrow : questionformsrows) {
			if (questionformsrow.getText().contains(supplyname)) {
				questionformsrow.findElement(By.xpath(".//td/input[@title='Edit']")).click();
				waitForLoading();
				break;
			}
		}
	}
	
	public void deleteSupply(String supplyname) {
		List<WebElement> questionformsrows = getSuppliesTableRows();
		for (WebElement questionformsrow : questionformsrows) {
			if (questionformsrow.getText().contains(supplyname)) {
				questionformsrow.findElement(By.xpath(".//td/input[@title='Delete']")).click();
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				alert.accept();
				waitForLoading();
				break;
			}
		}
	}
	
	public boolean isSupplyExists(String supplyname) {
		boolean exists =  suppliestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + supplyname + "']")).size() > 0;
		return exists;
	}

}
