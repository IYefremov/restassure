package com.cyberiansoft.test.vnextbo.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextBORepairOrderDetailsPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//*[@data-bind='click: startRO']")
	private WebElement startorderbtn;
	
	@FindBy(xpath = "//div[@class='order-info-details']/div")
	private WebElement orderdetails;
	
	@FindBy(id = "orderServices")
	private WebElement orderservicestable;
	
	public VNextBORepairOrderDetailsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(startorderbtn));
	}
	
	public String getRepairOrderActivePhaseStatus() {
		return orderdetails.findElement(By.id("phaseName")).getText().trim();
	}
	
	public String getRepairOrderCompletedValue() {
		return orderdetails.findElement(By.id("progressBarText")).getText().trim();
	}
	
	public void expandRepairOrderServiceDetailsTable() {
		if (orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).isDisplayed()) {
			orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).click();
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.visibilityOf(orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-up5']"))));
		}
	}
	
	public void clickStartOrderButton() {
		startorderbtn.click();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.invisibilityOf(startorderbtn));
		waitABit(1000);
	}
	
	public boolean isStartOrderButtonVisible() {
		return startorderbtn.isDisplayed();
	}
	
	public String getRepairOrderServicesPhaseStatus() {
		return orderservicestable.findElement(By.xpath(".//div[@class='clmn_5']/div/div")).getText().trim();
	}
	
	public String getRepairOrderServicesStatus(String serviceName) {
		WebElement servicerow = getRepairOrderServiceColumn(serviceName);
		return servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div")).getText().trim();
	}
	
	public void changeStatusForrepairorderService(String serviceName, String newStatus) {
		waitABit(1000);
		WebElement servicerow = getRepairOrderServiceColumn(serviceName);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div/span/span")))).click();
		servicerow = getRepairOrderServiceColumn(serviceName);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div/span/span")))).click();
		waitABit(2000);
		List<WebElement> popups = driver.findElements(By.xpath("//div[@class='k-list-scroller']/ul[@data-role='staticlist']"));
		for (WebElement pp : popups)
			if (pp.isDisplayed()) {
				pp.findElement(By.xpath("./li[text()='" +  newStatus + "']")).click();
				
			}
		WebDriverWait wait = new WebDriverWait(driver, 15, 1);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='k-loading-mask']")));
		waitABit(1000);
	}
	
	public WebElement getRepairOrderServiceColumn(String serviceName) {
		WebElement servicerow = null;
		List<WebElement> servicesrows = orderservicestable.findElement(By.xpath(".//div[@class='innerTable']"))
				.findElements(By.xpath("./div[@class='serviceRow']"));
		for (WebElement srvrow : servicesrows)
			if (srvrow.findElement(By.xpath(".//div[@class='clmn_2']/strong")).getText().trim().equals(serviceName)) {
				servicerow = srvrow;
				break;
			}
		return servicerow;
	}

}
