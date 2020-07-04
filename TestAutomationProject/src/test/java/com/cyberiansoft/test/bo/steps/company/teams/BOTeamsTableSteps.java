package com.cyberiansoft.test.bo.steps.company.teams;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.teams.BOTeamsDialog;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.teams.BOTeamsTable;
import com.cyberiansoft.test.bo.steps.tables.BOTableSteps;
import org.openqa.selenium.By;

import java.util.List;

public class BOTeamsTableSteps {

    public static List<String> getValuesListByColumnName(String columnName) {
        final BOTeamsTable teamsTable = new BOTeamsTable();
        WaitUtilsWebDriver.waitForVisibility(teamsTable.getTeamsTable());
        final int index = BOTableSteps.getTableColumnIndex(teamsTable, columnName);
        return Utils.getText(teamsTable.getTeamsTable().findElements(By.xpath(".//td[" + index + "]")));
    }

    public static String getValueByColumnName(String columnName) {
        return getValuesListByColumnName(columnName).get(0);
    }

    public static void openEditDialog(String team) {
        Utils.clickElement(new BOTeamsTable().getEditButtonByTeamName(team));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new BOTeamsDialog().getTeamDialog(), 5);
    }
}
