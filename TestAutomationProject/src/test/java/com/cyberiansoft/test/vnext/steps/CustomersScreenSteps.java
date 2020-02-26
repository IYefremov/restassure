package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;

public class CustomersScreenSteps {

    public static void selectCustomer(AppCustomer customer) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.selectCustomer(customer);
    }

    public static void clickAddCustomerButton() {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.clickAddCustomerButton();
    }

    public static void createNewRetailCustomer(RetailCustomer retailCustomer) {
        clickAddCustomerButton();
        VNextNewCustomerScreen nextNewCustomerScreen = new VNextNewCustomerScreen();
        nextNewCustomerScreen.createNewCustomer(retailCustomer);
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
        customersScreen.setCustomerAsDefault(appCustomer);
        WaitUtilsWebDriver.waitForTextToBePresentInElement(customersScreen.getPresetCustomerPanel(), appCustomer.getFullName());
    }

    public static void openCustomerForEdit(AppCustomer appCustomer) {
        selectCustomer(appCustomer);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
    }
}
