package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;


public class PrintTemplatesWebPage extends WebPageWithPagination {
	
	private final String invoicesitem = "Invoices";
	private final String workordersitem = "Work Orders";
	private final String inspectionsitem = "Inspections";
	private final String timesheetsitem = "Timesheets";
	
	@FindBy(id = "ctl00_ctl00_Content_Main_btnAddNew")
	private WebElement addnewtemplatebtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_btnEdit")
	private WebElement edittemplatebtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_btnDelete")
	private WebElement deletetemplatebtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_pnlBarTemplates")
	private WebElement printtemplateslist;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_btnSaveParams")
	private WebElement saveprinttemplateoptionsbtn;
	
	///////////////////
	@FindBy(id = "paramsTable")
	private WebElement printoutoptions;
	
	@FindBy(id = "lbEdit")
	private WebElement footertexteditbtn;
	
	@FindBy(id = "btnSaveFooter")
	private WebElement footersavebtn;
	
	@FindBy(xpath = "//a[text()='Edit Labels']")
	private WebElement editlabelslink;
	
	@FindBy(id = "tag")
	private TextField taglabelfld;
	
	@FindBy(id = "estimation")
	private TextField estimationlabelfld;
	
	@FindBy(id = "inspectedBy")
	private TextField inspectedbylabelfld;
	
	@FindBy(id = "header")
	private TextField headerlabelfld;
	
	@FindBy(id = "customerInfo")
	private TextField customerinfolabelfld;
	
	@FindBy(id = "ownerInfo")
	private TextField ownerinfolabelfld;
	
	@FindBy(id = "ro")
	private TextField rolabelfld;
	
	@FindBy(id = "stock")
	private TextField stocklabelfld;
	
	@FindBy(id = "claimInfo")
	private TextField claiminfolabelfld;
	
	@FindBy(id = "saveLabelsButton")
	private WebElement labelssavebtn;
	
	//////////////////////
	@FindBy(id = "lbEditColumns")
	private WebElement editcolumnslink;
	
	@FindBy(id = "displayedColumnsList")
	private WebElement displayedcolumnslist;
	
	@FindBy(id = "availableColumnsList")
	private WebElement availablecolumnslist;
	
	@FindBy(id = "saveColumnsButton")
	private WebElement columnssavebtn;
	
	//////////////////////////
	@FindBy(xpath = "//a[text()='Edit Options']")
	private WebElement editoptionstab;
	
	public PrintTemplatesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(addnewtemplatebtn));
	}
	
	
	public void deleteInvoicePrintTemplatePackage(String ptpackagename) {
		expandInvoicesPrintTemplatesGroup();
		selectInvoicesPrintTemplatesPackage(ptpackagename);
		clickDeletetemplateButton();
		acceptAlertAndWait();
	}
	
	public void clickDeletetemplateButton() {
		waitABit(1000);
		Actions act = new Actions(driver);
		act.click(deletetemplatebtn).perform();
	}
	
	public void deleteInspectionPrintTemplatePackage(String ptpackagename) {
		expandInspectionsPrintTemplatesGroup();
		selectInspectionsPrintTemplatesPackage(ptpackagename);
		clickDeletetemplateButton();
		acceptAlertAndWait();
	}
	
	public NewPrintTemplatesDialogWebPage clickAddNewPrintTemplateButton() {
		addnewtemplatebtn.click();
		return PageFactory.initElements(
				driver, NewPrintTemplatesDialogWebPage.class);
	}
	
	public NewPrintTemplatesDialogWebPage clickEditPrintTemplateButton() {
		edittemplatebtn.click();
		return PageFactory.initElements(
				driver, NewPrintTemplatesDialogWebPage.class);
	}
	
	public void expandInvoicesPrintTemplatesGroup() {
		expandListItem(getPrintTemplateGroup(invoicesitem));
	}
	
	public void expandInspectionsPrintTemplatesGroup() {
		expandListItem(getPrintTemplateGroup(inspectionsitem));
	}
	
	protected void expandListItem(WebElement listitem) {
		if (!isPrintTemplateGroupExpanded(listitem)) {
			Actions act = new Actions(driver);
			act.click(listitem.findElement(By.xpath("./a/span/span[@class='rpExpandHandle']"))).perform();
			waitUntilPageReloaded();
		}
	}
	
	public WebElement getPrintTemplateGroup(String ptgroupname) {
		WebElement ptgroup = null;
		List<WebElement> ptlist = getListOfPrintTemplatesItemsGroups();
		for (WebElement listitem : ptlist) {
			if (listitem.getText().contains(ptgroupname)) {
				ptgroup = listitem;
				break;
			}
		}
		return ptgroup;
	}
	
	public List<WebElement> getListOfPrintTemplatesItemsGroups() {
		return printtemplateslist.findElements(By.xpath("./ul/li"));
	}
	
	public boolean isPrintTemplateGroupExpanded(WebElement listitem) {
		return listitem.findElement(By.xpath("./a")).getAttribute("class").contains("rpExpanded");
	}
	
	public void selectInvoicesPrintTemplatesPackage(String packagename) {
		clickAndWait(getPrintTemplateGroup(invoicesitem).findElement(By.xpath(".//span[text()='" + packagename + "']")));
	}
	
	public void selectInspectionsPrintTemplatesPackage(String packagename) {
		clickAndWait(getPrintTemplateGroup(inspectionsitem).findElement(By.xpath(".//span[text()='" + packagename + "']")));
	}

	public boolean isInvoicesPrintTemplatesGroupContainsPackage(String packagename) {
		return getPrintTemplateGroup(invoicesitem).findElements(By.xpath(".//span[text()='" + packagename + "']")).size() > 0;
	}
	
	public boolean isInspectionsPrintTemplatesGroupContainsPackage(String packagename) {
		return getPrintTemplateGroup(inspectionsitem).findElements(By.xpath(".//span[text()='" + packagename + "']")).size() > 0;
	}
	
	public void clickSavePrintTemplateOptionsButton() {
		clickAndWait(saveprinttemplateoptionsbtn);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Template is saved.']"))));
		waitABit(1000);
	}
	
	public void checkPrintOutOptionsCheckboxes(String[] optionstocheck) {
		for (String optiontocheck : optionstocheck)
			checkPrintOutOptionsCheckboxes(optiontocheck);
	}
	
	public void checkPrintOutOptionsCheckboxes(String optiontocheck) {
		List<WebElement> printouts = printoutoptions.findElements(By.xpath("./tbody/tr"));
		for (WebElement printout : printouts)
			if (printout.getText().equals(optiontocheck))
				checkPrintOutOptionCheckbox(printout.findElement(By.xpath("./td/input")));
	}
	
	public void checkAllPrintOutOptionsCheckboxes() {
		List<WebElement> printouts = getAllPrintOutOptionsCheckboxes();
		for (WebElement printout : printouts)
			checkPrintOutOptionCheckbox(printout);
	}
	
	public void uncheckAllPrintOutOptionsCheckboxes() {
		List<WebElement> printouts = getAllPrintOutOptionsCheckboxes();
		for (WebElement printout : printouts)
			uncheckPrintOutOptionCheckbox(printout);
	}
	
	public List<WebElement> getAllPrintOutOptionsCheckboxes() {
		return printoutoptions.findElements(By.xpath("./tbody/tr/td/input"));
	}
	
	public void checkPrintOutOptionCheckbox(WebElement checkbox) {
		if (checkbox.getAttribute("checked") == null) {
			try {
				checkbox.click();
			} catch (ElementNotVisibleException e) {
				JavascriptExecutor js = (JavascriptExecutor)driver; 
				js.executeScript("arguments[0].click();", checkbox); 
			}
		}			
	}
	
	public void uncheckPrintOutOptionCheckbox(WebElement checkbox) {
		if (checkbox.getAttribute("checked") != null) {
			try {
				checkbox.click();
			} catch (ElementNotVisibleException e) {
				JavascriptExecutor js = (JavascriptExecutor)driver; 
				js.executeScript("arguments[0].click();", checkbox); 
			}
		}			
	}
	
	public void setFooterTextPrintTemplateOptions(String footertext) {
		footertexteditbtn.click();		
		driver.switchTo().frame(driver.findElement(By.id("ctl00_ctl00_Content_Main_footerTextArea_ifr")));
		driver.findElement(By.xpath("//body")).clear();
		driver.findElement(By.xpath("//body")).sendKeys(footertext);
		driver.switchTo().defaultContent();
		footersavebtn.click();
	}
	
	public void setInspectionLabelsPrintTemplateOptions(String taglabel, String estimation, String inspectedby, String headertext) {
		editlabelslink.click();
		clearAndType(taglabelfld, taglabel);
		clearAndType(estimationlabelfld, estimation);
		clearAndType(inspectedbylabelfld, inspectedby);
		clearAndType(headerlabelfld, headertext);
		labelssavebtn.click();
	}
	
	public void setInvoiceLabelsPrintTemplateOptions(String taglabel, String customerinfo, String ownerinfo, String _ro, String stock, String claiminfo) {
		editlabelslink.click();
		clearAndType(taglabelfld, taglabel);
		clearAndType(customerinfolabelfld, customerinfo);
		clearAndType(ownerinfolabelfld, ownerinfo);
		clearAndType(rolabelfld, _ro);
		clearAndType(stocklabelfld, stock);
		clearAndType(claiminfolabelfld, claiminfo);
		labelssavebtn.click();
	}
	
	public void selectInvoiceColumnsPrintTemplateOptions(String[] addtodisplayedcolumns, String[] addtoavailablecolumns) {
		editcolumnslink.click();			
		List<WebElement> displayeditems =  displayedcolumnslist.findElements(By.tagName("li"));			
		for (String addtoavailablecolumn : addtoavailablecolumns) {
			for (WebElement displayeditem : displayeditems) {
				if (displayeditem.getText().equals(addtoavailablecolumn)){
					new Actions(driver).dragAndDrop(displayeditem, availablecolumnslist).perform();
				}
			}
		}
				
		List<WebElement> availableitems =  availablecolumnslist.findElements(By.tagName("li"));					
		for (String addtodisplayedcolumn : addtodisplayedcolumns) {
			for (WebElement availableitem : availableitems) {
				if (availableitem.getText().equals(addtodisplayedcolumn)){
					new Actions(driver).dragAndDrop(availableitem, displayedcolumnslist).perform();
				}
			}
		}		
		columnssavebtn.click();
	}
	
	public void clickEditOptionsTab() {
		editoptionstab.click();
	}
	
}
