package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogValidations;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class VNextBOPartsProvidersRequestFormDialogInteractions {

    private static VNextBOPartsProvidersRequestFormDialog requestFormDialog;

    static {
        requestFormDialog = new VNextBOPartsProvidersRequestFormDialog();
    }

    public static void waitForRequestFormDialogToBeOpened() {
        WaitUtilsWebDriver.elementShouldBeVisible(requestFormDialog.getRequestFormDialog(), true, 5);
    }

    public static String getRequestFormDialogMessage() {
        return Utils.getText(requestFormDialog.getRequestFormDialogMessage());
    }

    public static String getTitle() {
        return Utils.getText(requestFormDialog.getTitle());
    }

    public static void clickRequestQuoteButton() {
        Utils.clickElement(requestFormDialog.getRequestQuoteButton());
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been displayed");
    }

    public static void clickCancelButton() {
        Utils.clickElement(requestFormDialog.getCancelButton());
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been displayed");
    }

    public static String getVinFromTitle(String title) {
        return Arrays.stream(title.split(" ")).reduce((first, second) -> second).get();
    }

    public static List<String> getPartNamesList() {
        return Utils.getText(requestFormDialog.getPartNames());
    }

    public static Integer getPartQuantityByOrder(int order) {
        return Integer.parseInt(Utils.getText(requestFormDialog.getPartQuantityFields().get(order)));
    }

    public static void clickPartCheckbox(int order) {
        Utils.clickElement(requestFormDialog.getPartCheckboxes().get(order));
    }

    public static void clickFirstPartCheckboxes(int partsNumber) {
        for (int i = 0; i < partsNumber; i++) {
            clickPartCheckbox(i);
        }
    }
}
