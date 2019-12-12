package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOPartsDetailsPanelSteps {

    public static void clickAddNewPartButton() {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getAddNewPartButton());
    }

    public static int getPartNumberInTheListByServiceName(String serviceName) {

        List<WebElement> servicesNamesList = new VNextBOPartsDetailsPanel().getServiceName();
        int serviceNumber = 0;
        for (WebElement serviceNameLabel : servicesNamesList) {
            if (Utils.getText(serviceNameLabel).equals(serviceName)) serviceNumber = servicesNamesList.indexOf(serviceNameLabel) + 1;
        }
        return serviceNumber;
    }

    public static int getPartsListSize() {

        return new VNextBOPartsDetailsPanel().getPartDetails().size();
    }

    private static void clickActionsButtonForPartByNumberInList(int partNumber) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getActionsButton().get(partNumber));
    }

    private static void clickDuplicateActionButtonForPartByNumberInList(int partNumber) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getDuplicateActionButton().get(partNumber));
    }

    private static void clickDeleteActionButtonForPartByNumberInList(int partNumber) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getDeleteActionButton().get(partNumber));
    }

    private static void clickAddLaborButtonForPartByNumberInList(int partNumber) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getAddLaborButton().get(partNumber));
    }

    private static void clickDeleteLaborButtonForPartByNumberInListAndServiceName(int partNumber, String laborServiceName) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().deleteLaborButton(partNumber, laborServiceName));
    }

    public static void setStatusForPartByPartNumberInList(int partNumber, String status) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getPartStatusField().get(partNumber));
        Utils.selectOptionInDropDown(detailsPanel.getPartStatusDropDown(), detailsPanel.getPartStatusListBoxOptions(), status, true);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void duplicatePartByNumberInList(int partNumber) {

        clickActionsButtonForPartByNumberInList(partNumber);
        clickDuplicateActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void deletePartByNumberInList(int partNumber) {

        clickActionsButtonForPartByNumberInList(partNumber);
        clickDeleteActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void expandLaborBlock(int partNumber) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getLaborsExpander().get(partNumber));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static int getLaborsAmountForPartByNumberInList(int partNumber) {

        return new VNextBOPartsDetailsPanel().laborsListForPartByNumberInList(partNumber).size();
    }

    public static void addLaborForPartByNumberInList(int partNumber, String laborServiceName) {

        clickAddLaborButtonForPartByNumberInList(partNumber);
        VNextBOAddLaborPartsDialogSteps.addLaborServiceToPart(laborServiceName);
    }

    public static void deleteLaborForPartByNumberInListANdLaborServiceName(int partNumber, String laborServiceName) {

        clickDeleteLaborButtonForPartByNumberInListAndServiceName(partNumber, laborServiceName);
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void displayPartsByStatus(String status) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getStatusesCheckbox());
        Utils.selectOptionInDropDownWithJs(detailsPanel.getStatusesCheckboxDropDown(), detailsPanel.statusCheckBoxDropDownItem(status));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickStatusesCheckBox() {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getStatusesCheckbox());
        WaitUtilsWebDriver.waitForLoading();
    }
}
