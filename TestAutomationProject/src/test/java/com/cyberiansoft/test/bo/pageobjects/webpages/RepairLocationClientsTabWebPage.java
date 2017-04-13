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

import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class RepairLocationClientsTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_comboAssignClient_Input")
	private TextField locationclientscmb;
	
	@FindBy(id = "ctl00_Content_comboAssignClient_DropDown")
	private DropDown locationclientsdd;
	
	@FindBy(id = "ctl00_Content_btnAddAssignedClient")
	private WebElement locationaddclientsbtn;
	
	@FindBy(id = "ctl00_Content_gvAssignedClients_ctl00")
	private WebTable locationclientstable;
	
	@FindBy(id = "ctl00_Content_btnUpdateClients")
	private WebElement updateclientsbtn;
	
	public RepairLocationClientsTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public RepairLocationClientsTabWebPage selectRepairLocationClient(String clientname) {
		locationclientscmb.click();
		locationclientscmb.clearAndType(clientname);
		//waitABit(1000);
		//new WebDriverWait(driver, 20)
		//  .until(ExpectedConditions.visibilityOf(locationclientsdd.getWrappedElement()));
		locationclientsdd.selectByVisibleText(clientname);
		return PageFactory.initElements(
				driver, RepairLocationClientsTabWebPage.class);
	}
	
	public RepairLocationClientsTabWebPage clickAddRepairLocationClientButton() {
		clickAndWait(locationaddclientsbtn);
		return PageFactory.initElements(
				driver, RepairLocationClientsTabWebPage.class);
	}
	
	public RepairLocationClientsTabWebPage clickUpdateClientsButton() {
		clickAndWait(updateclientsbtn);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Clients have been updated for this Repair Location']"))));
		return PageFactory.initElements(
				driver, RepairLocationClientsTabWebPage.class);
	}
	
	public List<WebElement> getRepairLocationClientsTableRows() {
		return locationclientstable.getTableRows();
	}
	
	public boolean isRepairLocationClientExists(String clientname) {
		boolean exists =  locationclientstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + clientname + "']")).size() > 0;
		return exists;
	}
	
	public WebElement getTableRowWithRepairLocationClient(String clientname) {
		List<WebElement> rows = getRepairLocationClientsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[2]")).getText().equals(clientname)) {
				return row;
			}
		} 
		return null;
	}
	
	public void deleteRepairLocationClient(String clientname) {
		WebElement row = getTableRowWithRepairLocationClient(clientname);
		if (row != null) {
			clickDeleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + clientname + " repair location client");	
		}
	}

}
