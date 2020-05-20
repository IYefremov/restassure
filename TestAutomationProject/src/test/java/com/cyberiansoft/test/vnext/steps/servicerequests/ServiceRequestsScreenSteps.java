package com.cyberiansoft.test.vnext.steps.servicerequests;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.servicerequests.ServiceRequestsScreenInteractions;
import com.cyberiansoft.test.vnext.screens.servicerequests.ServiceRequestsScreen;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.openqa.selenium.By;

public class ServiceRequestsScreenSteps {

    public static void findServiceRequest(String serviceRequestNumber) {

        TopScreenPanelSteps.searchData(serviceRequestNumber);
        BaseUtils.waitABit(500);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        WaitUtils.getGeneralFluentWait().until(__ -> new ServiceRequestsScreen().getServiceRequestsListElements().size() == 1);

    }

    public static void createInspection(ServiceRequestData serviceRequestData) {

        ServiceRequestsScreenInteractions.tapOnFirstServiceRequestRecord();
        MenuSteps.selectMenuItem(MenuItems.CREATE_INPSECTION);
        VehicleInfoScreenInteractions.waitPageLoaded();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.validateVehicleInfo(serviceRequestData.getVihicleInfo());
        TopScreenPanelSteps.goToTheNextScreen();
        TopScreenPanelSteps.goToTheNextScreen();
        TopScreenPanelSteps.goToTheNextScreen();
        TopScreenPanelSteps.saveChanges();
        BaseUtils.waitABit(500);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }
}
