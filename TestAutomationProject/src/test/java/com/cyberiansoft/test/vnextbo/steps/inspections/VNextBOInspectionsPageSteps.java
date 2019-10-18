package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class VNextBOInspectionsPageSteps extends VNextBOBaseWebPageSteps {

    public static VNextBOInspectionsApprovalWebPage clickTheApproveInspectionButton()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.getApproveInspectionIcon());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public static void clickClearFilterIcon()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.clearFilterBtn);
    }

    public static void clickEditAdvancedSearchIcon()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.editAdvancedSearchIcon);
    }

    public static void clickExpandAdvancedSearchPanel()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchCaret);
    }

    public static void clickInspectionApproveButton()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.approveInspectionIcon);
    }

    public static void clickArchiveIcon()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.archiveIcon);
    }

    public static void clickUnArchiveIcon()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.unArchiveIcon);
    }

    public static void clickInspectionImageZoomIcon()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.inspectionImageZoomIcon);
    }

    public static void clickInspectionNotesIcon()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.inspectionNotesIcon);
    }

    public static void clickPrintSupplementButton()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.printSupplementIcon);
    }

    public static void clickPrintInspectionButton()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.printInspectionIcon);
    }

    public static String getCustomSearchInfoTextValue()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.getText(inspectionsPage.filterInfoText);
    }

    public static String getSearchFieldValue()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return inspectionsPage.searchFld.getAttribute("value");
    }

    public static String getInspectionStatus(String inspectionNumber)
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.getText(inspectionsPage.inspectionStatusByInspectionNumber(inspectionNumber));
    }

    public static String getSelectedInspectionArchivingReason()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.getText(inspectionsPage.selectedInspectionArchivedReason);
    }

    public static void selectArchiveReason(String reason)
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickWithJS(inspectionsPage.archivingReasonByName(reason));
    }

    public static void openAdvancedSearchForm()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchCaret);
        Utils.clickElement(inspectionsPage.searchFieldAdvancedSearchIconGear);
    }

    public static void searchInspectionByText(String searchText)
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        inspectionsPage.setSearchFieldValue(searchText);
        inspectionsPage.clickSearchFilterButton();
    }

    public static List<String> getNamesOfAllInspectionsInTheList()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        List<String> inspectionsNamesList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionsNamesElementsList
        ) {
            inspectionsNamesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNamesList;
    }

    public static List<String> getStatusesOfAllInspectionsInTheList()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        List<String> inspectionsStatusesList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionStatusesList
        ) {
            inspectionsStatusesList.add(Utils.getText(inspectionRow));
        }
        return inspectionsStatusesList;
    }

    public static List<String> getNumbersOfAllInspectionsInTheList()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        List<String> inspectionsNumbersList = new ArrayList<>();
        for (WebElement inspectionRow: inspectionsPage.inspectionNumbersList
        ) {
            inspectionsNumbersList.add(Utils.getText(inspectionRow));
        }
        return inspectionsNumbersList;
    }

    public static void advancedSearchInspectionByCustomer(String customerName)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchAutocompleteField("Customer", customerName);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStatusAndInspectionNumber(String inspectionNumber, String statusSearch)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByInspectionNumber(String inspectionNumber)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#",inspectionNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStatus(String statusSearch)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", statusSearch);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByStockNumber(String stockNumber)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Stock#", stockNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByPONumber(String poNumber)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("PO#", poNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByRONumber(String roNumber)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("RO#", roNumber);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void advancedSearchInspectionByVIN(String VIN)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void findInspectionByCustomTimeFrame(String timeFrame)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", timeFrame);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void findInspectionByCustomTimeFrameAndNumber(String inspectionId, String fromDate, String toDate)
    {
        openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#", inspectionId);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", "Custom");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("From", fromDate);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("To", toDate);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
    }

    public static void deleteSavedAdvancedSearchFilter(String filterName)
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        inspectionsPage.openSavedAdvancedSearchFilter(filterName);
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    public static String getNotFoundInspectionMessage()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return  Utils.getText(inspectionsPage.howToCreateInspectionLink);
    }

    public static String getSelectedInspectionCustomerName()
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.getText(inspectionsPage.selectedInspectionCustomerName);
    }

    public static String getSelectedInspectionParameterValueByName(String parameterName)
    {
        VNextBOInspectionsWebPage inspectionsPage = new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.getText(inspectionsPage.selectedInspectionFieldValueByName(parameterName));
    }
}