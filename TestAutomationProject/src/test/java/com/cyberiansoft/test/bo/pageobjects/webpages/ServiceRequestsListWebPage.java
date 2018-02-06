package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.ios_client.utils.MailChecker;

//import com.cyberiansoft.test.bo.utils.WebElementExt;
//import lombok.experimental.ExtensionMethod;
//
//@ExtensionMethod(WebElementExt.class)
public class ServiceRequestsListWebPage extends BaseWebPage implements ClipboardOwner {

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "itemContainer")
	private WebElement servicerequestslist;

	@FindBy(id = "lbAddServiceRequest")
	private WebElement addservicerequestbtn;

	// Search Panel

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

	// Add Service Request General Info
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

	// Add Service Request Customer
	@FindBy(id = "Card_ddlClients_Input")
	private TextField addsrvcustomercmb;

	@FindBy(id = "Card_ddlClients_DropDown")
	private DropDown addsrvcustomerdd;

	// Add Service Request Owner
	@FindBy(id = "Card_ddlOwners_Input")
	private TextField addsrvownercmb;

	@FindBy(id = "Card_ddlOwners_DropDown")
	private DropDown addsrvownerdd;

	// Add Service Request Vehicle Info
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

	// Add Service Request Claim Info
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

	@FindBy(id = "Card_rpNotes_ctl01_spanTimeOfNote")
	private WebElement descriptionTime;

	@FindBy(id = "itemContainer")
	private WebElement serviceRequestContainer;

	@FindBy(id = "Card_tbTags_tag")
	private WebElement tagField;

	@FindBy(className = "tag")
	private List<WebElement> allAddedTags;

	@FindBy(className = "spanNote")
	private List<WebElement> oldDescriptions;

	@FindBy(className = "description-reason")
	private WebElement descriptionDocuments;

	@FindBy(id = "linkAnswers")
	private WebElement descriptionAnswers;

	@FindBy(className = "content")
	private WebElement documentContent;

	@FindBy(xpath = "//input[contains(@class, 'ruButton ruBrowse')]")
	private WebElement addFileBTN;

	@FindBy(xpath = "//div[contains(@class, 'ruButton ruRemove')]")
	private WebElement removeBTN;

	@FindBy(id = "ctl00_ctl00_Content_Main_rcbServicePachages_Input")
	private WebElement addServiceRequestDopDown;

	@FindBy(xpath = "//div[contains(@class, 'infoBlock main')]")
	private List<WebElement> serviceRequestInfoBlocks;

	@FindBy(id = "Card_ddlClients_Input")
	private WebElement customerName;

	@FindBy(id = "doneCustOwner")
	private WebElement acceptCustomerBTN;

	@FindBy(className = "icon-calendar")
	private WebElement appointmentCalendarIcon;

	@FindBy(id = "ctl00_ctl00_Content_Main_rdpStartDate_dateInput")
	private WebElement appointmentFromDate;

	@FindBy(id = "ctl00_ctl00_Content_Main_rdpStartTime_dateInput")
	private WebElement appointmentFromTime;

	@FindBy(id = "ctl00_ctl00_Content_Main_rdpEndDate_dateInput")
	private WebElement appointmentToDate;

	@FindBy(id = "ctl00_ctl00_Content_Main_rdpEndTime_dateInput")
	private WebElement appointmentToTime;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnAddApp")
	private WebElement addAppointmentBTN;

	@FindBy(id = "ctl00_ctl00_Content_Main_rcbTechnician_Input")
	private TextField addservicerequesapptechcmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_rcbTechnician_DropDown")
	private DropDown addservicerequesapptechdd;

	@FindBy(className = "addAppointmentLink")
	private WebElement addAppointmentBTNfromSRedit;

	@FindBy(xpath = "//div[contains(@class, 'appointment-info clearfix')]")
	private WebElement appointmentContent;

	@FindBy(id = "Card_rdpStartDate_dateInput")
	private WebElement appointmentFromDateSRedit;

	@FindBy(id = "Card_rdpStartTime_dateInput")
	private WebElement appointmentFromTimeSRedit;

	@FindBy(id = "Card_rdpEndDate_dateInput")
	private WebElement appointmentToDateSRedit;

	@FindBy(id = "Card_rdpEndTime_dateInput")
	private WebElement appointmentToTimeSRedit;

	@FindBy(xpath = "//div[contains(@class, 'appointment-info')]")
	private WebElement appointmentContentFromCalendar;

	@FindBy(id = "Card_dpScheduledDate_dateInput")
	private WebElement suggestedStart;

	@FindBy(id = "divGeneralButtonsDone")
	private WebElement acceptGeneralInfoBTN;

	@FindBy(id = "Card_btnCancel")
	private WebElement cancelAppointmentFromSRedit;

	@FindBy(className = "scheduler-dropdown")
	private WebElement techniciansFromSchedulerBTN;

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_rpTechniciansPopup_ctl00_tbSearchTech")
	private WebElement tecniciansNamesFromScheduler;

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_rpTechniciansPopup_ctl00_comboAreasTechPopup_Input")
	private WebElement tecniciansAreasFromScheduler;

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_rpTechniciansPopup_ctl00_comboTeamsTechPopup_Input")
	private WebElement tecniciansTeamsFromScheduler;

	@FindBy(className = "tech-item")
	private List<WebElement> techniciansList;

	@FindBy(className = "show-btns")
	private WebElement arrowInTechniciansList;

	@FindBy(className = "tech-content")
	private WebElement techContent;

	@FindBy(className = "rsHeaderTimeline")
	private WebElement timelineBTN;

	@FindBy(className = "rsHorizontalHeaderTable")
	private WebElement horizontalHeaderTimeline;

	@FindBy(id = "Card_ddlAdvisors_Arrow")
	private WebElement serviceAdvisorArrow;

	@FindBy(id = "Card_ddlAdvisors_DropDown")
	private WebElement advisorUsersList;

	@FindBy(id = "Card_ddlAdvisors_Input")
	private WebElement advisorInputField;

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

	public void selectSearchStatus(String _status) {
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
		try{
			waitABit(1000);
			driver.switchTo().alert().accept();
		}catch (Exception e){}
		waitABit(1000);
		clickAndWait(findbtn);
	}

	public void clickAddServiceRequestButton() {
		waitABit(1000);
		wait.until(ExpectedConditions.elementToBeClickable(addservicerequestbtn));
		waitABit(4000);
		click(addservicerequestbtn);
		waitABit(3000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
		driver.switchTo()
				.frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']/iframe")));
		wait.until(ExpectedConditions.elementToBeClickable(saveservicerequestbutton));
		waitABit(3000);
	}

	public WebElement getFirstServiceRequestFromList() {
		if (servicerequestslist.findElements(By.xpath("./div[contains(@class,'item')]")).size() > 0) {
			return servicerequestslist.findElement(By.xpath("./div[contains(@class,'item')]"));
		}
		return null;
	}

	public void selectFirstServiceRequestFromList() throws InterruptedException {
		waitABit(4000);
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList());
		Thread.sleep(1000);
		getFirstServiceRequestFromList().findElement(By.xpath(".//i[@class='detailsPopover-icon icon-chevron-right']"))
				.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
		waitABit(5000);
		driver.switchTo()
		.frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']/iframe")));
	}

	public void closeFirstServiceRequestFromTheList() throws InterruptedException {
		selectFirstServiceRequestFromList();
		switchToServiceRequestInfoFrame();
		wait.until(ExpectedConditions.elementToBeClickable(closeservicerequestbtn));
		clickCloseServiceRequestButton();
		driver.switchTo().defaultContent();
		waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public void clickCloseServiceRequestButton() {
		click(closeservicerequestbtn);
	}

	public void acceptFirstServiceRequestFromList() throws InterruptedException {
		waitABit(4000);
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList())
				.moveToElement(getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Accept']"))).click()
				.perform();
		// getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Accept']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
		Thread.sleep(1000);
	}

	public void rejectFirstServiceRequestFromList() {
		waitABit(8000);
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList())
		.moveToElement(getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Reject']"))).click().perform();

		//getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Reject']")).click();
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
	}

	public boolean isAcceptIconPresentForFirstServiceRequestFromList() {
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList(), 10, 10).click().perform();
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browsername = cap.getBrowserName();
		waitABit(4000);
		if (browsername.equalsIgnoreCase("internet explorer"))
			return getFirstServiceRequestFromList().findElements(By.xpath(".//a[@class='command-accept ']")).size() > 0;

		return // wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("command-accept
				// "))).isDisplayed();
		getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Accept']")).isDisplayed();
	}

	public String getStatusOfFirstServiceRequestFromList() {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='serviceRequestStatus']"))
				.getText();
	}

	public String getFirstInTheListServiceRequestNumber() {
		String srnumber = getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='itemSrNo']/b"))
				.getText();
		return srnumber.substring(0, srnumber.length() - 1);
	}

	public SRAppointmentInfoPopup clickAddAppointmentToFirstServiceRequestFromList() {
		getFirstServiceRequestFromList().findElement(By.xpath(".//i[contains(@class, 'icon-calendar')]")).click();
		waitABit(300);
		return PageFactory.initElements(driver, SRAppointmentInfoPopup.class);
	}

	public void setServiceRequestAppointmentTechnicians(String tech) {
		selectComboboxValueWithTyping(addservicerequesapptechcmb, addservicerequesapptechdd, tech);
	}

	public boolean isFirstServiceRequestFromListHasAppointment(String appointmenttime) {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//a/span")).getText().equals(appointmenttime);
		// return
		// getFirstServiceRequestFromList().findElement(By.xpath(".//span[text()='"
		// + appointmenttime + "']")).isDisplayed();
	}

	public String getWOForFirstServiceRequestFromList() {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='itemWO']")).getText();
	}
	
	public WebElement getServiceRequestCellBySRNumber(String srNumber) {
		WebElement srcell = null;
		List<WebElement> servicerequestscells = servicerequestslist.findElements(By.xpath("./div[contains(@class,'item')]"));
		for (WebElement servicerequestcell : servicerequestscells)
			if (servicerequestcell.findElement(By.xpath(".//span[@class='itemSrNo']/b")).getText().trim().contains(srNumber)) {
				srcell = servicerequestcell;
				break;
			}
				
				
		return srcell;
	}
	
	public String getWOForServiceRequestFromList(String srNumber) {
		WebElement srcell = getServiceRequestCellBySRNumber(srNumber);
		return srcell.findElement(By.xpath(".//span[@class='itemWO']")).getText();
	}

	public boolean isInsuranceCompanyPresentForFirstServiceRequestFromList(String insurancecompany)
			throws InterruptedException {
		waitABit(2000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		boolean exists = getFirstServiceRequestFromList()
				.findElements(By.xpath(".//div[@class='" + insurancecompany + "  ']")).size() > 0;
		return exists;
	}

	public boolean verifySearchResultsByServiceName(String servicename) {
		wait.until(ExpectedConditions.visibilityOf(servicerequestslist));
		return getFirstServiceRequestFromList()
				.findElements(By.xpath(".//div[@class='name' and contains(text(), '" + servicename + "')]")).size() > 0;
	}

	public boolean verifySearchResultsByModelIN(String _make, String _model, String _year, String vin) {
		boolean result = false;
		if (!(getFirstServiceRequestFromList() == null)) {
			result = getFirstServiceRequestFromList().findElements(By.xpath(
					".//div[@class='modelVin' and text()='" + _year + " " + _make + " " + _model + " " + vin + "']"))
					.size() > 0;
		}
		return result;
	}

	public String getFirstServiceRequestStatus() {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='serviceRequestStatus']"))
				.getText();
	}

	public String getFirstServiceRequestPhase() {
		return getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='serviceRequestPhase']"))
				.getText();
	}

	public void clickGeneralInfoEditButton() {
		click(getGeneralInfoEditButton());
	}

	public void clickCustomerEditButton() {
		click(getCustomerEditButton());
	}

	public void clickVehicleInforEditButton() {
		// Actions move = new Actions(driver);
		// move.dragAndDropBy(getClaimInfoEditButton(), 100,
		// 150).build().perform();
		getVehicleInfoEditButton().click();
	}

	public void clickClaimInfoEditButton() {
		driver.switchTo().defaultContent();
		// driver.switchTo()
		// .frame((WebElement)
		// driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']/iframe")));
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		getClaimInfoEditButton().click();
	}

	public void clickServiceEditButton() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getServiceEditButton().click();
	}

	public List<WebElement> getServiceRequestServicesToSelect() {
		WebElement services = driver
				.findElement(By.xpath("//div[@class='infoBlock-item infoBlock-edit servicesBlock']"));
		return services.findElements(By.xpath(".//div[@class='container-service']"));
	}

	public void switchToServiceRequestInfoFrame() {
		waitABit(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
//		driver.switchTo()
//				.frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']/iframe")));
	}

	public WebElement getGeneralInfoEditButton() {
		Actions moveact = new Actions(driver);
		moveact.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']"))).perform();
		moveact.moveToElement(
				driver.findElement(By.xpath("//div[@class='infoBlock-content']/b[text()='General Info:']"))).perform();
		return driver.findElement(By.xpath("//div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"));
	}

	public WebElement getCustomerEditButton() {
		Actions moveact = new Actions(driver);
		moveact.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']/b[text()='Customer:']")))
				.perform();
		return driver.findElement(
				By.xpath("//div[@class='infoBlock-content']/span[@class='infoBlock-editBtn edit-client']"));
	}

	public WebElement getVehicleInfoEditButton() {
		return driver.findElement(By.xpath("//div[@id='Card_divVehInfoAll']/span"));
	}

	public WebElement getClaimInfoEditButton() {
		return driver.findElement(By.id("Card_divCliamInfoAll"));
	}

	public WebElement getServiceEditButton() {
		Actions moveact = new Actions(driver);
		moveact.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']/b[text()='Service:']")))
				.perform();
		return driver.findElement(By.xpath(
				"//div[@class='infoBlock infoBlock-list pull-left']/div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"));
	}

	public void setServiceRequestGeneralInfoAssignedTo(String value) {
		selectComboboxValueWithTyping(addsrvassignedtocmb, addsrvassignedtodd, value);
	}

	public void setServiceRequestGeneralInfoTeam(String value) {
		selectComboboxValueWithTyping(addsrvteamcmb, addsrvteamdd, value);
	}

	public void selectAddServiceRequestsComboboxValue(String value) {
		selectComboboxValue(addservicerequestcmb, addservicerequestdd, value);
	}

	public void setServiceRequestGeneralInfo(String _team, String _assignedto, String _po, String _ro) {
		setServiceRequestGeneralInfoTeam(_team);
		waitABit(2000);
		setServiceRequestGeneralInfoAssignedTo(_assignedto);
		clearAndType(addsrvponum, _po);
		clearAndType(addsrvronum, _ro);
	}

	public void selectServiceRequestCustomer(String customer) {
		selectComboboxValueWithTyping(addsrvcustomercmb, addsrvcustomerdd, customer);
	}

	public void selectServiceRequestOwner(String owner) {
		click(driver.findElement(By.xpath("//li[@id='tabsCustOwner_o']/a")));
		waitABit(1000);
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
		driver.findElement(By.xpath("//div[@class='description-content']"))
				.findElement(By.xpath(".//div[@class='infoBlock-doneBtn sr-btn']")).click();
	}

	public void decodeAndVerifyServiceRequestVIN(String _make, String _model) {
		click(addsrvcardecodevinbtn);
		waitABit(2000);
		Assert.assertEquals(addsrvcarmake.getValue(), _make);
		Assert.assertEquals(addsrvcarmodel.getValue(), _model);
	}

	public void selectServiceRequesInsurance(String insurance) {
		selectComboboxValueWithTyping(addsrvinsurancecmb, addsrvinsurancedd, insurance);
	}

	public void clickDoneButton() {
		waitABit(2000);
		List<WebElement> donebtns = driver.findElements(donebtn);
		for (WebElement donebtn : donebtns) {
			if (donebtn.isDisplayed()) {
				new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(donebtn));
				donebtn.click();
				break;
			}
		}
	}

	public boolean saveNewServiceRequest() {
		try {
			waitABit(3000);
			wait.until(ExpectedConditions.elementToBeClickable(saveservicerequestbutton));
			click(saveservicerequestbutton);
			waitABit(3000);
			driver.switchTo().defaultContent();
			waitUntilPageReloaded();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void cancelNewServiceRequest() {
		click(cancelservicerequestbutton);
		driver.switchTo().defaultContent();
	}

	public void clickCheckInButtonForSelectedSR() {
		switchToServiceRequestInfoFrame();
		click(servicerequestcheckinbtn);
		driver.switchTo().defaultContent();
	}

	public String getCheckInButtonValueForSelectedSR() {
		String value = "";
		switchToServiceRequestInfoFrame();
		// driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		value = servicerequestcheckinbtn.getText();
		driver.switchTo().defaultContent();
		return value;
	}

	public boolean isCheckInButtonExistsForSelectedSR() {
		boolean exists = false;
		switchToServiceRequestInfoFrame();
		// driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		exists = driver.findElements(By.id("btnCheckInCheckOut")).size() > 0;
		driver.switchTo().defaultContent();
		return exists;
	}

	public boolean isCheckInButtonVisible() {
		boolean visible = false;
		switchToServiceRequestInfoFrame();
		// driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		visible = servicerequestcheckinbtn.isDisplayed();
		driver.switchTo().defaultContent();
		return visible;
	}

	public String getVINValueForSelectedServiceRequest() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		String VINValue = driver.findElement(By.xpath("//span[@data-for='Card_vehicleVin']")).getText();
		driver.switchTo().defaultContent();
		return VINValue;
	}

	public String getCustomerValueForSelectedServiceRequest() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		String clientname = driver.findElement(By.xpath("//span[@data-for='Card_hdnFullClientName']")).getText();
		driver.switchTo().defaultContent();
		return clientname;
	}

	public String getEmployeeValueForSelectedServiceRequest() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		String employee = driver.findElement(By.xpath("//span[@data-for='Card_hdnEmployeeFullName']")).getText();
		driver.switchTo().defaultContent();
		return employee;
	}

	public boolean isServiceIsPresentForForSelectedServiceRequest(String servicename) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.tagName("iframe")));
		boolean exists = driver.findElement(By.xpath("//span[contains(text(), '" + servicename + "')]")).isDisplayed();
		driver.switchTo().defaultContent();
		return exists;
	}

	public WebElement clickAddServicesIcon() {
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By
				.xpath("//div[contains(@class, 'infoBlock-list')]/div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']")))
				.perform();
		act.click(driver.findElement(By
				.xpath("//div[contains(@class, 'infoBlock-list')]/div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']")))
				.perform();
		return wait.until(ExpectedConditions.visibilityOf(
				driver.findElement(By.xpath("//div[@class='infoBlock-item infoBlock-edit servicesBlock']"))));
	}

	public void addServicesToServiceRequest(String...services) {
		WebElement servicespopup = clickAddServicesIcon();
		for (String srv : services) {
			System.out.println("+++" + srv);
			//driver.findElement(By.id("Card_comboService_Input")).click();
			driver.findElement(By.id("Card_comboService_Input")).clear();
			waitABit(1000);
			driver.findElement(By.id("Card_comboService_Input")).sendKeys(srv);
			driver.findElement(By.id("Card_comboService_DropDown")).findElement(By.name("serviceCheckbox")).click();
			driver.findElement(By.id("Card_btnAddServiceToList")).click();
			waitABit(500);
		}
		servicespopup.findElement(By.xpath(".//div[@class='infoBlock-list-doneBtn rp-btn-blue']")).click();
	}

	public void setServiePriceAndQuantity(String serviceName, String price, String quantity) {
		WebElement servicespopup = clickAddServicesIcon();
		WebElement servicerow = getSelectedServiceRow(serviceName);

		servicerow.findElement(By.xpath(".//input[@class='k-formatted-value price-service k-input']")).click();
		servicerow.findElement(By.xpath(".//input[@class='price-service k-input']")).clear();
		servicerow.findElement(By.xpath(".//input[@class='price-service k-input']")).sendKeys(price);

		servicerow.findElement(By.xpath(".//input[@class='k-formatted-value quantity-service k-input']")).click();
		servicerow.findElement(By.xpath(".//input[@class='quantity-service k-input']")).clear();
		servicerow.findElement(By.xpath(".//input[@class='quantity-service k-input']")).sendKeys(quantity);
		servicespopup.findElement(By.xpath(".//div[@class='infoBlock-list-doneBtn rp-btn-blue']")).click();
	}

	public WebElement getSelectedServiceRow(String serviceName) {
		WebElement servicerow = null;
		WebElement servicespopup = driver
				.findElement(By.xpath("//div[@class='infoBlock-item infoBlock-edit servicesBlock']"));
		List<WebElement> selectedservices = servicespopup
				.findElements(By.xpath(".//*[@class='container-service container-service-selected']"));
		for (WebElement srvrow : selectedservices)
			if (srvrow.findElements(By.xpath(".//span[text()='" + serviceName + "']")).size() > 0) {
				servicerow = srvrow;
				break;
			}
		return servicerow;
	}

	public boolean checkTimeOfLastDescription() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		click(driver.findElement(By.xpath("//div[@class='description-content']/span[@class='infoBlock-editBtn']")));
		wait.until(ExpectedConditions.elementToBeClickable(addsrvdescription.getWrappedElement()));
		try {
			new SimpleDateFormat("dd yyyy hh:mm").parse(descriptionTime.getText().substring(4, 17));
			closeservicerequestbtn.click();
			return true;
		} catch (ParseException e) {
			closeservicerequestbtn.click();
			return false;
		}
	}

	public boolean addTags(String... tags) {
		for (String tag : tags) {
			if (allAddedTags.stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
					.collect(Collectors.toList()).contains(tag)) {
				tagField.sendKeys(tag);
				tagField.sendKeys(Keys.ENTER);
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//input[contains (@class, 'ui-autocomplete-input not_valid')]")));
					return true;
				} catch (TimeoutException e) {
					return false;
				}
			} else {
				tagField.sendKeys(tag);
				tagField.sendKeys(Keys.ENTER);
			}
		}
		return true;
	}

	public boolean removeFirtsTag() {
		int prevSize = allAddedTags.size();
		allAddedTags.get(0).findElement(By.xpath("//a[contains(@title, 'Removing tag')]")).click();
		return prevSize - allAddedTags.size() == 1;
	}

	public boolean checkTags(String... tags) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		System.out.println(allAddedTags.stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
				.collect(Collectors.toList()));
		List tagsToCheck = new LinkedList(Arrays.asList(tags));
		tagsToCheck.remove(0);
		boolean result = allAddedTags.stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
				.collect(Collectors.toList()).containsAll(tagsToCheck);
		closeservicerequestbtn.click();
		return result;
	}

	public boolean addNewDescriptionAndCheckOld(String newDescription, String prevDescription) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		click(driver.findElement(By.xpath("//div[@class='description-content']/span[@class='infoBlock-editBtn']")));
		wait.until(ExpectedConditions.elementToBeClickable(addsrvdescription.getWrappedElement()));
		clearAndType(addsrvdescription, newDescription);
		WebElement lastDescription = oldDescriptions.get(0);
		if (!lastDescription.findElement(By.tagName("span")).getText().equals(prevDescription)) {
			return false;
		}
		addsrvdescription.clear();
		driver.findElement(By.xpath("//div[@class='description-content']"))
				.findElement(By.xpath(".//div[@class='infoBlock-doneBtn sr-btn']")).click();

		if (!driver.findElement(By.className("description-content")).findElement(By.className("infoBlock-valContainer"))
				.getAttribute("style").equals("display: none;"))
			return false;

		return true;
	}

	public boolean checkServiceDescription(String string) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		click(driver.findElement(By.xpath("//div[@class='description-content']/span[@class='infoBlock-editBtn']")));
		wait.until(ExpectedConditions.elementToBeClickable(addsrvdescription.getWrappedElement()));
		WebElement lastDescription = oldDescriptions.get(0);
		System.out.println(lastDescription.findElement(By.tagName("span")).getText());
		if (!lastDescription.findElement(By.tagName("span")).getText().equals(string)) {
			return false;
		}
		return true;

	}

	public boolean checkIfDescriptionIconsVisible() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		boolean documentShown;
		boolean answerShown;
		documentShown = descriptionDocuments.findElement(By.tagName("i")).getAttribute("style")
				.equals("display : none;");
		answerShown = descriptionAnswers.findElement(By.tagName("i")).getAttribute("style").equals("display : none;");

		return documentShown || answerShown;
	}

	public boolean checkServiceRequestDocumentIcon() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		
		if (driver.findElement(By.xpath("//div[contains(@class, 'description-reason')]")).findElement(By.tagName("i")).getAttribute("style").equals("display : none;"))
			return false;
		return true;
	}

	public void clickDocumentButton() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));

		String oldWindow = driver.getWindowHandle();
		descriptionDocuments.findElement(By.tagName("i")).click();
		Set allWindows = driver.getWindowHandles();
		allWindows.remove(oldWindow);
		driver.switchTo().window((String) allWindows.iterator().next());
	}

	public boolean checkElementsInDocument() {
		try {
			documentContent.findElement(By.xpath("//h2[contains(text(), 'Documents')]"));
			documentContent.findElement(By.xpath("//h3[contains(text(), 'Service Request:')]"));
			documentContent.findElement(By.className("add"));
			if (!documentContent.findElements(By.className("rgHeader")).stream().map(w -> w.getText())
					.collect(Collectors.toList()).containsAll(Arrays.asList("Name/Description", "Size", "Uploaded")))
				return false;
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean clickAddImageBTN() {
		driver.findElement(By.className("add")).click();
		try {
			updateWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnOk")));
			updateWait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnCancel")));

			updateWait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//input[contains(@class, 'ruButton ruBrowse')]")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void addImage() throws AWTException, InterruptedException {

		Actions builder = new Actions(driver);
		Action myAction = builder.moveToElement(addFileBTN).click().build();

		myAction.perform();
		ServiceRequestsListWebPage textTransfer = new ServiceRequestsListWebPage(driver);
		textTransfer.setClipboardContents("C:\\temp\\Pony.png");

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		driver.switchTo().activeElement();
		Thread.sleep(4000);
		updateWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnOk")))
				.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	}

	public void setClipboardContents(String aString) {

		StringSelection stringSelection = new StringSelection(aString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}

	public boolean checkPresentanceOFAddedFile() throws InterruptedException {
		try {
			updateWait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_gv_ctl00_ctl04_gbccolumn")))
					.click();
			updateWait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_ctl01_ctl01_Card_tbName")));
			updateWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnOk")))
					.click();
			waitABit(3000);

		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public int countFilesInDir(String dir) {
		return new File(dir).listFiles().length;
	}

	public boolean checkDeletionOfFile() throws InterruptedException {
		try {
			Thread.sleep(3000);
			updateWait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_gv_ctl00_ctl04_gbccolumn1")))
					.click();
			driver.switchTo().alert().accept();
			waitABit(3000);

			updateWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rgNoRecords")));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public void selectAddServiceRequestDropDown(String string) {
		if (!string.equals("01_Alex2SRT")) {
			addServiceRequestDopDown.click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals(string)).findFirst()
					.get().click();
		}

	}

	public void setCustomer(String customer) throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));

		serviceRequestInfoBlocks.get(1).click();
		customerName.click();
		customerName.sendKeys(customer);
		customerName.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		Actions act = new Actions(driver);
		act.moveToElement(acceptCustomerBTN).click().build().perform();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		updateWait.until(ExpectedConditions.elementToBeClickable(serviceRequestInfoBlocks.get(1))).click();
		act.moveToElement(acceptCustomerBTN).click().build().perform();
		Thread.sleep(2000);
	}

	public boolean addAppointmentFromSRlist(String fromDate, String toDate) {

		appointmentCalendarIcon.click();

		appointmentFromDate.clear();
		appointmentToDate.clear();
		appointmentFromTime.clear();
		appointmentToTime.clear();

		appointmentFromDate.sendKeys(fromDate);
		appointmentToDate.sendKeys(toDate);
		appointmentFromTime.sendKeys("6:00 AM");
		appointmentToTime.sendKeys("7:00 AM");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink")).click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(addAppointmentBTN)).click();
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean addAppointmentFromSRlist(String fromDate, String toDate, String technician) {
waitABit(3000);
		appointmentCalendarIcon.click();

		appointmentFromDate.clear();
		appointmentToDate.clear();
		waitABit(1000);

		appointmentFromTime.clear();
		appointmentToTime.clear();

		appointmentFromDate.sendKeys(fromDate);
		appointmentToDate.sendKeys(toDate);
		appointmentFromTime.sendKeys("6:00 AM");
		appointmentToTime.sendKeys("7:00 AM");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("gvTechnicians")));
		// wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("gvTechnicians"))));

		appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals(technician)).findFirst()
				.get().click();
		try {
			wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.id("ctl00_ctl00_Content_Main_btnAddApp")))).click();
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean checkDefaultAppointmentValuesAndaddAppointmentFomSREdit(String startDate, String endDate)
			throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		addAppointmentBTNfromSRedit.click();

		Thread.sleep(1000);
		if (!(appointmentFromDateSRedit.getText().isEmpty() && appointmentToDateSRedit.getText().isEmpty()
				&& appointmentFromTimeSRedit.getText().isEmpty() && appointmentToTimeSRedit.getText().isEmpty())) {
			return false;
		}

		Thread.sleep(1000);
		if (!(appointmentContent.findElement(By.id("Card_tbxSubject")).getAttribute("value").equals("Alex SASHAZ"))) {
			return false;
		}

		appointmentContent.findElement(By.id("Card_rcbAppLocations_Input")).click();

		Thread.sleep(1000);
		if (!driver.findElement(By.className("rcbHovered")).getText().equals("Custom") && appointmentContent
				.findElement(By.id("Card_rcbAppointmentLocations_Input")).getAttribute("disabled").equals("disabled")) {
			return false;
		}

		Thread.sleep(1000);
		if (!appointmentContent.findElement(By.id("Card_rcbTechnician_Input")).getAttribute("value").equals("All")
				&& appointmentContent.findElement(By.id("Card_rcbStates_Input")).getAttribute("value").equals("All")) {
			return false;
		}

		Thread.sleep(1000);
		if (!appointmentContent.findElement(By.id("Card_tbxAddress")).getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxCity")).getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxZip")).getText().isEmpty()) {
			return false;
		}

		Thread.sleep(1000);
		if (!appointmentContent.findElement(By.id("Card_tbAppointmentClientName")).getText().equals("Alex SASHAZ")
				&& appointmentContent.findElement(By.id("Card_tbAppointmentClientAddress")).getText()
						.equals("407 SILVER SAGE DR., NewYork, 10001")
				&& appointmentContent.findElement(By.id("Card_tbAppointmentClientPhone")).getText()
						.equals("14043801674")
				&& appointmentContent.findElement(By.id("Card_tbAppointmentClientEmail")).getText()
						.equals("ALICIA.VILLALOBOS@KCC.COM")) {
			return false;
		}
		driver.findElement(By.id("Card_rcbAppLocations_Arrow")).click();
		Thread.sleep(1000);
		try {
			appointmentContent.findElement(By.id("Card_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(0).click();
			Thread.sleep(500);
			appointmentContent.findElement(By.id("Card_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(1).click();
		} catch (TimeoutException e) {
			return false;
		}

		Thread.sleep(1000);
		if (driver.findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4 && driver
				.findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container")).size() != 4) {
			return false;
		}
		driver.findElement(By.id("Card_btnAddApp")).click();

		return true;
	}

	public boolean checkStatus(String status) {
		driver.switchTo().defaultContent();
		System.out.println(driver.findElement(By.className("serviceRequestStatus")).getText());
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("serviceRequestStatus"))).getText()
					.equals(status);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean checkShowHideTeches(String startDate, String endDate) throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		addAppointmentBTNfromSRedit.click();

		appointmentFromDateSRedit.clear();
		appointmentToDateSRedit.clear();
		appointmentFromTimeSRedit.clear();
		appointmentToTimeSRedit.clear();

		appointmentFromDateSRedit.sendKeys(startDate);
		appointmentToDateSRedit.sendKeys(endDate);
		appointmentFromTimeSRedit.sendKeys("6:00 AM");
		appointmentToTimeSRedit.sendKeys("7:00 AM");
		driver.findElement(By.id("Card_rdpEndTime_timePopupLink")).click();

		Thread.sleep(1000);
		try {
			appointmentContent.findElement(By.id("Card_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.tagName("li")).get(0).click();
			Thread.sleep(500);
			appointmentContent.findElement(By.id("Card_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.tagName("li")).get(1).click();
			if (driver.findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4
					&& driver.findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container"))
							.size() != 4) {
				return false;
			}

			if (!wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).getText()
					.equals("Hide")) {
				return false;
			}
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("gvTechnicians-table")));

			if (!wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).getText()
					.equals("Show")) {
				return false;
			}
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public boolean checkDefaultAppointmentValuesFromCalendar(String fromDate, String toDate, String subject)
			throws InterruptedException {
		Thread.sleep(5000);
		appointmentCalendarIcon.click();

		appointmentFromDate.clear();
		appointmentToDate.clear();
		appointmentFromTime.clear();
		appointmentToTime.clear();

		appointmentFromDate.sendKeys(fromDate);
		appointmentToDate.sendKeys(toDate);
		appointmentFromTime.sendKeys("6:00 AM");
		appointmentToTime.sendKeys("7:00 AM");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink")).click();

		Thread.sleep(1000);
		if (!(appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbxSubject"))
				.getAttribute("value").equals("Alex SASHAZ"))) {
			return false;
		}

		appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbAppLocations_Input")).click();

		Thread.sleep(1000);
		if (!driver.findElement(By.className("rcbHovered")).getText().equals("Custom") && appointmentContentFromCalendar
				.findElement(By.id("ctl00_ctl00_Content_Main_rcbAppointmentLocations_Input")).getAttribute("disabled")
				.equals("disabled")) {
			return false;
		}

		Thread.sleep(1000);
		if (!appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input"))
				.getAttribute("value").equals("All")) {
			return false;
		}

		// appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).click();

		Thread.sleep(1000);
		System.out.println(
				driver.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).getAttribute("disabled"));
		if (!driver.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).getAttribute("disabled")
				.equals("true")) {
			return false;
		}
		// driver.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Arrow")).click();

		Thread.sleep(1000);
		if (!appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbxAddress")).getText()
				.isEmpty()
				&& appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbxCity")).getText()
						.equals("L.A.")
				&& appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbxZip")).getText()
						.equals("78523")) {
			return false;
		}

		Thread.sleep(1000);
		if (!appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientName"))
				.getText().equals("Johon Connor")
				&& appointmentContentFromCalendar
						.findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientAddress")).getText()
						.equals("..., L.A., CA, 78523")
				&& appointmentContentFromCalendar
						.findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientPhone")).getText()
						.equals("77877")
				&& appointmentContentFromCalendar
						.findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientEmail")).getText()
						.equals("T@K.A")) {
			return false;
		}
		driver.findElement(By.id("ctl00_ctl00_Content_Main_rcbAppLocations_Arrow")).click();
		Thread.sleep(1000);
		try {
			appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(0).click();
			Thread.sleep(500);
			appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(1).click();
		} catch (TimeoutException e) {
			return false;
		}

		Thread.sleep(1000);
		if (driver.findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4 && driver
				.findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container")).size() != 4) {
			return false;
		}
		driver.findElement(By.id("ctl00_ctl00_Content_Main_tbxSubject")).sendKeys(subject);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_btnAddApp")).click();
		return true;
	}

	public void setSuggestedStartDate(String startDate) throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));

		serviceRequestInfoBlocks.get(0).click();
		suggestedStart.sendKeys(startDate);
		Thread.sleep(2000);
		Actions act = new Actions(driver);
		act.moveToElement(acceptGeneralInfoBTN).click().build().perform();

	}

	public boolean checkDefaultAppointmentDateFromSRedit(String startDate) throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		addAppointmentBTNfromSRedit.click();

		Thread.sleep(1000);
		if (!driver.findElement(By.id("Card_rcbAppointmentPhase_Input")).getAttribute("value").equals("Estimating"))
			return false;

		Thread.sleep(1000);
		System.out.println(appointmentFromDateSRedit.getAttribute("value"));
		if (!(appointmentFromDateSRedit.getAttribute("value").equals(startDate)
				&& appointmentFromTimeSRedit.getAttribute("value").equals("12:00 AM")
				&& appointmentToDateSRedit.getAttribute("value").equals(startDate))) {
			return false;
		}

		Thread.sleep(1000);
		System.out.println(appointmentContent.findElement(By.id("Card_tbxSubject")).getAttribute("value"));
		if (!(appointmentContent.findElement(By.id("Card_tbxSubject")).getAttribute("value").equals("Alex SASHAZ"))) {
			return false;
		}

		appointmentContent.findElement(By.id("Card_rcbAppLocations_Input")).click();

		Thread.sleep(1000);
		if (!driver.findElement(By.className("rcbHovered")).getText().equals("Custom") && appointmentContent
				.findElement(By.id("Card_rcbAppointmentLocations_Input")).getAttribute("disabled").equals("disabled")) {
			return false;
		}

		Thread.sleep(1000);
		if (!appointmentContent.findElement(By.id("Card_rcbTechnician_Input")).getAttribute("value").equals("All")
				&& appointmentContent.findElement(By.id("Card_rcbStates_Input")).getAttribute("value").equals("All")) {
			return false;
		}

		Thread.sleep(1000);
		if (!appointmentContent.findElement(By.id("Card_tbxAddress")).getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxCity")).getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxZip")).getText().isEmpty()) {
			return false;
		}

		Thread.sleep(1000);
		driver.findElement(By.id("Card_btnAddApp")).click();
		return true;
	}

	public boolean retryingFindClick(By by, By byInner, String startDate) throws InterruptedException {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {

			try {
				waitABit(1000);
				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
				Thread.sleep(1500);
				// wait.ignoring(TimeoutException.class).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rsNonWorkHour")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rsWrap")));
				driver.findElement(by).findElements(byInner).stream().map(w -> w.findElement(By.tagName("a")))
						.filter(t -> t.getText().split(" ")[1].equals(startDate.split("/")[1])).findFirst().get()
						.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
				Thread.sleep(500);
			}
			attempts++;
		}
		return result;
	}

	public int checkSchedulerByDateWeek(String startDate, boolean isDateShifted) throws InterruptedException {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("lbViewChangeScheduler"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));

		if (!isDateShifted) {
			retryingFindClick(By.className("rsFullTime"));
			waitABit(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			retryingFindClick(By.className("rsHorizontalHeaderTable"), By.tagName("th"), startDate);
		} else {
			retryingFindClick(By.className("rsNextDay"));
			waitABit(10000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.className("rsFullTime"))).click();
			retryingFindClick(By.className("rsFullTime"));
			waitABit(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			retryingFindClick(By.className("rsHorizontalHeaderTable"), By.tagName("th"), startDate);
		}

		waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		return updateWait.until(ExpectedConditions.presenceOfElementLocated(By.className("rsWrap")))
				.findElements(By.xpath("//div[contains(@class, 'rsApt appointmentClassDefault')]")).size();
	}

	public void goToSRmenu() throws InterruptedException {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnListTop"))).click();
		Thread.sleep(2000);
	}

	public void reloadPage() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(5000);
	}

	public int checkSchedulerByDateMonth(String date) {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("lbViewChangeScheduler"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.className("rsHeaderMonth"))).click();
		driver.findElement(By.xpath("//a[contains(@title, '" + date + "')]")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.className("rsFullTime"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		return updateWait.until(ExpectedConditions.presenceOfElementLocated(By.className("rsNonWorkHour")))
				.findElements(By.xpath("//div[contains(@class, 'rsApt appointmentClassDefault')]")).size();
	}

	public void goToMonthInScheduler() throws InterruptedException {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("lbViewChangeScheduler"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		retryingFindClick(By.className("rsHeaderMonth"));
		// wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.className("rsHeaderMonth"))).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rsDateBox")));
	}

	public boolean checkTechniciansFromScheduler() throws InterruptedException {
		driver.switchTo().defaultContent();
		waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		try {
			retryingFindClick(By.className("scheduler-dropdown"));
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(tecniciansNamesFromScheduler));
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(tecniciansAreasFromScheduler));
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(tecniciansTeamsFromScheduler));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean retryingFindClick(By by) throws InterruptedException {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
				Thread.sleep(500);
			}
			attempts++;
		}
		return result;
	}

	public boolean checkIf5TechiciansIsMaximum() throws InterruptedException {

		for (int i = 0; i < 7; i++) {
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(techniciansList.get(i))).click();
		}
		driver.switchTo().defaultContent();
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(@class, 'divColorPane violet')]")));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'divColorPane limeGreen')]")));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'divColorPane red')]")));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'divColorPane yellow')]")));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'divColorPane blue')]")));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public boolean alpyAndCheck5TecniciansFromScheduler() {
		arrowInTechniciansList.click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'sr-btn btn-apply')]")))
					.click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.id("ctl00_ctl00_Content_Main_AppointmentsScheduler1_RadScheduler1_ctl52_pnlColor")));

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]")));

			if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]"))
					.size() != 5
					&& driver.findElements(By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]"))
							.size() != 5
					&& driver
							.findElements(By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]"))
							.size() != 5
					&& driver.findElements(By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]"))
							.size() != 5
					&& driver.findElements(By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]"))
							.size() != 5) {
				return false;
			}

		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void selectTechnicianFromSchedulerByIndex(int i) throws InterruptedException {
		driver.switchTo().defaultContent();
		waitABit(4000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));

		retryingFindClick(By.className("scheduler-dropdown"));
		Thread.sleep(2000);
		retryingFindClick(techniciansList.get(i));
		Thread.sleep(2000);
	}

	public boolean retryingFindClick(WebElement element) throws InterruptedException {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(element)).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
				Thread.sleep(500);
			}
			attempts++;
		}
		return result;
	}

	public void aplyTechniciansFromScheduler() throws InterruptedException {
		arrowInTechniciansList.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'sr-btn btn-apply')]")))
				.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		Thread.sleep(10000);
	}

	public int countSR() {
		waitABit(3000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		waitABit(1000);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("appointmentClassDefault")));
		int defaultSRs = driver.findElements(By.className("appointmentClassDefault")).size();
		int failedSRs = driver.findElements(By.className("appointmentClassFailed")).size();
		int completedSRs = driver.findElements(By.className("appointmentClassCompleted")).size();
		return defaultSRs + failedSRs + completedSRs;
	}

	public boolean resetAndCheckTecniciansFromScheduler() throws InterruptedException {
		retryingFindClick(By.className("scheduler-dropdown"));
		arrowInTechniciansList.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-reset"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		waitABit(3000);

		if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]")).size() != 0)
			return false;

		if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]")).size() != 0)
			return false;

		if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]"))
				.size() != 0)
			return false;

		if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]")).size() != 0)
			return false;

		if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]")).size() != 0)
			return false;

		return true;
	}

	public int countSRinTimelineByDate(String startDate) {
		List<WebElement> days = horizontalHeaderTimeline.findElements(By.tagName("div"));
		boolean isDateVisible = false;

		for (WebElement e : days) {
			if (e.getText().equals(startDate)) {
				isDateVisible = true;
				break;
			}
		}

		if (!isDateVisible) {
			driver.findElement(By.className("rsNextDay")).click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		}

		int column = 0;

		for (WebElement e : days) {
			if (e.getText().equals(startDate)) {
				break;
			}
			column++;
		}

		return driver.findElement(By.className("rsAllDayTable")).findElements(By.tagName("td")).get(column)
				.findElements(By.className("rsWrap")).size();
	}

	public void goToTimeLine() {
		waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.className("rsHeaderTimeline"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		waitABit(4000);
	}

	public boolean checkLifeCycleBTN() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		driver.findElement(By.id("Card_srLifeCycle")).click();
		waitABit(1000);
		return driver.getWindowHandles().size() == 2;
	}

	public void addAppointmentWithoutDescription(String startDate, String toDate) throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));

		addAppointmentBTNfromSRedit.click();
		appointmentFromDateSRedit.clear();
		appointmentFromTimeSRedit.clear();
		appointmentToDateSRedit.clear();
		appointmentToTimeSRedit.clear();

		appointmentFromDateSRedit.sendKeys(startDate);
		appointmentToDateSRedit.sendKeys(toDate);
		appointmentFromTimeSRedit.sendKeys("6:00 AM");
		appointmentToTimeSRedit.sendKeys("7:00 AM");
		driver.findElement(By.id("Card_rdpEndTime_timePopupLink")).click();

		Thread.sleep(1000);
		driver.findElement(By.id("Card_btnAddApp")).click();

	}

	public boolean checkLifeCycleDate() throws InterruptedException {
		String parentFrame = driver.getWindowHandle();
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("Card_srLifeCycle"))).click();
		Set windows = driver.getWindowHandles();
		windows.remove(parentFrame);
		driver.switchTo().window((String) windows.iterator().next());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		try {
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.partialLinkText(LocalDate.now().format(formatter))));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public void goToLifeCycle() {
		String parentFrame = driver.getWindowHandle();
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
		driver.findElement(By.id("Card_srLifeCycle")).click();
		Set windows = driver.getWindowHandles();
		driver.close();
		windows.remove(parentFrame);
		driver.switchTo().window((String) windows.iterator().next());

	}

	public boolean checkLifeCycleContent() throws InterruptedException {
		Thread.sleep(5000);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'SR')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Year')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'VIN')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Make')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Stock')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Model')]")));

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_tbVin")));
			if (wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_tbSrNumber")))
					.getAttribute("value").isEmpty()) {
				return false;
			}
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void goToDocumentLinkFromLC() {
		waitABit(1000);
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Link to R-')]"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public boolean checkLifeCycleDocumentsContent() {
		waitABit(1000);

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Photos')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Documents')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Type')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Creation')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Description')]")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean checkDocumentDownloadingInLC() throws AWTException, InterruptedException {
		String parentFrame = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[contains(text(), 'Link to Documents')]")).click();
		Set windows = driver.getWindowHandles();
		windows.remove(parentFrame);
		driver.switchTo().window((String) windows.iterator().next());
		return true;
	}

	public WebElement getVehicleEditButton() {
		Actions moveact = new Actions(driver);
		moveact.moveToElement(driver.findElement(By.xpath("//div[@id='Card_divVehInfoAll']/b[text()='Vehicle info:']")))
				.perform();
		return driver.findElement(By.xpath("//div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"));
	}

	public void clickVehicleEditButton() {
		driver.findElement(By.id("Card_divVehInfoAll")).click();
		// click(getVehicleEditButton());
	}

	public void setVehicleInfo(String stock, String vin) {
		driver.findElement(By.id("Card_tbStock")).sendKeys(stock);
		driver.findElement(By.id("Card_vehicleVin")).sendKeys(vin);
	}

	public boolean goToWOfromLC() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Link to Work Order')]")))
					.click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Tag/Lic. Plate #:')]")));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public boolean checkAcceptanceOfSRinLC() {
		LocalDateTime dateToCheck = LocalDateTime.now().minusHours(10);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String dateSTR = dateToCheck.format(formatter);
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'ServiceRequests Accepted')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), '" + dateSTR + "')]")));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public boolean checkRejectOfSRinLC() {
		LocalDateTime dateToCheck = LocalDateTime.now().minusHours(10);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String dateSTR = dateToCheck.format(formatter);
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'ServiceRequests Rejected')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), '" + dateSTR + "')]")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean checkClosedOfSRinLC() {
		LocalDateTime dateToCheck = LocalDateTime.now().minusHours(10);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String dateSTR = dateToCheck.format(formatter);
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'ServiceRequests Closed')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[contains(text(), '" + dateSTR + "')]")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean checkSRsearchCriterias() {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_rbxPhases_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_rbcTeamsForFilter_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_rbcTechsForFilter_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_rcbRepairLocations_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_tbSearchTags_tagsinput")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_tbSearchFreeText")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_btnSearch")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void setServiceRequestGeneralInfo(String _assignedto) throws InterruptedException {
		setServiceRequestGeneralInfoAssignedTo(_assignedto);
		driver.findElement(By.id("Card_ddlClientsAssignedTo_Arrow")).click();
		Thread.sleep(7000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("divGeneralButtonsDone"))).click();
	}

	public void addAppointmentWithTechnisian(String startDate, String endDate, String string)
			throws InterruptedException {
		waitABit(3000);

		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));

		addAppointmentBTNfromSRedit.click();
		waitABit(2000);
		appointmentFromDateSRedit.clear();
		appointmentFromTimeSRedit.clear();
		appointmentToDateSRedit.clear();
		appointmentToTimeSRedit.clear();

		appointmentFromDateSRedit.sendKeys(startDate);
		appointmentToDateSRedit.sendKeys(endDate);
		appointmentFromTimeSRedit.sendKeys("6:00 AM");
		appointmentToTimeSRedit.sendKeys("7:00 AM");
		driver.findElement(By.id("Card_rdpEndTime_timePopupLink")).click();
		Thread.sleep(2000);
		appointmentContent.findElement(By.id("Card_rcbTechnician_Input")).click();
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
		// .findElement(By.linkText(string)).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals(string)).findFirst()
				.get().click();

		Thread.sleep(1000);
		driver.findElement(By.id("Card_btnAddApp")).click();
	}

	public boolean checkEmails(String message) throws InterruptedException {
		boolean flag1 = false;
		Thread.sleep(20000);
		for (int i = 0; i < 7; i++) {
			try {
				if (!MailChecker.searchEmailAndGetMailMessage("automationvozniuk@gmail.com", "ZZzz11!!", message,
						"reconpro+main@cyberiansoft.com").isEmpty()) {
					flag1 = true;
					break;
				}
			} catch (NullPointerException e) {
			}
			Thread.sleep(40000);
		}
		return flag1;
	}

	public void selectSREditFrame() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame((WebElement) driver.findElement(By.xpath("//div[@class='editServiceRequestPanel']")).findElement(By.xpath("./iframe")));
	}

	public boolean checkTestEmails() throws InterruptedException {
		boolean flag1 = false;
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(40000);
				if (!MailChecker.searchEmailAndGetMailMessage("automationvozniuk@gmail.com", "ZZzz11!!",
						"test appointment", "reconpro+main@cyberiansoft.com").isEmpty()) {
					flag1 = true;
				}
			} catch (NullPointerException e) {
			}
		}
		return flag1;
	}

	public int countAvailableServices() throws InterruptedException {
		Thread.sleep(2500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Card_comboService_Arrow")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Card_comboService_Arrow"))).click();
		Thread.sleep(1000);
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.tagName("li")).size();
	}

	public void clickDoneButtonAtAddServiceWindow() {
		driver.findElement(By.linkText("Done")).click();
	}

	public void scrollWindow(String pixels) {
		driver.switchTo().defaultContent();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + pixels + ")", "");
	}

	public void setRO(String ro) {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clearAndType(addsrvronum, ro);
	}

	public boolean checkForAlert() throws InterruptedException {
		try {
			driver.switchTo().alert().accept();
			Thread.sleep(5000);
			return true;
		} catch (Exception e) {
			Thread.sleep(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			return false;
		}
	}

	public String getAllAvailableServices() throws InterruptedException {
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Card_comboService_Arrow")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Card_comboService_Arrow"))).click();
		Thread.sleep(2500);
		return driver.findElement(By.id("Card_comboService_MoreResultsBox")).findElements(By.tagName("b")).get(2)
				.getText();
	}

	public boolean checkSRcreationMenuAtributes() {
		try {
			if (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("infoBlock-editBtn")))
					.size() != 6)
				return false;
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("divGeneralInfo")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//b[text()='Customer:']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Card_divVehInfoAll")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//b[text()='Description:']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//b[text()='Service:']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//b[text()='Appointments:']")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickRejectUndoButton() {
		Actions builder = new Actions(driver);
		builder.moveToElement(getFirstServiceRequestFromList()).perform();
		getFirstServiceRequestFromList().findElement(By.xpath(".//a[@title='Undo Reject']")).click();
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
		}
	}

	public boolean checkPresentanceOfServiceAdvisorsByFilter(String filter) {
		wait.until(ExpectedConditions.elementToBeClickable(serviceAdvisorArrow)).click();
		try {
			wait.until(ExpectedConditions.visibilityOf(advisorUsersList));
			Thread.sleep(4000);
			List<WebElement> list = advisorUsersList.findElements(By.tagName("li"));
			if (list.size() < 1)
				return false;
			advisorInputField.sendKeys(filter);
			Thread.sleep(3000);
			boolean result = advisorUsersList.findElements(By.tagName("li")).stream()
					.map(e -> e.getText()).map(t -> t.toLowerCase()).allMatch(t -> t.contains(filter));
			advisorUsersList.findElements(By.tagName("li")).get(0).click();
			Thread.sleep(2000);
			return result;
		} catch (TimeoutException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

	public String getkServiceAdvisorName() throws InterruptedException {
		getCustomerEditButton().click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(serviceAdvisorArrow)).click();
		wait.until(ExpectedConditions.visibilityOf(advisorUsersList));
		Thread.sleep(4000);
		String name = advisorUsersList.findElements(By.tagName("li")).stream()
		.map(e -> e.getText()).findFirst().get();
		return name;
	}

	public boolean checkAddedServices(String...services) {
	for(int i = 0; i < services.length; i++){
		try{
			
		}catch(TimeoutException e){
			return false;
		}
	}
		return true;
	}
}
