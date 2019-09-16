package com.cyberiansoft.test.monitorlite.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.monitorlite.config.MonitorLiteConfigInfo;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;

public class MonitorLiteSetUpTestCases extends MonitorLiteBaseTestCase {
	
	@BeforeMethod
	public void BackOfficeLogin() {
		DriverBuilder.getInstance().setDriver(BrowserType.CHROME);
		webdriver = DriverBuilder.getInstance().getDriver();
		//webdriver.navigate().refresh();
		webdriverGotoWebPage(MonitorLiteConfigInfo.getInstance().getBackOfficeReconProURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.userLogin(MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserName(),
				MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserPassword());
	}

	@AfterMethod
	public void BackOfficeLogout() {
		//webdriver.manage().deleteAllCookies();
		webdriver.quit();
		/*try {
			webdriver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {

		}
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		try{
			backofficeheader.clickLogout();
		}catch(Exception e){
			backofficeheader.refresh();
			backofficeheader.clickLogout();
		}*/
	}

	@Test(testName = "Test Case 68402:MonitorLite: Create SR on classic BO, "
			+ "Test Case 68404:Repair Orders: Start WO on the List", 
			description = "MonitorLite: Create SR on classic BO, "
					+ "Repair Orders: Start WO on the List")
	public void testRepairOrdersStartWOOnTheList() {

		final String srCustomer = "Test";
		final String VIN = "1GNEK13R4TJ423282";

		final String[] services = { "Glass Repair", "Bumper Repair" };
		final String[] servicesprices = { "100", "150" };
		final String servicesquantity = "1";
		final String woActivePhaseStatus = "Not Started";
		final String woActivePhaseStatusNEW = "QC";
		final String woDaysInProgress = "Days in progress 0";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = new OperationsWebPage(webdriver);
		backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = new ServiceRequestsListWebPage(webdriver);
		operationspage.clickNewServiceRequestList();
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
		
		VNextBOROWebPage repairorderspage = leftmenu.selectRepairOrdersMenu();
		repairorderspage.searchRepairOrderByNumber(srWONumber);
		
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(srWONumber));
		Assert.assertEquals(repairorderspage.getWorkOrderActivePhaseValue(srWONumber), woActivePhaseStatus);
		repairorderspage.clickStartRoForWorkOrder(srWONumber);
		Assert.assertEquals(repairorderspage.getWorkOrderActivePhaseValue(srWONumber), woActivePhaseStatusNEW);
		Assert.assertEquals(repairorderspage.getWorkOrderDaysInProgressValue(srWONumber), woDaysInProgress);
		
	}
	
	@Test(testName = "Test Case 68405:Repair Orders: Complete WO on the List", 
			description = "Repair Orders: Complete WO on the List")
	public void testRepairOrdersCompleteWOOnTheList() {

		final String srCustomer = "Test";
		final String VIN = "1GNEK13R4TJ423282";

		final String[] services = { "Glass Repair", "Bumper Repair" };
		final String[] servicesprices = { "100", "150" };
		final String servicesquantity = "1";
		final String woActivePhaseStatus = "Not Started";
		final String woHalfCopleteValue = "50%";
		final String woCopleteValue = "100%";
		final String woCompletedPhaseStatus = "Completed";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = new OperationsWebPage(webdriver);
		backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = new ServiceRequestsListWebPage(webdriver);
		operationspage.clickNewServiceRequestList();
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
		
		VNextBOROWebPage repairorderspage = leftmenu.selectRepairOrdersMenu();
		repairorderspage.searchRepairOrderByNumber(srWONumber);
		
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(srWONumber));
		Assert.assertEquals(repairorderspage.getWorkOrderActivePhaseValue(srWONumber), woActivePhaseStatus);
		repairorderspage.clickStartRoForWorkOrder(srWONumber);
		for (int i = 0 ; i < services.length; i++) {
			repairorderspage.completeWorkOrderServiceStatus(srWONumber, services[i]);
			if (i ==0)
				Assert.assertEquals(repairorderspage.getCompletedWorkOrderValue(srWONumber), woHalfCopleteValue);
		}
		Assert.assertEquals(repairorderspage.getCompletedWorkOrderValue(srWONumber), woCopleteValue);
		Assert.assertEquals(repairorderspage.getWorkOrderActivePhaseValue(srWONumber), woCompletedPhaseStatus);
		
	}
	
	@Test(testName = "Test Case 68406:Repair Orders: Start WO on the Order Details", 
			description = "Repair Orders: Start WO on the Order Details")
	public void testRepairOrdersStartWOOnTheOrderDetails() {

		final String srCustomer = "Test";
		final String VIN = "1GNEK13R4TJ423282";

		final String[] services = { "Glass Repair", "Bumper Repair" };
		final String[] servicesprices = { "100", "150" };
		final String servicesquantity = "1";
		final String woActivePhaseStatus = "Not Started";
		final String serviceQueuedStatus = "Queued";
		
		final String woActivePhaseStatusNEW = "QC";
		final String serviceActiveStatus = "Active";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = new OperationsWebPage(webdriver);
		backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = new ServiceRequestsListWebPage(webdriver);
		operationspage.clickNewServiceRequestList();
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
		
		VNextBOROWebPage repairorderspage = leftmenu.selectRepairOrdersMenu();
		repairorderspage.searchRepairOrderByNumber(srWONumber);
		
		VNextBORODetailsPage rodetailspage = repairorderspage.openWorkOrderDetailsPage(srWONumber);
		Assert.assertEquals(rodetailspage.getRepairOrderActivePhaseStatus(), woActivePhaseStatus);		
		Assert.assertEquals(rodetailspage.getRepairOrderServicesPhaseStatus(), serviceQueuedStatus);
		rodetailspage.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(rodetailspage.getRepairOrderServicesStatus(services[i]), serviceQueuedStatus);		
		rodetailspage.clickStartOrderButton();
		
		Assert.assertFalse(rodetailspage.isStartOrderButtonVisible());
		Assert.assertEquals(rodetailspage.getRepairOrderActivePhaseStatus(), woActivePhaseStatusNEW);		
		Assert.assertEquals(rodetailspage.getRepairOrderServicesPhaseStatus(), serviceActiveStatus);
		rodetailspage.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(rodetailspage.getRepairOrderServicesStatus(services[i]), serviceActiveStatus);		
	}
	
	@Test(testName = "Test Case 68407:Repair Orders: Complete WO on the Order Details", 
			description = "Repair Orders: Complete WO on the Order Details")
	public void testRepairOrdersCompleteWOOnTheOrderDetails() {

		final String srCustomer = "Test";
		final String VIN = "1GNEK13R4TJ423282";

		final String[] services = { "Glass Repair", "Bumper Repair" };
		final String[] servicesprices = { "100", "150" };
		final String servicesquantity = "1";
		final String woActivePhaseStatus = "Not Started";
		final String serviceQueuedStatus = "Queued";
		
		final String woActivePhaseStatusNEW = "QC";
		final String serviceActiveStatus = "Active";
		
		final String woHalfCopleteValue = "50%";
		final String woCopleteValue = "100%";
		final String woCompletedPhaseStatus = "Completed";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = new OperationsWebPage(webdriver);
		backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = new ServiceRequestsListWebPage(webdriver);
		operationspage.clickNewServiceRequestList();
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
		
		VNextBOROWebPage repairorderspage = leftmenu.selectRepairOrdersMenu();
		repairorderspage.searchRepairOrderByNumber(srWONumber);
		
		VNextBORODetailsPage rodetailspage = repairorderspage.openWorkOrderDetailsPage(srWONumber);
		Assert.assertEquals(rodetailspage.getRepairOrderActivePhaseStatus(), woActivePhaseStatus);		
		Assert.assertEquals(rodetailspage.getRepairOrderServicesPhaseStatus(), serviceQueuedStatus);
		rodetailspage.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(rodetailspage.getRepairOrderServicesStatus(services[i]), serviceQueuedStatus);		
		rodetailspage.clickStartOrderButton();
		
		rodetailspage.expandRepairOrderServiceDetailsTable();
		for (int i = 0 ; i < services.length; i++) {
			rodetailspage.changeStatusForrepairorderService(services[i], woCompletedPhaseStatus);
			if (i ==0) {
				Assert.assertEquals(rodetailspage.getRepairOrderActivePhaseStatus(), woActivePhaseStatusNEW);		
				Assert.assertEquals(rodetailspage.getRepairOrderServicesPhaseStatus(), serviceActiveStatus);
				Assert.assertEquals(rodetailspage.getRepairOrderCompletedValue(), woHalfCopleteValue);
			}
		}

		Assert.assertEquals(rodetailspage.getRepairOrderActivePhaseStatus(), woCompletedPhaseStatus);		
		Assert.assertEquals(rodetailspage.getRepairOrderServicesPhaseStatus(), woCompletedPhaseStatus);
		Assert.assertEquals(rodetailspage.getRepairOrderCompletedValue(), woCopleteValue);
		rodetailspage.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(rodetailspage.getRepairOrderServicesStatus(services[i]), woCompletedPhaseStatus);		
	}

}
