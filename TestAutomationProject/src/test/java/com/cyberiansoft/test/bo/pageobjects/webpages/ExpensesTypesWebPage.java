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
	}
	
	public void verifyExpensesTypesColumnsAreVisible() {
		
		Assert.assertTrue(expensestypestable.tableColumnExists("Name"));
		Assert.assertTrue(expensestypestable.tableColumnExists("Category"));
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
		wait.until(ExpectedConditions.visibilityOf(newexpencetypefld.getWrappedElement()));
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
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				alert.accept();
				waitForLoading();
				break;
			}
		}
	}
	
	public boolean isExpenseTypeExists(String expensetype) {
		return expensestypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + expensetype + "']")).size() > 0;
	}

}
