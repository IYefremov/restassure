package com.cyberiansoft.test.bo.steps.company.teams;

import com.cyberiansoft.test.bo.pageobjects.webpages.TeamsWebPage;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class TeamsSearchSteps {

    public static void selectSearchType(String type) {
        final TeamsWebPage teamsPage = new TeamsWebPage();
        selectComboboxValue(teamsPage.getSearchTypeCbx(), teamsPage.getSearchTypeDd(), type);
    }
}
