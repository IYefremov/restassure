package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.EventsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.MiscellaneousWebPage;
import com.cyberiansoft.test.dataclasses.bo.BackOfficeMonitorEventsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(VideoListener.class)
public class BackOfficeMonitorEventsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BackOfficeMonitorEventsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEstimateApprovedEventCreation(String rowID, String description, JSONObject testData) {

        BackOfficeMonitorEventsData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeMonitorEventsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneousPage = backOfficeHeader.clickMiscellaneousLink();
		EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(data.getEventName(), data.getAlertName(), data.getFirstConditionName(), 
                data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.clickEditButtonForEvent(data.getAlertName());
		eventsPage.verifyFirstConditionValues(data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < data.getFirstConditionNames().length; i++) {
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.selectFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.verifyFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.cancelNewEvent();
		}
		eventsPage.deleteEvent(data.getAlertName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEstimateDeclineEventCreation(String rowID, String description, JSONObject testData) {

        BackOfficeMonitorEventsData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeMonitorEventsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneousPage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(data.getEventName(), data.getAlertName(), data.getFirstConditionName(),
                data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.clickEditButtonForEvent(data.getAlertName());
		eventsPage.verifyFirstConditionValues(data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < data.getFirstConditionNames().length; i++) {
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.selectFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.verifyFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(data.getAlertName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNewInspectionEventCreation(String rowID, String description, JSONObject testData) {

        BackOfficeMonitorEventsData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeMonitorEventsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneousPage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();

		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(data.getEventName(), data.getAlertName(), data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.clickEditButtonForEvent(data.getAlertName());
		eventsPage.verifyFirstConditionValues(data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < data.getFirstConditionNames().length; i++) {
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.selectFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.verifyFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.cancelNewEvent();
		}
		eventsPage.deleteEvent(data.getAlertName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testHighPriorityROEventCreation(String rowID, String description, JSONObject testData) {

        BackOfficeMonitorEventsData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeMonitorEventsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneousPage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(data.getEventName(), data.getAlertName(), data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.clickEditButtonForEvent(data.getAlertName());
		eventsPage.verifyFirstConditionValues(data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < data.getFirstConditionNames().length; i++) {
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.selectFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.verifyFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(data.getAlertName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoiceCreatedEventCreation(String rowID, String description, JSONObject testData) {

        BackOfficeMonitorEventsData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeMonitorEventsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneousPage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(data.getEventName(), data.getAlertName(), data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.clickEditButtonForEvent(data.getAlertName());
		eventsPage.verifyFirstConditionValues(data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.cancelNewEvent();
        for (int i = 0;  i < data.getFirstConditionNames().length; i++) {
            eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.selectFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.verifyFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(data.getAlertName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNewRepairOrderEventCreation(String rowID, String description, JSONObject testData) {

        BackOfficeMonitorEventsData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeMonitorEventsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MiscellaneousWebPage miscellaneousPage = backOfficeHeader.clickMiscellaneousLink();
        EventsWebPage eventsPage = miscellaneousPage.clickEventsLink();
		
		eventsPage.verifyEventsTableColumnsAreVisible();
		eventsPage.createNewEventWithConditions(data.getEventName(), data.getAlertName(), data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.clickEditButtonForEvent(data.getAlertName());
		eventsPage.verifyFirstConditionValues(data.getFirstConditionName(), data.getFirstConditionType(), data.getFirstConditionCriterion());
		eventsPage.cancelNewEvent();
		for (int i = 0;  i < data.getFirstConditionNames().length; i++) {
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.selectFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.saveNewEvent();
			eventsPage.clickEditButtonForEvent(data.getAlertName());
			eventsPage.verifyFirstConditionValues(data.getFirstConditionNames()[i], data.getFirstConditionTypes()[i], data.getFirstConditionCriteria()[i]);
			eventsPage.cancelNewEvent();
		}		
		eventsPage.deleteEvent(data.getAlertName());
	}
}
