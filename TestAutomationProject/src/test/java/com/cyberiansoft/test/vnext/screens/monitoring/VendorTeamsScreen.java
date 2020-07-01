package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.customers.ListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VendorTeamsScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='vendor-teams']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id='vendor-teams']/div")
    private List<ListElement> teamsRecordsList;

    public VendorTeamsScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
