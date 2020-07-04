package com.cyberiansoft.test.bo.pageobjects.webpages.company.clients;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOClientsPage extends BaseWebPage {

    public BOClientsPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getEditButtonByClientName(String name) {
        return driver.findElement(By.xpath("//td[text()='" + name + "']/../td/input[@title='Edit']"));
    }
}
