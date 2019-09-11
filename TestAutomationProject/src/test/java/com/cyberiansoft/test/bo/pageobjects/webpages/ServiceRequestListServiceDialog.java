package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class ServiceRequestListServiceDialog extends BaseWebPage {

	@FindBy(xpath = "//div[@class='infoBlock-item infoBlock-edit servicesBlock']")
	private WebElement servicesBlock;

	@FindBy(id = "Card_comboService_Arrow")
	private WebElement serviceComboArrow;

	@FindBy(xpath = "//form/div[@class='rcbSlide servicesBlock_drop']")
	private WebElement serviceComboDropDown;

	@FindBy(xpath = "//form/div[@class='rcbSlide servicesBlock_drop']//ul/li/input[@class='templateServiceCheckbox']")
	private List<WebElement> serviceComboDropDownCheckboxOptions;

	@FindBy(id = "Card_btnAddServiceToList")
	private WebElement addServiceOptionButton;

	@FindBy(xpath = "//div[@class='container-service container-service-selected']")
	private List<WebElement> selectedServiceContainer;

	@FindBy(xpath = "//div[@class='container-service container-service-selected']/span[@id='showVehiclePart']")
	private WebElement selectedServiceContainerFirstVehiclePart;

	@FindBy(xpath = "//div[@class='infoBlock-list-doneBtn rp-btn-blue']")
	private WebElement servicesDoneButton;

	@FindBy(xpath = "//div[@class='infoBlock-cancelBtn cancelList rp-btn-grey']")
	private WebElement servicesCancelButton;

	public ServiceRequestListServiceDialog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void openServicesDropDown() {
		wait.until(ExpectedConditions.textToBePresentInElement(serviceComboArrow, "select"));
		wait.until(ExpectedConditions.elementToBeClickable(serviceComboArrow)).click();
		try {
			wait.until(ExpectedConditions.attributeContains(serviceComboDropDown, "display", "block"));
		} catch (Exception e) {
			waitABit(2000);
		}
	}

	public String getAllAvailableServices() {
		wait.until(ExpectedConditions.elementToBeClickable(serviceComboArrow)).click();
		wait.until(ExpectedConditions.attributeContains(serviceComboDropDown, "display", "block"));
		return driver.findElement(By.id("Card_comboService_MoreResultsBox")).findElements(By.tagName("b")).get(2)
				.getText();
	}

	public int countAvailableServices() {
		if (!getBrowserType().contains("edge")) {
			return wait.until(ExpectedConditions.visibilityOfAllElements(serviceComboDropDownCheckboxOptions)).size();
		} else {
			try {
				wait.ignoring(Exception.class).until(e -> serviceComboDropDownCheckboxOptions.size() > 0);
			} catch (TimeoutException ignored) {
				waitABit(3000);
			}
			return serviceComboDropDownCheckboxOptions.size();
		}
	}

	public void checkRandomServiceOption() {
		int i = RandomUtils.nextInt(0, countAvailableServices());
		try {
			wait
					.ignoring(WebDriverException.class)
					.until(ExpectedConditions.elementToBeClickable(serviceComboDropDownCheckboxOptions.get(i)))
					.click();
		} catch (Exception e) {
			waitABit(3000);
			wait.until(ExpectedConditions.elementToBeClickable(serviceComboDropDownCheckboxOptions.get(i))).click();
		}
	}

	public void clickAddServiceOption() {
		wait.until(ExpectedConditions.elementToBeClickable(addServiceOptionButton)).click();
		try {
			if (getBrowserType().contains("edge")) {
				wait.until(ExpectedConditions.attributeContains(serviceComboDropDown, "display", "null"));
			} else {
				wait.until(ExpectedConditions.attributeContains(serviceComboDropDown, "display", "none"));
			}
		} catch (Exception ignored) {
		}
	}

	public void clickVehiclePart() {
		wait.until(ExpectedConditions.elementToBeClickable(selectedServiceContainerFirstVehiclePart)).click();
	}

	public boolean isSelectedServiceContainerDisplayed() {
		return !wait.until(ExpectedConditions.visibilityOfAllElements(selectedServiceContainer)).isEmpty();
	}

	public int getNumberOfSelectedServiceContainersDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfAllElements(selectedServiceContainer)).size();
	}

	public void verifyOneServiceContainerIsDisplayed() {
		Assert.assertEquals(getNumberOfSelectedServiceContainersDisplayed(), 1,
				"The number of service containers displayed is not 1");
	}

	public void clickDoneServicesButton() {
		wait.until(ExpectedConditions.elementToBeClickable(servicesDoneButton)).click();
	}

	public void clickCancelServicesButton() {
		wait.until(ExpectedConditions.elementToBeClickable(servicesCancelButton)).click();
	}
}
