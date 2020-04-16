package com.cyberiansoft.test.bo.pageobjects.webpages.company.teams;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.tables.BOTable;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BOTeamsTable extends BOTable {

    @FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00")
    private WebElement teamsTable;

    public BOTeamsTable() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        setTable(teamsTable);
    }

    public WebElement getTableRowWithTeam(String team) {
        List<WebElement> rows = getTableRows();
        for (WebElement row : rows) {
            if (Utils.getText(row.findElement(By.xpath(".//td[" + getTableColumnIndex("Team") + "]"))).contains(team)) {
                return row;
            }
        }
        return null;
    }

    public WebElement getEditButtonByTeamName(String team) {
        return teamsTable.findElement(By.xpath("//td[text()='" + team + "']/../td/input[@title='Edit']"));
    }
}
