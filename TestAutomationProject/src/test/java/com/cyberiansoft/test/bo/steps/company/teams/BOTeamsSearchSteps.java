package com.cyberiansoft.test.bo.steps.company.teams;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.teams.BOTeamsSearchBlock;
import com.cyberiansoft.test.bo.steps.PaginationSteps;
import com.cyberiansoft.test.bo.steps.menu.BOMenuSteps;
import com.cyberiansoft.test.bo.steps.search.BOSearchSteps;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOTeamsSearchSteps {

    public static void setTeamLocation(String team) {
        Utils.clearAndType(new BOTeamsSearchBlock().getTeamLocationInputField(), team);
    }

    public static void clearTeamLocationField() {
        Utils.clear(new BOTeamsSearchBlock().getTeamLocationInputField());
    }

    public static void selectSearchType(String type) {
        final BOTeamsSearchBlock teamsPage = new BOTeamsSearchBlock();
        selectComboboxValue(teamsPage.getTypeCbx(), teamsPage.getTypeDd(), type);
    }

    public static void searchTeamsByTypeWithMaxPageSize(String searchType) {
        BOMenuSteps.open(Menu.COMPANY, SubMenu.TEAMS);
        BOSearchSteps.expandSearchTab();
        clearTeamLocationField();
        selectSearchType(searchType);
        PaginationSteps.setMaxPageSize();
        BOSearchSteps.search();
    }
}
