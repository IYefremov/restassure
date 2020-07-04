package com.cyberiansoft.test.bo.pageobjects.webpages.company.clients;

import com.cyberiansoft.test.bo.pageobjects.webpages.dialogs.BODialog;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class BOClientsDialog extends BODialog {

    @FindBy(xpath="//td[@class='ModalDialog']//div[contains(text(), 'Client')]")
    private WebElement clientDialog;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl05_ctl01_Card_comboAreas_Input")
    private ComboBox defaultAreaCmb;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl05_ctl01_Card_comboAreas_DropDown")
    private DropDown defaultAreaDd;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl05_ctl01_Card_tbFirstName")
    private WebElement firstName;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl05_ctl01_Card_tbLastName")
    private WebElement lastName;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl05_ctl01_Card_tbEmail")
    private WebElement email;
}
