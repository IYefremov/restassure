package com.cyberiansoft.test.monitorlite.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListInteractions;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.monitorlite.config.MonitorLiteConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPage;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationspage.clickNewServiceRequestList();
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(srCustomer);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(VIN);
		//serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(_make, _model);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.addServicesToServiceRequest(services);
		for (int i = 0; i < services.length; i++)
			serviceRequestsListInteractions.setServiePriceAndQuantity(services[i], servicesprices[i], servicesquantity);
		
		serviceRequestsListInteractions.saveNewServiceRequest();
		
		String srNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.getWOForFirstServiceRequestFromList();
		
		String srWONumber = serviceRequestsListInteractions.getWOForServiceRequestFromList(srNumber);

		webdriverGotoWebPage(MonitorLiteConfigInfo.getInstance().getBackOfficeMonitorLiteURL());
        VNextBOLoginSteps.userLogin(MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserName(),
				MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		
		leftmenu.selectRepairOrdersMenu();
        VNextBOROPageInteractions.search(srWONumber);
		
		Assert.assertTrue(VNextBOROPageValidations.isRepairOrderPresentInTable(srWONumber));
		Assert.assertEquals(VNextBOROPageInteractions.getWorkOrderActivePhaseValue(srWONumber), woActivePhaseStatus);
        VNextBOROPageInteractions.clickStartRoForWorkOrder(srWONumber);
		Assert.assertEquals(VNextBOROPageInteractions.getWorkOrderActivePhaseValue(srWONumber), woActivePhaseStatusNEW);
		Assert.assertEquals(VNextBOROPageInteractions.getWorkOrderDaysInProgressValue(srWONumber), woDaysInProgress);
		
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

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationspage.clickNewServiceRequestList();
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(srCustomer);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(VIN);
		//serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(_make, _model);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.addServicesToServiceRequest(services);
		for (int i = 0; i < services.length; i++)
			serviceRequestsListInteractions.setServiePriceAndQuantity(services[i], servicesprices[i], servicesquantity);
		
		serviceRequestsListInteractions.saveNewServiceRequest();
		
		String srNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.getWOForFirstServiceRequestFromList();
		
		String srWONumber = serviceRequestsListInteractions.getWOForServiceRequestFromList(srNumber);

		webdriverGotoWebPage(MonitorLiteConfigInfo.getInstance().getBackOfficeMonitorLiteURL());
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
        VNextBOLoginSteps.userLogin(MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserName(),
				MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		
		VNextBOROWebPage repairorderspage = leftmenu.selectRepairOrdersMenu();
        VNextBOROPageInteractions.search(srWONumber);
		
		Assert.assertTrue(VNextBOROPageValidations.isRepairOrderPresentInTable(srWONumber));
		Assert.assertEquals(VNextBOROPageInteractions.getWorkOrderActivePhaseValue(srWONumber), woActivePhaseStatus);
        VNextBOROPageInteractions.clickStartRoForWorkOrder(srWONumber);
		for (int i = 0 ; i < services.length; i++) {
            VNextBOROPageInteractions.completeWorkOrderServiceStatus(srWONumber, services[i]);
			if (i ==0)
				Assert.assertEquals(VNextBOROPageInteractions.getCompletedWorkOrderValue(srWONumber), woHalfCopleteValue);
		}
		Assert.assertEquals(VNextBOROPageInteractions.getCompletedWorkOrderValue(srWONumber), woCopleteValue);
		Assert.assertEquals(VNextBOROPageInteractions.getWorkOrderActivePhaseValue(srWONumber), woCompletedPhaseStatus);
		
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

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationspage.clickNewServiceRequestList();
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(srCustomer);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(VIN);
		//serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(_make, _model);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.addServicesToServiceRequest(services);
		for (int i = 0; i < services.length; i++)
			serviceRequestsListInteractions.setServiePriceAndQuantity(services[i], servicesprices[i], servicesquantity);
		
		serviceRequestsListInteractions.saveNewServiceRequest();
		
		String srNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.getWOForFirstServiceRequestFromList();
		
		String srWONumber = serviceRequestsListInteractions.getWOForServiceRequestFromList(srNumber);

		webdriverGotoWebPage(MonitorLiteConfigInfo.getInstance().getBackOfficeMonitorLiteURL());
        VNextBOLoginSteps.userLogin(MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserName(),
				MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserPassword());

        VNextBOROPageInteractions.search(srWONumber);
		
        VNextBOROPageInteractions.openWorkOrderDetailsPage(srWONumber);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderActivePhaseStatus(), woActivePhaseStatus);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesPhaseStatus(), serviceQueuedStatus);
        VNextBORODetailsPageInteractions.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesStatus(services[i]), serviceQueuedStatus);
        VNextBORODetailsPageInteractions.clickStartOrderButton();
		
		Assert.assertFalse(VNextBORODetailsPageValidations.isStartOrderButtonVisible());
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderActivePhaseStatus(), woActivePhaseStatusNEW);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesPhaseStatus(), serviceActiveStatus);
        VNextBORODetailsPageInteractions.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesStatus(services[i]), serviceActiveStatus);
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

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationspage.clickNewServiceRequestList();
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(srCustomer);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(VIN);
		//serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(_make, _model);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.addServicesToServiceRequest(services);
		for (int i = 0; i < services.length; i++)
			serviceRequestsListInteractions.setServiePriceAndQuantity(services[i], servicesprices[i], servicesquantity);
		
		serviceRequestsListInteractions.saveNewServiceRequest();
		
		String srNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.getWOForFirstServiceRequestFromList();
		
		String srWONumber = serviceRequestsListInteractions.getWOForServiceRequestFromList(srNumber);

		webdriverGotoWebPage(MonitorLiteConfigInfo.getInstance().getBackOfficeMonitorLiteURL());
        VNextBOLoginSteps.userLogin(MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserName(),
				MonitorLiteConfigInfo.getInstance().getUserMonitorLiteUserPassword());

        VNextBOROPageInteractions.search(srWONumber);
		
		VNextBORODetailsPage rodetailspage = new VNextBORODetailsPage();
		VNextBOROPageInteractions.openWorkOrderDetailsPage(srWONumber);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderActivePhaseStatus(), woActivePhaseStatus);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesPhaseStatus(), serviceQueuedStatus);
        VNextBORODetailsPageInteractions.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesStatus(services[i]), serviceQueuedStatus);
        VNextBORODetailsPageInteractions.clickStartOrderButton();

        VNextBORODetailsPageInteractions.expandRepairOrderServiceDetailsTable();
		for (int i = 0 ; i < services.length; i++) {
            VNextBORODetailsPageInteractions.changeStatusForRepairOrderService(services[i], woCompletedPhaseStatus);
			if (i ==0) {
				Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderActivePhaseStatus(), woActivePhaseStatusNEW);
				Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesPhaseStatus(), serviceActiveStatus);
				Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderCompletedValue(), woHalfCopleteValue);
			}
		}

		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderActivePhaseStatus(), woCompletedPhaseStatus);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesPhaseStatus(), woCompletedPhaseStatus);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderCompletedValue(), woCopleteValue);
        VNextBORODetailsPageInteractions.expandRepairOrderServiceDetailsTable();
		for (int i = 0; i < services.length; i++)
			Assert.assertEquals(VNextBORODetailsPageInteractions.getRepairOrderServicesStatus(services[i]), woCompletedPhaseStatus);
	}

}
