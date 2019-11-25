package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.AssignTechniciansPopup;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;

public class AssignTechniciansSteps {

    public static void clickTechniciansCell() {
        AssignTechniciansPopup assignTechniciansPopup = new AssignTechniciansPopup();
        assignTechniciansPopup.clickTechniciansCell();
    }

    public static void selectWorkOrderToAssignTechnicians() {
        AssignTechniciansPopup assignTechniciansPopup = new AssignTechniciansPopup();
        assignTechniciansPopup.selectWorkOrderToAssingTechnicians();
    }

    public static void clickDoneButton() {
        AssignTechniciansPopup assignTechniciansPopup = new AssignTechniciansPopup();
        assignTechniciansPopup.clickDoneButton();
    }

    public static void assignTechniciansToWorkOrder() {
        selectWorkOrderToAssignTechnicians();
        //AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.SELECTED_TECHNICIANS_WILL_BE_ASSIGNED_TO_ALL_WO_SERVICES);
        clickDoneButton();
    }

}
