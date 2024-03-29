package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class VNextErrorDialog extends VNextBaseScreen {

    @FindBy(xpath = "//div[@class='modal modal-error modal-in']")
    private WebElement errorDialog;

    @FindBy(xpath = "//div[@class='modal-text']")
    private WebElement modaldlgmsg;

    public VNextErrorDialog(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public String clickOKButtonAndGetMessage() {
        WaitUtils.elementShouldBeVisible(errorDialog, true);
        String msg = getErrorDialogMessage();
        clickErrorDialogOKButton();
        return msg;
    }

    public String getErrorDialogMessage() {
        WaitUtils.elementShouldBeVisible(modaldlgmsg, true);
        return modaldlgmsg.getText();
    }

    public void clickErrorDialogOKButton() {
        WaitUtils.elementShouldBeVisible(errorDialog.findElement(By.xpath(".//span[text()='OK']")), true);
        tap(errorDialog.findElement(By.xpath(".//span[text()='OK']")));
    }
}
