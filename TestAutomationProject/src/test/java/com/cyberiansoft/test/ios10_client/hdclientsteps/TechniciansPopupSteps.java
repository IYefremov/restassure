package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TechniciansPopup;

public class TechniciansPopupSteps {

    public static void selectTechniciansCustomView() {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        techniciansPopup.selectTechniciansCustomView();
    }

    public static void selectServiceTechnician(ServiceTechnician serviceTechnician) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        techniciansPopup.selecTechnician(serviceTechnician.getTechnicianFullName());
    }

    public static void unselectServiceTechnician(ServiceTechnician serviceTechnician) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        techniciansPopup.unselecTechnician(serviceTechnician.getTechnicianFullName());
    }

    public static void setTechnicianCustomPriceValue(ServiceTechnician serviceTechnician) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        techniciansPopup.setTechnicianCustomPriceValue(serviceTechnician.getTechnicianFullName(),
                serviceTechnician.getTechnicianPriceValue());
    }

    public static void setTechnicianCustomPercentageValue(ServiceTechnician serviceTechnician) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        techniciansPopup.setTechnicianCustomPriceValue(serviceTechnician.getTechnicianFullName(),
                serviceTechnician.getTechnicianPercentageValue());
    }

    public static void saveTechViewDetails() {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        techniciansPopup.saveTechViewDetails();
    }
}
