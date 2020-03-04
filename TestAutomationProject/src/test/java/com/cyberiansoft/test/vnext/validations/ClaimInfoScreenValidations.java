package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import org.testng.Assert;

public class ClaimInfoScreenValidations {

    public static void validateInsuranceCompanyVisible(boolean isVisible) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        if (isVisible)
            Assert.assertTrue(claimInfoScreen.getInsuranceCompanyFld().isDisplayed());
        else
            Assert.assertFalse(claimInfoScreen.getInsuranceCompanyFld().isDisplayed());
    }

    public static void validateClaimVisible(boolean isVisible) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        if (isVisible)
            Assert.assertTrue(claimInfoScreen.getClaimFld().isDisplayed());
        else
            Assert.assertFalse(claimInfoScreen.getClaimFld().isDisplayed());
    }

    public static void validatePolicyNumberVisible(boolean isVisible) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        if (isVisible)
            Assert.assertTrue(claimInfoScreen.getPolicyFld().isDisplayed());
        else
            Assert.assertFalse(claimInfoScreen.getPolicyFld().isDisplayed());
    }

    public static void validateDeductibleVisible(boolean isVisible) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        if (isVisible)
            Assert.assertTrue(claimInfoScreen.getDeductibleFld().isDisplayed());
        else
            Assert.assertFalse(claimInfoScreen.getDeductibleFld().isDisplayed());
    }

    public static void validateInsuranceCompanyValue(String expectedInsuranceCompany) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        Assert.assertEquals(claimInfoScreen.getInsuranceCompanyFld().getAttribute("value"), expectedInsuranceCompany);
    }
}
