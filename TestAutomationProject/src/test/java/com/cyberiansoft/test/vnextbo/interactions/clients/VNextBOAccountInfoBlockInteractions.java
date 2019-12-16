package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOAccountInfoBlock;
import org.openqa.selenium.support.PageFactory;

public class VNextBOAccountInfoBlockInteractions {

    private VNextBOAccountInfoBlock accountInfoBlock;

    public VNextBOAccountInfoBlockInteractions() {
        accountInfoBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOAccountInfoBlock.class);
    }

    public void setAccountingId(String accountingId) {
        Utils.clearAndType(accountInfoBlock.getAccountingId(), accountingId);
    }

    public void setAccountingId2(String accountingId2) {
        Utils.clearAndType(accountInfoBlock.getAccountingId2(), accountingId2);
    }

    private void clickExportAsArrow() {
        Utils.clickElement(accountInfoBlock.getExportAsArrow());
    }

    public void setExportAs(String exportAs) {
        Utils.clearAndType(accountInfoBlock.getExportAsInputField(), exportAs);
        WaitUtilsWebDriver.waitABit(3000);
        Utils.selectOptionInDropDownWithJs(accountInfoBlock.getExportAsDropDown(),
                accountInfoBlock.getExportAsListBoxOptionByText(exportAs));
    }

    public void setClass(String classOption) {

        if (!classOption.equals("")) {
            Utils.clickElement(accountInfoBlock.getClassArrow());
            Utils.selectOptionInDropDown(accountInfoBlock.getClassDropDown(),
                    accountInfoBlock.getClassListBoxOptions(), classOption, true);
        }
    }

    /*public void setQbAccount(String qbAccount) {
        Utils.clickElement(accountInfoBlock.getQbAccountArrow());
        Utils.selectOptionInDropDownWithJs(accountInfoBlock.getQbAccountDropDown(),
                accountInfoBlock.getQbAccountListBoxOptionByText(qbAccount));
    }*/

    public void setQbAccount(String qbAccount) {
        Utils.clickElement(accountInfoBlock.getQbAccountArrow());
        Utils.selectOptionInDropDown(accountInfoBlock.getQbAccountDropDown(),
                accountInfoBlock.getQbAccountListBoxOptions(), qbAccount, true);
    }

    public void clickPoNumberRequiredCheckbox() {
        Utils.clickElement(accountInfoBlock.getPoNumberRequiredCheckbox());
    }

    public boolean isPoNumberUpfrontRequiredCheckboxClickable() {
        try {
            WaitUtilsWebDriver.waitForElementToBeClickable(accountInfoBlock.getPoNumberUpfrontRequiredCheckbox(), 3);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}