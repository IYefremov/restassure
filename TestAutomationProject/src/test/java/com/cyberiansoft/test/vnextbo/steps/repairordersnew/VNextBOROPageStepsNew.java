package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class VNextBOROPageStepsNew {

    static String fromDate = LocalDate.now().minusYears(2).format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    static String toDate = LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("M/d/yyyy"));

    public static void searchOrdersByCustomer(String customer) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setCustomerField(customer);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByEmployee(String employee) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setEmployeeField(employee);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByPhase(String phase) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setPhaseField(phase);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByDepartment(String department) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setDepartmentField(department);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByWoType(String woType) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setWoTypeField(woType);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByWoNumber(String woNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setWoNumberField(woNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByRoNumber(String roNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setRoNumberField(roNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByStockNumber(String stockNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setStockNumberField(stockNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByVinNumber(String vinNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setVinNumberField(vinNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByRepairStatus(String repairStatus) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setRepairStatusField(repairStatus);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByPhaseStatus(String phase, String phaseStatus) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setPhaseField(phase);
        VNextBOROAdvancedSearchDialogStepsNew.setPhaseStatusField(phaseStatus);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByFlag(String flag) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setFlagField(flag);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersWithHasProblemsFlag() {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.clickHasProblemCheckBox();
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersDaysInProcess(String condition, String daysFromValue, Optional<String> daysToValue) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setDaysInProcess(condition, daysFromValue, daysToValue);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersDaysInPhase(String condition, String daysFromValue, Optional<String> daysToValue) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setDaysInPhase(condition, daysFromValue, daysToValue);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByTimeFrame(String timeFrame) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setTimeFrameField(timeFrame);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByCustomTimeFrame() {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByCustomTimeFrameWithSorting(String sortBy) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setSortByField(sortBy);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static boolean checkIfNoRecordsFoundMessageIsDisplayed() {

        return Utils.isElementDisplayed(new VNextBOROWebPageNew().getNoRecordsFoundMessage());
    }

    public static void setAllAdvancedSearchFields(VNextBOMonitorData data) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setAllFields(data);
    }

    public static void searchByAllAdvancedSearchFields(VNextBOMonitorData data) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setAllFields(data);
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void openSavedSearchesList() {

        Utils.clickElement(new VNextBOROWebPageNew().getSavedSearchDropDownField());
    }

    public static void searchBySavedAdvancedSearch(String searchName) {

        VNextBOROWebPageNew repairOrdersPage = new VNextBOROWebPageNew();
        openSavedSearchesList();
        VNextBOROWebPageValidationsNew.verifySavedSearchDropDownListContainsSavedSearch(searchName);
        Utils.clickElement(repairOrdersPage.savedSearchOptionByName(searchName));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void openSavedAdvancedSearch(String searchName) {

        VNextBOROWebPageNew repairOrdersPage = new VNextBOROWebPageNew();
        openSavedSearchesList();
        VNextBOROWebPageValidationsNew.verifySavedSearchDropDownListContainsSavedSearch(searchName);
        Utils.clickElement(repairOrdersPage.savedSearchOptionByName(searchName));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        Utils.clickElement(repairOrdersPage.getEditSavedSearchPencilIcon());
    }

    public static List<Date> getDescSortedStartDatesListValues(List<WebElement> startDatesWebElementsList) throws ParseException {

        List<Date> notSortedDates = new ArrayList<>();
        for (WebElement element : startDatesWebElementsList) {
            Date parse = new SimpleDateFormat("MM/dd/yyyy").parse(element.getText());
            notSortedDates.add(parse);
        }
        return notSortedDates.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    public static List<Date> getAscSortedStartDatesListValues(List<WebElement> startDatesWebElementsList) throws ParseException{

        List<Date> notSortedDates = new ArrayList<>();
        for (WebElement element : startDatesWebElementsList) {
            Date parse = new SimpleDateFormat("MM/dd/yyyy").parse(element.getText());
            notSortedDates.add(parse);
        }
        return notSortedDates.stream().sorted().collect(Collectors.toList());
    }
}
