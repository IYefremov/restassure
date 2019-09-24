package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.dataclasses.ServiceData;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;


public class ServiceRequestsListWebPage extends BaseWebPage implements ClipboardOwner {

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchTab;

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']//div[@class='content']")
	private WebElement searchTabExpanded;

	@FindBy(className = "toggler")
	private WebElement searchButton;

	@FindBy(xpath = "//div[@id='itemContainer']")
	private WebElement servicerequestslist;

	@FindBy(id = "lbAddServiceRequest")
	private WebElement addservicerequestbtn;

	@FindBy(className = "command-accept")
	private WebElement serviceRequestsAcceptButton;

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

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_comboFilterSrTypes_Input")
	private ComboBox serviceRequestcmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_comboFilterSrTypes_DropDown")
	private DropDown serviceRequestdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_tbSearchTags_tag")
	private TextField tagsfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_tbSearchFreeText")
	private WebElement freetextfld;

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
	private WebElement serviceRequestCheckInButton;

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
	private WebElement addServiceRequestDropDown;

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

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_comboAreasTechPopup_Arrow")
	private WebElement techniciansAreasFromSchedulerArrow;

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_comboTeamsTechPopup_Input")
	private WebElement techniciansTeamsFromScheduler;

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_comboTechniciansPopup_Input")
	private WebElement techniciansFromScheduler;

	@FindBy(className = "tech-item")
	private List<WebElement> techniciansList;

	@FindBy(id = "btnAddTechniciansToList")
    private WebElement techniciansSchedulerAddButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_comboTechniciansPopup_Arrow")
    private WebElement techniciansSchedulerArrow;

	@FindBy(id = "ctl00_ctl00_Content_Main_AppointmentsScheduler1_comboTechniciansPopup_DropDown")
    private WebElement techniciansDropDown;

	@FindBy(xpath = "//div[@id='ctl00_ctl00_Content_Main_AppointmentsScheduler1_comboTechniciansPopup_DropDown']//li")
    private List<WebElement> techniciansListBox;

	@FindBy(className = "show-btns")
	private WebElement arrowInTechniciansList;

	@FindBy(xpath = "//a[contains(@class, 'sr-btn btn-apply')]")
	private WebElement techniciansDialogApplyButton;

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

	@FindBy(xpath = "//div[@class='infoBlock-footer']/div[contains(@class, 'infoBlock-doneBtn')]")
	private List<WebElement> donebtns;

	@FindBy(xpath = "//div[@id='itemContainer']/div[1]//a[@class='detailsPopover']")
	private WebElement firstServiceRequestDetails;

	@FindBy(xpath = "//a[@class='detailsPopover']")
	private List<WebElement> serviceRequestsPopoverList;

	@FindBy(xpath = "//div[@class='editServiceRequestPanel']/iframe")
	private WebElement editServiceRequestPanelFrame;

	@FindBy(id = "Card_rdpEndTime_timePopupLink")
	private WebElement timePopupLink;

	@FindBy(id = "RadToolTipWrapper_Card_RadToolTip1")
	private WebElement appointmentTable;

	@FindBy(className = "sr-btn btn-check-in-sr pull-right")
	private WebElement checkInButton;

	@FindBy(xpath = "//div[@id='Card_divVehInfoAll']/span")
	private WebElement vehicleInfoButton;

	@FindBy(xpath = "//div[@class='infoBlock-item infoBlock-edit vehicle']")
	private WebElement editVehicleInfoBlock;

	@FindBy(xpath = "//div[text()='ServiceRequests Appointment Created']/following::div[1]")
	private WebElement lifeCycleBlock;

	@FindBy(xpath = "//input[@id='Card_tbxSubject']")
	private WebElement subjectField;

	@FindBy(id = "Card_rcbAppointmentPhase_Input")
	private WebElement phaseField;

	@FindBy(id = "Card_rcbAppLocations_Input")
	private WebElement locationTypeField;

	@FindBy(id = "Card_rcbTechnician_Input")
	private WebElement techniciansField;

	@FindBy(id = "Card_rdpEndTime_timeView_wrapper")
	private WebElement endTimeDialog;

	@FindBy(id = "ctl00_ctl00_Content_Main_rcbTechnician_Arrow")
	private WebElement techniciansArrow;

	@FindBy(id = "ctl00_ctl00_Content_Main_report_AsyncWait_Wait")
	private WebElement loading;

	@FindBy(xpath = "//div[@id='RadToolTipWrapper_Card_RadToolTip1']//span[@class='spanAppointmentWarning']")
	private WebElement appointmentWarning;

	@FindBy(xpath = "//div[@class='infoBlock-content']/b[text()='Service:']//following-sibling::span")
	private WebElement servicesEditButton;

	@FindBy(xpath = "//div[@id='Card_ddlClients_DropDown']//li")
	private List<WebElement> clients;

	@FindBy(xpath = "//div[contains(@id, 'RadToolTipWrapper')]")
	private WebElement appointmentDialog;

	@FindBy(id = "lbViewChangeScheduler")
	private WebElement scheduler;

	@FindBy(id = "Card_rcbAppLocations_Arrow")
	private WebElement arrow;

	@FindBy(id = "Card_hrefSREstimate")
	private WebElement serviceRequestInspection;

	@FindBy(id = "Card_hrefSRWO")
	private WebElement serviceRequestWorkOrder;

	@FindBy(id = "Card_hrefSRInvoice")
	private WebElement serviceRequestInvoice;

	@FindBy(id = "addAppointmentLinkEdit")
	private WebElement serviceRequestAppointmentFrame;

	@FindBy(xpath = "//input[@id='Card_btnAddApp']")
	private WebElement appointmentDialogAddButton;

	@FindBy(xpath = "//*[contains(@id, 'ddlTimeframe_Input')]")
	private ComboBox searchtimeframecmb;

	@FindBy(xpath = "//*[contains(@id, 'ddlTimeframe_DropDown')]")
	private DropDown searchtimeframedd;

	@FindBy(xpath = "//div[@id='techItems-content']/div[@class='tech-item']")
	private List<WebElement> techItemsList;

	public ServiceRequestsListWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		PageFactory.initElements(driver, WebPageWithPagination.class);
	}

	private boolean isSearchPanelExpanded() {
	    return Utils.isElementDisplayed(searchTabExpanded, 7);
	}

	public void makeSearchPanelVisible() {
        if (!isSearchPanelExpanded()) {
            Utils.clickElement(searchButton);
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
		selectComboboxValueWithTyping(teamcmb, teamdd, teamname);
	}

	public void selectSearchTechnician(String technician) {
		selectComboboxValueWithTyping(techniciancmb, techniciandd, technician);
	}

	public void setServiceRequestType(String serviceRequest) {
		selectComboboxValue(serviceRequestcmb, serviceRequestdd, serviceRequest);
	}

	public void setSearchFreeText(String anytext) {
	    Utils.clearAndType(freetextfld, anytext);
	}

	public void clickFindButton() {
		try {
			wait.ignoring(Exception.class).until(ExpectedConditions.alertIsPresent()).accept();
		} catch (TimeoutException ignored) {
		}
		clickAndWait(findbtn);
	}

	public void clickAddServiceRequestButton() {
		waitABit(2000);
		wait.until(ExpectedConditions.elementToBeClickable(addservicerequestbtn)).click();
		waitForLoading();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")));
		driver.switchTo().frame(editServiceRequestPanelFrame);
		wait.until(ExpectedConditions.elementToBeClickable(saveservicerequestbutton));
		waitABit(3000);
	}

	public WebElement getFirstServiceRequestFromList() {
		if (servicerequestslist.findElements(By.xpath("./div[@class='item']")).size() > 0) {
			return servicerequestslist.findElement(By.xpath("./div[@class='item']"));
		}
		return null;
	}

    public void selectFirstServiceRequestFromList() {
        Utils.clickWithActions(serviceRequestsPopoverList.get(0));
        waitForLoading();
        switchToServiceRequestInfoFrame();
    }

	public void closeFirstServiceRequestFromTheList() {
		selectFirstServiceRequestFromList();
		switchToServiceRequestInfoFrame();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(closeservicerequestbtn));
		} catch (TimeoutException e) {
			driver.switchTo().defaultContent();
			selectFirstServiceRequestFromList();
			wait.until(ExpectedConditions.elementToBeClickable(closeservicerequestbtn));
		}
		clickCloseServiceRequestButton();
		driver.switchTo().defaultContent();
		waitForLoading();
	}

    public void clickCloseServiceRequestButton() {
        Utils.clickElement(closeservicerequestbtn);
    }

	public void acceptFirstServiceRequestFromList() {
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickElement(serviceRequestsAcceptButton);
        waitForLoading();
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
		return getFirstServiceRequestFromList()
				.findElement(By.xpath(".//span[@class='serviceRequestStatus']"))
				.getText()
				.replaceAll("\\u00A0", "")
				.trim();
	}

	public String getFirstInTheListServiceRequestNumber() {
		String srnumber = getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='itemSrNo']/b"))
				.getText();
		return srnumber.substring(0, srnumber.length() - 1);
	}

	public void clickAddAppointmentToFirstServiceRequestFromList() {
		wait.until(ExpectedConditions
				.visibilityOf(getFirstServiceRequestFromList()
						.findElement(By.xpath(".//i[contains(@class, 'icon-calendar')]"))))
				.click();
		waitABit(2000);
	}

	public void setServiceRequestAppointmentTechnicians(String tech) {
		selectComboboxValueWithTyping(addservicerequesapptechcmb, addservicerequesapptechdd, tech);
	}

	public boolean appointmentExistsForFirstServiceRequestFromList(String appointmenttime) {
		System.out.println(getFirstServiceRequestFromList().findElement(By.xpath(".//a/span")).getText().equals(appointmenttime));
		return getFirstServiceRequestFromList().findElement(By.xpath(".//a/span")).getText().equals(appointmenttime);
	}

	public String getWOForFirstServiceRequestFromList() {
		WebElement element = getFirstServiceRequestFromList().findElement(By.xpath(".//span[@class='itemWO']"));
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getText();
	}

    private WebElement getServiceRequestCellBySRNumber(String srNumber) {
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

    public boolean isInsuranceCompanyPresentForFirstServiceRequestFromList(String insurancecompany) {
        waitForLoading();
        return getFirstServiceRequestFromList()
                .findElements(By.xpath(".//div[@class='" + insurancecompany + "  ']")).size() > 0;
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
                .getText()
                .replaceAll("\\u00A0", "")
                .trim();
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
        wait.until(ExpectedConditions.elementToBeClickable(vehicleInfoButton)).click();
        try {
            wait.until(ExpectedConditions.attributeContains(editVehicleInfoBlock, "display", "block"));
        } catch (TimeoutException e) {
            Assert.fail("The edit Vehicle Info block has not been opened.", e);
        }
    }

    public void clickClaimInfoEditButton() {
        switchToServiceRequestInfoFrame();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("Card_divCliamInfoAll")))).click();
    }

    public void clickServiceEditButton() {
        wait.until(ExpectedConditions.visibilityOf(servicesEditButton));
        clickWithJS(servicesEditButton);
    }

    public List<WebElement> getServiceRequestServicesToSelect() {
        WebElement services = driver
                .findElement(By.xpath("//div[@class='infoBlock-item infoBlock-edit servicesBlock']"));
        return services.findElements(By.xpath(".//div[@class='container-service']"));
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
        return vehicleInfoButton;

//                driver.findElement(By.xpath("//div[@id='Card_divVehInfoAll']/span"));
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

    public void setServiceRequestGeneralInfo(String _team, String assignedto, String po, String ro) {
        setServiceRequestGeneralInfo(_team, assignedto);
        clearAndType(addsrvponum, po);
        clearAndType(addsrvronum, ro);
    }

    public void setServiceRequestGeneralInfo(String _team, String assignedto) {
        setServiceRequestGeneralInfoTeam(_team);
        waitABit(2000);
        setServiceRequestGeneralInfoAssignedTo(assignedto);
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

    public void selectServiceRequestInsurance(String insurance) {
        selectComboboxValueWithTyping(addsrvinsurancecmb, addsrvinsurancedd, insurance);
    }

    public void clickDoneButton() {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(donebtns);
        for (WebElement donebtn : donebtns) {
            if (donebtn.isDisplayed()) {
                Utils.clickElement(donebtn);
                break;
            }
        }
        waitABit(1000);
    }

	public boolean saveNewServiceRequest() {
		try {
            Utils.clickElement(saveservicerequestbutton);
			wait.until(ExpectedConditions.elementToBeClickable(saveservicerequestbutton));
			waitForLoading();
			driver.switchTo().defaultContent();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void cancelNewServiceRequest() {
		Utils.clickElement(cancelservicerequestbutton);
		driver.switchTo().defaultContent();
	}

	public void clickCheckInButtonForSelectedSR() {
		switchToServiceRequestInfoFrame();
		Utils.clickElement(serviceRequestCheckInButton);
		driver.switchTo().defaultContent();
		waitABit(5000);
	}

	public String getCheckInButtonValueForSelectedSR() {
		String value;
		switchToServiceRequestInfoFrame();
		value = serviceRequestCheckInButton.getText();
		driver.switchTo().defaultContent();
		return value;
	}

	public boolean isCheckInButtonDisplayedForSelectedSR() {
		boolean exists;
		switchToServiceRequestInfoFrame();
		exists = driver.findElements(By.id("btnCheckInCheckOut")).size() > 0;
		driver.switchTo().defaultContent();
		return exists;
	}

	public boolean isCheckInButtonVisible() {
		switchToServiceRequestInfoFrame();
		boolean visible = Utils.isElementDisplayed(serviceRequestCheckInButton);
		driver.switchTo().defaultContent();
		return visible;
	}

	public String getVINValueForSelectedServiceRequest() {
		switchToServiceRequestInfoFrame();
		String VINValue = driver.findElement(By.xpath("//span[@data-for='Card_vehicleVin']")).getText();
		driver.switchTo().defaultContent();
		return VINValue;
	}

	public String getCustomerValueForSelectedServiceRequest() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		String clientname = driver.findElement(By.xpath("//span[@data-for='Card_hdnFullClientName']")).getText();
		driver.switchTo().defaultContent();
		return clientname;
	}

	public String getEmployeeValueForSelectedServiceRequest() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		String employee = driver.findElement(By.xpath("//span[@data-for='Card_hdnEmployeeFullName']")).getText();
		driver.switchTo().defaultContent();
		return employee;
	}

	public boolean isServiceIsPresentForForSelectedServiceRequest(String servicename) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
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

	public void addServicesToServiceRequest(String... services) {
		WebElement servicespopup = clickAddServicesIcon();
		for (String srv : services) {
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

	public void addServicesToServiceRequest(List<ServiceData> servicesData) {
		WebElement servicespopup = clickAddServicesIcon();
		for (ServiceData serviceData : servicesData) {
			//driver.findElement(By.id("Card_comboService_Input")).click();
			driver.findElement(By.id("Card_comboService_Input")).clear();
			waitABit(1000);
			driver.findElement(By.id("Card_comboService_Input")).sendKeys(serviceData.getServiceName());
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
		switchToServiceRequestInfoFrame();
//		driver.switchTo().defaultContent();
//		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		click(driver.findElement(By.xpath("//div[@class='description-content']/span[@class='infoBlock-editBtn']")));
		wait.until(ExpectedConditions.elementToBeClickable(addsrvdescription.getWrappedElement()));
		try {
			new SimpleDateFormat("dd yyyy hh:mm").parse(descriptionTime.getText().substring(4, 17));
			wait.until(ExpectedConditions.elementToBeClickable(closeservicerequestbtn)).click();
			return true;
		} catch (ParseException e) {
			wait.until(ExpectedConditions.elementToBeClickable(closeservicerequestbtn)).click();
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
		WebElement lastDescription = wait.until(ExpectedConditions.visibilityOfAllElements(oldDescriptions)).get(0);
		if (!lastDescription.findElement(By.tagName("span")).getText().equals(prevDescription)) {
			return false;
		}
		addsrvdescription.clear();
		driver.findElement(By.xpath("//div[@class='description-content']"))
				.findElement(By.xpath(".//div[@class='infoBlock-doneBtn sr-btn']")).click();

		return driver.findElement(By.className("description-content")).findElement(By.className("infoBlock-valContainer"))
				.getAttribute("style").equals("display: none;");
	}

	public boolean checkServiceDescription(String string) {
		switchToServiceRequestInfoFrame();
		waitABit(3000);
		click(driver.findElement(By.xpath("//div[@class='description-content']/span[@class='infoBlock-editBtn']")));
		wait.until(ExpectedConditions.elementToBeClickable(addsrvdescription.getWrappedElement()));
		WebElement lastDescription = oldDescriptions.get(0);
		return lastDescription.findElement(By.tagName("span")).getText().equals(string);

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
		switchToServiceRequestInfoFrame();
		return wait.until(ExpectedConditions
				.not(ExpectedConditions
						.attributeToBe(driver.findElement(By.xpath("//div[contains(@class, 'description-reason')]"))
								.findElement(By.tagName("i")), "style", "display : none;")));
//        return !driver.findElement(By.xpath("//div[contains(@class, 'description-reason')]")).findElement(By.tagName("i")).getAttribute("style").equals("display : none;");
	}

	public void clickDocumentButton() {
		switchToServiceRequestInfoFrame();
		String oldWindow = driver.getWindowHandle();
		descriptionDocuments.findElement(By.tagName("i")).click();
		Set allWindows = driver.getWindowHandles();
		allWindows.remove(oldWindow);
		try {
			driver.switchTo().window((String) allWindows.iterator().next());
		} catch (NoSuchElementException ignored) {
		}
	}

	public boolean checkElementsInDocument() {
		try {
			documentContent.findElement(By.xpath("//h2[contains(text(), 'Documents')]"));
			documentContent.findElement(By.xpath("//h3[contains(text(), 'Service Request:')]"));
			documentContent.findElement(By.className("add"));
			return documentContent.findElements(By.className("rgHeader"))
					.stream()
					.map(WebElement::getText)
					.collect(Collectors.toList())
					.containsAll(Arrays.asList("Name/Description", "Size", "Uploaded"));
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean clickAddImageBTN() {
		driver.findElement(By.className("add")).click();
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnOk")));
			wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnCancel")));

			wait.until(ExpectedConditions
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnOk")))
				.click();
		waitForLoading();
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	}

	public void setClipboardContents(String aString) {

		StringSelection stringSelection = new StringSelection(aString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}

	public boolean checkPresentanceOFAddedFile() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_gv_ctl00_ctl04_gbccolumn")))
					.click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_ctl01_ctl01_Card_tbName")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_ctl01_ctl02_BtnOk")))
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
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_Content_gv_ctl00_ctl04_gbccolumn1")))
					.click();
			driver.switchTo().alert().accept();
			waitABit(3000);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rgNoRecords")));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public void selectAddServiceRequestDropDown(String string) {
		addServiceRequestDropDown.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e
				.getText()
				.equals(string))
				.findFirst()
				.ifPresent(WebElement::click);
	}

	public void setCustomer(String customer) throws InterruptedException {
		switchToServiceRequestInfoFrame();

		serviceRequestInfoBlocks.get(1).click();
		customerName.click();
		customerName.sendKeys(customer);
		customerName.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		Actions act = new Actions(driver);
		act.moveToElement(acceptCustomerBTN).click().build().perform();
		Thread.sleep(2000);
		switchToServiceRequestInfoFrame();
		wait.until(ExpectedConditions.elementToBeClickable(serviceRequestInfoBlocks.get(1))).click();
		act.moveToElement(acceptCustomerBTN).click().build().perform();
		Thread.sleep(2000);
	}

	public boolean addAppointmentFromSRlist(String fromDate, String toDate) {
		wait.until(ExpectedConditions.elementToBeClickable(appointmentCalendarIcon)).click();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDate)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToDate)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTime)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToTime)).clear();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDate)).sendKeys(fromDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentToDate)).sendKeys(toDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTime)).sendKeys("6:00 AM");
		wait.until(ExpectedConditions.visibilityOf(appointmentToTime)).sendKeys("7:00 AM");
		wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.id("ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink"))))
				.click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(addAppointmentBTN)).click();
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean addAppointmentFromSRlist(String fromDate, String toDate, String technician) {
		wait.until(ExpectedConditions.elementToBeClickable(appointmentCalendarIcon)).click();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDate)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToDate)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTime)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToTime)).clear();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDate)).sendKeys(fromDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentToDate)).sendKeys(toDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTime)).sendKeys("6:00 AM");
		wait.until(ExpectedConditions.visibilityOf(appointmentToTime)).sendKeys("7:00 AM");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("gvTechnicians")));
		waitABit(1000);
		setServiceRequestAppointmentTechnicians(technician);
		try {
			wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.id("ctl00_ctl00_Content_Main_btnAddApp")))).click();
			try {
				wait.until(ExpectedConditions.attributeContains(appointmentDialog, "display", "none"));
			} catch (Exception ignored) {
			}
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean checkDefaultAppointmentValuesAndAddAppointmentFomSREdit() {
		verifyAddAppointmentButtonIsClickableForFirstServiceRequest();

		if (!(appointmentFromDateSRedit.getText().isEmpty() && appointmentToDateSRedit.getText().isEmpty()
				&& appointmentFromTimeSRedit.getText().isEmpty() && appointmentToTimeSRedit.getText().isEmpty())) {
			return false;
		}

//		wait.until(ExpectedConditions.visibilityOf(subjectField));
//		if (!subjectField.getAttribute("value").equals("Alex SASHAZ")) {
//			return false;
//		}

		wait.until(ExpectedConditions.elementToBeClickable(locationTypeField)).click();

		if (!driver.findElement(By.className("rcbHovered")).getText().equals("Custom") && appointmentContent
				.findElement(By.id("Card_rcbAppointmentLocations_Input")).getAttribute("disabled").equals("disabled")) {
			return false;
		}

		if (!techniciansField.getAttribute("value").equals("All")
				&& appointmentContent.findElement(By.id("Card_rcbStates_Input")).getAttribute("value").equals("All")) {
			return false;
		}

		if (!appointmentContent.findElement(By.id("Card_tbxAddress")).getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxCity")).getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxZip")).getText().isEmpty()) {
			return false;
		}

		if (!appointmentContent.findElement(By.id("Card_tbAppointmentClientName")).getText().equals("Alex SASHAZ")
				&& appointmentContent.findElement(By.id("Card_tbAppointmentClientAddress")).getText()
				.equals("407 SILVER SAGE DR., NewYork, 10001")
				&& appointmentContent.findElement(By.id("Card_tbAppointmentClientPhone")).getText()
				.equals("14043801674")
				&& appointmentContent.findElement(By.id("Card_tbAppointmentClientEmail")).getText()
				.equals("ALICIA.VILLALOBOS@KCC.COM")) {
			return false;
		}
		wait.until(ExpectedConditions.elementToBeClickable(arrow)).click();
		waitABit(2000);
		try {
			wait.until(ExpectedConditions
					.elementToBeClickable(techniciansField)).click();
			wait.ignoring(WebDriverException.class).until(ExpectedConditions.elementToBeClickable(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(0).click();
			waitABit(500);
			wait.ignoring(WebDriverException.class).until(ExpectedConditions.elementToBeClickable(techniciansField)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(1).click();
		} catch (TimeoutException e) {
			e.printStackTrace();
			return false;
		}

		waitABit(1000);
		if (driver.findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4 && driver
				.findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container")).size() != 4) {
			return false;
		}
		driver.findElement(By.id("Card_btnAddApp")).click();
		return true;
	}

	public void clickGetAppointmentButton() {
		wait.until(ExpectedConditions.elementToBeClickable(appointmentDialogAddButton)).click();
		try {
			waitShort.until(ExpectedConditions.invisibilityOf(appointmentTable));
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private void verifyAddAppointmentButtonIsClickableForFirstServiceRequest() {
		try {
			clickAddAppointmentButtonFromSREdit();
		} catch (TimeoutException e) {
			driver.switchTo().defaultContent();
			selectFirstServiceRequestFromList();
			clickAddAppointmentButtonFromSREdit();
		}
		wait.until(ExpectedConditions.visibilityOf(appointmentTable));
	}

	public void clickAddAppointmentButtonFromSREdit() {
		wait.until(ExpectedConditions.elementToBeClickable(addAppointmentBTNfromSRedit)).click();
		wait.until(ExpectedConditions.visibilityOf(appointmentTable));
	}

	public boolean checkShowHideTechs(String startDate, String endDate) {
		verifyAddAppointmentButtonIsClickableForFirstServiceRequest();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDateSRedit)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToDateSRedit)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTimeSRedit)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToTimeSRedit)).clear();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDateSRedit)).sendKeys(startDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentToDateSRedit)).sendKeys(endDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTimeSRedit)).sendKeys("6:00 AM");
		wait.until(ExpectedConditions.visibilityOf(appointmentToTimeSRedit)).sendKeys("7:00 AM");
		wait.until(ExpectedConditions.elementToBeClickable(timePopupLink)).click();
		wait.until(ExpectedConditions.attributeToBe(endTimeDialog, "visibility", "hidden"));

		try {
			wait.until(ExpectedConditions.elementToBeClickable(techniciansField)).click();
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
			waitABit(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.tagName("li")).get(0).click();
			waitABit(500);
			wait.until(ExpectedConditions.elementToBeClickable(techniciansField)).click();
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
			waitABit(2000);
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
			waitABit(2000);
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

	public boolean checkDefaultAppointmentValuesFromCalendar(String fromDate, String toDate, String subject) {
		wait.until(ExpectedConditions.elementToBeClickable(appointmentCalendarIcon)).click();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDate)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToDate)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTime)).clear();
		wait.until(ExpectedConditions.visibilityOf(appointmentToTime)).clear();

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDate)).sendKeys(fromDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentToDate)).sendKeys(toDate);
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTime)).sendKeys("6:00 AM");
		wait.until(ExpectedConditions.visibilityOf(appointmentToTime)).sendKeys("7:00 AM");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink")).click();

		waitABit(1000);

		appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbAppLocations_Input")).click();

		waitABit(1000);
		if (!driver.findElement(By.className("rcbHovered")).getText().equals("Custom") && appointmentContentFromCalendar
				.findElement(By.id("ctl00_ctl00_Content_Main_rcbAppointmentLocations_Input")).getAttribute("disabled")
				.equals("disabled")) {
			return false;
		}

		waitABit(1000);
		if (!appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input"))
				.getAttribute("value").equals("All")) {
			return false;
		}

		// appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).click();

		waitABit(1000);
		System.out.println(
				driver.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).getAttribute("disabled"));
		if (!driver.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).getAttribute("disabled")
				.equals("true")) {
			return false;
		}
		// driver.findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Arrow")).click();

		waitABit(1000);
		if (!appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbxAddress")).getText()
				.isEmpty()
				&& appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbxCity")).getText()
				.equals("L.A.")
				&& appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_tbxZip")).getText()
				.equals("78523")) {
			return false;
		}

		waitABit(1000);
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
		waitABit(1000);
		try {
			appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(0).click();
			waitABit(500);
			appointmentContentFromCalendar.findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).get(1).click();
		} catch (TimeoutException e) {
			return false;
		}

		waitABit(1000);
		if (driver.findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4 && driver
				.findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container")).size() != 4) {
			return false;
		}
		driver.findElement(By.id("ctl00_ctl00_Content_Main_tbxSubject")).sendKeys(subject);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_btnAddApp")).click();
		return true;
	}

	public void setSuggestedStartDate(String startDate) {
		switchToServiceRequestInfoFrame();
		serviceRequestInfoBlocks.get(0).click();
		wait.until(ExpectedConditions.elementToBeClickable(suggestedStart)).sendKeys(startDate);
		wait.until(ExpectedConditions.elementToBeClickable(acceptGeneralInfoBTN)).click();
	}

	public boolean checkDefaultAppointmentDateFromSRedit(String startDate) {
		switchToServiceRequestInfoFrame();
		clickAddAppointmentButtonFromSREdit();

		if (!wait.until(ExpectedConditions.visibilityOf(phaseField)).getAttribute("value").equals("Estimating"))
			return false;

		wait.until(ExpectedConditions.visibilityOf(appointmentFromDateSRedit));
		if (!(appointmentFromDateSRedit.getAttribute("value").equals(startDate)
//				&& appointmentFromTimeSRedit.getAttribute("value").equals("12:00 AM") //todo uncomment ??? fails for TC 56835
				&& appointmentToDateSRedit.getAttribute("value").equals(startDate))) {
			return false;
		}

//		if (!wait.until(ExpectedConditions.visibilityOf(subjectField)).getAttribute("value").equals("Alex SASHAZ")) {
//			return false;
//		}

		wait.until(ExpectedConditions.elementToBeClickable(locationTypeField)).click();

		if (!wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("rcbHovered")))).getText().equals("Custom") && appointmentContent
				.findElement(By.id("Card_rcbAppointmentLocations_Input")).getAttribute("disabled").equals("disabled")) {
			return false;
		}

		if (!wait.until(ExpectedConditions.elementToBeClickable(techniciansField)).getAttribute("value").equals("All")
				&& appointmentContent.findElement(By.id("Card_rcbStates_Input")).getAttribute("value").equals("All")) {
			return false;
		}

		if (!wait.until(ExpectedConditions.visibilityOf(appointmentContent.findElement(By.id("Card_tbxAddress"))))
				.getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxCity")).getText().isEmpty()
				&& appointmentContent.findElement(By.id("Card_tbxZip")).getText().isEmpty()) {
			return false;
		}

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("Card_btnAddApp")))).click();
		return true;
	}

	public boolean retryingFindClick(By by, By byInner, String startDate) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {

			try {
				waitForLoading();
				waitABit(1500);
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rsWrap")));
				} catch (Exception ignored) {
				}
				wait
						.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)))
						.findElements(byInner)
						.stream().map(w -> w.findElement(By.tagName("a")))
						.filter(t -> t.getText().split(" ")[1].equals(startDate.split("/")[1]))
						.findFirst()
						.get()
						.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
				waitABit(500);
			}
			attempts++;
		}
		return result;
	}

	public int checkSchedulerByDateWeek(String startDate, boolean isDateShifted) {
		driver.switchTo().defaultContent();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(scheduler)).click();
		} catch (Exception e) {
			Assert.fail("The scheduler has not been clickable", e);
		}
		waitForLoading();

		if (!isDateShifted) {
			retryingFindClick(By.className("rsFullTime"));
			waitForLoading();
			retryingFindClick(By.className("rsHorizontalHeaderTable"), By.tagName("th"), startDate);
		} else {
			retryingFindClick(By.className("rsNextDay"));
			waitForLoading();
			wait.until(ExpectedConditions.elementToBeClickable(By.className("rsFullTime"))).click();
			retryingFindClick(By.className("rsFullTime"));
			waitForLoading();
			retryingFindClick(By.className("rsHorizontalHeaderTable"), By.tagName("th"), startDate);
		}

		waitForLoading();
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rsWrap")))
				.findElements(By.xpath("//div[contains(@class, 'rsApt appointmentClassDefault')]")).size();
	}

	public void goToSRmenu() {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnListTop"))).click();
		waitABit(2000);
	}

	public void reloadPage() {
		driver.navigate().refresh();
		waitABit(5000);
	}

	public int checkSchedulerByDateMonth(String date) {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("lbViewChangeScheduler"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("rsHeaderMonth"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@title, '" + date + "')]"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("rsFullTime"))).click();
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rsNonWorkHour")))
				.findElements(By.xpath("//div[contains(@class, 'rsApt appointmentClassDefault')]")).size();
	}

	public void goToMonthInScheduler() {
		driver.switchTo().defaultContent();
		waitForLoading();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("lbViewChangeScheduler"))).click();
		waitForLoading();
		retryingFindClick(By.className("rsHeaderMonth"));
		// wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.className("rsHeaderMonth"))).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rsDateBox")));
	}

	public boolean checkTechniciansFromScheduler() {
		driver.switchTo().defaultContent();
		waitForLoading();
		try {
			retryingFindClick(By.className("scheduler-dropdown"));
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(techniciansAreasFromSchedulerArrow));
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(techniciansTeamsFromScheduler));
			wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(techniciansFromScheduler));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	private boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
				waitABit(500);
			}
			attempts++;
		}
		return result;
	}

	public int getMaximumTechniciansListSize() {
		for (int i = 0; i < 7; i++) {
            addTechnician();
		}
        final int size = WaitUtilsWebDriver.waitForVisibilityOfAllOptions(techniciansList, 5).size();
		driver.switchTo().defaultContent();
		return size;
	}

    private void addTechnician() {
        Utils.clickElement(techniciansSchedulerArrow);
        Utils.selectOptionInDropDown(techniciansDropDown, techniciansListBox);
        Utils.clickElement(techniciansSchedulerAddButton);
    }

    public boolean applyAndCheck5TechniciansFromScheduler() {
		arrowInTechniciansList.click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'sr-btn btn-apply')]")))
					.click();
			waitForLoading();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.id("ctl00_ctl00_Content_Main_AppointmentsScheduler1_RadScheduler1_ctl36_pnlColor")));

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

	public void selectTechnicianFromSchedulerByIndex(int i) {
		driver.switchTo().defaultContent();
		waitForLoading();

		retryingFindClick(By.className("scheduler-dropdown"));
		waitABit(2000);
		retryingFindClick(techniciansList.get(i));
		waitABit(2000);
	}

	public boolean retryingFindClick(WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(element)).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
				waitABit(500);
			}
			attempts++;
		}
		return result;
	}

	public void applyTechniciansFromScheduler() {
	    Utils.clickElement(arrowInTechniciansList);
	    WaitUtilsWebDriver.waitForVisibilityIgnoringException(techniciansDialogApplyButton, 5);
	    Utils.clickElement(techniciansDialogApplyButton);
		waitForLoading();
	}

	public int countSR() {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("appointmentClassDefault")));
		int defaultSRs = driver.findElements(By.className("appointmentClassDefault")).size();
		int failedSRs = driver.findElements(By.className("appointmentClassFailed")).size();
		int completedSRs = driver.findElements(By.className("appointmentClassCompleted")).size();
		return defaultSRs + failedSRs + completedSRs;
	}

	public boolean resetAndCheckTecniciansFromScheduler() {
		retryingFindClick(By.className("scheduler-dropdown"));
		arrowInTechniciansList.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-reset"))).click();
		waitForLoading();
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

		return driver.findElements(By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]")).size() == 0;
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
			waitForLoading();
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
		waitForLoading();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("rsHeaderTimeline"))).click();
		waitForLoading();
		waitABit(4000);
	}

	public boolean checkLifeCycleBTN() {
		switchToServiceRequestInfoFrame();
		driver.findElement(By.id("Card_srLifeCycle")).click();
		waitABit(1000);
		return driver.getWindowHandles().size() == 2;
	}

	public void addAppointmentWithoutDescription(String startDate, String toDate) {
		switchToServiceRequestInfoFrame();

		clickAddAppointmentButtonFromSREdit();
		wait.until(ExpectedConditions.visibilityOf(appointmentFromDateSRedit));
		wait.until(ExpectedConditions.visibilityOf(appointmentToDateSRedit));
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTimeSRedit));
		wait.until(ExpectedConditions.visibilityOf(appointmentToTimeSRedit));

		appointmentFromDateSRedit.sendKeys(startDate);
		appointmentToDateSRedit.sendKeys(toDate);
		appointmentFromTimeSRedit.sendKeys("6:00 AM");
		appointmentToTimeSRedit.sendKeys("7:00 AM");
		timePopupLink.click();

		waitABit(1000);
		driver.findElement(By.id("Card_btnAddApp")).click();
	}

	public boolean checkLifeCycleDate() {
		String parentFrame = driver.getWindowHandle();
		switchToServiceRequestInfoFrame();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("Card_srLifeCycle"))).click();
		waitABit(3000);
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set windows = driver.getWindowHandles();
		windows.remove(parentFrame);
		driver.switchTo().window((String) windows.iterator().next());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		try {
			wait.until(ExpectedConditions.visibilityOf(lifeCycleBlock));
			return lifeCycleBlock.getText().contains(LocalDate.now(ZoneId.of("US/Pacific")).format(formatter));
		} catch (TimeoutException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void goToLifeCycle() {
		String parentFrame = driver.getWindowHandle();
		switchToServiceRequestInfoFrame();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("Card_srLifeCycle")))).click();
		Set windows = driver.getWindowHandles();
		driver.close();
		windows.remove(parentFrame);
		driver.switchTo().window((String) windows.iterator().next());
	}

	public boolean isLifeCycleContentDisplayed() {
		waitABit(5000);
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
		waitForLoading();
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

	public boolean checkDocumentDownloadingInLC() {
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

	public boolean goToWOfromLifeCycle() {
		waitForLoading();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Link to')]"))).click();
			waitForSRLoading();
			waitForLoading();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath("//table[contains(@id, 'Content_Main_report_fixedTable')]//div[contains(text(), 'Service Request')]")));
//			wait.until(ExpectedConditions
//					.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Tag/Lic. Plate #:')]")));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void waitForSRLoading() {
		try {
			wait.until(ExpectedConditions.attributeContains(loading, "style", "display: block;"));
			wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(loading)));
			wait.until(ExpectedConditions.attributeToBe(loading, "", ""));
			waitABit(1000);
			wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(loading)));
			wait.until(ExpectedConditions.attributeContains(loading, "style", "display: none;"));
		} catch (Exception e) {
			waitABit(5000);
		}
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

	public void setServiceRequestGeneralInfo(String _assignedto) {
		setServiceRequestGeneralInfoAssignedTo(_assignedto);
//		driver.findElement(By.id("Card_ddlClientsAssignedTo_Arrow")).click();
//		waitABit(7000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("divGeneralButtonsDone"))).click();
	}

	public void addAppointmentWithTechnician(String startDate, String endDate, String string) {
		waitABit(3000);
		switchToServiceRequestInfoFrame();

		clickAddAppointmentButtonFromSREdit();
		wait.until(ExpectedConditions.visibilityOf(appointmentFromDateSRedit));
		wait.until(ExpectedConditions.visibilityOf(appointmentToDateSRedit));
		wait.until(ExpectedConditions.visibilityOf(appointmentFromTimeSRedit));
		wait.until(ExpectedConditions.visibilityOf(appointmentToTimeSRedit));

		appointmentFromDateSRedit.clear();
		appointmentToDateSRedit.clear();
		appointmentFromTimeSRedit.clear();
		appointmentToTimeSRedit.clear();

		appointmentFromDateSRedit.sendKeys(startDate);
		appointmentToDateSRedit.sendKeys(endDate);
		appointmentFromTimeSRedit.sendKeys("11:00 AM");
		appointmentToTimeSRedit.sendKeys("11:30 AM");
		timePopupLink.click();
		wait.until(ExpectedConditions.attributeToBe(endTimeDialog, "visibility", "hidden"));
		wait.until(ExpectedConditions.elementToBeClickable(techniciansField)).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem"))
				.stream()
				.filter(e -> e.getText().equals(string))
				.findFirst()
				.ifPresent(WebElement::click);

		waitABit(1000);
		driver.findElement(By.id("Card_btnAddApp")).click();
	}

	public void switchToServiceRequestInfoFrame() {
		driver.switchTo().defaultContent();
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(editServiceRequestPanelFrame));
		} catch (TimeoutException | StaleElementReferenceException e) {
			waitABit(2000);
		}
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

	public boolean checkForAlert() {
		try {
			driver.switchTo().alert().accept();
			waitForLoading();
			return true;
		} catch (Exception e) {
			waitForLoading();
			return false;
		}
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
		} catch (NoAlertPresentException ignored) {
		}
	}

	public boolean checkPresenceOfServiceAdvisersByFilter(String filter) {
		wait.until(ExpectedConditions.visibilityOf(addsrvcustomercmb.getWrappedElement())).clear();
		addsrvcustomercmb.sendKeys(filter);
//        int index = RandomUtils.nextInt(0, 5);
		int index = RandomUtils.nextInt(0, clients.size());
		try {
			wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
					By.xpath("//div[@id='Card_ddlClients_DropDown']//li"), 1));
		} catch (TimeoutException e) {
			Assert.fail("The drop down list has not been displayed.", e);
		}
		try {
			boolean result = clients.stream()
					.map(WebElement::getText).map(String::toLowerCase).allMatch(t -> t.contains(filter));
			clients.get(index).click();
			wait.until(ExpectedConditions.invisibilityOf(addsrvcustomerdd.getWrappedElement()));
			return result;
		} catch (TimeoutException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getFirstServiceAdviserName() {
		getCustomerEditButton().click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(addsrvcustomercmb.getWrappedElement())).click();
		} catch (TimeoutException e) {
			Assert.fail("The combobox with customers list is not clickable.", e);
		}
		waitForLoading();
		wait.until(ExpectedConditions.visibilityOfAllElements(clients));
		return clients
				.stream()
				.map(WebElement::getText)
				.findFirst()
				.get();
	}

	public boolean checkAddedServices(String... services) {
		for (int i = 0; i < services.length; i++) {
			try {

			} catch (TimeoutException e) {
				return false;
			}
		}
		return true;
	}

	public void selectSearchTimeFrame(WebConstants.TimeFrameValues timeframe) {
		selectComboboxValue(searchtimeframecmb, searchtimeframedd, timeframe.getName());
	}

	public void setSearchFromDate(String date) {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_calDateFrom_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_calDateFrom_dateInput")).sendKeys(date);
	}

	public void setSearchToDate(String date) {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_calDateTo_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_calDateTo_dateInput")).sendKeys(date);
	}

	public String getServiceRequestInspectionNumber() {
		return serviceRequestInspection.getText();
	}

	public String getServiceRequestInvoiceNumber() {
		return serviceRequestInvoice.getText();
	}

	public String getServiceRequestWorkOrderNumber() {
		return serviceRequestWorkOrder.getText();
	}

	public String getServiceRequestAppointmentStatus() {
		return serviceRequestAppointmentFrame.findElement(By.xpath(".//span[@class='appointmentReason']")).getText().trim();
	}
}