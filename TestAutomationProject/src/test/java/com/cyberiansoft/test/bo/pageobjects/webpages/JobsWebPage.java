package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class JobsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable jobstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addjobbtn;
	
	//New job
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField jobnamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbDesc")
	private TextField jobdescfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlClient_Input")
	private TextField jobclientcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlClient_DropDown")
	private DropDown jobclientdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlParentClient_Input")
	private TextField jobparentclientcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlParentClient_DropDown")
	private DropDown jobparentclientdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dpStartingDate_dateInput")
	private TextField jobstartdatefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dpTerminationDate_dateInput")
	private TextField jobenddatefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingId2")
	private TextField jobaccidfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingId")
	private TextField jobaccid2fld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement addjobOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement addjobCancelbtn;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboCustomer_Input")
	private WebElement searchcustomercbx;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_txtSearch")
	private TextField searchjobfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(xpath = "//input[@title='Delete']")
	private WebElement deletemarker;

	public JobsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}
	
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyJobsTableColumnsAreVisible() {	
		Assert.assertTrue(jobstable.tableColumnExists("Job"));
		Assert.assertTrue(jobstable.tableColumnExists("Description"));
		Assert.assertTrue(jobstable.tableColumnExists("Client"));
		Assert.assertTrue(jobstable.tableColumnExists("Start Date"));
		Assert.assertTrue(jobstable.tableColumnExists("End Date"));
		Assert.assertTrue(jobstable.tableColumnExists("AccountingID"));
		Assert.assertTrue(jobstable.tableColumnExists("AccountingID2"));
	}	
	
	public String getTableJobDescription(String job) {
        return getJob(job, ".//td[4]");
	}

    public String getJob(String job, String jobLocator) {
        String jobdesc = "";
        WebElement row = getTableRowWithJob(job);
        if (row != null) {
            jobdesc = row.findElement(By.xpath(jobLocator))
                    .getText()
                    .replaceAll("\\u00A0", "")
                    .trim();
        } else
            Assert.fail("Can't find " + job + " job");
        return jobdesc;
    }

    public String getTableJobClient(String job) {
        return getJob(job, ".//td[5]");
	}
	
	public String getTableJobStartDate(String job) {
        return getJob(job, ".//td[6]");
	}
	
	public String getTableJobEndDate(String job) {
        return getJob(job, ".//td[7]");
	}
	
	public String getTableJobAccountingID(String job) {
        return getJob(job, ".//td[8]");
	}
	
	public String getTableJobAccountingID2(String job) {
        return getJob(job, ".//td[9]");
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
		waitABit(3000);
	}
	
	public int getJobsTableRowsCount() {
		return getJobsTableRows().size();
	}
	
	public List<WebElement>  getJobsTableRows() {
		return jobstable.getTableRows();
	}
	
	public void setJobSearchCriteria(String _job) {
		clearAndType(searchjobfld, _job);
	}

	//TODO
	public void selectSearchCustomer(String customer) {
		searchcustomercbx.click();
		searchcustomercbx.sendKeys(customer);
		waitABit(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='" + customer + "']"))).click();
	}
	
	public WebElement getTableRowWithJob(String job) {
		List<WebElement> rows = getJobsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]")).getText().contains(job)) {
				return row;
			}
		} 
		return null;
	}
	
	public void clickAddJobButton() {
		click(addjobbtn);
	}
	
	public void setNewJobName(String job) {
		clearAndType(jobnamefld, job);
	}
	
	public void setNewJobDescription(String jobdesc) {
		clearAndType(jobdescfld, jobdesc);
	}
	
	public void setNewJobStartDate(String startdate) {
		clearAndType(jobstartdatefld, startdate);
	}
	
	public void setNewJobEndDate(String enddate) {
		clearAndType(jobenddatefld, enddate);
	}
	
	public void setNewJobAccountingID(String accid) {
		clearAndType(jobaccidfld, accid);
	}
	
	public void setNewJobAccountingID2(String accid) {
		clearAndType(jobaccid2fld, accid);
	}
	
	public void selectJobClient(String clientname) {
		jobclientcmb.click();
		jobclientcmb.clearAndType(clientname);
		wait.until(ExpectedConditions.visibilityOf(jobclientdd.getWrappedElement()));
		waitABit(1000);
		jobclientdd.selectByVisibleText(clientname);
	}
	
	public void selectJobParentClient(String clientname) { 
		waitABit(300);
		wait.until(ExpectedConditions.elementToBeClickable(jobparentclientcmb.getWrappedElement())).click();
		jobparentclientcmb.click();
		jobparentclientcmb.clearAndType(clientname);
		wait.until(ExpectedConditions.visibilityOf(jobparentclientdd.getWrappedElement()));
		waitABit(1000);
		jobparentclientdd.selectByVisibleText(clientname);
	}
	
	public String getNewJobName() {
		return jobnamefld.getValue();
	}
	
	public void clickAddJobOKButton() {
	waitABit(300);
		Actions act  = new Actions(driver);
		act.click(addjobOKbtn).perform();
		waitForLoading();
	}
	
	public void clickAddJobCancelButton() {
		click(addjobCancelbtn);
	}
	
	public void createNewJob(String job) {
		clickAddJobButton();
		setNewJobName(job);
		clickAddJobOKButton();
	}
	
	
	
	public boolean isJobPresent(String job) {
        return jobstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + job + "']")).size() > 0;
	}
	
	public void clickEditJob(String job) {
		WebElement row = getTableRowWithJob(job);
		if (row != null) {
			clickEditTableRow(row);
		} else
            Assert.fail("Can't find " + job + " job");
	}
	
	public void deleteJob(String job) {
		WebElement row = getTableRowWithJob(job);
		if (row != null) {
			deleteTableRow(row);
		} else
            Assert.fail("Can't find " + job + " job");
	}
	
	public void deleteJobAndCancelDeleting(String job) {
		WebElement row = getTableRowWithJob(job);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else
            Assert.fail("Can't find " + job + " job");
	}
}
