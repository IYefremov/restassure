package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;

public class RegularMainScreenSteps {

    public static void userLogin(String employeeName, String password)  {
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.selectEmployee(employeeName);
        mainScreen.enterEmployeePassword(password);
        RegularHomeScreenSteps.waitForHomeScreen();
    }
}
