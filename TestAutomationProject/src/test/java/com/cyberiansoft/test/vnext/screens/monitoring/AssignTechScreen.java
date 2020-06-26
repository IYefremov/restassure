package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class AssignTechScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='part-info']")
    private WebElement rootElement;

    @FindBy(xpath = "//input[@id='VendorTeams']")
    private WebElement vendorTeamField;

    @FindBy(xpath = "//input[@id='Technicians']")
    private WebElement technicianField;

    public AssignTechScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
