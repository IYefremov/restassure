package com.cyberiansoft.test.vnextbo.screens.clients;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOClientsListView extends VNextBOBaseWebPage {

    @FindBy(id = "clients-list-view")
    private WebElement clientsTable;

    @FindBy(id = "app-progress-spinner")
    private WebElement progressSpinner;

    @FindBy(xpath = "//tbody[@data-template='clients-view-row-template']//div[@class='actions-menu__icon']")
    private List<WebElement> actionsIconsList;

    @FindBy(xpath = "//div[contains(@class, 'menu-drop')]/div[contains(@data-bind, 'onEditClicked')]")
    private WebElement editDropMenuButton;

    @FindBy(xpath = "//div[contains(@class, 'menu-drop')]/div[contains(@data-bind, 'onArchiveClicked')]")
    private WebElement archiveDropMenuButton;

    public VNextBOClientsListView(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getActionsIconForClient(String client) {
        return Utils.getElement(driver.findElement(By.xpath("//td[text()='" + client
                + "']/following-sibling::*//div[@class='actions-menu__icon']")));
    }
}