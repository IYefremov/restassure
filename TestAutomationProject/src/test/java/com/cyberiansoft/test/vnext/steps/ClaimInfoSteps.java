package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;

public class ClaimInfoSteps {

    public static void setPolicyNumber(String policyNumber) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        claimInfoScreen.getPolicyFld().clear();
        claimInfoScreen.getPolicyFld().sendKeys(policyNumber);
    }

    public static void setClaimNumber(String claimNumber) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        claimInfoScreen.getClaimFld().clear();
        claimInfoScreen.getClaimFld().sendKeys(claimNumber);
    }

    public static void selectInsuranceCompany(String insuranceCompany) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        claimInfoScreen.getInsuranceCompanyFld().clear();
        claimInfoScreen.getInsuranceCompanyFld().sendKeys(insuranceCompany);
    }

    public static void setDeductibleValue(String deductible) {
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen();
        claimInfoScreen.setDeductibleValue(deductible);
    }
}
