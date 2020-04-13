package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.customers.CustomersListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextCustomersScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='customers-list']")
	private WebElement customersScreen;

	@FindBy(xpath = "//*[@data-autotests-id='customers-list']")
	private WebElement customersList;

	@FindBy(xpath = "//*[@data-autotests-id='customers-list']/div")
	private List<CustomersListElement> customersListArray;

	@FindBy(xpath = "//*[@action='select-retail']")
	private WebElement retailCustomerTab;

	@FindBy(xpath = "//*[@action='select-wholesale']")
	private WebElement wholesaleCustomerTab;

	@FindBy(xpath = "//div[@class='notice-plate']")
	private WebElement presetCustomerPanel;

	@FindBy(xpath = "//*[@class='notice-plate-info-name']")
	private WebElement clientMode;

	public VNextCustomersScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}

	public void clickAddCustomerButton() {
		tap(customersScreen.findElement(By.xpath(".//a[@class='floating-button color-red']")));
		tap(customersScreen.findElement(By.xpath(".//*[@action='add_customer' and @class='customers-button']")));
	}

	public boolean isAddCustomerButtonDisplayed() {
    	WaitUtils.waitUntilElementIsClickable(customersList);
		return ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(By.xpath("//*[@action='add']")).size() > 0;
	}

	public boolean isNothingFoundCaptionDisplayed() {
		return customersScreen.findElement(By.xpath(".//b[text()='Nothing found']")).isDisplayed();
	}

	public void selectCustomer(AppCustomer customer) {

		WaitUtils.waitUntilElementIsClickable(customersList);
		if (customer.isWholesale()) {
			switchToWholesaleMode();
		} else {
			switchToRetailMode();
		}
		SearchSteps.openSearchMenu();
		SearchSteps.fillTextSearch(customer.getFullName());
		CustomersListElement customersListElement = getCustomerElement(customer.getFullName());
		customersListElement.selectCustomer();
	}

	public CustomersListElement getCustomerElement(String fullName) {
		return customersListArray.stream().filter(listElement -> listElement.getCustomerFullName().equals(fullName)).findFirst().orElseThrow(() -> new RuntimeException("customer not found " + fullName));
	}

	public void switchToRetailMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(retailCustomerTab));
		WaitUtils.getGeneralFluentWait().until(driver -> {
			Actions actions = new Actions(appiumdriver);
			actions.moveToElement(retailCustomerTab, 30, 0).click().perform();
			return true;
		});
	}

	public void switchToWholesaleMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(wholesaleCustomerTab));
		tap(wholesaleCustomerTab);
	}



	public boolean isCustomerExists(AppCustomer customer) {
		WaitUtils.waitUntilElementIsClickable(customersList);
		SearchSteps.searchByText(customer.getFullName());
		return customersListArray.stream().anyMatch(customersListElement -> customersListElement.getCustomerFullName().equals(customer.getFullName()));
	}

	public String getDefaultCustomerValue() {
		return clientMode.getText();
	}

	public void resetPresetCustomer() {
		presetCustomerPanel.findElement(By.xpath(".//div[@class='notice-plate-remove']")).click();
	}

	public void openCustomerForEdit(AppCustomer customer) {
		selectCustomer(customer);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
	}
}
