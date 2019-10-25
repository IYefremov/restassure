package com.cyberiansoft.test.vnextbo.screens.commonObjects;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOPageSwitcherElements extends VNextBOBaseWebPage {

    @FindBy(xpath = "//tfoot//button[contains(@data-bind, 'nextPage')]")
    private WebElement footerNextPageBtn;

    @FindBy(xpath = "//tfoot//button[contains(@data-bind, 'previousPage')]")
    private WebElement footerPreviousPageBtn;

    @FindBy(xpath = "//thead//button[contains(@data-bind, 'nextPage')]")
    private WebElement headerNextPageBtn;

    @FindBy(xpath = "//thead//button[contains(@data-bind, 'previousPage')]")
    private WebElement headerPreviousPageBtn;

    @FindBy(xpath = "//tfoot//button[contains(@data-bind, 'firstPage')]")
    private WebElement footerFirstPageBtn;

    @FindBy(xpath = "//tfoot//button[contains(@data-bind, 'lastPage')]")
    private WebElement footerLastPageBtn;

    @FindBy(xpath = "//thead//button[contains(@data-bind, 'firstPage')]")
    private WebElement headerFirstPageBtn;

    @FindBy(xpath = "//thead//button[contains(@data-bind, 'lastPage')]")
    private WebElement headerLastPageBtn;

    @FindBy(xpath = "//thead//span[@class='k-input']")
    private WebElement headerItemsPerPageField;

    @FindBy(xpath = "//tfoot//span[@class='k-input']")
    private WebElement footerItemsPerPageField;

    @FindBy(xpath = "//thead//button[@class='pager__item active']")
    private WebElement headerActivePageNumber;

    @FindBy(xpath = "//tfoot//button[@class='pager__item active']")
    private WebElement footerActivePageNumber;

    @FindBy(xpath = "(//button[@class='pager__item active'])[1]")
    private WebElement activePageTopPagingElement;

    @FindBy(xpath = "(//button[@class='pager__item active'])[2]")
    private WebElement activePageBottomPagingElement;

    public WebElement specificPageButton(int pageNumber) {
        return driver.findElement(By.xpath("(//button[@class='pager__item' and @data-page-index='" +
                (pageNumber - 1) + "'])[1]"));
    }

    public WebElement itemsPerPageOption(String itemsPerPage) {
        return driver.findElement(By.xpath("(//ul[@class='k-list k-reset']/li[text()='" +
                itemsPerPage + " items per page'])[1]"));
    }

    public VNextBOPageSwitcherElements() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}