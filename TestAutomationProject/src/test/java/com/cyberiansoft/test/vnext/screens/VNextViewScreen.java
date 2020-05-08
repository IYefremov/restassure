package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VNextViewScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='view']")
	private WebElement viewscreen;

	@FindBy(xpath="//*[@class='estimation-details custom-estimation-details']")
	private WebElement estDetails;

	@FindBy(id="contentTable")
	private WebElement contentTable;


	final String startDateSubstring = "Date: ";

    public VNextViewScreen(WebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, Duration.ofSeconds(10)), this);
    }

    public void waitViewScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(viewscreen));
		//HelpingScreenInteractions.dismissHelpingScreenIfPresent();
	}

	public String getEstimationDateValue() {
    	waitViewScreenLoaded();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
    	final String startDateValue = estDetails.findElement(By.xpath(".//span[@data-path='Estimations.EstimationDate']/..")).getText();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    	return startDateValue.substring(startDateSubstring.length());
	}

	public String getCustomerNameValue() {
		waitViewScreenLoaded();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		final String customerFullName = contentTable.findElement(By.xpath(".//*[@class='client-cell']")).getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return customerFullName;
	}

	public String getCustomerAddressValue() {
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		final String customerAddress = contentTable.findElement(By.xpath(".//*[@class='address-cell']")).getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return customerAddress;
	}

	public String getCustomerEmailValue() {
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		final String customerEmail = contentTable.findElement(By.xpath(".//*[@data-path='Client.Email']")).getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return customerEmail;
	}

	public String getCustomerPhoneValue() {
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		final String customerPhone = contentTable.findElement(By.xpath(".//*[@data-path='Client.Phone']")).getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return customerPhone;
	}

	public boolean isServicePresent(String serviceName) {
		waitViewScreenLoaded();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		boolean exists = contentTable.findElements(By.xpath(".//span[@class='service-name-label' and contains(text(),'" + serviceName + "')]")).size() > 0;
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return exists;
	}

	public boolean isVehiclePartLinePresent(String vehiclePartName) {
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		boolean exists = contentTable.findElements(By.xpath(".//*[@class='vehicle-part custom-vehicle-part']/*[text()='" + vehiclePartName + "']")).size() > 0;
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return exists;
	}

	public boolean isVehiclePartInfoLinePresent(String serviceName, VehiclePartData vehiclePartData) {
    	String infoLine = serviceName + " (Severity: " + vehiclePartData.getVehiclePartSeverity() +
				"; Size: " + vehiclePartData.getVehiclePartSize() + ")";
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		boolean exists = contentTable.findElements(By.xpath(".//*[@class='line-item matrix-line-item custom-matrix-line-item']/*[contains(text(),'" + infoLine + "')]")).size() > 0;
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return exists;
	}

	public String getServiceNotesValue(String serviceName) {
		waitViewScreenLoaded();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		WebElement serviceParentCell = contentTable.findElement(By.xpath(".//*[contains(@class, 'line-item matrix-line-item')]/*[contains(text(), '" + serviceName + "')]/.."));
		String notestText = serviceParentCell.findElement(By.xpath(".//*[@class='service-item-notes']")).getText();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return  notestText;
	}

	public String getServiceOriginalAmount(String serviceName) {
		waitViewScreenLoaded();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		WebElement serviceParentCell = getServiceTableRow(serviceName);
		String originalAmaunt = serviceParentCell.findElement(By.xpath("./td[2]")).getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return  originalAmaunt;
	}

	public String getServiceSupplementAmount(String serviceName) {
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		WebElement serviceParentCell = getServiceTableRow(serviceName);
		String supplementAmaunt = serviceParentCell.findElement(By.xpath("./td[3]")).getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return  supplementAmaunt;
	}

	public String getServiceTotalAmount(String serviceName) {
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		WebElement serviceParentCell = getServiceTableRow(serviceName);
		String totalAmaunt = serviceParentCell.findElement(By.xpath("./td[4]")).getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return  totalAmaunt;
	}

	public String getSupplementAmount() {
    	final String supplementMatchString = "8Supplement Amount: ";
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		String supplementAmaunt = contentTable.findElement(By.xpath(".//td/b[contains(text(), '" + supplementMatchString + "')]")).
				getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return  supplementAmaunt.substring(supplementMatchString.length());
	}

	public String getOriginalAmount() {
		final String originalMatchString = "8Original Amount: ";
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(viewscreen.findElement(By.xpath(".//iframe[@class='printing-viewer']")));
		String supplementAmaunt = contentTable.findElement(By.xpath(".//td[contains(text(), '" + originalMatchString + "')]")).
				getText().trim();
		ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
		return  supplementAmaunt.substring(originalMatchString.length());
	}

	private WebElement getServiceTableRow(String serviceName) {
		return contentTable.findElement(By.xpath(".//tr/td[text()= '" + serviceName + "']/.."));
	}
}
