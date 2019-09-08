package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientsDetailsViewInteractions;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class VNextBOClientAddressFieldsVerifications {

    private VNextBOClientInfoBlock clientInfoBlock;
    private VNextBOEmailOptionsBlock emailOptionsBlock;
    private VNextBOPreferencesBlock preferencesBlock;
    private VNextBOAccountInfoBlock accountInfoBlock;
    private VNextBOAddressBlock addressBlock;

    public VNextBOClientAddressFieldsVerifications() {
        clientInfoBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOClientInfoBlock.class);
        emailOptionsBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOEmailOptionsBlock.class);
        preferencesBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOPreferencesBlock.class);
        accountInfoBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOAccountInfoBlock.class);
        addressBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOAddressBlock.class);
    }

    public void verifyClientInfoBlockIsExpanded() {
        final List<WebElement> infoBlockElements = Arrays.asList(
                clientInfoBlock.getRetailRadioButton(),
                clientInfoBlock.getWholesaleRadioButton(),
                clientInfoBlock.getCompanyInputField(),
                clientInfoBlock.getFirstNameInputField(),
                clientInfoBlock.getLastNameInputField(),
                clientInfoBlock.getEmailInputField(),
                clientInfoBlock.getPhoneInputField());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> infoBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickClientsInfoTab();
        }
    }

    public void verifyEmailOptionsBlockIsExpanded() {
        final List<WebElement> emailOptionBlockElements = Arrays.asList(
                emailOptionsBlock.getDefaultRecipientInputField(),
                emailOptionsBlock.getCcInputField(),
                emailOptionsBlock.getBccInputField(),
                emailOptionsBlock.getInvoicesCheckbox(),
                emailOptionsBlock.getInspectionsCheckbox(),
                emailOptionsBlock.getIncludeInspectionCheckbox());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> emailOptionBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickEmailOptionsTab();
        }
    }

    public void verifyPreferencesBlockIsExpanded() {
        final List<WebElement> preferencesBlockElements = Arrays.asList(
                preferencesBlock.getUseSingleWoTypeCheckbox(),
                preferencesBlock.getDefaultAreaArrow());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> preferencesBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickPreferencesTab();
        }
    }

    public void verifyMiscellaneousBlockIsExpanded() {
        try {
            WaitUtilsWebDriver.waitForVisibility(new VNextBOMiscellaneousBlock().getNotesField(), 4);
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickMiscellaneousTab();
        }
    }

    public void verifyAccountInfoBlockIsExpanded() {
        final List<WebElement> infoBlockElements = Arrays.asList(
                accountInfoBlock.getAccountingId(),
                accountInfoBlock.getAccountingId2(),
                accountInfoBlock.getExportAsArrow(),
                accountInfoBlock.getClassArrow(),
                accountInfoBlock.getQbAccountArrow());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> infoBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickAccountInfoTab();
        }
    }

    public void verifyAddressBlockIsExpanded() {
        final List<WebElement> addressBlockElements = Arrays.asList(
                addressBlock.getAddress1ShipToInputField(),
                addressBlock.getAddress2ShipToInputField(),
                addressBlock.getCityShipToInputField(),
                addressBlock.getCountryShipToInputField(),
                addressBlock.getStateProvinceShipToInputField(),
                addressBlock.getZipShipToInputField());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> addressBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickAddressTab();
        }
    }
}