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

public class VNextBOInspectionsWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//ul[@data-automation-id='inspectionsList']")
	private WebElement inspectionslist;
	
	@FindBy(xpath = "//div[@class='entity-details__content']")
	private WebElement inspectiondetailspanel;
	
	@FindBy(xpath = "//table[@data-automation-id='inspectionsDetailsServicesList']")
	private WebElement inspectionserviceslist;
	
	@FindBy(xpath = "//ul[@data-automation-id='inspectionsDetailsDamagesList']")
	private WebElement imagelegend;
	
	@FindBy(xpath = "//button[@data-automation-id='inspectionsDetailsPrintButton']")
	private WebElement printinspectionicon;
	
	public VNextBOInspectionsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(inspectionslist));
	}
	
	public void selectInspectionInTheList(String inspnumber) {
		inspectionslist.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b[text()='" + inspnumber + "']")).click();
		waitABit(4000);
	}
		
	public boolean isServicePresentForSelectedInspection(String servicename) {
		return inspectionserviceslist.findElements(By.xpath("./tbody/tr/td[text()='" + servicename + "']")).size() > 0;
	}
	
	public boolean isServiceNotesIconDisplayed(String servicename) {
		WebElement sepviserow = inspectionserviceslist.findElement(By.xpath("./tbody/tr/td[text()='" + servicename + "']/.."));
		return sepviserow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).isDisplayed();
	}
	
	public boolean isMatrixServiceExists(String matrixservicename) {
		WebElement matrixsepviserow = inspectionserviceslist.findElement(By.xpath(".//tr[@class='entity-details__matrix']"));
		return matrixsepviserow.findElement(By.xpath("./td[contains(text(), '" +  matrixservicename + "')]")).isDisplayed();
	}

	public List<WebElement> getAllMatrixServicesRows(String matrixservicename) {
		return inspectionserviceslist.findElements(By.xpath(".//tr[@class='entity-details__matrix']"));
	}

	
	public boolean isImageExistsForMatrixServiceNotes(WebElement matrixsepviserow) {
		boolean exists = false;
		matrixsepviserow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).click();
		WebElement notesmodaldlg = new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("notesViewer"))));
		exists = notesmodaldlg.findElement(By.xpath("//div[@class='image-notes__preview--modal']")).isDisplayed();
		new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.elementToBeClickable(notesmodaldlg.findElement(By.xpath(".//button[@class='close']")))).click();
		waitABit(500);
		return exists;	
	}
	
	public void clickServiceNotesIcon(String servicename) {
		WebElement sepviserow = inspectionserviceslist.findElement(By.xpath("./tbody/tr/td[text()='" + servicename + "']/.."));
		sepviserow.findElement(By.xpath("./td[@class='notes__service-table--centered']/i[@title='Notes']")).click();
	}
	
	public boolean isImageExistsForServiceNote(String servicename) {
		boolean exists = false;
		clickServiceNotesIcon(servicename);
		WebElement notesmodaldlg = new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("notesViewer"))));
		exists = notesmodaldlg.findElement(By.xpath("//div[@class='image-notes__preview--modal']")).isDisplayed();
		new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.elementToBeClickable(notesmodaldlg.findElement(By.xpath(".//button[@class='close']")))).click();
		waitABit(500);
		return exists;		
	}
	
	public boolean isImageLegendContainsBreakageIcon(String brackageicontype) {
		return imagelegend.findElements(By.xpath("./li[contains(text(), '" + brackageicontype + "')]")).size() > 0;
	}
	
	public String getSelectedInspectionCustomerName() {
		return inspectiondetailspanel.findElement(By.xpath(".//span[@data-bind='text:clientName']")).getText();
	}
	
	public String getSelectedInspectionTotalAmauntValue() {
		return inspectiondetailspanel.findElement(By.xpath(".//th[@data-bind='text: amount']")).getText();
	}
	
	public VNextBOInspectionInfoWebPage clickSelectedInspectionPrintIcon() {
		String mainWindowHandle = driver.getWindowHandle();
		printinspectionicon.click();
		waitForNewTab();
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(
			driver, VNextBOInspectionInfoWebPage.class);
	}
	

}
