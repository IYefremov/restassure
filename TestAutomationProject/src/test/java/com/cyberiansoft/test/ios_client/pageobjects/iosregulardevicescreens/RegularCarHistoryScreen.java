package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularCarHistoryScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Search\"]")
    private IOSElement searchbtn;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIASearchBar[1]")
    private IOSElement searchbar;
	
	@iOSFindBy(uiAutomator = ".popover().buttons()[\"Close\"]")
    private IOSElement closesearchbtn;
	
	@iOSFindBy(name = "Switch to web")
    private IOSElement switchtowebbtn;
	
	@iOSFindBy(name = "Invoices")
    private IOSElement invoicesmenu;
	
	@iOSFindBy(name = "Work Orders")
    private IOSElement myworkordersmenumenu;
	
	int xx = 0;
	int yy = 0;
	
	public RegularCarHistoryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn));
		xx = searchbtn.getLocation().getX() + searchbtn.getSize().getWidth() + 50;

		yy = searchbtn.getLocation().getY() + 20;
	}
	
	public void searchCar(String vin)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn)).click();
		Helpers.keyboadrType(vin);
		appiumdriver.findElement(By.xpath("//UIAKeyboard[1]/UIAButton[@name=\"Search\"]")).click();
	}
	
	public void clickCancelSearchButton() throws InterruptedException {
		Thread.sleep(2000);
		TouchAction action = new TouchAction(appiumdriver);
		action.press(xx,yy).release().perform();
	}
	
	public void clickFirstCarHistoryInTable() {		
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[0]")).click();
	}
	
	public String getFirstCarHistoryValueInTable() {		
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]").getAttribute("name");
	}
	
	public String getFirstCarHistoryDetailsInTable() {		
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[2]").getAttribute("name");
	}
	
	public void clickFirstCar() {		
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]").click();
	}
	
	public void clickSwitchToWeb() {		
		switchtowebbtn.click();
	}
	
	public RegularMyInvoicesScreen clickCarHistoryInvoices() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(invoicesmenu)).click();
		return new RegularMyInvoicesScreen(appiumdriver);
	}
	
	public void clickCarHistoryMyWorkOrders() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(myworkordersmenumenu)).click();
	}

}
