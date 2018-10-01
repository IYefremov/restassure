package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
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
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InspectionsWebPage inspectionsPage = operationsPage
                .clickInspectionsLink()
                .makeSearchPanelVisible()
                .selectSearchStatus(data.getStatus())
                .clickFindButton()
                .verifyInspectionsTableColumnsAreVisible();
        String firstInspection = inspectionsPage.getFirstInspectionNumber();
        InspectionEditorWebPage inspectionEditorPage = inspectionsPage.clickEditInspection(firstInspection);
        Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
                "The Inspection Editor has not been opened for inspection with New status");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyOpeningInspectionEditorForDraftStatus(String rowID, String description, JSONObject testData) {

        BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InspectionsWebPage inspectionsPage = operationsPage
                .clickInspectionsLink()
                .makeSearchPanelVisible()
                .selectSearchStatus(data.getStatus())
                .clickFindButton()
                .verifyInspectionsTableColumnsAreVisible();
        String firstInspection = inspectionsPage.getFirstInspectionNumber();
        InspectionEditorWebPage inspectionEditorPage = inspectionsPage.clickEditInspection(firstInspection);
        Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
                "The Inspection Editor has not been opened for inspection with Draft status");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void editEstimationDetailsForInspectionWithNewStatus(String rowID, String description, JSONObject testData) {

        BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InspectionsWebPage inspectionsPage = operationsPage
                .clickInspectionsLink()
                .makeSearchPanelVisible()
                .selectSearchStatus(data.getStatus())
                .clickFindButton()
                .verifyInspectionsTableColumnsAreVisible();
        String firstInspection = inspectionsPage.getFirstInspectionNumber();
        String windowHandle = inspectionsPage.getInspectionsWindowHandle();
        String changedInspectionDate = inspectionsPage.getChangedInspectionDate(data.getDay());

        InspectionEditorWebPage inspectionEditorPage = inspectionsPage.clickEditInspection(firstInspection);
        Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
                "The Inspection Editor has not been opened for inspection with New status");
        inspectionEditorPage
                .clickDateInput()
                .clickPreviousMonthButton();
        Assert.assertTrue(inspectionEditorPage.isNextMonthButtonEnabled(),
                "The Next month button has not been enabled after clicking the Previous month button");
        inspectionEditorPage
                .selectDay(data.getDay())
                .clickSaveInspectionButton()
                .closeNewTab(windowHandle);
        inspectionsPage.makeSearchPanelVisible()
                .selectSearchStatus(data.getStatus())
                .setInspectionNumberSearchCriteria(firstInspection)
                .clickFindButton();
        Assert.assertTrue(inspectionsPage.inspectionExists(firstInspection), "The inspection is not displayed");
        Assert.assertEquals(changedInspectionDate, inspectionsPage.getFirstInspectionDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void editEstimationDetailsForInspectionWithDraftStatus(String rowID, String description, JSONObject testData) {

        BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InspectionsWebPage inspectionsPage = operationsPage
                .clickInspectionsLink()
                .makeSearchPanelVisible()
                .selectSearchStatus(data.getStatus())
                .clickFindButton()
                .verifyInspectionsTableColumnsAreVisible();
        String firstInspection = inspectionsPage.getFirstInspectionNumber();
        String windowHandle = inspectionsPage.getInspectionsWindowHandle();
        String changedInspectionDate = inspectionsPage.getChangedInspectionDate(data.getDay());

        InspectionEditorWebPage inspectionEditorPage = inspectionsPage.clickEditInspection(firstInspection);
        Assert.assertTrue(inspectionEditorPage.isInspectionEditorOpened(firstInspection),
                "The Inspection Editor has not been opened for inspection with New status");
        inspectionEditorPage
                .clickDateInput()
                .clickPreviousMonthButton();
        Assert.assertTrue(inspectionEditorPage.isNextMonthButtonEnabled(),
                "The Next month button has not been enabled after clicking the Previous month button");
        inspectionEditorPage
                .selectDay(data.getDay())
                .clickSaveInspectionButton()
                .closeNewTab(windowHandle);
        inspectionsPage.makeSearchPanelVisible()
                .selectSearchStatus(data.getStatus())
                .setInspectionNumberSearchCriteria(firstInspection)
                .clickFindButton();
        Assert.assertTrue(inspectionsPage.inspectionExists(firstInspection), "The inspection is not displayed");
        Assert.assertEquals(changedInspectionDate, inspectionsPage.getFirstInspectionDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInspection(String rowID, String description, JSONObject testData) {

        BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InspectionsWebPage inpectionsPage = operationsPage.clickInspectionsLink();
        inpectionsPage.makeSearchPanelVisible();
        inpectionsPage.selectSearchTimeframe(data.getTimeFrame());
        inpectionsPage.clickFindButton();
        inpectionsPage.verifyInspectionsTableColumnsAreVisible();

        Assert.assertEquals(data.getPage1(), inpectionsPage.getCurrentlySelectedPageNumber());
        Assert.assertEquals(data.getPage1(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.setPageSize(data.getPage1());
        Assert.assertEquals(data.getTableRowCount1(), inpectionsPage.getInspectionsTableRowCount());

        String lastpagenumber = inpectionsPage.getLastPageNumber();
        inpectionsPage.clickGoToLastPage(browsertype.getBrowserTypeString());

        inpectionsPage.clickGoToFirstPage();
        Assert.assertEquals(data.getPage1(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.clickGoToNextPage();
        Assert.assertEquals(data.getPage2(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.clickGoToPreviousPage();
        Assert.assertEquals(data.getPage1(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.setPageSize(String.valueOf(data.getPageInt50()));
        Integer lastPageNumberInteger = Integer.valueOf(lastpagenumber);
        if (lastPageNumberInteger < data.getPageInt50()) {
            Assert.assertEquals(lastPageNumberInteger, Integer.valueOf(inpectionsPage.getInspectionsTableRowCount()));
        } else {
            Assert.assertEquals(data.getPageInt50(), inpectionsPage.getInspectionsTableRowCount());
        }

        inpectionsPage.makeSearchPanelVisible();
        inpectionsPage.verifySearchFieldsAreVisible();

        inpectionsPage.selectSearchCustomer(data.getCustomer());
        inpectionsPage.selectSearchTechnician(data.getTechnician(), data.getTechnicianFull());
        inpectionsPage.selectSearchStatus(data.getStatus());
        inpectionsPage.setInspectionNumberSearchCriteria(data.getInspNum());
        inpectionsPage.clickFindButton();
        Assert.assertEquals(data.getTableRowCount1(), inpectionsPage.getInspectionsTableRowCount());
        inpectionsPage.inspectionExists(data.getInspNum());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditDuplicateByRO(String rowID, String description, JSONObject testData) {

        BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = operationsPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible()
                .selectSearchTimeframe(data.getTimeFrame())
                .setTimeFrame(data.getFromTime(), data.getToTime())
                .searchInspectionByNumber(data.getInspectionNum1());
        Assert.assertTrue(inspectionsWebPage.inspectionExists(data.getInspectionNum1()));
        DuplicateInspectionsWebPage duplicateInspectionsWebPage = inspectionsWebPage.clickDuplicateByROLink();
        duplicateInspectionsWebPage.switchToDuplicateInspectionPage();
        Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum1(), data.getROnum()),
                "The duplicate by RO inspection #1 has not been displayed");
        Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum2(), data.getROnum()),
                "The duplicate by RO inspection #2 has not been displayed");//todo change the locator after the inspection will be displayed only for RO
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditDuplicateByVIN(String rowID, String description, JSONObject testData) {

        BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = operationsPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible()
                .selectSearchTimeframe(data.getTimeFrame())
                .setTimeFrame(data.getFromTime(), data.getToTime())
                .searchInspectionByNumber(data.getInspectionNum1());
        Assert.assertTrue(inspectionsWebPage.inspectionExists(data.getInspectionNum1()));
        DuplicateInspectionsWebPage duplicateInspectionsWebPage = inspectionsWebPage.clickDuplicateByVINLink();
        duplicateInspectionsWebPage.switchToDuplicateInspectionPage();
        Assert.assertTrue(duplicateInspectionsWebPage.isVINdisplayed(data.getVIN()), "The VIN has not been displayed");
        //todo uncomment after the inspectiontypes with unique VIN numbers will be created
//        Assert.assertTrue(duplicateInspectionsWebPage.isInspectionDisplayed(data.getInspectionNum1()),
//                "The inspection #1 has not been displayed for duplicate by VIN");
//        Assert.assertTrue(duplicateInspectionsWebPage.isVINdisplayedForInspection(data.getInspectionNum2(), data.getVIN()),
//                "The inspection #2 has not been displayed for duplicate by VIN");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditDuplicateByVINandRO(String rowID, String description, JSONObject testData) {

        BOOperationsInspectionsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInspectionsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = operationsPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible()
                .selectSearchTimeframe(data.getTimeFrame())
                .setTimeFrame(data.getFromTime(), data.getToTime())
                .searchInspectionByNumber(data.getInspectionNum1());
        Assert.assertTrue(inspectionsWebPage.inspectionExists(data.getInspectionNum1()));
        DuplicateInspectionsWebPage duplicateInspectionsWebPage = inspectionsWebPage.clickDuplicateByVINandROLink();
        duplicateInspectionsWebPage.switchToDuplicateInspectionPage();
        Assert.assertTrue(duplicateInspectionsWebPage.isVINdisplayed(data.getVIN()), "The VIN has not been displayed");
        Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum1(), data.getROnum()),
                "The duplicate by VIN and RO inspection #1 has not been displayed");
        Assert.assertTrue(duplicateInspectionsWebPage.isROdisplayedForInspection(data.getInspectionNum2(), data.getROnum()),
                "The duplicate by VIN and RO inspection #2 has not been displayed");
    }
}
