package com.cyberiansoft.test.vnextbo.validations.companyinfo;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.companyinfo.VNextBOCompanyInfoData;
import com.cyberiansoft.test.vnextbo.interactions.companyinfo.VNextBOCompanyInfoPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOCompanyInfoPage;
import org.testng.Assert;

public class VNextBOCompanyInfoPageValidations {

    private static boolean isSuccessNotificationDisplayed(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOCompanyInfoPage().getSuccessMessage(), expected, 7);
    }

    public static void verifySuccessNotificationIsDisplayed() {
        Assert.assertTrue(isSuccessNotificationDisplayed(true),
                "The success notification hasn't been displayed after clicking the 'Save' button");
    }
    
    public static void verifyCompanyInfoValues(VNextBOCompanyInfoData companyInfo, int index) {
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getCompanyValue(), companyInfo.getCompany()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getAddressLine1Value(), companyInfo.getAddressLine1()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getAddressLine2Value(), companyInfo.getAddressLine2()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getCityValue(), companyInfo.getCity()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getCountryValue(), companyInfo.getCountry()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getStateProvinceValue(), companyInfo.getStateProvince()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getZipValue(), companyInfo.getZip()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getPhoneCodeValue(), companyInfo.getPhoneCode()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getPhoneValue(), companyInfo.getPhone()[index]);
        Assert.assertEquals(VNextBOCompanyInfoPageInteractions.getEmailValue(), companyInfo.getEmail()[index]);
    }
}
