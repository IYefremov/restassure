package com.cyberiansoft.test.vnextbo.steps.servicerequests;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.VNextBOSRPage;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.VNextBOSRPageValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.VNextBOSRTableValidations;
import io.appium.java_client.functions.ExpectedCondition;
import org.testng.Assert;

public class VNextBOSRPageSteps {

    private static void waitForLoadMoreServicesButton() {
        try {
            WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>) driver ->
                    VNextBOSRPageValidations.isLoadMoreServicesButtonEnabled());
        } catch (Exception ignored) {}
    }

    private static void clickLoadMoreButton(int srListSize) {
        if (VNextBOSRPageValidations.isLoadMoreServicesButtonDisplayed()) {
            Utils.clickElement(new VNextBOSRPage().getLoadMoreButton());
            Assert.assertTrue(VNextBOSRPageValidations.isLoadMoreServicesButtonDisabled(),
                    "The 'Load 20 more items' button is not disabled after click");
            VNextBOSRTableSteps.waitForServiceRequestsListToBeUpdated(srListSize);
            VNextBOSRTableValidations.verifyMoreServiceRequestsHaveBeenLoaded(srListSize);
        }
    }

    public static void loadMoreServices() {
        final int LOADED_ITEMS = 20;
        final int srListSize = VNextBOSRTableSteps.getSRListSize();
        if (srListSize >= LOADED_ITEMS && srListSize % LOADED_ITEMS == 0) {
            waitForLoadMoreServicesButton();
            clickLoadMoreButton(srListSize);
        }
    }
}
