package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInspectionsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addinspectionbtn;
	
	@FindBy(xpath="//div[@class='list-block list-block-search searchbar-found virtual-list']")
	private WebElement inspectionslist;
	
	@FindBy(xpath="//a[@action='back']/i")
	private WebElement backbtn;
	
	public VNextInspectionsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(addinspectionbtn));
		wait.until(ExpectedConditions.visibilityOf(inspectionslist));
	}
	
	public VNextCustomersScreen clickAddInspectionButton() {
		waitABit(3000);		
		tap(addinspectionbtn);
		log(LogStatus.INFO, "Tap Add inspection button");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		log(LogStatus.INFO, "Tap Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public void createSimpleInspection() {	
		VNextCustomersScreen customersscreen = clickAddInspectionButton();
		customersscreen.selectCustomer("Oksana Osmak");
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		waitABit(4000);
		inspinfoscreen.saveInspectionfromFirstScreen();
	}
	
	public String getFirstInspectionNumber() {
		return inspectionslist.findElement(By.xpath(".//div[@class='item-title']")).getText();
	}
	
	public String getFirstInspectionCustomerValue() {
		return inspectionslist.findElement(By.xpath(".//div[@action='select']/div[2]")).getText();
	}
	
	public String getFirstInspectionPrice() {
		return inspectionslist.findElement(By.xpath(".//div[@class='item-after']")).getText();
	}
	
	public String getInspectionPriceValue(String inspectionnumber) {
		String inspprice = null;
		WebElement inspcell = getInspectionCell(inspectionnumber);
		if (inspcell != null)
			inspprice = inspcell.findElement(By.xpath(".//div[@class='item-after']")).getText();
		else
			Assert.assertTrue(false, "Can't find inspection: " + inspectionnumber);
		return inspprice;	
	}
	
	public WebElement getInspectionCell(String inspectionnumber) {
		WebElement inspcell = null;
		List<WebElement> inspections = inspectionslist.findElements(By.xpath(".//a[@class='item-link item-content']"));
		for (WebElement invcell : inspections)
			if (invcell.findElements(By.xpath(".//div[@class='item-title' and text()='" + inspectionnumber + "']")).size() > 0)
				inspcell = invcell;
		return inspcell;
	}
	
	public VNextInspectionsMenuScreen clickOnInspectionByInspNumber(String inspnumber) {
		tap(inspectionslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + inspnumber + "']")));
		log(LogStatus.INFO, "Tap on Inspection: " + inspnumber);
		return new VNextInspectionsMenuScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen clickOpenInspectionToEdit(String inspnumber) {
		VNextInspectionsMenuScreen inspmenulist = clickOnInspectionByInspNumber(inspnumber);
		return inspmenulist.clickEditInspectionMenuItem();
	}
	
	public VNextEmailScreen clickOnInspectionToEmail(String inspnumber) {
		VNextInspectionsMenuScreen inspmenulist = clickOnInspectionByInspNumber(inspnumber);
		return inspmenulist.clickEmailInspectionMenuItem();
	}
	
	public List<WebElement> getInspectionsList() {
		return inspectionslist.findElements(By.xpath("./ul/li"));
	}
}
