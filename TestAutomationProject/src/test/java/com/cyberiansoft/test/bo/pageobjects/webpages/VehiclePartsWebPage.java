package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

public class VehiclePartsWebPage extends WebPageWithPagination {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable vehiclepartstable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addvehiclepartbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField vehiclepartnamefld;

    @FindBy(xpath = "//select[@id='ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_lAvailable']")
    private WebElement availableServices;

    @FindBy(xpath = "//select[@id='ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_lAssigned']")
    private WebElement assignedServices;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlServices_btnAddToAssigned")
	private WebElement selectAddToAssignedBtn;
	
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
		Assert.assertTrue(vehiclepartstable.tableColumnExists("Part"));
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

	private List<String> getServicesNames(WebElement element) {
        return new Select(element)
                .getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
	}

	private List<String> getAvailableServicesNames() {
        return getServicesNames(availableServices);
	}

	private List<String> getAssignedServiceNames() {
        return getServicesNames(assignedServices);
	}

	public void verifyThatAssignedServicesListIsEmpty() {
        List<String> serviceNames = getAssignedServiceNames();
        if (!serviceNames.isEmpty()) {
            serviceNames.forEach(this::unassignServiceForVehiclePart);
            Assert.assertEquals(0, getAssignedServicesList().size());
            saveNewVehiclePart();
        } else {
            cancelNewVehiclePart();
        }
    }

	public void verifyThatAvailableServicesListIsEmpty() {
        List<String> serviceNames = getAvailableServicesNames();
        if (!serviceNames.isEmpty()) {
            serviceNames.forEach(this::assignServiceForVehiclePart);
            Assert.assertEquals(0, getAvailableServicesList().size());
            saveNewVehiclePart();
        } else {
            cancelNewVehiclePart();
        }
    }

	public void assignServiceForVehiclePart(String serviceName) {
		selectAvailableServiceForVehiclePart(serviceName);
		click(selectAddToAssignedBtn);
	}
	
	public void selectAvailableServiceForVehiclePart(String serviceName) {
		Select availableServices = new Select(this.availableServices);
		availableServices.selectByVisibleText(serviceName);
	}
	
	public void selectAssignedServiceForVehiclePart(String serviceName) {
		Select assignedServices = new Select(this.assignedServices);
		assignedServices.selectByVisibleText(serviceName);
	}
	
	public void unassignServiceForVehiclePart(String serviceName) {
		selectAssignedServiceForVehiclePart(serviceName);
		click(selectaddtoavailablebtn);		
	}
	
	public List<WebElement> getAvailableServicesList() {
		Select availableServices = new Select(this.availableServices);
		return availableServices.getOptions();
	}
	
	public List<WebElement> getAssignedServicesList() {
		Select assignedServices = new Select(this.assignedServices);
		return assignedServices.getOptions();
	}
	
	public void saveNewVehiclePart() {	
		clickAndWait(newvehiclepartOKbtn);
		waitABit(3000);
	}
	
	public void cancelNewVehiclePart() {	
		click(newvehiclepartCancelbtn);
	}
	
	public void clickEditButtonForVehiclePart(String vehiclepart) {
		List<WebElement> vehiclepartsrows = getVehiclePartsTableRows();
		for (WebElement vehiclepartsrow : vehiclepartsrows) {
			if (vehiclepartsrow.getText().contains(vehiclepart)) {
				vehiclepartsrow.findElement(By.xpath(".//td/input[@title='Edit']")).click();
				waitForLoading();
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
				waitForLoading();
				break;
			}
		}
	}
	
	public boolean isVehiclePartExists(String vehiclepart) {
		return vehiclepartstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + vehiclepart + "']")).size() > 0;
	}
}
