package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class VehiclePartsWebPage extends WebPageWithPagination {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable vehiclepartstable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addvehiclepartbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField vehiclepartnamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_lAvailable")
	private WebElement availableservicescmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_lAssigned")
	private WebElement assignedservicescmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_btnAddToAssigned")
	private WebElement selectaddtoassignedbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_btnAddToAvailable")
	private WebElement selectaddtoavailablebtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_btnAllToAssigned")
	private WebElement selectallavailableservicesbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newvehiclepartOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newvehiclepartCancelbtn;

	public VehiclePartsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void verifyVehiclePartsColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(vehiclepartstable.getWrappedElement()));
		Assert.assertTrue(vehiclepartstable.isTableColumnExists("Part"));
	}
	
	public List<WebElement> getVehiclePartsTableRows() {
		return vehiclepartstable.getTableRows();
	}
	
	public void clickAddVehiclePartButton() {
		clickAndWait(addvehiclepartbtn);
	}
	
	public void createNewVehicleParWithAllServicesSelected(String vehiclepart) {
		clickAddVehiclePartButton();
		setVehiclePartName(vehiclepart);
		selectAllServicesForVehiclePart();
		saveNewVehiclePart();
	}
	
	public void setVehiclePartNewName(String vehiclepart, String newvehiclepartname) {
		clickEditButtonForVehiclePart(vehiclepart);
		setVehiclePartName(newvehiclepartname);
		saveNewVehiclePart();
	}
	
	public void setVehiclePartName(String vehiclepartname) {
		wait.until(ExpectedConditions.visibilityOf(vehiclepartnamefld.getWrappedElement()));
		getVehiclePartNameField().clear();
		getVehiclePartNameField().sendKeys(vehiclepartname);
	}
	
	public WebElement getVehiclePartNameField() {
		wait.until(ExpectedConditions.visibilityOf(vehiclepartnamefld.getWrappedElement()));
		return vehiclepartnamefld.getWrappedElement();
	}
	
	public void selectAllServicesForVehiclePart() {
		List<WebElement> availableoptions = getAvailableServicesList();
		click(selectallavailableservicesbtn);
		List<WebElement> selectedoptions = getAssignedServicesList();
		Assert.assertEquals(selectedoptions.size(), availableoptions.size());		
	}
	
	public void assignServiceForVehiclePart(String servicename) {
		selectAvailableServiceForVehiclePart(servicename);
		click(selectaddtoassignedbtn);		
	}
	
	public void selectAvailableServiceForVehiclePart(String servicename) {
		Select assignedservices = new Select(availableservicescmb);
		assignedservices.selectByVisibleText(servicename);		
	}
	
	public void selectAssignedServiceForVehiclePart(String servicename) {
		Select assignedservices = new Select(assignedservicescmb);
		assignedservices.selectByVisibleText(servicename);		
	}
	
	public void unassignServiceForVehiclePart(String servicename) {
		selectAssignedServiceForVehiclePart(servicename);
		click(selectaddtoavailablebtn);		
	}
	
	public List<WebElement> getAvailableServicesList() {
		Select assignedservices = new Select(availableservicescmb);
		return assignedservices.getOptions();
	}
	
	public List<WebElement> getAssignedServicesList() {
		Select assignedservices = new Select(assignedservicescmb);
		return assignedservices.getOptions();
	}
	
	public void saveNewVehiclePart() {	
		clickAndWait(newvehiclepartOKbtn);
	}
	
	public void cancelNewVehiclePart() {	
		click(newvehiclepartCancelbtn);
	}
	
	public void clickEditButtonForVehiclePart(String vehiclepart) {
		List<WebElement> vehiclepartsrows = getVehiclePartsTableRows();
		for (WebElement vehiclepartsrow : vehiclepartsrows) {
			if (vehiclepartsrow.getText().contains(vehiclepart)) {
				vehiclepartsrow.findElement(By.xpath(".//td/input[@title='Edit']")).click();
				waitUntilPageReloaded();
				break;
			}
		}
	}
	
	public void deleteVehiclePart(String vehiclepart) {
		List<WebElement> vehiclepartsrows = getVehiclePartsTableRows();
		for (WebElement vehiclepartsrow : vehiclepartsrows) {
			if (vehiclepartsrow.getText().contains(vehiclepart)) {
				vehiclepartsrow.findElement(By.xpath(".//td/input[@title='Delete']")).click();
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				alert.accept();
				waitUntilPageReloaded();
				break;
			}
		}
	}
	
	public boolean isVehiclePartExists(String vehiclepart) {
		boolean exists =  vehiclepartstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + vehiclepart + "']")).size() > 0;
		return exists;
	}

}
