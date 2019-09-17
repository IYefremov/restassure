package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.IServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;

public class ServiceRequestSteps {

    public static void waitServiceRequestScreenLoaded() {
        ServiceRequestsScreen serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();
    }

    public static String getFirstServiceRequestNumber() {
        ServiceRequestsScreen serviceRequestsScreen = new ServiceRequestsScreen();
        return serviceRequestsScreen.getFirstServiceRequestNumber();
    }

    public static void selectServiceRequest(String serviceRequestNumber) {
        ServiceRequestsScreen serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
    }

    public static void startCreatingInspectionFromServiceRequest(String serviceRequestNumber, IInspectionsTypes inspectionType) {
        selectServiceRequest(serviceRequestNumber);
        MenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_INPECTION);
        InspectionTypesSteps.selectInspectionType(inspectionType);
    }

    public static void startCreatingWorkOrderFromServiceRequest(String serviceRequestNumber, IWorkOrdersTypes workOrdersType) {
        selectServiceRequest(serviceRequestNumber);
        MenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_WORKORDER_HD);
        WorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void startCreatingServicerequest(AppCustomer appCustomer, IServiceRequestTypes serviceRequestType) {
        waitServiceRequestScreenLoaded();
        ServiceRequestsScreen serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.clickAddButton();
        CustomersScreenSteps.selectCustomer(appCustomer);
        ServiceRequestTypesSteps.selectServiceRequestType(serviceRequestType);
    }

    public static void startCreatingServicerequest(IServiceRequestTypes serviceRequestType) {
        waitServiceRequestScreenLoaded();
        ServiceRequestsScreen serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.clickAddButton();
        ServiceRequestTypesSteps.selectServiceRequestType(serviceRequestType);
    }
}
