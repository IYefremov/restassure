package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.MailChecker;
import com.cyberiansoft.test.dataclasses.bo.BOoperationsSRdata;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.email.EmailUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

//@Listeners(VideoListener.class)
public class BackOfficeOperationsServiceRequestsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOoperationsSRdata.json";
    private EmailUtils emailUtils;
    private MailChecker mailChecker;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
        mailChecker = new MailChecker();
    }
//        try {
//            emailUtils = new EmailUtils(EmailHost.GMAIL, BOConfigInfo.getInstance().getUserName(),
//                    BOConfigInfo.getInstance().getUserPassword(), EmailFolder.INBOX);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentWholesale(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

        ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
        servicerequestslistpage.makeSearchPanelVisible();
        servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
        servicerequestslistpage.clickAddServiceRequestButton();
        servicerequestslistpage.clickGeneralInfoEditButton();

        servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
        servicerequestslistpage.clickDoneButton();

        servicerequestslistpage.clickCustomerEditButton();
        servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
        servicerequestslistpage.clickDoneButton();

        servicerequestslistpage.clickVehicleInforEditButton();
        servicerequestslistpage.setServiceRequestVIN(data.getVIN());
        servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
        servicerequestslistpage.clickDoneButton();

        servicerequestslistpage.clickClaimInfoEditButton();
        servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
        servicerequestslistpage.clickDoneButton();

        servicerequestslistpage.setServiceRequestLabel(data.getLabel());
        servicerequestslistpage.setServiceRequestDescription(data.getLabel());
        servicerequestslistpage.saveNewServiceRequest();
        servicerequestslistpage.makeSearchPanelVisible();
        servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
        servicerequestslistpage.clickFindButton();
        servicerequestslistpage.acceptFirstServiceRequestFromList();
        SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
                .clickAddAppointmentToFirstServiceRequestFromList();
        appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
        appointmentpopup.setStartTimeValue(data.getStartTime());
        appointmentpopup.setEndTimeValue(data.getEndTime());
        Assert.assertEquals(appointmentpopup.getSubjectValue(), data.getClientName());
        Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getClientName());
        Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), data.getTechnicianFieldValue());
        String appointmentfromdate = appointmentpopup.getFromDateValue();
        String appointmentstarttime = appointmentpopup.getStartTimeValue();
        appointmentpopup.clickAddAppointment();
        servicerequestslistpage
                .appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentRetail(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getSubjectValue(), data.getNewServiceRequest());
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
		Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), data.getTechnicianFieldValue());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage
				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentLocationTypeCustom(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
		servicerequestslistpage.clickFindButton();

		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();

		appointmentpopup.setFromDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.selectLocationType(data.getLocationType());
		appointmentpopup.setClientAddressValue(data.getClientAddress());
		appointmentpopup.setClientCityValue(data.getClientCity());
		appointmentpopup.setClientZipValue(data.getClientZip());
		appointmentpopup.clickAddAppointment();

		servicerequestslistpage
				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		Assert.assertEquals(appointmentpopup.getClientAddressValue(), data.getClientAddress());
		Assert.assertEquals(appointmentpopup.getClientCityValue(), data.getClientCity());
		Assert.assertTrue(appointmentpopup.getClientZipValue().equals(data.getClientZip())
				|| appointmentpopup.getClientZipValue().equals(data.getClientZip2()));
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentLocationTypeCustomer(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
		servicerequestslistpage.clickFindButton();
//todo  org.openqa.selenium.interactions.Actions moveToElement
//INFO: When using the W3C Action commands, offsets are from the center of element
		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.selectLocationType(data.getLocationType());
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage
				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentLocationTypeOwner(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
		servicerequestslistpage.selectServiceRequestOwner(data.getNewServiceRequest());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
		servicerequestslistpage.clickFindButton();

		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
		appointmentpopup.selectLocationType(data.getLocationType());
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage
				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentLocationTypeRepairLocation(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
		// servicerequestslistpage.clickFindButton();

		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.selectLocationType(data.getLocationType());
		appointmentpopup.selectLocation(data.getLocation());
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage
				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationsCLUserItNotPossibleToAcceptSR_OptionIsNotPresent(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		backOfficeHeader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
        loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
		HomeWebPage homepage = backOfficeHeader.clickHomeLink();
		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getVIN());
		servicerequestslistpage.clickFindButton();
		Assert.assertFalse(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.rejectFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsCLUserVerifyThatAcceptedSRIsInReadOnlyMode_NotPossibleToEdit(String rowID, String description, JSONObject testData) throws InterruptedException {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        backOfficeHeader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
		backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		HomeWebPage homepage = backOfficeHeader.clickHomeLink();
		Thread.sleep(1000);
		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();

		backOfficeHeader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
		backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getVIN());
		servicerequestslistpage.clickFindButton();
		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());

		backOfficeHeader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);

        loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
        backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		homepage = backOfficeHeader.clickHomeLink();
		Thread.sleep(1000);
		servicerequestslistpage = homepage.clickNewServiceRequestLink();
		Thread.sleep(2000);
		servicerequestslistpage.makeSearchPanelVisible();

		servicerequestslistpage.setSearchFreeText(data.getVIN());
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		servicerequestslistpage.switchToServiceRequestInfoFrame();
        Assert.assertFalse(servicerequestslistpage.getGeneralInfoEditButton().isDisplayed());
		Assert.assertFalse(servicerequestslistpage.getCustomerEditButton().isDisplayed());
		Assert.assertFalse(servicerequestslistpage.getVehicleInfoEditButton().isDisplayed());
		servicerequestslistpage.clickCloseServiceRequestButton();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsCLUserItNotPossibleToAddLabelsWhenCreateSR(String rowID, String description, JSONObject testData) throws InterruptedException {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        backOfficeHeader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
        loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
		backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		HomeWebPage homepage = backOfficeHeader.clickHomeLink();
		Thread.sleep(1000);
		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		Assert.assertFalse(servicerequestslistpage.getServiceRequestLabelField().isDisplayed());

	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsSRListVerifyThatCheckInButtonIsNotPresentWhenCreateSR(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getCustomer());
		servicerequestslistpage.clickDoneButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getVIN());
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertFalse(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
		servicerequestslistpage.rejectFirstServiceRequestFromList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationsVerifyThatCheckInButtonAppearsWhenSRIsSaved(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getCustomer());
		servicerequestslistpage.clickDoneButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getVIN());
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertFalse(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonVisible());
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationsSRListVerifyThatCheckInButtonIsChangedToUndoCheckInAfterPressingAndViceVersa(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(data.getCustomer());
		servicerequestslistpage.clickDoneButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getVIN());
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertFalse(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonVisible());
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), data.getCheckInButton());
		servicerequestslistpage.clickCheckInButtonForSelectedSR();
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), data.getUndoCheckInButton());
		servicerequestslistpage.clickCheckInButtonForSelectedSR();
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), data.getCheckInButton());
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestDescription(String rowID, String description, JSONObject testData) {
        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(description);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkTimeOfLastDescription());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequest(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
        serviceRequestsWebPage.clickAddServiceRequestButton();
        serviceRequestsWebPage.addTags(data.getTags());
        Assert.assertTrue(serviceRequestsWebPage.addTags(data.getTags()[data.getTags().length - 1]));
        serviceRequestsWebPage.addTags(data.getSymbol());
        Assert.assertTrue(serviceRequestsWebPage.removeFirtsTag());
        serviceRequestsWebPage.saveNewServiceRequest();
        serviceRequestsWebPage.selectFirstServiceRequestFromList();
        Assert.assertTrue(serviceRequestsWebPage.checkTags(data.getTags()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestDescriptionInExistingSR(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[0]);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.addNewDescriptionAndCheckOld(data.getDescriptions()[1],
                data.getDescriptions()[0]));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testShownSRDuringCreation(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		Assert.assertFalse(serviceRequestsWebPage.checkIfDescriptionIconsVisible());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreatingSRWithDifferentDescriptions(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[0]);
		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[1]);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkServiceDescription(data.getDescriptions()[1]));
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkDescriptionDocument(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkServiceRequestDocumentIcon());
		serviceRequestsWebPage.clickDocumentButton();
		Assert.assertTrue(serviceRequestsWebPage.checkElementsInDocument());
		Assert.assertTrue(serviceRequestsWebPage.clickAddImageBTN());
		//serviceRequestsWebPage.addImage();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkMultiTechInSR(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(data.getFirstDay(), data.getSecondDay()));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(
				serviceRequestsWebPage.checkDefaultAppointmentValuesAndAddAppointmentFomSREdit());
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkMultiTechInSRshowHideTech(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(data.getFirstDay(), data.getSecondDay()));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkShowHideTeches(data.getFirstDay(), data.getSecondDay()));
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMultiTechInSideScrollbar(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentValuesFromCalendar(
				        data.getFirstDay(), data.getSecondDay(), data.getCustomer()));
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkSRappointmentSchedulerWeek(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.checkSchedulerByDateWeek(data.getFirstDay(), data.isDateShifted());
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkSRappointmentSchedulerMonth(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationsPage.clickNewServiceRequestList();
		int prevRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(data.getFirstDay());
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.reloadPage();
		int afterRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(data.getFirstDay());
        Assert.assertEquals(afterRequestsCount - prevRequestsCount, 1);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRappointmentSchedulerMultiTechniciansFilterOf5(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToMonthInScheduler();
		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
	}

    //todo edge
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkSchedulerTechniciansFilter(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.goToMonthInScheduler();
		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
		serviceRequestsWebPage.applyTechniciansFromScheduler();
		int countBeforeAnySelections = serviceRequestsWebPage.countSR();
		serviceRequestsWebPage.selectTechnicianFromSchedulerByIndex(0);
		serviceRequestsWebPage.applyTechniciansFromScheduler();
	}

    //todo edge
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkSRmultiTechReset(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.goToMonthInScheduler();
		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
		serviceRequestsWebPage.resetAndCheckTecniciansFromScheduler();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkSRcreation(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkSRLCnoEntry(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		Assert.assertFalse(serviceRequestsWebPage.checkLifeCycleBTN());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkSRLCestimate(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentWithoutDescription(data.getFirstDay(), data.getSecondDay());
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
		Assert.assertTrue(serviceRequestsWebPage.checkLifeCycleDate());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCafterCreation(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.isLifeCycleContentDisplayed());
		serviceRequestsWebPage.goToDocumentLinkFromLC();
		Assert.assertTrue(serviceRequestsWebPage.checkLifeCycleDocumentsContent());
		Assert.assertTrue(serviceRequestsWebPage.checkDocumentDownloadingInLC());
		Assert.assertTrue(serviceRequestsWebPage.clickAddImageBTN());
	//	serviceRequestsWebPage.addImage();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLifeCycleWOAutoCreation(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getVehicleStock(), data.getVehicleVIN());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.goToWOfromLifeCycle());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCapproved(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getVehicleStock(), data.getVehicleVIN());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getNewStatus()));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.checkAcceptanceOfSRinLC());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
        public void checkSRLCrejected(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getVehicleStock(), data.getVehicleVIN());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getNewStatus()));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.checkRejectOfSRinLC());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCclosed(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.makeSearchPanelVisible();
		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
		// serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		// serviceRequestsWebPage.clickAddServiceRequestButton();
		// serviceRequestsWebPage.clickCustomerEditButton();
		// serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		// serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.clickVehicleEditButton();
		// serviceRequestsWebPage.setVehicleInfo(data.getStock123() , data.getStock123());
		// serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.saveNewServiceRequest();
		// serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		// Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
		// serviceRequestsWebPage.selectFirstServiceRequestFromList();
		// serviceRequestsWebPage.goToLifeCycle();
		// Assert.assertTrue(serviceRequestsWebPage.checkClosedOfSRinLC());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestAccepted(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getEmailNotification());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsSRCreated(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getEmailNotification());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//todo fails
//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void  testMiscellaneousEventsServiceRequestCheckedIn(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getNotificationDropDown());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
        serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickCheckInButtonForSelectedSR();
		serviceRequestsWebPage.switchToServiceRequestInfoFrame();
		serviceRequestsWebPage.saveNewServiceRequest();
//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWord()));

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsAppointmentCreated(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getAlert());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getAlert());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentFromSRlist(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
//        Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()) || mailChecker.checkTestEmails());

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getAlert());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsAppointmentFailed(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getAlert());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getAlert());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentFromSRlist(data.getFirstDay(), data.getSecondDay(), data.getTechnician());

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getAlert());
		// eventsWebPage.deleteSelectedEvent();
	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestAppointmentCreated(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(
//				mailChecker.checkTestEmails() || mailChecker.checkEmails(data.getEmailKeyWord()));
//todo fails
//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestAcceptedByTech(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(mailChecker.checkTestEmails()
//				|| mailChecker.checkEmails(data.getEmailKeyWord()));

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestEstimationCreated(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()));

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestIsMonitored(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordRemainder())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordRemainder()) || mailChecker.checkTestEmails());

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordRemainder());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestOrderCreated(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()));

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestRejected(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()) || mailChecker.checkTestEmails());

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestCheckIn(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        MiscellaneousWebPage miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
        eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getNotificationDropDown());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickCheckInButtonForSelectedSR();
		serviceRequestsWebPage.switchToServiceRequestInfoFrame();
		serviceRequestsWebPage.saveNewServiceRequest();
//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWord()));

        
//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

        miscellaneouspage = backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestTypeDuplicateSearchIssue(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
        ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage
                .clickServiceEditButton()
                .openServicesDropDown();
        Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickErrorWithBLockingRadioButton();
		serviceRequestTypesPage.selectStockRoVinOptions();
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		serviceRequestTypesPage.getTableRowWithServiceRequestType(data.getServiceRequestType());
		String currentWindow = serviceRequestTypesPage.getCurrentWindow();
		ServiceRequestTypesVehicleInfoSettingsPage settingsPage = serviceRequestTypesPage
				.clickSettingsVehicleInfo(data.getServiceRequestType());
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
		settingsPage.unselectCheckBox(data.getVIN());
		settingsPage.unselectCheckBox(data.getStockNum());
		settingsPage.unselectCheckBox(data.getRoNum());
		settingsPage.clickUpdateButton();
		settingsPage.closeNewTab(currentWindow);
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
		operationspage = backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		companyPage = backOfficeHeader.clickCompanyLink();
		serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickNoneRadioButton();
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		serviceRequestTypesPage.getTableRowWithServiceRequestType(data.getServiceRequestType());
		currentWindow = serviceRequestTypesPage.getCurrentWindow();
		settingsPage = serviceRequestTypesPage.clickSettingsVehicleInfo(data.getServiceRequestType());
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
        settingsPage.selectCheckBox(data.getVIN());
        settingsPage.selectCheckBox(data.getStockNum());
        settingsPage.selectCheckBox(data.getRoNum());
		settingsPage.clickUpdateButton();
		settingsPage.closeNewTab(currentWindow);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestTypeDuplicateNotificationRO(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage
                .clickServiceEditButton()
                .openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		
		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.selectOption(data.getRoNum());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		String randomRO = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestTypeDuplicateErrorVIN(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage
                .clickServiceEditButton()
                .openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.selectOption(data.getVIN());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randomVIN = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestTypeDuplicateErrorRO(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
        serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage
                .clickServiceEditButton()
                .openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.selectOption(data.getRoNum());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		String randomRO = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestTypeDuplicateNotificationVIN(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
        serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage
                .clickServiceEditButton()
                .openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.selectOption(data.getVIN());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randomVIN = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestTypeDuplicateErrorStock(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
        serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage
                .clickServiceEditButton()
                .openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.selectOption(data.getStockNum());
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randonStock = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestTypeDuplicateNotificationStock(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
        serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage
                .clickServiceEditButton()
                .openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.selectOption(data.getStockNum());
		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randonStock = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestUndoReject(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companypage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		Assert.assertTrue(serviceRequestTypesPage.isAllowUndoRejectChecked());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.makeSearchPanelVisible();
		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo(),
                data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setServiceRequestVIN(data.getVIN());
		serviceRequestsWebPage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickClaimInfoEditButton();
		serviceRequestsWebPage.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setServiceRequestLabel(data.getTestDescription());
		serviceRequestsWebPage.setServiceRequestDescription(data.getTestDescription());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.makeSearchPanelVisible();
		serviceRequestsWebPage.setSearchFreeText(data.getClientName());
		serviceRequestsWebPage.clickFindButton();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickRejectUndoButton();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestAdviserListing(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
        serviceRequestsWebPage.clickAddServiceRequestButton();
        serviceRequestsWebPage.clickCustomerEditButton();
        Assert.assertTrue(serviceRequestsWebPage.checkPresenceOfServiceAdvisersByFilter(data.getTestDescription()));
        serviceRequestsWebPage.clickDoneButton();
        serviceRequestsWebPage.clickCustomerEditButton();
        serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
        serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertEquals(serviceRequestsWebPage.getServiceAdviserName(), data.getCustomer());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyVehiclePartCanBeAssignedToServicesInSR(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsListPage = operationsPage
                .clickNewServiceRequestList()
                .selectAddServiceRequestDropDown(data.getServiceRequestType())
                .clickAddServiceRequestButton();
        ServiceRequestListServiceDialog serviceDialog = serviceRequestsListPage
                .clickServiceEditButton()
                .openServicesDropDown()
                .checkRandomServiceOption()
                .clickAddServiceOption();
        Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = serviceDialog.clickVehiclePart();
        int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
        int assignedVehiclePartOptions = vehiclePartDialog.getAssignedVehiclePartOptions();
        vehiclePartDialog
                .selectRandomAvailableVehiclePartOption()
                .clickMoveToTheRight();
        Assert.assertEquals(availableVehiclePartOptions - 1, vehiclePartDialog.getAvailableVehiclePartOptions(),
                "The available vehicle Part Options have not been reduced after moving to the right");
        Assert.assertEquals(assignedVehiclePartOptions + 1, vehiclePartDialog.getAssignedVehiclePartOptions(),
                "The assigned vehicle Part Options have not been increased after moving to the right");
        vehiclePartDialog
                .clickServiceVehiclePartOkButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickDoneServicesButton()
                .saveNewServiceRequest();

        serviceRequestsListPage
                .selectFirstServiceRequestFromList()
                .clickServiceEditButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickCancelServicesButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyVehiclePartCanBeUnassignedFromServicesInSR(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsListPage = operationsPage
                .clickNewServiceRequestList()
                .selectAddServiceRequestDropDown(data.getServiceRequestType())
                .clickAddServiceRequestButton();
        ServiceRequestListServiceDialog serviceDialog = serviceRequestsListPage
                .clickServiceEditButton()
                .openServicesDropDown()
                .checkRandomServiceOption()
                .clickAddServiceOption();
        Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = serviceDialog.clickVehiclePart();
        int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
        int assignedVehiclePartOptions = vehiclePartDialog.getAssignedVehiclePartOptions();
        vehiclePartDialog
                .selectRandomAvailableVehiclePartOption()
                .clickMoveToTheRight()
                .clickServiceVehiclePartOkButton()
                .clickVehiclePart();
        Assert.assertEquals(availableVehiclePartOptions - 1, vehiclePartDialog.getAvailableVehiclePartOptions(),
                "The available vehicle Part Options have not been reduced after moving to the right");
        Assert.assertEquals(assignedVehiclePartOptions + 1, vehiclePartDialog.getAssignedVehiclePartOptions(),
                "The assigned vehicle Part Options have not been increased after moving to the right");
        vehiclePartDialog
                .clickServiceVehiclePartOkButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickVehiclePart();

        vehiclePartDialog
                .selectRandomAssignedVehiclePartOption()
                .clickMoveToTheLeft()
                .clickServiceVehiclePartOkButton()
                .clickVehiclePart();

        Assert.assertEquals(availableVehiclePartOptions, vehiclePartDialog.getAvailableVehiclePartOptions(),
                "The available vehicle Part Options have not been increased after moving to the left");
        Assert.assertEquals(assignedVehiclePartOptions, vehiclePartDialog.getAssignedVehiclePartOptions(),
                "The assigned vehicle Part Options have not been reduced after moving to the left");

        vehiclePartDialog
                .clickServiceVehiclePartCancelButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickDoneServicesButton()
                .saveNewServiceRequest();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAllVehiclePartsCanBeAssignedToServicesInSR(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsListPage = operationsPage
                .clickNewServiceRequestList()
                .selectAddServiceRequestDropDown(data.getServiceRequestType())
                .clickAddServiceRequestButton();
        ServiceRequestListServiceDialog serviceDialog = serviceRequestsListPage
                .clickServiceEditButton()
                .openServicesDropDown()
                .checkRandomServiceOption()
                .clickAddServiceOption();
        Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = serviceDialog.clickVehiclePart();
        int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
        int assignedVehiclePartOptions = vehiclePartDialog.getAssignedVehiclePartOptions();
        vehiclePartDialog.clickMoveAllToTheRight();
        Assert.assertEquals(vehiclePartDialog.getAvailableVehiclePartOptions(), assignedVehiclePartOptions,
                "The available vehicle Part Options have not been reduced by the number of assigned options " +
                        "after moving all options to the right");
        Assert.assertEquals(vehiclePartDialog.getAssignedVehiclePartOptions(), availableVehiclePartOptions,
                "The assigned vehicle Part Options have not been increased by the number of available options" +
                        "after moving all options to the right");
        vehiclePartDialog.clickServiceVehiclePartOkButton();
        Assert.assertEquals(serviceDialog.getNumberOfSelectedServiceContainersDisplayed(), availableVehiclePartOptions,
                "The number of service displayed containers differs from the number of assigned options");
        serviceDialog
                .clickDoneServicesButton()
                .saveNewServiceRequest();

        serviceRequestsListPage
                .selectFirstServiceRequestFromList()
                .clickServiceEditButton();

        Assert.assertEquals(serviceDialog.getNumberOfSelectedServiceContainersDisplayed(), availableVehiclePartOptions,
                "The number of service displayed containers differs from the number of assigned options");
        serviceDialog.clickCancelServicesButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAllVehiclePartsCanBeUnassignedFromServicesInSR(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsListPage = operationsPage
                .clickNewServiceRequestList()
                .selectAddServiceRequestDropDown(data.getServiceRequestType())
                .clickAddServiceRequestButton();
        ServiceRequestListServiceDialog serviceDialog = serviceRequestsListPage
                .clickServiceEditButton()
                .openServicesDropDown()
                .checkRandomServiceOption()
                .clickAddServiceOption();
        Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = serviceDialog.clickVehiclePart();
        int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
        vehiclePartDialog.clickMoveAllToTheRight();
        Assert.assertEquals(vehiclePartDialog.getAvailableVehiclePartOptions(), 0,
                "The available vehicle Part Options have not been reduced by the number of assigned options " +
                        "after moving all options to the right");
        Assert.assertEquals(vehiclePartDialog.getAssignedVehiclePartOptions(), availableVehiclePartOptions,
                "The assigned vehicle Part Options have not been increased by the number of available options" +
                        "after moving all options to the right");

        vehiclePartDialog.clickMoveAllToTheLeft();
        Assert.assertEquals(vehiclePartDialog.getAvailableVehiclePartOptions(), availableVehiclePartOptions,
                "The available vehicle Part Options have not been increased by the number of assigned options " +
                        "after moving all options to the left");
        Assert.assertEquals(vehiclePartDialog.getAssignedVehiclePartOptions(), 0,
                "The assigned vehicle Part Options have not been reduced by the number of available options" +
                        "after moving all options to the left");

        vehiclePartDialog
                .clickServiceVehiclePartOkButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickDoneServicesButton()
                .saveNewServiceRequest();

        serviceRequestsListPage
                .selectFirstServiceRequestFromList()
                .clickServiceEditButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickCancelServicesButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyVehiclePartWillRemainUnassignedToServicesInSRAfterClickingTheCancelButton(String rowID, String description, JSONObject testData) {

        BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        ServiceRequestsListWebPage serviceRequestsListPage = operationsPage
                .clickNewServiceRequestList()
                .selectAddServiceRequestDropDown(data.getServiceRequestType())
                .clickAddServiceRequestButton();
        ServiceRequestListServiceDialog serviceDialog = serviceRequestsListPage
                .clickServiceEditButton()
                .openServicesDropDown()
                .checkRandomServiceOption()
                .clickAddServiceOption();
        Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = serviceDialog.clickVehiclePart();
        int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
        int assignedVehiclePartOptions = vehiclePartDialog.getAssignedVehiclePartOptions();
        vehiclePartDialog
                .selectRandomAvailableVehiclePartOption()
                .clickMoveToTheRight();
        Assert.assertEquals(availableVehiclePartOptions - 1, vehiclePartDialog.getAvailableVehiclePartOptions(),
                "The available vehicle Part Options have not been reduced after moving to the right");
        Assert.assertEquals(assignedVehiclePartOptions + 1, vehiclePartDialog.getAssignedVehiclePartOptions(),
                "The assigned vehicle Part Options have not been increased after moving to the right");

        vehiclePartDialog
                .clickServiceVehiclePartCancelButton()
                .clickVehiclePart();
        Assert.assertEquals(availableVehiclePartOptions, vehiclePartDialog.getAvailableVehiclePartOptions(),
                "The available vehicle Part Options have been changed after clicking the 'Cancel' button");
        Assert.assertEquals(assignedVehiclePartOptions, vehiclePartDialog.getAssignedVehiclePartOptions(),
                "The assigned vehicle Part Options have been changed after clicking the 'Cancel' button");
        vehiclePartDialog
                .clickServiceVehiclePartOkButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickDoneServicesButton()
                .saveNewServiceRequest();

        serviceRequestsListPage
                .selectFirstServiceRequestFromList()
                .clickServiceEditButton()
                .verifyOneServiceContainerIsDisplayed()
                .clickCancelServicesButton();
    }

//	//TODO
//	//@Test(testName = "Test Case 65521:Operation - Service Request - Services add notes")
//	public void testServicerequestServicesAddNotes() {
//		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage.clickServiceEditButton();
//		serviceRequestsWebPage.addServicesToServiceRequest("Zak_Money_Multiple","Zak_Labor_Multiple");
//		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertTrue(serviceRequestsWebPage.checkAddedServices("Zak_Money_Multiple","Zak_Labor_Multiple"));
//	}
}