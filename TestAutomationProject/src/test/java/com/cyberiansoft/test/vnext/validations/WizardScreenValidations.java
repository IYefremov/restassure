package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VnextBaseServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class WizardScreenValidations {

    public static void validateTotalPriceValue(String expectedPrice) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        VnextBaseServicesScreen servicesScreen = new VnextBaseServicesScreen();
        WaitUtils.waitUntilElementInvisible(By.xpath("//div[@class='notifier']"));
        WaitUtils.getGeneralFluentWait(5, 300).
                until(ExpectedConditions.textToBePresentInElement(servicesScreen.getTotalPrice(), expectedPrice));
        Assert.assertEquals(baseWizardScreen.getInspectionTotalPriceValue(), expectedPrice);
    }
}
