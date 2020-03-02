package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.WorkOrderStatuses;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularBaseAppScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import org.openqa.selenium.Alert;

public class RegularMyWorkOrdersSteps {

    public static void startCreatingWorkOrderWithJob(IWorkOrdersTypes workOrdersType, String jobName) {
        startCreatingWorkOrder(workOrdersType);
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectJob(jobName);
    }

    public static void startCreatingWorkOrder(AppCustomer appCustomer, IWorkOrdersTypes workOrdersType) {
        waitMyWorkOrdersLoaded();
        clickAddWorkOrderButton();
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void startCreatingWorkOrder(IWorkOrdersTypes workOrdersType) {
        waitMyWorkOrdersLoaded();
        clickAddWorkOrderButton();
        RegularWorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void clickAddWorkOrderButton() {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickAddOrderButton();
    }

    public static void waitMyWorkOrdersLoaded() {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.waitMyWorkOrdersScreenLoaded();
    }

    public static void selectWorkOrderForCopyServices(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY_SERVICES);
    }

    public static void clickCreateInvoiceIconForWO(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderId);
    }

    public static void clickCreateInvoiceIconAndSelectInvoiceType(IInvoicesTypes invoiceType) {
        clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(invoiceType);
    }

    public static void clickInvoiceIcon() {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickInvoiceIcon();
    }


    public static void selectWorkOrderForApprove(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderForApprove(workOrderId);
    }

    public static void selectWorkOrder(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrder(workOrderId);
    }

    public static void selectSendEmailMenuForWorkOrder(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
    }

    public static void selectWorkOrderForCopyVehicle(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY_VEHICLE);
    }

    public static void selectWorkOrderForEdit(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.EDIT);
    }

    public static void selectWorkOrderNotesMenu(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.NOTES);
    }

    public static void selectWorkOrderDeleteMenu(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.DELETE);
    }

    public static void selectWorkOrderForNewInspection(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.NEW_INSPECTION);
    }

    public static void openWorkOrderDetails(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.DETAILS);
    }

    public static void selectWorkOrderChangeStatusMenu(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_STATUS);
    }

    public static void changeCustomerForWorkOrder(String workOrderId, AppCustomer customer) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_CUSTOMER);
        RegularCustomersScreenSteps.selectCustomer(customer);
        waitMyWorkOrdersLoaded();
    }

    public static void deleteWorkOrder(String workOrderID) {
        selectWorkOrderDeleteMenu(workOrderID);
        Helpers.getAlertTextAndAccept();
    }

    public static void startCreatingNewInspectionfromWorkOrder(String workOrderId, IInspectionsTypes inspectionsType) {
        selectWorkOrderForNewInspection(workOrderId);
        RegularInspectionTypesSteps.selectInspectionType(inspectionsType);
    }

    public static void changeStatusForWorkOrder(String workOrderId, WorkOrderStatuses workOrderStatuse) {
        selectWorkOrderChangeStatusMenu(workOrderId);
        RegularBaseAppScreen baseAppScreen = new RegularBaseAppScreen();
        baseAppScreen.selectUIAPickerValue(workOrderStatuse.getValue());
        baseAppScreen.clickPickerWheelDoneButton();
    }

    public static void startCopyingServicesForWorkOrder(String workOrderID, IWorkOrdersTypes workOrdersType) {
        selectWorkOrderForCopyServices(workOrderID);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void startCopyingServicesForWorkOrder(String workOrderID, AppCustomer appCustomer, IWorkOrdersTypes workOrdersType) {
        selectWorkOrderForCopyServices(workOrderID);
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void startCopyingVehicleForWorkOrder(String workOrderID, IWorkOrdersTypes workOrdersType) {
        selectWorkOrderForCopyVehicle(workOrderID);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void startCopyingVehicleForWorkOrder(String workOrderID, AppCustomer appCustomer, IWorkOrdersTypes workOrdersType) {
        selectWorkOrderForCopyVehicle(workOrderID);
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void cancelCreatingWorkOrder() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickCancelWizard();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
    }

    public static void switchToTeamView() {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        waitMyWorkOrdersLoaded();
        myWorkOrdersScreen.switchToTeamView();
    }
}
