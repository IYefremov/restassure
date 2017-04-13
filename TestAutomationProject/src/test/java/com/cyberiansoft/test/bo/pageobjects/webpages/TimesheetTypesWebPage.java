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

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class TimesheetTypesWebPage extends WebPageWithPagination {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00")
	private WebTable timesheettypestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addtimesheettypebtn;
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;	
	
	//New Timesheet type
	@FindBy(xpath = "//input[contains(@id, 'Card_name')]")
	private TextField timesheettypenamefld;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_description')]")
	private TextField timesheettypedescfld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_comboEntryType_Input')]")
	private ComboBox timesheettypeentrytypecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboEntryType_DropDown')]")
	private DropDown timesheettypeentrytypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newtimesheettypeOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newtimesheettypecancelbtn;
	
	public TimesheetTypesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public boolean searchPanelIsExpanded() {
		click(new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(searchbtn)));
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public void clickAddTimesheetTypeButton() {
		clickAndWait(addtimesheettypebtn);
	}
	
	public void setNewTimesheetTypeName(String timesheettypename) {
		clearAndType(timesheettypenamefld, timesheettypename);	
	}
	
	public void setNewTimesheetTypeDescription(String timesheettypedesc) {
		clearAndType(timesheettypedescfld, timesheettypedesc);	
	}
	
	public void selectNewTimesheetTypeEntryType(String timesheettypeentrytype) {
		selectComboboxValue(timesheettypeentrytypecmb, timesheettypeentrytypedd, timesheettypeentrytype);
	}
	
	public void clickNewTimesheetTypeOKButton() {
		clickAndWait(newtimesheettypeOKbtn);
	}
	
	public void clickNewTimesheetTypeCancelButton() {
		click(newtimesheettypecancelbtn);
	}
	
	public String getNewimesheetTypeEntryType() {
		return timesheettypeentrytypecmb.getSelectedValue();
	}
	
	public String createNewTimesheetType(String timesheettype) {
		setNewTimesheetTypeName(timesheettype);
		String defentrytype = getNewimesheetTypeEntryType();
		clickNewTimesheetTypeOKButton();
		return defentrytype;
	}

	public List<WebElement>  getTimesheetTypesTableRows() {
		return timesheettypestable.getTableRows();
	}
	
	public WebElement getTableRowWithTimesheetType(String timesheettype) {
		List<WebElement> rows = getTimesheetTypesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]")).getText().equals(timesheettype)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTableTimesheetTypeDescription(String timesheettype) {
		String tsdescription = "";
		WebElement row = getTableRowWithTimesheetType(timesheettype);
		if (row != null) {
			tsdescription = row.findElement(By.xpath(".//td[4]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + timesheettype + " timesheet type");
		return tsdescription;
	}
	
	public String getTableTimesheetTypeEntryType(String timesheettype) {
		String tsentrytype = "";
		WebElement row = getTableRowWithTimesheetType(timesheettype);
		if (row != null) {
			tsentrytype = row.findElement(By.xpath(".//td[5]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + timesheettype + " timesheet type");
		return tsentrytype;
	}
	
	public boolean isTimesheetTypeExists(String timesheettype) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  timesheettypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + timesheettype + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public void clickEditTimesheetType(String timesheettype) throws InterruptedException {
		WebElement row = getTableRowWithTimesheetType(timesheettype);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + timesheettype + " timesheet type");
	}
	
	public void deleteTimesheetType(String timesheettype) {
		WebElement row = getTableRowWithTimesheetType(timesheettype);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + timesheettype + " timesheet type");	
		}
	}
	
	public void deleteTimesheetTypeAndCancelDeleting(String timesheettype) throws InterruptedException {
		WebElement row = getTableRowWithTimesheetType(timesheettype);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + timesheettype + " timesheet type");	
		}
	}
	
}
