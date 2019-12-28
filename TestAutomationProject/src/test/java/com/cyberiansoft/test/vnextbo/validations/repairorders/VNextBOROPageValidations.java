package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.DateUtils;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VNextBOROPageValidations {

    public static void verifyAnotherTechnicianIsDisplayed(String woNumber, String prevTechnician) {
        Assert.assertNotEquals(VNextBOROPageInteractions.getTechniciansValueForWO(woNumber), prevTechnician,
                "The technician hasn't been changed");
    }

    public static void verifyAdvancedSearchDialogIsDisplayed() {
        Assert.assertTrue(isAdvancedSearchDialogDisplayed(), "The advanced search dialog is not opened");
    }

    public static void verifyAdvancedSearchDialogIsClosed() {
        Assert.assertTrue(isAdvancedSearchDialogClosed(), "The advanced search dialog is not closed");
    }

    private static boolean isAdvancedSearchDialogDisplayed() {
        return Utils.isElementWithAttributeContainingValueDisplayed(
                new VNextBOROAdvancedSearchDialog().getAdvancedSearchDialogContainer(), "style", "display: block", 5);
    }

    private static boolean isAdvancedSearchDialogClosed() {
        return Utils.isElementWithAttributeContainingValueDisplayed(
                new VNextBOROAdvancedSearchDialog().getAdvancedSearchDialogContainer(), "style", "display: none", 5);
    }

    public static boolean isNoteForWorkOrderDisplayed(String woNumber, boolean expected) {
        return isArrowDisplayed(woNumber, "/../../..//div[@class='dark box']", expected);
    }

    public static void verifyNoteTextIsDisplayed(String noteMessage) {
        Assert.assertEquals(VNextBOROPageInteractions.getOrderNoteText(), noteMessage,
                "The order note text hasn't been displayed");
    }

    public static boolean isSavedSearchEditIconDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getSavedSearchEditIcon(), expected, 5);
    }

    public static boolean isWorkOrderDisplayedByVin(String vin, boolean expected) {
        return isWorkOrderDisplayed(vin, expected);
    }

    private static boolean isWorkOrderDisplayed(String text, boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                By.xpath("//strong[contains(text(), \"" + text + "\")]"), expected, 10);
    }

    public static boolean areTableHeaderTitlesDisplayed(List<String> titles, List<String> repeaterTitles) {
        List<String> extracted = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOROWebPage().getTableHeader())
                .forEach((title) -> extracted.add(title.getText()));

        final String[] strings = extracted.get(3).split("\n");
        extracted.remove(3);

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(extracted.get(i));
            System.out.println(titles.get(i));
            System.out.println(extracted.get(i).equals(titles.get(i)));
            System.out.println("&&&&&&&&&&&&\n");
        }
        return extracted.containsAll(titles) && Arrays.asList(strings).containsAll(repeaterTitles);
    }

    public static boolean isPrevButtonDisabled() {
        return Utils.isElementWithAttributeContainingValueDisplayed(new VNextBOROWebPage().getPrevButton(),
                "disabled", "true", 5);
    }

    public static boolean isSavedSearchContainerDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getSavedSearchContainer(), expected);
    }

    public static boolean isDepartmentDropdownDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getDepartmentWideDropdownActive(), expected);
    }

    public static boolean isPhasesDropdownDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getPhasesWideDropdownActive(), expected);
    }

    public static boolean isSearchInputFieldDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getSearchInput(), expected);
    }

    public static boolean isPhasesTabDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getPhasesWideTab(), expected);
    }

    public static boolean isDepartmentsTabDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getDepartmentsWideTab(), expected);
    }

    public static boolean isWorkOrderDisplayedByOrderNumber(String orderNumber, boolean expected) {
        return isWorkOrderDisplayed(orderNumber, expected);
    }

    public static boolean isWorkOrderDisplayedByRoNumber(String roNumber, boolean expected) {
        return isWorkOrderDisplayed(roNumber, expected);
    }

    public static boolean isWorkOrderDisplayedByFirstName(String firstName, boolean expected) {
        return isWorkOrderDisplayedByPartialText(firstName, expected);
    }

    public static boolean isWorkOrderDisplayedByLastName(String lastName, boolean expected) {
        return isWorkOrderDisplayedByPartialText(lastName, expected);
    }

    public static boolean isWorkOrderDisplayedByName(String name, boolean expected) {
        return isWorkOrderDisplayed(name, expected);
    }

    public static boolean isWorkOrderDisplayedAfterSearchByEmail(String name, boolean expected) {
        return isWorkOrderDisplayed(name, expected);
    }

    public static boolean isWorkOrderDisplayedAfterSearchByCompanyName(String name, boolean expected) {
        return isWorkOrderDisplayed(name, expected);
    }

    private static boolean isWorkOrderDisplayedByPartialText(String text, boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getTableBody()
                .findElement(By.xpath(".//strong[contains(text(), '" + text + "')]")), expected, 5);
    }

    public static boolean isWoTypeDisplayed(String woType) {
        return WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage()
                .getTableBody())
                .findElements(By.xpath(".//div"))
                .stream()
                .anyMatch(e -> e.getText().contains(woType));
    }

    public static boolean isArrowDownDisplayed(String wo, boolean expected) {
        return isArrowDisplayed(wo, "//i[@class='icon-arrow-down']", expected);
    }

    public static boolean isArrowUpDisplayed(String wo, boolean expected) {
        return isArrowDisplayed(wo, "//i[@class='icon-arrow-up']", expected);
    }

    private static boolean isArrowDisplayed(String wo, String arrow, boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(By.xpath("//strong[text()='" +
                wo + "']/../../.." + arrow), expected, 5);
    }

    public static boolean isRepairOrderPresentInTable(String orderNumber) {
        return new VNextBOROWebPage().getRepairOrdersTableBody()
                .findElements(By.xpath(".//strong[text()='" + orderNumber + "']")).size() > 0;
    }

    public static boolean isPoNumClickable(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeClickable(new VNextBOROWebPage().getPoNumTitle(), expected, 5);
    }

    public static boolean areValuesDisplayed(List<WebElement> values) {
        try {
            WaitUtilsWebDriver.getWebDriverWait(5)
                    .until((ExpectedCondition<Boolean>) sum -> !values.get(0).getText().equals(""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDepartmentNarrowScreenClickable() {
        return isScreenClickable(new VNextBOROWebPage().getDepartmentsNarrowScreen());
    }

    public static boolean isPhasesNarrowScreenClickable() {
        return isScreenClickable(new VNextBOROWebPage().getPhasesNarrowScreen());
    }

    private static boolean isScreenClickable(WebElement phasesNarrowScreen) {
        return Utils.isElementClickable(phasesNarrowScreen);
    }

    public static boolean isTableDisplayed() {
        return Utils.isElementDisplayed(new VNextBOROWebPage().getTable(), 5);
    }

    public static void verifyOrdersAreDisplayed() {
        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(true),
                    "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    public static boolean isTextNoRecordsDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getNoRecordsFound(), expected, 3);
    }

    public static boolean isAdvancedSearchCaretDisplayed() {
        return Utils.isElementDisplayed(new VNextBOROWebPage().getAdvancedSearchCaret());
    }

    public static boolean isProblemIndicatorDisplayedForOrder(String order) {
        return Utils.isElementDisplayed(new VNextBOROWebPage().getProblemIndicatorForOrder(order), 4);
    }

    private static void verifyDateOrNotStartedPhaseForOrderWithoutDate(LocalDate dateBeforeCurrentDate) {
        final List<String> ordersDatesList = VNextBOROPageInteractions.getOrdersTargetDatesList();
        final List<String> ordersCurrentPhaseList = VNextBOROPageInteractions.getOrdersCurrentPhaseList();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat());

        ordersDatesList.forEach(System.out::println);
        ordersCurrentPhaseList.forEach(System.out::println);
        for (int i = 0; i < ordersDatesList.size(); i++) {
            if (ordersDatesList.get(i).isEmpty()) {
                Assert.assertEquals("Not Started", ordersCurrentPhaseList.get(i),
                        "The current phase of the order should be 'Not started'");
            } else {
                Assert.assertTrue(LocalDate.parse(ordersDatesList.get(i), formatter).isAfter(dateBeforeCurrentDate),
                        "The order started date is not within the given date boundaries");
            }
        }
    }

    private static void verifyTargetDateForOrders(LocalDate dateBeforeCurrentDate, List<String> ordersDatesList) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat());

        for (int i = 0; i < ordersDatesList.size(); i++) {
            if (i == 0) {
                continue;
            }
            final LocalDate previous = LocalDate.parse(ordersDatesList.get(i - 1), formatter);
            final LocalDate next = LocalDate.parse(ordersDatesList.get(i), formatter);
            verifyOrderNextTargetDateIsAfterDateStarted(dateBeforeCurrentDate, next);
            if (previous.isEqual(next)) {
                continue;
            }
            verifyOrderTargetDateIsWithinBoundaries(previous, next);
        }
    }

    private static void verifyTargetDateForOrders(LocalDate dateStarted, LocalDate dateFinished, List<String> ordersDatesList) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat());

        for (int i = 0; i < ordersDatesList.size(); i++) {
            if (i == 0) {
                continue;
            }
            final LocalDate previous = LocalDate.parse(ordersDatesList.get(i - 1), formatter);
            final LocalDate next = LocalDate.parse(ordersDatesList.get(i), formatter);
            verifyOrderNextTargetDateIsAfterDateStarted(dateStarted, next);
            if (previous.isEqual(next)) {
                continue;
            }
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

    private static void verifyCurrentPhaseForOrder(String currentPhase) {
        final List<String> ordersCurrentPhaseList = VNextBOROPageInteractions.getOrdersCurrentPhaseList();

        ordersCurrentPhaseList.forEach(phase -> {
            System.out.println(phase);
            if (!phase.equals("Not Started")) {
                Assert.assertEquals(currentPhase, phase,
                        "The current phase is not equal to the phase from the search criterion");
            }
        });
    }

    public static void verifyOrdersAfterSearchByDate(LocalDate dateBeforeCurrentDate) {
        if (!isTableDisplayed()) {
            Assert.assertTrue(isTextNoRecordsDisplayed(true), "The text notification is not displayed");
        } else {
            verifyDateOrNotStartedPhaseForOrderWithoutDate(dateBeforeCurrentDate);
        }
    }

    public static void verifyOrdersAfterSearchByTimeFrame(LocalDate dateBeforeCurrentDate) {
        if (!isTableDisplayed()) {
            Assert.assertTrue(isTextNoRecordsDisplayed(true), "The text notification is not displayed");
        } else {
            System.out.println("high priority");
            verifyTargetDateForOrders(dateBeforeCurrentDate, VNextBOROPageInteractions.getHighPriorityDates());
            System.out.println("normal priority");
            verifyTargetDateForOrders(dateBeforeCurrentDate, VNextBOROPageInteractions.getNormalPriorityDates());
            System.out.println("low priority");
            verifyTargetDateForOrders(dateBeforeCurrentDate, VNextBOROPageInteractions.getLowPriorityDates());
        }
    }

    public static void verifyOrdersAfterSearchByTimeFrame(LocalDate dateStarted, LocalDate dateFinished) {
        if (!isTableDisplayed()) {
            Assert.assertTrue(isTextNoRecordsDisplayed(true), "The text notification is not displayed");
        } else {
            System.out.println("high priority");
            verifyTargetDateForOrders(dateStarted, dateFinished, VNextBOROPageInteractions.getHighPriorityDates());
            System.out.println("normal priority");
            verifyTargetDateForOrders(dateStarted, dateFinished, VNextBOROPageInteractions.getNormalPriorityDates());
            System.out.println("low priority");
            verifyTargetDateForOrders(dateStarted, dateFinished, VNextBOROPageInteractions.getLowPriorityDates());
        }
    }

    public static void verifyOrdersAfterSearchByPhase(String currentPhase) {
        if (!isTableDisplayed()) {
            Assert.assertTrue(isTextNoRecordsDisplayed(true), "The text notification is not displayed");
        } else {
            verifyCurrentPhaseForOrder(currentPhase);
        }
    }

    public static void verifyAllOrdersHaveProblemIndicators() {
        if (!isTableDisplayed()) {
            Assert.assertTrue(isTextNoRecordsDisplayed(true), "The text notification is not displayed");
        } else {
            final VNextBOROWebPage roPage = new VNextBOROWebPage();
            Assert.assertEquals(roPage.getProblemIndicatorsList().size(), roPage.getOrdersDisplayedOnPage().size(),
                    "The problem indicators are not displayed for all orders");
        }
    }

    public static boolean isArbitrationDateMoreThanCurrentDate(String arbitrationDate) {
        final LocalDate currentDate = CustomDateProvider.getCurrentDateLocalized();
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateUtils.THE_SHORTEST_DATE_FORMAT.getFormat());
        return currentDate.isBefore(LocalDate.parse(arbitrationDate, dateFormat));
    }

    public static boolean areOrdersWithArbitrationDatesDisplayedBeforeAnotherOrders(List<String> arbitrationDatesList) {
        boolean foundFirstEmptyOrder = false;

        for (String order : arbitrationDatesList) {
            if (foundFirstEmptyOrder && !order.isEmpty()) {
                return false;
            }
            if (!foundFirstEmptyOrder && order.isEmpty()) {
                foundFirstEmptyOrder = true;
            }
        }
        return true;
    }

    public static void verifyOrdersAreDisplayedByPriority(List<String> ordersPriorityValues) {
        final List<String> sortedList = VNextBOROPageInteractions.sortByPriority(ordersPriorityValues);
        Assert.assertEquals(ordersPriorityValues, sortedList, "The orders are not sorted by priority");
    }

    public static void verifyOrdersAreDisplayedByPriorityOnTheLastPage() {
        if (VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable()) {
            VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
            final List<String> ordersPriorityValues = VNextBOROPageInteractions.getOrdersPriorityValues();
            final int size = ordersPriorityValues.size();
            final List<String> ordersPriorityValuesOnTheLastPage
                    = ordersPriorityValues.subList(size - (size % 100), size);
            verifyOrdersAreDisplayedByPriority(ordersPriorityValuesOnTheLastPage);
        }
    }

    private static void verifyOrdersAreSortedByDateInDescendingOrder(List<String> priorityDates) {
        if (priorityDates != null) {
            final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat());
            for (int i = 0; i < priorityDates.size(); i++) {
                if (i == 0) {
                    continue;
                }
                final String current = priorityDates.get(i);
                final String previous = priorityDates.get(i - 1);
                verifyOrdersByDatesInDescendingOrder(dateFormat, current, previous);
            }
        }
    }

    private static void verifyOrdersAreSortedByDateInAscendingOrder(List<String> priorityDates) {
        if (priorityDates != null) {
            final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat());
            for (int i = 0; i < priorityDates.size(); i++) {
                if (i == 0) {
                    continue;
                }
                final String current = priorityDates.get(i);
                final String previous = priorityDates.get(i - 1);
                verifyOrdersByDatesInAscendingOrder(dateFormat, current, previous);
            }
        }
    }

    private static void verifyOrdersByDatesInDescendingOrder(DateTimeFormatter dateFormat, String current, String previous) {
        if (!LocalDate.parse(current, dateFormat).isEqual(LocalDate.parse(previous, dateFormat))) {
            Assert.assertTrue(LocalDate.parse(current, dateFormat)
                            .isBefore(LocalDate.parse(previous, dateFormat)),
                    "The orders are not sorted by dates in descending order");
        }
    }

    private static void verifyOrdersByDatesInAscendingOrder(DateTimeFormatter dateFormat, String current, String previous) {
        if (!LocalDate.parse(current, dateFormat).isEqual(LocalDate.parse(previous, dateFormat))) {
            Assert.assertTrue(LocalDate.parse(current, dateFormat)
                            .isAfter(LocalDate.parse(previous, dateFormat)),
                    "The orders are not sorted by dates in ascending order");
        }
    }

    public static void verifyHighPriorityOrdersAreSortedByDateInDescendingOrder() {
        final List<String> highPriorityDates = VNextBOROPageInteractions.getHighPriorityDates();
        verifyOrdersAreSortedByDateInDescendingOrder(highPriorityDates);
    }

    public static void verifyNormalPriorityOrdersAreSortedByDateInDescendingOrder() {
        final List<String> normalPriorityDates = VNextBOROPageInteractions.getNormalPriorityDates();
        verifyOrdersAreSortedByDateInDescendingOrder(normalPriorityDates);
    }

    public static void verifyLowPriorityOrdersAreSortedByDateInDescendingOrder() {
        final List<String> lowPriorityDates = VNextBOROPageInteractions.getLowPriorityDates();
        verifyOrdersAreSortedByDateInDescendingOrder(lowPriorityDates);
    }

    public static void verifyHighPriorityOrdersAreSortedByDateInAscendingOrder() {
        final List<String> highPriorityDates = VNextBOROPageInteractions.getHighPriorityDates();
        verifyOrdersAreSortedByDateInAscendingOrder(highPriorityDates);
    }

    public static void verifyNormalPriorityOrdersAreSortedByDateInAscendingOrder() {
        final List<String> normalPriorityDates = VNextBOROPageInteractions.getNormalPriorityDates();
        verifyOrdersAreSortedByDateInAscendingOrder(normalPriorityDates);
    }

    public static void verifyLowPriorityOrdersAreSortedByDateInAscendingOrder() {
        final List<String> lowPriorityDates = VNextBOROPageInteractions.getLowPriorityDates();
        verifyOrdersAreSortedByDateInAscendingOrder(lowPriorityDates);
    }

    public static void verifyMaximumNumberOfOrdersOnPage(int expectedMaxNumber) {
        final int ordersOnPage = VNextBOROPageInteractions.getOrdersNumberOnPage();
        try {
            WaitUtilsWebDriver.getWait().until((ExpectedCondition<Boolean>) driver -> ordersOnPage <= expectedMaxNumber);
        } catch (Exception ignored) {}

        if (ordersOnPage < expectedMaxNumber) {
            Assert.assertTrue(true, "The number of orders on page " + ordersOnPage
                    + " is less than the expected max number of items per page - " + expectedMaxNumber);
        } else {
            Assert.assertEquals(ordersOnPage, expectedMaxNumber,
                    "The number of orders on page is not equal to the max number of orders");
        }
    }

    public static void verifyInvoiceWindowIsOpened() {
        String mainWindow = Utils.getParentTab();
        WaitUtilsWebDriver.waitForNewTab();
        Assert.assertEquals(DriverBuilder.getInstance().getDriver().getWindowHandles().size(), 2,
                "The invoice hasn't been opened");
        Utils.closeNewWindow(mainWindow);
    }

    public static void verifyPhasesForWideScreen(List<Integer> phasesValues, VNextBOMonitorData data) {
        for (int i = 0; i < phasesValues.size(); i++) {
            VNextBOROPageInteractions.clickPhaseForWideScreen(data.getPhases().get(i));
            if (phasesValues.get(i) == 0) {
                Assert.assertFalse(VNextBOROPageValidations.isTableDisplayed(), "The table shouldn't be displayed");
                Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(true),
                        "The text notification is not displayed");
            } else {
                Assert.assertTrue(phasesValues.get(i) >= VNextBOROPageInteractions.getNumOfOrdersOnPage(),
                        "The phases repair orders number in table " +
                                "is less than value displayed in menu container");
            }
        }
    }

    public static void verifyDepartmentsForWideScreen(List<Integer> departmentValues, VNextBOMonitorData data) {
        for (int i = 0; i < departmentValues.size(); i++) {
            VNextBOROPageInteractions.clickDepartmentForWideScreen(data.getDepartments().get(i));
            if (departmentValues.get(i) == 0) {
                Assert.assertFalse(VNextBOROPageValidations.isTableDisplayed(), "The table shouldn't be displayed");
                Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(true),
                        "The text notification is not displayed");
            } else {
                Assert.assertTrue(departmentValues.get(0) >= VNextBOROPageInteractions.getNumOfOrdersOnPage(),
                        "The departments repair orders number in table " +
                                "is less than value displayed in menu container");
            }
        }
    }
}