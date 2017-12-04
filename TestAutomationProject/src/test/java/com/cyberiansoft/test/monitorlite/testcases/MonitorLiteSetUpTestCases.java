package com.cyberiansoft.test.monitorlite.testcases;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListWebPage;
import com.cyberiansoft.test.monitorlite.config.MonitorLiteConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextRepairOrdersWebPage;

public class MonitorLiteSetUpTestCases extends MonitorLiteBaseTestCase {
	
	@BeforeMethod
	public void BackOfficeLogin() throws InterruptedException {
		webdriverGotoWebPage(MonitorLiteConfigInfo.getInstance().getBackOfficeReconProURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserName(), 
				MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserPassword());
	}

	//@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		try {
			webdriver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {

		}
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		try{
			backofficeheader.clickLogout();
		}catch(Exception e){
			backofficeheader.refresh();
			backofficeheader.clickLogout();
		}
	}

	@Test(testName = "Test Case 25584:Operation - New service request - Appointment - Retail", description = "Operation - New service request - Appointment - Retail")
	public void testOperationNewServiceRequestAppointmentRetail() throws InterruptedException {

		final String srCustomer = "Test";
		final String VIN = "1GNEK13R4TJ423282";

		final String[] services = { "Glass Repair", "Bumper Repair" };
		final String[] servicesprices = { "100", "150" };
		final String servicesquantity = "1";
		final String woActivePhaseStatus = "Not Started";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(srCustomer);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		//servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.addServicesToServiceRequest(services);
		for (int i = 0; i < services.length; i++)
			servicerequestslistpage.setServiePriceAndQuantity(services[i], servicesprices[i], servicesquantity);
		
		servicerequestslistpage.saveNewServiceRequest();
		
		String srNumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		servicerequestslistpage.getWOForFirstServiceRequestFromList();
		
		String srWONumber = servicerequestslistpage.getWOForServiceRequestFromList(srNumber);

		webdriverGotoWebPage(MonitorLiteConfigInfo.getInstance().getBackOfficeMonitorLiteURL());
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserName(), 
				MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		
		VNextRepairOrdersWebPage repairorderspage = leftmenu.selectRepairOrdersMenu();
		repairorderspage.searchRepairOrderByNumber(srWONumber);
		
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(srWONumber));
		Assert.assertEquals(repairorderspage.getWorkOrderActivePhaseValue(srWONumber), woActivePhaseStatus);
		
		
		/*servicerequestslistpage.makeSearchPanelVisible();		
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
				.isFirstServiceRequestFromListHasAppointment(appointmentfromdate + " " + appointmentstarttime);*/
	}

}
