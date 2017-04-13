package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class RepairLocationManagersTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_ddlTeam_Input")
	private ComboBox teamcmb;
	
	@FindBy(id = "ctl00_Content_ddlTeam_DropDown")
	private DropDown teamdd;
	
	@FindBy(id = "ctl00_Content_comboEmployee_Input")
	private TextField employeecmb;
	
	@FindBy(id = "ctl00_Content_comboEmployee_DropDown")
	private DropDown employeedd;
	
	@FindBy(id = "ctl00_Content_btnAddEmployee")
	private WebElement addemployeebtn;
	
	@FindBy(id = "ctl00_Content_btnUpdate")
	private WebElement updateemployeesbtn;
	
	@FindBy(id = "ctl00_Content_gvEmployees_ctl00")
	private WebTable managerstable;
	
	public RepairLocationManagersTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public List<WebElement> getManagersTableRows() {
		return managerstable.getTableRows();
	}
	
	public WebElement getTableRowWithManager(String employee) {
		List<WebElement> rows = getManagersTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[2]")).getText().equals(employee)) {
				return row;
			}
		} 
		return null;
	}
	
	public RepairLocationManagersTabWebPage selectTeam(String team) {
		selectComboboxValue(teamcmb, teamdd, team);
		return PageFactory.initElements(
				driver, RepairLocationManagersTabWebPage.class);
	}
	
	public RepairLocationManagersTabWebPage selectRepairLocationManager(String employee) {
		employeecmb.click();
		employeecmb.clearAndType(employee);
		//waitABit(1000);
		employeedd.selectByVisibleText(employee);
		return PageFactory.initElements(
				driver, RepairLocationManagersTabWebPage.class);
	}
	
	public RepairLocationManagersTabWebPage clickAddManagerButton() {
		clickAndWait(addemployeebtn);
		return PageFactory.initElements(
				driver, RepairLocationManagersTabWebPage.class);
	}
	
	public RepairLocationManagersTabWebPage clickUpdateManagersButton() {
		clickAndWait(updateemployeesbtn);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Managers have been updated for this Repair Location']"))));
		return PageFactory.initElements(
				driver, RepairLocationManagersTabWebPage.class);
	}
	
	public boolean isRepairLocationManagerExists(String employee) {
		boolean exists =  managerstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + employee + "']")).size() > 0;
		return exists;
	}
	
	public void deleteRepairLocationManager(String employee) {
		WebElement row = getTableRowWithManager(employee);
		if (row != null) {
			clickDeleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + employee + " repair location manager");	
		}
	}

}
