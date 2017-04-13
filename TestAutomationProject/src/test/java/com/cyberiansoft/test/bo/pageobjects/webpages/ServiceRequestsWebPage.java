package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.Calendar;
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

public class ServiceRequestsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable servicerequeststable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ddlDevice_Input")
	private WebElement defaultdevicefordispatchcmbbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnSetDefaultDevice")
	private WebElement setdefaultdevicebtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_rcbServicePachages_Input")
	private WebElement addservicerequestcmbbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_lbAddServiceRequest")
	private WebElement addservicelink;

	// ///////////////////////////
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_ddlTeam_Input")
	private WebElement teamcmbbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_Card_tbPONo")
	private TextField pofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_Card_tbRONumber")
	private TextField rofld;

	@FindBy(xpath = "//span[text()='Save']")
	private WebElement savebtn;

	@FindBy(xpath = "//input[@value='Dispatch']")
	private WebElement dispatchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl04_btnEditLink")
	private WebElement editsevicebtn;

	// /////////////////////////////////////////////////
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Customer']")
	private WebElement customertab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Vehicle Info']")
	private WebElement vehicleinfotab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Services']")
	private WebElement servicestab;

	@FindBy(id = "ctl00_ctl00_Content_Main_Card_vehicleVin")
	private TextField vinnfld;

	// /////////////////////////////////////////////////
	@FindBy(xpath = "//span[@class='rlbButtonTR']/span/span")
	private WebElement moverightbtn;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbxFreeText")
	private TextField searchtextfld;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboStatus_Input")
	private ComboBox searchstatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboStatus_DropDown")
	private DropDown searchstatusdd;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboLabel_Input")
	private ComboBox searchlabelcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboLabel_DropDown")
	private DropDown searchlabeldd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	public ServiceRequestsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerequeststable.getWrappedElement()));
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyInvoicesTableColumnsAreVisible() {		
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Status"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Package"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Device"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Vehicle Info"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Client"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Stock"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("RO"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Inspection"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("WorkOrder"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Invoice"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Description"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Media"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Scheduled"));
		Assert.assertTrue(servicerequeststable.isTableColumnExists("Email"));
	}
	
	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(searchtextfld.isDisplayed());
		Assert.assertTrue(searchstatuscmb.isDisplayed());
		Assert.assertTrue(searchlabelcmb.isDisplayed());
	}
	
	public int getTechnicianCommissionsTableRowCount() {
		return servicerequeststable.getTableRowCount();
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public boolean technicianCommissionsTableTableIsVisible() {
		return servicerequeststable.isDisplayed();
	}
	
	public void selectSearchStatus(String status) { 
		selectComboboxValue(searchstatuscmb, searchstatusdd, status);
	}
	
	public void selectSearchLabel(String _label) throws InterruptedException  {
		selectComboboxValue(searchlabelcmb, searchlabeldd, _label);
	}
	
	public void setSearchText(String searchtext)  { 
		clearAndType(searchtextfld, searchtext);
	}
	
	public void verifySearchResultsByVehicleInfo(String vehicleinfo) {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerequeststable.getWrappedElement().findElement(By.xpath(".//tr/td[text()='" + vehicleinfo + "']")))); 
	}

	public void createServiceRequest(String devicefordispatch,
			String servicerequest, String po, String ro)
			throws InterruptedException {
		click(defaultdevicefordispatchcmbbox);
		driver.findElement(
				By.xpath("//ul[@class='rcbList']/li[text()='"
						+ devicefordispatch + "']")).click();
		click(setdefaultdevicebtn);
		Thread.sleep(5000);
		addservicerequestcmbbox.click();
		driver.findElement(
				By.xpath("//ul[@class='rcbList']/li[text()='" + servicerequest
						+ "']")).click();
		addservicelink.click();
		teamcmbbox.click();
		driver.findElement(
				By.xpath("//ul[@class='rcbList']/li[text()='Default team']"))
				.click();
		clearAndType(pofld, po);
		clearAndType(rofld, ro);
		clickSaveBtn();
	}

	public void createServiceRequest1(String devicefordispatch,
			String po, String ro)
			throws InterruptedException {
		defaultdevicefordispatchcmbbox.click();
		driver.findElement(
				By.xpath("//ul[@class='rcbList']/li[text()='"
						+ devicefordispatch + "']")).click();
		Thread.sleep(1000);
		addservicelink.click();
		teamcmbbox.click();
		driver.findElement(
				By.xpath("//ul[@class='rcbList']/li[text()='Default team']"))
				.click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_Card_tbDescription"))
				.clear();
		Calendar cal = Calendar.getInstance();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_Card_tbDescription"))
				.sendKeys("Vitaliy" + cal.getTimeInMillis());

		clearAndType(pofld, po);
		clearAndType(rofld, ro);
		clickSaveBtn();
	}

	public void clickSaveBtn() {
		click(savebtn);
		waitABit(4000);
	}

	public void clickDispatchBtn() {
		click(dispatchbtn);
		waitABit(4000);
	}

	public void clickEditBtn() {
		click(editsevicebtn);
		waitABit(1000);
	}

	public void clickCustomerTab() {
		click(customertab);
		waitABit(1000);
	}

	public void clickVehicleInfoTab() {
		click(vehicleinfotab);
		waitABit(1000);
	}

	public void clickServicesTab() {
		click(servicestab);
		waitABit(1000);
	}

	public void selectCustomer(String customer) {
		List<WebElement> customersrows = driver
				.findElements(By
						.xpath("//table[@id='ctl00_ctl00_Content_Main_Card_gv_ctl00']/tbody/tr"));
		for (WebElement customersrow : customersrows) {
			if (customersrow.findElement(By.xpath(".//td[2]")).getText()
					.contains(customer)) {
				customersrow.findElement(By.xpath(".//td/input")).click();
				break;
			}
		}

		waitABit(2000);
	}

	public void setVIN(String vin) {
		clearAndType(vinnfld, vin);
	}

	public void selectSelectServiceType(String servicetype) {
		waitABit(2000);
		click(driver.findElement(
				By.xpath("//tr/td[contains(text(),'" + servicetype + "')]")));
		click(moverightbtn);
		waitABit(4000);
	}

	public void setServiceTypeQuantity(String servicetype, String quantity) {
		List<WebElement> allassignedservices = driver
				.findElements(By
						.xpath("//ul[@class='rlbList']/li[contains(@id,'ctl00_ctl00_Content_Main_Card_rlbServicesAssigned')]"));
		for (WebElement servicerow : allassignedservices) {
			if (servicerow.findElement(By.xpath(".//tr/td[2]")).getText()
					.equals(servicetype)) {
				servicerow.findElement(By.xpath(".//tr/td[3]/span/input"))
						.clear();
				servicerow.findElement(By.xpath(".//tr/td[3]/span/input"))
						.sendKeys(quantity);
			}
		}
		waitABit(3000);
	}

}
