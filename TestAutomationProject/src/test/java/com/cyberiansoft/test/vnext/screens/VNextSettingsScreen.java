package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VNextSettingsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@action='manual-update']")
	private WebElement manualsendradio;
	
	@FindBy(xpath="//*[@action='back']")
	private WebElement backbtn;

    public VNextSettingsScreen() {
	}
	
	public void setManualSendOn() {
		if (manualsendradio.getAttribute("checked") == null)
			tap(manualsendradio);
	}
	
	public void setManualSendOff() {
		if (manualsendradio.getAttribute("checked") != null)
			tap(manualsendradio);
	}

}
