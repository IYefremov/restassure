package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.enums.DateUtils;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VNextBOROWebPageValidationsNew {

    public static void verifyCustomersAreCorrectInTheTable(String expectedCustomer) {

        for (WebElement customer: new VNextBOROWebPageNew().getOrdersCustomersList()) {
            Assert.assertEquals(Utils.getText(customer), expectedCustomer, "Customer hasn't been correct");
        }
    }

    public static void verifyEmployeesAreCorrectInTheTable(String expectedEmployee) {

        for (WebElement technician: new VNextBOROWebPageNew().getOrdersTechniciansList()) {
            Assert.assertTrue(Utils.getText(technician).contains(expectedEmployee),
                    "Employee " + Utils.getText(technician) + "hasn't been correct");
        }
    }

    public static void verifyPhasesAreCorrectInTheTable(String expectedPhase) {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed())
            Assert.assertEquals(Utils.getText(new VNextBOROWebPageNew().getNoRecordsFoundMessage()), "No records found. Please refine search criteria ...",
                    "No records found message hasn't been displayed or has been incorrect");
        else
            for (WebElement phase: new VNextBOROWebPageNew().getOrdersPhasesList()) {
                Assert.assertEquals(Utils.getText(phase), expectedPhase, "Phase hasn't been correct");
                }
    }

    public static void verifyDepartmentsAreCorrectInTheTable(String expectedDepartment) {

        for (WebElement department: new VNextBOROWebPageNew().getOrdersDepartmentsList()) {
            Assert.assertEquals(Utils.getText(department), expectedDepartment, "Department hasn't been correct");
        }
    }

    public static void verifyWoTypesAreCorrectInTheTable(String expectedWoType) {

        for (WebElement woType: new VNextBOROWebPageNew().getWoTypesList()) {
            Assert.assertEquals(Utils.getText(woType), expectedWoType, "WO type hasn't been correct");
        }
    }

    public static void verifyWoNumbersAreCorrectInTheTable(String expectedWoNumber) {

        for (WebElement woNumber: new VNextBOROWebPageNew().getWoNumbersList()) {
            Assert.assertEquals(Utils.getText(woNumber), expectedWoNumber, "WO number hasn't been correct");
        }
    }

    public static void verifyRoNumbersAreCorrectInTheTable(String expectedRoNumber) {

        for (WebElement roNumber: new VNextBOROWebPageNew().getRoNumbersList()) {
            Assert.assertEquals(Utils.getInputFieldValue(roNumber), expectedRoNumber, "RO number hasn't been correct");
        }
    }

    public static void verifyStockNumbersAreCorrectInTheTable(String expectedStockNumber) {

        for (WebElement stockNumber: new VNextBOROWebPageNew().getStockNumbersList()) {
            Assert.assertEquals(Utils.getInputFieldValue(stockNumber), expectedStockNumber, "Stock number hasn't been correct");
        }
    }

    public static void verifyVinNumbersAreCorrectInTheTable(String expectedVinNumber) {

        for (WebElement vinNumber: new VNextBOROWebPageNew().getVinNumbersList()) {
            Assert.assertEquals(Utils.getText(vinNumber), expectedVinNumber, "VIN number hasn't been correct");
        }
    }

    public static void verifyOrderTableContainsRecords() {

        Assert.assertTrue(new VNextBOROWebPageNew().getRepairOrdersTableRowsList().size() > 0, "Orders have not been displayed");
    }

    public static void verifyOrdersTableAfterSearch() {

        if (VNextBOROPageStepsNew.checkIfNoRecordsFoundMessageIsDisplayed())
            Assert.assertEquals(Utils.getText(new VNextBOROWebPageNew().getNoRecordsFoundMessage()), "No records found. Please refine search criteria ...",
                    "No records found message hasn't been displayed or has been incorrect");
        else
            verifyOrderTableContainsRecords();
    }

    public static void verifyProblemIndicatorIsDisplayedForEachRecord() {

        Assert.assertEquals(new VNextBOROWebPageNew().getRepairOrdersTableRowsList().size(), new VNextBOROWebPageNew().getProblemIndicatorsList().size(),
                "Not all orders has had Problems indicator");
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
}
