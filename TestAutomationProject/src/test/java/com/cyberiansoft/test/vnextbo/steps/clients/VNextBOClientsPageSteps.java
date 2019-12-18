package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOClientsPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickAddNewClientButton() {

        Utils.clickElement(new VNextBOClientsWebPage().getAddNewClientButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void openActiveTab() {

        Utils.clickElement(new VNextBOClientsWebPage().getActiveTab());
    }

    public static void openArchivedTab() {

        Utils.clickElement(new VNextBOClientsWebPage().getArchivedTab());
    }

    public static int getClientsAmount() {

        return new VNextBOClientsWebPage().getClientRecords().size();
    }

    public static List<String> getColumnValuesByTitleFromColumnWithText(String columnTitle) {

        return new VNextBOClientsWebPage().columnTextCellsByTitle(columnTitle).stream().
                map(WebElement::getText).collect(Collectors.toList());
    }

    public static List<String> getColumnValuesFromColumnWithCheckBoxes(String columnTitle) {

        List<String> checkboxesStates = new ArrayList<>();
        for (WebElement checkBox : new VNextBOClientsWebPage().columnTextCellsWithCheckboxesTitle(columnTitle)
             ) {
            checkboxesStates.add(checkBox.getAttribute("checked"));
        }
        return checkboxesStates;
    }

    public static String getClientsNotFoundMessage() {

        return Utils.getText(new VNextBOClientsWebPage().getNoClientsFoundMessage());
    }

    public static void clickActionsButtonForClient(String client) {

        Utils.clickElement(new VNextBOClientsWebPage().getActionsIconForClient(client));
    }

    public static void clickEditDropMenuButton() {

        Utils.clickElement(new VNextBOClientsWebPage().getEditDropMenuButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void clickArchiveDropMenuButton() {

        Utils.clickElement(new VNextBOClientsWebPage().getArchiveDropMenuButton());
    }

    public static void clickRestoreDropMenuButton() {

        Utils.clickElement(new VNextBOClientsWebPage().getRestoreDropMenuButton());
    }

    public static void openClientsDetailsPage(String client) {

        clickActionsButtonForClient(client);
        clickEditDropMenuButton();
    }

    public static void searchClientByEmail(String email) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setEmailField(email);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchClientByPhone(String phone) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setPhoneField(phone);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchClientByName(String name) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setNameField(name);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchClientByAddress(String address) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setAddressField(address);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchClientByType(String clientType) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setTypeDropDownField(clientType);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void archiveClient(String clientName) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(clientName);
        clickActionsButtonForClient(clientName);
        clickArchiveDropMenuButton();
        VNextBOModalDialogSteps.clickOkButton();
    }

    public static void restoreClient(String clientName) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(clientName);
        clickActionsButtonForClient(clientName);
        clickRestoreDropMenuButton();
        VNextBOModalDialogSteps.clickOkButton();
    }

    public static void createNewClient(VNextBOClientsData clientsData, boolean wholesale){

        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(clientsData, wholesale);
        WaitUtilsWebDriver.waitABit(1000);
        VNextBOClientDetailsViewAccordionSteps.clickOkButton();
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOClientsWebPage().getAddNewClientButton(), true);
    }
}