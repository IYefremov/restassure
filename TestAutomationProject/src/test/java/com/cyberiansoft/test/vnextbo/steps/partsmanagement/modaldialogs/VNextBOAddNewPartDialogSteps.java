package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOAddNewPartDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAddNewServiceMonitorDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOAddNewPartDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAddNewPartDialogValidations;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class VNextBOAddNewPartDialogSteps {

    public static void setServiceField(String serviceName) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clearAndType(addNewPartDialog.getServiceField(), serviceName);
        Utils.selectOptionInDropDownWithJs(addNewPartDialog.getServiceFieldDropDown(),
                addNewPartDialog.dropDownFieldOption(serviceName));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void setDescription(String description) {

        Utils.clearAndType(new VNextBOAddNewPartDialog().getDescriptionTextarea(), description);
    }

    public static void setCategory(String categoryName) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clickElement(addNewPartDialog.getCategoryField());
        Utils.selectOptionInDropDownWithJs(addNewPartDialog.getCategoryFieldDropDown(),
                addNewPartDialog.dropDownFieldOption(categoryName));
    }

    public static void setSubCategory(String subCategoryName) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clickElement(addNewPartDialog.getSubCategoryField());
        Utils.selectOptionInDropDownWithJs(addNewPartDialog.getSubCategoryFieldDropDown(),
                addNewPartDialog.dropDownFieldOption(subCategoryName));
    }

    public static void selectPartsFromPartsList(List<String> partsList) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        for (String part : partsList) {
            VNextBOAddNewPartDialogInteractions.setPartName(part);
            Utils.clickElement(addNewPartDialog.partsListOptionByText(part));
        }
    }

    public static void submit() {
        VNextBOAddNewPartDialogInteractions.clickSubmitButton();
        VNextBOAddNewPartDialogValidations.verifyDialogIsDisplayed(false);
    }

    public static void cancel() {
        VNextBOAddNewPartDialogInteractions.clickCancelButton();
        VNextBOAddNewPartDialogValidations.verifyDialogIsDisplayed(false);
    }

    public static void openServiceDropDown() {
        final VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clickElement(addNewPartDialog.getServiceFieldArrow());
        WaitUtilsWebDriver.elementShouldBeVisible(addNewPartDialog.getServiceFieldDropDown(), true, 3);
    }

    public static void closeServiceDropDown() {
        final VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        final boolean visible = WaitUtilsWebDriver.elementShouldBeVisible(addNewPartDialog.getServiceFieldDropDown(), true, 0);
        if (visible) {
            Utils.clickElement(addNewPartDialog.getServiceFieldArrow());
            WaitUtilsWebDriver.elementShouldBeVisible(addNewPartDialog.getServiceFieldDropDown(), false, 2);
        }
    }

    public static int getServiceDropDownOptionsSize() {
        final VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(addNewPartDialog.getServiceFieldDropDownOptions(), 3);
        return addNewPartDialog.getServiceFieldDropDownOptions().size();
    }

    public static void waitForServiceListOptionsToBeUpdatedInDropDown(int previousNumber) {
        try {
            WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>)(driver) ->
                    new VNextBOAddNewServiceMonitorDialog().getServiceListBoxOptions().size() != previousNumber);
        } catch (Exception ignored) {}
    }

    public static void setServiceName(String serviceName) {
        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clearAndType(addNewPartDialog.getServiceField(), serviceName);
    }
}
