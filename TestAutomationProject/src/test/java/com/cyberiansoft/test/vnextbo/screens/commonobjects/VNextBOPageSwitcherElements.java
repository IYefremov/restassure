package com.cyberiansoft.test.vnextbo.screens.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPageSwitcherElements extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@class='view']//tfoot//button[contains(@data-bind, 'nextPage')]")
    private WebElement footerNextPageBtn;

    @FindBy(xpath = "//section[@class='view']//tfoot//button[contains(@data-bind, 'previousPage')]")
    private WebElement footerPreviousPageBtn;

    @FindBy(xpath = "//section[@class='view']//thead//button[contains(@data-bind, 'nextPage')]")
    private WebElement headerNextPageBtn;

    @FindBy(xpath = "//section[@class='view']//thead//button[contains(@data-bind, 'previousPage')]")
    private WebElement headerPreviousPageBtn;

    @FindBy(xpath = "//section[@class='view']//tfoot//button[contains(@data-bind, 'firstPage')]")
    private WebElement footerFirstPageBtn;

    @FindBy(xpath = "//section[@class='view']//tfoot//button[contains(@data-bind, 'lastPage')]")
    private WebElement footerLastPageBtn;

    @FindBy(xpath = "//section[@class='view']//thead//button[contains(@data-bind, 'firstPage')]")
    private WebElement headerFirstPageBtn;

    @FindBy(xpath = "//section[@class='view']//thead//button[contains(@data-bind, 'lastPage')]")
    private WebElement headerLastPageBtn;

    @FindBy(xpath = "//section[@class='view']//thead//span[@class='k-input']")
    private WebElement headerItemsPerPageField;

    @FindBy(xpath = "//section[@class='view']//tfoot//span[@class='k-input']")
    private WebElement footerItemsPerPageField;

    @FindBy(xpath = "//section[@class='view']//thead//button[@class='pager__item active']")
    private WebElement headerActivePageNumber;

    @FindBy(xpath = "//section[@class='view']//tfoot//button[@class='pager__item active']")
    private WebElement footerActivePageNumber;

    @FindBy(xpath = "(//section[@class='view']//button[@class='pager__item active'])[1]")
    private WebElement activePageTopPagingElement;

    @FindBy(xpath = "(//section[@class='view']//button[@class='pager__item active'])[2]")
    private WebElement activePageBottomPagingElement;

    public WebElement specificPageButton(int pageNumber) {
        return driver.findElement(By.xpath("(//button[@class='pager__item' and @data-page-index='" +
                (pageNumber - 1) + "'])[1]"));
    }

    public WebElement itemsPerPageOption(String itemsPerPage) {

        return driver.findElement(By.xpath("//ul[@class='k-list k-reset' and @aria-hidden='false']/li[text()='" +
                itemsPerPage + " items per page']"));
    }

    public VNextBOPageSwitcherElements() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}