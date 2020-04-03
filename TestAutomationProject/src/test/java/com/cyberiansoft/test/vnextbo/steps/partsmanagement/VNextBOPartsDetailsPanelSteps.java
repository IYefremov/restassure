package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.enums.partsmanagement.CoreStatus;
import com.cyberiansoft.test.enums.partsmanagement.PartCondition;
import com.cyberiansoft.test.enums.partsmanagement.PartStatus;
import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOAddNewPartDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsOrdersListPanel;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOAddLaborPartsDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOAddLaborPartsDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOAddNewPartDialogSteps;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOConfirmationDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAddNewPartDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPMNotesDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class VNextBOPartsDetailsPanelSteps {

    public static void addNewPart(VNextBOPartsData data) {
        setAddNewPartValues(data);
        final int partsCounterValueBefore = Integer.parseInt(VNextBOAddNewPartDialogInteractions.getSelectedPartsCounter());
        VNextBOAddNewPartDialogSteps.selectPartsFromPartsList(Arrays.asList(data.getPartItems()));
        VNextBOAddNewPartDialogValidations.verifySelectedPartsCounterValueIsCorrect(String.valueOf(partsCounterValueBefore + data.getPartItems().length));
        VNextBOAddNewPartDialogSteps.submit();
    }

    public static void setAddNewPartValues(VNextBOPartsData data) {
        openAddNewPartDialog();
        VNextBOAddNewPartDialogSteps.setServiceField(data.getService());
        VNextBOAddNewPartDialogValidations.verifyServiceFieldIsCorrect(data.getService());
        VNextBOAddNewPartDialogSteps.setDescription(data.getDescription());
        VNextBOAddNewPartDialogSteps.setCategory(data.getCategory());
        VNextBOAddNewPartDialogSteps.setSubCategory(data.getSubcategory());
    }

    public static void openAddNewPartDialog() {
        WaitUtilsWebDriver.waitABit(1000);
        VNextBOPartsDetailsPanelSteps.clickAddNewPartButton();
        VNextBOAddNewPartDialogValidations.verifyDialogIsDisplayed(true);
    }

    public static void clickAddNewPartButton() {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getAddNewPartButton());
    }

    public static int getPartNumberInTheListByServiceName(String serviceName) {

        List<WebElement> servicesNamesList = new VNextBOPartsDetailsPanel().getPartNames();
        int serviceNumber = 0;
        for (WebElement serviceNameLabel : servicesNamesList) {
            if (Utils.getText(serviceNameLabel).equals(serviceName)) serviceNumber = servicesNamesList.indexOf(serviceNameLabel) + 1;
        }
        return serviceNumber;
    }

    public static int getPartsListSize() {

        return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOPartsDetailsPanel().getPartDetails()).size();
    }

    public static void clickAddLaborButtonForPartByNumberInList(int partNumber) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().getAddLaborButton().get(partNumber));
        WaitUtilsWebDriver.waitForVisibility(new VNextBOAddLaborPartsDialog().getDialogContent(), 5);
    }

    public static void clickDeleteLaborButtonForPartByNumberInListAndServiceName(int partNumber, String laborServiceName) {

        Utils.clickElement(new VNextBOPartsDetailsPanel().deleteLaborButton(partNumber, laborServiceName));
        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getYesButton(), 2);
    }

    public static void setStatusForPartByPartNumberInList(int partNumber, String status) {

        VNextBOPartsDetailsPanelInteractions.setStatusForPartByPartNumber(partNumber, status);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void setStatusForPartByPartName(String partName, String status) {
        final int partNumber = VNextBOPartsDetailsPanelInteractions.getPartNumberByPartName(partName);
        VNextBOPartsDetailsPanelInteractions.setStatusForPartByPartNumber(partNumber, status);
    }

    public static void setAllConditionsForPartByPartNumberInList(int partNumber) {
        Stream.of(PartCondition.values()).forEach(condition -> {
            VNextBOPartsDetailsPanelInteractions.setConditionForPartByPartNumber(partNumber, condition.getValue());
            VNextBOPartsDetailsPanelValidations.verifyPartCondition(0, condition.getValue());
        });
    }

    public static void openPMPageAndSetStatusForPart(String pmWindow, String partName, String status) {
        Utils.openTab(pmWindow);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartName(partName, status);
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

        openDuplicateDialogByNumberInList(partNumber);
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void openDuplicateDialogByNumberInList(int partNumber) {

        VNextBOPartsDetailsPanelInteractions.clickActionsButtonForPartByNumberInList(partNumber);
        VNextBOPartsDetailsPanelInteractions.clickDuplicateActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
    }

    public static void deletePartByNumberInList(int partNumber) {

        VNextBOPartsDetailsPanelInteractions.clickActionsButtonForPartByNumberInList(partNumber);
        VNextBOPartsDetailsPanelInteractions.clickDeleteActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void deletePartByNumberInListAndCancelDeletingWithXIcon(int partNumber) {
        VNextBOPartsDetailsPanelInteractions.clickActionsButtonForPartByNumberInList(partNumber);
        VNextBOPartsDetailsPanelInteractions.clickDeleteActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void deletePartByNumberInListAndCancelDeletingWithNoButton(int partNumber) {
        VNextBOPartsDetailsPanelInteractions.clickActionsButtonForPartByNumberInList(partNumber);
        VNextBOPartsDetailsPanelInteractions.clickDeleteActionButtonForPartByNumberInList(partNumber);
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void openDocumentsDialogByNumberInList(int partNumber) {

        VNextBOPartsDetailsPanelInteractions.clickActionsButtonForPartByNumberInList(partNumber);
        VNextBOPartsDetailsPanelInteractions.clickDocumentsActionButtonForPartByNumberInList(partNumber);
        VNextBOPartDocumentsDialogValidations.verifyPartDocumentsDialogIsOpened(true);
    }

    public static void openNotesDialogByNumberInList(int partNumber) {

        VNextBOPartsDetailsPanelInteractions.clickActionsButtonForPartByNumberInList(partNumber);
        VNextBOPartsDetailsPanelInteractions.clickNotesActionButtonForPartByNumberInList(partNumber);
        VNextBOPMNotesDialogValidations.verifyNotesDialogIsOpened(true);
    }

    public static void expandLaborBlockForPartByNumberInList(int partNumber) {

        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickWithJS(partsDetailsPanel.getLaborsExpander().get(partNumber));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitForAttributeNotToContain(
                partsDetailsPanel.getPartLaborsBlock().get(partNumber), "style", "display: none", 3);
    }

    public static int getLaborsAmountForPartByNumberInList(int partNumber) {

        return new VNextBOPartsDetailsPanel().laborsListForPartByNumberInList(partNumber).size();
    }

    public static void addLaborForPartByNumberInList(int partNumber, String laborServiceName) {

        clickAddLaborButtonForPartByNumberInList(partNumber);
        VNextBOAddLaborPartsDialogSteps.addLaborServiceToPart(laborServiceName);
    }

    public static void deleteLaborForPartByNumberInListAndLaborServiceName(int partNumber, String laborServiceName) {

        clickDeleteLaborButtonForPartByNumberInListAndServiceName(partNumber, laborServiceName);
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void displayPartsByStatus(String status) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getStatusesCheckbox());
        Utils.selectOptionInDropDownWithJs(detailsPanel.getStatusesCheckboxDropDown(), detailsPanel.statusCheckBoxDropDownItem(status));
    }

    public static void updatePartsList(String woNum) {
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitABit(4000); //wait for part to be created
        Utils.refreshPage();
        WaitUtilsWebDriver.waitABit(1000);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
    }

    public static void updatePartsList(String woNum, boolean condition) {
        if (condition) {
            WaitUtilsWebDriver.waitForPageToBeLoaded();
            WaitUtilsWebDriver.waitABit(5000); //wait for parts to be created
            Utils.refreshPage();
            VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        }
    }

    public static void addPartWithPartsListUpdate(VNextBOPartsData data, String woNum) {
        final String status = PartStatus.OPEN.getStatus();
        addNewPart(data);
        updatePartsList(woNum);
        Assert.assertTrue(VNextBOPartsDetailsPanelValidations.isPartStatusPresent(status),
                "The part is not displayed with " + status + " status");
    }

    public static void addPartWithPartsListUpdateIfNotPresent(VNextBOPartsData data, String woNum) {
        if (VNextBOPartsDetailsPanelInteractions.getPartNumbersByPartName(data.getPartItems()[0]).isEmpty()) {
            addNewPart(data);
            updatePartsList(woNum);
        }
    }

    public static void addPartIfNotPresentWithPartsListUpdate(VNextBOPartsData data, String woNum) {
        if (!VNextBOPartsDetailsPanelValidations.isPartDisplayed(data.getPartItems()[0])) {
            addPartWithPartsListUpdate(data, woNum);
        }
    }

    public static void addPartIfOpenStatusIsNotPresent(VNextBOPartsData data, String woNum) {
        final String status = PartStatus.OPEN.getStatus();
        if (!VNextBOPartsDetailsPanelValidations.isPartStatusPresent(status)) {
            addNewPart(data);
            updatePartsList(woNum);
        }

        Assert.assertTrue(VNextBOPartsDetailsPanelValidations.isPartStatusPresent(status),
                "The part is not displayed with " + status + " status");
    }

    public static void addPartIfOpenStatusIsNotPresent(VNextBOPartsData data, String woNum, int expectedNumber) {
        final String status = PartStatus.OPEN.getStatus();
        final int partsNumber = expectedNumber - VNextBOPartsDetailsPanelInteractions.getPartsNumberWithStatus(status);
        for (int i = 0; i < partsNumber; i++) {
            WaitUtilsWebDriver.waitABit(1000);
            addNewPart(data);
        }

        updatePartsList(woNum, partsNumber > 0);
        Assert.assertTrue(VNextBOPartsDetailsPanelInteractions.getPartsNumberWithStatus(status) >= expectedNumber,
                "The parts number is not with " + status + " status is not >= " + expectedNumber);
    }

    public static void deleteServicesByStatus(String status) {
        VNextBOPartsDetailsPanelInteractions.clickStatusesCheckBox();
        final WebElement selectedStatus = VNextBOPartsDetailsPanelInteractions.getSelectedStatus(status);
        if (selectedStatus != null) {
            VNextBOPartsDetailsPanelInteractions.selectStatusToDelete(selectedStatus);
            deleteServices();
        } else {
            closeStatusesCheckbox();
        }
    }

    private static void closeStatusesCheckbox() {
        VNextBOPartsDetailsPanelInteractions.clickStatusesCheckBox();
        VNextBOPartsDetailsPanelInteractions.waitForStatusesCheckBoxToBeOpened(false);
    }

    public static void deleteServicesByName(String ...names) {
        VNextBOPartsDetailsPanelInteractions.selectCheckboxesForServicesByName(names);
        if (VNextBOPartsDetailsPanelValidations.isDeleteSelectedPartsButtonDisplayed(true)) {
            deleteServices();
        }
    }

    public static List<String> getProviderDropDownOptions() {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(partsDetailsPanel.getProviderFieldArrow());
        return Utils.getText(partsDetailsPanel.getPartsListBoxOptions());
    }

    public static String selectProviderInDropDown() {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final String selection = Utils.selectOption(partsDetailsPanel.getPartDropDown(), partsDetailsPanel.getPartsListBoxOptions());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        return selection;
    }

    private static void clickDeleteServices() {
        VNextBOPartsDetailsPanelInteractions.clickDeleteButton();
        VNextBOConfirmationDialogValidations.verifyDialogMessageIsDisplayed(VNextBOAlertMessages.VERIFY_TO_BE_DELETED);
    }

    public static void deleteServices() {
        clickDeleteServices();
        VNextBOConfirmationDialogInteractions.clickYesButton();
    }

    public static void clickDeleteServicesAndCancelWithXIcon() {
        clickDeleteServices();
        VNextBOConfirmationDialogInteractions.closeDialogWithXIcon();
        Assert.assertTrue(VNextBOConfirmationDialogValidations.isConfirmationDialogOpened(false));
    }

    public static void clickDeleteServicesAndCancel() {
        clickDeleteServices();
        VNextBOConfirmationDialogInteractions.clickNoButton();
    }

    public static void clickDuplicatePartsAndCancelWithXIcon(int partNumber) {
        VNextBOPartsDetailsPanelSteps.openDuplicateDialogByNumberInList(partNumber);
        VNextBOConfirmationDialogInteractions.closeDialogWithXIcon();
        Assert.assertTrue(VNextBOConfirmationDialogValidations.isConfirmationDialogOpened(false),
                "The confirmation dialog hasn't been closed");
    }

    public static void clickDuplicatePartsAndCancel(int partNumber) {
        VNextBOPartsDetailsPanelSteps.openDuplicateDialogByNumberInList(partNumber);
        VNextBOConfirmationDialogInteractions.clickNoButton();
        Assert.assertTrue(VNextBOConfirmationDialogValidations.isConfirmationDialogOpened(false),
                "The confirmation dialog hasn't been closed");
    }

    public static void updatePartsListAfterDuplicating(String woNum, int expectedPartsAmount) {
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(expectedPartsAmount);
        VNextBOPartsDetailsPanelValidations.verifyDuplicatePartIsAdded(woNum, expectedPartsAmount);
    }

    public static void updatePartsList(String woNum, int expectedPartsAmount) {
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(expectedPartsAmount);
    }

    public static void openPartsProvidersModalDialog() {
        VNextBOPartsDetailsPanelInteractions.clickGetQuotesPartButton();
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been opened");
    }

    public static void checkNACoreStatusOptionIsNotDisplayed(int partNumber) {
        VNextBOPartsDetailsPanelInteractions.openCoreStatusDropDown(partNumber);
        Assert.assertFalse(VNextBOPartsDetailsPanelValidations.isCoreStatusOptionDisplayed(
                partNumber, CoreStatus.NA.getStatus()), "The core status option is displayed");
    }

    public static int getPartsCountForAllOrdersByStatus(String status) {
        return new VNextBOPartsOrdersListPanel().getListOptions()
                .stream()
                .map((order) -> {
                    VNextBOPartsOrdersListPanelSteps.openPartOrderDetails(order);
                    return VNextBOPartsDetailsPanelInteractions.getPartsNumberWithStatus(status);
                })
                .reduce((prev, next) -> prev + next)
                .orElse(0);
    }

    public static void setNewRandomValuesForThePart(VNextBOPartsManagementData data, int order) {
        final String vendorPrice = VNextBOPartsDetailsPanelInteractions.setVendorPrice(order);
        VNextBOPartsDetailsPanelValidations.verifyPartVendorPrice(order, vendorPrice);

        final String quantity = VNextBOPartsDetailsPanelInteractions.setQuantity(order);
        VNextBOPartsDetailsPanelValidations.verifyQuantity(order, quantity);

        final String corePrice = VNextBOPartsDetailsPanelInteractions.setCorePrice(order);
        VNextBOPartsDetailsPanelValidations.verifyPartCorePrice(order, corePrice);

        final String laborCredit = VNextBOPartsDetailsPanelInteractions.setLaborCredit(order);
        VNextBOPartsDetailsPanelValidations.verifyPartLaborCreditValue(order, laborCredit);

        VNextBOPartsDetailsPanelInteractions.setProvider(data.getProvider());
        VNextBOPartsDetailsPanelValidations.verifyProviderIsSet(order, data.getProvider());

        final String price = VNextBOPartsDetailsPanelInteractions.setPrice(order);
        VNextBOPartsDetailsPanelValidations.verifyPrice(order, price);

        final String eta = CustomDateProvider.getCurrentDateInFullFormat(true);
        VNextBOPartsDetailsPanelInteractions.setCurrentDateIntoTheETAField(order);
        VNextBOPartsDetailsPanelValidations.verifyETA(order, eta);
    }
}