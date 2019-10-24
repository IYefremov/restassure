package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonObjects.VNextBOSearchPanelSteps;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOClientsPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickAddNewClientButton()
    {
        Utils.clickElement(new VNextBOClientsWebPage().getAddNewClientButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openActiveTab()
    {
        Utils.clickElement(new VNextBOClientsWebPage().getActiveTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openArchivedTab()
    {
        Utils.clickElement(new VNextBOClientsWebPage().getArchivedTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static int getClientsAmount()
    {
        return new VNextBOClientsWebPage().getClientRecords().size();
    }

    public static List<String> getColumnValuesByTitleFromColumnWithText(String columnTitle)
    {
        return new VNextBOClientsWebPage().columnTextCellsByTitle(columnTitle).stream().
                map(WebElement::getText).collect(Collectors.toList());
    }

    public static List<String> getColumnValuesFromColumnWithCheckBoxes(String columnTitle)
    {
        List<String> checkboxesStates = new ArrayList<>();
        for (WebElement checkBox : new VNextBOClientsWebPage().columnTextCellsWithCheckboxesTitle(columnTitle)
             ) {
            checkboxesStates.add(checkBox.getAttribute("checked"));
        }
        return checkboxesStates;
    }

    public static String getClientsNotFoundMessage()
    {
        return Utils.getText(new VNextBOClientsWebPage().getNoClientsFoundMessage());
    }

    public static void searchUserByEmail(String email)
    {
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setEmailField(email);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByPhone(String phone)
    {
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setPhoneField(phone);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByName(String name)
    {
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setNameField(name);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByAddress(String address)
    {
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setAddressField(address);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchUserByType(String clientType)
    {
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setTypeDropDownField(clientType);
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }
}