package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import org.testng.Assert;

import java.util.List;

public class RegularServiceDetailsScreenSteps {

    public static void setServiceDetailsDataAndSave(ServiceData serviceData) {
        setServiceDetailsData(serviceData);
        saveServiceDetails();
    }

    public static void setServiceDetailsData(ServiceData serviceData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (serviceData.getServicePrice() != null)
            setServicePriceValue(serviceData.getServicePrice());
        if (serviceData.getServiceQuantity() != null)
            setServiceQuantityValue(serviceData.getServiceQuantity());
        if (serviceData.getServiceNotes() != null)
            setServiceNotes(serviceData.getServiceNotes());
        if (serviceData.getVehiclePart() != null)
            slectServiceVehiclePart(serviceData.getVehiclePart());
        if (serviceData.getVehicleParts() != null)
            selectServiceVehicleParts(serviceData.getVehicleParts());
        if (serviceData.getQuestionData() != null)
            selectedServiceDetailsScreen.answerQuestion2(serviceData.getQuestionData());
        if (serviceData.getQuestionsData() != null)
            selectedServiceDetailsScreen.answerQuestions(serviceData.getQuestionsData());
    }

    public static void setServiceNotes(String notes) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickNotesCell();
        RegularNotesScreenSteps.setTextNotes(notes);
        RegularNotesScreenSteps.saveNotes();
    }

    public static void setServicePriceValue(String servicePrice) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(servicePrice);
    }

    public static void setServiceQuantityValue(String serviceQuantity) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceQuantityValue(serviceQuantity);
    }

    public static void slectServiceVehiclePart(VehiclePartData vehiclePartData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void selectServiceVehicleParts(List<VehiclePartData> vehiclePartsData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (VehiclePartData vehiclePartData : vehiclePartsData)
            selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void saveServiceDetails() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void cancelServiceDetails() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.cancelSelectedServiceDetails();
    }

    public static void clickServiceTechniciansIcon() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
    }

    public static void selectTechniciansCustomView() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
    }

    public static void selectServiceTechnician(ServiceTechnician serviceTechnician) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selecTechnician(serviceTechnician.getTechnicianFullName());
    }

    public static void unselectServiceTechnician(ServiceTechnician serviceTechnician) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.unselecTechnician(serviceTechnician.getTechnicianFullName());
    }

    public static void selectServiceAdjustment(ServiceAdjustmentData serviceAdjustmentData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickAdjustments();
        selectedServiceDetailsScreen.selectAdjustment(serviceAdjustmentData.getAdjustmentData().getAdjustmentName());
    }

    public static void setLaborServiceData(LaborServiceData laborServiceData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (laborServiceData.getLaborServiceRate() != null) {
            selectedServiceDetailsScreen.setServiceRateValue(laborServiceData.getLaborServiceRate());
        }

        if (laborServiceData.getLaborServicePanel() != null) {
            selectedServiceDetailsScreen.clickOperationCell();
            selectedServiceDetailsScreen.selectLaborServicePanel(laborServiceData.getLaborServicePanel());
            selectedServiceDetailsScreen.selectLaborServicePart(laborServiceData.getLaborServicePart());
            selectedServiceDetailsScreen.clickSelectedServiceDetailsDoneButton();
        }
        if (laborServiceData.getLaborServiceTime() != null) {
            selectedServiceDetailsScreen.setServiceTimeValue(laborServiceData.getLaborServiceTime());
        }

    }

    public static void setTechnicianCustomPriceValue(ServiceTechnician serviceTechnician) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(serviceTechnician.getTechnicianFullName(),
                serviceTechnician.getTechnicianPriceValue());
    }

    public static void setTechnicianCustomPercentageValue(ServiceTechnician serviceTechnician) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(serviceTechnician.getTechnicianFullName(),
                serviceTechnician.getTechnicianPercentageValue());
    }

}
