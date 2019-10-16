package com.cyberiansoft.test.vnextbo.verifications.Users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOUsersPageValidations extends VNextBOBaseWebPageValidations {

    public static void isAddNewUserBtnDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.addNewUserBtn),
                "\"Add New User\" button hasn't been displayed");
    }

    public static void isUsersTableDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.usersTable.getWrappedElement()),
                "\"Add New User\" button hasn't been displayed");
    }

    public static void isPageNavigationButtonsDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.nextPageBtn) &
                        Utils.isElementDisplayed(vNexBOUsersWebPage.previousPageBtn),
                "Navigation buttons haven't been displayed");
    }
}