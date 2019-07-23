package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.InsuranceCompanyData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularClaimScreen;

public class RegularClaimScreenSteps {

    public static void setClaimData(InsuranceCompanyData insuranceCompanyData) {
        RegularClaimScreen claimScreen = new RegularClaimScreen();
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
