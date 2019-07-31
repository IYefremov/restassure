package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class SearchSteps {

    public static void fillTextSearch(String searchString) {
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
        openSearchFilters();
        fillTextSearch(text);
        selectStatus(status);
        search();
    }

    public static void searchByText(String text) {
        openSearchFilters();
        fillTextSearch(text);
        search();
        BaseUtils.waitABit(3000);
    }

    public static void searchByFlag(RepairOrderFlag repairOrderFlag) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        openSearchFilters();
        commonFilterScreen.getFlag().selectOption(repairOrderFlag.name());
        search();
    }

    public static void searchByPriority(OrderPriority high) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        openSearchFilters();
        commonFilterScreen.getPriority().selectOption(high.getValue());
        search();
    }

    public static void searchByDepartment(String departmentName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        openSearchFilters();
        commonFilterScreen.getDepartment().selectOption(departmentName);
        search();
    }

    public static void searchByPhase(String phaseName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        openSearchFilters();
        commonFilterScreen.getPhase().selectOption(phaseName);
        search();
    }

    public static void clearAllFilters() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        repairOrderScreen.getActiveFilterslabel().clearAllFilters();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void openSearchFilters() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.waitUntilElementIsClickable(repairOrderScreen.getSearchButton());
        repairOrderScreen.openSearchMenu();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getCommonFiltersToggle(), true);
        repairOrderScreen.openCommonFilters();
    }

    public static void textSearch(String serviceName) {
        GeneralWizardInteractions.openSearchFilter();
        GeneralWizardInteractions.setSearchText(serviceName);
        ScreenNavigationSteps.pressHardwareBackButton();
    }
}
