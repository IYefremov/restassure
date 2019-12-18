package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsAdvancedSearchForm;

public class VNextBOClientsAdvancedSearchSteps {

    public static void clickSearchButton() {

        Utils.clickElement(new VNextBOClientsAdvancedSearchForm().getSearchButton());
    }

    public static void clickCloseButton() {

        Utils.clickElement(new VNextBOClientsAdvancedSearchForm().getCloseButton());
    }

    public static void setNameField(String name) {

        Utils.clearAndType(new VNextBOClientsAdvancedSearchForm().getNameField(), name);
    }

    public static void setAddressField(String address) {

        Utils.clearAndType(new VNextBOClientsAdvancedSearchForm().getAddressField(), address);
    }

    public static void setEmailField(String email) {

        Utils.clearAndType(new VNextBOClientsAdvancedSearchForm().getEmailField(), email);
    }

    public static void setPhoneField(String phone) {

        Utils.clearAndType(new VNextBOClientsAdvancedSearchForm().getPhoneField(), phone);
    }

    public static void setTypeDropDownField(String typeValue) {

        VNextBOClientsAdvancedSearchForm advancedSearchForm =
                new VNextBOClientsAdvancedSearchForm();
        Utils.clickElement(advancedSearchForm.getTypeDropDownField());
        Utils.clickWithJS(advancedSearchForm.dropDownFieldOption(typeValue));
    }
}