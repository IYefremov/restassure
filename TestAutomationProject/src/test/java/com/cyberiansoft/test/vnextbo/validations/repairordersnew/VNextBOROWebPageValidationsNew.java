package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.enums.DateUtils;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOROWebPageValidationsNew extends VNextBOBaseWebPageValidations {

    public static void verifyCustomersAreCorrectInTheTable(String expectedCustomer) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement customer: new VNextBOROWebPageNew().getOrdersCustomersList()) {
                Assert.assertEquals(Utils.getText(customer), expectedCustomer, "Customer hasn't been correct");
            }
        }
    }

    public static void verifySearchResultByService(String expectedServiceName) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
            VNextBORODetailsStepsNew.expandAllPhases();
            VNextBORODetailsValidationsNew.verifyServiceOrTaskDescriptionsContainText(expectedServiceName);
            Utils.goToPreviousPage();
        }
    }

    public static void verifyEmployeesAreCorrectInTheTable(String expectedEmployee) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement technician: new VNextBOROWebPageNew().getOrdersTechniciansList()) {
                Assert.assertTrue(Utils.getText(technician).contains(expectedEmployee),
                        "Employee " + Utils.getText(technician) + "hasn't been correct");
            }
        }
    }

    public static void verifyPhasesAreCorrectInTheTable(String expectedPhase) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement phase: new VNextBOROWebPageNew().getOrdersPhasesList()) {
                Assert.assertEquals(Utils.getText(phase), expectedPhase, "Phase hasn't been correct");
            }
        }
    }

    public static void verifyDepartmentsAreCorrectInTheTable(String expectedDepartment) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement department: new VNextBOROWebPageNew().getOrdersDepartmentsList()) {
                Assert.assertEquals(Utils.getText(department), expectedDepartment, "Department hasn't been correct");
            }
        }
    }

    public static void verifyWoTypesAreCorrectInTheTable(String expectedWoType) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement woType: new VNextBOROWebPageNew().getWoTypesList()) {
                Assert.assertEquals(Utils.getText(woType), expectedWoType, "WO type hasn't been correct");
            }
        }
    }

    public static void verifyWoNumbersAreCorrectInTheTable(String expectedWoNumber) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement woNumber: new VNextBOROWebPageNew().getWoNumbersList()) {
                Assert.assertEquals(Utils.getText(woNumber), expectedWoNumber, "WO number hasn't been correct");
            }
        }
    }

    public static void verifyRoNumbersAreCorrectInTheTable(String expectedRoNumber) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement roNumber: new VNextBOROWebPageNew().getRoNumbersList()) {
                Assert.assertEquals(Utils.getInputFieldValue(roNumber), expectedRoNumber, "RO number hasn't been correct");
            }
        }
    }

    public static void verifyStockNumbersAreCorrectInTheTable(String expectedStockNumber) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement stockNumber: new VNextBOROWebPageNew().getStockNumbersList()) {
                Assert.assertEquals(Utils.getInputFieldValue(stockNumber), expectedStockNumber, "Stock number hasn't been correct");
            }
        }
    }

    public static void verifyVinNumbersAreCorrectInTheTable(String expectedVinNumber) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            for (WebElement vinNumber: new VNextBOROWebPageNew().getVinNumbersList()) {
                Assert.assertEquals(Utils.getText(vinNumber), expectedVinNumber, "VIN number hasn't been correct");
            }
        }
    }

    public static void verifyOrderTableContainsRecords() {

        Assert.assertTrue(new VNextBOROWebPageNew().getRepairOrdersTableRowsList().size() > 0, "Orders have not been displayed");
    }

    public static void verifyOrdersTableAfterSearch() {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else verifyOrderTableContainsRecords();
    }

    public static void verifyProblemIndicatorIsDisplayedForEachRecord() {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed()) verifyNotFoundMessageIsCorrect();
        else {
            Assert.assertEquals(new VNextBOROWebPageNew().getRepairOrdersTableRowsList().size(), new VNextBOROWebPageNew().getProblemIndicatorsList().size(),
                    "Not all orders has had Problems indicator");
        }
    }

    public static void verifyProblemIndicatorIsDisplayedForOrder(String orderNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOROWebPageNew().problemIndicatorByOrderNumber(orderNumber)),
                "Problems indicator hasn't been displayed for the order " + orderNumber);
    }

    public static void verifyNotFoundMessageIsCorrect() {

        Assert.assertEquals(Utils.getText(new VNextBOROWebPageNew().getNoRecordsFoundMessage()), "No records found. Please refine search criteria ...",
                    "No records found message hasn't been displayed or has been incorrect");
    }

    public static void verifySortingByStartDateIsCorrect(List<WebElement> actualStartDatesElementsList, List<Date> expectedStartDatesList) throws ParseException {

        List<Date> actualStartDatesList = new ArrayList<>();
        for (WebElement element : actualStartDatesElementsList) {
            Date parse = new SimpleDateFormat("MM/dd/yyyy").parse(element.getText());
            actualStartDatesList.add(parse);
        }
        Assert.assertEquals(actualStartDatesList, expectedStartDatesList, "Orders have been sorted incorrectly");
    }

    public static void verifyOrdersAreSortedByPriorityAndStartDateFromOldestToNewest() throws ParseException {

        List<WebElement> highPriorityDates = new VNextBOROWebPageNew().getHighPriorityOrdersStartDatesList();
        if (highPriorityDates.size() > 0) {
            verifySortingByStartDateIsCorrect(new VNextBOROWebPageNew().getHighPriorityOrdersStartDatesList(),
                    VNextBOROPageStepsNew.getAscSortedStartDatesListValues(highPriorityDates));
        }

        List<WebElement> normalPriorityDates = new VNextBOROWebPageNew().getNormalPriorityOrdersStartDatesList();
        if (normalPriorityDates.size() > 0) {
            verifySortingByStartDateIsCorrect(new VNextBOROWebPageNew().getNormalPriorityOrdersStartDatesList(),
                    VNextBOROPageStepsNew.getAscSortedStartDatesListValues(normalPriorityDates));
        }

        List<WebElement> lowPriorityDates = new VNextBOROWebPageNew().getLowPriorityOrdersStartDatesList();
        if (highPriorityDates.size() > 0) {
            verifySortingByStartDateIsCorrect(new VNextBOROWebPageNew().getLowPriorityOrdersStartDatesList(),
                    VNextBOROPageStepsNew.getAscSortedStartDatesListValues(lowPriorityDates));
        }
    }

    public static void verifyOrdersAreSortedByPriorityAndStartDateFromNewestToOldest() throws ParseException {

        List<WebElement> highPriorityDates = new VNextBOROWebPageNew().getHighPriorityOrdersStartDatesList();
        if (highPriorityDates.size() > 0) {
            verifySortingByStartDateIsCorrect(new VNextBOROWebPageNew().getHighPriorityOrdersStartDatesList(),
                    VNextBOROPageStepsNew.getDescSortedStartDatesListValues(highPriorityDates));
        }

        List<WebElement> normalPriorityDates = new VNextBOROWebPageNew().getNormalPriorityOrdersStartDatesList();
        if (normalPriorityDates.size() > 0) {
            verifySortingByStartDateIsCorrect(new VNextBOROWebPageNew().getNormalPriorityOrdersStartDatesList(),
                    VNextBOROPageStepsNew.getDescSortedStartDatesListValues(normalPriorityDates));
        }

        List<WebElement> lowPriorityDates = new VNextBOROWebPageNew().getLowPriorityOrdersStartDatesList();
        if (highPriorityDates.size() > 0) {
            verifySortingByStartDateIsCorrect(new VNextBOROWebPageNew().getLowPriorityOrdersStartDatesList(),
                    VNextBOROPageStepsNew.getDescSortedStartDatesListValues(lowPriorityDates));
        }
    }

    public static void verifyOrdersAfterSearchByTimeFrame(LocalDate dateBeforeCurrentDate) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed())
            Assert.assertEquals(Utils.getText(new VNextBOROWebPageNew().getNoRecordsFoundMessage()), "No records found. Please refine search criteria ...",
                    "No records found message hasn't been displayed or has been incorrect");
        else {
            verifyTargetDateForOrders(dateBeforeCurrentDate, VNextBOROPageInteractions.getHighPriorityDates());
            verifyTargetDateForOrders(dateBeforeCurrentDate, VNextBOROPageInteractions.getNormalPriorityDates());
            verifyTargetDateForOrders(dateBeforeCurrentDate, VNextBOROPageInteractions.getLowPriorityDates());
        }
    }

    private static void verifyTargetDateForOrders(LocalDate dateBeforeCurrentDate, List<String> ordersDatesList) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat());
        for (int i = 1; i < ordersDatesList.size(); i++) {

            final LocalDate previous = LocalDate.parse(ordersDatesList.get(i - 1), formatter);
            final LocalDate next = LocalDate.parse(ordersDatesList.get(i), formatter);
            verifyOrderNextTargetDateIsAfterDateStarted(dateBeforeCurrentDate, next);
            if (previous.isEqual(next)) continue;
            verifyOrderTargetDateIsWithinBoundaries(previous, next);
        }
    }

    private static void verifyTargetDateForOrders(LocalDate dateStarted, LocalDate dateFinished, List<String> ordersDatesList) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat());
        for (int i = 1; i < ordersDatesList.size(); i++) {

            final LocalDate previous = LocalDate.parse(ordersDatesList.get(i - 1), formatter);
            final LocalDate next = LocalDate.parse(ordersDatesList.get(i), formatter);
            verifyOrderNextTargetDateIsAfterDateStarted(dateStarted, next);
            if (previous.isEqual(next)) continue;
            verifyOrderTargetDateIsWithinBoundaries(previous, next, dateFinished);
        }
    }

    private static void verifyOrderNextTargetDateIsAfterDateStarted(LocalDate dateStarted, LocalDate next) {

        Assert.assertTrue(next.isAfter(dateStarted),
                "The order target date is not within the given date boundaries");
    }

    private static void verifyOrderTargetDateIsWithinBoundaries(LocalDate previous, LocalDate next, LocalDate dateFinished) {

        if (previous.isBefore(next) || next.isAfter(dateFinished)) {
            Assert.fail("The order target date is not within the given date boundaries");
        }
    }

    private static void verifyOrderTargetDateIsWithinBoundaries(LocalDate previous, LocalDate next) {
        if (previous.isBefore(next)) {
            Assert.fail("The order target date is not within the given date boundaries");
        }
    }

    public static void verifyOrdersAfterSearchByTimeFrame(LocalDate dateStarted, LocalDate dateFinished) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed())
            Assert.assertEquals(Utils.getText(new VNextBOROWebPageNew().getNoRecordsFoundMessage()), "No records found. Please refine search criteria ...",
                    "No records found message hasn't been displayed or has been incorrect");
        else {
            verifyTargetDateForOrders(dateStarted, dateFinished, VNextBOROPageInteractions.getHighPriorityDates());
            verifyTargetDateForOrders(dateStarted, dateFinished, VNextBOROPageInteractions.getNormalPriorityDates());
            verifyTargetDateForOrders(dateStarted, dateFinished, VNextBOROPageInteractions.getLowPriorityDates());
        }
    }

    public static void verifySavedSearchDropDownFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROWebPageNew().getSavedSearchDropDownField()), expectedValue,
                "Saved search field has contained incorrect value");
    }

    public static void verifySavedSearchDropDownListContainsSavedSearch(String searchName) {

        List<String> savedSearchesList = new VNextBOROWebPageNew().getSavedSearchDropDownList().stream().
                map(WebElement::getText).collect(Collectors.toList());
        Assert.assertTrue(savedSearchesList.contains(searchName),
                "Saved search hasn't been presented in the saved searches list");
    }

    public static void verifyFirstOrderArbitrationDateIsMoreThanCurrentDate() {

        final LocalDate currentDate = CustomDateProvider.getCurrentDateLocalized();
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateUtils.THE_SHORTEST_DATE_FORMAT.getFormat());
        Assert.assertTrue(currentDate.isBefore(LocalDate.parse(VNextBOROPageStepsNew.getArbitrationDatesList().get(0), dateFormat)),
                "The arbitration date of the first order is not after the current date");
    }

    public static void verifyOrdersWithArbitrationDatesAreDisplayedBeforeOtherOrders() {

        boolean foundFirstEmptyOrder = false;
        for (String order : VNextBOROPageStepsNew.getArbitrationDatesList()) {
            if (foundFirstEmptyOrder && !order.isEmpty()) {
                Assert.fail("The orders with arbitration dates are not displayed before the orders without them");
            }
            if (!foundFirstEmptyOrder && order.isEmpty()) {
                foundFirstEmptyOrder = true;
            }
        }
        Assert.assertTrue(foundFirstEmptyOrder, "The orders with arbitration dates are not displayed before the orders without them");
    }
}
