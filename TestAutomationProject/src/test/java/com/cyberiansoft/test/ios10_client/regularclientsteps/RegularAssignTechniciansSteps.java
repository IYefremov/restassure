package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularAssignTechniciansScreen;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;

public class RegularAssignTechniciansSteps {

    public static void clickTechniciansCell() {
        RegularAssignTechniciansScreen assignTechniciansScreen = new RegularAssignTechniciansScreen();
        assignTechniciansScreen.clickTechniciansCell();
    }

    public static void selectWorkOrderToAssignTechnicians() {
        RegularAssignTechniciansScreen assignTechniciansScreen = new RegularAssignTechniciansScreen();
        assignTechniciansScreen.selectWorkOrderToAssingTechnicians();
    }

    public static void clickDoneButton() {
        RegularAssignTechniciansScreen assignTechniciansScreen = new RegularAssignTechniciansScreen();
        assignTechniciansScreen.clickDoneButton();
    }

    public static void assignTechniciansToWorkOrder() {
        selectWorkOrderToAssignTechnicians();
        //AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.SELECTED_TECHNICIANS_WILL_BE_ASSIGNED_TO_ALL_WO_SERVICES);
        clickDoneButton();
    }

    public static void assignTechniciansToWorkOrderWithServices() {
        selectWorkOrderToAssignTechnicians();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.SELECTED_TECHNICIANS_WILL_BE_ASSIGNED_TO_ALL_WO_SERVICES);
        clickDoneButton();
    }
}
