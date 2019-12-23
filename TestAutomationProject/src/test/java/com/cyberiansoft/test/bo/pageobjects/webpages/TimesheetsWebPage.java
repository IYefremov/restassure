package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TimesheetsWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl00_Label1")
    private WebElement timesheetsBTN;

	public TimesheetsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickTimesheets() {
        Utils.clickElement(timesheetsBTN);
	}
}
