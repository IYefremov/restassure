package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmPasswordWebPage extends BaseWebPage {

	@FindBy(id = "tbUsrPwd")
	private TextField userpswfld;

	@FindBy(id = "tbUsrCfrmPwd")
	private TextField usercnfrmpswfld;
	
	@FindBy(id = "ctl00_Content_btnSubmit")
	private WebElement submitbtn;

	public ConfirmPasswordWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public BackOfficeLoginWebPage confirmUserPassword(String newpassword) {
		userpswfld.clearAndType(newpassword);
		usercnfrmpswfld.clearAndType(newpassword);
		submitbtn.click();
		return PageFactory.initElements(driver, BackOfficeLoginWebPage.class);
	}

}
