package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.r360.RepairOrdersSearchData;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

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
        BaseUtils.waitABit(1000);
        waitUntilRepairsOrdersPageIsOpened();
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

    private static void waitUntilRepairsOrdersPageIsOpened() {

        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    new RepairOrderScreen().getRepairOrderList().isDisplayed();
                    return true;
                });
    }
}
