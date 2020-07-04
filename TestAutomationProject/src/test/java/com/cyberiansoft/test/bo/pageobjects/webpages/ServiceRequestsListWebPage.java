package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class ServiceRequestsListWebPage extends BaseWebPage {

    @FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
    private WebElement searchTab;

    @FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']//div[@class='content']")
    private WebElement searchTabExpanded;

    @FindBy(className = "toggler")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@id='itemContainer']")
    private WebElement serviceRequestsList;

    @FindBy(xpath = "//div[@id='itemContainer']/div[@class='item']")
    private List<WebElement> serviceRequestsListElements;

    @FindBy(id = "lbAddServiceRequest")
    private WebElement addServiceRequestButton;

    @FindBy(xpath = "//a[@title='Accept']")
    private WebElement serviceRequestsAcceptButton;

    @FindBy(xpath = "//a[@title='Reject']")
    private WebElement serviceRequestsRejectButton;

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
    private WebElement saveServiceRequestButton;

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
    private TextField addSRDescription;

    @FindBy(id = "btnCloseServiceRequestTop")
    private WebElement closeServiceRequestButton;

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

    @FindBy(xpath = "//div[@class='infoBlock description']//span[@data-for='Card_hfNotes']")
    private WebElement descriptionTextBlock;

    @FindBy(id = "linkAnswers")
    private WebElement descriptionAnswers;

    @FindBy(className = "content")
    private WebElement documentContent;

    @FindBy(xpath = "//input[contains(@class, 'ruButton ruBrowse')]")
    private WebElement addFileButton;

    @FindBy(xpath = "//div[contains(@class, 'ruButton ruRemove')]")
    private WebElement removeBTN;

    @FindBy(id = "ctl00_ctl00_Content_Main_rcbServicePachages_Input")
    private WebElement addServiceRequestDropDown;

    @FindBy(xpath = "//div[contains(@class, 'infoBlock main')]")
    private List<WebElement> serviceRequestInfoBlocks;

    @FindBy(id = "Card_ddlClients_Input")
    private WebElement customerName;

    @FindBy(id = "doneCustOwner")
    private WebElement acceptCustomerButton;

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
    private WebElement addAppointmentButton;

    @FindBy(id = "ctl00_ctl00_Content_Main_rcbTechnician_Input")
    private TextField addSRAppTechCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_rcbTechnician_DropDown")
    private DropDown addSRAppTechnicianDropDown;

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

    @FindBy(xpath = "//div[@class='editServiceRequestPanel']/div/img[@id='ctl00_ctl00_Content_Main_Image1']")
    private WebElement editServiceRequestPanelImage;

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

    @FindBy(xpath = "//*[contains(@id, 'ddlTimeframe_Input')]")
    private ComboBox searchtimeframecmb;

    @FindBy(xpath = "//*[contains(@id, 'ddlTimeframe_DropDown')]")
    private DropDown searchtimeframedd;

    @FindBy(id = "Card_srLifeCycle")
    private WebElement srLifeCycle;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_calDateFrom_dateInput")
    private WebElement fromDateInputField;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_calDateTo_dateInput")
    private WebElement toDateInputField;

    @FindBy(xpath = "//div[@class='description-content']/span[@class='infoBlock-editBtn']")
    private WebElement infoBlockEditButton;

    @FindBy(xpath = "//div[@class='description-content']//div[@class='infoBlock-doneBtn sr-btn']")
    private WebElement infoBlockDoneButton;

    @FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
    private WebElement imagesOkButton;

    @FindBy(id = "ctl00_Content_ctl01_ctl02_BtnCancel")
    private WebElement imagesCancelButton;

    @FindBy(xpath = "//div[@class='infoBlock-item infoBlock-edit servicesBlock']")
    private WebElement servicesPopup;

    @FindBy(id = "ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink")
    private WebElement tmePopupLink;

    @FindBy(xpath = "//input[@id='Card_btnAddApp']")
    private WebElement addAppButton;

    @FindBy(xpath = "//div[@class='infoBlock-content']/b[text()='Customer:']")
    private WebElement customerBlock;

    @FindBy(xpath = "//span[@class='infoBlock-editBtn edit-client']/i[@class='icon-pencil']")
    private WebElement customerEditIcon;

    @FindBy(xpath = "//div[@id='techItems-content']/div[@class='tech-item']")
    private List<WebElement> techItemsList;

    public ServiceRequestsListWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getFirstSRFromList() {
        return getServiceRequestsListElements().size() > 0 ? getServiceRequestsListElements().get(0) : null;
    }
}