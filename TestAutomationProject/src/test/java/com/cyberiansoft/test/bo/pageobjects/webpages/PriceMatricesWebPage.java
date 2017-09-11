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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class PriceMatricesWebPage extends WebPageWithPagination {

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addpricematrixbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable pricematricestable;
	
	//New price Matrix
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField newpricematrixnamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboService_Input")
	private ComboBox newpricematrixservicecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboService_DropDown")
	private DropDown newpricematrixservicedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboType_Input")
	private ComboBox newpricematrixtypecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboType_DropDown")
	private DropDown newpricematrixtypedd;
	
	@FindBy(xpath = "//span[@class='rtsIn']/span[text()='Damage Severities']")
	private WebElement damageseveritiestab;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlDamageSeverity_lAvailable")
	private WebElement availabledamageseveretiescmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlDamageSeverity_lAssigned")
	private WebElement assigneddamageseveretiescmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlDamageSeverity_btnAddToAssigned")
	private WebElement addtoassignedseveritiesbtn;
	
	@FindBy(xpath = "//span[@class='rtsIn']/span[text()='Damage Sizes']")
	private WebElement damagesizestab;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlDamageSize_lAvailable")
	private WebElement availabledamagesizescmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlDamageSize_lAssigned")
	private WebElement assigneddamagesizescmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlDamageSize_btnAddToAssigned")
	private WebElement addtoassignedsizesbtn;
	
	@FindBy(xpath = "//span[@class='rtsIn']/span[text()='Vehicle Parts']")
	private WebElement vehiclepartstab;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlVehicleParts_lAvailable")
	private WebElement availablevehiclepartscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlVehicleParts_lAssigned")
	private WebElement assignedvehiclepartscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlVehicleParts_btnAddToAssigned")
	private WebElement addtoassignedvehiclepartsbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement OKnewPricemarixbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement cancelnewPricemarixbtn;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	public PriceMatricesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickAddPriceMarixButton() {
		click(addpricematrixbtn);
	}
	
	public void saveNewPriceMatrix() throws InterruptedException {
		clickAndWait(OKnewPricemarixbtn);
	}
	
	public void clickCancelNewPriceMatrix() {
		click(cancelnewPricemarixbtn);
	}
	
	public void setPriceMarixName(String pricematrixname) {
		clearAndType(newpricematrixnamefld, pricematrixname);
	}
	
	public void selectPriceMarixService(String pricematrixservice) {
		selectComboboxValue(newpricematrixservicecmb, newpricematrixservicedd, pricematrixservice);	
	}
	
	public void selectPriceMarixType(String pricematrixtype) {
		selectComboboxValue(newpricematrixtypecmb, newpricematrixtypedd, pricematrixtype);	
	}
	
	public void assignPriceMatrixDamageSize(String damagessize) {
		click(damagesizestab);
		selectAvailableDamageSize(damagessize);
		click(addtoassignedsizesbtn);
	}
	
	public void selectAvailableDamageSize(String damagessize) {
		Select assignedservices = new Select(availabledamagesizescmb);
		assignedservices.selectByVisibleText(damagessize);		
	}
	
	public void assignPriceMatrixDamageSeverity(String damageseverity) {
		click(damageseveritiestab);
		selectAvailableDamageSeverity(damageseverity);
		click(addtoassignedseveritiesbtn);
	}
	
	public void selectAvailableDamageSeverity(String damageseverity) {
		Select assignedservices = new Select(availabledamageseveretiescmb);
		assignedservices.selectByVisibleText(damageseverity);		
	}
	
	public void assignPriceMatrixVehiclePart(String vehiclepart) {
		click(vehiclepartstab);
		selectAvailableVehiclePart(vehiclepart);
		click(addtoassignedvehiclepartsbtn);
	}
	
	public void selectAvailableVehiclePart(String vehiclepart) {
		Select assignedservices = new Select(availablevehiclepartscmb);
		assignedservices.selectByVisibleText(vehiclepart);		
	}
	
	public List<WebElement>  getPriceMatricesTableRows() {
		return pricematricestable.getTableRows();
	}
	
	public WebElement getTableRowWithPriceMatrix(String pricematrixname) {
		List<WebElement> rows = getPriceMatricesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[5]")).getText().equals(pricematrixname)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTablePriceMatrixService(String pricematrixname) {
		String pricematrixservice = "";
		WebElement row = getTableRowWithPriceMatrix(pricematrixname);
		if (row != null) {
			pricematrixservice = row.findElement(By.xpath(".//td[6]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + pricematrixname + " price matrix");
		return pricematrixservice;
	}
	
	public String getTablePriceMatrixType(String pricematrixname) {
		String pricematrixtype = "";
		WebElement row = getTableRowWithPriceMatrix(pricematrixname);
		if (row != null) {
			pricematrixtype = row.findElement(By.xpath(".//td[7]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + pricematrixname + " price matrix");
		return pricematrixtype;
	}
	
	public boolean isPriceMatrixExists(String pricematrix) {
		boolean exists =  pricematricestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + pricematrix + "']")).size() > 0;
		return exists;
	}
	
	public void clickEditPriceMatrix(String pricematrix) throws InterruptedException {
		WebElement row = getTableRowWithPriceMatrix(pricematrix);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + pricematrix + " price matrix");		
	}
	
	public void clickPricesForPriceMatrix(String pricematrix) throws InterruptedException {
		List<WebElement> rows = getPriceMatricesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[5]")).getText().contains(pricematrix)) {
				row.findElement(By.xpath(".//a[text()='Prices']")).click();
				try{
				updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
				}catch(Exception e){}
//			Thread.sleep(300);
//				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
				break;
			}			
		}		
	}
	
	public void deletePriceMatrix(String pricematrix) throws InterruptedException {
		WebElement row = getTableRowWithPriceMatrix(pricematrix);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + pricematrix + " price matrix");	
		}
	}
	
	public void deletePriceMatrixAndCancelDeleting(String pricematrix) throws InterruptedException {
		WebElement row = getTableRowWithPriceMatrix(pricematrix);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + pricematrix + " price matrix");	
		}
	}
	
	public VehiclePartsWebPage clickPricesVehiclePartLink(String vehiclepart) {
		driver.findElement(By.xpath("//a[text()='" + vehiclepart + "']")).click();
		return PageFactory.initElements(driver, VehiclePartsWebPage.class);
	}
}
