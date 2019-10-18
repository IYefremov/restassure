package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.ServiceAdjustmentData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.NotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;

import java.util.List;

public class ServiceDetailsScreenSteps {

    public static void setServiceDetailsData(ServiceData serviceData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        if (serviceData.getServicePrice() != null)
            setServicePriceValue(serviceData.getServicePrice());
        if (serviceData.getServiceQuantity() != null)
            setServiceQuantityValue(serviceData.getServiceQuantity());
        if (serviceData.getServiceNotes() != null)
            setServiceNotes(serviceData.getServiceNotes());
        if (serviceData.getPreExistingDamage() != null)
        selectPreExistingDamage(serviceData.getPreExistingDamage());
        if (serviceData.getVehiclePart() != null)
            slectServiceVehiclePart(serviceData.getVehiclePart());
        if (serviceData.getVehicleParts() != null)
            slectServiceVehicleParts(serviceData.getVehicleParts());
        if (serviceData.getQuestionData() != null)
            selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
        saveServiceDetails();
    }

    public static void setServicePriceValue(String servicePrice) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(servicePrice);
    }

    public static void selectPreExistingDamage(String damageName) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.checkPreexistingDamage(damageName);
    }

    public static void setServiceQuantityValue(String serviceQuantity) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceQuantityValue(serviceQuantity);
    }

    public static void setServiceNotes(String notes) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        NotesScreen notesScreen = selectedServiceDetailsScreen.clickNotesCell();
        notesScreen.setNotes(notes);
        notesScreen.clickSaveButton();
    }

    public static void slectServiceVehiclePart(VehiclePartData vehiclePartData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void slectServiceVehicleParts(List<VehiclePartData> vehiclePartsData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (VehiclePartData vehiclePartData : vehiclePartsData)
            selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void saveServiceDetails() {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void cancelServiceDetails() {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
    }

    public static void clickServiceTechniciansIcon() {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
    }

    public static void clickServiceTechniciansCell() {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansCell();
    }

    public static void selectServiceAdjustment(ServiceAdjustmentData serviceAdjustmentData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickAdjustments();
        selectedServiceDetailsScreen.selectAdjustment(serviceAdjustmentData.getAdjustmentData().getAdjustmentName());
        saveServiceDetails();
    }

    public static void setLaborServiceData(LaborServiceData laborServiceData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
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
}
