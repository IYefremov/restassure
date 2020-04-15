package com.cyberiansoft.test.bo.steps.company.teams;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.teams.BOTeamsSearchBlock;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOTeamsSearchSteps {

    public static void setTeamLocation(String team) {
        Utils.clearAndType(new BOTeamsSearchBlock().getTeamLocationInputField(), team);
    }

    public static void selectSearchType(String type) {
        final BOTeamsSearchBlock teamsPage = new BOTeamsSearchBlock();
        selectComboboxValue(teamsPage.getTypeCbx(), teamsPage.getTypeDd(), type);
    }
}
