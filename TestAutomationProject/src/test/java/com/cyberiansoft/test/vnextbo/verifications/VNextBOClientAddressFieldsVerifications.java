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

    public static void verifyClientInfoBlockIsExpanded() {
        final VNextBOClientInfoBlock clientInfoBlock = new VNextBOClientInfoBlock();
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

    public static void verifyEmailOptionsBlockIsExpanded() {
        final VNextBOEmailOptionsBlock emailOptionsBlock = new VNextBOEmailOptionsBlock();
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

    public static void verifyPreferencesBlockIsExpanded() {
        final VNextBOPreferencesBlock preferencesBlock = new VNextBOPreferencesBlock();
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

    public static void verifyMiscellaneousBlockIsExpanded() {
        try {
            WaitUtilsWebDriver.waitForVisibility(new VNextBOMiscellaneousBlock().getNotesField(), 4);
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickMiscellaneousTab();
        }
    }

    public static void verifyAccountInfoBlockIsExpanded() {
        final VNextBOAccountInfoBlock accountInfoBlock = new VNextBOAccountInfoBlock();
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

    public static void verifyAddressBlockIsExpanded() {
        final VNextBOAddressBlock addressBlock = new VNextBOAddressBlock();
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