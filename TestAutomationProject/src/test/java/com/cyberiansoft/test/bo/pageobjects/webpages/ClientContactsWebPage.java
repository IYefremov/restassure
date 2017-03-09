package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

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

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ClientContactsWebPage extends BaseWebPage{
	
	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert") 
	private WebElement adduserbtn;
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable clientcontactstable;

	public ClientContactsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(adduserbtn));
	}
	
	
	public AddEditClientUsersContactsDialogWebPage clickAddUserBtn(){
		clickAndWait(adduserbtn);		
		return PageFactory.initElements(
				driver, AddEditClientUsersContactsDialogWebPage.class);
	}
	
	
	public boolean isClientContactExistsInTable(String contactfstname, String contactlstname) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  clientcontactstable.getWrappedElement().
				findElements(By.xpath(".//td/b[text()='" + contactfstname + " " + contactlstname + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	
	public List<WebElement>  getClientContactsTableRows() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clientcontactstable.getWrappedElement()));
		return clientcontactstable.getTableRows();
	}
	
	
	public WebElement getTableRowWithContact(String contactfstname) {
		List<WebElement> clientstablerows = getClientContactsTableRows();
		for (WebElement clientstablerow : clientstablerows) {
			if (clientstablerow.findElement(By.xpath(".//td[" + clientcontactstable.getTableColumnIndex("Full Name / Company") + "]/b")).getText().contains(contactfstname)) {
				return clientstablerow;
			}
		}
		return null;
	}
	
	
	public AddEditClientUsersContactsDialogWebPage clickEditContactUser(String contactfstname) {
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[1]/input")).click();
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		}
		return PageFactory.initElements(
				driver, AddEditClientUsersContactsDialogWebPage.class);
	}
	
	
	
	public void clickDeleteClientContact(String contactfstname){
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[2]/input")).click();
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		}
		
		driver.switchTo().alert().accept();
		waitUntilPageReloaded();
	
	}
	
	public String getClientStatusText(String contactfstname){
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			return clientstablerow.findElement(By.xpath(".//td[5]/span")).getText();
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		} 
		return null;
	}
	
	public String getClientStatusTextColor(String contactfstname){
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			String value = clientstablerow.findElement(By.xpath(".//td[5]/span")).getAttribute("style").toLowerCase();
			return  value.substring(value.indexOf(' ') + 1, value.indexOf(';'));
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		} 
		return null;
	}
	
	
	public void waitForMessageChange(String contactfstname, String message){
		while(!getClientStatusText(contactfstname).equals(message)){
			driver.navigate().refresh();
		}
	}
	
}