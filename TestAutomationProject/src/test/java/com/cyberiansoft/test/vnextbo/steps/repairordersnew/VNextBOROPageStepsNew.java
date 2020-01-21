package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public static boolean checkIfNoRecordsFoundMessageIsDisplayed() {

        return Utils.isElementDisplayed(new VNextBOROWebPageNew().getNoRecordsFoundMessage());
    }
}
