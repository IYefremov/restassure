package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.MailChecker;
import com.cyberiansoft.test.dataclasses.bo.BOoperationsServiceRequestsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BackOfficeOperationsServiceRequestsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOoperationsServiceRequestsData.json";
    private MailChecker mailChecker;

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
        mailChecker = new MailChecker();
    }

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testOperationNewServiceRequestAppointmentWholesale(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//
//        ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//        servicerequestslistpage.makeSearchPanelVisible();
//        servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//        servicerequestslistpage.clickAddServiceRequestButton();
//        servicerequestslistpage.clickGeneralInfoEditButton();
//
//        servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
//        servicerequestslistpage.clickDoneButton();
//
//        servicerequestslistpage.clickCustomerEditButton();
//        servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
//        servicerequestslistpage.clickDoneButton();
//
//        servicerequestslistpage.clickVehicleInforEditButton();
//        servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//        servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//        servicerequestslistpage.clickDoneButton();
//
//        servicerequestslistpage.clickClaimInfoEditButton();
//        servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
//        servicerequestslistpage.clickDoneButton();
//
//        servicerequestslistpage.setServiceRequestLabel(data.getLabel());
//        servicerequestslistpage.setServiceRequestDescription(data.getLabel());
//        servicerequestslistpage.saveNewServiceRequest();
//        servicerequestslistpage.makeSearchPanelVisible();
//        servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
//        servicerequestslistpage.clickFindButton();
//        servicerequestslistpage.acceptFirstServiceRequestFromList();
//        SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
//                .clickAddAppointmentToFirstServiceRequestFromList();
//        appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//        appointmentpopup.setStartTimeValue(data.getStartTime());
//        appointmentpopup.setEndTimeValue(data.getEndTime());
//        Assert.assertEquals(appointmentpopup.getSubjectValue(), data.getClientName());
//        Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getClientName());
//        Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), data.getTechnicianFieldValue());
//        String appointmentfromdate = appointmentpopup.getFromDateValue();
//        String appointmentstarttime = appointmentpopup.getStartTimeValue();
//        appointmentpopup.clickAddAppointment();
//        servicerequestslistpage
//                .appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
//    }
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void testOperationNewServiceRequestAppointmentRetail(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//		servicerequestslistpage.clickGeneralInfoEditButton();
//
//		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickClaimInfoEditButton();
//		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
//		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
//		servicerequestslistpage.clickFindButton();
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
//				.clickAddAppointmentToFirstServiceRequestFromList();
//		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//		appointmentpopup.setStartTimeValue(data.getStartTime());
//		appointmentpopup.setEndTimeValue(data.getEndTime());
//		Assert.assertEquals(appointmentpopup.getSubjectValue(), data.getNewServiceRequest());
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//		Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), data.getTechnicianFieldValue());
//		String appointmentfromdate = appointmentpopup.getFromDateValue();
//		String appointmentstarttime = appointmentpopup.getStartTimeValue();
//		appointmentpopup.clickAddAppointment();
//		servicerequestslistpage
//				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testOperationNewServiceRequestAppointmentLocationTypeCustom(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//		servicerequestslistpage.clickGeneralInfoEditButton();
//
//		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickClaimInfoEditButton();
//		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
//		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
//		servicerequestslistpage.clickFindButton();
//
//		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());
//
//		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
//				.clickAddAppointmentToFirstServiceRequestFromList();
//
//		appointmentpopup.setFromDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
//		appointmentpopup.setToDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
//		appointmentpopup.setStartTimeValue(data.getStartTime());
//		appointmentpopup.setEndTimeValue(data.getEndTime());
//		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
//		String appointmentfromdate = appointmentpopup.getFromDateValue();
//		String appointmentstarttime = appointmentpopup.getStartTimeValue();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//
//		appointmentpopup.selectLocationType(data.getLocationType());
//		appointmentpopup.setClientAddressValue(data.getClientAddress());
//		appointmentpopup.setClientCityValue(data.getClientCity());
//		appointmentpopup.setClientZipValue(data.getClientZip());
//		appointmentpopup.clickAddAppointment();
//
//		servicerequestslistpage
//				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
//		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//
//		Assert.assertEquals(appointmentpopup.getClientAddressValue(), data.getClientAddress());
//		Assert.assertEquals(appointmentpopup.getClientCityValue(), data.getClientCity());
//		Assert.assertTrue(appointmentpopup.getClientZipValue().equals(data.getClientZip())
//				|| appointmentpopup.getClientZipValue().equals(data.getClientZip2()));
//		appointmentpopup.clickAddAppointment();
//		servicerequestslistpage.closeFirstServiceRequestFromTheList();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testOperationNewServiceRequestAppointmentLocationTypeCustomer(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//		servicerequestslistpage.clickGeneralInfoEditButton();
//
//		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickClaimInfoEditButton();
//		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
//		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
//		servicerequestslistpage.clickFindButton();
//
//		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());
//
//		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
//				.clickAddAppointmentToFirstServiceRequestFromList();
//		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//		appointmentpopup.setStartTimeValue(data.getStartTime());
//		appointmentpopup.setEndTimeValue(data.getEndTime());
//		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
//		String appointmentfromdate = appointmentpopup.getFromDateValue();
//		String appointmentstarttime = appointmentpopup.getStartTimeValue();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//
//		appointmentpopup.selectLocationType(data.getLocationType());
//		appointmentpopup.clickAddAppointment();
//		servicerequestslistpage
//				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
//		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//
//		appointmentpopup.clickAddAppointment();
//		servicerequestslistpage.closeFirstServiceRequestFromTheList();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testOperationNewServiceRequestAppointmentLocationTypeOwner(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//		servicerequestslistpage.clickGeneralInfoEditButton();
//
//		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
//		servicerequestslistpage.selectServiceRequestOwner(data.getNewServiceRequest());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickClaimInfoEditButton();
//		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
//		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
//		servicerequestslistpage.clickFindButton();
//
//		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());
//
//		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
//				.clickAddAppointmentToFirstServiceRequestFromList();
//		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//		appointmentpopup.setStartTimeValue(data.getStartTime());
//		appointmentpopup.setEndTimeValue(data.getEndTime());
//		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
//		String appointmentfromdate = appointmentpopup.getFromDateValue();
//		String appointmentstarttime = appointmentpopup.getStartTimeValue();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//		appointmentpopup.selectLocationType(data.getLocationType());
//		appointmentpopup.clickAddAppointment();
//		servicerequestslistpage
//				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
//		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void testOperationNewServiceRequestAppointmentLocationTypeRepairLocation(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//		servicerequestslistpage.clickGeneralInfoEditButton();
//
//		servicerequestslistpage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPoNum(), data.getRoNum());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getNewServiceRequest());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.clickClaimInfoEditButton();
//		servicerequestslistpage.selectServiceRequestInsurance(data.getInsurance());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.setServiceRequestLabel(data.getLabel());
//		servicerequestslistpage.setServiceRequestDescription(data.getLabel());
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getNewServiceRequest());
//		// servicerequestslistpage.clickFindButton();
//
//		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());
//
//		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
//				.clickAddAppointmentToFirstServiceRequestFromList();
//		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
//		appointmentpopup.setStartTimeValue(data.getStartTime());
//		appointmentpopup.setEndTimeValue(data.getEndTime());
//		Assert.assertEquals(appointmentpopup.getTechnicianValue(), data.getAssignedTo());
//		String appointmentfromdate = appointmentpopup.getFromDateValue();
//		String appointmentstarttime = appointmentpopup.getStartTimeValue();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//
//		appointmentpopup.selectLocationType(data.getLocationType());
//		appointmentpopup.selectLocation(data.getLocation());
//		appointmentpopup.clickAddAppointment();
//		servicerequestslistpage
//				.appointmentExistsForFirstServiceRequestFromList(appointmentfromdate + " " + appointmentstarttime);
//		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
//		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), data.getNewServiceRequest());
//
//		appointmentpopup.clickAddAppointment();
//		servicerequestslistpage.closeFirstServiceRequestFromTheList();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testOperationsCLUserItNotPossibleToAcceptSR_OptionIsNotPresent(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//		backofficeheader.clickLogout();
//		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//        loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
//		HomeWebPage homepage = backofficeheader.clickHomeLink();
//		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getVIN());
//		servicerequestslistpage.clickFindButton();
//		Assert.assertFalse(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
//		servicerequestslistpage.rejectFirstServiceRequestFromList();
//		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());
//	}
//
//	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void testOperationsCLUserVerifyThatAcceptedSRIsInReadOnlyMode_NotPossibleToEdit(String rowID, String description, JSONObject testData) throws InterruptedException {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        backofficeheader.clickLogout();
//		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//		loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
//		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		HomeWebPage homepage = backofficeheader.clickHomeLink();
//		Thread.sleep(1000);
//		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.saveNewServiceRequest();
//
//		backofficeheader.clickLogout();
//		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//		loginpage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
//		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getVIN());
//		servicerequestslistpage.clickFindButton();
//		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), data.getStatus());
//
//		backofficeheader.clickLogout();
//		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//
//        loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
//        backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		homepage = backofficeheader.clickHomeLink();
//		Thread.sleep(1000);
//		servicerequestslistpage = homepage.clickNewServiceRequestLink();
//		Thread.sleep(2000);
//		servicerequestslistpage.makeSearchPanelVisible();
//
//		servicerequestslistpage.setSearchFreeText(data.getVIN());
//		servicerequestslistpage.clickFindButton();
//		servicerequestslistpage.selectFirstServiceRequestFromList();
//		servicerequestslistpage.switchToServiceRequestInfoFrame();
//        Assert.assertFalse(servicerequestslistpage.getGeneralInfoEditButton().isDisplayed());
//		Assert.assertFalse(servicerequestslistpage.getCustomerEditButton().isDisplayed());
//		Assert.assertFalse(servicerequestslistpage.getVehicleInfoEditButton().isDisplayed());
//		servicerequestslistpage.clickCloseServiceRequestButton();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void testOperationsCLUserItNotPossibleToAddLabelsWhenCreateSR(String rowID, String description, JSONObject testData) throws InterruptedException {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        backofficeheader.clickLogout();
//		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//        loginpage.UserLogin(BOConfigInfo.getInstance().getAlternativeUserName(), BOConfigInfo.getInstance().getAlternativeUserPassword());
//		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//		HomeWebPage homepage = backofficeheader.clickHomeLink();
//		Thread.sleep(1000);
//		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		Assert.assertFalse(servicerequestslistpage.getServiceRequestLabelField().isDisplayed());
//
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void testOperationsSRListVerifyThatCheckInButtonIsNotPresentWhenCreateSR(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getCustomer());
//		servicerequestslistpage.clickDoneButton();
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getVIN());
//		servicerequestslistpage.clickFindButton();
//		servicerequestslistpage.selectFirstServiceRequestFromList();
//		Assert.assertFalse(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
//		servicerequestslistpage.rejectFirstServiceRequestFromList();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testOperationsVerifyThatCheckInButtonAppearsWhenSRIsSaved(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getCustomer());
//		servicerequestslistpage.clickDoneButton();
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getVIN());
//		servicerequestslistpage.clickFindButton();
//		servicerequestslistpage.selectFirstServiceRequestFromList();
//		Assert.assertFalse(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		servicerequestslistpage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
//		Assert.assertTrue(servicerequestslistpage.isCheckInButtonVisible());
//		servicerequestslistpage.closeFirstServiceRequestFromTheList();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testOperationsSRListVerifyThatCheckInButtonIsChangedToUndoCheckInAfterPressingAndViceVersa(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
//		servicerequestslistpage.selectAddServiceRequestsComboboxValue(data.getAddServiceRequestValue());
//		servicerequestslistpage.clickAddServiceRequestButton();
//
//		servicerequestslistpage.clickCustomerEditButton();
//		servicerequestslistpage.selectServiceRequestCustomer(data.getCustomer());
//		servicerequestslistpage.clickDoneButton();
//		servicerequestslistpage.clickVehicleInforEditButton();
//		servicerequestslistpage.setServiceRequestVIN(data.getVIN());
//		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
//		servicerequestslistpage.clickDoneButton();
//
//		servicerequestslistpage.saveNewServiceRequest();
//		servicerequestslistpage.makeSearchPanelVisible();
//		servicerequestslistpage.setSearchFreeText(data.getVIN());
//		servicerequestslistpage.clickFindButton();
//		servicerequestslistpage.selectFirstServiceRequestFromList();
//		Assert.assertFalse(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
//		servicerequestslistpage.acceptFirstServiceRequestFromList();
//		servicerequestslistpage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(servicerequestslistpage.isCheckInButtonDisplayedForSelectedSR());
//		Assert.assertTrue(servicerequestslistpage.isCheckInButtonVisible());
//		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), data.getCheckInButton());
//		servicerequestslistpage.clickCheckInButtonForSelectedSR();
//		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), data.getUndoCheckInButton());
//		servicerequestslistpage.clickCheckInButtonForSelectedSR();
//		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), data.getCheckInButton());
//		servicerequestslistpage.closeFirstServiceRequestFromTheList();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testServiceRequestDescription(String rowID, String description, JSONObject testData) {
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.setServiceRequestDescription(description);
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkTimeOfLastDescription());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testServiceRequest(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//        serviceRequestsWebPage.clickAddServiceRequestButton();
//        serviceRequestsWebPage.addTags(data.getTags());
//        Assert.assertTrue(serviceRequestsWebPage.addTags(data.getTags()[data.getTags().length - 1]));
//        serviceRequestsWebPage.addTags(data.getSymbol());
//        Assert.assertTrue(serviceRequestsWebPage.removeFirtsTag());
//        serviceRequestsWebPage.saveNewServiceRequest();
//        serviceRequestsWebPage.selectFirstServiceRequestFromList();
//        Assert.assertTrue(serviceRequestsWebPage.checkTags(data.getTags()));
//    }
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void testServiceRequestDescriptionInExistingSR(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[0]);
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.addNewDescriptionAndCheckOld(data.getDescriptions()[1],
//                data.getDescriptions()[0]));
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void testShownSRDuringCreation(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		Assert.assertFalse(serviceRequestsWebPage.checkIfDescriptionIconsVisible());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testCreatingSRWithDifferentDescriptions(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[0]);
//		serviceRequestsWebPage.setServiceRequestDescription(data.getDescriptions()[1]);
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkServiceDescription(data.getDescriptions()[1]));
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkDescriptionDocument(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
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
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkMultiTechInSR(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(data.getFirstDay(), data.getSecondDay()));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(
//				serviceRequestsWebPage.checkDefaultAppointmentValuesAndAddAppointmentFomSREdit());
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkMultiTechInSRshowHideTech(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(data.getFirstDay(), data.getSecondDay()));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkShowHideTeches(data.getFirstDay(), data.getSecondDay()));
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void checkMultiTechInSideScrollbar(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentValuesFromCalendar(
//				        data.getFirstDay(), data.getSecondDay(), data.getCustomer()));
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkSRappointmentSchedulerWeek(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.checkSchedulerByDateWeek(data.getFirstDay(), data.isDateShifted());
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkSRappointmentSchedulerMonth(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationsPage.clickNewServiceRequestList();
//		int prevRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(data.getFirstDay());
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.reloadPage();
//		int afterRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(data.getFirstDay());
//        Assert.assertEquals(afterRequestsCount - prevRequestsCount, 1);
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void checkSRappointmentSchedulerMultiTechniciansFilterOf5(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
//		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
//		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkSchedulerTechniciansFilter(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
//		serviceRequestsWebPage.aplyTechniciansFromScheduler();
//		int countBeforeAnySelections = serviceRequestsWebPage.countSR();
//		serviceRequestsWebPage.selectTechnicianFromSchedulerByIndex(0);
//		serviceRequestsWebPage.aplyTechniciansFromScheduler();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkSRmultiTechReset(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.goToMonthInScheduler();
//		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
//		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
//		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
//		serviceRequestsWebPage.resetAndCheckTecniciansFromScheduler();
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkSRcreation(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkSRLCnoEntry(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		Assert.assertFalse(serviceRequestsWebPage.checkLifeCycleBTN());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void checkSRLCestimate(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.setSuggestedStartDate(data.getFirstDay());
//		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(data.getFirstDay()));
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.addAppointmentWithoutDescription(data.getFirstDay(), data.getSecondDay());
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getStatus()));
//		Assert.assertTrue(serviceRequestsWebPage.checkLifeCycleDate());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void checkSRLCafterCreation(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
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
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//	public void checkSRLCwoAutoCreation(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.goToSRmenu();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.setVehicleInfo(data.getVehicleStock(), data.getVehicleVIN());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.goToWOfromLC());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
////	public void checkSRLCapproved(String data.getCustomer(), String data.getFirstDay(), String data.getSecondDay(), String status, String SRcustomer,
////			String newStatus) {
//	public void checkSRLCapproved(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.setVehicleInfo(data.getVehicleStock(), data.getVehicleVIN());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getNewStatus()));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.checkAcceptanceOfSRinLC());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
////	public void checkSRLCrejected(String data.getCustomer(), String data.getFirstDay(), String data.getSecondDay(), String status, String SRcustomer,
////			String newStatus) {
//        public void checkSRLCrejected(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.setVehicleInfo(data.getVehicleStock(), data.getVehicleVIN());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(data.getNewStatus()));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.checkRejectOfSRinLC());
//	}
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
////  @Test(testName = "Test Case 57879:Operation - Service Request Life Cycle - Closed", dataProvider = "provideSRdata1")
////	public void checkSRLCclosed(String data.getCustomer(), String data.getFirstDay(), String data.getSecondDay(), String status, String SRcustomer,
////			String newStatus) {
//	public void checkSRLCclosed(String rowID, String description, JSONObject testData) {
//
//        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
//        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//
//        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
//        ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.makeSearchPanelVisible();
//		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
//		// serviceRequestsWebPage.selectAddServiceRequestDropDown(data.getServiceRequestType());
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsServiceRequestAccepted(String rowID, String description, JSONObject testData) {

        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneouspage = backofficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getEmailNotification());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
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
		Assert.assertTrue(
				mailChecker.checkEmails(data.getEmailKeyWordRemainder()) || mailChecker.checkEmails(data.getEmailKeyWordWasCreated()));

		miscellaneouspage = backofficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMiscellaneousEventsSRCreated(String rowID, String description, JSONObject testData) {

        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneouspage = backofficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getEmailNotification());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
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
		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWord())
				|| mailChecker.checkTestEmails());
		miscellaneouspage = backofficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMiscellaneousEventsServiceRequestCheckedIn(String rowID, String description, JSONObject testData) {

        BOoperationsServiceRequestsData data = JSonDataParser.getTestDataFromJson(testData, BOoperationsServiceRequestsData.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneouspage = backofficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent(data.getEvent());
		eventsWebPage.setAlertNewName(data.getEventNewName());
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.setEmailNotificationDropDownForSelected(data.getNotificationDropDown());
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
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
		Assert.assertTrue(mailChecker.checkEmails(data.getEmailKeyWord()));
		miscellaneouspage = backofficeHeader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName(data.getEventNewName());
		eventsWebPage.deleteSelectedEvent();
	}

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
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate, data.getTechnician());
//        Assert.assertTrue(serviceRequestsWebPage.checkEmails(data.getEmailKeyWordWasCreated()) || serviceRequestsWebPage.checkTestEmails());
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
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate, data.getTechnician());
//		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails() || serviceRequestsWebPage.checkEmails(data.getEmailKeyWordWasCreated()));
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
//		eventsWebPage.selectEvent(data.getEvent());
//		eventsWebPage.setAlertNewName(data.getEventNewName());
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, data.getTechnician());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(
//				serviceRequestsWebPage.checkTestEmails() || serviceRequestsWebPage.checkEmails("was not checked in"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
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
//		eventsWebPage.selectEvent(data.getEvent());
//		eventsWebPage.setAlertNewName(data.getEventNewName());
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, data.getTechnician());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails()
//				|| serviceRequestsWebPage.checkEmails("Service Request with RO#  was created"));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
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
//		eventsWebPage.selectEvent(data.getEvent());
//		eventsWebPage.setAlertNewName(data.getEventNewName());
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, data.getTechnician());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails(data.getEmailKeyWordWasCreated()));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
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
//		eventsWebPage.selectEvent(data.getEvent());
//		eventsWebPage.setAlertNewName(data.getEventNewName());
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, data.getTechnician());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails(data.getEmailKeyWordRemainder()) || serviceRequestsWebPage.checkTestEmails());
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
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
//		eventsWebPage.selectEvent(data.getEvent());
//		eventsWebPage.setAlertNewName(data.getEventNewName());
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, data.getTechnician());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails(data.getEmailKeyWordWasCreated()));
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
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
//		eventsWebPage.selectEvent(data.getEvent());
//		eventsWebPage.setAlertNewName(data.getEventNewName());
//		Assert.assertTrue(eventsWebPage.saveNewEvent());
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
//		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
//		eventsWebPage.setEmailNotificationCheckBoxForSelected();
//		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
//		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, data.getTechnician());
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkEmails(data.getEmailKeyWordWasCreated()) || serviceRequestsWebPage.checkTestEmails());
//		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
//		eventsWebPage = miscellaneouspage.clickEventsLink();
//		eventsWebPage.selectEventRowByName(data.getEventNewName());
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
//		serviceRequestsWebPage.selectServiceRequestCustomer(data.getCustomer());
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickGeneralInfoEditButton();
//		serviceRequestsWebPage.setServiceRequestGeneralInfo(data.getServiceRequestGeneralInfo());
//		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, data.getTechnician());
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
