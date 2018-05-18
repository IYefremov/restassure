package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class InfoContentDialogWebPage extends BaseWebPage {

    @FindBy(id = "RadToolTipWrapper_ctl00_ctl00_Content_Main_ctl01_ctl01_Card_toolTipTeamInfo")
    private WebElement infoContentDialog;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboEmployeeServices")
    private WebElement employeeServicesCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_btnReassign")
    private WebElement reassignButton;

    public InfoContentDialogWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean verifyInfoContentDialogIsDisplayed() {
        return wait.until(ExpectedConditions.attributeContains(infoContentDialog,
                "style", "visibility: visible;"));
    }

    public InfoContentDialogWebPage chooseEmployeeToReassign(String employee) {
        new Select(employeeServicesCombobox).selectByVisibleText(employee);
        return PageFactory.initElements(
                driver, InfoContentDialogWebPage.class);
    }

    public InfoContentDialogWebPage reassignEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(reassignButton)).click();
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.attributeContains(infoContentDialog,
                "style", "visibility: hidden;"));
        return PageFactory.initElements(
                driver, InfoContentDialogWebPage.class);
    }
}
