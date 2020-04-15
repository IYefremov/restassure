package com.cyberiansoft.test.bo.steps.company.teams;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.teams.BOTeamsDialog;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOTeamsDialogSteps {

    public static void setPartProvider(String provider) {
        final BOTeamsDialog teamsDialog = new BOTeamsDialog();
        selectComboboxValue(teamsDialog.getPartProviderCmb(), teamsDialog.getPartProviderDd(), provider);
    }

        public static String getPartProvider() {
        return Utils.getInputFieldValue(new BOTeamsDialog().getPartProviderCmb().getWrappedElement());
    }

    public static void closeDialog() {
        final BOTeamsDialog teamsDialog = new BOTeamsDialog();
        Utils.clickElement(teamsDialog.getCancelButton());
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(teamsDialog.getTeamDialog(), 2);
    }
}
