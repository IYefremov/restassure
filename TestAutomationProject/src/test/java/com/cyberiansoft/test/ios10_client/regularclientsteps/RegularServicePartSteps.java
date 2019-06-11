package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServicePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServicePartPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import org.testng.Assert;

public class RegularServicePartSteps {

    public static void selectServicePartData(ServicePartData servicePartData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (servicePartData.getServiceCategory() != null)
            if (!servicePartData.getServiceCategory().isPredefined())
                selectedServiceDetailsScreen.selectServicePartCategory(servicePartData.getServiceCategory().getPartName());
        if (servicePartData.getServiceSubCategory() != null)
            if (!servicePartData.getServiceSubCategory().isPredefined())
                selectedServiceDetailsScreen.selectServicePartSubcategory(servicePartData.getServiceSubCategory().getPartName());
        if (servicePartData.getServiceSubCategoryPart() != null)
            if (!servicePartData.getServiceSubCategoryPart().isPredefined())
                selectedServiceDetailsScreen.selectServicePartSubcategoryPart(servicePartData.getServiceSubCategoryPart().getPartName());
        if (servicePartData.getServiceSubCategoryPosition() != null)
            if (!servicePartData.getServiceSubCategoryPosition().isPredefined())
                selectedServiceDetailsScreen.selectServicePartSubcategoryPosition(servicePartData.getServiceSubCategoryPosition().getPartName());
        verifyServicePartData(servicePartData);
        selectedServiceDetailsScreen.clickSelectedServiceDetailsDoneButton();
    }

    public static void verifyServicePartData(ServicePartData servicePartData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (servicePartData.getServiceCategory() != null)
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePartCategoryValue(), servicePartData.getServiceCategory().getPartName());
        if (servicePartData.getServiceSubCategory() != null)
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePartSubCategoryValue(), servicePartData.getServiceSubCategory().getPartName());
    }

}
