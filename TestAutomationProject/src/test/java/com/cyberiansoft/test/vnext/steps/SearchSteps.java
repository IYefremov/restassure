package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class SearchSteps {

    public static void fillTextSearch(String searchString) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getClearSearchInputButton());
        WaitUtils.click(commonFilterScreen.getClearSearchInputButton());
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getSearchInputField());
        commonFilterScreen.setSearchInputField(searchString);
        BaseUtils.waitABit(1000);
    }

    public static void selectStatus(RepairOrderStatus status) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.getRepairStatus().selectOption(status.getStatusString());
    }

    public static void clickStatusFilter() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    commonFilterScreen.getRepairStatus().getRootElement().click();
                    return true;
                });
        WaitUtils.getGeneralFluentWait().until((webDriver) -> webDriver.findElements(By.xpath(commonFilterScreen.getRepairStatus().getElementsLocator())).size() > 0);
    }

    public static void clickDepartmentFilter() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    commonFilterScreen.getDepartment().getRootElement().click();
                    return true;
                });
        WaitUtils.getGeneralFluentWait().until((webDriver) -> webDriver.findElements(By.xpath(commonFilterScreen.getDepartment().getElementsLocator())).size() > 0);
    }

    public static void clickPhaseFilter() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    commonFilterScreen.getPhase().getRootElement().click();
                    return true;
                });
        WaitUtils.getGeneralFluentWait().until((webDriver) -> webDriver.findElements(By.xpath(commonFilterScreen.getPhase().getElementsLocator())).size() > 0);
    }

    public static void clickFlagFilter() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    commonFilterScreen.getFlag().getRootElement().click();
                    return true;
                });
        WaitUtils.getGeneralFluentWait().until((webDriver) -> webDriver.findElements(By.xpath(commonFilterScreen.getFlag().getElementsLocator())).size() > 0);
    }

    public static void clickPriorityFilter() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    commonFilterScreen.getPriority().getRootElement().click();
                    return true;
                });
        WaitUtils.getGeneralFluentWait().until((webDriver) -> webDriver.findElements(By.xpath(commonFilterScreen.getPriority().getElementsLocator())).size() > 0);
    }

    public static void search() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.elementShouldBeVisible(commonFilterScreen.getSearchButton().rootElement, true);
        commonFilterScreen.getSearchButton().click();
        BaseUtils.waitABit(1000);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }

    public static void searchByTextAndStatus(String text, RepairOrderStatus status) {
        BaseUtils.waitABit(20*1000);
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        openSearchMenu();
        fillTextSearch(text);
        clickCommonFiltersToggle();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getRepairStatus().getRootElement());
        commonFilterScreen.getRepairStatus().selectOption(status.getStatusString());
        search();
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(repairOrderScreen.getRootElement());
    }

    public static void searchByText(String text) {
        SearchSteps.openSearchMenu();
        SearchSteps.fillTextSearch(text);
        SearchSteps.cancelSearch();
    }

    public static void searchByFlag(RepairOrderFlag repairOrderFlag) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        openSearchFilters();
        commonFilterScreen.getFlag().selectOption(StringUtils.capitalize(StringUtils.lowerCase(repairOrderFlag.name())));
        search();
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void searchByPriority(OrderPriority orderPriority) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        openSearchFilters();
        commonFilterScreen.getPriority().selectOption(orderPriority.getValue());
        search();
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void selectPriority(OrderPriority orderPriority) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.getPriority().selectOption(orderPriority.getValue());
    }

    public static void searchByDepartment(String departmentName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();

        openSearchFilters();
        commonFilterScreen.getDepartment().selectOption(departmentName);
        search();
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void selectDepartment(String departmentName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.getDepartment().selectListElement(departmentName);
    }

    public static void selectFlag(RepairOrderFlag repairOrderFlag) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.getFlag().selectListElement(repairOrderFlag.name());
    }

    public static void searchByPhase(String phaseName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        openSearchFilters();
        commonFilterScreen.getPhase().selectOption(phaseName);
        search();
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void selectPhase(String phaseName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.getPhase().selectListElement(phaseName);
    }

    public static void clearAllFilters() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        repairOrderScreen.getActiveFilterslabel().clearAllFilters();
        BaseUtils.waitABit(1000);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void openSearchMenu() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.waitUntilElementIsClickable(repairOrderScreen.getSearchButton());
        repairOrderScreen.openSearchMenu();
    }

    public static void openSearchFilters() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        openSearchMenu();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getCommonFiltersToggle(), true);
        clickCommonFiltersToggle();
    }

    public static void clickCommonFiltersToggle() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        repairOrderScreen.openCommonFilters();
    }

    public static void textSearch(String serviceName) {
        GeneralWizardInteractions.openSearchFilter();
        GeneralWizardInteractions.setSearchText(serviceName);
        GeneralWizardInteractions.closeSearchFilter();
    }

    public static void textSearchTeam(String serviceName) {
        textSearch(serviceName);
        WaitUtils.waitLoadDialogDisappears();
    }

    public static void clearFilters() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.getClearFilter().click();
    }

    public static void cancelSearch() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        commonFilterScreen.getCancelSearchInputButton().click();
    }
}
