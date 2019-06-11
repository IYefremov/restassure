package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class MonitorSearchSteps {
    public static void searchByText(String searchString) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getClearSearchInputButton());
        commonFilterScreen.getClearSearchInputButton().click();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getSearchInputField());
        commonFilterScreen.setSearchInputField(searchString);
        BaseUtils.waitABit(1000);
    }

    public static void selectStatus(RepairOrderStatus status) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getStatus().getRootElement());
        commonFilterScreen.getStatus().selectOption(status.getStatusString());
    }

    public static void search() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.elementShouldBeVisible(commonFilterScreen.getSearchButton(), true);
        commonFilterScreen.search();
    }

    public static void searchByTextAndStatus(String text, RepairOrderStatus status) {
        MonitorSteps.openSearchFilters();
        searchByText(text);
        selectStatus(status);
        MonitorSearchSteps.search();
    }

    public static void verifySearchResultsAreEmpty() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.elementShouldBeVisible(commonFilterScreen.getNothingFoundLable(), true);
    }

    public static void searchByFlag(RepairOrderFlag repairOrderFlag) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        MonitorSteps.openSearchFilters();
        commonFilterScreen.getFlag().selectOption(repairOrderFlag.name());
        MonitorSearchSteps.search();
    }

    public static void searchByPriority(OrderPriority high) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        MonitorSteps.openSearchFilters();
        commonFilterScreen.getPriority().selectOption(high.getValue());
        MonitorSearchSteps.search();
    }

    public static void searchByDepartment(String departmentName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        MonitorSteps.openSearchFilters();
        commonFilterScreen.getDepartment().selectOption(departmentName);
        MonitorSearchSteps.search();
    }

    public static void searchByPhase(String phaseName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        MonitorSteps.openSearchFilters();
        commonFilterScreen.getPhase().selectOption(phaseName);
        MonitorSearchSteps.search();
    }
}
