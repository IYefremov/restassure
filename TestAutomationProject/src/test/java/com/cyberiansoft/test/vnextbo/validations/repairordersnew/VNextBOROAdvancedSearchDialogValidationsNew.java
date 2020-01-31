package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROAdvancedSearchDialogNew;
import org.testng.Assert;

public class VNextBOROAdvancedSearchDialogValidationsNew {

    public static void verifyCustomerFieldContainsCorrectValue(String expectedCustomer) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getCustomerInputField()), expectedCustomer,
                "Customer field has contained incorrect value");
    }

    public static void verifyDialogIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOROAdvancedSearchDialogNew().getAdvancedSearchDialog()),
                " Advanced search dialog hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(new VNextBOROAdvancedSearchDialogNew().getAdvancedSearchDialog()),
                " Advanced search dialog hasn't been closed");
    }

    public static void verifyHasThisTextFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getHasThisTextInputField()), expectedValue,
                "Has this text field has contained incorrect value");
    }

    public static void verifyEmployeeFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getEmployeeInputField()), expectedValue,
                "Employee field has contained incorrect value");
    }

    public static void verifyPhaseFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getPhaseDropDown()), expectedValue,
                "Phase field has contained incorrect value");
    }

    public static void verifyPhaseStatusFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getPhaseStatusDropDown()), expectedValue,
                "Phase status field has contained incorrect value");
    }

    public static void verifyTaskFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getTaskDropDown()), expectedValue,
                "Task field has contained incorrect value");
    }

    public static void verifyTaskStatusFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getTaskStatusDropDown()), expectedValue,
                "Task status field has contained incorrect value");
    }

    public static void verifyDepartmentFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getDepartmentDropDown()), expectedValue,
                "Department field has contained incorrect value");
    }

    public static void verifyWoTypeFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getWoTypeDropDown()), expectedValue,
                "Wo Type field has contained incorrect value");
    }

    public static void verifyWoNumberFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getWoNumberInputField()), expectedValue,
                "Wo Number field has contained incorrect value");
    }

    public static void verifyRoNumberFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getRoNumberInputField()), expectedValue,
                "Ro Number field has contained incorrect value");
    }

    public static void verifyStockNumberFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getStockNumberInputField()), expectedValue,
                "Stock Number field has contained incorrect value");
    }

    public static void verifyVinNumberFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getVinInputField()), expectedValue,
                "VIN field has contained incorrect value");
    }

    public static void verifyTimeFrameFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getTimeFrameDropDown()), expectedValue,
                "Timeframe field has contained incorrect value");
    }

    public static void verifyRepairStatusFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getRepairStatusDropDown()), expectedValue,
                "Repair Status field has contained incorrect value");
    }

    public static void verifyDaysInProcessFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getDaysInProcessDropDown()), expectedValue,
                "Days In Process field has contained incorrect value");
    }

    public static void verifyDaysInProcessFromFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getDaysInProcessFromValue()), expectedValue,
                "Days In Process From field has contained incorrect value");
    }

    public static void verifyDaysInPhaseFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getDaysInPhaseDropDown()), expectedValue,
                "Days In Phase field has contained incorrect value");
    }

    public static void verifyDaysInPhaseFromFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getDaysInPhaseFromValue()), expectedValue,
                "Days In Phase From field has contained incorrect value");
    }

    public static void verifyFlagFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getFlagDropDown()), expectedValue,
                "Flag field has contained incorrect value");
    }

    public static void verifySortByFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOROAdvancedSearchDialogNew().getSortByDropDown()), expectedValue,
                "Sort by field has contained incorrect value");
    }

    public static void verifySearchNameFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getSearchNameInputField()), expectedValue,
                "Search Name field has contained incorrect value");
    }

    public static void verifyAllFieldsContainCorrectValues(VNextBOMonitorData data, boolean clearedFields) {

        verifyCustomerFieldContainsCorrectValue(data.getCustomer());
        verifyHasThisTextFieldContainsCorrectValue(data.getHasThisText());
        verifyEmployeeFieldContainsCorrectValue(data.getEmployee());
        verifyPhaseFieldContainsCorrectValue(data.getPhase());
        verifyPhaseStatusFieldContainsCorrectValue(data.getPhaseStatus());
        verifyTaskFieldContainsCorrectValue(data.getTask());
        verifyTaskStatusFieldContainsCorrectValue(data.getTaskStatus());
        verifyDepartmentFieldContainsCorrectValue(data.getDepartment());
        verifyWoTypeFieldContainsCorrectValue(data.getWoType());
        verifyWoNumberFieldContainsCorrectValue(data.getWoNum());
        verifyRoNumberFieldContainsCorrectValue(data.getRoNum());
        verifyStockNumberFieldContainsCorrectValue(data.getStockNum());
        verifyVinNumberFieldContainsCorrectValue(data.getVinNum());
        verifyTimeFrameFieldContainsCorrectValue(data.getTimeFrame());
        verifyRepairStatusFieldContainsCorrectValue(data.getRepairStatus());
        verifyDaysInProcessFieldContainsCorrectValue(data.getDaysInProcess());
        if (!clearedFields) {
            verifyDaysInProcessFromFieldContainsCorrectValue(data.getDaysNumStart());
            verifyDaysInPhaseFromFieldContainsCorrectValue(data.getDaysNumStart());
        }
        verifyDaysInPhaseFieldContainsCorrectValue(data.getDaysInPhase());
        verifyFlagFieldContainsCorrectValue(data.getFlag());
        verifySortByFieldContainsCorrectValue(data.getSortBy());
        verifySearchNameFieldContainsCorrectValue(data.getSearchName());
    }
}
