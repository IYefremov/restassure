package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddLaborPartsDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    public static void clickAddLaborButtonForPartByNumberInList(int partNumber) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getAddLaborButton().get(partNumber));
        WaitUtilsWebDriver.getWebDriverWait(2).until(ExpectedConditions.visibilityOf(new VNextBOAddLaborPartsDialog().getAddLaborButton()));
    }

    public static void clickDeleteLaborButtonForPartByNumberInListAndServiceName(int partNumber, String laborServiceName) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().deleteLaborButton(partNumber, laborServiceName));
        WaitUtilsWebDriver.getWebDriverWait(2).until(ExpectedConditions.visibilityOf(new VNextBOModalDialog().getYesButton()));
    }

    public static void setStatusForPartByPartNumberInList(int partNumber, String status) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getPartStatusField().get(partNumber));
        Utils.selectOptionInDropDownWithJsScroll(detailsPanel.getPartStatusDropDown(), detailsPanel.getPartStatusListBoxOptions(), status);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void setPriceForPartByPartNumberInList(int partNumber, String price) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getPartPriceField().get(partNumber));
        Utils.clearAndType(detailsPanel.getPartPriceField().get(partNumber), price);
        Utils.clickElement(detailsPanel.getPartQuantityField().get(partNumber));
    }

    public static void setQuantityForPartByPartNumberInList(int partNumber, String quantity) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getPartQuantityField().get(partNumber));
        Utils.clearAndType(detailsPanel.getPartQuantityField().get(partNumber), quantity);
        Utils.clickElement(detailsPanel.getPartPriceField().get(partNumber));
    }

    public static void duplicatePartByNumberInList(int partNumber) {

        clickActionsButtonForPartByNumberInList(partNumber);
        clickDuplicateActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
    }

    public static void deletePartByNumberInList(int partNumber) {

        clickActionsButtonForPartByNumberInList(partNumber);
        clickDeleteActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
    }

    public static void expandLaborBlockForPartByNumberInList(int partNumber) {

        Utils.clickWithJS(new VNextBOPartsDetailsPanel().getLaborsExpander().get(partNumber));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
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
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void displayPartsByStatus(String status) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getStatusesCheckbox());
        Utils.selectOptionInDropDownWithJs(detailsPanel.getStatusesCheckboxDropDown(), detailsPanel.statusCheckBoxDropDownItem(status));
    }

    public static void clickStatusesCheckBox() {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getStatusesCheckbox());
    }
}
