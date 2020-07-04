package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOChangeTechniciansDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOChangeTechniciansDialogValidations;

public class VNextBOChangeTechniciansDialogSteps {

    public static void setOptionsForTechniciansDialog(String vendor, String technician) {
        VNextBOChangeTechniciansDialogValidations.verifyVendorIsSet(vendor);
        VNextBOChangeTechniciansDialogValidations.verifyTechnicianIsSet(technician);
    }

    public static void setOptionsAndClickOkButtonForTechniciansDialog(String vendor, String technician) {
        setOptionsForTechniciansDialog(vendor, technician);
        new VNextBOChangeTechniciansDialogInteractions().clickOrderServiceOkButton();
    }

    public static void setOptionsAndClickCancelButtonForTechniciansDialog(String vendor, String technician) {
        setOptionsForTechniciansDialog(vendor, technician);
        new VNextBOChangeTechniciansDialogInteractions().clickOrderServiceCancelButton();
    }

    public static void setOptionsAndClickXButtonForTechniciansDialog(String vendor, String technician) {
        setOptionsForTechniciansDialog(vendor, technician);
        new VNextBOChangeTechniciansDialogInteractions().clickOrderServiceXButton();
    }
}