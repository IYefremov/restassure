package com.cyberiansoft.test.vnextbo.screens.clients;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOClientsWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@id='clients-view']//button[@class='btn btn-add-new-service pull-left']")
    private WebElement addNewClientButton;

    @FindBy(xpath = "//span[text()='Active']")
    private WebElement activeTab;

    @FindBy(xpath = "//span[text()='Archived']")
    private WebElement archivedTab;

    @FindBy(xpath = "//div[@id='clients-list-view']//table")
    private WebElement clientsTable;

    @FindBy(xpath = "//tbody[@data-template='clients-view-row-template']/tr")
    private List<WebElement> clientRecords;

    public VNextBOClientsWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}