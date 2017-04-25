package com.cyberiansoft.test.bo.testcases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.HomeWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.SRAppointmentInfoPopup;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;

public class BackOfficeOperationsServiceRequestsTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		try{
			webdriver.switchTo().alert().accept();
		}
			catch(NoAlertPresentException e){
				
		}
		
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		backofficeheader.clickLogout();
	}
	
	@Test(testName = "Test Case 25584:Operation - New service request - Appointment - Retail", description = "Operation - New service request - Appointment - Retail")
	public void testOperationNewServiceRequestAppointmentRetail() throws InterruptedException {

		final String teamname= "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";
		
		final String newservicerequest= "Alex SASHAZ";
		
		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";
		
		final String insurance = "Oranta";
		final String _label = "test";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
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
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
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
		servicerequestslistpage.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
	}
	
	@Test(testName = "Test Case 25589:Operation - New service request - Appointment - Wholesale", description = "Operation - New service request - Appointment - Wholesale")
	public void testOperationNewServiceRequestAppointmentWholesale() throws InterruptedException {

		final String teamname= "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";
		
		final String newservicerequest= "006 - Test Company";
		
		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";
		
		final String insurance = "Oranta";
		final String _label = "test";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
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
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getSubjectValue(), "Dfg 25");
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), "Johon Connor");
		Assert.assertEquals(appointmentpopup.getTechnicianFieldValue(), "All");
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
	}
	
	@Test(testName = "Test Case 26164:Operation - New service request - Appointment - Location Type: Custom", description = "Operation - New service request - Appointment - Location Type: Custom")
	public void testOperationNewServiceRequestAppointmentLocationTypeCustom() throws InterruptedException {

		final String teamname= "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";
		
		final String newservicerequest= "Alex SASHAZ";
		
		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";
		
		final String insurance = "Oranta";
		final String _label = "test";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
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
		
		
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		
		appointmentpopup.setFromDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getDayAfterTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		
		appointmentpopup.selectLocationType("Custom");
		appointmentpopup.setClientAddressValue("101 Main Road.,");
		appointmentpopup.setClientCityValue("Chicago");
		appointmentpopup.setClientZipValue("20002");
		appointmentpopup.clickAddAppointment();
		
		servicerequestslistpage.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		Thread.sleep(1000);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(2000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "101 Main Road.,");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "Chicago");
		Assert.assertEquals(appointmentpopup.getClientZipValue(), "20002");
		appointmentpopup.clickAddAppointment();
		appointmentpopup.waitABit(2000);
		servicerequestslistpage.closeFirstServiceRequestFromTheList();		
	}
	
	@Test(testName = "Test Case 26165:Operation - New service request - Appointment - Location Type: Customer", description = "Operation - New service request - Appointment - Location Type: Customer")
	public void testOperationNewServiceRequestAppointmentLocationTypeCustomer() throws InterruptedException {

		final String teamname= "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";
		
		final String newservicerequest= "Alex SASHAZ";
		
		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";
		
		final String insurance = "Oranta";
		final String _label = "test";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
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
		
		
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		
		appointmentpopup.selectLocationType("Customer");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(2000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(2000);
		servicerequestslistpage.closeFirstServiceRequestFromTheList();		
	}
	
	@Test(testName = "Test Case 26168:Operation - New service request - Appointment - Location Type: Owner", description = "Operation - New service request - Appointment - Location Type: Owner")
	public void testOperationNewServiceRequestAppointmentLocationTypeOwner() throws InterruptedException {

		final String teamname= "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";
		
		final String newservicerequest= "Alex SASHAZ";
		
		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";
		
		final String insurance = "Oranta";
		final String _label = "test";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
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
		
		
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		
		appointmentpopup.selectLocationType("Owner");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "407 SILVER SAGE DR.");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "NewYork");
		Assert.assertEquals(appointmentpopup.getClientZipValue(), "10001");
		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		appointmentpopup.clickAddAppointment();
		appointmentpopup.waitABit(2000);
		servicerequestslistpage.closeFirstServiceRequestFromTheList();		
	}
	
	@Test(testName = "Test Case 26172:Operation - New service request - Appointment - Location Type: Repair Location", description = "Operation - New service request - Appointment - Location Type: Repair Location")
	public void testOperationNewServiceRequestAppointmentLocationTypeRepairLocation() throws InterruptedException {

		final String teamname= "Default team";
		final String addsrvalue = "Vit_All_Services";
		final String assignedto = "Vitaliy Kupchynskyy";
		final String ponum = "D525";
		final String ronum = "Dfg 25";
		
		final String newservicerequest= "Alex SASHAZ";
		
		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";
		
		final String insurance = "Oranta";
		final String _label = "test";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
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
		//servicerequestslistpage.clickFindButton();
		
		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "OnHold");
		
		
		SRAppointmentInfoPopup appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		appointmentpopup.setFromDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setToDateValue(BackOfficeUtils.getTomorrowDateFormatted());
		appointmentpopup.setStartTimeValue("8:00 AM");
		appointmentpopup.setEndTimeValue("8:40 AM");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getTechnicianValue(), assignedto);
		String appointmentfromdate = appointmentpopup.getFromDateValue();
		String appointmentstarttime = appointmentpopup.getStartTimeValue();
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		
		appointmentpopup.selectLocationType("Repair Location");
		Thread.sleep(1000);
		appointmentpopup.selectLocation("VD_Location");
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientCountryValue().trim(), "Ukraine");
		Assert.assertEquals(appointmentpopup.getClientStateValue(), "Kyiv");
		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "227 street");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "mercedes");
		Assert.assertEquals(appointmentpopup.getClientZipValue(), "02222");
		appointmentpopup.clickAddAppointment();
		Thread.sleep(1000);
		servicerequestslistpage.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);
		appointmentpopup = servicerequestslistpage.clickAddAppointmentToFirstServiceRequestFromList();
		Thread.sleep(1000);
		Assert.assertEquals(appointmentpopup.getClientInfoNameValue(), newservicerequest);
		
		Assert.assertEquals(appointmentpopup.getClientInfoAddressValue(), "407 SILVER SAGE DR., NewYork, 10001");
		Assert.assertEquals(appointmentpopup.getClientInfoPhoneValue(), "14043801674");
		Assert.assertEquals(appointmentpopup.getClientInfoEmailValue(), "ALICIA.VILLALOBOS@KCC.COM");
		Assert.assertEquals(appointmentpopup.getClientCountryValue().trim(), "Ukraine");
		Assert.assertEquals(appointmentpopup.getClientStateValue(), "Kyiv");
		Assert.assertEquals(appointmentpopup.getClientAddressValue(), "227 street");
		Assert.assertEquals(appointmentpopup.getClientCityValue(), "mercedes");
		Assert.assertEquals(appointmentpopup.getClientZipValue(), "02222");
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
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);	
		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
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
	
	@Test(testName = "Test Case 24854:Operations: CLUser - Verify that accepted SR is in read-only mode (not possible to edit)", 
			description = "Operations: CLUser - Verify that accepted SR is in read-only mode (not possible to edit)")
	@Parameters({ "user.name", "user.psw" })
	public void testOperationsCLUserVerifyThatAcceptedSRIsInReadOnlyMode_NotPossibleToEdit(String userName, String userPassword) throws InterruptedException {
	
		final String addsrvalue = "SR_type_WO_auto_create";
		
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);	
		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
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
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		servicerequestslistpage = operationspage.clickNewServiceRequestLink();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		Assert.assertTrue(servicerequestslistpage.isAcceptIconPresentForFirstServiceRequestFromList());
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getStatusOfFirstServiceRequestFromList(), "Scheduled");
		
		
		backofficeheader.clickLogout();
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);

		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
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
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);	
		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("zayats@cyberiansoft.com", "1234567");
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
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
	
	@Test(testName = "Test Case 26221:Operations: SR list - Verify that Check In Button is not present when create SR", 
			description = "Operations: SR list - Verify that Check In Button is not present when create SR")
	public void testOperationsSRListVerifyThatCheckInButtonIsNotPresentWhenCreateSR() throws InterruptedException {

		
		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);	

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
	
	@Test(testName = "Test Case 26225:Operations: SR list - Verify that Check In button is appeared when SR is saved", 
			description = "Operations: SR list - Verify that Check In button is appeared when SR is saved")
	public void testOperationsVerifyThatCheckInButtonIsAppearedWhenSRIsSaved() throws InterruptedException {

		
		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);	

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
	
	@Test(testName = "Test Case 26249:Operations: SR list - Verify that Check In button is changed to Undo Check In after pressing and vice versa", 
			description = "Operations: SR list - Verify that Check In button is changed to Undo Check In after pressing and vice versa")
	public void testOperationsSRListVerifyThatCheckInButtonIsChangedToUndoCheckInAfterPressingAndViceVersa() throws InterruptedException {

		
		final String addsrvalue = "Type_for_Check_In_ON";
		final String customer = "002 - Test Company";
		final String VIN = "1GC5KXBG1AZ501950";
		final String _make = "Chevrolet";
		final String _model = "Silverado 2500HD";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);	

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

}
