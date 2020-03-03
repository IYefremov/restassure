package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class VNextBOROPageStepsNew extends VNextBOBaseWebPageSteps {

    static String fromDate = LocalDate.now().minusYears(8).format(DateTimeFormatter.ofPattern("M/d/yyyy"));
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

    public static void searchOrdersByOrderNumber(String orderNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        VNextBOROAdvancedSearchDialogStepsNew.setHasThisTextField(orderNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame(fromDate, toDate);
        VNextBOROAdvancedSearchDialogStepsNew.setRepairStatusField("All");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
        WaitUtilsWebDriver.waitABit(2000);
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

    public static List<String> getArbitrationDatesList() {

        return Utils.getText(new VNextBOROWebPageNew().getArbitrationDatesList());
    }

    public static void openOrderDetailsByNumberInList(int rowNumber) {

        VNextBOROWebPageNew repairOrdersPage = new VNextBOROWebPageNew();
        WaitUtilsWebDriver.waitForVisibility(repairOrdersPage.getWoNumbersList().get(rowNumber));
        WaitUtilsWebDriver.waitForElementNotToBeStale(repairOrdersPage.getWoNumbersList().get(rowNumber));
        Utils.clickElement(repairOrdersPage.getWoNumbersList().get(rowNumber));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void viewOrdersProblemsByOrderNumber(String orderNumber) {

        VNextBOROWebPageNew repairOrdersPage = new VNextBOROWebPageNew();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        Utils.clickElement(repairOrdersPage.actionsButtonByOrderNumber(orderNumber));
        Utils.clickElement(repairOrdersPage.getViewProblemsActionButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void switchToFilterTab(String tabName) {
        if (tabName.equals("Phases"))
            Utils.clickElement(new VNextBOROWebPageNew().getPhasesSwitcherTab());
        else if (tabName.equals("Departments"))
            Utils.clickElement(new VNextBOROWebPageNew().getDepartmentsSwitcherTab());
    }

    public static void filterOrdersByDepartment(String department) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.getDepartmentsDropdown());
        VNextBOROWebPageValidationsNew.verifyDepartmentsAllAmountsIsCorrect();
        Utils.clickWithJS(ordersPage.departmentFilterDropDownOption(department));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void filterOrdersByPhase(String phase) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.getPhasesDropdown());
        VNextBOROWebPageValidationsNew.verifyPhasesAllAmountsIsCorrect();
        Utils.clickWithJS(ordersPage.phaseFilterDropDownOption(phase));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void changeDepartmentForFirstOrder(String department) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(ordersPage.getOrdersDepartmentsList().get(0)));
        Utils.clickElement(ordersPage.getOrdersDepartmentsList().get(0));
        Utils.clickWithJS(ordersPage.orderDepartmentDropDownOption(department));
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.textToBePresentInElement(ordersPage.getOrdersDepartmentsList().get(0), department));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void openDepartmentsDropDown() {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickWithActions(ordersPage.getDepartmentsDropdown());
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(ordersPage.getOrdersAmountThroughDepartmentsList(), 2);
        if (ordersPage.getOrdersAmountThroughDepartmentsList().size() == 0) {
            Utils.clickWithActions(ordersPage.getDepartmentsDropdown());
            WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(ordersPage.getOrdersAmountThroughDepartmentsList(), 2);
        }
    }

    public static int getOrdersAmountForDepartment(String department) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        int ordersAmount = 0;
        openDepartmentsDropDown();
        if (!Utils.getText(ordersPage.ordersAmountForDepartment(department)).trim().equals(""))
            ordersAmount = Integer.parseInt(Utils.getText(ordersPage.ordersAmountForDepartment(department)).trim());
        switchToFilterTab("Departments");
        return ordersAmount;
    }

    public static int getOrdersAmountForPhaseFromTable(String phase) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        int ordersAmount = 0;
        if (!Utils.getText(ordersPage.ordersAmountForPhaseInTable(phase)).trim().equals(""))
            ordersAmount = Integer.parseInt(Utils.getText(ordersPage.ordersAmountForPhaseInTable(phase)).trim());
        return ordersAmount;
    }

    public static void checkInOrder(String orderNumber) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.actionsButtonByOrderNumber(orderNumber));
        VNextBOROWebPageValidationsNew.verifyCheckInCheckOutActionButtons(false);
        Utils.clickElement(ordersPage.getCheckInActionButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void checkOutOrder(String orderNumber) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.actionsButtonByOrderNumber(orderNumber));
        VNextBOROWebPageValidationsNew.verifyCheckInCheckOutActionButtons(true);
        Utils.clickElement(ordersPage.getCheckOutActionButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void hideDisplayOrderNote(boolean displayNote) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.getOrderNoteIcon());
        if (displayNote) {
            WaitUtilsWebDriver.waitForVisibility(ordersPage.getOrderNoteText());
            VNextBOROWebPageValidationsNew.verifyNotePopUpIsDisplayed(true);
        }
        else {
            WaitUtilsWebDriver.waitForInvisibility(ordersPage.getOrderNoteText());
            VNextBOROWebPageValidationsNew.verifyNotePopUpIsDisplayed(false);
        }
    }

    public static void completeCurrentPhaseForFirstOrder() {

        Utils.clickElement(new VNextBOROWebPageNew().getOrdersPhasesList().get(0));
        Utils.clickElement(new VNextBOROWebPageNew().getCompleteCurrentPhaseActionButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void openFirstOrderNotes() {

        Utils.clickElement(new VNextBOROWebPageNew().getOrderNoteText());
    }

    public static void addNoteForFirstOrderAndNotSaveWItXIcon(String noteText) {

        openFirstOrderNotes();
        VNextBONotesDialogStepsNew.addNote(noteText, false);
    }

    public static void addNoteForFirstOrder(String noteText) {

        openFirstOrderNotes();
        VNextBONotesDialogStepsNew.addNote(noteText, true);
    }

    public static void changeStockNumberForFirstOrder(String newStockNumber) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.getStockNumbersList().get(0));
        Utils.clearAndType(ordersPage.getStockNumbersList().get(0), newStockNumber);
        Utils.clickElement(ordersPage.getDepartmentsSwitcherTab());
    }

    public static void changeRoNumberForFirstOrder(String newRoNumber) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.getRoNumbersList().get(0));
        Utils.clearAndType(ordersPage.getRoNumbersList().get(0), newRoNumber);
        Utils.clickElement(ordersPage.getDepartmentsSwitcherTab());
    }

    public static void changePoNumberForFirstOrder(String newPoNumber) {

        VNextBOROWebPageNew ordersPage = new VNextBOROWebPageNew();
        Utils.clickElement(ordersPage.getPoNumbersList().get(0));
        Utils.clearAndType(ordersPage.getPoNumbersList().get(0), newPoNumber);
        Utils.clickElement(ordersPage.getDepartmentsSwitcherTab());
    }

    public static void openChangeTechnicianDialogForFirstOrder() {

        Utils.clickElement(new VNextBOROWebPageNew().getOrdersTechniciansList().get(0));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
