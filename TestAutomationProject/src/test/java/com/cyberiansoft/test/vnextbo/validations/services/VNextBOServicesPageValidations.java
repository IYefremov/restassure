package com.cyberiansoft.test.vnextbo.validations.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.services.VNextBOServiceData;
import com.cyberiansoft.test.vnextbo.screens.services.VNextBOServicesWebPage;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServicesWebPageSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOServicesPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyMoneyServiceRecordDataAreCorrect(VNextBOServiceData serviceData) {

        Assert.assertTrue(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Type").contains(serviceData.getServiceType()),
                "Type hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Name"),
                serviceData.getServiceName(), "Name hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Price"),
                "$" + serviceData.getServicePrice(), "Price hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordPriceTypeIconTitle(serviceData.getServiceName()), "Money", "Service icon hasn't been correct");
    }

    public static void verifyPercentageServiceRecordDataAreCorrect(VNextBOServiceData serviceData) {

        Assert.assertTrue(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Type").contains(serviceData.getServiceType()),
                "Type hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Name"),
                serviceData.getServiceName(), "Name hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Price"),
                serviceData.getServicePrice() + "%", "Price hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordPriceTypeIconTitle(serviceData.getServiceName()), "Percentage", "Service icon hasn't been correct");
    }

    public static void verifyLaborServiceRecordDataAreCorrect(VNextBOServiceData serviceData) {

        Assert.assertTrue(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Type").contains(serviceData.getServiceType()),
                "Type hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Name"),
                serviceData.getServiceName(), "Name hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Price"),
                "$" + serviceData.getServiceLaborRate(), "Price hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordPriceTypeIconTitle(serviceData.getServiceName()), "Labor", "Service icon hasn't been correct");
    }

    public static void verifyPartServiceRecordDataAreCorrect(VNextBOServiceData serviceData) {

        Assert.assertTrue(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Type").contains(serviceData.getServiceType()),
                "Type hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Name"),
                serviceData.getServiceName(), "Name hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordSpecificColumnValue(serviceData.getServiceName(), "Price"),
                "$0.00", "Price hasn't been correct");
        Assert.assertEquals(VNextBOServicesWebPageSteps.getServiceRecordPriceTypeIconTitle(serviceData.getServiceName()), "Part", "Service icon hasn't been correct");
    }

    public static void verifyServicesNotFoundMessageIsDisplayed() {

        Assert.assertEquals(Utils.getText(new VNextBOServicesWebPage().getServicesNotFoundMessage()), "No services to show",
                "Not found message hasn't been displayed or has been incorrect");
    }

    public static void verifyServiceOrderNumberIsCorrect(String serviceName, String expectedOrderNumber) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOServicesWebPage().orderNumberFieldByName(serviceName)), expectedOrderNumber,
                "Order number hasn't been correct");
    }
}