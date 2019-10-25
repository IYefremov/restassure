package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.InsuranceCompanyData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ClaimScreen;

public class ClaimScreenSteps {

    public static void setClaimData(InsuranceCompanyData insuranceCompanyData) {
        ClaimScreen claimScreen = new ClaimScreen();
        if (insuranceCompanyData.getInsuranceCompanyName() != null)
            claimScreen.selectInsuranceCompany(insuranceCompanyData.getInsuranceCompanyName());
        if (insuranceCompanyData.getClaimNumber() != null)
            claimScreen.setClaim(insuranceCompanyData.getClaimNumber());
        if (insuranceCompanyData.getPolicyNumber() != null)
            claimScreen.setPolicy(insuranceCompanyData.getPolicyNumber());
        if (insuranceCompanyData.getDeductible() != null)
            claimScreen.setDeductible(insuranceCompanyData.getDeductible());
    }
}
