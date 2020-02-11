package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOAddNewServiceDialog;
import org.testng.Assert;

public class VNextBOAddNewServiceDialogValidations {

    public static void verifyPriceTypeIsCorrect(String expectedPriceType) {

        Assert.assertEquals(Utils.getText(new VNextBOAddNewServiceDialog().getPriceTypeDropDown()), expectedPriceType,
                "Price type hasn't been correct");
    }

    public static void verifyServiceIsCorrect(String expectedService) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewServiceDialog().getServiceDropDownField()), expectedService,
                "Service hasn't been correct");
    }

    public static void verifyDescriptionIsCorrect(String expectedDescription) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewServiceDialog().getDescriptionTextArea()), expectedDescription,
                "Description hasn't been correct");
    }

    public static void verifyPriceIsCorrect(String expectedPrice) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewServiceDialog().getPriceVisibleInputField()), expectedPrice,
                "Price hasn't been correct");
    }

    public static void verifyQuantityIsCorrect(String expectedQuantity) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewServiceDialog().getQuantityVisibleInputField()), expectedQuantity,
                "Quantity hasn't been correct");
    }

    public static void verifyLaborRateIsCorrect(String expectedRate) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewServiceDialog().getLaborRateVisibleInputField()), expectedRate,
                "Labor rate hasn't been correct");
    }

    public static void verifyLaborTimeIsCorrect(String expectedTime) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewServiceDialog().getLaborTimeVisibleInputField()), expectedTime,
                "Labor time hasn't been correct");
    }

    public static void verifyCategoryIsCorrect(String expectedCategory) {

        Assert.assertEquals(Utils.getText(new VNextBOAddNewServiceDialog().getCategoryDropDownField()), expectedCategory,
                "Category hasn't been correct");
    }

    public static void verifySubCategoryIsCorrect(String expectedSubCategory) {

        Assert.assertEquals(Utils.getText(new VNextBOAddNewServiceDialog().getSubCategoryDropDownField()), expectedSubCategory,
                "Subcategory hasn't been correct");
    }

    private static void allServicesTypesValidations(VNextBOMonitorData serviceData) {
        verifyPriceTypeIsCorrect(serviceData.getPriceType());
        verifyServiceIsCorrect(serviceData.getService());
        verifyDescriptionIsCorrect(serviceData.getServiceDescription());
    }

    public static void verifyAllFieldsAreSetCorrectly(VNextBOMonitorData serviceData) {

        allServicesTypesValidations(serviceData);
        verifyPriceIsCorrect(serviceData.getServicePrice());
        verifyQuantityIsCorrect(serviceData.getServiceQuantity());
    }

    public static void verifyLaborServiceFieldsAreSetCorrectly(VNextBOMonitorData serviceData) {

        allServicesTypesValidations(serviceData);
        verifyLaborRateIsCorrect(serviceData.getServiceLaborRate());
        verifyLaborTimeIsCorrect(serviceData.getServiceLaborTime());
    }

    public static void verifyPartServiceFieldsAreSetCorrectly(VNextBOMonitorData serviceData) {

        verifyPriceTypeIsCorrect(serviceData.getPriceType());
        verifyServiceIsCorrect(serviceData.getService());
        verifyCategoryIsCorrect(serviceData.getServiceCategory());
        verifySubCategoryIsCorrect(serviceData.getServiceSubcategory());
    }
}
