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
    private WebElement employeeListCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_btnReassign")
    private WebElement reassignButton;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_lbChooseEmployess")
    private WebElement bubbleTopInfoWithReassign;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_lbCannotChangeTeam")
    private WebElement bubbleTopInfo;

    public InfoContentDialogWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean verifyInfoContentDialogIsDisplayed() {
        return wait.until(ExpectedConditions.attributeContains(infoContentDialog,
                "style", "visibility: visible;"));
    }

    public InfoContentDialogWebPage chooseEmployeeToReassign(String employee) {
        new Select(employeeListCombobox).selectByVisibleText(employee);
        return this;
    }

    public boolean isTopBubbleInfoDisplayedWithReassign(String topBubbleInfo) {
        return wait.until(ExpectedConditions.visibilityOf(bubbleTopInfoWithReassign)).getText().equals(topBubbleInfo);
    }

    public boolean isTopBubbleInfoDisplayed(String topBubbleInfo) {
        return wait.until(ExpectedConditions.visibilityOf(bubbleTopInfo)).getText().equals(topBubbleInfo);
    }

    public InfoContentDialogWebPage reassignEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(reassignButton)).click();
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.attributeContains(infoContentDialog,
                "style", "visibility: hidden;"));
        return this;
    }

    public boolean isEmployeeListDisabled() {
        return isDisabled(employeeListCombobox);
    }

    public boolean isReassignButtonDisabled() {
        return isDisabled(reassignButton);
    }

    private boolean isDisabled(WebElement element) {
        try {
            wait.until(ExpectedConditions.attributeContains(element, "disabled", ""));
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
