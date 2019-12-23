package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReportsWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl07_Label1")
	private WebElement technicianCommissionLink;

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl66_Label1")
	private WebElement apadStatementLink;

	public ReportsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickTechnicianCommissionsLink() {
        Utils.clickElement(technicianCommissionLink);
	}

	public void clickAPADStatementLink() {
	    Utils.clickElement(apadStatementLink);
	}
}
