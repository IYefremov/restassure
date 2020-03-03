package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOAccountInfoBlock;

public class VNextBOAccountInfoBlockInteractions {

    public static void setAccountingId(String accountingId) {
        Utils.clearAndType(new VNextBOAccountInfoBlock().getAccountingId(), accountingId);
    }

    public static void setAccountingId2(String accountingId2) {
        Utils.clearAndType(new VNextBOAccountInfoBlock().getAccountingId2(), accountingId2);
    }

    private static void clickExportAsArrow() {
        Utils.clickElement(new VNextBOAccountInfoBlock().getExportAsArrow());
    }

    public static void setExportAs(String exportAs) {
        final VNextBOAccountInfoBlock accountInfoBlock = new VNextBOAccountInfoBlock();
        Utils.clearAndType(accountInfoBlock.getExportAsInputField(), exportAs);
        WaitUtilsWebDriver.waitABit(3000);
        Utils.selectOptionInDropDownWithJs(accountInfoBlock.getExportAsDropDown(),
                accountInfoBlock.getExportAsListBoxOptionByText(exportAs));
    }

    public static void setClass(String classOption) {

        if (!classOption.equals("")) {
            final VNextBOAccountInfoBlock accountInfoBlock = new VNextBOAccountInfoBlock();
            Utils.clickElement(accountInfoBlock.getClassArrow());
            Utils.selectOptionInDropDown(accountInfoBlock.getClassDropDown(),
                    accountInfoBlock.getClassListBoxOptions(), classOption, true);
        }
    }

    public static void setQbAccount(String qbAccount) {
        final VNextBOAccountInfoBlock accountInfoBlock = new VNextBOAccountInfoBlock();
        Utils.clickElement(accountInfoBlock.getQbAccountArrow());
        Utils.selectOptionInDropDown(accountInfoBlock.getQbAccountDropDown(),
                accountInfoBlock.getQbAccountListBoxOptions(), qbAccount, true);
    }

    public static void clickPoNumberRequiredCheckbox() {
        Utils.clickElement(new VNextBOAccountInfoBlock().getPoNumberRequiredCheckbox());
    }
}