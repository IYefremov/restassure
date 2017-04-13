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

public class ServiceRequestTypesWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00")
	private WebTable servicerequesttypestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addservicerequesttypebtn;
	
	//New Service Request type
	@FindBy(xpath = "//input[contains(@id, 'Card_tbxServiceRequestTypeName')]")
	private TextField invoicetypenamefld;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbxServiceRequestTypeDescription')]")
	private TextField invoicetypedescfld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlTeam_Input')]")
	private WebElement invoicetypeteamcmb;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_rcbServicePachages_Input')]")
	private WebElement invoicetypepackagecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newservicerequesttypeOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newservicerequesttypecancelbtn;
	
	public ServiceRequestTypesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickAddServiceRequestTypeButton() {
		clickAndWait(addservicerequesttypebtn);
	}
	
	public void setNewServiceRequestTypeName(String srtypename) {
		clearAndType(invoicetypenamefld, srtypename);		
	}
	
	public void setNewServiceRequestTypeDescription(String srtypedesc) {
		clearAndType(invoicetypedescfld, srtypedesc);		
	}
	
	public void selectNewServiceRequestTypeTeam(String srtypeteam) {
		waitABit(1000);
    	new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.visibilityOf(invoicetypeteamcmb));
    	invoicetypeteamcmb.click();
    	invoicetypeteamcmb.clear();
    	invoicetypeteamcmb.sendKeys(srtypeteam);
    	waitABit(300);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li/em[text()='" + srtypeteam + "']"))).click();
		waitABit(1000);
		
	}
	
	public void selectNewServiceRequestTypePackage(String srtypepackage) {
		waitABit(1000);
    	new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.visibilityOf(invoicetypepackagecmb));
    	invoicetypepackagecmb.click();
    	invoicetypepackagecmb.clear();
    	invoicetypepackagecmb.sendKeys(srtypepackage);
    	waitABit(300);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li/em[text()='" + srtypepackage + "']"))).click();
		waitABit(1000);
		
	}
	
	public void clickNewServiceRequestTypeOKButton() {
		clickAndWait(newservicerequesttypeOKbtn);
	}
	
	public void clickNewServiceRequestTypeCancelButton() {
		click(newservicerequesttypecancelbtn);
	}
	
	public void createNewServiceRequestType(String newsrtype) {
		setNewServiceRequestTypeName(newsrtype);
		clickNewServiceRequestTypeOKButton();
	}
	
	public List<WebElement> getServiceRequestTypesTableRows() {
		return servicerequesttypestable.getTableRows();
	}
	
	public WebElement getTableRowWithServiceRequestType(String srtype) {
		List<WebElement> rows = getServiceRequestTypesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[5]")).getText().equals(srtype)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTableServiceRequestTypeDescription(String srtype) {
		String srtypedesc = "";
		WebElement row = getTableRowWithServiceRequestType(srtype);
		if (row != null) {
			srtypedesc = row.findElement(By.xpath(".//td[6]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + srtype + " service request type");
		return srtypedesc;
	}
	
	public boolean isServiceRequestTypeExists(String srtype) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  servicerequesttypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + srtype + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public void clickEditServiceRequestType(String srtype) throws InterruptedException {
		WebElement row = getTableRowWithServiceRequestType(srtype);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + srtype + "service request type");
	}
	
	public void deleteServiceRequestType(String srtype) throws InterruptedException {
		WebElement row = getTableRowWithServiceRequestType(srtype);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + srtype + "service request type");	
		}
	}
	
	public void deleteServiceRequestTypeAndCancelDeleting(String srtype) throws InterruptedException {
		WebElement row = getTableRowWithServiceRequestType(srtype);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + srtype + "service request type");	
		}
	}
}
