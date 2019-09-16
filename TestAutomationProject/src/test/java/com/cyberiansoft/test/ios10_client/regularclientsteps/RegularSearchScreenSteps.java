package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSearchScreen;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;

public class RegularSearchScreenSteps {

    public static void searchStatus(String statusValue) {
        RegularSearchScreen searchScreen = new RegularSearchScreen();
        searchScreen.searchStatus(statusValue);
    }

    public static void searchCustomer(AppCustomer appCustomer) {
        RegularSearchScreen searchScreen = new RegularSearchScreen();
        searchScreen.clickSearchCustomer();
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
    }

    public static void searchInvoiceType(IInvoicesTypes iInvoicesType) {
        RegularSearchScreen searchScreen = new RegularSearchScreen();
        searchScreen.clickSearchType();
        RegularInvoiceTypesSteps.selectInvoiceType(iInvoicesType);
    }

    public static void saveSearchDialog() {
        RegularSearchScreen searchScreen = new RegularSearchScreen();
        searchScreen.saveSearchDialog();
    }

    public static void setSearchText(String searchText) {
        RegularSearchScreen searchScreen = new RegularSearchScreen();
        searchScreen.setSearchText(searchText);
    }
}
