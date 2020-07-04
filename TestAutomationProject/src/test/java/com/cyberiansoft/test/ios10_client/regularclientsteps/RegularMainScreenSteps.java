package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularMainScreenSteps {

    public static void userLogin(String employeeName, String password)  {
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.selectEmployee(employeeName);
        mainScreen.enterEmployeePassword(password);
        RegularHomeScreenSteps.waitForHomeScreen();
    }

    public static void updateMainDataBase()  {
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.updateDatabase();
    }

    public static void clickStatusIcon() {
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.clickStatusIcon();
    }

    public static void updateVINDataBase() {
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.clickUpdateVINDatabase();
        Helpers.acceptAlert();
    }
}
