package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.pageobjects.webpages.dialogs.BODialog;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOSubscriptionDialog extends BODialog {

    @FindBy(xpath="//td[@class='ModalDialog']//div[contains(text(), 'Subscribtion')]")
    private WebElement subscriptionDialog;

    @FindBy(id="ctl00_ctl00_Content_Main_ctl01_ctl01_Card_rblMode")
    private WebElement modeBlock;

    public BOSubscriptionDialog() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getFullMode() {
        return modeBlock.findElement(By.xpath(".//label[text()='Full']"));
    }

    public WebElement getNoneMode() {
        return modeBlock.findElement(By.xpath(".//label[text()='None']"));
    }

    public WebElement getViewMode() {
        return modeBlock.findElement(By.xpath(".//label[text()='View']"));
    }
}
