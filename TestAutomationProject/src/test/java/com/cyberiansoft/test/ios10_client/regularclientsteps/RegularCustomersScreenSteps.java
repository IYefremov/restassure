package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularAddCustomerScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;

public class RegularCustomersScreenSteps {

    public static void selectCustomer(AppCustomer appCustomer) {
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer(appCustomer);
    }

    public static void clickOnCustomer(AppCustomer appCustomer) {
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.clickOnCustomer(appCustomer);
    }

    public static void switchToRetailMode() {
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.swtchToRetailMode();
    }

    public static void switchToWholesaleMode() {
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.swtchToWholesaleMode();
    }

    public static void addNewRetailCustomer(RetailCustomer retailCustomer) {
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.clickAddCustomersButton();
        RegularAddCustomerScreen addCustomerScreen = new RegularAddCustomerScreen();
        if (retailCustomer.getFirstName() != null)
            addCustomerScreen.setFirstName(retailCustomer.getFirstName());
        if (retailCustomer.getLastName() != null)
            addCustomerScreen.setLastName(retailCustomer.getLastName());
        if (retailCustomer.getMailAddress() != null)
            addCustomerScreen.setMail(retailCustomer.getMailAddress());
        if (retailCustomer.getCustomerPhone() != null)
            addCustomerScreen.setPhone(retailCustomer.getCustomerPhone());
        if (retailCustomer.getCustomerAddress1() != null)
            addCustomerScreen.setAddress1(retailCustomer.getCustomerAddress1());
        if (retailCustomer.getCustomerAddress2() != null)
            addCustomerScreen.setAddress2(retailCustomer.getCustomerAddress2());
        if (retailCustomer.getCustomerCity() != null)
            addCustomerScreen.setCity(retailCustomer.getCustomerCity());
        if (retailCustomer.getCustomerCountry() != null)
            addCustomerScreen.selectCountry(retailCustomer.getCustomerCountry());
        if (retailCustomer.getCustomerState() != null)
            addCustomerScreen.selectState(retailCustomer.getCustomerState());
        if (retailCustomer.getCustomerZip() != null)
            addCustomerScreen.setZip(retailCustomer.getCustomerZip());
        addCustomerScreen.clickSaveBtn();
    }
}
