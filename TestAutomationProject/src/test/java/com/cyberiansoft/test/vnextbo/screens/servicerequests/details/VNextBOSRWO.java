package com.cyberiansoft.test.vnextbo.screens.servicerequests.details;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOSRWO extends VNextBOSRDetailsPage {

    @FindBy(xpath = "//div[@class='InfoBlock-header']")
    private List<WebElement> infoBlockHeadersList;

    public WebElement getWOEditIcon() {
        for (WebElement block : infoBlockHeadersList) {
            if (block.findElement(By.xpath("./div")).getText().equals("WO:")) {
                return block.findElement(By.xpath("./div[@class='InfoBlock-icon']"));
            }
        }
        return null;
    }

    public VNextBOSRWO() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}