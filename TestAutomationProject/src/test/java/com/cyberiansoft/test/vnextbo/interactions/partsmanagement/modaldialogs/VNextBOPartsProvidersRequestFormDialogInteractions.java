package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogValidations;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class VNextBOPartsProvidersRequestFormDialogInteractions {

    public static void waitForRequestFormDialogToBeOpened() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOPartsProvidersRequestFormDialog().getRequestFormDialog(), 5);
    }

    public static void waitForRequestFormDialogToBeClosed() {
        WaitUtilsWebDriver.waitForAttributeToContainIgnoringException(
                new VNextBOPartsProvidersRequestFormDialog().getRequestFormDialog(), "style", "display: none", 5);
        VNextBOPartsProvidersRequestFormDialogValidations.verifyRequestFormDialogIsOpened(false);
    }

    public static String getRequestFormDialogMessage() {
        return Utils.getText(new VNextBOPartsProvidersRequestFormDialog().getRequestFormDialogMessage());
    }

    public static String getTitle() {
        return Utils.getText(new VNextBOPartsProvidersRequestFormDialog().getTitle());
    }

    public static void clickRequestQuoteButton() {
        Utils.clickElement(new VNextBOPartsProvidersRequestFormDialog().getRequestQuoteButton());
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been displayed");
    }

    public static void clickCancelButton() {
        Utils.clickElement(new VNextBOPartsProvidersRequestFormDialog().getCancelButton());
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been displayed");
    }

    public static String getVinFromTitle(String title) {
        return Arrays.stream(title.split(" ")).reduce((first, second) -> second).get();
    }

    public static List<String> getPartNamesList() {
        return Utils.getText(new VNextBOPartsProvidersRequestFormDialog().getPartNames());
    }

    public static Integer getPartQuantityByOrder(int order) {
        return Integer.parseInt(Utils.getText(new VNextBOPartsProvidersRequestFormDialog().getPartQuantityFields().get(order)));
    }

    public static void clickPartCheckbox(int order) {
        Utils.clickElement(new VNextBOPartsProvidersRequestFormDialog().getPartCheckboxes().get(order));
    }

    public static void clickFirstPartCheckboxes(int partsNumber) {
        for (int i = 0; i < partsNumber; i++) {
            clickPartCheckbox(i);
        }
    }
}
