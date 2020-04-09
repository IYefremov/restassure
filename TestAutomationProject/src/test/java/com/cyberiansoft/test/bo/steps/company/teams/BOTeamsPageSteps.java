package com.cyberiansoft.test.bo.steps.company.teams;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.TeamsWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BOTeamsPageSteps {

    public static List<String> getTeamsList() {
        final TeamsWebPage teamsPage = new TeamsWebPage();
        final WebElement table = teamsPage.getTeamsTable().getWrappedElement();
        WaitUtilsWebDriver.elementShouldBeVisible(table, true, 10);
        final int index = teamsPage.getTeamsTable().getTableColumnIndex("Team");
        return Utils.getText(table.findElements(By.xpath(".//td[" + index + "]")));
    }
}
