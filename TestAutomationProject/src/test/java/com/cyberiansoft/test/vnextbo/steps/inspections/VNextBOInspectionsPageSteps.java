package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class VNextBOInspectionsPageSteps {

    private static VNextBOInspectionsWebPage inspectionsPage;
    private static VNextBOInspectionsAdvancedSearchSteps vNextBOInspectionsAdvancedSearchSteps;
    private static WebDriver webDriver;

    public VNextBOInspectionsPageSteps(WebDriver driver) {

        webDriver = driver;
        inspectionsPage = new VNextBOInspectionsWebPage(driver);;
    }

    public VNextBOInspectionsApprovalWebPage clickTheApproveInspectionButton() {
        Utils.clickElement(inspectionsPage.getApproveInspectionIcon());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public static void clickClearFilterIcon() {
        Utils.clickElement(inspectionsPage.clearFilterBtn);
    }

    public static void clickEditAdvancedSearchIcon() { Utils.clickElement(inspectionsPage.editAdvancedSearchIcon); }

    public static void clickExpandAdvancedSearchPanel() { Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchCaret); }

    public static String getCustomSearchInfoTextValue() {
        return Utils.getText(inspectionsPage.filterInfoText);
    }

    public static String getSearchFieldValue() {
        return inspectionsPage.searchFld.getAttribute("value");
    }

    public static void openAdvancedSearchForm()
    {
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchCaret);
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchIconGear);
    }

    public static void searchInspectionByText(String searchText) {
        inspectionsPage.setSearchFieldValue(searchText);
        inspectionsPage.clickSearchFilterButton();
    }

    public static List<String> getNamesOfAllInspectionsInTheList()
    {
        List<String> inspectionsNamesList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionsNamesElementsList
        ) {
            inspectionsNamesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNamesList;
    }

    public static List<String> getStatusesOfAllInspectionsInTheList()
    {
        List<String> inspectionsStatusesList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionStatusesList
        ) {
            inspectionsStatusesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsStatusesList;
    }

    public static List<String> getNumbersOfAllInspectionsInTheList()
    {
        List<String> inspectionsNumbersList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionNumbersList
        ) {
            inspectionsNumbersList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNumbersList;
    }

    public static void advancedSearchInspectionByCustomer(String customerName) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchAutocompleteField("Customer", customerName);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStatusAndInspectionNumber(String inspectionNumber, String statusSearch) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByInspectionNumber(String inspectionNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStatus(String statusSearch) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStockNumber(String stockNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Stock#", stockNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByPONumber(String poNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("PO#", poNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByRONumber(String roNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("RO#", roNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByVIN(String VIN) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void findInspectionByCustomTimeFrame(String timeFrame) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", timeFrame);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void findInspectionByCustomTimeFrameAndNumber(String inspectionId, String fromDate, String toDate) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#", inspectionId);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", "Custom");
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("From", fromDate);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("To", toDate);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void deleteSavedAdvancedSearchFilter(String filterName) {
        inspectionsPage.openSavedAdvancedSearchFilter(filterName);
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    public static String getNotFoundInspectionMessage()
    {
        return  Utils.getText(inspectionsPage.howToCreateInspectionLink);
    }

    public static String getSelectedInspectionCustomerName() {
        return Utils.getText(inspectionsPage.selectedInspectionCustomerName);
    }

    public static String getSelectedInspectionParameterValueByName(String parameterName) {
        return Utils.getText(inspectionsPage.selectedInspectionFieldValueByName(parameterName));
    }
}