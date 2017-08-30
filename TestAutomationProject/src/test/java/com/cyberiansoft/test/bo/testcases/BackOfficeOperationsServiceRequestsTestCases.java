package com.cyberiansoft.test.bo.testcases;

import java.awt.AWTException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.EventsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.HomeWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.MiscellaneousWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.SRAppointmentInfoPopup;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.Retry;
import com.cyberiansoft.test.ios_client.utils.MailChecker;

public class BackOfficeOperationsServiceRequestsTestCases extends BaseTestCase {

	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl, String userName, String userPassword) {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
	}

	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		try {
			webdriver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {

		}
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		backofficeheader.clickLogout();
	}

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

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
		servicerequestslistpage.selectServiceRequesInsurance(insurance);
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

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
		servicerequestslistpage.selectServiceRequesInsurance(insurance);
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

	@Test(testName = "Test Case 26164:Operation - New service request - Appointment - Location Type: Custom", description = "Operation - New service request - Appointment - Location Type: Custom", retryAnalyzer = Retry.class)
	public void testOperationNewServiceRequestAppointmentLocationTypeCustom() throws InterruptedException {

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

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
		servicerequestslistpage.selectServiceRequesInsurance(insurance);
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
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

//		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, CA, 10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
//		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");

		appointmentpopup.selectLocationType("Custom");
		appointmentpopup.setClientAddressValue("407 SILVER SAGE DR.");
		appointmentpopup.setClientCityValue("NewYork");
		appointmentpopup.setClientZipValue("20002");
		appointmentpopup.clickAddAppointment();

		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		Thread.sleep(1000);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(2000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
		Assert.assertTrue(appointmentpopup.getClientZipValue().equals("20002")
				|| appointmentpopup.getClientZipValue().equals("10001"));
		appointmentpopup.clickAddAppointment();
		appointmentpopup.waitABit(2000);
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

	@Test(testName = "Test Case 26165:Operation - New service request - Appointment - Location Type: Customer", description = "Operation - New service request - Appointment - Location Type: Customer")
	public void testOperationNewServiceRequestAppointmentLocationTypeCustomer() throws InterruptedException {

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

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
		servicerequestslistpage.selectServiceRequesInsurance(insurance);
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

//		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, CA, 10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
//		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");

		appointmentpopup.selectLocationType("Customer");
		Thread.sleep(1000);
//		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
//		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
//		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(2000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

//		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
//		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
//		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
//		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(2000);
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

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
		servicerequestslistpage.selectServiceRequesInsurance(insurance);
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

//		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, CA, 10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
//		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");

		appointmentpopup.selectLocationType("Owner");
		Thread.sleep(1000);
//		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
//		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
//		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

//		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
//		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
//		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
//		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		appointmentpopup.clickAddAppointment();
		appointmentpopup.waitABit(2000);
		servicerequestslistpage.closeFirstServiceRequestFromTheList();
	}

	@Test(testName = "Test Case 26172:Operation - New service request - Appointment - Location Type: Repair Location", description = "Operation - New service request - Appointment - Location Type: Repair Location")
	public void testOperationNewServiceRequestAppointmentLocationTypeRepairLocation() throws InterruptedException {

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

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
		servicerequestslistpage.selectServiceRequesInsurance(insurance);
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
		appointmentpopup.waitABit(3000);
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

//		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
//		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");

		appointmentpopup.selectLocationType("Repair Location");
		appointmentpopup.waitABit(1000);
		appointmentpopup.selectLocation("VD_Location");
		appointmentpopup.waitABit(1000);
//		Assert.assertEquals(appointmentpopup.getClientCountryValue().trim(), "Ukraine");
//		Assert.assertEquals(appointmentpopup.getClientStateValue(), "Kyiv");
//		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "227 street");
//		Assert.assertEquals(appointmentpopup.getClientCityValue(), "mercedes");
//		Assert.assertEquals(appointmentpopup.getClientZipValue(), "02222");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);

//		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
//		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
//		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
//		Assert.assertTrue(appointmentpopup.getClientCountryValue().trim().equals("Ukraine")||
//				appointmentpopup.getClientCountryValue().trim().equals("United States"));
//		Assert.assertTrue(appointmentpopup.getClientStateValue().equals("Kyiv")
//		|| appointmentpopup.getClientStateValue().equals("All"));
//		Assert.assertTrue(appointmentpopup.getClientAddressValue().equals("227 street")
//				|| appointmentpopup.getClientAddressValue().equals("407 SILVER SAGE DR., NewYork, 10001"));
//		Assert.assertEquals(appointmentpopup.getClientCityValue(), "mercedes");
//		Assert.assertEquals(appointmentpopup.getClientZipValue(), "02222");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
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

	@Test(testName = "Test Case 24854:Operations: CLUser - Verify that accepted SR is in read-only mode (not possible to edit)", description = "Operations: CLUser - Verify that accepted SR is in read-only mode (not possible to edit)")
	@Parameters({ "user.name", "user.psw" })
	public void testOperationsCLUserVerifyThatAcceptedSRIsInReadOnlyMode_NotPossibleToEdit(String userName,
			String userPassword) throws InterruptedException {

		final String addsrvalue = "SR_type_WO_auto_create";

		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";

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

		backofficeheader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		servicerequestslistpage = operationspage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "Scheduled");

		backofficeheader.clickLogout();
		loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);

		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
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
	public void testOperationsSRListVerifyThatCheckInButtonIsNotPresentWhenCreateSR() throws InterruptedException {

		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
	public void testOperationsVerifyThatCheckInButtonIsAppearedWhenSRIsSaved() throws InterruptedException {

		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
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
	public void testServiceRequestdescription(String description) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
	public void testServiceRequest(String[] tags, String symbol) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
	public void testServiceRequestDesciptionInExistingSR(String[] descriptions) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		Assert.assertFalse(serviceRequestsWebPage.checkIfDescriptionIconsVisible());
	}

	@Test(testName = "Test Case 56756:Operation - Service Request - Description in new SR", dataProvider = "provideSomeDescriptions")
	public void testCreatingSRWithDifferentDescriptions(String[] descriptions) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.setServiceRequestDescription(descriptions[0]);
		serviceRequestsWebPage.setServiceRequestDescription(descriptions[1]);
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkServiceDescription(descriptions[1]));
	}

	@Test(testName = "Test Case 56829:Operation - Service Request - Check Documents")
	public void checkDescriptionDocument() throws AWTException, InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkServiceRequestDocumentIcon());
		serviceRequestsWebPage.clickDocumentButton();
		Assert.assertTrue(serviceRequestsWebPage.checkElementsInDocument());
		Assert.assertTrue(serviceRequestsWebPage.clickAddImageBTN());
		serviceRequestsWebPage.addImage();
//		Assert.assertTrue(serviceRequestsWebPage.checkPresentanceOFAddedFile());
//		Assert.assertTrue(serviceRequestsWebPage.checkDeletionOfFile());
	}

	@Test(testName = "Test Case 56832:Operation - Service Request - Appointment - Add Multi Tech in SR", dataProvider = "provideSRdata")
	public void checkMultiTechInSR(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
				serviceRequestsWebPage.checkDefaultAppointmentValuesAndaddAppointmentFomSREdit(startDate, endDate));
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(status));

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

	@Test(testName = "Test Case 56834:Operation - Service Request - Appointment - Multi Tech - show/hide tech", dataProvider = "provideSRdata", retryAnalyzer = Retry.class)
	public void checkMultiTechInSRshowHideTech(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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

	@Test(testName = "Test Case 56833:Operation - Service request - Appointment - Multi Tech in side scrollbar", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void checkMultiTechInSideScrollbar(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		int prevReqestsCount = serviceRequestsWebPage.checkSchedulerByDateWeek(startDate, isDateShifted);
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
	}

	@Test(testName = "Test Case 56835:Operation - Service Request - Appointment - Scheduler - Month", dataProvider = "provideSRdata")
	public void checkSRappointmentSchedulerMonth(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		int prevReqestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(startDate);
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
		int afterReqestsCount = serviceRequestsWebPage.checkSchedulerByDateMonth(startDate);
		Assert.assertTrue(afterReqestsCount - prevReqestsCount == 1);
	}

	@Test(testName = "Test Case 56840:Operation - Service Request - Appointment - Scheduler - Multi Technicians filter of 5", dataProvider = "provideSRdata")
	public void checkSRappointmentSchedulerMultiTechniciansFilterOf5(String customer, String startDate, String endDate,
			String status, boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
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

	@Test(testName = "Test Case 56841:Operation - Service Request - Appointment - Scheduler - Multi Technicians Reset", dataProvider = "provideSRdata")
	public void checkSRmultiTechReset(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
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
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.setSuggestedStartDate(startDate);
		Assert.assertTrue(serviceRequestsWebPage.checkDefaultAppointmentDateFromSRedit(startDate));
	}

	// @Test(testName = "Test Case 56837:Operation - Service Request -
	// Appointment - Scheduler - Timeline", dataProvider = "provideSRdata")
	public void checkSRtimeline(String customer, String startDate, String endDate, String status, boolean isDateShifted)
			throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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

	@Test(testName = "Test Case 57805:Operation - Service Request Life Cycle - No Entry", dataProvider = "provideSRdata")
	public void checkSRLCnoEntry(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.clickAddServiceRequestButton();
		Assert.assertFalse(serviceRequestsWebPage.checkLifeCycleBTN());
	}

	@Test(testName = "Test Case 57874:Operation - Service Request Life Cycle - Appointment - Estimate", dataProvider = "provideSRdata")
	public void checkSRLCestimate(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
	
	@Test(testName = "Test Case 57806:Operation - Service Request Life Cycle - After Creation", dataProvider = "provideSRdata")
	public void checkSRLCafterCreation(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException, AWTException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
		serviceRequestsWebPage.addImage();
//		Assert.assertTrue(serviceRequestsWebPage.checkPresentanceOFAddedFile());
//		Assert.assertTrue(serviceRequestsWebPage.checkDeletionOfFile());
	}
	
	@Test(testName = "Test Case 57807:Operation - Service Request Life Cycle - WO Auto Creation", dataProvider = "provideSRdata")
	public void checkSRLCwoAutoCreation(String customer, String startDate, String endDate, String status,
			boolean isDateShifted) throws InterruptedException, AWTException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.goToSRmenu();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_Auto");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo("123" , "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.goToWOfromLC());
	}
	
	@Test(testName = "Test Case 57875:Operation - Service Request Life Cycle - Approved", dataProvider = "provideSRdata1")
	public void checkSRLCapproved(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo("123" , "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.checkAcceptanceOfSRinLC());
	}
	
	@Test(testName = "Test Case 57879:Operation - Service Request Life Cycle - Rejected", dataProvider = "provideSRdata1")
	public void checkSRLCrejected(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickVehicleEditButton();
		serviceRequestsWebPage.setVehicleInfo("123" , "123");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.goToLifeCycle();
		Assert.assertTrue(serviceRequestsWebPage.checkRejectOfSRinLC());
	}
	
	@Test(testName = "Test Case 57879:Operation - Service Request Life Cycle - Closed", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void checkSRLCclosed(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.makeSearchPanelVisible();
		Assert.assertTrue(serviceRequestsWebPage.checkSRsearchCriterias());
//		serviceRequestsWebPage.selectAddServiceRequestDropDown("Oleksa_AcceptanceAndAllRequired");
//		serviceRequestsWebPage.clickAddServiceRequestButton();
//		serviceRequestsWebPage.clickCustomerEditButton();
//		serviceRequestsWebPage.selectServiceRequestCustomer(customer);
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.clickVehicleEditButton();
//		serviceRequestsWebPage.setVehicleInfo("123" , "123");
//		serviceRequestsWebPage.clickDoneButton();
//		serviceRequestsWebPage.saveNewServiceRequest();
//		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
//		Assert.assertTrue(serviceRequestsWebPage.checkStatus(newStatus));
//		serviceRequestsWebPage.selectFirstServiceRequestFromList();
//		serviceRequestsWebPage.goToLifeCycle();
//		Assert.assertTrue(serviceRequestsWebPage.checkClosedOfSRinLC());
	}
	
	@Test(testName = "Test Case 59700:Miscellaneous - Events: Service Request Accepted", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestAccepted(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Remainder")||
				serviceRequestsWebPage.checkEmails("was created"));
		
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 59700:Miscellaneous - Events:SR Created", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsSRCreated(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#  was created")||
				serviceRequestsWebPage.checkTestEmails());
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 31350:Miscellaneous - Events: Service Request Checked In", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestCheckedIn(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Checked In");
		eventsWebPage.setAlertNewName("test appointment SR Checked In");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
		eventsWebPage.setEmailNototificationDropDownForSelected("ServiceRequest Checked In");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		serviceRequestsWebPage.selectFirstServiceRequestFromList();
		serviceRequestsWebPage.clickCheckInButtonForSelectedSR();
		serviceRequestsWebPage.selectSREditFrame();
		serviceRequestsWebPage.saveNewServiceRequest();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Service Request with RO#"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 31234:Miscellaneous - Events: Appointment Created", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsAppointmentCreated(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Appointment Created");
		eventsWebPage.setAlertNewName("test appointment Appointment Created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment Appointment Created");
		eventsWebPage.setEmailNototificationDropDownForSelected("test appointment creation/fail");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 31296:Miscellaneous - Events: Appointment Failed", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsAppointmentFailed(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Appointment Failed");
		eventsWebPage.setAlertNewName("test appointment Appointment Failed");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment Appointment Failed");
		eventsWebPage.setEmailNototificationDropDownForSelected("test appointment creation/fail");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
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
		eventsWebPage.selectEventRowByName("test appointment Appointment Failed");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 59702:Miscellaneous - Events: Service Request Appointment Created", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestAppointmentCreated(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails() ||serviceRequestsWebPage.checkEmails("was not checked in"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	@Test(testName = "Test Case 59701:Miscellaneous - Events: Service Request Accepted By Tech", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestAcceptedByTech(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkTestEmails()||
				serviceRequestsWebPage.checkEmails("Service Request with RO#  was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 59703:Miscellaneous - Events: Service Request Estimation Created", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestEstimationCreated(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 59704:Miscellaneous - Events: Service Request Is Monitored", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestIsMonitored(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("Remainder")||
				serviceRequestsWebPage.checkTestEmails());
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 59705:Miscellaneous - Events: Service Request Order Created", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestOrderCreated(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 59706:Miscellaneous - Events: Service Request Rejected", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestRejected(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Created");
		eventsWebPage.setAlertNewName("test appointment SR created");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR created");
		eventsWebPage.setEmailNototificationDropDownForSelected("My Service Requests");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
		serviceRequestsWebPage.saveNewServiceRequest();
		serviceRequestsWebPage.rejectFirstServiceRequestFromList();
		Assert.assertTrue(serviceRequestsWebPage.checkEmails("was created"));
		miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.selectEventRowByName("test appointment SR created");
//		eventsWebPage.deleteSelectedEvent();
	}
	
	@Test(testName = "Test Case 59636:Events: SR Check In", dataProvider = "provideSRdata1", retryAnalyzer = Retry.class)
	public void testMiscellaneousEventsServiceRequestCheckIn(String customer, String startDate, String endDate, String status,
			String SRcustomer, String newStatus) throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MiscellaneousWebPage miscellaneouspage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsWebPage = miscellaneouspage.clickEventsLink();
		eventsWebPage.clickAddEventButton();
		eventsWebPage.selectEvent("Service Request Checked In");
		eventsWebPage.setAlertNewName("test appointment SR Checked In");
		eventsWebPage.saveNewEvent();
		eventsWebPage.selectEventRowByName("test appointment SR Checked In");
		eventsWebPage.setEmailNotificationCheckBoxForSelected();
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("Zak_Request_Type");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickCustomerEditButton();
		serviceRequestsWebPage.selectServiceRequestCustomer("Automation Wholesale");
		serviceRequestsWebPage.clickDoneButton();
		serviceRequestsWebPage.clickGeneralInfoEditButton();
		serviceRequestsWebPage.setServiceRequestGeneralInfo("Automation1 Primary  Tech");
		serviceRequestsWebPage.addAppointmentWithTechnisian(startDate, endDate, "Automation 2 Appointment Tech");
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
	
	//@Test(testName = "Test Case 63581:Company - Service Request Type: Duplicate search Issue")
	public void testServiceRequestTypeDublicateSearchIssue(){
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage serviceRequestsWebPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsWebPage.selectAddServiceRequestDropDown("01_Alex2SRT");
		serviceRequestsWebPage.clickAddServiceRequestButton();
		serviceRequestsWebPage.clickServiceEditButton();
		Assert.assertEquals(serviceRequestsWebPage.countAvailableServices() , 2);
		CompanyWebPage companyPage = backofficeheader.clickCompanyLink();
		ServicePackagesWebPage servicePackagePage = companyPage.clickServicePackagesLink();
		servicePackagePage.clickEditServicePackage("01_Alex2SRT");
	}
}
