package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.dataclasses.r360.RepairOrdersSearchData;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;

public class RepairOrdersCommonFiltersPageSteps {

    public static void selectSavedSearch(String searchName) {

        new CommonFilterScreen().getSavedSearch().selectOption(searchName);
    }

    public static void selectTimeFrame(String timeFrame) {

        new CommonFilterScreen().getTimeframe().selectOption(timeFrame);
    }

    public static void selectDepartment(String department) {

        new CommonFilterScreen().getDepartment().selectOption(department);
    }

    public static void selectPhase(String phase) {

        new CommonFilterScreen().getPhase().selectOption(phase);
    }

    public static void selectRepairStatus(String status) {

        new CommonFilterScreen().getRepairStatus().selectOption(status);
    }

    public static void selectFlag(String flag) {

        new CommonFilterScreen().getFlag().selectOption(flag);
    }

    public static void selectPriority(String priority) {

        new CommonFilterScreen().getPriority().selectOption(priority);
    }

    public static void tapSearchButton() {

        new CommonFilterScreen().getSearchButton().click();
    }

    public static void clearFilters() {

        new CommonFilterScreen().getClearFilter().click();
    }

    public static void setAllSearchFields(RepairOrdersSearchData searchData) {

        selectSavedSearch(searchData.getSavedSearch());
        selectTimeFrame(searchData.getTimeFrame());
        selectDepartment(searchData.getDepartment());
        selectPhase(searchData.getPhase());
        selectRepairStatus(searchData.getRepairStatus());
        selectFlag(searchData.getFlag());
        selectPriority(searchData.getPriority());
    }
}
