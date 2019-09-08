package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOEmailOptionsBlock;
import org.openqa.selenium.support.PageFactory;

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
}