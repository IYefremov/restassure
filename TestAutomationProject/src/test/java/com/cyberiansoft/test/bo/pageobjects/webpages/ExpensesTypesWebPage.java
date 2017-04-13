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

public class ExpensesTypesWebPage extends WebPageWithPagination {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_editions_ctl00")
	private WebTable expensestypestable;

	@FindBy(id = "ctl00_ctl00_Content_Main_editions_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addexpencetypebtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField newexpencetypefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newexpencetypeOKbtn;

	public ExpensesTypesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(expensestypestable.getWrappedElement()));
	}
	
	public void verifyExpensesTypesColumnsAreVisible() {
		
		Assert.assertTrue(expensestypestable.isTableColumnExists("Name"));
		Assert.assertTrue(expensestypestable.isTableColumnExists("Category"));
	}
	
	public List<WebElement> getExpensesTypesTableRows() {
		return expensestypestable.getTableRows();
	}
	
	public void clickAddExpenseTypeButton() {
		clickAndWait(addexpencetypebtn);
	}
	
	public void createNewExpenseType(String expensetype) {
		clickAddExpenseTypeButton();
		setExpenseTypeName(expensetype);
	}
	
	public void setExpenseTypeNewName(String expensetype, String newexpensetypename) {
		clickEditButtonForExpenseType(expensetype);
		setExpenseTypeName(newexpensetypename);
	}
	
	public void setExpenseTypeName(String newexpensetypename) {
		setNewExpenceTypeName(newexpensetypename);
		clickNewExpenceTypeOKButton();
	}
	
	public void setNewExpenceTypeName(String newexpensetypename) {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(newexpencetypefld.getWrappedElement()));
		clearAndType(newexpencetypefld, newexpensetypename);
	}
	
	public void clickNewExpenceTypeOKButton() {
		clickAndWait(newexpencetypeOKbtn);
	}
	
	public void clickEditButtonForExpenseType(String expensetype) {
		List<WebElement> qexpensetypesrows = getExpensesTypesTableRows();
		for (WebElement qexpensetypesrow : qexpensetypesrows) {
			if (qexpensetypesrow.getText().contains(expensetype)) {
				clickAndWait(qexpensetypesrow.findElement(By.xpath(".//td/input[@title='Edit']")));
				break;
			}
		}
	}
	
	public void deleteExpenseType(String expensetype) {
		List<WebElement> qexpensetypesrows = getExpensesTypesTableRows();
		for (WebElement qexpensetypesrow : qexpensetypesrows) {
			if (qexpensetypesrow.getText().contains(expensetype)) {
				click(qexpensetypesrow.findElement(By.xpath(".//td/input[@title='Delete']")));
				new WebDriverWait(driver, 10)
				  .until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				alert.accept();
				waitUntilPageReloaded();
				break;
			}
		}
	}
	
	public boolean isExpenseTypeExists(String expensetype) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  expensestypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + expensetype + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}

}
