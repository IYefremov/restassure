package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.DataProviderPool;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BackOfficeOperationsServiceRequestsTestCases extends BaseTestCase {

    @Test(testName = "Test Case 25584:Operation - New service request - Appointment - Retail", description = "Operation - New service request - Appointment - Retail")
	public void testOperationNewServiceRequestAppointmentRetail() throws InterruptedException {

		final String teamname = "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";

		final String newservicerequest = "Alex SASHAZ";

		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";

		final String insurance = "Oranta";
		final String _label = "test";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(teamname, assignedto, ponum, ronum);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(insurance);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(_label);
		servicerequestslistpage.setServiceRequestDescription(_label);
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(newservicerequest);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getSubjectValue(), newservicerequest);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), "All");
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		appointmentpopup.clickAddAppointment();
		Thread.sleep(3000);
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
	}

    @Test(testName = "Test Case 25589:Operation - New service request - Appointment - Wholesale", description = "Operation - New service request - Appointment - Wholesale")
	public void testOperationNewServiceRequestAppointmentWholesale() throws InterruptedException {

		final String teamname = "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";

		final String newservicerequest = "Alex SASHAZ";

		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";

		final String insurance = "Oranta";
		final String _label = "test";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(teamname, assignedto, ponum, ronum);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(insurance);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(_label);
		servicerequestslistpage.setServiceRequestDescription(_label);
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(newservicerequest);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getSubjectValue(), "Alex SASHAZ");
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), "Alex SASHAZ");
		Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), "All");
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
	}

	@Test(testName = "Test Case 26164:Operation - New service request - Appointment - Location Type: Custom",
            description = "Operation - New service request - Appointment - Location Type: Custom")
	public void testOperationNewServiceRequestAppointmentLocationTypeCustom() {

		final String teamname = "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";

		final String newservicerequest = "Alex SASHAZ";

		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";

		final String insurance = "Oranta";
		final String _label = "test";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(teamname, assignedto, ponum, ronum);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(insurance);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(_label);
		servicerequestslistpage.setServiceRequestDescription(_label);
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(newservicerequest);
		servicerequestslistpage.clickFindButton();

		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "OnHold");

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();

		appointmentpopup.setFromDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

		appointmentpopup.selectLocationType("Custom");
		appointmentpopup.setClientAddressValue("407 SILVER SAGE DR.");
		appointmentpopup.setClientCityValue("NewYork");
		appointmentpopup.setClientZipValue("20002");
		appointmentpopup.clickAddAppointment();

		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
		Assert.assertTrue(appointmentpopup.getClientZipValue().equals("20002")
				|| appointmentpopup.getClientZipValue().equals("10001"));
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(testName = "Test Case 26165:Operation - New service request - Appointment - Location Type: Customer",
            description = "Operation - New service request - Appointment - Location Type: Customer")
	public void testOperationNewServiceRequestAppointmentLocationTypeCustomer() {

		final String teamname = "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";

		final String newservicerequest = "Alex SASHAZ";

		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";

		final String insurance = "Oranta";
		final String _label = "test";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(teamname, assignedto, ponum, ronum);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(insurance);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(_label);
		servicerequestslistpage.setServiceRequestDescription(_label);
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(newservicerequest);
		servicerequestslistpage.clickFindButton();

		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "OnHold");

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

		appointmentpopup.selectLocationType("Customer");
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

	@Test(testName = "Test Case 26168:Operation - New service request - Appointment - Location Type: Owner", description = "Operation - New service request - Appointment - Location Type: Owner")
	public void testOperationNewServiceRequestAppointmentLocationTypeOwner() throws InterruptedException {

		final String teamname = "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";

		final String newservicerequest = "Alex SASHAZ";

		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";

		final String insurance = "Oranta";
		final String _label = "test";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(teamname, assignedto, ponum, ronum);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.selectServiceRequestOwner(newservicerequest);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(insurance);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(_label);
		servicerequestslistpage.setServiceRequestDescription(_label);
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(newservicerequest);
		servicerequestslistpage.clickFindButton();

		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "OnHold");

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		appointmentpopup.selectLocationType("Owner");
		Thread.sleep(1000);
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

	
		appointmentpopup.clickAddAppointment();
		appointmentpopup.waitABit(2000);
//		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(testName = "Test Case 26172:Operation - New service request - Appointment - Location Type: Repair Location", description = "Operation - New service request - Appointment - Location Type: Repair Location")
	public void testOperationNewServiceRequestAppointmentLocationTypeRepairLocation() {

		final String teamname = "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";

		final String newservicerequest = "Alex SASHAZ";

		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";

		final String insurance = "Miami Beach Insurance";
		final String _label = "test";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickGeneralInfoEditButton();

		servicerequestslistpage.setServiceRequestGeneralInfo(teamname, assignedto, ponum, ronum);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(insurance);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.setServiceRequestLabel(_label);
		servicerequestslistpage.setServiceRequestDescription(_label);
		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(newservicerequest);
		// servicerequestslistpage.clickFindButton();

		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "OnHold");

		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage
				.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

		appointmentpopup.selectLocationType("Repair Location");
		appointmentpopup.selectLocation("VD_Location");
		appointmentpopup.clickAddAppointment();
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

		appointmentpopup.clickAddAppointment();
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(testName = "Test Case 24852:Operations: CLUser - it not possible to accept SR (option is not present)"
			+ "Test Case 24853:Operations: CLUser - it is possible to reject SR (option is present)", description = "Operations: CLUser - it not possible to accept SR (option is not present)"
					+ "Operations: CLUser - it is possible to reject SR (option is present)")
	public void testOperationsCLUserItNotPossibleToAcceptSR_OptionIsNotPresent() throws InterruptedException {

		final String addsrvalue = "SR_type_WO_auto_create";

		final String VIN = "1GNDU23E33D176859";
		final String _make = "Chevrolet";
		final String _model = "Venture";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		HomeWebPage homepage = backofficeheader.clickHomeLink();
		Thread.sleep(1000);
		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		Assert.assertFalse(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.rejectFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "Request Rejected");
	}

	@Test(testName = "Test Case 24854:Operations: CLUser - Verify that accepted SR is in read-only mode " +
            "(not possible to edit)", description = "Operations: CLUser - Verify that accepted SR is in read-only mode " +
            "(not possible to edit)", dataProvider = "getUserData", dataProviderClass = DataProviderPool.class)
	public void testOperationsCLUserVerifyThatAcceptedSRIsInReadOnlyMode_NotPossibleToEdit(String userName, String userPassword) throws InterruptedException {

		final String addsrvalue = "SR_type_WO_auto_create";

		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";

		final String anotherLogin = "zayats@cyberiansoft.com";
		final String anotherPassword = "1234567";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(anotherLogin, anotherPassword);
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		HomeWebPage homepage = backofficeheader.clickHomeLink();
		Thread.sleep(1000);
		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();

		backofficeheader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
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

		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		servicerequestslistpage.switchToServiceRequestInfoFrame();
        Assert.assertFalse(servicerequestslistpage.getGeneralInfoEditButton().isDisplayed());
		Assert.assertFalse(servicerequestslistpage.getCustomerEditButton().isDisplayed());
		Assert.assertFalse(servicerequestslistpage.getVehicleInfoEditButton().isDisplayed());
		servicerequestslistpage.clickCloseServiceRequestButton();
	}

    @Test(testName = "Test Case 24855:Operations: CLUser - it is not possible to add labels when create SR", description = "Operations: CLUser - it is not possible to add labels when create SR")
	public void testOperationsCLUserItNotPossibleToAddLabelsWhenCreateSR() throws InterruptedException {

		final String addsrvalue = "SR_type_WO_auto_create";

		final String VIN = "1GNDU23E33D176859";
		final String _make = "Chevrolet";
		final String _model = "Venture";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		HomeWebPage homepage = backofficeheader.clickHomeLink();
		Thread.sleep(1000);
		ServiceRequestsListWebPage servicerequestslistpage = homepage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		Assert.assertFalse(servicerequestslistpage.getServiceRequestLabelField().isDisplayed());

	}

    @Test(testName = "Test Case 26221:Operations: SR list - Verify that Check In Button is not present when create SR", description = "Operations: SR list - Verify that Check In Button is not present when create SR")
	public void testOperationsSRListVerifyThatCheckInButtonIsNotPresentWhenCreateSR() {

		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(customer);
		servicerequestslistpage.clickDoneButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertFalse(servicerequestslistpage.isCheckInButtonExistsForSelectedSR());
		servicerequestslistpage.rejectFirstServiceRequestFromList();
	}

    @Test(testName = "Test Case 26225:Operations: SR list - Verify that Check In button is appeared when SR is saved", description = "Operations: SR list - Verify that Check In button is appeared when SR is saved")
	public void testOperationsVerifyThatCheckInButtonIsAppearedWhenSRIsSaved() {

		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(customer);
		servicerequestslistpage.clickDoneButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertFalse(servicerequestslistpage.isCheckInButtonExistsForSelectedSR());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonExistsForSelectedSR());
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonVisible());
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(testName = "Test Case 26249:Operations: SR list - Verify that Check In button is changed to Undo Check In after pressing and vice versa", description = "Operations: SR list - Verify that Check In button is changed to Undo Check In after pressing and vice versa")
	public void testOperationsSRListVerifyThatCheckInButtonIsChangedToUndoCheckInAfterPressingAndViceVersa()
			throws InterruptedException {

		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(customer);
		servicerequestslistpage.clickDoneButton();
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertFalse(servicerequestslistpage.isCheckInButtonExistsForSelectedSR());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonExistsForSelectedSR());
		Assert.assertTrue(servicerequestslistpage.isCheckInButtonVisible());
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), "Check-In");
		servicerequestslistpage.clickCheckInButtonForSelectedSR();
		Thread.sleep(5000);
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), "Undo Check-In");
		servicerequestslistpage.clickCheckInButtonForSelectedSR();
		Thread.sleep(5000);
		Assert.assertEquals(servicerequestslistpage.getCheckInButtonValueForSelectedSR(), "Check-In");
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

    @Test(testName = "Test Case 56760:Operation - Service Request - Description in excisting SR", dataProvider = "provideSRdescription")
	public void testServiceRequestDescription(String description) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(description);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkTimeOfLastDescription());
	}

	@DataProvider
	public Object[][] provideSRdescription() {
		return new Object[][] { { "test description" } };
	}

    @Test(testName = "Test Case 56761:Operation - Service Request - Tags manipulation in new SR", dataProvider = "provideSRwholeInfo")
	public void testServiceRequest(String[] tags, String symbol) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.addTags(tags);
		Assert.assertTrue(serviceRequestsWebPage.addTags(tags[tags.length - 1]));
		serviceRequestsWebPage.addTags(symbol);
		Assert.assertTrue(serviceRequestsWebPage.removeFirtsTag());
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkTags(tags));
	}

	@DataProvider
	public Object[][] provideSRwholeInfo() {
		return new Object[][] { { new String[] { "tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7" }, "s" } };
	}

    @Test(testName = "Test Case 56760:Operation - Service Request - Description in excisting SR", dataProvider = "provideSomeDescriptions")
	public void testServiceRequestDesciptionInExistingSR(String[] descriptions) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(descriptions[0]);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.addNewDescriptionAndCheckOld(descriptions[1], descriptions[0]));

	}

	@DataProvider
	public Object[][] provideSomeDescriptions() {
		return new Object[][] { { new String[] { "test description1", "test description2" } } };
	}

    @Test(testName = "Test Case 56827:Operation - Service Request - Documents not shown during creation,"
			+ "Test Case 56828:Operation - Service Request - Answers not shown during creation")
	public void testShownSRDuringCreation() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		Assert.assertFalse(serviceRequestsWebPage.checkIfDescriptionIconsVisible());
	}

	@Test(testName = "Test Case 56756:Operation - Service Request - Description in new SR", dataProvider = "provideSomeDescriptions")
	public void testCreatingSRWithDifferentDescriptions(String[] descriptions) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(descriptions[0]);
		serviceRequestsWebPage.setServiceRequestDescription(descriptions[1]);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkServiceDescription(descriptions[1]));
	}

	@Test(testName = "Test Case 56829:Operation - Service Request - Check Documents")
	public void checkDescriptionDocument() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
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

	@Test(testName = "Test Case 56832:Operation - Service Request - Appointment - Add Multi Tech in SR",
            dataProvider = "provideSRdata")
	public void checkMultiTechInSR(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(
				serviceRequestsWebPage.checkDefaultAppointmentValuesAndAddAppointmentFomSREdit());
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
	}

	@Test(testName = "Test Case 56834:Operation - Service Request - Appointment - Multi Tech - show/hide tech", dataProvider = "provideSRdata")
	public void checkMultiTechInSRshowHideTech(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkShowHideTeches(startDate, endDate));
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
	}

	@Test(testName = "Test Case 56833:Operation - Service request - Appointment - Multi Tech in side scrollbar", dataProvider = "provideSRdata1")
	public void checkMultiTechInSideScrollbar(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
		Assert.assertTrue(
				serviceRequestsWebPage.checkDefaultAppointmentValuesFromCalendar(startDate, endDate, SRcustomer));
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
	}

	@DataProvider
	public Object[][] provideSRdata1() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String firstDate;
		String secondDate;
		if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
			firstDate = LocalDate.now().plusDays(3).format(formatter);
			secondDate = LocalDate.now().plusDays(4).format(formatter);
		} else if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			firstDate = LocalDate.now().plusDays(2).format(formatter);
			secondDate = LocalDate.now().plusDays(3).format(formatter);
		} else {
			firstDate = LocalDate.now().plusDays(1).format(formatter);
			secondDate = LocalDate.now().plusDays(2).format(formatter);
		}
		return new Object[][] { { "Alex SASHAZ", firstDate, secondDate, "OnHold", "Alex SASHAZ", "Scheduled" } };
	}

	@Test(testName = "Test Case 56835:Operation - Service Request - Appointment - Scheduler - Week", dataProvider = "provideSRdata")
	public void checkSRappointmentSchedulerWeek(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		int prevReqestsCount = serviceRequestsWebPage.checkSchedulerByDateWeek(startDate, isDateShifted);
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
	}

    @DataProvider
    public Object[][] provideSRdata() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String firstDate;
        String secondDate;
        boolean isDateShifted;
        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            firstDate = LocalDate.now().plusDays(3).format(formatter);
            secondDate = LocalDate.now().plusDays(4).format(formatter);
            isDateShifted = true;
        } else if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            firstDate = LocalDate.now().plusDays(2).format(formatter);
            secondDate = LocalDate.now().plusDays(3).format(formatter);
            isDateShifted = true;
        } else {
            firstDate = LocalDate.now().plusDays(1).format(formatter);
            secondDate = LocalDate.now().plusDays(2).format(formatter);
            isDateShifted = false;
        }

        return new Object[][] { { "Alex SASHAZ", firstDate, secondDate, "Scheduled", isDateShifted } };
    }

    @DataProvider
    public Object[][] provideSRdataForSchedulerMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return new Object[][] {{ "Alex SASHAZ", LocalDate.now().plusDays(1).format(formatter) }};
    }

	@Test(testName = "Test Case 56835:Operation - Service Request - Appointment - Scheduler - Month",
            dataProvider = "provideSRdataForSchedulerMonth")
	public void checkSRappointmentSchedulerMonth(String customer, String startDate) throws InterruptedException {
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationsPage.clickNewServiceRequestList();
		int prevRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(startDate);
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.reloadPage();
		int afterRequestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(startDate);
        Assert.assertEquals(1, afterRequestsCount - prevRequestsCount);
	}

	@Test(testName = "Test Case 56840:Operation - Service Request - Appointment - Scheduler - Multi Technicians filter of 5")
	public void checkSRappointmentSchedulerMultiTechniciansFilterOf5() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToMonthInScheduler();
		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
	}

	@Test(testName = "Test Case 56838:Operation - Service Request - Appointment - Scheduler - Technicians filter", dataProvider = "provideSRdata")
	public void checkSchedulerTechniciansFilter(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.goToMonthInScheduler();
		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
		serviceRequestsWebPage.aplyTechniciansFromScheduler();
		int countBeforeAnySelections = serviceRequestsWebPage.countSR();
		serviceRequestsWebPage.selectTechnicianFromSchedulerByIndex(0);
		serviceRequestsWebPage.aplyTechniciansFromScheduler();
	}

	@Test(testName = "Test Case 56841:Operation - Service Request - Appointment - Scheduler - Multi Technicians Reset",
            dataProvider = "provideSRdata")
	public void checkSRmultiTechReset(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.goToMonthInScheduler();
		Assert.assertTrue(serviceRequestsWebPage.checkTechniciansFromScheduler());
		Assert.assertTrue(serviceRequestsWebPage.checkIf5TechiciansIsMaximum());
		Assert.assertTrue(serviceRequestsWebPage.alpyAndCheck5TecniciansFromScheduler());
		serviceRequestsWebPage.resetAndCheckTecniciansFromScheduler();
	}

	@Test(testName = "Test Case 56839:Operation - Service Request - Appointment - Scheduler - Add Service Request", dataProvider = "provideSRdata")
	public void checkSRcreation(String customer, String startDate, String endDate, String status, boolean isDateShifted)
            throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
	}

	// @Test(testName = "Test Case 56837:Operation - Service Request -
	// Appointment - Scheduler - Timeline", dataProvider = "provideSRdata")
	public void checkSRtimeline(String customer, String startDate, String endDate, String status, boolean isDateShifted)
			throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.goToMonthInScheduler();
		serviceRequestsWebPage.goToTimeLine();
		int srCountBefore = serviceRequestsWebPage.countSRinTimelineByDate(startDate);
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.goToMonthInScheduler();
		serviceRequestsWebPage.goToTimeLine();
		int srCountAfter = serviceRequestsWebPage.countSRinTimelineByDate(startDate);
		Assert.assertTrue(srCountBefore != srCountAfter);
	}

	@Test(testName = "Test Case 57805:Operation - Service Request Life Cycle - No Entry")
	public void checkSRLCnoEntry() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		Assert.assertFalse(serviceRequestsWebPage.checkLifeCycleBTN());
	}

	@Test(testName = "Test Case 57874:Operation - Service Request Life Cycle - Appointment - Estimate", dataProvider = "provideSRdata")
	public void checkSRLCestimate(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentWithoutDescription(startDate, endDate);
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));
		Assert.assertFalse(serviceRequestsWebPage.checkLifeCycleDate());

	}

	@Test(testName = "Test Case 57806:Operation - Service Request Life Cycle - After Creation")
	public void checkSRLCafterCreation() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.checkLifeCycleContent());
		serviceRequestsWebPage.goToDocumentLinkFromLC();
		Assert.assertTrue(serviceRequestsWebPage.checkLifeCycleDocumentsContent());
		Assert.assertTrue(serviceRequestsWebPage.checkDocumentDownloadingInLC());
		Assert.assertTrue(serviceRequestsWebPage.clickAddImageBTN());
	//	serviceRequestsWebPage.addImage();
	}

	@Test(testName = "Test Case 57807:Operation - Service Request Life Cycle - WO Auto Creation", dataProvider = "provideSRdata")
	public void checkSRLCwoAutoCreation(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_Auto");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.goToWOfromLC());
	}

	@Test(testName = "Test Case 57875:Operation - Service Request Life Cycle - Approved", dataProvider = "provideSRdata1")
	public void checkSRLCapproved(String customer, String startDate, String endDate, String status, String SRcustomer,
			String newStatus) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.checkAcceptanceOfSRinLC());
	}

	@Test(testName = "Test Case 57879:Operation - Service Request Life Cycle - Rejected", dataProvider = "provideSRdata1")
	public void checkSRLCrejected(String customer, String startDate, String endDate, String status, String SRcustomer,
			String newStatus) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.checkRejectOfSRinLC());
	}

	@Test(testName = "Test Case 57879:Operation - Service Request Life Cycle - Closed", dataProvider = "provideSRdata1")
	public void checkSRLCclosed(String customer, String startDate, String endDate, String status, String SRcustomer,
			String newStatus) {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.makeSearchPanelVisible();
		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
		// serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
		// serviceRequestsWebPage.clickAddServiceRequestButton();
		// serviceRequestsWebPage.clickCustomerEditButton();
		// serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		// serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.clickVehicleEditButton();
		// serviceRequestsWebPage.setVehicleInfo("123" , "123");
		// serviceRequestsWebPage.clickDoneButton();
		// serviceRequestsWebPage.saveNewServiceRequest();
		// serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		// Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
		// serviceRequestsWebPage.selectFirstServiceRequestFromList();
		// serviceRequestsWebPage.goToLifeCycle();
		// Assert.assertTrue(serviceRequestsWebPage.checkClosedOfSRinLC());
	}

	@Test(testName = "Test Case 59700:Miscellaneous - Events: Service Request Accepted",
            dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestAccepted(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(
				serviceRequestsWebPage.checkEmails("Remainder") || serviceRequestsWebPage.checkEmails("was created"));

		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
	}

    @Test(testName = "Test Case 59700:Miscellaneous - Events:SR Created", dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsSRCreated(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#  was created")
				|| serviceRequestsWebPage.checkTestEmails());
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 31350:Miscellaneous - Events: Service Request Checked In",
            dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestCheckedIn(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Checked In");
		eventsWebPage.setAlertNewName("test appointment SR Checked In");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
		eventsWebPage.setEmailNotificationDropDownForSelected("ServiceRequest Checked In");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
        serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickCheckInButtonForSelectedSR();
		serviceRequestsWebPage.switchToServiceRequestInfoFrame();
		serviceRequestsWebPage.saveNewServiceRequest();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
		eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 31234:Miscellaneous - Events: Appointment Created",
            dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsAppointmentCreated(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Appointment Created");
		eventsWebPage.setAlertNewName("test appointment Appointment Created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment Appointment Created");
		eventsWebPage.setEmailNotificationDropDownForSelected("test appointment creation/fail");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate, "Automation 2 Appointment Tech");
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment Appointment Created");
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 31296:Miscellaneous - Events: Appointment Failed",
            dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsAppointmentFailed(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Appointment Failed");
		eventsWebPage.setAlertNewName("test appointment Appointment Failed");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment Appointment Failed");
		eventsWebPage.setEmailNotificationDropDownForSelected("test appointment creation/fail");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.addAppointmentFromSRlist(startDate, endDate, "Automation 2 Appointment Tech");
		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails() || serviceRequestsWebPage.checkEmails("was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment Appointment Failed");
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 59702:Miscellaneous - Events: Service Request Appointment Created",
            dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestAppointmentCreated(String customer, String startDate,
			String endDate, String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(
				serviceRequestsWebPage.checkTestEmails() || serviceRequestsWebPage.checkEmails("was not checked in"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 59701:Miscellaneous - Events: Service Request Accepted By Tech", dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestAcceptedByTech(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails()
				|| serviceRequestsWebPage.checkEmails("Service Request with RO#  was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 59703:Miscellaneous - Events: Service Request Estimation Created",
            dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestEstimationCreated(String customer, String startDate,
			String endDate, String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 59704:Miscellaneous - Events: Service Request Is Monitored", dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestIsMonitored(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Remainder") || serviceRequestsWebPage.checkTestEmails());
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		// eventsWebPage.deleteSelectedEvent();
	}

    @Test(testName = "Test Case 59705:Miscellaneous - Events: Service Request Order Created", dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestOrderCreated(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 59706:Miscellaneous - Events: Service Request Rejected", dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestRejected(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNotificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created")||serviceRequestsWebPage.checkTestEmails());
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		// eventsWebPage.deleteSelectedEvent();
	}

	@Test(testName = "Test Case 59636:Events: SR Check In", dataProvider = "provideSRdata1")
	public void testMiscellaneousEventsServiceRequestCheckIn(String customer, String startDate, String endDate,
			String status, String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Checked In");
		eventsWebPage.setAlertNewName("test appointment SR Checked In");
		Assert.assertTrue(eventsWebPage.saveNewEvent());
		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnician(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickCheckInButtonForSelectedSR();
		serviceRequestsWebPage.switchToServiceRequestInfoFrame();
		serviceRequestsWebPage.saveNewServiceRequest();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
	}

    @Test(testName = "Test Case 63581:Company - Service Request Type: Duplicate search Issue")
	public void testServiceRequestTypeDublicateSearchIssue() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickErrorWithBLockingRadioButton();
		serviceRequestTypesPage.selectStockRoVinOptions();
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		serviceRequestTypesPage.getTableRowWithServiceRequestType("01_Alex2SRT");
		String currentWindow = serviceRequestTypesPage.getCurrentWindow();
		ServiceRequestTypesVehicleInfoSettingsPage settingsPage = serviceRequestTypesPage
				.clickSettingsVehicleInfo("01_Alex2SRT");
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
		settingsPage.unselectCheckBox("VIN #");
		settingsPage.unselectCheckBox("Stock #");
		settingsPage.unselectCheckBox("RO #");
		settingsPage.clickUpdateButton();
		settingsPage.closeNewTab(currentWindow);
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
		operationspage = backofficeheader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		companyPage = backofficeheader.clickCompanyLink();
		serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickNoneRadioButton();
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		serviceRequestTypesPage.getTableRowWithServiceRequestType("01_Alex2SRT");
		currentWindow = serviceRequestTypesPage.getCurrentWindow();
		settingsPage = serviceRequestTypesPage.clickSettingsVehicleInfo("01_Alex2SRT");
		serviceRequestTypesPage.switchToSecondWindow(currentWindow);
		settingsPage.selectCheckBox("VIN #");
		settingsPage.selectCheckBox("Stock #");
		settingsPage.selectCheckBox("RO #");
		settingsPage.clickUpdateButton();
		settingsPage.closeNewTab(currentWindow);
	}

    @Test(testName = "Test Case 64129:Company - Service Request Type: Duplicate Notification RO")
	public void testServiceRequestTypeDublicateNotificationRO() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption("VIN");
		serviceRequestTypesPage.selectOption("RO");
		serviceRequestTypesPage.unselectOption("Stock");
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backofficeheader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		String randomRO = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(testName = "Test Case 64124:Company - Service Request Type: Duplicate Error VIN")
	public void testServiceRequestTypeDublicateErrorVIN() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption("RO");
		serviceRequestTypesPage.selectOption("VIN");
		serviceRequestTypesPage.unselectOption("Stock");
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backofficeheader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randomVIN = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(testName = "Test Case 64125:Company - Service Request Type: Duplicate Error RO")
	public void testServiceRequestTypeDublicateErrorRO() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption("VIN");
		serviceRequestTypesPage.selectOption("RO");
		serviceRequestTypesPage.unselectOption("Stock");
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backofficeheader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		String randomRO = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setRO(randomRO);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(testName = "Task 64149:Automate Test Case 64128:Company - Service Request Type: Duplicate Notification VIN" )
	public void testServiceRequestTypeDublicateNotificationVIN() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption("RO");
		serviceRequestTypesPage.selectOption("VIN");
		serviceRequestTypesPage.unselectOption("Stock");
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backofficeheader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randomVIN = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo("123", randomVIN);
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(testName = "Task 64147:Automate Test Case 64126:Company - Service Request Type: Duplicate Error STOCK")
	public void testServiceRequestTypeDublicateErrorStock() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption("VIN");
		serviceRequestTypesPage.selectOption("Stock");
		serviceRequestTypesPage.unselectOption("RO");
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backofficeheader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randonStock = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(testName = "Task 64148:Automate Test Case 64127:Company - Service Request Type: Duplicate Notification STOCK")
	public void testServiceRequestTypeDublicateNotificationStock() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices(), 2);
		serviceRequestsWebPage.scrollWindow("-300");
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companyPage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("01_Alex2SRT");
		serviceRequestTypesPage.openGeneralSettingsTab();
		serviceRequestTypesPage.clickWarningOnlyRadioButton();
		serviceRequestTypesPage.unselectOption("RO");
		serviceRequestTypesPage.selectOption("Stock");
		serviceRequestTypesPage.unselectOption("VIN");
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		operationspage = backofficeheader.clickOperationsLink();
		serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		String randonStock = Integer.toString(new Random().nextInt());
		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();

		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setVehicleInfo(randonStock, "123");
		serviceRequestsWebPage.clickDoneButton();
		Assert.assertTrue(serviceRequestsWebPage.saveNewServiceRequest());
	}

    @Test(testName = "Test Case 66190:Operation - Service Request - Undo Rejected")
	public void testServicerequestUndoReject() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage serviceRequestTypesPage = companypage.clickServiceRequestTypesLink();
		serviceRequestTypesPage.clickEditServiceRequestType("Vit_All_Services");
		Assert.assertTrue(serviceRequestTypesPage.isAllowUndoRejectChecked());
		serviceRequestTypesPage.clickEditServiceRequestTypeOkButton();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.makeSearchPanelVisible();
		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue("Vit_All_Services");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();		
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Default team", "Vitaliy Kupchynskyy", "D525", "Dfg 25");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Alex SASHAZ");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleInforEditButton();
		serviceRequestsWebPage.setServiceRequestVIN("1HGCG55691A267167");
		serviceRequestsWebPage.decodeAndVerifyServiceRequestVIN("Honda", "Accord");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickClaimInfoEditButton();
		serviceRequestsWebPage.selectServiceRequestInsurance("Oranta");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.setServiceRequestLabel("test");
		serviceRequestsWebPage.setServiceRequestDescription("test");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.makeSearchPanelVisible();
		serviceRequestsWebPage.setSearchFreeText("Alex SASHAZ");
		serviceRequestsWebPage.clickFindButton();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickRejectUndoButton();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
	}

    //todo fails needs to be discussed with Sasha Zakaulov. п. 6 "Select 001 - Test Company and click Client Users". Where are Client Users?
    @Test(testName = "Test Case 65611:Operation - Service Request - Adviser Listing")
	public void testServicerequestAdviserListing() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("001 - Test Company");
		Assert.assertTrue(serviceRequestsWebPage.checkPresenceOfServiceAdvisorsByFilter("tes"));
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertEquals(serviceRequestsWebPage.getkServiceAdvisorName() , "_TesW _TesW");
	}
	
	//TODO
	//@Test(testName = "Test Case 65521:Operation - Service Request - Services add notes")
	public void testServicerequestServicesAddNotes() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestList();
		serviceRequestsWebPage.selectAddServiceRequestsComboboxValue("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		serviceRequestsWebPage.addServicesToServiceRequest("Zak_Money_Multiple","Zak_Labor_Multiple");
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertTrue(serviceRequestsWebPage.checkAddedServices("Zak_Money_Multiple","Zak_Labor_Multiple"));
	}
}
