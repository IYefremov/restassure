package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBORODetailsWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBORODetailsValidationsNew {

    public static void verifyServiceOrTaskDescriptionsContainText(String text) {

        boolean present = VNextBORODetailsStepsNew.getServiceAndTaskDescriptionsList()
                .stream()
                .anyMatch(string -> string.contains(text));

        Assert.assertTrue(present, "The order contains neither the service nor the task '" + text + "'.");
    }

    public static void verifyPhaseIsDisplayed(String phase) {

        List<String> displayedPhases = new VNextBORODetailsWebPageNew().getPhasesList().stream().
                map(WebElement::getText).collect(Collectors.toList());
        Assert.assertTrue(displayedPhases.contains(phase), "Phase hasn't been displayed");
    }

    public static void verifyServiceTableContainsCorrectColumns() {

        List<String> expectedColumnsList = Arrays.asList("Service", "Qty", "Price", "Vendor Price", "Vendor / Technician",
                "Status", "Started / Completed", "Actions");
        List<String> actualColumnsList = new VNextBORODetailsWebPageNew().getServicesTableColumnsTitles().stream().
                map(WebElement::getText).collect(Collectors.toList());
        Assert.assertEquals(expectedColumnsList, actualColumnsList, "Not all columns have been displayed");
    }

    public static void verifyMoreInfoSectionContainsCorrectFields() {

        VNextBORODetailsWebPageNew detailsPage = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsPage.getMoreInfoSection());
        List<String> expectedFieldsList = Arrays.asList("Mileage:", "Plate:", "Type:", "Completed (Repair) Time:",
                "Delivered:", "Created:");
        List<String> actualFieldsList = detailsPage.getMoreInfoFields().stream().map(WebElement::getText).collect(Collectors.toList());
        Assert.assertEquals(expectedFieldsList, actualFieldsList, "Not all fields have been displayed");
        Utils.clickElement(detailsPage.getMoreInfoSection());
    }

    public static void verifyOrderDetailsSectionIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().getOrderDetailsSection()),
                "Order details section hasn't been displayed");
    }

    public static void verifyOrderStatusIsCorrect(String expectedStatus) {

        ConditionWaiter.create(__ -> Utils.getText(new VNextBORODetailsWebPageNew().getOrderStatusDropDown()).equals(expectedStatus)).execute();
        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().getOrderStatusDropDown()), expectedStatus,
                "Order status hasn't been correct");
    }

    public static void verifyOrderCloseReasonIsCorrect(String expectedReason) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().getOrderCloseReason()), expectedReason,
                "Order close reason hasn't been correct");
    }

    public static void verifyProblemIndicatorIsDisplayedForPhase(String phase) {

        ConditionWaiter.create(__ -> new VNextBORODetailsWebPageNew().problemIndicatorByPhase(phase).isDisplayed()).execute();
        Assert.assertTrue(new VNextBORODetailsWebPageNew().problemIndicatorByPhase(phase).isDisplayed(),
                "Problem indicator hasn't been displayed for the phase '" + phase + "'.");
    }

    public static void verifyProblemIndicatorIsDisplayedForService(String service) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().problemIndicatorByService(service)),
                "Problem indicator hasn't been displayed for the service '" + service + "'.");
    }

    public static void verifyPhaseStatusInDropdownFieldIsCorrect(String phase, String status) {

        ConditionWaiter.create(__ -> Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)).equals(status)).execute();
        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)), status,
                "Status hasn't been correct for the '" + phase + "'.");
    }

    public static void verifyPhaseTextStatusIsCorrect(String phase, String status) {

        ConditionWaiter.create(__ -> Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusTextByPhase(phase)).equals(status)).execute();
        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusTextByPhase(phase)), status,
                "Status hasn't been correct for the '" + phase + "'.");
    }
    public static void verifyServiceStatusIsCorrect(String service, String status) {

        ConditionWaiter.create(__ -> Utils.getText(new VNextBORODetailsWebPageNew().serviceStatusDropDownByService(service)).equals(status)).execute();
        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceStatusDropDownByService(service)), status,
                "Status hasn't been correct for the '" + service + "'.");
    }

    public static void verifyActionsButtonIsNotDisplayedForPhase(String phase) {

        try {
            Assert.assertFalse(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().actionsMenuButtonForPhase(phase)),
                    "Actions button has been displayed");
        } catch (NoSuchElementException ex) {
        }
    }

    public static void verifyReportProblemActionButtonIsNotDisplayedForCompletedService(String service) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        try {
            Assert.assertFalse(Utils.isElementDisplayed(detailsWebPageNew.getReportProblemForServiceActionButton()),
                    "Actions button has been displayed");
        } catch (NoSuchElementException ex) {
        }
    }

    public static void verifyPhaseTotalPriceHasBeenChanged(String phase, String initialPrice) {

        Assert.assertNotEquals(initialPrice, Utils.getText(new VNextBORODetailsWebPageNew().phaseTotalPrice(phase)), "Total price hasn't changed for the phase: " + phase);
    }

    public static void verifyOrderPriorityIsCorrect(String priority) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().getPriorityDropDown()), priority,
                "Priority hasn't been correct");
    }

    public static void verifyServiceIsDisplayed(String serviceDescription, boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) {
            ConditionWaiter.create(__ -> new VNextBORODetailsWebPageNew().serviceDescription(serviceDescription).isDisplayed()).execute();
            Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().serviceDescription(serviceDescription)),
                    "Service with description " + serviceDescription + " hasn't been added");
        }
        else try {
            Assert.assertFalse(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().serviceDescription(serviceDescription)),
                    "Service with description " + serviceDescription + " has been added");
        } catch (NoSuchElementException ex) {}
    }

    public static void verifyServicePriceIsCorrect(String service, String expectedPrice) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBORODetailsWebPageNew().servicePriceInputField(service)), expectedPrice,
                "Service price hasn't been correct");
    }

    public static void verifyServiceVendorPriceIsCorrect(String service, String expectedPrice) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBORODetailsWebPageNew().serviceVendorPriceInputField(service)), expectedPrice,
                "Service vendor price hasn't been correct");
    }

    public static void verifyServiceQuantityIsCorrect(String service, String expectedQuantity) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBORODetailsWebPageNew().serviceQtyInputField(service)), expectedQuantity,
                "Service quantity hasn't been correct");
    }

    public static void verifyPartServicesAmountIsCorrect(int expectedNumber) {

        Assert.assertEquals(new VNextBORODetailsWebPageNew().getPartServicesNamesList().size(), expectedNumber,
                "Part services amount hasn't been correct");
    }

    public static void verifyFlagIsCorrect(String flagColor) {

        Assert.assertTrue(new VNextBORODetailsWebPageNew().getFlagIcon().getAttribute("style").contains(flagColor),
                "Flag hasn't been correct");
    }

    public static void verifyServiceStartedDateIsCorrect(String service, String expectedStartDate) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceStartedDate(service)), expectedStartDate,
                "Service started date hasn't been correct");
    }

    public static void verifyServiceCompletedDateIsCorrect(String service, String expectedCompletedDate) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceCompletedDate(service)), expectedCompletedDate,
                "Service completed date hasn't been correct");
    }

    public static void verifyServiceVendorIsCorrect(String service, String expectedVendor) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceVendorDropDown(service)), expectedVendor,
                "Vendor hasn't been correct");
    }

    public static void verifyServiceTechnicianIsCorrect(String service, String expectedTechnician) {

        ConditionWaiter.create(__ -> Utils.getText(new VNextBORODetailsWebPageNew().serviceTechnicianDropDown(service)).equals(expectedTechnician)).execute();
        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceTechnicianDropDown(service)), expectedTechnician,
                "Technician hasn't been correct");
    }

    public static void verifyServiceHelpInfoIsCorrect(String service, String expectedHelpInfo) {

        VNextBORODetailsWebPageNew detailsPage = new VNextBORODetailsWebPageNew();
        Utils.hoverElement(detailsPage.serviceHelpIcon(service));
        Assert.assertTrue(Utils.isElementDisplayed(detailsPage.serviceHelpIconHelpInfo(service)),
                "Service help info pop-up hasn't been displayed");
        Assert.assertEquals(Utils.getText(detailsPage.serviceHelpIconHelpInfo(service)), expectedHelpInfo,
                "Service completed date hasn't been correct");
    }

    public static void verifyPhaseIsCheckedInCheckedOut(String phase, boolean shouldBeCheckedIn) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        if (shouldBeCheckedIn) Assert.assertTrue(Utils.isElementDisplayed(detailsWebPageNew.getCheckOutActionButton()),
                "Check Out option hasn't been displayed");
        else Assert.assertTrue(Utils.isElementDisplayed(detailsWebPageNew.getCheckInActionButton()),
                "Check In option hasn't been displayed");
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
    }

    public static void verifyPartsTableIsDisplayed() {

        List<String> expectedColumnsList = Arrays.asList("", "Work description", "Part #", "Hours", "Qty", "Part Price",
                "Vendor Price", "Ordered From", "Status", "Phase", "Ordered Date", "Received", "Actions");
        List<String> actualColumnsList = new VNextBORODetailsWebPageNew().getPartsTableColumnsTitles().stream().
                map(WebElement::getText).collect(Collectors.toList());
        Assert.assertEquals(expectedColumnsList, actualColumnsList, "Not all columns have been displayed");
    }

    public static void verifyPartsServicesAreDisplayed() {

        Assert.assertTrue(new VNextBORODetailsWebPageNew().getPartServicesNamesList().size() > 0, "Part services haven't been displayed");
    }

    public static void verifyServiceIconIsCorrect(String service, String expectedServiceIcon) {

        ConditionWaiter.create(__ -> new VNextBORODetailsWebPageNew().serviceIcon(service).getAttribute("class").equals(expectedServiceIcon)).execute();
        Assert.assertEquals(new VNextBORODetailsWebPageNew().serviceIcon(service).getAttribute("class"), expectedServiceIcon,
                "Service icon hasn't been correct");
    }

    public static void verifyInspectionIsDisplayedInMoreInfo(String expectedInspection) {

        Assert.assertTrue(new VNextBORODetailsWebPageNew().getInspectionsList().stream().
                map(WebElement::getText).collect(Collectors.toList()).contains(expectedInspection), "Inspection hasn't been displayed");
    }

    public static void verifyInspectionWindowCanBeOpened() {

        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        Utils.clickElement(new VNextBORODetailsWebPageNew().getInspectionsList().get(0));
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitForNewTab();
        String inspectionWindowHandle = Utils.getNewTab(mainWindow);
        Assert.assertNotEquals(mainWindow, inspectionWindowHandle, "The inspection window hasn't been opened");
        Utils.closeNewTab(mainWindow);
    }
}
