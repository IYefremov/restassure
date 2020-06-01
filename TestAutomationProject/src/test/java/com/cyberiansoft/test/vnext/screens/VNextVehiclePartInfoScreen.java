package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextVehiclePartInfoScreen extends VNextBaseScreen {

	@FindBy(xpath = "//div[@data-page='matrix-info']")
	private WebElement vehiclepartinfoscreen;

	@FindBy(xpath = "//*[@action='size']")
	private WebElement vehiclepartsizeselect;

	@FindBy(xpath = "//*[@action='severity']")
	private WebElement vehiclepartseverityselect;

	@FindBy(xpath = "//div[@input='price']")
	private WebElement vehiclepartpricefld;

	@FindBy(xpath = "//*[@data-autotests-id='available-services-list']/div")
	private List<ServiceListItem> servicesList;

	@FindBy(xpath = "//*[@action='notes']")
	private WebElement notesbutton;

	@FindBy(xpath = "//*[@action='save']")
	private WebElement savebtn;

	public VNextVehiclePartInfoScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}

	//todo: rewrite
	public void selectVehiclePartSize(String vehiclepartsize) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='size']")));
		tap(vehiclepartsizeselect);
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='select-item']/div/div[contains(text(), '" + vehiclepartsize + "')]")));
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-item']/div/div[contains(text(), '" + vehiclepartsize + "')]")));
	}

	//todo: rewrite
	public void selectVehiclePartSeverity(String vehiclepartseverity) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='severity']")));
		tap(vehiclepartseverityselect);
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='select-item']/div/div[contains(text(), '" + vehiclepartseverity + "')]")));
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-item']/div/div[contains(text(), '" + vehiclepartseverity + "')]")));
	}


	//todo: make a step
	public void selectVehiclePartAdditionalService(String serviceName) {
		WaitUtils.getGeneralFluentWait().until(driver -> {
			getServiceListItem(serviceName).clickAddService();
			return true;
		});
	}

	public void openVehiclePartLaborServiceDetails(String additionalservicename) {
		WebElement webElement = WaitUtils.waitUntilElementIsClickable(By.xpath("//div[@class='checkbox-item-title' and text()='" + additionalservicename + "']"));
		tap(webElement);
	}

	public ServiceListItem getServiceListItem(String serviceName) {
		return servicesList.stream().filter(listElement -> listElement.getServiceName().equals(serviceName)).findFirst().orElseThrow(() -> new RuntimeException("service not found " + serviceName));
	}

	public void openServiceDetailsScreen(String serviceName) {
		WaitUtils.getGeneralFluentWait().until(driver -> {
			getServiceListItem(serviceName).openServiceDetails();
			return true;
		});
	}

	public String getMatrixServiceTotalPriceValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartinfoscreen.findElement(By.xpath(".//span[@class='money-wrapper']"))));
		return vehiclepartinfoscreen.findElement(By.xpath(".//span[@class='money-wrapper']")).getText();
	}

	public void clickSaveVehiclePartInfo() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(savebtn));
		tap(savebtn);
	}

	public VNextNotesScreen clickMatrixServiceNotesOption() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(notesbutton));
		tap(notesbutton);
		return new VNextNotesScreen();
	}

	public void switchToSelectedServicesView() {
		tap(appiumdriver.findElement(By.xpath(".//*[@action='selected']")));
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='selected' and @class='button active']")));
		wait = new WebDriverWait(appiumdriver, 5);
	}

	private WebElement getSelectedServicesList() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		return wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(By.xpath(".//*[@data-autotests-id='all-services']"))));
	}

	private List<WebElement> getServicesListItems() {
		return getSelectedServicesList().findElements(By.xpath(".//*[@action='open-added-service-details']"));
	}

	private WebElement getSelectedServiceCell(String serviceName) {
		return getServicesListItems().stream().filter(element -> element.
				findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim().
				equals(serviceName)).findFirst()
				.orElseThrow(() -> new RuntimeException("Answer not found"));
	}

	public void expandSelectedServiceDetails(String serviceName) {
		tap(getSelectedServiceCell(serviceName));
	}
}
