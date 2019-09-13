package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.utils.TestUser;

public class RegularStatusScreenSteps {

    public static void updateMainDataBase(TestUser testuser) {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.updateDatabase();
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.userLogin(testuser.getTestUserName(), testuser.getTestUserPassword());
    }
}
