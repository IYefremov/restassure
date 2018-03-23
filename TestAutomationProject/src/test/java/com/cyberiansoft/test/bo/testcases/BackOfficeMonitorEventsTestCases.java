package com.cyberiansoft.test.bo.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.EventsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.MonitorWebPage;

public class BackOfficeMonitorEventsTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backofficedemo.url", "userdemo.name", "userdemo.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		try{
		backofficeheader.clickLogout();
		Thread.sleep(3000);
		}catch(Exception e){
			backofficeheader.refresh();
			backofficeheader.clickLogout();
		}
	}
	
	@Test(description = "Test Case 19250:\"Estimate approved\" event creation")
	public void testEstimateApprovedEventCreation() throws Exception {
		
		final String eventname = "Estimate Approved";
		final String alertname = "Test estimate";
		final String firstconditionname = "Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstconditionnames[] = {"Amount", "Completed", "InspectionType", "Status"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty"};
		final String firstconditioncriterias[] = {"10", "1", "Inte", ""};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		EventsWebPage eventspage = monitorpage.clickEventsLink();	
		
		eventspage.verifyEventsTableColumnsAreVisible();
		eventspage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventspage.clickEditButtonForEvent(alertname);
		eventspage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);		
		eventspage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.saveNewEvent();
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.cancelNewEvent();
		}		
		eventspage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19282:\"Estimate decline\" event creation")
	public void testEstimateDeclineEventCreation() throws Exception {
		
		final String eventname = "Estimate Declined";
		final String alertname = "Test Estimate Decline";
		final String firstconditionname = "Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstconditionnames[] = {"Amount", "Completed", "InspectionType", "Status"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty"};
		final String firstconditioncriterias[] = {"10", "1", "Inte", ""};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		EventsWebPage eventspage = monitorpage.clickEventsLink();	
		
		eventspage.verifyEventsTableColumnsAreVisible();
		eventspage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventspage.clickEditButtonForEvent(alertname);
		eventspage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);		
		eventspage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.saveNewEvent();
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.cancelNewEvent();
		}		
		eventspage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19283:\"New Inspection\" event creation")
	public void testNewInspectionEventCreation() throws Exception {
		
		final String eventname = "New Inspection";
		final String alertname = "Test New Inspection";
		final String firstconditionname = "Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstconditionnames[] = {"Amount", "Completed", "InspectionType", "Status"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty"};
		final String firstconditioncriterias[] = {"10", "1", "Inte", ""};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		EventsWebPage eventspage = monitorpage.clickEventsLink();	
		
		eventspage.verifyEventsTableColumnsAreVisible();
		eventspage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventspage.clickEditButtonForEvent(alertname);
		eventspage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);		
		eventspage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.saveNewEvent();
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.cancelNewEvent();
		}		
		eventspage.deleteEvent(alertname);
	}
	
	//@Test(description = "Test Case 19384:\"60 min to target time\" event creation")
	public void test60MinToTargetTimeEventCreation() throws Exception {
		
		final String eventname = "60 Min to Target Time";
		final String alertname = "Test 60 Min to Target Time";
		final String firstconditionname = "Client Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstconditionnames[] = {"Amount", "Approved", "Order Status", "Status Reason"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty"};
		final String firstconditioncriterias[] = {"10", "1", "Inte", ""};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		EventsWebPage eventspage = monitorpage.clickEventsLink();	
		
		eventspage.verifyEventsTableColumnsAreVisible();
		eventspage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventspage.clickEditButtonForEvent(alertname);
		eventspage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);		
		eventspage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.saveNewEvent();
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.cancelNewEvent();
		}		
		eventspage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19563:\"High Priority RO\" event creation")
	public void testHighPriorityROEventCreation() throws Exception {
		
		final String eventname = "High Priority RO";
		final String alertname = "Test High Priority RO";
		final String firstconditionname = "Client Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstconditionnames[] = {"Amount", "Approved", "Order Status", "Status Reason", "Phase", "Repair Location", "Repair Status", "Target Date", "Area", "Employee Name", "Team"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty", "equals", "notEmpty", "equals", "empty", "contains", "contains", "contains"};
		final String firstconditioncriterias[] = {"10", "1", "Inte", "", "start", "", "1", "", "Int", "Int", "Int"};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		EventsWebPage eventspage = monitorpage.clickEventsLink();	
		
		eventspage.verifyEventsTableColumnsAreVisible();
		eventspage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventspage.clickEditButtonForEvent(alertname);
		eventspage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);		
		eventspage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.saveNewEvent();
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.cancelNewEvent();
		}		
		eventspage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19564:\"Invoice Created\" event creation")
	public void testInvoiceCreatedEventCreation() throws Exception {
		
		final String eventname = "Invoice Created";
		final String alertname = "Test Invoice Created";
		final String firstconditionname = "Client Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstconditionnames[] = {"Amount", "Approved", "Order Status", "Status Reason", "Phase", "Repair Location", "Repair Status", "Target Date", "Area", "Employee Name", "Team"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty", "equals", "notEmpty", "equals", "empty", "contains", "contains", "contains"};
		final String firstconditioncriterias[] = {"10", "1", "Inte", "", "start", "", "1", "", "Int", "Int", "Int"};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		EventsWebPage eventspage = monitorpage.clickEventsLink();	
		
		eventspage.verifyEventsTableColumnsAreVisible();
		eventspage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventspage.clickEditButtonForEvent(alertname);
		eventspage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);		
		eventspage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.saveNewEvent();
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.cancelNewEvent();
		}		
		eventspage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19567:\"New Repair Order\" event creation")
	public void testNewRepairOrderEventCreation() throws Exception {
		
		final String eventname = "New Repair Order";
		final String alertname = "Test New Repair Order";
		final String firstconditionname = "Client Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstconditionnames[] = {"Amount", "Approved", "Order Status", "Status Reason", "Phase", "Repair Location", "Repair Status", "Target Date", "Area", "Employee Name", "Team"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty", "equals", "notEmpty", "equals", "empty", "contains", "contains", "contains"};
		final String firstconditioncriterias[] = {"10", "1", "Inte", "", "start", "", "1", "", "Int", "Int", "Int"};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		EventsWebPage eventspage = monitorpage.clickEventsLink();	
		
		eventspage.verifyEventsTableColumnsAreVisible();
		eventspage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventspage.clickEditButtonForEvent(alertname);
		eventspage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);		
		eventspage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.saveNewEvent();
			eventspage.clickEditButtonForEvent(alertname);
			eventspage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);		
			eventspage.cancelNewEvent();
		}		
		eventspage.deleteEvent(alertname);
	}

}
