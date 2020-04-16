package com.cyberiansoft.test.bo.pageobjects.webpages.company.teams;

import com.cyberiansoft.test.bo.pageobjects.webpages.BOSearchPanel;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOTeamsSearchBlock extends BOSearchPanel {

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_Input")
    private ComboBox typeCbx;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_DropDown")
    private DropDown typeDd;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbxTeamName")
    private WebElement teamLocationInputField;

    public BOTeamsSearchBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
