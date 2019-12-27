package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class VNextBOInspectionsPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickTheApproveInspectionButton() {

        Utils.clickElement(new VNextBOInspectionsWebPage().getApproveInspectionIcon());
    }

    public static void clickClearFilterIcon() {

        Utils.clickElement(new VNextBOInspectionsWebPage().clearFilterBtn);
    }

    public static void clickEditAdvancedSearchIcon() {

        Utils.clickElement(new VNextBOInspectionsWebPage().editAdvancedSearchIcon);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickExpandAdvancedSearchPanel() {

        Utils.clickElement(new VNextBOInspectionsWebPage().searchFieldAdvancedSearchCaret);
    }

    public static void clickInspectionApproveButton() {

        Utils.clickElement(new VNextBOInspectionsWebPage().approveInspectionIcon);
    }

    public static void clickArchiveIcon() {

        Utils.clickElement(new VNextBOInspectionsWebPage().archiveIcon);
    }

    public static void clickUnArchiveIcon() {

        Utils.clickElement(new VNextBOInspectionsWebPage().unArchiveIcon);
    }

    public static void clickInspectionImageZoomIcon() {

        Utils.clickElement(new VNextBOInspectionsWebPage().inspectionImageZoomIcon);
    }

    public static void clickInspectionNotesIcon() {

        Utils.clickElement(new VNextBOInspectionsWebPage().inspectionNotesIcon);
    }

    public static void clickPrintSupplementButton() {

        Utils.clickElement(new VNextBOInspectionsWebPage().printSupplementIcon);
    }

    public static void clickPrintInspectionButton() {

        Utils.clickElement(new VNextBOInspectionsWebPage().printInspectionIcon);
    }

    public static String getCustomSearchInfoTextValue() {

        return Utils.getText(new VNextBOInspectionsWebPage().filterInfoText);
    }

    public static String getSearchFieldValue() {

        return new VNextBOInspectionsWebPage().searchFld.getAttribute("value");
    }

    public static String getInspectionStatus(String inspectionNumber) {

        return Utils.getText(new VNextBOInspectionsWebPage().inspectionStatusByInspectionNumber(inspectionNumber));
    }

    public static String getSelectedInspectionArchivingReason() {

        return Utils.getText(new VNextBOInspectionsWebPage().selectedInspectionArchivedReason);
    }

    public static void selectArchiveReason(String reason) {

        Utils.clickWithJS(new VNextBOInspectionsWebPage().archivingReasonByName(reason));
    }

    public static void openAdvancedSearchForm() {

        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage();
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchCaret);
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchIconGear);
    }

    public static void searchInspectionByText(String searchText) {

        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage();
        inspectionsPage.setSearchFieldValue(searchText);
        inspectionsPage.clickSearchFilterButton();
    }

    public static List<String> getNamesOfAllInspectionsInTheList() {

        List<String> inspectionsNamesList = new ArrayList<>();
        for (WebElement inspectionRow: new VNextBOInspectionsWebPage().inspectionsNamesElementsList
        ) {
            inspectionsNamesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNamesList;
    }

    public static List<String> getStatusesOfAllInspectionsInTheList() {

        List<String> inspectionsStatusesList = new ArrayList<>();
        for (WebElement inspectionRow: new VNextBOInspectionsWebPage().inspectionStatusesList
        ) {
            inspectionsStatusesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsStatusesList;
    }

    public static List<String> getNumbersOfAllInspectionsInTheList() {

        List<String> inspectionsNumbersList = new ArrayList<>();
        for (WebElement inspectionRow: new VNextBOInspectionsWebPage().inspectionNumbersList
        ) {
            inspectionsNumbersList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNumbersList;
    }

    public static void advancedSearchInspectionByCustomer(String customerName) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchAutocompleteField("Customer", customerName);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStatusAndInspectionNumber(String inspectionNumber, String statusSearch) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByInspectionNumber(String inspectionNumber) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStatus(String statusSearch) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStockNumber(String stockNumber) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Stock#", stockNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByPONumber(String poNumber) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("PO#", poNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByRONumber(String roNumber) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("RO#", roNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByVIN(String VIN) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void searchInspectionByCustomTimeFrame(String timeFrame) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", timeFrame);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void searchInspectionByCustomTimeFrameAndNumber(String inspectionId, String fromDate, String toDate) {

        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#", inspectionId);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", "Custom");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("From", fromDate);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("To", toDate);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void deleteSavedAdvancedSearchFilter(String filterName) {

        new VNextBOInspectionsWebPage().openSavedAdvancedSearchFilter(filterName);
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    public static String getNotFoundInspectionMessage() {

        return  Utils.getText(new VNextBOInspectionsWebPage().howToCreateInspectionLink);
    }

    public static String getSelectedInspectionCustomerName() {

        return Utils.getText(new VNextBOInspectionsWebPage().selectedInspectionCustomerName);
    }

    public static String getSelectedInspectionParameterValueByName(String parameterName) {

        return Utils.getText(new VNextBOInspectionsWebPage().selectedInspectionFieldValueByName(parameterName));
    }

    public static void waitUntilInspectionsPageIsOpened() {
        VNextBOInspectionsWebPage inspectionsWebPage = new VNextBOInspectionsWebPage();
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(inspectionsWebPage.getInspectionsList()));
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(inspectionsWebPage.getInspectionDetailsPanel()));
    }
}