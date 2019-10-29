package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOClientInfoBlock;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientInfoBlockInteractions {

    private VNextBOClientInfoBlock clientInfoBlock;

    public VNextBOClientInfoBlockInteractions() {
        clientInfoBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOClientInfoBlock.class);
    }

    public void setRetailCompanyType() {
        Utils.clickElement(clientInfoBlock.getRetailRadioButton());
    }

    public void setWholesaleCompanyType() {
        Utils.clickElement(clientInfoBlock.getWholesaleRadioButton());
    }

    public void setCompanyName(String companyName) {
        Utils.clearAndType(clientInfoBlock.getCompanyInputField(), companyName);
    }

    public void setFirstName(String firstName) {
        Utils.clearAndType(clientInfoBlock.getFirstNameInputField(), firstName);
    }

    public void setLastName(String lastName) {
        Utils.clearAndType(clientInfoBlock.getLastNameInputField(), lastName);
    }

    public void setEmail(String email) {
        Utils.clearAndType(clientInfoBlock.getEmailInputField(), email);
    }

    public void setPhone(String phone) {
        Utils.clearAndType(clientInfoBlock.getPhoneInputField(), phone);
    }
}