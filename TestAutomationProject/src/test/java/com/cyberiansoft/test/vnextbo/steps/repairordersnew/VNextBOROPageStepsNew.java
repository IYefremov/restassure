package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;

public class VNextBOROPageStepsNew {

    public static void searchOrdersByCustomer(String customer) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setCustomerField(customer);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByEmployee(String employee) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setEmployeeField(employee);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByPhase(String phase) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setPhaseField(phase);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByDepartment(String department) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setDepartmentField(department);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByWoType(String woType) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setWoTypeField(woType);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByWoNumber(String woNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setWoNumberField(woNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByRoNumber(String roNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setRoNumberField(roNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByStockNumber(String stockNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setStockNumberField(stockNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }

    public static void searchOrdersByVinNumber(String vinNumber) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setVinNumberField(vinNumber);
        VNextBOROAdvancedSearchDialogStepsNew.setCustomTimeFrame("1/1/2018", "1/1/2028");
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
    }
}
