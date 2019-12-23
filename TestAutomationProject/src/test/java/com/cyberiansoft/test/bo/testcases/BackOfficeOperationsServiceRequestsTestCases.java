package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.verifications.ServiceRequestsListVerifications;
import com.cyberiansoft.test.dataclasses.bo.BOoperationsSRdata;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

public class BackOfficeOperationsServiceRequestsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOoperationsSRdata.json";
	private NadaEMailService nada;

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
		nada = new NadaEMailService();
		nada.setEmailId(BOConfigInfo.getInstance().getUserNadaName());
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentWholesale(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);

        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();

		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.setServiceRequestLabel(data.getLabel());
		serviceRequestsListInteractions.setServiceRequestDescription(data.getLabel());
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
		SRAppointmentInfoPopup appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getSubjectValue(), data.getClientName());
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getClientName());
		Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), data.getTechnicianFieldValue());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		appointmentpopup.clickAddAppointment();
        ServiceRequestsListVerifications
				.isAppointmentPresentForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentRetail(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();

		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.setServiceRequestLabel(data.getLabel());
		serviceRequestsListInteractions.setServiceRequestDescription(data.getLabel());
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
		SRAppointmentInfoPopup appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getSubjectValue(), data.getNewServiceRequest());
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
		Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), data.getTechnicianFieldValue());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		appointmentpopup.clickAddAppointment();
        ServiceRequestsListVerifications
				.isAppointmentPresentForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentLocationTypeCustom(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);

        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();

		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.setServiceRequestLabel(data.getLabel());
		serviceRequestsListInteractions.setServiceRequestDescription(data.getLabel());
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();

		Assert.assertTrue(ServiceRequestsListVerifications.isAcceptIconNotDisplayedForFirstServiceRequestFromList());
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
        Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));

		SRAppointmentInfoPopup appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();

		appointmentpopup.setFromDateValue(CustomDateProvider.getDayAfterTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setToDateValue(CustomDateProvider.getDayAfterTomorrowLocalizedDateFormattedShort());
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

		ServiceRequestsListVerifications
				.isAppointmentPresentForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		Assert.assertEquals(appointmentpopup.getClientAddressValue(), data.getClientAddress());
		Assert.assertEquals(appointmentpopup.getClientCityValue(), data.getClientCity());
		Assert.assertTrue(appointmentpopup.getClientZipValue().equals(data.getClientZip())
				|| appointmentpopup.getClientZipValue().equals(data.getClientZip2()));
		appointmentpopup.clickAddAppointment();
		serviceRequestsListInteractions.closeFirstServiceRequestFromTheList();
	}

	//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentLocationTypeCustomer(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();

		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.setServiceRequestLabel(data.getLabel());
		serviceRequestsListInteractions.setServiceRequestDescription(data.getLabel());
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
        Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));

		SRAppointmentInfoPopup appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setToDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.selectLocationType(data.getLocationType());
		appointmentpopup.clickAddAppointment();
        ServiceRequestsListVerifications
				.isAppointmentPresentForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.clickAddAppointment();
		serviceRequestsListInteractions.closeFirstServiceRequestFromTheList();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentLocationTypeOwner(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();

		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.selectServiceRequestOwner(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.setServiceRequestLabel(data.getLabel());
		serviceRequestsListInteractions.setServiceRequestDescription(data.getLabel());
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();

		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
        Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));

		SRAppointmentInfoPopup appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setToDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
		appointmentpopup.selectLocationType(data.getLocationType());
		appointmentpopup.clickAddAppointment();
        ServiceRequestsListVerifications.isAppointmentPresentForFirstServiceRequestFromList(
                appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentLocationTypeRepairLocation(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();

		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.setServiceRequestLabel(data.getLabel());
		serviceRequestsListInteractions.setServiceRequestDescription(data.getLabel());
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();

		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
        Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));

		SRAppointmentInfoPopup appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setToDateValue(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		appointmentpopup.setStartTimeValue(data.getStartTime());
		appointmentpopup.setEndTimeValue(data.getEndTime());
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.selectLocationType(data.getLocationType());
		appointmentpopup.selectLocation(data.getLocation());
		appointmentpopup.clickAddAppointment();
        ServiceRequestsListVerifications
				.isAppointmentPresentForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = new SRAppointmentInfoPopup(webdriver);
		ServiceRequestsListInteractions.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.clickAddAppointment();
		serviceRequestsListInteractions.closeFirstServiceRequestFromTheList();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsCLUserItNotPossibleToAcceptSR_OptionIsNotPresent(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickLogout();
		BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(webdriver);
		loginpage.userLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
		HomeWebPage homepage = new HomeWebPage(webdriver);
		backOfficeHeader.clickHomeLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = homepage.clickNewServiceRequestLink();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		Assert.assertTrue(ServiceRequestsListVerifications.isAcceptIconNotDisplayedForFirstServiceRequestFromList());
		serviceRequestsListInteractions.rejectFirstServiceRequestFromList();
        Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsCLUserVerifyThatAcceptedSRIsInReadOnlyMode_NotPossibleToEdit(String rowID, String description, JSONObject testData) throws InterruptedException {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickLogout();
		BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(webdriver);
		loginpage.userLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
		HomeWebPage homepage = new HomeWebPage(webdriver);
		backOfficeHeader.clickHomeLink();
		Thread.sleep(1000);
		ServiceRequestsListInteractions serviceRequestsListInteractions = homepage.clickNewServiceRequestLink();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();

		backOfficeHeader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.userLogin(BOConfigInfo.getInstance().getUserNadaName(), BOConfigInfo.getInstance().getUserNadaPassword());
		backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
        Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));

		backOfficeHeader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);

		loginpage.userLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
		backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		homepage = new HomeWebPage(webdriver);
		backOfficeHeader.clickHomeLink();
		Thread.sleep(1000);
		serviceRequestsListInteractions = homepage.clickNewServiceRequestLink();
		Thread.sleep(2000);
		serviceRequestsListInteractions.makeSearchPanelVisible();

		serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
		Assert.assertFalse(serviceRequestsListInteractions.getGeneralInfoEditButton().isDisplayed());
		Assert.assertFalse(serviceRequestsListInteractions.getCustomerEditButton().isDisplayed());
		Assert.assertTrue(serviceRequestsListInteractions.isVehicleInfoEditButtonNotDisplayed());
		serviceRequestsListInteractions.clickCloseServiceRequestButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsCLUserItNotPossibleToAddLabelsWhenCreateSR(String rowID, String description, JSONObject testData) throws InterruptedException {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickLogout();
		BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(webdriver);
		loginpage.userLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
		HomeWebPage homepage = new HomeWebPage(webdriver);
		backOfficeHeader.clickHomeLink();
		Thread.sleep(1000);
		ServiceRequestsListInteractions serviceRequestsListInteractions = homepage.clickNewServiceRequestLink();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		Assert.assertFalse(serviceRequestsListInteractions.getServiceRequestLabelField().isDisplayed());

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsSRListVerifyThatCheckInButtonIsNotPresentWhenCreateSR(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isCheckInButtonInvisible());
		serviceRequestsListInteractions.rejectFirstServiceRequestFromList();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsVerifyThatCheckInButtonAppearsWhenSRIsSaved(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
        serviceRequestsListInteractions.makeSearchPanelVisible();
        serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isCheckInButtonInvisible());
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isCheckInButtonVisible());
		serviceRequestsListInteractions.closeFirstServiceRequestFromTheList();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationsSRListVerifyThatCheckInButtonIsChangedToUndoCheckInAfterPressingAndViceVersa(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        backOfficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isCheckInButtonInvisible());
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
//		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isCheckInButtonVisible());
		Assert.assertEquals(serviceRequestsListInteractions.getCheckInButtonValueForSelectedSR(), data.getCheckInButton());
		serviceRequestsListInteractions.clickCheckInButtonForSelectedSR();
		Assert.assertEquals(serviceRequestsListInteractions.getCheckInButtonValueForSelectedSR(), data.getUndoCheckInButton());
		serviceRequestsListInteractions.clickCheckInButtonForSelectedSR();
		Assert.assertEquals(serviceRequestsListInteractions.getCheckInButtonValueForSelectedSR(), data.getCheckInButton());
		serviceRequestsListInteractions.closeFirstServiceRequestFromTheList();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestDescription(String rowID, String description, JSONObject testData) {
		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.setServiceRequestDescription(description);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.checkTimeOfLastDescription());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequest(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestsListVerifications.verifyTagsAreAdded(data.getTags());
		Assert.assertTrue(ServiceRequestsListVerifications.verifyTagsAreAdded(data.getTags()[data.getTags().length - 1]));
		ServiceRequestsListVerifications.verifyTagsAreAdded(data.getSymbol());
		Assert.assertTrue(ServiceRequestsListVerifications.isFirstTagRemoved());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.areTagsAdded(data.getTags()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestDescriptionInExistingSR(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[0]);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isNewDescriptionAddedAndCheckedOld(data.getDescriptions()[1],
				data.getDescriptions()[0]));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testShownSRDuringCreation(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		Assert.assertFalse(ServiceRequestsListVerifications.verifyDescriptionIconsAreVisible());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreatingSRWithDifferentDescriptions(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[0]);
		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[1]);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.verifyServiceDescriptionIsPresent(data.getDescriptions()[1]));
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkDescriptionDocument(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isServiceRequestDocumentIconVisible());
		serviceRequestsWebPage.clickDocumentButton();
		Assert.assertTrue(ServiceRequestsListVerifications.checkElementsInDocument());
		serviceRequestsWebPage.clickAddImageButton();
		Assert.assertTrue(ServiceRequestsListVerifications.areImageButtonsDisplayed());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMultiTechInSR(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
        serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setAppointmentValues(data.getFirstDay(), data.getSecondDay());
		serviceRequestsWebPage.clickAddAppointmentButtonFromSRList();
        Assert.assertTrue(ServiceRequestsListVerifications.isAddAppointmentFromSRListClosed(),
                "The Add Appointment dialog hasn't been closed");
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		new ServiceRequestsListInteractions().openAddAppointmentForFirstSR();
		Assert.assertTrue(
				ServiceRequestsListVerifications.checkDefaultAppointmentValuesAndAddAppointmentFomSREdit());
		Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMultiTechInSRShowHideTech(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setAppointmentValues(data.getFirstDay(), data.getSecondDay());
		serviceRequestsWebPage.clickAddAppointmentButtonFromSRList();
		Assert.assertTrue(ServiceRequestsListVerifications.isAddAppointmentFromSRListClosed(),
                "The Add Appointment dialog hasn't been closed");
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.openAddAppointmentForFirstSR();
		serviceRequestsWebPage.setAppointmentSREditValues(data.getFirstDay(), data.getSecondDay());
        Assert.assertTrue(ServiceRequestsListVerifications.checkShowHideTechs());
		Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMultiTechInSideScrollbar(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));
		serviceRequestsListInteractions.setAppointmentValues(data.getFirstDay(), data.getSecondDay());
		Assert.assertTrue(ServiceRequestsListVerifications.checkDefaultAppointmentValuesFromCalendar(data.getCustomer()));
		Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getNewStatus()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRappointmentSchedulerWeek(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.checkSchedulerByDateWeek(data.getFirstDay(), data.isDateShifted());
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
        serviceRequestsWebPage.clickAddAppointmentButtonFromSREdit();
        Assert.assertTrue(ServiceRequestsListVerifications.checkDefaultAppointmentDateFromSREdit(data.getFirstDay()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRappointmentSchedulerMonth(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
        int prevRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(data.getFirstDay());
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDayTime());
        serviceRequestsWebPage.clickAddAppointmentButtonFromSREdit();
        Assert.assertTrue(ServiceRequestsListVerifications.checkDefaultAppointmentDateFromSREdit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.reloadPage();
		int afterRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(data.getFirstDay());
		Assert.assertEquals(afterRequestsCount - prevRequestsCount, 1);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRAppointmentSchedulerMultiTechniciansFilterOf5(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
        serviceRequestsWebPage.goToMonthInScheduler();
        serviceRequestsWebPage.clickTechniciansSchedulerDropDown();
        Assert.assertTrue(ServiceRequestsListVerifications.checkTechniciansFromScheduler());
		Assert.assertEquals(5, serviceRequestsWebPage.getMaximumTechniciansListSize(),
                "The maximum technicians list size is not 5");
		serviceRequestsWebPage.clickArrowTechniciansLink();
		Assert.assertTrue(ServiceRequestsListVerifications.check5TechniciansFromScheduler());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSchedulerTechniciansFilter(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDayTime());
		serviceRequestsWebPage.clickAddAppointmentButtonFromSREdit();
		Assert.assertTrue(ServiceRequestsListVerifications.checkDefaultAppointmentDateFromSREdit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.goToMonthInScheduler();
		serviceRequestsWebPage.clickTechniciansSchedulerDropDown();
		Assert.assertTrue(ServiceRequestsListVerifications.checkTechniciansFromScheduler());
		serviceRequestsWebPage.applyTechniciansFromScheduler();
		serviceRequestsWebPage.countSR();
        System.out.println(serviceRequestsWebPage.countSR());
//        serviceRequestsWebPage.selectTechnicianFromSchedulerByIndex(0);
		serviceRequestsWebPage.applyTechniciansFromScheduler();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRMultiTechReset(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDayTime());
        serviceRequestsWebPage.clickAddAppointmentButtonFromSREdit();
        Assert.assertTrue(ServiceRequestsListVerifications.checkDefaultAppointmentDateFromSREdit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.goToMonthInScheduler();
        serviceRequestsWebPage.clickTechniciansSchedulerDropDown();
        Assert.assertTrue(ServiceRequestsListVerifications.checkTechniciansFromScheduler());
		Assert.assertEquals(5, serviceRequestsWebPage.getMaximumTechniciansListSize(),
                "The maximum technicians list size is not 5");
		serviceRequestsWebPage.clickArrowTechniciansLink();
		Assert.assertTrue(ServiceRequestsListVerifications.check5TechniciansFromScheduler());
		serviceRequestsWebPage.resetTechniciansFromScheduler();
		Assert.assertTrue(ServiceRequestsListVerifications.checkTechniciansFromSchedulerAfterResetting());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRCreation(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
        serviceRequestsWebPage.clickAddAppointmentButtonFromSREdit();
        Assert.assertTrue(ServiceRequestsListVerifications.checkDefaultAppointmentDateFromSREdit(data.getFirstDay()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCnoEntry(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.clickAddSRButton();
		serviceRequestsWebPage.clickSRLifeCycleButton();
		Assert.assertFalse(ServiceRequestsListVerifications.areTwoWindowsOpened());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCEstimate(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDayTime());
        serviceRequestsWebPage.clickAddAppointmentButtonFromSREdit();
        Assert.assertTrue(ServiceRequestsListVerifications.checkDefaultAppointmentDateFromSREdit(data.getFirstDay()));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickAddAppointmentButtonFromSREdit();
		serviceRequestsWebPage.setSubjectForSRAppointment("");
		serviceRequestsWebPage.clickAddAppointmentButton();
		Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getStatus()));
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(ServiceRequestsListVerifications.checkLifeCycleDate());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCafterCreation(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(ServiceRequestsListVerifications.isLifeCycleContentDisplayed());
		serviceRequestsWebPage.goToDocumentLinkFromLC();
		Assert.assertTrue(ServiceRequestsListVerifications.checkLifeCycleDocumentsContent());
		Assert.assertTrue(ServiceRequestsListVerifications.checkDocumentDownloadingInLC());
        serviceRequestsWebPage.clickAddImageButton();
        Assert.assertTrue(ServiceRequestsListVerifications.areImageButtonsDisplayed());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLifeCycleWOAutoCreation(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRMenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getVehicleStock(), data.getVehicleVIN());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		serviceRequestsWebPage.goToWOFromLifeCycle();
		Assert.assertTrue(ServiceRequestsListVerifications.isSRLifeCycleDisplayed());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCapproved(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getNewStatus()));
        serviceRequestsWebPage.selectFirstServiceRequestFromList();
        serviceRequestsWebPage.goToLifeCycle();
		ServiceRequestsListVerifications.checkAcceptanceOfSRInLifeCycle();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCrejected(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		Assert.assertTrue(ServiceRequestsListVerifications.isStatusDisplayed(data.getNewStatus()));
        serviceRequestsWebPage.selectFirstServiceRequestFromList();
        serviceRequestsWebPage.goToLifeCycle();
		ServiceRequestsListVerifications.checkRejectOfSRInLifeCycle();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkSRLCclosed(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.makeSearchPanelVisible();
		ServiceRequestsListVerifications.checkSRSearchCriteria();
		// serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		// serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		// serviceRequestsWebPage.clickCustomerEditButton();
		// serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		// serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.clickVehicleEditButton();
		// serviceRequestsWebPage.setVehicleInfo(data.getStock123() , data.getStock123());
		// serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.issaveNewServiceRequest();
		// serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		// Assert.assertTrue(serviceRequestsWebPage.isStatusDisplayed(newStatus));
		// serviceRequestsWebPage.selectFirstServiceRequestFromList();
		// serviceRequestsWebPage.goToLifeCycle();
		// Assert.assertTrue(serviceRequestsWebPage.checkClosedOfSRinLC());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestAccepted(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getEmailNotification());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
	}

	//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsSRCreated(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getEmailNotification());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();

		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

		//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestCheckedIn(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getNotificationDropDown());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
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
		ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
		serviceRequestsWebPage.saveNewServiceRequest();
//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWord()));

		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsAppointmentCreated(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

        backOfficeHeader.clickMiscellaneousLink();
        MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
        miscellaneouspage.clickEventsLink();
        EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getAlert());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getAlert());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentFromSRList(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
        Assert.assertTrue(ServiceRequestsListVerifications.isAddAppointmentFromSRListClosed(),
                "The Add Appointment dialog hasn't been closed");
//        Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()) || mailChecker.checkTestEmails());

// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getAlert());
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsAppointmentFailed(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getAlert());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getAlert());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentFromSRList(data.getFirstDay(), data.getSecondDay());
        Assert.assertTrue(ServiceRequestsListVerifications.isAddAppointmentFromSRListClosed(),
                "The Add Appointment dialog hasn't been closed");
// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getAlert());
		// eventsWebPage.deleteSelectedEvent();
	}

	//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestAppointmentCreated(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//		Assert.assertTrue(
//				mailChecker.checkTestEmails() || mailChecker.checkEmails(data.getEmailKeyWord()));
//todo fails
//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestAcceptedByTech(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();

		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//		Assert.assertTrue(mailChecker.checkTestEmails()
//				|| mailChecker.checkEmails(data.getEmailKeyWord()));

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestEstimationCreated(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();

		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()));

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestIsMonitored(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordRemainder())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordRemainder()) || mailChecker.checkTestEmails());

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordRemainder());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestOrderCreated(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();

		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()));

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestRejected(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWordWasCreated())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getSelected());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
		serviceRequestsWebPage.addAppointmentWithTechnician(data.getFirstDay(), data.getSecondDay(), data.getTechnician());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWordWasCreated()) || mailChecker.checkTestEmails());

//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWordWasCreated());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestCheckIn(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

//        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//                .withSubject(data.getEmailKeyWord())
//                .unreadOnlyMessages(true).maxMessagesToSearch(5);

		MiscellaneousWebPage miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getNotificationDropDown());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
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
		ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
		serviceRequestsWebPage.saveNewServiceRequest();
		// todo uncomment after BO will be configured.
//        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
//                new NadaEMailService.MailSearchParametersBuilder()
//                        .withSubject(data.getEmailKeyWordWasCreated());
//        String mailmessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
//        System.out.println("MESSAGE:\n"+ mailmessage);

//		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWord()));
//        Assert.assertTrue(emailUtils.waitForMessageWithSubjectInFolder(mailSearchParameters),
//                "Could not find email message with subject containing " + data.getEmailKeyWord());

		miscellaneouspage = new MiscellaneousWebPage(webdriver);
		backOfficeHeader.clickMiscellaneousLink();
		eventsWebPage = new EventsWebPage(webdriver);
		miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestTypeDuplicateSearchIssue(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickErrorWithBLockingRadioButton();
		serviceRequestTypesPage.selectStockRoVinOptions();
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		serviceRequestTypesPage.getTableRowWithServiceRequestType(data.getServiceRequestType());
		String currentWindow = serviceRequestTypesPage.getCurrentWindow();
		ServiceRequestTypesVehicleInfoSettingsPage settingsPage = new ServiceRequestTypesVehicleInfoSettingsPage(webdriver);
		serviceRequestTypesPage.clickSettingsVehicleInfo(data.getServiceRequestType());
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
		settingsPage.unselectCheckBox(data.getVIN());
		settingsPage.unselectCheckBox(data.getStockNum());
		settingsPage.unselectCheckBox(data.getRoNum());
		settingsPage.clickUpdateButton();
		settingsPage.closeNewTab(currentWindow);
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
		operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickNoneRadioButton();
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		serviceRequestTypesPage.getTableRowWithServiceRequestType(data.getServiceRequestType());
		currentWindow = serviceRequestTypesPage.getCurrentWindow();
		settingsPage = new ServiceRequestTypesVehicleInfoSettingsPage(webdriver);
		serviceRequestTypesPage.clickSettingsVehicleInfo(data.getServiceRequestType());
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
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        serviceRequestsListInteractions.selectAddServiceRequestDropDown(data.getServiceRequestType());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsListInteractions.scrollWindow("-300");
        backOfficeHeader.clickCompanyLink();
        CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();

		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.selectOption(data.getRoNum());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		backOfficeHeader.clickOperationsLink();
        operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();
		String randomRO = Integer.toString(new Random().nextInt());
		serviceRequestsListInteractions.setRO(randomRO);
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.saveNewServiceRequest();

		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();
		serviceRequestsListInteractions.setRO(randomRO);
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.saveNewServiceRequest();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestTypeDuplicateErrorVIN(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.selectOption(data.getVIN());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		String randomVIN = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestTypeDuplicateErrorRO(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.selectOption(data.getRoNum());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		String randomRO = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestTypeDuplicateNotificationVIN(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.selectOption(data.getVIN());
		serviceRequestTypesPage.unselectOption(data.getStockNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		String randomVIN = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		serviceRequestsWebPage.setVehicleInfo(data.getStock123(), randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestTypeDuplicateErrorStock(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.selectOption(data.getStockNum());
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		String randonStock = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestTypeDuplicateNotificationStock(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption(data.getRoNum());
		serviceRequestTypesPage.selectOption(data.getStockNum());
		serviceRequestTypesPage.unselectOption(data.getVIN());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		String randonStock = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
		serviceRequestsWebPage.setVehicleInfo(randonStock, data.getStock123());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceRequestUndoReject(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		CompanyWebPage companyPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = new ServiceRequestTypesWebPage(webdriver);
		companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType(data.getServiceRequestType());
		Assert.assertTrue(serviceRequestTypesPage.isAllowUndoRejectChecked());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.makeSearchPanelVisible();
		ServiceRequestsListVerifications.checkSRSearchCriteria();
		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo(),
				data.getAssignedTo(), data.getPoNum(), data.getRoNum());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInfoEditButton();
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
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		serviceRequestsWebPage.clickCustomerEditButton();
//		Assert.assertTrue(serviceRequestsListVerifications.checkPresenceOfServiceAdvisersByFilter(data.getCustomer()));
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertEquals(serviceRequestsWebPage.getFirstServiceAdviserName(), data.getCustomer());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyVehiclePartCanBeAssignedToServicesInSR(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsWebPage = new ServiceRequestsListInteractions();
		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		serviceDialog.checkRandomServiceOption();
		serviceDialog.clickAddServiceOption();
		Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        serviceDialog.clickVehiclePart();
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = new ServiceRequestListServiceVehiclePartDialog(webdriver);
		int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
		int assignedVehiclePartOptions = vehiclePartDialog.getAssignedVehiclePartOptions();
		vehiclePartDialog.selectRandomAvailableVehiclePartOption();
		vehiclePartDialog.clickMoveToTheRight();
		Assert.assertEquals(availableVehiclePartOptions - 1, vehiclePartDialog.getAvailableVehiclePartOptions(),
				"The available vehicle Part Options have not been reduced after moving to the right");
		Assert.assertEquals(assignedVehiclePartOptions + 1, vehiclePartDialog.getAssignedVehiclePartOptions(),
				"The assigned vehicle Part Options have not been increased after moving to the right");
		vehiclePartDialog.clickServiceVehiclePartOkButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickDoneServicesButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickServiceEditButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickCancelServicesButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyVehiclePartCanBeUnassignedFromServicesInSR(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		serviceDialog.checkRandomServiceOption();
		serviceDialog.clickAddServiceOption();
		Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        serviceDialog.clickVehiclePart();
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = new ServiceRequestListServiceVehiclePartDialog(webdriver);
		int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
		int assignedVehiclePartOptions = vehiclePartDialog.getAssignedVehiclePartOptions();
		vehiclePartDialog.selectRandomAvailableVehiclePartOption();
		vehiclePartDialog.clickMoveToTheRight();
		vehiclePartDialog.clickServiceVehiclePartOkButton();
		serviceDialog.clickVehiclePart();
		Assert.assertEquals(availableVehiclePartOptions - 1, vehiclePartDialog.getAvailableVehiclePartOptions(),
				"The available vehicle Part Options have not been reduced after moving to the right");
		Assert.assertEquals(assignedVehiclePartOptions + 1, vehiclePartDialog.getAssignedVehiclePartOptions(),
				"The assigned vehicle Part Options have not been increased after moving to the right");
		vehiclePartDialog.clickServiceVehiclePartOkButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickVehiclePart();

		vehiclePartDialog.selectRandomAssignedVehiclePartOption();
		vehiclePartDialog.clickMoveToTheLeft();
		vehiclePartDialog.clickServiceVehiclePartOkButton();
		serviceDialog.clickVehiclePart();

		Assert.assertEquals(availableVehiclePartOptions, vehiclePartDialog.getAvailableVehiclePartOptions(),
				"The available vehicle Part Options have not been increased after moving to the left");
		Assert.assertEquals(assignedVehiclePartOptions, vehiclePartDialog.getAssignedVehiclePartOptions(),
				"The assigned vehicle Part Options have not been reduced after moving to the left");

		vehiclePartDialog.clickServiceVehiclePartCancelButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickDoneServicesButton();
		serviceRequestsListInteractions.saveNewServiceRequest();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAllVehiclePartsCanBeAssignedToServicesInSR(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		serviceDialog.checkRandomServiceOption();
		serviceDialog.clickAddServiceOption();
		Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        serviceDialog.clickVehiclePart();
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = new ServiceRequestListServiceVehiclePartDialog(webdriver);
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
		serviceDialog.clickDoneServicesButton();
		serviceRequestsListInteractions.saveNewServiceRequest();


		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		serviceRequestsListInteractions.clickServiceEditButton();

		Assert.assertEquals(serviceDialog.getNumberOfSelectedServiceContainersDisplayed(), availableVehiclePartOptions,
				"The number of service displayed containers differs from the number of assigned options");
		serviceDialog.clickCancelServicesButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAllVehiclePartsCanBeUnassignedFromServicesInSR(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		serviceDialog.checkRandomServiceOption();
		serviceDialog.clickAddServiceOption();
		Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        serviceDialog.clickVehiclePart();
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = new ServiceRequestListServiceVehiclePartDialog(webdriver);
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


		vehiclePartDialog.clickServiceVehiclePartOkButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickDoneServicesButton();
		serviceRequestsListInteractions.saveNewServiceRequest();


		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickCancelServicesButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyVehiclePartWillRemainUnassignedToServicesInSRAfterClickingTheCancelButton(String rowID, String description, JSONObject testData) {

		BOoperationsSRdata data = JSonDataParser.getTestDataFromJson(testData, BOoperationsSRdata.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
        operationsPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(data.getServiceRequestType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		serviceDialog.checkRandomServiceOption();
		serviceDialog.clickAddServiceOption();
		Assert.assertTrue(serviceDialog.isSelectedServiceContainerDisplayed(), "The service container is not displayed");
        serviceDialog.clickVehiclePart();
        ServiceRequestListServiceVehiclePartDialog vehiclePartDialog = new ServiceRequestListServiceVehiclePartDialog(webdriver);
		int availableVehiclePartOptions = vehiclePartDialog.getAvailableVehiclePartOptions();
		int assignedVehiclePartOptions = vehiclePartDialog.getAssignedVehiclePartOptions();

		vehiclePartDialog.selectRandomAvailableVehiclePartOption();
		vehiclePartDialog.clickMoveToTheRight();
		Assert.assertEquals(availableVehiclePartOptions - 1, vehiclePartDialog.getAvailableVehiclePartOptions(),
				"The available vehicle Part Options have not been reduced after moving to the right");
		Assert.assertEquals(assignedVehiclePartOptions + 1, vehiclePartDialog.getAssignedVehiclePartOptions(),
				"The assigned vehicle Part Options have not been increased after moving to the right");


		vehiclePartDialog.clickServiceVehiclePartCancelButton();
		serviceDialog.clickVehiclePart();
		Assert.assertEquals(availableVehiclePartOptions, vehiclePartDialog.getAvailableVehiclePartOptions(),
				"The available vehicle Part Options have been changed after clicking the 'Cancel' button");
		Assert.assertEquals(assignedVehiclePartOptions, vehiclePartDialog.getAssignedVehiclePartOptions(),
				"The assigned vehicle Part Options have been changed after clicking the 'Cancel' button");
		vehiclePartDialog.clickServiceVehiclePartOkButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickDoneServicesButton();
		serviceRequestsListInteractions.saveNewServiceRequest();


		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.verifyOneServiceContainerIsDisplayed();
		serviceDialog.clickCancelServicesButton();
	}

//	//TODO
//	//@Test(testName = "Test Case 65521:Operation - Service Request - Services add notes")
//	public void testServicerequestServicesAddNotes() {
//		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
//		OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
//		ServiceRequestsListInteractions serviceRequestsWebPage = operationsPage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButtonAndSave();
//		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage.clickServiceEditButton();
//		serviceRequestsWebPage.addServicesToServiceRequest("Zak_Money_Multiple","Zak_Labor_Multiple");
//		ServiceRequestListServiceDialog serviceDialog = serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertTrue(serviceRequestsWebPage.checkAddedServices("Zak_Money_Multiple","Zak_Labor_Multiple"));
//	}
}