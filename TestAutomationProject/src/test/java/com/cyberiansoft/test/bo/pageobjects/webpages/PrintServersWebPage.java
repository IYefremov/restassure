package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class PrintServersWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable printserverstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addprintserveerebtn;
	
	//New Print server
	@FindBy(xpath = "//input[contains(@id, 'Card_tbName')]")
	private TextField servernamefld;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbDescr')]")
	private TextField serverdescfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newprintserverOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newprintservercancelbtn;

	
	public PrintServersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickAddPrintServerButton() {
		clickAndWait(addprintserveerebtn);
	}
	
	public void setPrintServerName(String servername) {
		clearAndType(servernamefld, servername);		
	}
	
	public void setPrintServerDescription(String serverdesc) {
		clearAndType(serverdescfld, serverdesc);		
	}
	
	public void clickNewPrintServerOKButton() {
		clickAndWait(newprintserverOKbtn);
		waitABit(8000);
	}
	
	public void clickNewPrintServerCancelButton() {
		click(newprintservercancelbtn);
	}
	
	public void addNewPrintServer(String servername) {
		setPrintServerName(servername);
		clickNewPrintServerOKButton();
	}
	
	public void addNewPrintServer(String servername, String serverdesc) {
		setPrintServerName(servername);
		setPrintServerDescription(serverdesc);
		clickNewPrintServerOKButton();
	}
	
	public List<WebElement>  getPrintServersTableRows() {
		return printserverstable.getTableRows();
	}
	
	public WebElement getTableRowWithPrintServer(String servername) {
		List<WebElement> rows = getPrintServersTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]")).getText().equals(servername)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTablePrintServerDescription(String servername) {
		String serverdesc = "";
		WebElement row = getTableRowWithPrintServer(servername);
		if (row != null) {
			serverdesc = row.findElement(By.xpath(".//td[5]"))
                    .getText()
                    .replaceAll("\\u00A0", "")
                    .trim();
		} else
            Assert.fail("Can't find " + servername + " print server");
		return serverdesc;
	}
	
	public boolean printServerExists(String servername) {
        return printserverstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + servername + "']")).size() > 0;
	}
	
	public void clickEditPrintServer(String servername) {
		WebElement row = getTableRowWithPrintServer(servername);
		if (row != null) {
			clickEditTableRow(row);
		} else
            Assert.fail("Can't find " + servername + " print server");
	}
	
	public void deletePrintServer(String servername) {
		WebElement row = getTableRowWithPrintServer(servername);
		if (row != null) {
			deleteTableRow(row);
		} else {
            Assert.fail("Can't find " + servername + " print server");
		}
	}
	
	public void deletePrintServerAndCancelDeleting(String servername) {
		WebElement row = getTableRowWithPrintServer(servername);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
            Assert.fail("Can't find " + servername + " print server");
		}
	}

    public void verifyPrintServersDoNotExist(String printsrvname, String printsrvnameedited) {
        while (printServerExists(printsrvname)) {
            deletePrintServer(printsrvname);
        }
        while (printServerExists(printsrvnameedited)) {
            deletePrintServer(printsrvnameedited);
        }
    }
}
