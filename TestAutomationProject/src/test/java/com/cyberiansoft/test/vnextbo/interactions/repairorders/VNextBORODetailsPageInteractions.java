package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VNextBORODetailsPageInteractions {

    public static void setStatus(String status) {
        clickStatusBox();
        selectStatus(status);
    }

    private static void clickStatusBox() {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(new VNextBORODetailsPage().getStatusListBox());
        WaitUtilsWebDriver.waitABit(1000);
    }

    private static void selectStatus(String status) {
        final List<WebElement> statusListBoxOptions = new VNextBORODetailsPage().getStatusDropDownContainer()
                .findElements(By.xpath("//ul[@data-role='staticlist']/li"));
        Utils.selectOptionInDropDown(statusListBoxOptions.get(0), statusListBoxOptions, status);
    }

    public static String getRoStatusValue() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(new VNextBORODetailsPage().getRoStatusElement()).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public static void updateRoStatusValue(String previousStatus) {
        try {
            WaitUtilsWebDriver
                    .getShortWait()
                    .until((ExpectedCondition<Boolean>) driver ->
                            !getRoStatusValue().equals(previousStatus));
        } catch (Exception ignored) {}
    }

    public static String getServiceId(String description) {
        final WebElement serviceElement = new VNextBORODetailsPage().getServiceByName(description);
        if (serviceElement != null) {

            final String id = WaitUtilsWebDriver.getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(serviceElement))
                    .findElement(By.xpath(".//../..")).getAttribute("data-order-service-id");
            System.out.println(id);
            return id;
        }
        return "";
    }

    public static void expandServicesTable() {
        final VNextBORODetailsPage detailsPage = new VNextBORODetailsPage();
        Utils.clickElement(detailsPage.getServicesExpandArrow());
        WaitUtilsWebDriver.waitForInvisibility(detailsPage.getServicesExpandArrow());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void expandServicesTable(String service) {
        final WebElement element = DriverBuilder
                .getInstance()
                .getDriver()
                .findElement(By.xpath("//div[@data-name='" + service + "']//i[@class='switchTable icon-arrow-down5']"));
        if (Utils.isElementDisplayed(element)) {
            Utils.clickElement(element);
            WaitUtilsWebDriver.waitForInvisibilityIgnoringException(element, 5);
            WaitUtilsWebDriver.waitForLoading();
        }
    }

    public static int getNumberOfVendorTechnicianOptionsByName(String name) {
        try {
            return WaitUtilsWebDriver
                    .getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-template='" +
                            "order-service-item-template']//div[@class='clmn_4']//span[@class='k-input' and text()='" +
                            name + "']"))).size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setServiceStatusForService(String serviceId, String status) {
        clickServiceStatusBox(serviceId);
        selectServiceStatus(status);
    }

    public static void setServiceStatusForService(int order, String status) {
        clickServiceStatusBox(order);
        selectServiceStatus(status);
    }

    private static void clickServiceStatusBox(int order) {
        WaitUtilsWebDriver.waitForLoading();
        final WebElement serviceStatus = new VNextBORODetailsPage().getServicesStatusWidgetList().get(order);
        Utils.clickElement(serviceStatus);
    }

    private static void clickServiceStatusBox(String serviceId) {
        WaitUtilsWebDriver.waitForLoading();
        final WebElement service = DriverBuilder.getInstance().getDriver()
                .findElement(By.xpath("//div[@data-order-service-id='" + serviceId
                        + "']//div[contains(@data-bind, 'orderServiceStatusName')]/../span[@title]"));
        Utils.getActions().moveToElement(service).build().perform();
        Utils.clickElement(service);
    }

    private static void selectServiceStatus(String status) {
        final List<WebElement> serviceStatusListBoxOptions = DriverBuilder.getInstance().getDriver()
                .findElements(By.xpath("//div[@aria-hidden='false']//ul[@class='k-list k-reset']/li"));
        Utils.selectOptionInDropDown(serviceStatusListBoxOptions.get(0), serviceStatusListBoxOptions, status, true);
    }

    public static String getServiceStatusValue(String serviceId) {
        final WebElement serviceStatusValue = DriverBuilder.getInstance().getDriver().findElement(By.xpath(
                "//div[@data-order-service-id='" + serviceId + "']//span[contains(@class, 'service-status-dropdown')]//span[@class='k-input']"));
        return Utils.getText(serviceStatusValue);
    }

    public static String getServiceDescription(String serviceId) {
        return WaitUtilsWebDriver
                .getWait()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//div[@class='serviceRow' and @data-order-service-id='"
                                + serviceId + "']/div[@class='clmn_2']/div[@title]")))
                .getText();
    }

    public static String getServiceQuantity(String serviceId) {
        System.out.println(serviceId);
        return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".000");
    }

    public static String getServiceLaborTime(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".00 hr");
    }

    public static String getServicePrice(String serviceId) {
        String result = getTextValue(serviceId, "/div[@class='clmn_3 grid__number']/span");
        return StringUtils.substringBefore(result, ",");
    }

    public static String getServiceVendorPrice(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_3_1']/span", ".00")
                .replace("$", "");
    }

    public static void setServiceVendorPrice(String serviceId, String serviceDescription, String newValue) {
        setTextValue(serviceId, serviceDescription, "/div[@class='clmn_3_1']/input", newValue);
    }

    private static String getTextValue(String serviceId, String xpath, String replacement) {
        final WebElement element = new VNextBORODetailsPage().getElementInServicesTable(serviceId, xpath);
        Utils.setAttributeWithJS(element, "style", "display: block;");
        WaitUtilsWebDriver.waitABit(1000);
        final String text = WaitUtilsWebDriver.waitForVisibility(element).getText().replace(replacement, "");
        Utils.setAttributeWithJS(element, "style", "display: none;");
        return text;
    }

    private static String getTextValue(String serviceId, String xpath) {
        final WebElement element = new VNextBORODetailsPage().getElementInServicesTable(serviceId, xpath);
        Utils.setAttributeWithJS(element, "style", "display: block;");
        final String text = WaitUtilsWebDriver.waitForVisibility(element).getText();
        Utils.setAttributeWithJS(element, "style", "display: none;");
        return text;
    }

    private static void setTextValue(String serviceId, String serviceDescription, String xpath, String newValue) {
        final WebElement element = new VNextBORODetailsPage().getElementInServicesTable(serviceId, xpath);
        Utils.clearAndType(element, newValue);
        clickServiceDescriptionName(serviceDescription);
        WaitUtilsWebDriver.waitABit(1000);
    }

    private static void clickServiceDescriptionName(String serviceDescription) {
        Objects.requireNonNull(WaitUtilsWebDriver
                .waitForElementToBeClickable(new VNextBORODetailsPage().getServiceByName(serviceDescription)))
                .click();
    }

    public static void openActionsDropDownForPhase() {
        handleActionsButton(new VNextBORODetailsPage().getPhaseActionsDropDown());
    }

    public static void openActionsDropDownForPhase(String phase) {
        final WebElement actionsTrigger = DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase
                + "']//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]"));
        try {
            WaitUtilsWebDriver.waitForVisibility(actionsTrigger.findElement(By.xpath("./div[contains(@class, 'drop checkout')]")), 3);
        } catch (Exception ignored) {
            Utils.clickElement(actionsTrigger.findElement(By.xpath("./i[contains(@class, 'checkout-arrow')]")));
            WaitUtilsWebDriver.waitForVisibility(actionsTrigger.findElement(By.xpath("./div[contains(@class, 'drop checkout')]")));
        }
    }

    public static void closeActionsDropDownForPhase() {
        handleActionsButton(new VNextBORODetailsPage().getPhaseActionsDropDownHidden());
    }

    private static void handleActionsButton(WebElement phaseActionsDropDown) {
        try {
            WaitUtilsWebDriver.waitForVisibility(phaseActionsDropDown, 5);
        } catch (Exception e) {
            Utils.clickElement(new VNextBORODetailsPage().getPhaseActionsTrigger());
        }
    }

    private static void setOptionForPhase(WebElement option) {
        Utils.clickElement(option);
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(new VNextBORODetailsPage().getPhaseActionsDropDown(), 5);
    }

    public static void clickReportProblemForPhase(String phase) {
        setOptionForPhase(new VNextBORODetailsPage().getPhaseActionsReportProblemOption(phase));
    }

    public static void clickResolveProblemForPhase(String phase) {
        setOptionForPhase(new VNextBORODetailsPage().getPhaseActionsResolveProblemOption(phase));
    }

    public static void clickCheckInOptionForPhase() {
        setOptionForPhase(new VNextBORODetailsPage().getPhaseActionsCheckInOption());
    }

    public static void clickCheckOutOptionForPhase() {
        setOptionForPhase(new VNextBORODetailsPage().getPhaseActionsCheckOutOption());
    }

    public static void openNotesDialog(String serviceId) {
        new VNextBORODetailsPage().clickActionsIcon(serviceId);
        Utils.clickElement(By
                .xpath("//div[@class='serviceRow' and @data-order-service-id='" + serviceId
                        + "']//div[@class='clmn_7']/div[contains(@class, 'order-service-menu')]//label[text()='Notes']"));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static List<String> getPhaseStatusValues() {
        try {
            return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBORODetailsPage().getPhaseStatus())
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
        } catch (Exception ignored) {
            return null;
        }
    }
}