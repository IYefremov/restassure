package com.cyberiansoft.test.bo.steps.operations.servicerequestslist;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist.BOSRListBlock;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BOSRListBlockSteps {

    public static String getSrByNumber(String srNumber) {
        return Utils.getText(new BOSRListBlock().getSrNumbersList())
                .stream()
                .map(num -> num.replace(",", ""))
                .filter(num -> num.equals(srNumber))
                .findFirst()
                .orElse("");
    }

    public static void openSRDetailsBlock(int order) {
        final List<WebElement> list = WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new BOSRListBlock().getSrPopoverList());
        if (list.size() >= order) {
            Utils.clickElement(new BOSRListBlock().getSrPopoverList().get(order));
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
            WaitUtilsWebDriver.waitForPendingRequestsToComplete();
            BOSRDetailsPanelSteps.waitForDetailsPanelToBeOpened();
            BOSRDetailsHeaderPanelSteps.waitForButtonsBlockToBeDisplayed();
        }
    }
}
