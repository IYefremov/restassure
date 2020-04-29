package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.r360.RepairOrdersSearchData;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ListPicker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepairOrdersCommonFiltersPageValidations {

    public static void verifySavedSearchFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getSavedSearch().getRootElement()),
                "'Saved search' field hasn't been displayed");
    }

    public static void verifySavedSearchFieldContainsCorrectValue(String savedSearch) {

        Assert.assertEquals(new Select(new CommonFilterScreen().getSavedSearch().getRootElement()).getFirstSelectedOption().getText().trim(),
                savedSearch, "'Saved search' field has contained incorrect value");
    }

    public static void verifyTimeFrameFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getTimeframe().getRootElement()),
                "'TimeFrame' field hasn't been displayed");
    }

    public static void verifyTimeFrameFieldContainsCorrectValue(String timeFrame) {

        Assert.assertEquals(new Select(new CommonFilterScreen().getTimeframe().getRootElement()).getFirstSelectedOption().getText().trim(),
                timeFrame, "'TimeFrame' field has contained incorrect value");
    }

    public static void verifyFromFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getDateFrom()),
                "'From' field hasn't been displayed");
    }

    public static void verifyToFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getDateTo()),
                "'To' field hasn't been displayed");
    }

    public static void verifyDepartmentFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getDepartment().getRootElement()),
                "'Department' field hasn't been displayed");
    }

    public static void verifyDepartmentFieldContainsCorrectValue(String department) {

        Assert.assertEquals(new Select(new CommonFilterScreen().getDepartment().getRootElement()).getFirstSelectedOption().getText().trim(),
                department, "'Department' field has contained incorrect value");
    }

    public static void verifyPhaseFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getPhase().getRootElement()),
                "'Phase' field hasn't been displayed");
    }

    public static void verifyPhaseFieldContainsCorrectValue(String phase) {

        Assert.assertEquals(new Select(new CommonFilterScreen().getPhase().getRootElement()).getFirstSelectedOption().getText().trim(),
                phase, "'Phase' field has contained incorrect value");
    }

    public static void verifyRepairStatusFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getRepairStatus().getRootElement()),
                "'Repair status' field hasn't been displayed");
    }

    public static void verifyRepairStatusFieldContainsCorrectValue(String status) {

        Assert.assertEquals(new Select(new CommonFilterScreen().getRepairStatus().getRootElement()).getFirstSelectedOption().getText().trim(),
                status, "'Repair status' field has contained incorrect value");
    }

    public static void verifyFlagFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getFlag().getRootElement()),
                "'Flag' field hasn't been displayed");
    }

    public static void verifyFlagFieldContainsCorrectValue(String flag) {

        Assert.assertEquals(new Select(new CommonFilterScreen().getFlag().getRootElement()).getFirstSelectedOption().getText().trim(),
                flag, "'Flag' field has contained incorrect value");
    }

    public static void verifyPriorityFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new CommonFilterScreen().getPriority().getRootElement()),
                "'Priority' field hasn't been displayed");
    }

    public static void verifyPriorityFieldContainsCorrectValue(String priority) {

        Assert.assertEquals(new Select(new CommonFilterScreen().getPriority().getRootElement()).getFirstSelectedOption().getText().trim(),
                priority, "'Priority' field has contained incorrect value");
    }

    public static void verifySearchButtonIsDisplayed() {

        Assert.assertTrue(new CommonFilterScreen().getSearchButton().isDisplayed(),
                "'Search' button hasn't been displayed");
    }

    public static void verifyClearFiltersButtonIsDisplayed() {

        Assert.assertTrue(new CommonFilterScreen().getClearFilter().isDisplayed(),
                "'Clear filters' button hasn't been displayed");
    }

    public static void verifyCommonFiltersScreenHasAllElements() {

        verifySavedSearchFieldIsDisplayed();
        verifyTimeFrameFieldIsDisplayed();
        verifyFromFieldIsDisplayed();
        verifyToFieldIsDisplayed();
        verifyDepartmentFieldIsDisplayed();
        verifyPhaseFieldIsDisplayed();
        verifyRepairStatusFieldIsDisplayed();
        verifyFlagFieldIsDisplayed();
        verifyPriorityFieldIsDisplayed();
        verifySearchButtonIsDisplayed();
        verifyClearFiltersButtonIsDisplayed();
    }

    public static void verifyAllFieldsContainCorrectValues(RepairOrdersSearchData searchData) {

        verifySavedSearchFieldContainsCorrectValue(searchData.getSavedSearch());
        verifyTimeFrameFieldContainsCorrectValue(searchData.getTimeFrame());
        verifyDepartmentFieldContainsCorrectValue(searchData.getDepartment());
        verifyPhaseFieldContainsCorrectValue(searchData.getPhase());
        verifyRepairStatusFieldContainsCorrectValue(searchData.getRepairStatus());
        verifyFlagFieldContainsCorrectValue(searchData.getFlag());
        verifyPriorityFieldContainsCorrectValue(searchData.getPriority());
    }

    public static void verifyTimeFrameDropDownContainsCorrectOptions() {

        List<String> expectedTimeFramesList = Arrays.asList("Custom", "Today", "Week To Date", "Last Week", "Month to Date",
                "Last Month", "Last 30 days", "Last 90 days", "Year To Date", "Last Year");
        ListPicker timeFrameField = new CommonFilterScreen().getTimeframe();
        WaitUtils.click(timeFrameField.getRootElement());
        Select dropdown = new Select(timeFrameField.getRootElement());
        Assert.assertEquals(dropdown.getOptions().stream().map(WebElement::getText).collect(Collectors.toList()).stream().map(String::trim).collect(Collectors.toList()),
                expectedTimeFramesList, "'TimeFrame' dropdown has contained incorrect values");
        WaitUtils.click(timeFrameField.getRootElement());
    }

    public static void verifyPriorityDropDownContainsCorrectOptions() {

        List<String> expectedTimeFramesList = Arrays.asList("Low", "Normal", "High");
        ListPicker timeFrameField = new CommonFilterScreen().getPriority();
        WaitUtils.click(timeFrameField.getRootElement());
        Select dropdown = new Select(timeFrameField.getRootElement());
        Assert.assertEquals(dropdown.getOptions().stream().map(WebElement::getText).collect(Collectors.toList()).stream().map(String::trim).collect(Collectors.toList()),
                expectedTimeFramesList, "'Priority' dropdown has contained incorrect values");
        WaitUtils.click(timeFrameField.getRootElement());
    }
}
