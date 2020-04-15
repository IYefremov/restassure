package com.cyberiansoft.test.vnext.steps.customers;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.SearchSteps;

public class CustomersScreenSteps {

    public static void selectCustomer(AppCustomer customer) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.selectCustomer(customer);
    }

    public static void tapOnCustomer(String companyName) {

        Utils.clickElement(new VNextCustomersScreen()
                .getCustomersListArray()
                .stream().filter(customer -> customer.getCustomerFullName().contains(companyName)).findFirst().get().getRootElement());
    }

    public static void clickAddCustomerButton() {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.clickAddCustomerButton();
    }

    public static void createNewRetailCustomer(RetailCustomer retailCustomer) {
        switchToRetailMode();
        clickAddCustomerButton();
        VNextNewCustomerScreen newCustomerScreen = new VNextNewCustomerScreen();
        newCustomerScreen.createNewCustomer(retailCustomer);
    }

    public static void switchToRetailMode() {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
    }

    public static void switchToWholesaleMode() {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToWholesaleMode();
    }

    public static void resetPresetCustomer() {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.resetPresetCustomer();
    }

    public static void setCustomerAsDefault(AppCustomer appCustomer) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        selectCustomer(appCustomer);
        MenuSteps.selectMenuItem(MenuItems.SET_AS_DEFAULT);
        WaitUtilsWebDriver.waitForTextToBePresentInElement(customersScreen.getPresetCustomerPanel(), appCustomer.getFullName());
        SearchSteps.cancelSearch();
    }

    public static void openCustomerForEdit(AppCustomer appCustomer) {
        selectCustomer(appCustomer);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
    }

}
