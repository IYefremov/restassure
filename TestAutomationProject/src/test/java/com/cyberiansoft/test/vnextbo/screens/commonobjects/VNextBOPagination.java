package com.cyberiansoft.test.vnextbo.screens.commonobjects;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextBOPagination extends VNextBOBaseWebPage {

    @FindBy(xpath = "//thead//div[@class='pager']")
    private WebElement headerPagination;

    @FindBy(xpath = "//tfoot//div[@class='pager']")
    private WebElement footerPagination;

    public VNextBOPagination() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    private WebElement getHeaderNextPageButton() {
        return headerPagination.findElement(By.xpath("//button[contains(@data-bind, 'nextPage')]"));
    }

    private WebElement getFooterNextPageButton() {
        return footerPagination.findElement(By.xpath("//button[contains(@data-bind, 'nextPage')]"));
    }

    private WebElement getHeaderLastPageButton() {
        return headerPagination.findElement(By.xpath("//button[contains(@data-bind, 'lastPage')]"));
    }

    private WebElement getFooterLastPageButton() {
        return footerPagination.findElement(By.xpath("//button[contains(@data-bind, 'lastPage')]"));
    }

    private WebElement getHeaderPreviousPageButton() {
        return headerPagination.findElement(By.xpath("//button[contains(@data-bind, 'previousPage')]"));
    }

    private WebElement getFooterPreviousPageButton() {
        return footerPagination.findElement(By.xpath("//button[contains(@data-bind, 'previousPage')]"));
    }

    private WebElement getHeaderFirstPageButton() {
        return headerPagination.findElement(By.xpath("//button[contains(@data-bind, 'lastPage')]"));
    }

    private WebElement getFooterFirstPageButton() {
        return footerPagination.findElement(By.xpath("//button[contains(@data-bind, 'lastPage')]"));
    }
}
