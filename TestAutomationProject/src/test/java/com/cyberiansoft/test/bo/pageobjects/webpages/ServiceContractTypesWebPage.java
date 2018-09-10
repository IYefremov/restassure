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
	}
	
	public void waitServiceContractTypesPageIsLoaded() { 
		waitABit(1000);
		wait.until(ExpectedConditions.visibilityOf(servicecontracttypestable.getWrappedElement()));
		Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='Service Contract Types']")).isDisplayed());
	}
	
	public String getTableServiceContractTypeDescription(String servicecontacttype) {
        return getTableServiceContractType(servicecontacttype, ".//td[5]");
	}

    public String getTableServiceContractType(String serviceContactType, String cellLocator) {
        String servicecontracttypedesc = "";
        WebElement row = getTableRowWithServiceContractType(serviceContactType);
        if (row != null) {
            servicecontracttypedesc = row.findElement(By.xpath(cellLocator))
                    .getText()
                    .replaceAll("\\u00A0", "")
                    .trim();
        } else
            Assert.fail("Can't find " + serviceContactType + " service contact type");
        return servicecontracttypedesc;
    }

    public String getTableServiceContractTypePrice(String servicecontacttype) {
        return getTableServiceContractType(servicecontacttype, ".//td[6]");
	}
	
	public String getTableServiceContractTypeSalesPrice(String servicecontacttype) {
        return getTableServiceContractType(servicecontacttype, ".//td[7]");
	}
	
	public String getTableServiceContractTypeAccID(String servicecontacttype) {
        return getTableServiceContractType(servicecontacttype, ".//td[8]");
	}
	
	public String getTableServiceContractTypeAccID2(String servicecontacttype) {
        return getTableServiceContractType(servicecontacttype, ".//td[9]");
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
	
	public void createNewServiceContractType(String servicecontracttypename) {
		setNewServiceContractTypeName(servicecontracttypename);
		clickNewServiceContractTypeOKButton();
	}
	
	public boolean isServiceContractTypeExists(String servicecontacttype) {
		boolean exists =  servicecontracttypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + servicecontacttype + "']")).size() > 0;
		return exists;
	}
	
	public void clickEditServiceContractType(String servicecontacttype) {
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");
	}
	
	public void deleteServiceContractType(String servicecontacttype) {
		WebElement row = getTableRowWithServiceContractType(servicecontacttype);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicecontacttype + " service contact type");		
	}
	
	public void deleteServiceContractTypeAndCancelDeleting(String servicecontacttype) {
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
		waitABit(2000);
	}
	
	public void clickNewServiceContractTypeCancelButton() {
		click(newservicecontacttypecancelbtn);
	}

    public void verifyServiceContractTypesDoNotExist(String contracttype, String contracttypeedited) {
        while (isServiceContractTypeExists(contracttype)) {
            deleteServiceContractType(contracttype);
        }
        while (isServiceContractTypeExists(contracttypeedited)) {
            deleteServiceContractType(contracttypeedited);
        }
    }
}
