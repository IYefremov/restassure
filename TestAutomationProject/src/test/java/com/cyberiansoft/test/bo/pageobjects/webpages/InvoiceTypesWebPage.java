package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.DropDown;
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
import java.util.Set;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

public class InvoiceTypesWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00")
	private WebTable invoicestypestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addinvoicetypebtn;
	
	//Assigned clients tab
	@FindBy(xpath = "//input[contains(@id, 'Content_comboAssignClient_Input')]")
	private TextField assignedclientscmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Content_comboAssignClient_DropDown')]")
	private DropDown assignedclientsdd;
	
	@FindBy(id = "ctl00_Content_gvAssignedClients_ctl00")
	private WebTable assignedclientstable;
	
	@FindBy(xpath = "//input[contains(@id, 'Content_btnAddAssignedClient')]")
	private WebElement addassignedclientbtn;
	
	@FindBy(xpath = "//input[contains(@id, 'ontent_btnUpdateClients')]")
	private WebElement updateclientsbtn;
		
	public InvoiceTypesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public NewInvoiceTypeDialogWebPage clickAddInvoiceTypeButton() {
		clickAndWait(addinvoicetypebtn);
		return PageFactory.initElements(
				driver, NewInvoiceTypeDialogWebPage.class);
	}
	
	public List<WebElement>  getInvoiceTypesTableRows() {
		return invoicestypestable.getTableRows();
	}
	
	public WebElement getTableRowWithInvoiceType(String invoicetype) {
		List<WebElement> rows = getInvoiceTypesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[5]")).getText().equals(invoicetype)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTableInvoiceTypeDescription(String invoicetype) {
		String invoicetypedesc = "";
		WebElement row = getTableRowWithInvoiceType(invoicetype);
		if (row != null) {
			invoicetypedesc = row.findElement(By.xpath(".//td[6]"))
                    .getText()
                    .replaceAll("\\u00A0", "")
                    .trim();
		} else
            Assert.fail("Can't find " + invoicetype + " invoice type");
		return invoicetypedesc;
	}
	
	public boolean invoiceTypeExists(String invoicetype) {
        return invoicestypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + invoicetype + "']")).size() > 0;
	}
	
	public NewInvoiceTypeDialogWebPage clickEditInvoiceType(String invoicetype) {
		WebElement row = getTableRowWithInvoiceType(invoicetype);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + invoicetype + " invoice type");
		return PageFactory.initElements(
				driver, NewInvoiceTypeDialogWebPage.class);
	}
	
	public Set<String> clickClientsLinkForInvoiceType(String invoicetype) {
		WebElement row = getTableRowWithInvoiceType(invoicetype);
		if (row != null) {
			row.findElement(By.xpath(".//a[text()='Clients']")).click();
		} else 
			Assert.assertTrue(false, "Can't find " + invoicetype + " invoice type");
		waitForNewTab();
    	String mainWindowHandle = driver.getWindowHandle();
		for (String activeHandle : driver.getWindowHandles()) {
	        if (!activeHandle.equals(mainWindowHandle)) {
	        	driver.switchTo().window(activeHandle);
	        }
	    }
		return driver.getWindowHandles();
	}
	
	public void closeAssignedClientsTab(String mainWindowHandle) {
		driver.close();
		driver.switchTo().window(mainWindowHandle);
	}
	
	public void deleteInvoiceType(String invoicetype) {
		WebElement row = getTableRowWithInvoiceType(invoicetype);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + invoicetype + " invoice type");	
		}
	}
	
	public void deleteInvoiceTypeAndCancelDeleting(String invoicetype) {
		WebElement row = getTableRowWithInvoiceType(invoicetype);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + invoicetype + " invoice type");	
		}
	}
	
	public void addAssignedClient(String clientname) {
		assignedclientscmb.click();
		assignedclientscmb.clearAndType(clientname);
		wait.until(ExpectedConditions.visibilityOf(assignedclientsdd.getWrappedElement()));
		waitABit(1000);
		assignedclientsdd.selectByVisibleText(clientname);
		addassignedclientbtn.click();
		waitForLoading();
	}
	
	public void clickUpdateClientsButton() {
		clickAndWait(updateclientsbtn);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()= 'Clients have been updated for this Invoice Type']")));
		
	}
	
	public boolean isAssignedClientSelected(String clientname) {
		boolean exists =  assignedclientstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + clientname + "']")).size() > 0;
		return exists;
	}
	
	public List<WebElement>  getAssignedClientsTableRows() {
		return assignedclientstable.getTableRows();
	}
	
	public WebElement getTableRowWithAssignedClient(String clientname) {
		List<WebElement> rows = getAssignedClientsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[2]")).getText().equals(clientname)) {
				return row;
			}
		} 
		return null;
	}
	
	public void deleteAssignedClient(String clientname) throws InterruptedException {
		WebElement row = getTableRowWithAssignedClient(clientname);
		if (row != null) {
			clickDeleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + clientname + " client in Assigned Clients table");	
		}
	}

    public void verifyInvoiceTypesDoNotExist(String invoicetype, String invoicetypeedited) {
        while (invoiceTypeExists(invoicetype)) {
            deleteInvoiceType(invoicetype);
        }
        while (invoiceTypeExists(invoicetypeedited)) {
            deleteInvoiceType(invoicetypeedited);
        }
	}
}
