package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOAddNewServiceMonitorDialogInteractions;

public class VNextBOAddNewServiceMonitorDialogSteps {

    public static void setAddNewLaborServiceMonitorValues(VNextBOMonitorData data, String serviceDescription) {
        VNextBOAddNewServiceMonitorDialogInteractions.setPriceType(data.getPriceType());
        VNextBOAddNewServiceMonitorDialogInteractions.setService(data.getService());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceDetails(data.getServiceDetails());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceDescription(serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceLaborRate(data.getServiceLaborRate());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceLaborTime(data.getServiceLaborTime());
        VNextBOAddNewServiceMonitorDialogInteractions.clickSubmitButton();
    }

    public static void setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithSubmit(
            VNextBOMonitorData data, String serviceDescription) {
        setAddNewServiceFieldsForAllOrMoneyPriceTypes(data, serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.clickSubmitButton();
    }

    public static void setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithCancelButton(
            VNextBOMonitorData data, String serviceDescription) {
        setAddNewServiceFieldsForAllOrMoneyPriceTypesWithoutServiceDetails(data, serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.clickCancelButton();
    }

    public static void setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithXButton(
            VNextBOMonitorData data, String serviceDescription) {
        setAddNewServiceFieldsForAllOrMoneyPriceTypesWithoutServiceDetails(data, serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.clickXButton();
    }

    private static void setAddNewServiceFieldsForAllOrMoneyPriceTypes(VNextBOMonitorData data, String serviceDescription) {
        VNextBOAddNewServiceMonitorDialogInteractions.setPriceType(data.getPriceType());
        VNextBOAddNewServiceMonitorDialogInteractions.setService(data.getService());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceDetails(data.getServiceDetails());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceDescription(serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.setServicePrice(data.getServicePrice());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceQuantity(data.getServiceQuantity());
    }

    private static void setAddNewServiceFieldsForAllOrMoneyPriceTypesWithoutServiceDetails(VNextBOMonitorData data, String serviceDescription) {
        VNextBOAddNewServiceMonitorDialogInteractions.setPriceType(data.getPriceType());
        VNextBOAddNewServiceMonitorDialogInteractions.setService(data.getService());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceDescription(serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.setServicePrice(data.getServicePrice());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceQuantity(data.getServiceQuantity());
    }

    public static String getSubcategoryWhileAddingNewPartServiceMonitorValues(
            VNextBOMonitorData data, String serviceDescription) {
        VNextBOAddNewServiceMonitorDialogInteractions.setPriceType(data.getPriceType());
        VNextBOAddNewServiceMonitorDialogInteractions.setService(data.getService());
        VNextBOAddNewServiceMonitorDialogInteractions.setServiceDescription(serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.setCategory(data.getServiceCategory());
        return VNextBOAddNewServiceMonitorDialogInteractions.setSubcategory();
    }

    public static void setAddNewAllServiceMonitorValues(VNextBOMonitorData data, String serviceDescription) {
        setAddNewServiceFieldsForAllOrMoneyPriceTypes(data, serviceDescription);
        VNextBOAddNewServiceMonitorDialogInteractions.clickSubmitButton();
    }
}
