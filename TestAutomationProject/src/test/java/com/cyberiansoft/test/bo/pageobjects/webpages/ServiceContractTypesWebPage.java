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

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ServiceContractTypesWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable servicecontracttypestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addservicecontacttypetn;
	
	//New Service Contract Type
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField newservicecontacttypenamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbDesc")
	private TextField newservicecontacttypedescfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingId")
	private TextField newservicecontacttypeaccidfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingId2")
	private TextField newservicecontacttypeaccid2fld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbPrice")
	private TextField newservicecontacttypepricefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbSalesPrice")
	private TextField newservicecontacttypesalespricefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newservicecontacttypeOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newservicecontacttypecancelbtn;
	
	public ServiceContractTypesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void waitServiceContractTypesPageIsLoaded() { 
		waitABit(1000);
		new WebDriverWait(driver, 60)
		  .until(ExpectedConditions.visibilityOf(servicecontracttypestable.getWrappedElement()));
		Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='Service Contract Types']")).isDisplayed());
	}
	
	public String getTableServiceContractTypeDescription(String servicecontacttype) {
		String servicecontracttypedesc = "";
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			servicecontracttypedesc = row.findElement(By.xpath(".//td[5]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");
		return servicecontracttypedesc;
	}
	
	public String getTableServiceContractTypePrice(String servicecontacttype) {
		String servicecontracttypeprice = "";
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			servicecontracttypeprice = row.findElement(By.xpath(".//td[6]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");
		return servicecontracttypeprice;
	}
	
	public String getTableServiceContractTypeSalesPrice(String servicecontacttype) {
		String servicecontracttypesalesprice = "";
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			servicecontracttypesalesprice = row.findElement(By.xpath(".//td[7]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");
		return servicecontracttypesalesprice;
	}
	
	public String getTableServiceContractTypeAccID(String servicecontacttype) {
		String servicecontracttypeaccid = "";
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			servicecontracttypeaccid = row.findElement(By.xpath(".//td[8]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");
		return servicecontracttypeaccid;
	}
	
	public String getTableServiceContractTypeAccID2(String servicecontacttype) {
		String servicecontracttypeaccid2 = "";
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			servicecontracttypeaccid2 = row.findElement(By.xpath(".//td[9]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");
		return servicecontracttypeaccid2;
	}
	
	public void clickAddServiceContractTypeButton() {
		click(addservicecontacttypetn);
	}
	
	public int getServiceContractTypesTableRowsCount() {
		return getServiceContractTypesTableRows().size();
	}
	
	public List<WebElement>  getServiceContractTypesTableRows() {
		return servicecontracttypestable.getTableRows();
	}
	
	public WebElement getTableRowWithServiceContractType(String servicecontacttype) {
		List<WebElement> rows = getServiceContractTypesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[4]")).getText().equals(servicecontacttype)) {
				return row;
			}
		} 
		return null;
	}
	
	public void createNewServiceContractType(String servicecontracttypename) throws InterruptedException {
		setNewServiceContractTypeName(servicecontracttypename);
		clickNewServiceContractTypeOKButton();
	}
	
	public boolean isServiceContractTypeExists(String servicecontacttype) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  servicecontracttypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + servicecontacttype + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public void clickEditServiceContractType(String servicecontacttype) throws InterruptedException {
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");
	}
	
	public void deleteServiceContractType(String servicecontacttype) throws InterruptedException {
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");		
	}
	
	public void deleteServiceContractTypeAndCancelDeleting(String servicecontacttype) throws InterruptedException {
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");		
	}
	
	public void setNewServiceContractTypeName(String servicecontracttypename) {
		clearAndType(newservicecontacttypenamefld, servicecontracttypename);
	}
	
	public void setNewServiceContractTypeDescription(String servicecontracttypedesc) {
		clearAndType(newservicecontacttypedescfld, servicecontracttypedesc);
	}
	
	public void setNewServiceContractTypeAccountingID(String accid) {
		clearAndType(newservicecontacttypeaccidfld, accid);
	}
	
	public void setNewServiceContractTypeAccountingID2(String accid2) {
		clearAndType(newservicecontacttypeaccid2fld, accid2);
	}
	
	public void setNewServiceContractTypePrice(String price) {
		clearAndType(newservicecontacttypepricefld, price);
	}
	
	public void setNewServiceContractTypeSalesPrice(String salesprice) {
		clearAndType(newservicecontacttypesalespricefld, salesprice);
	}
	
	public void clickNewServiceContractTypeOKButton() {
		clickAndWait(newservicecontacttypeOKbtn);
	}
	
	public void clickNewServiceContractTypeCancelButton() {
		click(newservicecontacttypecancelbtn);
	}
	
}
