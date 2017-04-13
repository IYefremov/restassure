package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class ServiceRequestsListWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "itemContainer")
	private WebElement servicerequestslist;
	
	@FindBy(id = "lbAddServiceRequest")
	private WebElement addservicerequestbtn;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_rbxPhases_Input")
	private ComboBox statuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_rbxPhases_DropDown")
	private DropDown statusdd;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_rbcTeamsForFilter_Input")
	private TextField teamcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_rbcTeamsForFilter_DropDown")
	private DropDown teamdd;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_rbcTechsForFilter_Input")
	private TextField techniciancmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_rbcTechsForFilter_DropDown")
	private DropDown techniciandd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_tbSearchTags_tag")
	private TextField tagsfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_tbSearchFreeText")
	private TextField freetextfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_btnSearch")
	private WebElement findbtn;
	
	@FindBy(xpath = "//input[@class='bs-btn btn-cancel pull-right']")
	private WebElement cancelservicerequestbutton;
	
	@FindBy(id = "btnSaveTop")
	private WebElement saveservicerequestbutton;
	
	//Add Service Request General Info
	@FindBy(id = "Card_ddlTeam_Input")
	private TextField addsrvteamcmb;
	
	@FindBy(id = "Card_ddlTeam_DropDown")
	private DropDown addsrvteamdd;
	
	@FindBy(id = "Card_tbPONo")
	private TextField addsrvponum;
	
	@FindBy(id = "Card_tbRONumber")
	private TextField addsrvronum;
	
	@FindBy(id = "Card_ddlClientsAssignedTo_Input")
	private TextField addsrvassignedtocmb;
	
	@FindBy(id = "Card_ddlClientsAssignedTo_DropDown")
	private DropDown addsrvassignedtodd;
	
	//Add Service Request Customer
	@FindBy(id = "Card_ddlClients_Input")
	private TextField addsrvcustomercmb;
	
	@FindBy(id = "Card_ddlClients_DropDown")
	private DropDown addsrvcustomerdd;
	
	//Add Service Request Owner
	@FindBy(id = "Card_ddlOwners_Input")
	private TextField addsrvownercmb;
	
	@FindBy(id = "Card_ddlOwners_DropDown")
	private DropDown addsrvownerdd;
	
	//Add Service Request Vehicle Info
	@FindBy(id = "Card_vehicleVin")
	private TextField addsrvvin;
		
	@FindBy(id = "Card_rcbMake_Input")
	private TextField addsrvcarmake;
	
	@FindBy(id = "Card_rcbModel_Input")
	private TextField addsrvcarmodel;
	
	@FindBy(id = "Card_vehicleYear")
	private WebElement addsrvcaryear;
	
	@FindBy(id = "Card_btnDecodeVin")
	private WebElement addsrvcardecodevinbtn;
	
	//Add Service Request Claim Info
	@FindBy(id = "Card_cbInsurance_Input")
	private TextField addsrvinsurancecmb;
	
	@FindBy(id = "Card_cbInsurance_DropDown")
	private DropDown addsrvinsurancedd;
	
	///////////////////////////////////
	@FindBy(id = "Card_tbTags_tag")
	private TextField addsrvlabel;
	
	@FindBy(id = "Card_tbNote")
	private TextField addsrvdescription;
	
	
	@FindBy(id = "btnCloseServiceRequestTop")
	private WebElement closeservicerequestbtn;
	
	@FindBy(id = "btnCheckInCheckOut")
	private WebElement servicerequestcheckinbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbServicePachages_Input")
	private ComboBox addservicerequestcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbServicePachages_DropDown")
	private DropDown addservicerequestdd;
	
	final By addSREditbuttons = By.xpath("//span[contains(@class, 'infoBlock-editBtn bs-btn bs-btn-mini')]");
	final By donebtn = By.xpath("//div[@class='infoBlock-footer']/div[contains(@class, 'infoBlock-doneBtn')]");
	
	public ServiceRequestsListWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public boolean searchPanelIsExpanded() {
		wait.until(ExpectedConditions.elementToBeClickable(searchtab)).click();
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(statuscmb.isDisplayed());
		Assert.assertTrue(teamcmb.isDisplayed());
		Assert.assertTrue(techniciancmb.isDisplayed());
		Assert.assertTrue(tagsfld.isDisplayed());
		Assert.assertTrue(freetextfld.isDisplayed());
		Assert.assertTrue(findbtn.isDisplayed());
	}
	
	public void selectSearchStatus(String _status)  { 
		selectComboboxValue(statuscmb, statusdd, _status);
	}
	
	public void selectSearchTeam(String teamname) { 
		doubleselectComboboxValueWithTyping(teamcmb, teamdd, teamname);
	}
	
	public void selectSearchTechnician(String technician) {
		selectComboboxValueWithTyping(techniciancmb, techniciandd, technician);
	}
	
	public void setSearchFreeText(String anytext) { 
		freetextfld.clearAndType(anytext);
	}
	
	public void clickFindButton() { 
		//waitABit(1000);
		clickAndWait(findbtn);
	}
	
	public void clickAddServiceRequestButton() { 
		//waitABit(1000);
		wait.until(ExpectedConditions.elementToBeClickable(addservicerequestbtn));
		click(addservicerequestbtn);	
		//waitABit(100);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));		
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']/iframe")));
		//driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		wait.until(ExpectedConditions.elementToBeClickable(saveservicerequestbutton));
	}
	
	public WebElement getFirstServiceRequestFromList() {		
		if (servicerequestslist.findElements(By.xpath("./div[contains(@class,'item')]")).size() > 0) { 
			return servicerequestslist.findElement(By.xpath("./div[contains(@class,'item')]"));
		}
		return null;
	}
	
	public void selectFirstServiceRequestFromList() {
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList());
		getFirstServiceRequestFromList().findElement(By.xpath(".//i[@class='detailsPopover-icon icon-chevron-right']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
		//waitABit(8000);
	}
	
	public void closeFirstServiceRequestFromTheList() {
		selectFirstServiceRequestFromList();
		switchToServiceRequestInfoFrame();
		//driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		wait.until(ExpectedConditions.elementToBeClickable(closeservicerequestbtn));
		clickCloseServiceRequestButton();
		driver.switchTo().defaultContent();
		//waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}
	
	public void clickCloseServiceRequestButton() {
		click(closeservicerequestbtn);
		//new WebDriverWait(driver, 10)
		 // .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
	}
	
	public void acceptFirstServiceRequestFromList() {
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList()).perform();
		getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Accept']")).click();	
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
		//Thread.sleep(4000);
	}
	
	public void rejectFirstServiceRequestFromList() {
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList()).perform();
		getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Reject']")).click();	
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
	}
	
	
	public boolean isAcceptIconPresentForFirstServiceRequestFromList() {
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList(), 10, 10).click().perform();
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browsername = cap.getBrowserName();
		if (browsername.equalsIgnoreCase("internet explorer")) 
			return getFirstServiceRequestFromList().findElements(By.xpath(".//a[@class='command-accept ']")).size() > 0;
		
		return getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Accept']")).isDisplayed();
	}
	
	public String getStatusOfFirstServiceRequestFromList() {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='serviceRequestStatus']")).getText();
	}
	
	public String getFirstInTheListServiceRequestNumber() {
		String srnumber = getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='itemSrNo']/b")).getText();
		return srnumber.substring(0, srnumber.length()-1);
	}
	
	public SRAppointmentInfoPopup clickAddAppointmentToFirstServiceRequestFromList() {
		getFirstServiceRequestFromList().findElement(By.xpath(".//i[contains(@class, 'icon-calendar')]")).click();
		//waitABit(300);
		return PageFactory.initElements(driver,
				SRAppointmentInfoPopup.class);
	}
	
	public boolean isFirstServiceRequestFromListHasAppointment(String appointmenttime) {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//a/span")).getText().equals(appointmenttime);
		//return getFirstServiceRequestFromList().findElement(By.xpath(".//span[text()='" + appointmenttime + "']")).isDisplayed();
	}
	
	public String getWOForFirstServiceRequestFromList() {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='itemWO']")).getText();
	}
	
	public boolean isInsuranceCompanyPresentForFirstServiceRequestFromList(String insurancecompany) {
		boolean exists =  getFirstServiceRequestFromList().findElements(By.xpath(".//div[@class='" + insurancecompany + "  ']")).size() > 0;
		return exists;
	}
	
	public boolean verifySearchResultsByServiceName(String servicename)  { 
		wait.until(ExpectedConditions.visibilityOf(servicerequestslist));
		return getFirstServiceRequestFromList().findElements(By.xpath(".//div[@class='name' and contains(text(), '" + servicename + "')]")).size() > 0;
	}
	
	public boolean verifySearchResultsByModelIN(String _make, String _model, String _year, String vin)  { 
		boolean result = false;
		if (!(getFirstServiceRequestFromList() == null)) {
			result = getFirstServiceRequestFromList().findElements(By.xpath(".//div[@class='modelVin' and text()='" + _year + " " + _make + " " + _model + " " + vin + "']")).size() > 0;
		}
		return result;
	}
	
	public String getFirstServiceRequestStatus()  { 
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='serviceRequestStatus']")).getText();		
	}
	
	public String getFirstServiceRequestPhase()  { 
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='serviceRequestPhase']")).getText();		
	}
	
	public void clickGeneralInfoEditButton()  { 
		click(getGeneralInfoEditButton());
	}
	
	public void clickCustomerEditButton()  { 
		click(getCustomerEditButton());
	}
	
	public void clickVehicleInforEditButton()  { 
		//Actions move = new Actions(driver);		
        //move.dragAndDropBy(getClaimInfoEditButton(), 100, 150).build().perform();	
        getVehicleInfoEditButton().click();
	}
	
	public void clickClaimInfoEditButton()  {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']/iframe")));
		//driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		getClaimInfoEditButton().click();
	}
	
	public void clickServiceEditButton()  { 
		getServiceEditButton().click();
	}
	
	public List<WebElement> getServiceRequestServicesToSelect() {
		WebElement services = driver.findElement(By.xpath("//div[@class='infoBlock-item infoBlock-edit servicesBlock']"));
		return services.findElements(By.xpath(".//div[@class='container-service']"));
	}
	
	public void switchToServiceRequestInfoFrame() {
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']/iframe")));			
	}
	
	
	public WebElement getGeneralInfoEditButton()  { 
		Actions moveact = new Actions(driver);
	    moveact.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']"))).perform();
		moveact.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']/b[text()='General Info:']"))).perform();		
		return driver.findElement(By.xpath("//div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"));
	}
	
	public WebElement getCustomerEditButton()  { 
		Actions moveact = new Actions(driver);
		moveact.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']/b[text()='Customer:']"))).perform();
		return driver.findElement(By.xpath("//div[@class='infoBlock-content']/span[@class='infoBlock-editBtn edit-client']"));
	}
	
	public WebElement getVehicleInfoEditButton()  { 
		return driver.findElement(By.xpath("//div[@id='Card_divVehInfoAll']/span"));		
	}
	
	public WebElement getClaimInfoEditButton()  { 
		return driver.findElement(By.xpath("//div[@id='Card_divCliamInfoAll']/span"));
	}
	
	public WebElement getServiceEditButton()  { 
		Actions moveact = new Actions(driver);
		moveact.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']/b[text()='Service:']"))).perform();
		return driver.findElement(By.xpath("//div[@class='infoBlock infoBlock-list pull-left']/div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"));
	}
	
	public void setServiceRequestGeneralInfoAssignedTo(String value)  {
		selectComboboxValueWithTyping(addsrvassignedtocmb, addsrvassignedtodd, value);
	}
	
	public void setServiceRequestGeneralInfoTeam(String value)  {	
		selectComboboxValueWithTyping(addsrvteamcmb, addsrvteamdd, value);
	}
	
	public void selectAddServiceRequestsComboboxValue(String value) {
		selectComboboxValue(addservicerequestcmb, addservicerequestdd, value);
	}
	
	public void setServiceRequestGeneralInfo(String _team, String _assignedto, String _po, String _ro) {
		setServiceRequestGeneralInfoTeam(_team);
		//waitABit(2000);
		setServiceRequestGeneralInfoAssignedTo(_assignedto);
		clearAndType(addsrvponum, _po);
		clearAndType(addsrvronum, _ro);
	}
	
	public void selectServiceRequestCustomer(String customer) {
		selectComboboxValueWithTyping(addsrvcustomercmb, addsrvcustomerdd, customer);
	}
	
	public void selectServiceRequestOwner(String owner) {
		click(driver.findElement(By.xpath("//li[@id='tabsCustOwner_o']/a")));
		//waitABit(1000);
		selectComboboxValueWithTyping(addsrvownercmb, addsrvownerdd, owner);
	}
	
	public void setServiceRequestVIN(String vin) {
		wait.until(ExpectedConditions.visibilityOf(addsrvvin.getWrappedElement()));	
		clearAndType(addsrvvin, vin);
	}
	
	public void setServiceRequestLabel(String _label) {
		clearAndType(addsrvlabel, _label);
	}
	
	public WebElement getServiceRequestLabelField() {
		return addsrvlabel.getWrappedElement();
	}
	
	public void setServiceRequestDescription(String description) {
		click(driver.findElement(By.xpath("//div[@class='description-content']/span[@class='infoBlock-editBtn']")));
		wait.until(ExpectedConditions.elementToBeClickable(addsrvdescription.getWrappedElement()));
		clearAndType(addsrvdescription, description);
		driver.findElement(By.xpath("//div[@class='description-content']")).findElement(By.xpath(".//div[@class='infoBlock-doneBtn sr-btn']")).click();
	}
	
	public void decodeAndVerifyServiceRequestVIN(String _make, String _model) {
		click(addsrvcardecodevinbtn);
		//waitABit(2000);
		Assert.assertEquals(addsrvcarmake.getValue(), _make);
		Assert.assertEquals(addsrvcarmodel.getValue(), _model);
	}
	
	
	public void selectServiceRequesInsurance(String insurance) {
		selectComboboxValueWithTyping(addsrvinsurancecmb, addsrvinsurancedd, insurance);
	}
	
	public void clickDoneButton() {
		List<WebElement> donebtns = driver.findElements(donebtn);
		for (WebElement donebtn : donebtns) {
			if (donebtn.isDisplayed()) {
				new WebDriverWait(driver, 10)
				  .until(ExpectedConditions.elementToBeClickable(donebtn));
				donebtn.click();
				break;
			}
		}
	}
	
	public void saveNewServiceRequest() {
		click(saveservicerequestbutton);
		driver.switchTo().defaultContent();
		waitUntilPageReloaded();
	}
	
	public void cancelNewServiceRequest() {
		click(cancelservicerequestbutton);
		driver.switchTo().defaultContent();
	}
	
	public void clickCheckInButtonForSelectedSR()  { 
		switchToServiceRequestInfoFrame();
		//driver.switchTo().frame(driver.findElement(By.tagName("iframe")));	
		click(servicerequestcheckinbtn);
		driver.switchTo().defaultContent();
	}
	
	public String getCheckInButtonValueForSelectedSR()  {
		String value = "";
		switchToServiceRequestInfoFrame();
		//driver.switchTo().frame(driver.findElement(By.tagName("iframe")));	
		value = servicerequestcheckinbtn.getText();
		driver.switchTo().defaultContent();
		return value;
	}
	
	public boolean isCheckInButtonExistsForSelectedSR()  { 
		boolean exists = false;
		switchToServiceRequestInfoFrame();
		//driver.switchTo().frame(driver.findElement(By.tagName("iframe")));	
		exists =  driver.findElements(By.id("btnCheckInCheckOut")).size() > 0;
		driver.switchTo().defaultContent();
		return exists;
	}
	
	public boolean isCheckInButtonVisible()  { 
		boolean visible = false;
		switchToServiceRequestInfoFrame();
		//driver.switchTo().frame(driver.findElement(By.tagName("iframe")));	
		visible = servicerequestcheckinbtn.isDisplayed();
		driver.switchTo().defaultContent();
		return visible;
	}
	
	public String getVINValueForSelectedServiceRequest() {
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		String VINValue = driver.findElement(By.xpath("//span[@data-for='Card_vehicleVin']")).getText();
		driver.switchTo().defaultContent();
		return VINValue;
	}
	
	public String getCustomerValueForSelectedServiceRequest() {
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		String clientname = driver.findElement(By.xpath("//span[@data-for='Card_hdnFullClientName']")).getText();
		driver.switchTo().defaultContent();
		return clientname;
	}
	
	public String getEmployeeValueForSelectedServiceRequest() {
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		String employee = driver.findElement(By.xpath("//span[@data-for='Card_hdnEmployeeFullName']")).getText();
		driver.switchTo().defaultContent();
		return employee;
	}
	
	public boolean isServiceIsPresentForForSelectedServiceRequest(String servicename) {
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		boolean exists = driver.findElement(By.xpath("//span[contains(text(), '" + servicename + "')]")).isDisplayed();
		driver.switchTo().defaultContent();
		return exists;
	}

	
	public void addServicesToServiceRequest(String[] services) {
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div[contains(@class, 'infoBlock-list')]/div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"))).perform();
		act.click(driver.findElement(By.xpath("//div[contains(@class, 'infoBlock-list')]/div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"))).perform();
		WebElement servicespopup = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='infoBlock-item infoBlock-edit servicesBlock']"))));
		for (String srv : services) {
			servicespopup.findElement(By.xpath(".//span[@class='name-service' and text()='" + srv + "']")).click();
		}
		servicespopup.findElement(By.xpath(".//div[@class='infoBlock-list-doneBtn rp-btn-blue']")).click();
	}

}
