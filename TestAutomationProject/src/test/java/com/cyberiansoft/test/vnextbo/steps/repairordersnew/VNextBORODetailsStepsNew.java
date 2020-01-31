package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBORODetailsWebPageNew;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBORODetailsStepsNew {

    public static List<String> getServiceAndTaskDescriptionsList() {

        final List<WebElement> serviceAndTaskDescriptionsList = new VNextBORODetailsWebPageNew().getServiceAndTaskDescriptionsList();
        return serviceAndTaskDescriptionsList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static void expandAllServiceRows() {

        for (WebElement expander : new VNextBORODetailsWebPageNew().getServiceExpanderList()) {
            Utils.clickElement(expander);
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        }
    }
}
