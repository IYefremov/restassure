package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.bo.BOOperationsInspectionsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BackOfficeOperationsInspectionsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOOperationsInspectionsData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyOpeningInspectionEditorForNewStatus(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InspectionsWebPage inspectionsPage = new InspectionsWebPage(webdriver);
		operationsPage.clickInspectionsLink();
		inspectionsPage.makeSearchPanelVisible();
		inspectionsPage.selectSearchStatus(data.getStatus());
		inspectionsPage.clickFindButton();
		inspectionsPage.verifyInspectionsTableColumnsAreVisible();
		String firstInspection = inspectionsPage.getFirstInspectionNumber();
		InspectionEditorWebPage inspectionEditorPage = new InspectionEditorWebPage(webdriver);
		inspectionsPage.clickEditInspection(firstInspection);
		Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
				"The Inspection Editor has not been opened for inspection with New status");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyOpeningInspectionEditorForDraftStatus(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InspectionsWebPage inspectionsPage = new InspectionsWebPage(webdriver);
		operationsPage.clickInspectionsLink();
		inspectionsPage.makeSearchPanelVisible();
		inspectionsPage.selectSearchStatus(data.getStatus());
		inspectionsPage.clickFindButton();
		inspectionsPage.verifyInspectionsTableColumnsAreVisible();
		String firstInspection = inspectionsPage.getFirstInspectionNumber();
		InspectionEditorWebPage inspectionEditorPage = new InspectionEditorWebPage(webdriver);
		inspectionsPage.clickEditInspection(firstInspection);
		Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
				"The Inspection Editor has not been opened for inspection with Draft status");
	}

	//todo uncomment after bug fix #75725
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void editEstimationDetailsForInspectionWithNewStatus(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InspectionsWebPage inspectionsPage = new InspectionsWebPage(webdriver);
		operationsPage.clickInspectionsLink();
		inspectionsPage.makeSearchPanelVisible();
		inspectionsPage.selectSearchStatus(data.getStatus());
		inspectionsPage.clickFindButton();
		inspectionsPage.verifyInspectionsTableColumnsAreVisible();
		String firstInspection = inspectionsPage.getFirstInspectionNumber();
		String windowHandle = inspectionsPage.getInspectionsWindowHandle();
		String changedInspectionDate = inspectionsPage.getChangedInspectionDate(data.getDay());

		InspectionEditorWebPage inspectionEditorPage = new InspectionEditorWebPage(webdriver);
		inspectionsPage.clickEditInspection(firstInspection);
		Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
				"The Inspection Editor has not been opened for inspection with New status");
		inspectionEditorPage.clickDateInput();
		inspectionEditorPage.clickPreviousMonthButton();
		Assert.assertTrue(inspectionEditorPage.isNextMonthButtonEnabled(),
				"The Next month button has not been enabled after clicking the Previous month button");
		inspectionEditorPage.selectDay(data.getDay());
		inspectionEditorPage.clickSaveInspectionButton();
		inspectionEditorPage.closeNewTab(windowHandle);
		inspectionsPage.makeSearchPanelVisible();
		inspectionsPage.selectSearchStatus(data.getStatus());
		inspectionsPage.setInspectionNumberSearchCriteria(firstInspection);
		inspectionsPage.clickFindButton();
		Assert.assertTrue(inspectionsPage.inspectionExists(firstInspection), "The inspection is not displayed");
		Assert.assertEquals(inspectionsPage.getFirstInspectionDate(), changedInspectionDate);
	}

	//todo uncomment after bug fix #75725
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void editEstimationDetailsForInspectionWithDraftStatus(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InspectionsWebPage inspectionsPage = new InspectionsWebPage(webdriver);
		operationsPage.clickInspectionsLink();
		inspectionsPage.makeSearchPanelVisible();
		inspectionsPage.selectSearchStatus(data.getStatus());
		inspectionsPage.clickFindButton();
		inspectionsPage.verifyInspectionsTableColumnsAreVisible();
		String firstInspection = inspectionsPage.getFirstInspectionNumber();
		String windowHandle = inspectionsPage.getInspectionsWindowHandle();
		String changedInspectionDate = inspectionsPage.getChangedInspectionDate(data.getDay());

		InspectionEditorWebPage inspectionEditorPage = new InspectionEditorWebPage(webdriver);
		inspectionsPage.clickEditInspection(firstInspection);
		Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
				"The Inspection Editor has not been opened for inspection with New status");
		if (inspectionEditorPage.isDateInputDisplayed()) {
			inspectionEditorPage.clickDateInput();
			inspectionEditorPage.clickPreviousMonthButton();
			Assert.assertTrue(inspectionEditorPage.isNextMonthButtonEnabled(),
					"The Next month button has not been enabled after clicking the Previous month button");
			inspectionEditorPage.selectDay(data.getDay());
			inspectionEditorPage.clickSaveInspectionButton();
			inspectionEditorPage.closeNewTab(windowHandle);
			inspectionsPage.makeSearchPanelVisible();
			inspectionsPage.selectSearchStatus(data.getStatus());
			inspectionsPage.setInspectionNumberSearchCriteria(firstInspection);
			inspectionsPage.clickFindButton();
			Assert.assertTrue(inspectionsPage.inspectionExists(firstInspection), "The inspection is not displayed");
			Assert.assertEquals(changedInspectionDate, inspectionsPage.getFirstInspectionDate());
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationInspection(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(data.getFromTime(), data.getToTime());
		inspectionsWebPage.clickFindButton();
		inspectionsWebPage.verifyInspectionsTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), inspectionsWebPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), inspectionsWebPage.getGoToPageFieldValue());

		inspectionsWebPage.setPageSize(data.getPage1());
		Assert.assertEquals(data.getTableRowCount1(), inspectionsWebPage.getInspectionsTableRowCount());

		String lastpagenumber = inspectionsWebPage.getLastPageNumber();
		inspectionsWebPage.clickGoToLastPage(browserType.getBrowserTypeString());

		inspectionsWebPage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), inspectionsWebPage.getGoToPageFieldValue());

		inspectionsWebPage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), inspectionsWebPage.getGoToPageFieldValue());

		inspectionsWebPage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), inspectionsWebPage.getGoToPageFieldValue());

		inspectionsWebPage.setPageSize(String.valueOf(data.getPageInt50()));
		Integer lastPageNumberInteger = Integer.valueOf(lastpagenumber);
		if (lastPageNumberInteger < data.getPageInt50()) {
			Assert.assertEquals(lastPageNumberInteger, Integer.valueOf(inspectionsWebPage.getInspectionsTableRowCount()));
		} else {
			Assert.assertEquals(data.getPageInt50(), inspectionsWebPage.getInspectionsTableRowCount());
		}

		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.verifySearchFieldsAreVisible();

		inspectionsWebPage.selectSearchCustomer(data.getCustomer());
		inspectionsWebPage.selectSearchTechnician(data.getTechnician(), data.getTechnicianFull());
		inspectionsWebPage.selectSearchStatus(data.getStatus());
		inspectionsWebPage.setInspectionNumberSearchCriteria(data.getInspNum());
		inspectionsWebPage.clickFindButton();
		Assert.assertEquals(data.getTableRowCount1(), inspectionsWebPage.getInspectionsTableRowCount());
		inspectionsWebPage.inspectionExists(data.getInspNum());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditDuplicateByRO(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeframe(data.getTimeFrame());
		inspectionsWebPage.setTimeFrame(data.getFromTime(), data.getToTime());
		inspectionsWebPage.searchInspectionByNumber(data.getInspectionNum1());
		Assert.assertTrue(inspectionsWebPage.inspectionExists(data.getInspectionNum1()));
		DuplicateInspectionsWebPage duplicateInspectionsWebPage = new DuplicateInspectionsWebPage(webdriver);
		inspectionsWebPage.clickDuplicateByROLink();
		duplicateInspectionsWebPage.switchToDuplicateInspectionPage();
		Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum1(), data.getROnum()),
				"The duplicate by RO inspection #1 has not been displayed");
		Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum2(), data.getROnum()),
				"The duplicate by RO inspection #2 has not been displayed");//todo change the locator after the inspection will be displayed only for RO
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditDuplicateByVIN(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeframe(data.getTimeFrame());
		inspectionsWebPage.setTimeFrame(data.getFromTime(), data.getToTime());
		inspectionsWebPage.searchInspectionByNumber(data.getInspectionNum1());
		Assert.assertTrue(inspectionsWebPage.inspectionExists(data.getInspectionNum1()));
		DuplicateInspectionsWebPage duplicateInspectionsWebPage = new DuplicateInspectionsWebPage(webdriver);
		inspectionsWebPage.clickDuplicateByVINLink();
		duplicateInspectionsWebPage.switchToDuplicateInspectionPage();
		Assert.assertTrue(duplicateInspectionsWebPage.isVINdisplayed(data.getVIN()), "The VIN has not been displayed");
		//todo uncomment after the inspectiontypes with unique VIN numbers will be created
//        Assert.assertTrue(duplicateInspectionsWebPage.isInspectionDisplayed(data.getInspectionNum1()),
//                "The inspection #1 has not been displayed for duplicate by VIN");
//        Assert.assertTrue(duplicateInspectionsWebPage.isVINdisplayedForInspection(data.getInspectionNum2(), data.getVIN()),
//                "The inspection #2 has not been displayed for duplicate by VIN");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditDuplicateByVINAndRO(String rowID, String description, JSONObject testData) {

		BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);

        operationsPage.clickInspectionsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeframe(data.getTimeFrame());
		inspectionsWebPage.setTimeFrame(data.getFromTime(), data.getToTime());
		inspectionsWebPage.searchInspectionByNumber(data.getInspectionNum1());
		Assert.assertTrue(inspectionsWebPage.inspectionExists(data.getInspectionNum1()));
		DuplicateInspectionsWebPage duplicateInspectionsWebPage = new DuplicateInspectionsWebPage(webdriver);
		inspectionsWebPage.clickDuplicateByVINandROLink();
		duplicateInspectionsWebPage.switchToDuplicateInspectionPage();
		Assert.assertTrue(duplicateInspectionsWebPage.isVINdisplayed(data.getVIN()), "The VIN has not been displayed");
		Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum1(), data.getROnum()),
				"The duplicate by VIN and RO inspection #1 has not been displayed");
		Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum2(), data.getROnum()),
				"The duplicate by VIN and RO inspection #2 has not been displayed");
	}
}
