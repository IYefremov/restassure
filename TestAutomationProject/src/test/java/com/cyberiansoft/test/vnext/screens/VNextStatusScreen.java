package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextStatusScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@action='update-main-db']")
	private WebElement updateMainDBBtn;
	
	@FindBy(xpath="//*[@action='feedback']")
	private WebElement feedbackBtn;
	
	@FindBy(xpath="//*[@action='back-office']")
	private WebElement gotoBOBtn;

	@FindBy(xpath="//span[text()='Start sync']")
	private WebElement startSyncBtn;

    public VNextStatusScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}

}
