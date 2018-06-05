package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.bo.BOOperationsServiceRequests;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BackOfficeOperationsServiceRequestsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOOperationsServiceRequestsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentWholesale(String rowID, String description, JSONObject testData) {

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

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
                .isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentRetail(String rowID, String description, JSONObject testData) {

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

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
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentLocationTypeCustom(String rowID, String description, JSONObject testData) {

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

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
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		Assert.assertEquals(appointmentpopup.getClientAddressValue(), data.getClientAddress());
		Assert.assertEquals(appointmentpopup.getClientCityValue(), data.getClientCity());
		Assert.assertTrue(appointmentpopup.getClientZipValue().equals(data.getClientZip())
				|| appointmentpopup.getClientZipValue().equals(data.getClientZip2()));
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentLocationTypeCustomer(String rowID, String description, JSONObject testData) {

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

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
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequestAppointmentLocationTypeOwner(String rowID, String description, JSONObject testData) {

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

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
		appointmentpopup.selectLocationType("Owner");
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequestAppointmentLocationTypeRepairLocation(String rowID, String description, JSONObject testData) {

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

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
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());

		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationsCLUserItNotPossibleToAcceptSR_OptionIsNotPresent(String rowID, String description, JSONObject testData) {

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(data.getAnotherLogin(), data.getAnotherPassword());
		HomeWebPage homepage = backofficeheader.clickHomeLink();
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
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "Request Rejected");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class) //todo username, userpassword from dataProvider
	public void testOperationsCLUserVerifyThatAcceptedSRIsInReadOnlyMode_NotPossibleToEdit(String rowID, String description, JSONObject testData) throws InterruptedException {

		final String anotherLogin = "zayats@cyberiansoft.com";
		final String anotherPassword = "1234567";

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(anotherLogin, anotherPassword);
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		HomeWebPage homepage = backofficeheader.clickHomeLink();
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

		backofficeheader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(data.getVIN());
		servicerequestslistpage.clickFindButton();
		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "Scheduled");

		backofficeheader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);

		loginpage.UserLogin(anotherLogin, anotherPassword);
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		homepage = backofficeheader.clickHomeLink();
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

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		HomeWebPage homepage = backofficeheader.clickHomeLink();
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

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
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

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
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

        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
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
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), "Check-In");
		servicerequestslistpage.clickCheckInButtonForSelectedSR();
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), "Undo Check-In");
		servicerequestslistpage.clickCheckInButtonForSelectedSR();
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), "Check-In");
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceRequestDescription(String rowID, String description, JSONObject testData) {
        BOOperationsServiceRequests data = JSonDataParser.getTestDataFromJson(testData, BOOperationsServiceRequests.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(description);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkTimeOfLastDescription());
	}

//	@DataProvider
//	public Object[][] provideSRdescription() {
//		return new Object[][] { { "test description" } };
//	}
//
//    @Test(testName = "Test Case 56761:Operation - Service Request - Tags manipulation in new SR", dataProvider = "provideSRwholeInfo")
//	public void testServiceRequest(String[] tags, String symbol) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.addTags(tags);
//		Assert.assertTrue(serviceRequestsWebPage.addTags(tags[tags.length - 1]));
//		serviceRequestsWebPage.addTags(symbol);
//		Assert.assertTrue(serviceRequestsWebPage.removeFirtsTag());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkTags(tags));
//	}
//
//	@DataProvider
//	public Object[][] provideSRwholeInfo() {
//		return new Object[][] { { new String[] { "tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7" }, "s" } };
//	}
//
//    @Test(testName = "Test Case 56760:Operation - Service Request - Description in excisting SR", dataProvider = "provideSomeDescriptions")
//	public void testServiceRequestDesciptionInExistingSR(String[] descriptions) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.setServiceRequestDescription(descriptions[0]);
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.addNewDescriptionAndCheckOld(descriptions[1], descriptions[0]));
//	}
//
//	@DataProvider
//	public Object[][] provideSomeDescriptions() {
//		return new Object[][] { { new String[] { "test description1", "test description2" } } };
//	}
//
//    @Test(testName = "Test Case 56827:Operation - Service Request - Documents not shown during creation,"
//			+ "Test Case 56828:Operation - Service Request - Answers not shown during creation")
//	public void testShownSRDuringCreation() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		Assert.assertFalse(serviceRequestsWebPage.checkIfDescriptionIconsVisible());
//	}
//
//	@Test(testName = "Test Case 56756:Operation - Service Request - Description in new SR", dataProvider = "provideSomeDescriptions")
//	public void testCreatingSRWithDifferentDescriptions(String[] descriptions) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.setServiceRequestDescription(descriptions[0]);
//		serviceRequestsWebPage.setServiceRequestDescription(descriptions[1]);
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkServiceDescription(descriptions[1]));
//	}
//
//	@Test(testName = "Test Case 56829:Operation - Service Request - Check Documents")
//	public void checkDescriptionDocument() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkServiceRequestDocumentIcon());
//		serviceRequestsWebPage.clickDocumentButton();
//		Assert.assertTrue(serviceRequestsWebPage.checkElementsInDocument());
//		Assert.assertTrue(serviceRequestsWebPage.clickAddImageBTN());
//		//serviceRequestsWebPage.addImage();
//	}
//
//	@Test(testName = "Test Case 56832:Operation - Service Request - Appointment - Add Multi Tech in SR",
//            dataProvider = "provideSRdata")
//	public void checkMultiTechInSR(String data.getCustomer(), String startDate, String endDate, String status,
//			boolean isDateShifted) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(
//				serviceRequestsWebPage.checkDefaultAppointmentValuesAndAddAppointmentFomSREdit());
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
//	}
//
//	@Test(testName = "Test Case 56834:Operation - Service Request - Appointment - Multi Tech - show/hide tech", dataProvider = "provideSRdata")
//	public void checkMultiTechInSRshowHideTech(String data.getCustomer(), String startDate, String endDate, String status,
//			boolean isDateShifted) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkShowHideTeches(startDate, endDate));
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
//	}
//
//	@Test(testName = "Test Case 56833:Operation - Service request - Appointment - Multi Tech in side scrollbar", dataProvider = "provideSRdata1")
//	public void checkMultiTechInSideScrollbar(String data.getCustomer(), String startDate, String endDate, String status,
//			String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
//		Assert.assertTrue(
//				serviceRequestsWebPage.checkDefaultAppointmentValuesFromCalendar(startDate, endDate, SRcustomer));
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
//	}
//
//	@DataProvider
//	public Object[][] provideSRdata1() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//		String firstDate;
//		String secondDate;
//		if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
//			firstDate = LocalDate.now().plusDays(3).format(formatter);
//			secondDate = LocalDate.now().plusDays(4).format(formatter);
//		} else if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
//			firstDate = LocalDate.now().plusDays(2).format(formatter);
//			secondDate = LocalDate.now().plusDays(3).format(formatter);
//		} else {
//			firstDate = LocalDate.now().plusDays(1).format(formatter);
//			secondDate = LocalDate.now().plusDays(2).format(formatter);
//		}
//		return new Object[][] { { data.getClientName(), firstDate, secondDate, data.getStatus(), data.getClientName(), "Scheduled" } };
//	}
//
//	@Test(testName = "Test Case 56835:Operation - Service Request - Appointment - Scheduler - Week", dataProvider = "provideSRdata")
//	public void checkSRappointmentSchedulerWeek(String data.getCustomer(), String startDate, String endDate, String status,
//			boolean isDateShifted) throws InterruptedException {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		int prevReqestsCount = serviceRequestsWebPage.checkSchedulerByDateWeek(startDate, isDateShifted);
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setSuggestedStartDate(startDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
//	}
//
//    @DataProvider
//    public Object[][] provideSRdata() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//        String firstDate;
//        String secondDate;
//        boolean isDateShifted;
//        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
//            firstDate = LocalDate.now().plusDays(3).format(formatter);
//            secondDate = LocalDate.now().plusDays(4).format(formatter);
//            isDateShifted = true;
//        } else if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
//            firstDate = LocalDate.now().plusDays(2).format(formatter);
//            secondDate = LocalDate.now().plusDays(3).format(formatter);
//            isDateShifted = true;
//        } else {
//            firstDate = LocalDate.now().plusDays(1).format(formatter);
//            secondDate = LocalDate.now().plusDays(2).format(formatter);
//            isDateShifted = false;
//        }
//
//        return new Object[][] { { data.getClientName(), firstDate, secondDate, "Scheduled", isDateShifted } };
//    }
//
//    @DataProvider
//    public Object[][] provideSRdataForSchedulerMonth() {
//        String date;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//        LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
//
//        if (localDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
//            date = localDate.plusDays(3).format(formatter);
//        } else if (localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
//            date = localDate.plusDays(2).format(formatter);
//        } else {
//            date = localDate.plusDays(1).format(formatter);
//        }
//        return new Object[][] {{ data.getClientName(), date }};
//    }
//
//	@Test(testName = "Test Case 56835:Operation - Service Request - Appointment - Scheduler - Month",
//            dataProvider = "provideSRdataForSchedulerMonth")
//	public void checkSRappointmentSchedulerMonth(String data.getCustomer(), String startDate) {
//		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationsPage.clickNewServiceRequestList();
//		int prevRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(startDate);
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setSuggestedStartDate(startDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.reloadPage();
//		int afterRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(startDate);
//        Assert.assertEquals(afterRequestsCount - prevRequestsCount, 1);
//	}
//
//	@Test(testName = "Test Case 56840:Operation - Service Request - Appointment - Scheduler - Multi Technicians filter of 5")
//	public void checkSRappointmentSchedulerMultiTechniciansFilterOf5() throws InterruptedException {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
//		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
//		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
//	}
//
//	@Test(testName = "Test Case 56838:Operation - Service Request - Appointment - Scheduler - Technicians filter", dataProvider = "provideSRdata")
//	public void checkSchedulerTechniciansFilter(String data.getCustomer(), String startDate, String endDate, String status,
//			boolean isDateShifted) throws InterruptedException {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.setSuggestedStartDate(startDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
//		serviceRequestsWebPage.aplyTechniciansFromScheduler();
//		int countBeforeAnySelections = serviceRequestsWebPage.countSR();
//		serviceRequestsWebPage.selectTechnicianFromSchedulerByIndex(0);
//		serviceRequestsWebPage.aplyTechniciansFromScheduler();
//	}
//
//	@Test(testName = "Test Case 56841:Operation - Service Request - Appointment - Scheduler - Multi Technicians Reset",
//            dataProvider = "provideSRdata")
//	public void checkSRmultiTechReset(String data.getCustomer(), String startDate, String endDate, String status,
//			boolean isDateShifted) throws InterruptedException {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.setSuggestedStartDate(startDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
//		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
//		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
//		serviceRequestsWebPage.resetAndCheckTecniciansFromScheduler();
//	}
//
//	@Test(testName = "Test Case 56839:Operation - Service Request - Appointment - Scheduler - Add Service Request", dataProvider = "provideSRdata")
//	public void checkSRcreation(String data.getCustomer(), String startDate, String endDate, String status, boolean isDateShifted) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.setSuggestedStartDate(startDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
//	}
//
//	// @Test(testName = "Test Case 56837:Operation - Service Request -
//	// Appointment - Scheduler - Timeline", dataProvider = "provideSRdata")
//	public void checkSRtimeline(String data.getCustomer(), String startDate, String endDate, String status, boolean isDateShifted)
//			throws InterruptedException {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		serviceRequestsWebPage.goToTimeLine();
//		int srCountBefore = serviceRequestsWebPage.countSRinTimelineByDate(startDate);
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setSuggestedStartDate(startDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		serviceRequestsWebPage.goToTimeLine();
//		int srCountAfter = serviceRequestsWebPage.countSRinTimelineByDate(startDate);
//		Assert.assertTrue(srCountBefore != srCountAfter);
//	}
//
//	@Test(testName = "Test Case 57805:Operation - Service Request Life Cycle - No Entry")
//	public void checkSRLCnoEntry() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		Assert.assertFalse(serviceRequestsWebPage.checkLifeCycleBTN());
//	}
//
//	@Test(testName = "Test Case 57874:Operation - Service Request Life Cycle - Appointment - Estimate", dataProvider = "provideSRdata")
//	public void checkSRLCestimate(String data.getCustomer(), String startDate, String endDate, String status,
//			boolean isDateShifted) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setSuggestedStartDate(startDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.addAppointmentWithoutDescription(startDate, endDate);
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
//		Assert.assertTrue(serviceRequestsWebPage.checkLifeCycleDate());
//	}
//
//	@Test(testName = "Test Case 57806:Operation - Service Request Life Cycle - After Creation")
//	public void checkSRLCafterCreation() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.isLifeCycleContentDisplayed());
//		serviceRequestsWebPage.goToDocumentLinkFromLC();
//		Assert.assertTrue(serviceRequestsWebPage.checkLifeCycleDocumentsContent());
//		Assert.assertTrue(serviceRequestsWebPage.checkDocumentDownloadingInLC());
//		Assert.assertTrue(serviceRequestsWebPage.clickAddImageBTN());
//	//	serviceRequestsWebPage.addImage();
//	}
//
//	@Test(testName = "Test Case 57807:Operation - Service Request Life Cycle - WO Auto Creation", dataProvider = "provideSRdata")
//	public void checkSRLCwoAutoCreation(String data.getCustomer(), String startDate, String endDate, String status,
//			boolean isDateShifted) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_Auto");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.goToWOfromLC());
//	}
//
//	@Test(testName = "Test Case 57875:Operation - Service Request Life Cycle - Approved", dataProvider = "provideSRdata1")
//	public void checkSRLCapproved(String data.getCustomer(), String startDate, String endDate, String status, String SRcustomer,
//			String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.checkAcceptanceOfSRinLC());
//	}
//
//	@Test(testName = "Test Case 57879:Operation - Service Request Life Cycle - Rejected", dataProvider = "provideSRdata1")
//	public void checkSRLCrejected(String data.getCustomer(), String startDate, String endDate, String status, String SRcustomer,
//			String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.checkRejectOfSRinLC());
//	}
//
//	@Test(testName = "Test Case 57879:Operation - Service Request Life Cycle - Closed", dataProvider = "provideSRdata1")
//	public void checkSRLCclosed(String data.getCustomer(), String startDate, String endDate, String status, String SRcustomer,
//			String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.makeSearchPanelVisible();
//		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
//		// serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
//		// serviceRequestsWebPage.clickAddServiceRequestButton();
//		// serviceRequestsWebPage.clickCustomerEditButton();
//		// serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		// serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.clickVehicleEditButton();
//		// serviceRequestsWebPage.setVehicleInfo("123" , "123");
//		// serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.saveNewServiceRequest();
//		// serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		// Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
//		// serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		// serviceRequestsWebPage.goToLifeCycle();
//		// Assert.assertTrue(serviceRequestsWebPage.checkClosedOfSRinLC());
//	}
//
//	//todo check
//	@Test(testName = "Test Case 59700:Miscellaneous - Events: Service Request Accepted",
//            dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestAccepted(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(
//				serviceRequestsWebPage.checkEmails("Remainder") || serviceRequestsWebPage.checkEmails("was created"));
//
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//	}
//
//    @Test(testName = "Test Case 59700:Miscellaneous - Events:SR Created", dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsSRCreated(String data.getCustomer(), String startDate, String endDate, String status,
//			String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#  was created")
//				|| serviceRequestsWebPage.checkTestEmails());
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 31350:Miscellaneous - Events: Service Request Checked In",
//            dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestCheckedIn(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) throws InterruptedException {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Checked In");
//		eventsWebPage.setAlertNewName("test appointment SR Checked In");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
//		eventsWebPage.setEmailNotificationDropDownForSelected("ServiceRequest Checked In");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//        serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.clickCheckInButtonForSelectedSR();
//		serviceRequestsWebPage.switchToServiceRequestInfoFrame();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
//		eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 31234:Miscellaneous - Events: Appointment Created",
//            dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsAppointmentCreated(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Appointment Created");
//		eventsWebPage.setAlertNewName("test appointment Appointment Created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment Appointment Created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("test appointment creation/fail");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate, "Automation 2 Appointment Tech");
//        Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created") || serviceRequestsWebPage.checkTestEmails());
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment Appointment Created");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 31296:Miscellaneous - Events: Appointment Failed",
//            dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsAppointmentFailed(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Appointment Failed");
//		eventsWebPage.setAlertNewName("test appointment Appointment Failed");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment Appointment Failed");
//		eventsWebPage.setEmailNotificationDropDownForSelected("test appointment creation/fail");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate, "Automation 2 Appointment Tech");
//		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails() || serviceRequestsWebPage.checkEmails("was created"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment Appointment Failed");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 59702:Miscellaneous - Events: Service Request Appointment Created",
//            dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestAppointmentCreated(String data.getCustomer(), String startDate,
//			String endDate, String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(
//				serviceRequestsWebPage.checkTestEmails() || serviceRequestsWebPage.checkEmails("was not checked in"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 59701:Miscellaneous - Events: Service Request Accepted By Tech", dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestAcceptedByTech(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails()
//				|| serviceRequestsWebPage.checkEmails("Service Request with RO#  was created"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 59703:Miscellaneous - Events: Service Request Estimation Created",
//            dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestEstimationCreated(String data.getCustomer(), String startDate,
//			String endDate, String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 59704:Miscellaneous - Events: Service Request Is Monitored", dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestIsMonitored(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Remainder") || serviceRequestsWebPage.checkTestEmails());
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	//todo check
//    @Test(testName = "Test Case 59705:Miscellaneous - Events: Service Request Order Created", dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestOrderCreated(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 59706:Miscellaneous - Events: Service Request Rejected", dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestRejected(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Created");
//		eventsWebPage.setAlertNewName("test appointment SR created");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created") || serviceRequestsWebPage.checkTestEmails());
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR created");
//		// eventsWebPage.deleteSelectedEvent();
//	}
//
//	@Test(testName = "Test Case 59636:Events: SR Check In", dataProvider = "provideSRdata1")
//	public void testMiscellaneousEventsServiceRequestCheckIn(String data.getCustomer(), String startDate, String endDate,
//			String status, String SRcustomer, String newStatus) {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.clickAddEventButton();
//		eventsWebPage.selectEvent("Service Request Checked In");
//		eventsWebPage.setAlertNewName("test appointment SR Checked In");
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.clickCheckInButtonForSelectedSR();
//		serviceRequestsWebPage.switchToServiceRequestInfoFrame();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
//	}
//
//    @Test(testName = "Test Case 63581:Company - Service Request Type: Duplicate search Issue")
//	public void testServiceRequestTypeDublicateSearchIssue() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
//		serviceRequestsWebPage.scrollWindow("-300");
//		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickErrorWithBLockingRadioButton();
//		serviceRequestTypesPage.selectStockRoVinOptions();
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		serviceRequestTypesPage.getTableRowWithServiceRequestType("01_Alex2SRT");
//		String currentWindow = serviceRequestTypesPage.getCurrentWindow();
//		ServiceRequestTypesVehicleInfoSettingsPage settingsPage = serviceRequestTypesPage
//				.clickSettingsVehicleInfo("01_Alex2SRT");
//		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
//		settingsPage.unselectCheckBox("data.getVIN() #");
//		settingsPage.unselectCheckBox("Stock #");
//		settingsPage.unselectCheckBox("RO #");
//		settingsPage.clickUpdateButton();
//		settingsPage.closeNewTab(currentWindow);
//		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
//		operationspage = backofficeheader.clickOperationsLink();
//		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		companyPage = backofficeheader.clickCompanyLink();
//		serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickNoneRadioButton();
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		serviceRequestTypesPage.getTableRowWithServiceRequestType("01_Alex2SRT");
//		currentWindow = serviceRequestTypesPage.getCurrentWindow();
//		settingsPage = serviceRequestTypesPage.clickSettingsVehicleInfo("01_Alex2SRT");
//		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
//		settingsPage.selectCheckBox("data.getVIN() #");
//		settingsPage.selectCheckBox("Stock #");
//		settingsPage.selectCheckBox("RO #");
//		settingsPage.clickUpdateButton();
//		settingsPage.closeNewTab(currentWindow);
//	}
//
//    @Test(testName = "Test Case 64129:Company - Service Request Type: Duplicate Notification RO")
//	public void testServiceRequestTypeDublicateNotificationRO() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
//		serviceRequestsWebPage.scrollWindow("-300");
//		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickWarningOnlyRadioButton();
//		serviceRequestTypesPage.unselectOption("data.getVIN()");
//		serviceRequestTypesPage.selectOption("RO");
//		serviceRequestTypesPage.unselectOption("Stock");
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		operationspage = backofficeheader.clickOperationsLink();
//		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		String randomRO = Integer.toString(new Random().nextInt());
//		serviceRequestsWebPage.setRO(randomRO);
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setRO(randomRO);
//		serviceRequestsWebPage.clickDoneButton();
//		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
//	}
//
//    @Test(testName = "Test Case 64124:Company - Service Request Type: Duplicate Error data.getVIN()")
//	public void testServiceRequestTypeDublicateErrorVIN() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
//		serviceRequestsWebPage.scrollWindow("-300");
//		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickWarningOnlyRadioButton();
//		serviceRequestTypesPage.unselectOption("RO");
//		serviceRequestTypesPage.selectOption("data.getVIN()");
//		serviceRequestTypesPage.unselectOption("Stock");
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		operationspage = backofficeheader.clickOperationsLink();
//		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		String randomVIN = Integer.toString(new Random().nextInt());
//		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
//		serviceRequestsWebPage.clickDoneButton();
//		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
//	}
//
//    @Test(testName = "Test Case 64125:Company - Service Request Type: Duplicate Error RO")
//	public void testServiceRequestTypeDublicateErrorRO() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
//		serviceRequestsWebPage.scrollWindow("-300");
//		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickWarningOnlyRadioButton();
//		serviceRequestTypesPage.unselectOption("data.getVIN()");
//		serviceRequestTypesPage.selectOption("RO");
//		serviceRequestTypesPage.unselectOption("Stock");
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		operationspage = backofficeheader.clickOperationsLink();
//		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		String randomRO = Integer.toString(new Random().nextInt());
//		serviceRequestsWebPage.setRO(randomRO);
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setRO(randomRO);
//		serviceRequestsWebPage.clickDoneButton();
//		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
//	}
//
//    @Test(testName = "Task 64149:Automate Test Case 64128:Company - Service Request Type: Duplicate Notification data.getVIN()" )
//	public void testServiceRequestTypeDublicateNotificationVIN() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
//		serviceRequestsWebPage.scrollWindow("-300");
//		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickWarningOnlyRadioButton();
//		serviceRequestTypesPage.unselectOption("RO");
//		serviceRequestTypesPage.selectOption("data.getVIN()");
//		serviceRequestTypesPage.unselectOption("Stock");
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		operationspage = backofficeheader.clickOperationsLink();
//		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		String randomVIN = Integer.toString(new Random().nextInt());
//		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
//		serviceRequestsWebPage.clickDoneButton();
//		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
//	}
//
//    @Test(testName = "Task 64147:Automate Test Case 64126:Company - Service Request Type: Duplicate Error STOCK")
//	public void testServiceRequestTypeDublicateErrorStock() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
//		serviceRequestsWebPage.scrollWindow("-300");
//		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickWarningOnlyRadioButton();
//		serviceRequestTypesPage.unselectOption("data.getVIN()");
//		serviceRequestTypesPage.selectOption("Stock");
//		serviceRequestTypesPage.unselectOption("RO");
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		operationspage = backofficeheader.clickOperationsLink();
//		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		String randonStock = Integer.toString(new Random().nextInt());
//		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
//		serviceRequestsWebPage.clickDoneButton();
//		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
//	}
//
//    @Test(testName = "Task 64148:Automate Test Case 64127:Company - Service Request Type: Duplicate Notification STOCK")
//	public void testServiceRequestTypeDublicateNotificationStock() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
//		serviceRequestsWebPage.scrollWindow("-300");
//		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
//		serviceRequestTypesPage.openGeneralSettingsTab();
//		serviceRequestTypesPage.clickWarningOnlyRadioButton();
//		serviceRequestTypesPage.unselectOption("RO");
//		serviceRequestTypesPage.selectOption("Stock");
//		serviceRequestTypesPage.unselectOption("data.getVIN()");
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		operationspage = backofficeheader.clickOperationsLink();
//		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		String randonStock = Integer.toString(new Random().nextInt());
//		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
//		serviceRequestsWebPage.clickDoneButton();
//		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
//	}
//
//    @Test(testName = "Test Case 66190:Operation - Service Request - Undo Rejected")
//	public void testServicerequestUndoReject() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
//		ServiceRequestTypesWebPage serviceRequestTypesPage = companypage.clickServiceRequestTypesLink();
//		serviceRequestTypesPage.clickEditServiceRequestType("Vit_All_Services");
//		Assert.assertTrue(serviceRequestTypesPage.isAllowUndoRejectChecked());
//		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.makeSearchPanelVisible();
//		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
//		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue("Vit_All_Services");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo("Default team", "Vitaliy Kupchynskyy", "D525", "Dfg 25");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getClientName());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleInforEditButton();
//		serviceRequestsWebPage.setServiceRequestVIN("1HGCG55691A267167");
//		serviceRequestsWebPage.decodeAndVerifyServiceRequestVIN("Honda", "Accord");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickClaimInfoEditButton();
//		serviceRequestsWebPage.selectServiceRequestInsurance("Oranta");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setServiceRequestLabel("test");
//		serviceRequestsWebPage.setServiceRequestDescription("test");
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.makeSearchPanelVisible();
//		serviceRequestsWebPage.setSearchFreeText(data.getClientName());
//		serviceRequestsWebPage.clickFindButton();
//		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		serviceRequestsWebPage.clickRejectUndoButton();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//	}
//
//    @Test(testName = "Test Case 65611:Operation - Service Request - Adviser Listing")
//	public void testServiceRequestAdviserListing() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//        serviceRequestsWebPage.clickCustomerEditButton();
//        Assert.assertTrue(serviceRequestsWebPage.checkPresenceOfServiceAdvisersByFilter("tes"));
//        serviceRequestsWebPage.clickDoneButton();
//        serviceRequestsWebPage.clickCustomerEditButton();
//        serviceRequestsWebPage.selectServiceRequestCustomer("001 - Test Company");
//        serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertEquals(serviceRequestsWebPage.getServiceAdviserName(), "001 - Test Company");
//	}
//
//	//TODO
//	//@Test(testName = "Test Case 65521:Operation - Service Request - Services add notes")
//	public void testServicerequestServicesAddNotes() {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickServiceEditButton();
//		serviceRequestsWebPage.addServicesToServiceRequest("Zak_Money_Multiple","Zak_Labor_Multiple");
//		serviceRequestsWebPage.clickServiceEditButton();
//		Assert.assertTrue(serviceRequestsWebPage.checkAddedServices("Zak_Money_Multiple","Zak_Labor_Multiple"));
//	}
}
