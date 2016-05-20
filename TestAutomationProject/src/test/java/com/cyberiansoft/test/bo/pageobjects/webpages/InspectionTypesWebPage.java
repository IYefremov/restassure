package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class InspectionTypesWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00")
	private WebTable inspectiontypestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addinspectiontypesbtn;
	
	public InspectionTypesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(inspectiontypestable.getWrappedElement()));
	}

	public List<WebElement> getInspectionTypesTableRows() {
		return inspectiontypestable.getTableRows();
	}
	
	public WebElement getTableRowWithInspectionType(String insptype) {
		List<WebElement> rows = getInspectionTypesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + inspectiontypestable.getTableColumnIndex("Type") + "]")).getText().equals(insptype)) {
				return row;
			}
		} 
		return null;
	}
	
	public NewInspectionTypeDialogWebPage clickAddInspectionTypeButton() {
		clickAndWait(addinspectiontypesbtn);
		return PageFactory.initElements(
				driver, NewInspectionTypeDialogWebPage.class);
	}
	
	public boolean isInspectionTypeExists(String insptype) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  inspectiontypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + insptype + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public void deleteInspectionType(String insptype) {
		WebElement row = getTableRowWithInspectionType(insptype);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + insptype + " inspection type");			
	}
	
	public NewInspectionTypeDialogWebPage clickEditInspectionType(String insptype) {
		WebElement row = getTableRowWithInspectionType(insptype);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + insptype + " inspection type");	
		return PageFactory.initElements(
				driver, NewInspectionTypeDialogWebPage.class);
	}
	
	public InspectionTypesVehicleInfoSettingsWebPage clickInspectionVehicleInfoSettingLink(String insptype) {
		String mainWindowHandle = driver.getWindowHandle();
		WebElement row = getTableRowWithInspectionType(insptype);
		row.findElement(By.xpath(".//td[" + inspectiontypestable.getTableColumnIndex("Vehicle Info") + "]/a")).click();
		waitForNewTab();
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
			   driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(
				driver, InspectionTypesVehicleInfoSettingsWebPage.class);
	}
}
