package com.cyberiansoft.test.bo.pageobjects.webpages.company.teams;

import com.cyberiansoft.test.bo.pageobjects.webpages.dialogs.BODialog;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class BOTeamsDialog extends BODialog {

    @FindBy(xpath="//td[@class='ModalDialog']//div[contains(text(), 'Team')]")
    private WebElement teamDialog;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboProviders_Input")
    private ComboBox partProviderCmb;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboProviders_DropDown")
    private DropDown partProviderDd;
}
