package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOHeaderPanel extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//a[@data-bind='click: logout']")
	private WebElement logoutLink;
	
	@FindBy(xpath = "//div[@class='user']/span")
	private WebElement userProfileLink;
	
	@FindBy(id = "upgrade-banner")
	private WebElement upgradeNowButton;
	
	public VNextBOHeaderPanel() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
}