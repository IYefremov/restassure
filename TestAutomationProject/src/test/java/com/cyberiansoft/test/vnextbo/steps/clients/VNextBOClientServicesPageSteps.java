package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOClientsClientServicesPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOClientServicesPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickClientServicesBackButton() {

        Utils.clickElement(new VNextBOClientsClientServicesPage().getClientServicesBackButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setServicePackage(String packageName) {

        VNextBOClientsClientServicesPage servicesPage = new VNextBOClientsClientServicesPage();
        Utils.clickElement(servicesPage.getServicesPackageDropDownField());
        Utils.selectOptionInDropDown(servicesPage.getServicesPackageDropDownField(),
                servicesPage.getServicesPackageDropDownOptions(), packageName, true);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static int getServicesAmount() {

        return new VNextBOClientsClientServicesPage().getServicesRecords().size();
    }

    public static List<String> getColumnsTitles() {

        return new VNextBOClientsClientServicesPage().getColumnsTitles().stream().
                map(WebElement::getText).collect(Collectors.toList());
    }

    public static List<String> getColumnValuesByTitleFromColumnWithText(String columnTitle) {

        return new VNextBOClientsClientServicesPage().columnTextCellsByTitle(columnTitle).stream().
                map(WebElement::getText).collect(Collectors.toList());
    }

    public static String getServicesNotFoundMessage() {

        return Utils.getText(new VNextBOClientsClientServicesPage().getNoRecordsFoundMessage());
    }

    public static void changeFirstLineRequiredFieldValue(String value) {

        VNextBOClientsClientServicesPage clientServicesPage = new VNextBOClientsClientServicesPage();
        Select selectRequired = new Select(clientServicesPage.getFirstLineRequiredDropDownField());
        selectRequired.selectByVisibleText(value);
    }

    public static void changeFirstLineTechnicianFieldValue(String value) {

        VNextBOClientsClientServicesPage clientServicesPage = new VNextBOClientsClientServicesPage();
        Utils.clickElement(clientServicesPage.getFirstLineTechnicianDropDownField());
        Utils.clickWithJS(clientServicesPage.technicianOptionByName(value));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clearFirstLineEffectiveDate() {

        VNextBOClientsClientServicesPage clientServicesPage = new VNextBOClientsClientServicesPage();
        Utils.clear(clientServicesPage.getFirstLineEffectiveDateField());
        Utils.clickElement(clientServicesPage.getFirstLineRequiredDropDownField());
    }

    public static void changeFirstLineEffectiveDate() {

        VNextBOClientsClientServicesPage clientServicesPage = new VNextBOClientsClientServicesPage();
        Utils.clickElement(clientServicesPage.getFirstLineEffectiveDateCalendarIcon());
        Utils.clickWithJS(clientServicesPage.getCalendarCurrentDayButton());
    }

    public static void changeFirstLineEffectivePrice(String newPrice) {

        VNextBOClientsClientServicesPage clientServicesPage = new VNextBOClientsClientServicesPage();
        Utils.clickElement(clientServicesPage.getFirstLineEffectivePriceField());
        Utils.clearAndType(clientServicesPage.getFirstLineEffectivePriceField(), newPrice);
        Utils.clickElement(clientServicesPage.getFirstLineEffectiveDateCalendarIcon());
    }
}