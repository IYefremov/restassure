package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOClientInfoBlock;

public class VNextBOClientInfoBlockInteractions {

    public static void setRetailCompanyType() {
        Utils.clickElement(new VNextBOClientInfoBlock().getRetailRadioButton());
    }

    public static void setWholesaleCompanyType() {
        Utils.clickElement(new VNextBOClientInfoBlock().getWholesaleRadioButton());
    }

    public static void setCompanyName(String companyName) {
        Utils.clearAndType(new VNextBOClientInfoBlock().getCompanyInputField(), companyName);
    }

    public static void setFirstName(String firstName) {
        Utils.clearAndType(new VNextBOClientInfoBlock().getFirstNameInputField(), firstName);
    }

    public static void setLastName(String lastName) {
        Utils.clearAndType(new VNextBOClientInfoBlock().getLastNameInputField(), lastName);
    }

    public static void setEmail(String email) {
        Utils.clearAndType(new VNextBOClientInfoBlock().getEmailInputField(), email);
    }

    public static void setPhone(String phone) {
        Utils.clearAndType(new VNextBOClientInfoBlock().getPhoneInputField(), phone);
    }
}