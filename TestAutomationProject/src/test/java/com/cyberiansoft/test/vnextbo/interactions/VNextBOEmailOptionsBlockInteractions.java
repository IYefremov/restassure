package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOEmailOptionsBlock;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class VNextBOEmailOptionsBlockInteractions {

    private VNextBOEmailOptionsBlock emailOptionsBlock;

    public VNextBOEmailOptionsBlockInteractions() {
        emailOptionsBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOEmailOptionsBlock.class);
    }

    public void setDefaultRecipient(String value) {
        Utils.clearAndType(emailOptionsBlock.getDefaultRecipientInputField(), value);
    }

    public void setCc(String value) {
        Utils.clearAndType(emailOptionsBlock.getCcInputField(), value);
    }

    public void setBcc(String value) {
        Utils.clearAndType(emailOptionsBlock.getBccInputField(), value);
    }

    public void clickInvoicesCheckbox() {
        Utils.clickElement(emailOptionsBlock.getInvoicesCheckbox());
    }

    public void clickIInspectionsCheckbox() {
        Utils.clickElement(emailOptionsBlock.getInspectionsCheckbox());
    }

    public void clickIncludeInspectionCheckbox() {
        Utils.clickElement(emailOptionsBlock.getIncludeInspectionCheckbox());
    }

    public boolean isInvoicesCheckboxClickable() {
        return Utils.isElementClickable(emailOptionsBlock.getInvoicesCheckbox());
    }

    public boolean isInspectionsCheckboxClickable() {
        return Utils.isElementClickable(emailOptionsBlock.getInspectionsCheckbox());
    }

    public boolean isIncludeInspectionCheckboxClickable() {
        return Utils.isElementClickable(emailOptionsBlock.getIncludeInspectionCheckbox());
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
}