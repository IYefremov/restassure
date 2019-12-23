package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.enums.DateUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

class TimeframeFilter {
	
	@FindBy(xpath = "//*[contains(@id, 'filterer_ddlTimeframe_Input')]")
	private ComboBox searchtimeframecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'filterer_ddlTimeframe_DropDown')]")
	private DropDown searchtimeframedd;
	
	public void selectSearchTimeframe(WebConstants.TimeFrameValues timeframe) { 
		selectComboboxValue(searchtimeframecmb, searchtimeframedd, timeframe.getName());
	}
	
	public void verifyTableDateRangeForCurrentTablePage(Date startrange, Date endrange, List<WebElement> datecells) {
		DateFormat dateFormat = new SimpleDateFormat(DateUtils.FULL_DATE_FORMAT.getFormat());
		for (WebElement datecell : datecells) {
			try {
				Date datevalue = dateFormat.parse(datecell.getText());
				Assert.assertTrue((datevalue.after(startrange) & datevalue.before(endrange)), "Date " + datecell.getText() + " is not after " + startrange + " or not before " + endrange);
			} catch (ParseException e) {
				Assert.assertTrue(false, "Can't parse " + datecell.getText() + " value");
			}		
		}	
	}
	
	/*public void verifyTableDateRangeForAllTablePages(Date startrange, Date endrange, WebTable table, String datecolumnname) {		
		int pagenum =  Integer.valueOf(getLastPageNumber());	
		for (int i = 1; i <= pagenum; i++) {
			List<WebElement> datecells = table.getTableColumnCells(datecolumnname);
			verifyTableDateRangeForCurrentTablePage(startrange, endrange, datecells);
			if (i < pagenum)
				clickGoToNextPage();
		}
	}
	
	public void verifyTableDateRangeForFirstAndLastTablePages(Date startrange, Date endrange, WebTable table, String datecolumnname) {
		int pagenum =  Integer.valueOf(getLastPageNumber());
		for (int i = 1; i <= 3; i++) {
			List<WebElement> datecells = table.getTableColumnCells(datecolumnname);
			verifyTableDateRangeForCurrentTablePage(startrange, endrange, datecells);
			if (i < pagenum) {
				clickGoToLastPage();
			}
		}
	}*/

}
