package com.cyberiansoft.test.vnextbo.screens.clients;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientsWebPage extends VNextBOBaseWebPage {

    public VNextBOClientsWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}