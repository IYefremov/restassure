package com.cyberiansoft.test.bo.steps.company.companyinfo;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.enums.companyinfo.PartProvidersDialog;

import java.util.List;

public class PartProvidersDialogSteps {

    public static List<String> getPartProviderOptions() {
        final PartProvidersDialog partProvidersDialog = new PartProvidersDialog();
        WaitUtilsWebDriver.elementShouldBeVisible(partProvidersDialog.getProviderField(), true, 5);
        Utils.clickElement(partProvidersDialog.getProviderField());
        final List<String> options = Utils.getText(partProvidersDialog.getProviderOptions());
        options.remove("(Select a provider)");
        options.forEach(System.out::println);
        return options;
    }
}
