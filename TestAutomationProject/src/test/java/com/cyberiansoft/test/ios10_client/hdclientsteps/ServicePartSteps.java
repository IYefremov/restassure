package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.ServicePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServicePartPopup;
import org.testng.Assert;

public class ServicePartSteps {

    public static void selectServicePartData(ServicePartData servicePartData) {
        ServicePartPopup servicePartPopup = new ServicePartPopup();
        if (servicePartData.getServiceCategory() != null)
            if (!servicePartData.getServiceCategory().isPredefined())
                servicePartPopup.selectServicePartCategory(servicePartData.getServiceCategory().getPartName());
        if (servicePartData.getServiceSubCategory() != null)
            if (!servicePartData.getServiceSubCategory().isPredefined())
                servicePartPopup.selectServicePartSubcategory(servicePartData.getServiceSubCategory().getPartName());
        if (servicePartData.getServiceSubCategoryPart() != null)
            if (!servicePartData.getServiceSubCategoryPart().isPredefined())
                servicePartPopup.selectServicePartSubcategoryPart(servicePartData.getServiceSubCategoryPart().getPartName());
        if (servicePartData.getServiceSubCategoryPosition() != null)
            if (!servicePartData.getServiceSubCategoryPosition().isPredefined())
                servicePartPopup.selectServicePartSubcategoryPosition(servicePartData.getServiceSubCategoryPosition().getPartName());
        verifyServicePartData(servicePartData);
        servicePartPopup.saveSelectedServicePart();
    }

    public static void verifyServicePartData(ServicePartData servicePartData) {
        ServicePartPopup servicePartPopup = new ServicePartPopup();
        if (servicePartData.getServiceCategory() != null)
            Assert.assertEquals(servicePartPopup.getServicePartCategoryValue(), servicePartData.getServiceCategory().getPartName());
        if (servicePartData.getServiceSubCategory() != null)
            Assert.assertEquals(servicePartPopup.getServicePartSubCategoryValue(), servicePartData.getServiceSubCategory().getPartName());
    }
}
