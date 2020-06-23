package com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairorders;

import com.cyberiansoft.test.bo.pageobjects.webpages.tables.BOTable;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BOROTable extends BOTable {

    @FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00")
    private WebElement teamsTable;

    @FindBy(xpath = "//table[@id='ctl00_ctl00_Content_Main_gv_ctl00']//a[contains(@href, 'VendorOrderServices')]")
    private List<WebElement> ordersList;

    public BOROTable() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        setTable(teamsTable);
    }
}
