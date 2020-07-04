package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class ClaimInfoSteps {

    public static void setPolicyNumber(String policyNumber) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        claimInfoScreen.getPolicyFld().sendKeys(policyNumber);
    }

    public static void setClaimNumber(String claimNumber) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            claimInfoScreen.getClaimFld().sendKeys(claimNumber);
            return true;
        });
    }

    public static void selectInsuranceCompany(String insuranceCompany) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        WaitUtils.waitUntilElementIsClickable(claimInfoScreen.getInsuranceCompanyFld());
        claimInfoScreen.openInsuranceCompaniesList();
        claimInfoScreen.selectInsuranceCompany(insuranceCompany);
    }

    public static void setDeductibleValue(String deductible) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        claimInfoScreen.setDeductibleValue(deductible);
    }
}
