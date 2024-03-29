package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;

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
            selectServiceVehiclePart(serviceData.getVehiclePart());
        if (serviceData.getVehicleParts() != null)
            selectServiceVehicleParts(serviceData.getVehicleParts());
        if (serviceData.getPreExistingDamage() != null)
            selectPreExistingDamage(serviceData.getPreExistingDamage());
        if (serviceData.getQuestionData() != null)
            selectedServiceDetailsScreen.answerServiceQuestion(serviceData.getQuestionData());
        if (serviceData.getQuestionsData() != null)
            selectedServiceDetailsScreen.answerQuestions(serviceData.getQuestionsData());
        if (serviceData.getServicePartData() != null) {
            selectedServiceDetailsScreen.clickServicePartCell();
            RegularServicePartSteps.selectServicePartData(serviceData.getServicePartData());
        }
    }

    public static void selectPreExistingDamage(String damageName) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.checkPreexistingDamage(damageName);
    }

    public static void setServiceNotes(String notes) {
        clickNotes();
        RegularNotesScreenSteps.setTextNotes(notes);
        RegularNotesScreenSteps.saveNotes();
    }

    public static void clickNotes() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickNotesCell();
    }

    public static void setServicePriceValue(String servicePrice) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(servicePrice);
    }

    public static void setServiceQuantityValue(String serviceQuantity) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceQuantityValue(serviceQuantity);
    }

    public static void selectServiceVehiclePart(VehiclePartData vehiclePartData) {
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

    public static void selectTechniciansEvenlyView() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selectTechniciansEvenlyView();
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
        clickAdjustmentsCell();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selectAdjustment(serviceAdjustmentData.getAdjustmentData().getAdjustmentName());
    }

    public static void setLaborServiceData(LaborServiceData laborServiceData) {
        if (laborServiceData.getLaborServiceRate() != null) {
            setLaborServiceRate(laborServiceData.getLaborServiceRate());
        }

        if (laborServiceData.getLaborServicePanel() != null) {
            selectLaborServicePanels(laborServiceData);
        }
        if (laborServiceData.getLaborServiceTime() != null) {
            setLaborServiceTime(laborServiceData.getLaborServiceTime());
        }
    }

    public static void setLaborServiceRate(String rateValue) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceRateValue(rateValue);
    }

    public static void setLaborServiceTime(String timeValue) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceTimeValue(timeValue);
    }

    public static void selectLaborServicePanels(LaborServiceData laborServiceData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickOperationCell();
        selectedServiceDetailsScreen.selectLaborServicePanel(laborServiceData.getLaborServicePanel());
        selectedServiceDetailsScreen.selectLaborServicePart(laborServiceData.getLaborServicePart());
        selectedServiceDetailsScreen.clickSelectedServiceDetailsDoneButton();
    }

    public static void selectLaborPartServiceData(PartServiceData partServiceData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickOperationCell();

        if (partServiceData.getCategory() != null)
            selectedServiceDetailsScreen.selectServicePartCategory(partServiceData.getCategory());
        if (partServiceData.getSubCategory() != null)
            selectedServiceDetailsScreen.selectServicePartSubcategory(partServiceData.getSubCategory());
        if (partServiceData.getPartName() != null) {
            for (String partName : partServiceData.getPartName().getPartNameList())
                selectedServiceDetailsScreen.selectServicePartSubcategoryPart(partName);
        }
        if (partServiceData.getPartPosition() != null)
            selectedServiceDetailsScreen.selectServicePartSubcategoryPosition(partServiceData.getPartPosition());


    }

    public static void clickSelectedServiceDetailsDoneButton() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickSelectedServiceDetailsDoneButton();
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

    public static void clickRemoveServiceButton() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickRemoveServiceButton();
    }

    public static void answerServiceQuestion(QuestionsData questionsData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.answerServiceQuestion(questionsData);
    }

    public static void clickAdjustmentsCell() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickAdjustments();
    }

    public static void clickServicePartCell() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickServicePartCell();
    }

}
