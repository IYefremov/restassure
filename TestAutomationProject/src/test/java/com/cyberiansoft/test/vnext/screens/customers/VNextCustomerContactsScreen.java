package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.customers.CustomerContactListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextCustomerContactsScreen extends VNextBaseScreen {

	@FindBy(xpath="//a[@class='floating-button color-red']")
	private WebElement plusButton;

	@FindBy(xpath="//a[@class='floating-button color-red']/i[@class='icon icon-close']")
	private WebElement closeButton;

	@FindBy(xpath="//div[@data-autotests-id='contacts-list']")
	private WebElement contactsList;

	@FindBy(xpath="//div[@class='page-content']//div[@class='list-item']")
	private List<CustomerContactListElement> contactsNamesList;

	@FindBy(xpath="//a[@action='add_from_device_contact']/span[@class='speed-dial-icon']")
	private WebElement fromContactsIcon;

	@FindBy(xpath="//a[@action='add_from_device_contact']/span[@class='speed-dial-label']")
	private WebElement fromContactsButton;

	@FindBy(xpath="//a[@action='add_customer']/span[@class='speed-dial-icon']")
	private WebElement newContactIcon;

	@FindBy(xpath="//a[@action='add_customer']/span[@class='speed-dial-label']")
	private WebElement newContactButton;

	public VNextCustomerContactsScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}
}