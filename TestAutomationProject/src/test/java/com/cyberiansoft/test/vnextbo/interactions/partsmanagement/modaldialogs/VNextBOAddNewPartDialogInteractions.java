package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOAddNewPartDialog;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class VNextBOAddNewPartDialogInteractions {

    public static int getPartsListSize() {
        try {
            final VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
            WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(addNewPartDialog.getPartsList(), 2);
            return addNewPartDialog.getPartsList().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getSelectedPartsCounter() {
        return Utils.getText(new VNextBOAddNewPartDialog().getSelectedPartsCounter());
    }

    public static void setPartName(String partName) {
        Utils.clearAndType(new VNextBOAddNewPartDialog().getPartsListFilterField(), partName);
    }

    public static void clickSelectAllPartsButton() {
        Utils.clickElement(new VNextBOAddNewPartDialog().getSelectAllPartsButton());
    }

    public static void clickUnSelectAllPartsButton() {
        Utils.clickElement(new VNextBOAddNewPartDialog().getUnSelectAllPartsButton());
    }

    public static void clickSubmitButton() {
        Utils.clickElement(new VNextBOAddNewPartDialog().getSubmitButton());
    }

    public static void clickCancelButton() {
        Utils.clickElement(new VNextBOAddNewPartDialog().getCancelButton());
    }

    public static List<String> getDisplayedPartsListOptionsNames() {
        return Utils.getText(new VNextBOAddNewPartDialog().getDisplayedPartsListOptions());
    }

    public static int getDisplayedPartsListOptionsSize() {
        return new VNextBOAddNewPartDialog().getDisplayedPartsListOptions().size();
    }

    public static void waitForPartsListToBeUpdated(int prevSize) {
        try {
            WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>) driver ->
                    VNextBOAddNewPartDialogInteractions.getDisplayedPartsListOptionsSize() != prevSize);
        } catch (Exception ignored) {}
    }

    public static List<String> getHighlightedServicesInDropDown(String serviceName) {
        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        return Utils.getText(addNewPartDialog.dropDownFieldHighlightedOptions(serviceName));
    }
}
