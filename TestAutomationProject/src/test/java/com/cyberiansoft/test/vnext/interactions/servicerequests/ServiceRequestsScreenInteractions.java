package com.cyberiansoft.test.vnext.interactions.servicerequests;

import com.cyberiansoft.test.vnext.screens.servicerequests.ServiceRequestsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ServiceRequestListElement;
import org.openqa.selenium.By;

public class ServiceRequestsScreenInteractions {

    public static void tapOnFirstServiceRequestRecord() {

        WaitUtils.click(new ServiceRequestsScreen().getServiceRequestsListElements().get(0).getRootElement());
    }

    public static void expandFirstServiceRequestRecord() {

        WaitUtils.click(By.xpath(new ServiceRequestsScreen().getServiceRequestsListElements().get(0).getExpandButtonLocator()));
    }

    public static int getFirstSRInspectionCount() {

        expandFirstServiceRequestRecord();
        ServiceRequestListElement serviceRequestListElement = new ServiceRequestsScreen().getServiceRequestsListElements().get(0);
        WaitUtils.getGeneralFluentWait().until(__ -> !serviceRequestListElement.getRootElement().findElement(By.xpath(serviceRequestListElement.getInspectionsCount())).getText().equals(""));
        int inspectionCount = Integer.parseInt(serviceRequestListElement.getRootElement().findElement(By.xpath(serviceRequestListElement.getInspectionsCount())).getText());
        expandFirstServiceRequestRecord();
        return inspectionCount;
    }
}
