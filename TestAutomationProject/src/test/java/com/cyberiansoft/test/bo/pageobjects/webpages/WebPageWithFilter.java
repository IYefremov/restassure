package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.enums.DateUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class WebPageWithFilter extends WebPageWithPagination {

	@FindBy(xpath = "//input[contains(@id, 'filterer_ddlTimeframe_Input')]")
	private ComboBox searchtimeframecmb;
	
	@FindBy(xpath = "//div[contains(@id, 'filterer_ddlTimeframe_DropDown')]")
	private DropDown searchtimeframedd;

    @FindBy(xpath = "//input[contains(@id, 'filterer_ddlPORequired_Input')]")
    private ComboBox searchBillingCmb;

    @FindBy(xpath = "//div[contains(@id, 'filterer_ddlPORequired_DropDown')]")
    private DropDown searchBillingDropDown;

    @FindBy(xpath = "//input[contains(@id, 'filterer_txtInvoiceNo')]")
    private TextField invoiceField;

    public WebPageWithFilter selectBillingOption(WebConstants.BillingValues billingValues) {
        selectComboboxValue(searchBillingCmb, searchBillingDropDown, billingValues.getName());
        return this;
    }

	public WebPageWithFilter(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public WebPageWithFilter selectSearchTimeFrame(WebConstants.TimeFrameValues timeframe) {
		selectComboboxValue(searchtimeframecmb, searchtimeframedd, timeframe.getName());
		return this;
	}

	public WebPageWithFilter insertInvoice(String invoiceName) {
        clearAndType(invoiceField, invoiceName);
        return this;
    }
	
	public void verifyTableDateRangeForCurrentTablePage(LocalDate startrange, LocalDate endrange, List<WebElement> datecells) {
		DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getDate());
		for (WebElement datecell : datecells) {
			LocalDate datevalue = LocalDate.parse(datecell.getText(), dateFormat);
			Assert.assertTrue((datevalue.isAfter(startrange) & datevalue.isBefore(endrange)), "Date " + datecell.getText() + " is not after " + startrange + " or not before " + endrange);
		}
	}
	
	public void verifyTableDateRangeForCurrentTablePage(LocalDate startrange, LocalDate endrange, List<WebElement> datecells, DateTimeFormatter dateFormat) {
		for (WebElement datecell : datecells) {
			LocalDate datevalue = LocalDate.parse(datecell.getText(), dateFormat);
			Assert.assertTrue((datevalue.isAfter(startrange) & datevalue.isBefore(endrange)), "Date " + datecell.getText() + " is not after " + startrange + " or not before " + endrange);		
		}	
	}
	
	public void verifyTableDateRangeForAllTablePages(LocalDate startrange, LocalDate endrange, WebTable table, String datecolumnname) {
		int pagenum =  Integer.valueOf(getLastPageNumber());
		for (int i = 1; i <= pagenum; i++) {
			List<WebElement> datecells = table.getTableColumnCells(datecolumnname);
			verifyTableDateRangeForCurrentTablePage(startrange, endrange, datecells);
			if (i < pagenum)
				clickGoToNextPage();
		}
	}
	
	public void verifyTableDateRangeForAllTablePages(LocalDate startrange, LocalDate endrange, WebTable table, String datecolumnname, DateTimeFormatter dateFormat) {		
		int pagenum =  Integer.valueOf(getLastPageNumber());	
		for (int i = 1; i <= pagenum; i++) {
			List<WebElement> datecells = table.getTableColumnCells(datecolumnname);
			verifyTableDateRangeForCurrentTablePage(startrange, endrange, datecells, dateFormat);
			if (i < pagenum)
				clickGoToNextPage();
		}
	}
	
	public void verifyTableDateRangeForFirstAndLastTablePages(LocalDate startrange, LocalDate endrange, WebTable table, String datecolumnname) {
		int pagenum =  Integer.valueOf(getLastPageNumber());
		for (int i = 1; i <= 3; i++) {
			List<WebElement> datecells = table.getTableColumnCells(datecolumnname);
			verifyTableDateRangeForCurrentTablePage(startrange, endrange, datecells);
			if (i < pagenum) {
				clickGoToLastPage();
			}
		}
	}
	
	public void verifyTableDateRangeForFirstAndLastTablePages(LocalDate startrange, LocalDate endrange, WebTable table, String datecolumnname, DateTimeFormatter dateFormat) {
		int pagenum =  Integer.valueOf(getLastPageNumber());
		for (int i = 1; i <= 3; i++) {
			List<WebElement> datecells = table.getTableColumnCells(datecolumnname);
			verifyTableDateRangeForCurrentTablePage(startrange, endrange, datecells, dateFormat);
			if (i < pagenum) {
				clickGoToLastPage();
			}
		}
	}

}
