package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class WebPageWithTimeframeFilter extends WebPageWithPagination {
	
	@FindBy(xpath = "//*[contains(@id, 'filterer_ddlTimeframe_Input')]")
	private ComboBox searchtimeframecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'filterer_ddlTimeframe_DropDown')]")
	private DropDown searchtimeframedd;
	
	public WebPageWithTimeframeFilter(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void selectSearchTimeframe(WebConstants.TimeFrameValues timeframe) { 
		selectComboboxValue(searchtimeframecmb, searchtimeframedd, timeframe.getName());
	}
	
	public void verifyTableDateRangeForCurrentTablePage(LocalDate startrange, LocalDate endrange, List<WebElement> datecells) {
		DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern(BackOfficeUtils.getFullDateFormat());
		
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
