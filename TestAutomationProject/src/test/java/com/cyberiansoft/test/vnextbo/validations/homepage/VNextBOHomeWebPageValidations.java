package com.cyberiansoft.test.vnextbo.validations.homepage;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOHomeWebPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyAccessClientPortalLinkIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOHomeWebPage().getAccessClientPortalLink()),
                "Access Client Portal link hasn't been displayed");
    }

    public static void verifyAccessReconProBOLinkIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOHomeWebPage().getAccessReconProBOLink()),
                "Access ReconPro BackOffice link hasn't been displayed");
    }

    public static void verifySupportForBOButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOHomeWebPage().getSupportForBOButton()),
                "Support for Back Office button hasn't been displayed");
    }

    public static void verifySupportForMobileAppButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOHomeWebPage().getSupportForMobileAppButton()),
                "Support for Mobile App button hasn't been displayed");
    }

    public static void verifyAllHomePageElements() {

        verifyLogoIsDisplayed();
        verifyUserInfoBlockIsDisplayed();
        verifyLogoutButtonIsDisplayed();
        verifyHelpButtonIsDisplayed();
        verifyCopyRightTextIsDisplayed();
        verifyTermsAndConditionsLinkIsDisplayed();
        verifyPrivacyPolicyLinkIsDisplayed();
        verifyIntercomButtonIsDisplayed();
        verifyAccessClientPortalLinkIsDisplayed();
        verifyAccessReconProBOLinkIsDisplayed();
        verifySupportForBOButtonIsDisplayed();
        verifySupportForMobileAppButtonIsDisplayed();
    }

    public static void verifyClientPortalPageIsOpened(String actualClientPortalPageUrl) {

        Assert.assertTrue(actualClientPortalPageUrl.contains("ibs.cyberianconcepts.com/Billing"), "Client Portal page hasn't been opened");
    }

    public static void verifyReconProBoPageIsOpened(String actualReconProBoPageUrl) {

        Assert.assertTrue(actualReconProBoPageUrl.contains(".cyberianconcepts.com/Home.aspx"), "Recon Pro Back Office page hasn't been opened");
    }

    public static void verifySupportForBoPageIsOpened(String actualSupportForBoPageUrl) {

        Assert.assertTrue(actualSupportForBoPageUrl.contains("intercom.help") &&
                actualSupportForBoPageUrl.contains("reconpro-reconmonitor-back-office"), "Support for Back Office page hasn't been opened");
    }

    public static void verifySupportForMobileAppPageIsOpened(String actualSupportForMobileAppPageUrl) {

        Assert.assertTrue(actualSupportForMobileAppPageUrl.contains("intercom.help") &&
                actualSupportForMobileAppPageUrl.contains("reconpro-mobile-app"), "Support for Mobile App page hasn't been opened");
    }
}
