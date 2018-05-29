package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class AllUsersWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable alluserstable;	
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboApplication_Input")
	private WebElement applicationsearchcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbSearch")
	private TextField usersearchfld;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_0']")
	private WebElement accauntantchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_1']")
	private WebElement administratorchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_2']")
	private WebElement clientchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_3']")
	private WebElement clientinspectorchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_4']")
	private WebElement clientmonitormanagerchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_5']")
	private WebElement clientaccauntantchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_6']")
	private WebElement salespersonmonitormanagerchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_7']")
	private WebElement salespersonchkbox;
	
	@FindBy(xpath = "//label[text()='SuperUser']")
	private WebElement superuserchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_9']")
	private WebElement technicianchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_10']")
	private WebElement workmanagerchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl02_filterer_listRoles_11']")
	private WebElement workmanagerreadonlychkbox;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")
	private WebElement findbtn;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	public AllUsersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	wait.until(ExpectedConditions.visibilityOf(alluserstable.getWrappedElement()));
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			searchbtn.click();
		}
	}
	
	public void verifyAllUsersTableColumnsAreVisible() {
		Assert.assertTrue(alluserstable.tableColumnExists("Full Name / Username / Roles"));
		Assert.assertTrue(alluserstable.tableColumnExists("Application"));
		Assert.assertTrue(alluserstable.tableColumnExists("Contact Info"));
		Assert.assertTrue(alluserstable.tableColumnExists("Accounting ID"));
	}
	
	public void verifyAllUserSearchParametersAreVisible() {
		Assert.assertTrue(applicationsearchcmb.isDisplayed());
		Assert.assertTrue(usersearchfld.isDisplayed());
		Assert.assertTrue(accauntantchkbox.isDisplayed());
		Assert.assertTrue(administratorchkbox.isDisplayed());
		Assert.assertTrue(clientchkbox.isDisplayed());
		Assert.assertTrue(clientinspectorchkbox.isDisplayed());
		Assert.assertTrue(clientmonitormanagerchkbox.isDisplayed());
		Assert.assertTrue(clientaccauntantchkbox.isDisplayed());
		Assert.assertTrue(salespersonmonitormanagerchkbox.isDisplayed());
		Assert.assertTrue(salespersonchkbox.isDisplayed());
		Assert.assertTrue(superuserchkbox.isDisplayed());
		Assert.assertTrue(technicianchkbox.isDisplayed());
		Assert.assertTrue(workmanagerchkbox.isDisplayed());
		Assert.assertTrue(workmanagerreadonlychkbox.isDisplayed());
	}
	
	public void selectSearchApplication(String _application) throws InterruptedException {
		applicationsearchcmb.click();
		applicationsearchcmb.sendKeys(_application);
//		wait.until(ExpectedConditions.visibilityOf(updateProcess));
//		wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		//Thread.sleep(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li/em[text()='" + _application + "']"))).click();
	}
	
	public void setSearchUserParameter(String _user) {
		clearAndType(usersearchfld,_user);
	}
	
	public void checkSuperUserCheckBox() {
		click(superuserchkbox);
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public int getAllUsersTableRowCount() {
		return getAllUsersTableRows().size();
	}
	
	public List<WebElement>  getAllUsersTableRows() {
		return alluserstable.getTableRows();
	}
	
	
	public boolean userExists(String username) {
		boolean exists =  alluserstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + username + "']")).size() > 0;
		return exists;
	}

}
