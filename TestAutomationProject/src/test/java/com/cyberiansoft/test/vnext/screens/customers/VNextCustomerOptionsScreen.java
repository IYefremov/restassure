package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextCustomerOptionsScreen extends VNextBaseScreen {

	@FindBy(xpath="//div[@data-automation-id='view']")
	private WebElement viewButton;

	@FindBy(xpath = "//div[@data-automation-id='presetCustomer']")
	private WebElement setAsDefaultButton;

	@FindBy(xpath = "//div[@data-automation-id='customerContacts']")
	private WebElement customerContactsButton;

	@FindBy(xpath = "//div[@class='actions-menu-close']")
	private WebElement closeButton;

	public VNextCustomerOptionsScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}
}