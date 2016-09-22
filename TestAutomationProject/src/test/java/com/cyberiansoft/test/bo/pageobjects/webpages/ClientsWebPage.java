package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class ClientsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable clientstable;
	
	@FindBy(xpath = "//table[contains(@id, 'Content_Main_gvDeleted')]")
	private WebTable clientsarchivedtable;
	
	@FindBy(xpath = "//a[contains(@id, 'lbInsert') and @class='add']")
	private WebElement addclientbtn;
	
	@FindBy(xpath = "//input[contains(@id, 'btnImport') and @value='Import']")
	private WebElement importbtn;
	
	@FindBy(xpath = "//input[@title='Archive']")
	private WebElement deletemarker;
	
	@FindBy(xpath = "//span[@class='rtsIn']/span[text()='Active']")
	private WebElement activetab;
	
	@FindBy(xpath = "//span[@class='rtsIn']/span[text()='Archived']")
	private WebElement archivedtab;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_comboType_Input")
	private ComboBox searchtypecbx;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_comboType_DropDown")
	private DropDown searchtypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_tbSearch")
	private TextField searchclientfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_BtnFind")
	private WebElement findbtn;
	
	//Import clients
	

	@FindBy(xpath = "//input[@class='ruFakeInput radPreventDecorate']")
	private WebElement importfilefld;
	
	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_SubmitButton') and @value='Upload file']")
	private WebElement uploadfilebtn;

	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_btnGoToGrid') and @value='Next']")
	private WebElement fileinputnextbtn;
	
	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_btnNext') and @value='Import']")
	private WebElement importclientsbtn;
	
	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_Button2') and @value='To Clients']")
	private WebElement toclientsbtn;
	
	//Client Services
	@FindBy(id = "ctl00_Content_filterer_comboPackages_Input")
	private ComboBox servicepackagcmb;
	
	@FindBy(id = "ctl00_Content_filterer_comboPackages_DropDown")
	private DropDown servicepackagdd;
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebElement clientservicestable;
	
	//Client Users
	@FindBy(id = "ctl00_Content_filterer_tbSearch")
	private TextField clientuserssearchfld;
	
	@FindBy(id = "ctl00_Content_filterer_BtnFind")
	private WebElement clientusersfindbtn;
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable clientuserstable;
	
	public ClientsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clientstable.getWrappedElement()));
	}
	
	
	public boolean searchPanelIsExpanded() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(searchbtn));
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyEmployeesTableColumnsAreVisible() {
		
		Assert.assertTrue(clientstable.isTableColumnExists("Contacts"));
		Assert.assertTrue(clientstable.isTableColumnExists("Client"));
		Assert.assertTrue(clientstable.isTableColumnExists("Address"));
		Assert.assertTrue(clientstable.isTableColumnExists("Email"));
		Assert.assertTrue(clientstable.isTableColumnExists("Phone"));
		Assert.assertTrue(clientstable.isTableColumnExists("Notes"));		
		Assert.assertTrue(clientstable.isTableColumnExists("Wholesale"));
		Assert.assertTrue(clientstable.isTableColumnExists("PO# req."));
		Assert.assertTrue(clientstable.isTableColumnExists("Commission"));
	}
	
	public void clickArchivedTab() { 
		clickAndWait(archivedtab);
	}
	
	public void clickActiveTab() { 
		clickAndWait(activetab);
	}
	
	public void clickFindButton() { 
		clickAndWait(new WebDriverWait(driver, 60)
		  .until(ExpectedConditions.elementToBeClickable(findbtn)));
	}
	
	public int getClientsTableRowsCount() {
		return getClientsTableRows().size();
	}
	
	public List<WebElement>  getClientsTableRows() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clientstable.getWrappedElement()));
		return clientstable.getTableRows();
	}
	
	public List<WebElement>  getArchivedClientsTableRows() {
		return clientsarchivedtable.getTableRows();
	}
	
	public void setClientSearchCriteria(String name) {
		clearAndType(searchclientfld, name);
	}
	
	public void searchClientByName(String companyname) {
		makeSearchPanelVisible();
		setClientSearchCriteria(companyname);
		clickFindButton();
	}
	
	public void deleteUserViaSearch(String clientname)
			throws InterruptedException {
		makeSearchPanelVisible();
		setClientSearchCriteria(clientname);
		clickFindButton();
		Thread.sleep(3000);
		deletemarker.click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
		Thread.sleep(3000);
	}
	
	public NewClientDialogWebPage clickAddClientButton() {
		click(addclientbtn);
		return PageFactory.initElements(
				driver, NewClientDialogWebPage.class);
	}

	public void selectSearchType(String _type)  { 
		selectComboboxValue(searchtypecbx, searchtypedd, _type);
	}
	
	public void deleteClient(String clientname) {
		WebElement clientstablerow = getTableRowWithClient(clientname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[2]/input")).click();
			WebDriverWait wait = new WebDriverWait(driver, 5);
	        wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			waitABit(300);
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else {
			Assert.assertTrue(false, "Can't find client: " + clientname);
		}
	}
	
	public void restoreClient(String clientname) throws InterruptedException {
		WebElement archivedclientstablerow = getTableRowWithArchivedClient(clientname);
		if (archivedclientstablerow != null) {
			archivedclientstablerow.findElement(By.xpath(".//td[1]/input")).click();
			WebDriverWait wait = new WebDriverWait(driver, 5);
	        wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			waitABit(300);
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} else {
			Assert.assertTrue(false, "Can't find client: " + clientname);
		}
	}
	
	public String mouseMoveToClientNotesGridAndGetNoteContent(String clientname) throws InterruptedException {
		String notetxt = "";
		WebElement clientstablerow = getTableRowWithClient(clientname);
		if (clientstablerow != null) {
			waitABit(1000);
			Actions action = new Actions(driver);
			action.moveToElement(clientstablerow.findElement(By.xpath("./td[11]/img"))).clickAndHold().build().perform();
			//action.moveToElement(clientstablerow.findElement(By.xpath("./td[11]/img"))).build().perform();
			waitABit(1000);
			List<WebElement> notes = driver.findElements(By.xpath("//div[contains(@id, 'RadToolTipNote')]"));
			for (WebElement note : notes) {
				if (note.isDisplayed()) {
					notetxt = note.getText().trim();
					break;
				}									
			}
			action.release().build().perform();
		} else {
			Assert.assertTrue(false, "Can't find client: " + clientname);
		}
		return notetxt;
	}
	
	public WebElement getTableRowWithClient(String clientname) {
		List<WebElement> clientstablerows = getClientsTableRows();
		for (WebElement clientstablerow : clientstablerows) {
			if (clientstablerow.findElement(By.xpath(".//td[" + clientstable.getTableColumnIndex("Client") + "]")).getText().equals(clientname)) {
				return clientstablerow;
			}
		}
		return null;
	}
	
	public WebElement getTableRowWithArchivedClient(String clientname) {
		List<WebElement> archivedclientstablerows = getArchivedClientsTableRows();
		for (WebElement archivedclientstablerow : archivedclientstablerows) {
			if (archivedclientstablerow.findElement(By.xpath(".//td[2]")).getText().equals(clientname)) {
				return archivedclientstablerow;
			}
		}
		return null;
	}
	
	public NewClientDialogWebPage clickEditClient(String clientname) {
		WebElement clientstablerow = getTableRowWithClient(clientname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[1]/input")).click();
		} else {
			Assert.assertTrue(false, "Can't find client: " + clientname);
		}
		return PageFactory.initElements(
				driver, NewClientDialogWebPage.class);
	}
	
	public boolean isClientExistsInTable(String clientname) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  clientstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + clientname + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public boolean isClientExistsInArchivedTable(String clientname) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  clientsarchivedtable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + clientname + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public void importClients(String fileimport) {
		click(importbtn);
		waitABit(2000);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(importfilefld));
		File importfile = new File("data/" + fileimport);
		driver.findElement(By.xpath("//input[@class='ruFileInput']")).sendKeys(importfile.getAbsolutePath());
		waitABit(2000);
		click(uploadfilebtn);
		waitABit(2000);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='File uploaded successfully']"))));
		
		click(fileinputnextbtn);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(importclientsbtn));
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id, 'IsWholesale_3_Input')]")))).click();
		waitABit(300);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='IsWholeSaler']"))).click();
		click(importclientsbtn);
		waitABit(1000);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Imported: 1 Not imported: 0']"))));
		click(toclientsbtn);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clientstable.getWrappedElement()));
	}
	
	public Set<String> clickServicesLinkForClient(String clientname) {
		WebElement row = getTableRowWithClient(clientname);
		if (row != null) {
			row.findElement(By.xpath(".//a[text()='Services']")).click();
		} else 
			Assert.assertTrue(false, "Can't find client: " + clientname);
		
		waitForNewTab();
    	String mainWindowHandle = driver.getWindowHandle();
    	driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		for (String activeHandle : driver.getWindowHandles()) {
	        if (!activeHandle.equals(mainWindowHandle)) {
	        	driver.switchTo().window(activeHandle);
	        }
	    }
		return driver.getWindowHandles();
	}
	
	public Set<String> clickClientUsersLinkForClient(String clientname) {
		WebElement row = getTableRowWithClient(clientname);
		if (row != null) {
			row.findElement(By.xpath(".//a[text()='Client Users']")).click();
		} else 
			Assert.assertTrue(false, "Can't find client: " + clientname);
		
		waitForNewTab();
    	String mainWindowHandle = driver.getWindowHandle();
    	driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		for (String activeHandle : driver.getWindowHandles()) {
	        if (!activeHandle.equals(mainWindowHandle)) {
	        	driver.switchTo().window(activeHandle);
	        }
	    }
		return driver.getWindowHandles();
	}
	
	public void selectClientServicePackage(String servicepackage) {
		selectComboboxValue(servicepackagcmb, servicepackagdd, servicepackage);
		waitUntilPageReloaded();
	}
	
	public List<WebElement> getClientServicesTableRows() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clientservicestable));
		return clientservicestable.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_Content_gv_ctl00')]"));
	}
	
	public void searchClientUser(String clienuser) {
		clearAndType(clientuserssearchfld, clienuser);
		clickAndWait(clientusersfindbtn);
	}
	
	public List<WebElement> getClientUsersTableRows() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clientuserstable.getWrappedElement()));
		return clientuserstable.getTableRows();
	}
	
	public int getClientUsersTableRowCount() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(clientuserstable.getWrappedElement()));
		return clientuserstable.getTableRowCount();
	}
	
	public void closeClientServicesTab(String mainWindowHandle) {
		driver.close();
		driver.switchTo().window(mainWindowHandle);
	}
}
