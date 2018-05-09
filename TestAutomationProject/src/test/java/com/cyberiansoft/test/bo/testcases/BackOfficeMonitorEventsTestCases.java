package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.EventsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.MiscellaneousWebPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class BackOfficeMonitorEventsTestCases extends BaseTestCase {

	@Test(description = "Test Case 19250:\"Estimate approved\" event creation")
	public void testEstimateApprovedEventCreation() {
		
		final String eventname = "Estimate Approved";
		final String alertname = "Test estimate";
		final String firstconditionname = "Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriteria = "Inttest";

		final String firstConditionNames[] = {"Amount", "Completed", "InspectionType", "Status"};
		final String firstConditionTypes[] = {"equals", "moreThan", "contains", "empty"};
		final String firstConditionCriteria[] = {"10", "1", "Inte", ""};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneousPage = backofficeheader.clickMiscellaneousLink();
		EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.clickEditButtonForEvent(alertname);
		eventsPage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < firstConditionNames.length; i++) {
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.selectFirstConditionValues(firstConditionNames[i], firstConditionTypes[i], firstConditionCriteria[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.verifyFirstConditionValues(firstConditionNames[i], firstConditionTypes[i], firstConditionCriteria[i]);
			eventsPage.cancelNewEvent();
		}
		eventsPage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19282:\"Estimate decline\" event creation")
	public void testEstimateDeclineEventCreation() {
		
		final String eventname = "Estimate Declined";
		final String alertname = "Test Estimate Decline";
		final String firstconditionname = "Name";
		final String firstconditiontype = "equals";
		final String firstconditioncriterion = "Inttest";

		final String firstconditionnames[] = {"Amount", "Completed", "InspectionType", "Status"};
		final String firstconditiontypes[] = {"equals", "moreThan", "contains", "empty"};
		final String firstconditioncriteria[] = {"10", "1", "Inte", ""};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
        MiscellaneousWebPage miscellaneousPage = backofficeheader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriterion);
		eventsPage.clickEditButtonForEvent(alertname);
		eventsPage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriterion);
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriteria[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriteria[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19283:\"New Inspection\" event creation")
	public void testNewInspectionEventCreation() {
		
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
		
        MiscellaneousWebPage miscellaneousPage = backofficeheader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();

		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.clickEditButtonForEvent(alertname);
		eventsPage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.cancelNewEvent();
		}
		eventsPage.deleteEvent(alertname);
	}
	
	//@Test(description = "Test Case 19384:\"60 min to target time\" event creation")
	public void test60MinToTargetTimeEventCreation() {
		
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

        MiscellaneousWebPage miscellaneousPage = backofficeheader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.clickEditButtonForEvent(alertname);
		eventsPage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19563:\"High Priority RO\" event creation")
	public void testHighPriorityROEventCreation() {
		
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

        MiscellaneousWebPage miscellaneousPage = backofficeheader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.clickEditButtonForEvent(alertname);
		eventsPage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19564:\"Invoice Created\" event creation")
	public void testInvoiceCreatedEventCreation() {
		
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

        MiscellaneousWebPage miscellaneousPage = backofficeheader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.clickEditButtonForEvent(alertname);
		eventsPage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.cancelNewEvent();
        for (int i = 0;  i < firstconditionnames.length; i++) {
            eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(alertname);
	}
	
	@Test(description = "Test Case 19567:\"New Repair Order\" event creation")
	public void testNewRepairOrderEventCreation() {
		
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

        MiscellaneousWebPage miscellaneousPage = backofficeheader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(eventname, alertname, firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.clickEditButtonForEvent(alertname);
		eventsPage.verifyFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < firstconditionnames.length; i++) {
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.selectFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(alertname);
			eventsPage.verifyFirstConditionValues(firstconditionnames[i], firstconditiontypes[i], firstconditioncriterias[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(alertname);
	}
}
