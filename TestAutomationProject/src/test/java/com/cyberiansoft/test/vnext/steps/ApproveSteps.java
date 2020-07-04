package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ApproveSteps {

    public static void drawSignature() {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        approveScreen.drawSignature();
    }

    public static void saveApprove() {
        ListSelectPageInteractions.saveListPage();
        WaitUtils.waitLoadDialogDisappears();
        try {
            WaitUtils.getGeneralFluentWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='notifier-contaier']")));
            WaitUtils.getGeneralFluentWait().until(ExpectedConditions.invisibilityOf(
                    ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@class='notifier-contaier']"))
            ));
        } catch (TimeoutException ex) {
        }
    }

    public static void clickClearSignatureButton() {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        approveScreen.clickClearSignatureButton();
    }
}
