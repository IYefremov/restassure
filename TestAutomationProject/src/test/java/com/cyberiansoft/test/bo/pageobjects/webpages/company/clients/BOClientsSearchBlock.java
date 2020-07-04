package com.cyberiansoft.test.bo.pageobjects.webpages.company.clients;

import com.cyberiansoft.test.bo.pageobjects.webpages.BOSearchPanel;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOClientsSearchBlock extends BOSearchPanel {

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_tbSearch")
    private WebElement clientInputField;

    public BOClientsSearchBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
