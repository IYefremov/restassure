package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROReportProblemDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROResolveProblemDialog;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VNextBORODetailsPageInteractions {

    private VNextBORODetailsPage detailsPage;

    public VNextBORODetailsPageInteractions() {
        detailsPage = new VNextBORODetailsPage();
    }

    public boolean isRODetailsSectionDisplayed() {
        return Utils.isElementDisplayed(detailsPage.getRoDetailsSection());
    }

    public void setStatus(String status) {
        clickStatusBox();
        selectStatus(status);
    }

    private void clickStatusBox() {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(detailsPage.getStatusListBox());
        WaitUtilsWebDriver.waitABit(1000);
    }

    private void selectStatus(String status) {
        final List<WebElement> statusListBoxOptions = detailsPage.getStatusDropDownContainer()
                .findElements(By.xpath("//ul[@data-role='staticlist']/li"));
        Utils.selectOptionInDropDown(statusListBoxOptions.get(0), statusListBoxOptions, status);
    }

    public String getRoStatusValue() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(detailsPage.getRoStatusElement()).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    @Nullable
    private WebElement getServiceByName(String service) {
        try {
            return WaitUtilsWebDriver.waitForVisibility(By.xpath("//div[text()='" + service + "']"));
        } catch (NoSuchElementException ignored) {
            try {
                final WebElement element = DriverBuilder.getInstance().getDriver()
                        .findElement(By.xpath("//div[contains(text(), '" + service + "')]"));
                WaitUtilsWebDriver.waitForVisibility(element);
                return element.getText().trim().equals(service) ? element : null;
            } catch (NoSuchElementException ignore) {}
            return null;
        }
    }

    public String getServiceId(String description) {
        final WebElement serviceElement = getServiceByName(description);
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

    public void expandServicesTable() {
        Utils.clickElement(detailsPage.getServicesExpandArrow());
        WaitUtilsWebDriver.waitForInvisibility(detailsPage.getServicesExpandArrow());
        WaitUtilsWebDriver.waitForLoading();
    }

    public void expandServicesTable(String service) {
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

    public int getNumberOfVendorTechnicianOptionsByName(String name) {
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

    public void setServiceStatusForService(String serviceId, String status) {
        clickServiceStatusBox(serviceId);
        selectServiceStatus(status);
    }

    public void setServiceStatusForService(int order, String status) {
        clickServiceStatusBox(order);
        selectServiceStatus(status);
    }

    private void clickServiceStatusBox(int order) {
        WaitUtilsWebDriver.waitForLoading();
        final WebElement serviceStatus = detailsPage.getServicesStatusWidgetList().get(order);
        Utils.clickElement(serviceStatus);
    }

    private void clickServiceStatusBox(String serviceId) {
        WaitUtilsWebDriver.waitForLoading();
        final WebElement service = DriverBuilder.getInstance().getDriver()
                .findElement(By.xpath("//div[@data-order-service-id='" + serviceId
                        + "']//div[contains(@data-bind, 'orderServiceStatusName')]/../span[@title]"));
        Utils.getActions().moveToElement(service).build().perform();
        Utils.clickElement(service);
    }

    private void selectServiceStatus(String status) {
        final List<WebElement> serviceStatusListBoxOptions = DriverBuilder.getInstance().getDriver()
                .findElements(By.xpath("//div[@aria-hidden='false']//ul[@class='k-list k-reset']/li"));
        Utils.selectOptionInDropDown(serviceStatusListBoxOptions.get(0), serviceStatusListBoxOptions, status, true);
    }

    public String getServiceStatusValue(String serviceId) {
        final WebElement serviceStatusValue = DriverBuilder.getInstance().getDriver().findElement(By.xpath(
                "//div[@data-order-service-id='" + serviceId + "']//span[contains(@class, 'service-status-dropdown')]//span[@class='k-input']"));
        return Utils.getText(serviceStatusValue);
    }

    public String getServiceDescription(String serviceId) {
        return WaitUtilsWebDriver
                .getWait()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//div[@class='serviceRow' and @data-order-service-id='"
                                + serviceId + "']/div[@class='clmn_2']/div[@title]")))
                .getText();
    }

    public String getServiceQuantity(String serviceId) {
        System.out.println(serviceId);
        return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".000");
    }

    public String getServiceLaborTime(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_2_1 grid__number']/span", ".00 hr");
    }

    public String getServicePrice(String serviceId) {
        String result = getTextValue(serviceId, "/div[@class='clmn_3 grid__number']/span");
        return StringUtils.substringBefore(result, ",");
    }

    public String getServiceVendorPrice(String serviceId) {
        return getTextValue(serviceId, "/div[@class='clmn_3_1']/span", ".00")
                .replace("$", "");
    }

    public void setServiceVendorPrice(String serviceId, String serviceDescription, String newValue) {
        setTextValue(serviceId, serviceDescription, "/div[@class='clmn_3_1']/input", newValue);
    }

    public void setServiceVendor(String serviceId, String serviceDescription, String newValue) {
        setTextValue(serviceId, serviceDescription, "/div[@class='clmn_3_1']/input", newValue);
    }

    private String getTextValue(String serviceId, String xpath, String replacement) {
        final WebElement element = detailsPage.getElementInServicesTable(serviceId, xpath);
        Utils.setAttributeWithJS(element, "style", "display: block;");
        WaitUtilsWebDriver.waitABit(1000);
        final String text = WaitUtilsWebDriver.waitForVisibility(element).getText().replace(replacement, "");
        Utils.setAttributeWithJS(element, "style", "display: none;");
        return text;
    }

    private String getTextValue(String serviceId, String xpath) {
        final WebElement element = detailsPage.getElementInServicesTable(serviceId, xpath);
        Utils.setAttributeWithJS(element, "style", "display: block;");
        final String text = WaitUtilsWebDriver.waitForVisibility(element).getText();
        Utils.setAttributeWithJS(element, "style", "display: none;");
        return text;
    }

    private void setTextValue(String serviceId, String serviceDescription, String xpath, String newValue) {
        final WebElement element = detailsPage.getElementInServicesTable(serviceId, xpath);
        Utils.clearAndType(element, newValue);
        clickServiceDescriptionName(serviceDescription);
        WaitUtilsWebDriver.waitABit(1000);
    }

    private void clickServiceDescriptionName(String serviceDescription) {
        Objects.requireNonNull(WaitUtilsWebDriver
                .waitForElementToBeClickable(getServiceByName(serviceDescription)))
                .click();
    }

    public void openActionsDropDownForPhase() {
        handleActionsButton(detailsPage.getPhaseActionsDropDown());
    }

    public void openActionsDropDownForPhase(String phase) {
        final WebElement actionsTrigger = DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase
                + "']//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]"));
        try {
            WaitUtilsWebDriver.waitForVisibility(actionsTrigger.findElement(By.xpath("./div[contains(@class, 'drop checkout')]")), 3);
        } catch (Exception ignored) {
            Utils.clickElement(actionsTrigger.findElement(By.xpath("./i[contains(@class, 'checkout-arrow')]")));
            WaitUtilsWebDriver.waitForVisibility(actionsTrigger.findElement(By.xpath("./div[contains(@class, 'drop checkout')]")));
        }
    }

    public void closeActionsDropDownForPhase() {
        handleActionsButton(detailsPage.getPhaseActionsDropDownHidden());
    }

    private void handleActionsButton(WebElement phaseActionsDropDown) {
        try {
            WaitUtilsWebDriver.waitForVisibility(phaseActionsDropDown, 5);
        } catch (Exception e) {
            Utils.clickElement(detailsPage.getPhaseActionsTrigger());
        }
    }

    private void setOptionForPhase(WebElement option) {
        Utils.clickElement(option);
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(detailsPage.getPhaseActionsDropDown(), 5);
    }

    public void clickReportProblemForPhase(String phase) {
        setOptionForPhase(detailsPage.getPhaseActionsReportProblemOption(phase));
    }

    public void clickResolveProblemForPhase(String phase) {
        setOptionForPhase(detailsPage.getPhaseActionsResolveProblemOption(phase));
    }

    public void clickCheckInOptionForPhase() {
        setOptionForPhase(detailsPage.getPhaseActionsCheckInOption());
    }

    public void clickCheckOutOptionForPhase() {
        setOptionForPhase(detailsPage.getPhaseActionsCheckOutOption());
    }

    public List<String> getPhaseStatusValues() {
        try {
            return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(detailsPage.getPhaseStatus())
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
        } catch (Exception ignored) {
            return null;
        }
    }
}