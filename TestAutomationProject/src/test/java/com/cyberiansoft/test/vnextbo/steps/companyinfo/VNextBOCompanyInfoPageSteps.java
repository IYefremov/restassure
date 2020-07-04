package com.cyberiansoft.test.vnextbo.steps.companyinfo;

import com.cyberiansoft.test.dataclasses.vNextBO.companyinfo.VNextBOCompanyInfoData;
import com.cyberiansoft.test.vnextbo.interactions.companyinfo.VNextBOCompanyInfoPageInteractions;

public class VNextBOCompanyInfoPageSteps {

    public static void setCompanyInfoValues(VNextBOCompanyInfoData companyInfo, int index) {
        VNextBOCompanyInfoPageInteractions.setCompanyName(companyInfo.getCompany()[index]);
        VNextBOCompanyInfoPageInteractions.setAddressLine1(companyInfo.getAddressLine1()[index]);
        VNextBOCompanyInfoPageInteractions.setAddressLine2(companyInfo.getAddressLine2()[index]);
        VNextBOCompanyInfoPageInteractions.setCity(companyInfo.getCity()[index]);
        VNextBOCompanyInfoPageInteractions.setCountry(companyInfo.getCountry()[index]);
        VNextBOCompanyInfoPageInteractions.setStateProvince(companyInfo.getStateProvince()[index]);
        VNextBOCompanyInfoPageInteractions.setZip(companyInfo.getZip()[index]);
        VNextBOCompanyInfoPageInteractions.setPhoneCode(companyInfo.getPhoneCode()[index]);
        VNextBOCompanyInfoPageInteractions.setPhone(companyInfo.getPhone()[index]);
        VNextBOCompanyInfoPageInteractions.setEmail(companyInfo.getEmail()[index]);
        VNextBOCompanyInfoPageInteractions.clickSaveButton();
    }
}
