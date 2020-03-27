package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogValidations;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOPartsProvidersDialogSteps {

    public static void openRequestFormDialog(String provider) {
        VNextBOPartsProvidersDialogInteractions.clickGetNewQuoteButton(provider);
        Assert.assertTrue(VNextBOPartsProvidersRequestFormDialogValidations.isRequestFormDialogOpened(),
                "The request form dialog hasn't been opened");
    }

    public static String openStore(String provider) {
        WaitUtilsWebDriver.waitABit(1000);
        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        VNextBOPartsProvidersDialogInteractions.clickGetNewQuoteButton(provider);
        WaitUtilsWebDriver.waitForNewTab();
        Utils.getNewTab(mainWindow);
        return mainWindow;
    }

    public static void closePartsProvidersDialog() {
        VNextBOPartsProvidersDialogInteractions.clickCloseButton();
        VNextBOPartsProvidersDialogInteractions.waitForPartsProvidersModalDialogToBeClosed();
    }

    public static String getRandomDataProvider() {
        final List<WebElement> providerNamesList = new VNextBOPartsProvidersDialog().getDataProvidersList();
        int order = RandomUtils.nextInt(0, providerNamesList.size());
        return Utils.getText(providerNamesList.get(order));
    }

    public static List<String> getOptionsList() {
        return Utils.getText(new VNextBOPartsProvidersDialog().getOptionsList())
                .stream()
                .map(option -> option.substring(option.indexOf("-") + 2))
                .collect(Collectors.toList());
    }

    public static List<String> getProvidersList() {
        final List<String> providers = Utils.getText(new VNextBOPartsProvidersDialog().getOptionsList());
        providers.forEach(System.out::println);
        return providers;
    }
}
