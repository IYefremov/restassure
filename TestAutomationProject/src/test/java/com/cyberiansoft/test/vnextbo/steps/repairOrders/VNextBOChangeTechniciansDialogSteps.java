package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOChangeTechniciansDialogInteractions;
import com.cyberiansoft.test.vnextbo.verifications.dialogs.VNextBOChangeTechniciansDialogVerifications;

public class VNextBOChangeTechniciansDialogSteps {

    private VNextBOChangeTechniciansDialogInteractions techniciansDialogInteractions;

    public VNextBOChangeTechniciansDialogSteps() {
        techniciansDialogInteractions = new VNextBOChangeTechniciansDialogInteractions();
    }

    public void setOptionsForTechniciansDialog(String vendor, String technician) {
        final VNextBOChangeTechniciansDialogVerifications techniciansDialogVerifications =
                new VNextBOChangeTechniciansDialogVerifications();
        techniciansDialogVerifications.verifyVendorIsSet(vendor);
        techniciansDialogVerifications.verifyTechnicianIsSet(technician);
    }

    public void setOptionsAndClickOkButtonForTechniciansDialog(String vendor, String technician) {
        setOptionsForTechniciansDialog(vendor, technician);
        techniciansDialogInteractions.clickOrderServiceOkButton();
    }

    public void setOptionsAndClickCancelButtonForTechniciansDialog(String vendor, String technician) {
        setOptionsForTechniciansDialog(vendor, technician);
        techniciansDialogInteractions.clickOrderServiceCancelButton();
    }

    public void setOptionsAndClickXButtonForTechniciansDialog(String vendor, String technician) {
        setOptionsForTechniciansDialog(vendor, technician);
        techniciansDialogInteractions.clickOrderServiceXButton();
    }
}