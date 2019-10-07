package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Optional;

import java.util.ArrayList;
import java.util.List;

public class VNextBOInspectionsPageSteps {

    private VNextBOInspectionsWebPage inspectionsPage;
    private VNextBOInspectionsAdvancedSearchSteps vNextBOInspectionsAdvancedSearchSteps;
    private WebDriver webDriver;

    public VNextBOInspectionsPageSteps(WebDriver driver) {

        webDriver = driver;
        inspectionsPage = new VNextBOInspectionsWebPage(driver);;
    }

    public VNextBOInspectionsApprovalWebPage clickTheApproveInspectionButton() {
        Utils.clickElement(inspectionsPage.getApproveInspectionIcon());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public void clickClearFilterIcon() {
        Utils.clickElement(inspectionsPage.clearFilterBtn);
    }

    public void clickEditAdvancedSearchIcon() { Utils.clickElement(inspectionsPage.editAdvancedSearchIcon); }

    public void clickExpandAdvancedSearchPanel() { Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchCaret); }

    public String getCustomSearchInfoTextValue() {
        return Utils.getText(inspectionsPage.filterInfoText);
    }

    public String getSearchFieldValue() {
        return inspectionsPage.searchFld.getAttribute("value");
    }

    public void openAdvancedSearchForm()
    {
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchCaret);
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchIconGear);
    }

    public void searchInspectionByText(String searchText) {
        inspectionsPage.setSearchFieldValue(searchText);
        inspectionsPage.clickSearchFilterButton();
    }

    public List<String> getNamesOfAllInspectionsInTheList()
    {
        List<String> inspectionsNamesList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionsNamesElementsList
        ) {
            inspectionsNamesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNamesList;
    }

    public List<String> getStatusesOfAllInspectionsInTheList()
    {
        List<String> inspectionsStatusesList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionStatusesList
        ) {
            inspectionsStatusesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsStatusesList;
    }

    public List<String> getNumbersOfAllInspectionsInTheList()
    {
        List<String> inspectionsNumbersList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionNumbersList
        ) {
            inspectionsNumbersList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNumbersList;
    }

    public void advancedSearchInspectionByCustomer(String customerName) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchAutocompleteField("Customer", customerName);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void advancedSearchInspectionByStatusAndInspectionNumber(String inspectionNumber, String statusSearch) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void advancedSearchInspectionByInspectionNumber(String inspectionNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void advancedSearchInspectionByStatus(String statusSearch) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void advancedSearchInspectionByStockNumber(String stockNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Stock#", stockNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void advancedSearchInspectionByPONumber(String poNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("PO#", poNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void advancedSearchInspectionByRONumber(String roNumber) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("RO#", roNumber);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void advancedSearchInspectionByVIN(String VIN) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void findInspectionByCustomTimeFrame(String timeFrame) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", timeFrame);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void findInspectionByCustomTimeFrameAndNumber(String inspectionId, String fromDate, String toDate) {
        openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#", inspectionId);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", "Custom");
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("From", fromDate);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("To", toDate);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public void deleteSavedAdvancedSearchFilter(String filterName) {
        inspectionsPage.openSavedAdvancedSearchFilter(filterName);
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        vNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    public String getNotFoundInspectionMessage()
    {
        return  Utils.getText(inspectionsPage.howToCreateInspectionLink);
    }

    public String getSelectedInspectionCustomerName() {
        return Utils.getText(inspectionsPage.selectedInspectionCustomerName);
    }

    public String getSelectedInspectionParameterValueByName(String parameterName) {
        return Utils.getText(inspectionsPage.selectedInspectionFieldValueByName(parameterName));
    }
}