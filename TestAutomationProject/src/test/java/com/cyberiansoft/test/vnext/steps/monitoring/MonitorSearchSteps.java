package com.cyberiansoft.test.vnext.steps.monitoring;

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
    }

    public static void selectStatus(RepairOrderStatus status) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getStatus().getRootElement());
        commonFilterScreen.getStatus().selectOption(status.getStatusString());
    }

    public static void search() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.search();
    }

    public static void searchByTextAndStatus(String text,RepairOrderStatus status){
        MonitorSteps.openSearchFilters();
        searchByText(text);
        selectStatus(status);
        MonitorSearchSteps.search();
    }
}
