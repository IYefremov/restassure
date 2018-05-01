package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularServicesScreen extends iOSRegularBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String servicesscreencapt = "Services";
	
	/*@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(accessibility = "Delete")
    private IOSElement deletebtn;
	
	@iOSFindBy(accessibility = "AvailableServicesSwitchButton")
    private IOSElement servicetypesbtn;
	
	@iOSFindBy(accessibility = "Price Matrices")
    private IOSElement pricematrixespopupname;
	
	@iOSFindBy(accessibility = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(accessibility = "Vehicle Part")
    private IOSElement vehiclepartsbtn;
	
	@iOSFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;
	
	@iOSFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;*/
	
	public RegularServicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Services")));
	}

	public void clickCancelButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}

	public boolean isDefaultServiceIsSelected() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='"
						+ defaultServiceValue + "']/XCUIElementTypeButton[@name='selected']").isDisplayed();
	}

	public boolean checkServiceIsSelected(String servicename) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(servicename)));
		
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='selected']")).size() > 0;
	}
	
	public int getNumberOfServiceSelectedItems(String servicename) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='selected']")).size();
	}
	
	public int getNumberOfSelectedServices() {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeButton[@name='selected']")).size();
	}

	
	public String getServicePriceValue(String servicename) {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[3]")).getAttribute("value");
	}
	
	public boolean isServiceIsSelectedWithServiceValues(String servicename, String pricevalue) {
		boolean selected = false;
		List<WebElement> services = appiumdriver.findElementByClassName("XCUIElementTypeTable").findElements(MobileBy.AccessibilityId(servicename));
		if (services.size() > 0) {
			if (services.size() > 1) {
				for (WebElement srvc : services) {
					if ((srvc.findElements(MobileBy.AccessibilityId("selected")).size() > 0) && (srvc.
							findElements(MobileBy.className("XCUIElementTypeStaticText")).get(2).getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
					pricevalue.replaceAll(" ", "")))) {
						selected = true;
						break;
					}
				}
			} else {
				if ((services.get(0).findElements(MobileBy.AccessibilityId("selected")).size() > 0) && (services.get(0).
						findElements(MobileBy.className("XCUIElementTypeStaticText")).get(2).getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
						pricevalue.replaceAll(" ", "")))) {
					selected = true;
				}
			}
		}
		return selected;
	}

	public int getServiceSelectedNumber(String servicename) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(servicename)).size();
	}
	
	public String getTotalAmaunt() {
		String totalAmaunt = "";
		if (appiumdriver.findElementsByName("TotalAmount").size() > 0)
			totalAmaunt = appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
		else
			totalAmaunt = appiumdriver.findElementByAccessibilityId("AmountTotal").getAttribute("value");
		return totalAmaunt;
	}
	
	public String getSubTotalAmaunt() {
		String subtotalAmaunt = "";
		if (appiumdriver.findElementsByName("SubtotalAmount").size() > 0)
			subtotalAmaunt = appiumdriver.findElementByName("SubtotalAmount").getAttribute("value");
		else
			subtotalAmaunt = appiumdriver.findElementByName("AmountSubtotal").getAttribute("value");
		return subtotalAmaunt;
	}

	public boolean isServiceTypeExists(String servicetype) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(servicetype)).size() > 0;
	}

	public void searchServiceByName(String servicename) {
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").click();
		appiumdriver.getKeyboard().sendKeys(servicename + "\n");

	}
	
	public void selectService(String servicename) {
		if (elementExists("Clear text"))
			appiumdriver.findElementByAccessibilityId("Search").clear();

		//WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		//wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(servicename)));

		IOSElement servicecell = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeTable").
				findElement(MobileBy.AccessibilityId(servicename));
		if (!servicecell.isDisplayed()) {
			if (appiumdriver.findElementsByAccessibilityId("Search").size() > 0)
				searchServiceByName(servicename);
		}

		if (appiumdriver.findElementByClassName("XCUIElementTypeTable").
				findElement(MobileBy.AccessibilityId(servicename)).findElements(MobileBy.AccessibilityId("unselected")).size() > 0)
			appiumdriver.findElementByClassName("XCUIElementTypeTable").
				findElement(MobileBy.AccessibilityId(servicename)).findElement(MobileBy.AccessibilityId("unselected")).click();
		else

			appiumdriver.findElementByClassName("XCUIElementTypeTable").
					findElement(MobileBy.AccessibilityId(servicename)).findElement(MobileBy.className("XCUIElementTypeImage")).click();
	}
	
	public void selectServicePanel(String servicepanelname) {

		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicepanelname + "']/XCUIElementTypeStaticText[@name='" + servicepanelname + "']/..")));
		appiumdriver.findElementByName(servicepanelname).click();
		//appiumdriver.findElementByName(servicepanelname).click();
	}
	
	public void selectServiceSubSrvice(String servicesubsrvicename) {
		appiumdriver.findElementByAccessibilityId(servicesubsrvicename).click();
	}
	
	public boolean isServiceWithSubSrviceSelected(String servicename, String servicesubsrvicename) {
		return appiumdriver.findElementByClassName("XCUIElementTypeTable").findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[@name='" + servicesubsrvicename + "']")).size() > 0;
	}
	
	
	public void selectSubService(String servicename) {
		if (elementExists(MobileBy.AccessibilityId("Clear text")))
			appiumdriver.findElementByAccessibilityId("Search").clear();
		if (!appiumdriver.findElementByClassName("XCUIElementTypeTable").
				findElement(MobileBy.AccessibilityId(servicename)).isDisplayed())
			searchServiceByName(servicename);
		IOSElement servicecell = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeTable").
				findElement(MobileBy.AccessibilityId(servicename)).
				findElement(MobileBy.AccessibilityId("unselected"));
		/*if (!servicecell.isDisplayed()) {
			if (appiumdriver.findElementsByAccessibilityId("Search").size() > 0)
				searchServiceByName(servicename);
		}*/
		servicecell.click();
	//	TouchAction action = new TouchAction(appiumdriver);
		//action.tap(element(servicecell,  servicecell.getLocation().getX()+2, servicecell.getLocation().getY()+2)).perform();
		
		
		
		//Assert.assertTrue(appiumdriver.findElementByClassName("XCUIElementTypeTable").
		//		findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='selected']")).size() > 0);
		/*
		Helpers.waitABit(300);
		appiumdriver.findElementByAccessibilityId(servicename).click();
		//appiumdriver.findElementByAccessibilityId("Search").sendKeys(servicename);
		Helpers.waitABit(500);
		
		if (appiumdriver.findElementsByAccessibilityId("Save").size() > 0) {
			//appiumdriver.findElementByAccessibilityId("Save").click();
		}
		else {
			if (appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='unselected']").size() > 0)
				new TouchAction(appiumdriver).tap(appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='unselected']")).perform();
			//WebElement par = getServiceTableCell(servicename);
			//new TouchAction(appiumdriver).tap(par.findElement(MobileBy.AccessibilityId("unselected"))).perform() ;
		}
		//appiumdriver.findElementByAccessibilityId("Cancel").click();
		//par.findElement(MobileBy.xpath(".//XCUIElementTypeButton[@name='unselected']")).click();
		 */
	}
	
	public WebElement getServiceTableCell(String servicename) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicename + "']/.."));
	}

	public RegularSelectedServiceDetailsScreen openCustomServiceDetails(String servicename) {
		if (elementExists("Clear text"))
			appiumdriver.findElementByAccessibilityId("Search").clear();
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + servicename + "']")).isDisplayed())
			if (appiumdriver.findElementsByAccessibilityId("Search").size() > 0)
				searchServiceByName(servicename);
		//swipeToElement(appiumdriver.
		//		findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + servicename + "']/..")));
		IOSElement el = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='custom detail button']"));
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(el.getLocation().getX()+2, el.getLocation().getY()+2).perform();
		
		/*Helpers.waitABit(2000);
		appiumdriver.findElementByAccessibilityId(servicename).click();
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='custom detail button']")).waitAction(1000).release().perform();
		if (appiumdriver.findElementsByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeOther/XCUIElementTypeStaticText[@value='" + servicename + "']").size() > 0) {
			//appiumdriver.findElementByAccessibilityId("Save").click();
		}
		
		WebElement opensrvbtn = appiumdriver.findElement(MobileBy.
				xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='custom detail button']"));	
		new TouchAction(appiumdriver).tap(opensrvbtn).perform() ;
		*/
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public RegularSelectedServiceDetailsScreen clickServiceCustomDetailButton(String service) {
		//appiumdriver.findElementByXPath("//UIAScrollView[2]/UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']").click();
		
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + service + "']/XCUIElementTypeButton[@name='custom detail button']").click();
		//Helpers.scroolToByXpath("//UIATableView[1]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']");
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public RegularSelectedServiceDetailsScreen openSelectedServiceDetails(String service) {
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + service + "']/XCUIElementTypeButton[@name='custom detail button']");
		if (selectedservices.size() > 1) {
			((WebElement) appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + service + "']/XCUIElementTypeButton[@name='custom detail button']").get(1)).click();
		} else {
			selectedservices.get(0).click();
		}
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void clickToolButton() {
		appiumdriver.findElementByAccessibilityId("services").click();
	}
	
	public void openCustomServiceDetailsByPartOfServiceName(String service) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[2]/UIATableCell[4]").click();
	}


	public RegularPriceMatrixScreen selectServicePriceMatrices(String servicepricematrices) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicepricematrices + "']")).click();
		return new RegularPriceMatrixScreen(appiumdriver);
	}
	
	public void setSelectedServiceRequestServicesQuantity(String servicename, String _quantity) throws InterruptedException {
		WebElement par = getServiceTableCell(servicename);
		par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity + "\n");
	}
	
	public void openServiceDetailsByIndex(String servicename, int servicedetailindex) {
		MobileElement serviceCell = ((MobileElement) appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='custom detail button']").get(servicedetailindex));
		if (serviceCell.isDisplayed())
			serviceCell.click();
		else {
			swipeToElement((MobileElement) appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='custom detail button']/..").get(servicedetailindex));
			((MobileElement) appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='custom detail button']").get(servicedetailindex)).click();
		}
	}
	
	public void setSelectedServiceRequestServicePrice(String servicename, String price) {
		//Helpers.waitABit(500);
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[2]").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[2]").clear();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[2]").sendKeys(price + "\n");
	}
	
	public boolean priceMatricesPopupIsDisplayed() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("Price Matrices")).isDisplayed();
	}

	public RegularPriceMatrixScreen selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByAccessibilityId(pricematrice).click();
		return new RegularPriceMatrixScreen(appiumdriver);
	}
	
	public void removeSelectedService(String servicename) {
		searchServiceByName(servicename);
		IOSElement el = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='selected']"));
		el.click();
		//TouchAction action = new TouchAction(appiumdriver);
		//action.tap(el.getLocation().getX()+2, el.getLocation().getY()+2).perform();
	}
	
	public void clickNotesButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Compose")).click();
	}
	
	public void clickVehiclePartsButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Vehicle Part")).click();
	}

	public static String getServicesScreenCaption() {
		return servicesscreencapt;
	}
	
	public void clickBackServicesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
		appiumdriver.findElement(MobileBy.name("Back")).click();
	}
	
	public void clickAddServicesButton() {	
		IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
		navbar.findElement(MobileBy.AccessibilityId("Add")).click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Services")));
		Helpers.waitABit(1000);
	}
	
	public void clickSaveAsFinal() {
		clickSaveButton();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Final")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Final")).click();
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
	}
	
	public void clickSaveAsDraft() {
		clickSaveButton();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Draft")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Draft")).click();
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
	}
	
	public String getListOfSelectedVehicleParts() {
		WebElement par = getServiceTableCell("Vehicle Part");	
		return par.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public boolean isServiceWithVehiclePartExists(String srvname, String srvvehiclepart) {
		return Helpers.elementExists(By.xpath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + 
				srvname + "]/XCUIElementTypeStaticText[@name='" + srvvehiclepart + "']"));
	}
	
	public boolean isServiceApproved(String srvname) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + 
				srvname + "']/XCUIElementTypeButton[@name='selected']")).size() > 0;
	}
	
	public boolean isServiceDeclinedSkipped(String srvname) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + 
				srvname + "']/XCUIElementTypeButton[@name='declined']")).size() > 0;
	}
	
	public boolean isNotesIconPresentForSelectedService(String servicename) {
		WebElement selectedservicestable = appiumdriver.findElementByClassName("XCUIElementTypeTable");
		return selectedservicestable.findElements(By.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeImage[@name='ESTIMATION_NOTES']")).size() > 0;
	}
	
	public boolean isNotesIconPresentForSelectedWorkOrderService(String servicename) {
		WebElement selectedservicestable = appiumdriver.findElementByClassName("XCUIElementTypeTable");
		return selectedservicestable.findElements(By.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeImage[@name='ORDER_NOTES']")).size() > 0;
	}
	
	public void clickTechnicianToolbarIcon() {
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("technician")).click();
	}
	
	public void changeTechnician(String servicetype, String techname) {
		appiumdriver.findElementByAccessibilityId(servicetype).click();
		/*if (appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView").size() > 0) {
			((IOSElement) appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView").get(1)).
			findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		} else {*/
		appiumdriver.findElementByAccessibilityId("DefaultEmployeeSelectorView").
			findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		//}
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Save']").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Service Types']/XCUIElementTypeButton[@name='Save']").click();
	}
	
	public void saveWorkOrder() {
		clickSaveButton();
	}

}
