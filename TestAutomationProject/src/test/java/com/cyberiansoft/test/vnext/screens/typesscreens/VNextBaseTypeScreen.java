package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

@Getter
public class VNextBaseTypeScreen extends VNextBaseScreen {

    @FindBy(xpath = "//*[@data-automation-id='search-icon']")
    private WebElement searchbtn;

    @FindBy(xpath = "//*[@data-autotests-id='search-input']")
    private WebElement searchfld;

    @FindBy(xpath = "//*[@data-autotests-id='search-cancel']")
    private WebElement cancelsearchbtn;

    @FindBy(xpath = "//*[@data-automation-id='search-clear']")
    private WebElement clearsearchicon;

    @FindBy(xpath = "//*[@action='my']")
    private WebElement myviewtab;

    @FindBy(xpath = "//*[@action='team']")
    private WebElement teamviewtab;

    @FindBy(xpath = "//*[@action='add']")
    private WebElement addbtn;

    public VNextBaseTypeScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextBaseTypeScreen() {
    }

    protected WebElement getListCell(WebElement typesList, String cellValue) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='check-item']")));
        List<WebElement> listCells = typesList.findElements(By.xpath(".//*[contains(@class, 'entity-item accordion-item')]"));

        WebElement tableCell = listCells.stream().
                filter(
                        elemnt ->
                                elemnt.findElement(By.xpath(".//*[@action='select']/div[@class='checkbox-item-title']"))
                        .getText().trim().equals(cellValue))
                .findFirst().orElse(null);
        if (tableCell == null)
            Assert.fail("Can't find cell in the list: " + cellValue);
        return tableCell;
    }

    protected void clickAddButton() {
        WaitUtils.elementShouldBeVisible(addbtn, true);
        tap(addbtn);
    }

    protected void switchToTeamView() {
        tap(WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@action='team']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='button active' and @action='team']")));
    }

    protected boolean isTeamViewActive() {
        return teamviewtab.getAttribute("class").contains("active");
    }

    //todo rewrite!!!
    protected void switchToMyView() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(myviewtab));
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Actions actions = new Actions(appiumdriver);
            actions.moveToElement(myviewtab, 30, 0).click().perform();
            BaseUtils.waitABit(3000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'button active') and @action='my']")));
            return true;
        });
    }

    protected boolean isMyViewActive() {
        return myviewtab.getAttribute("class").contains("active");
    }

    public void clickSearchButton() {
        tap(searchbtn);
    }

    public void clickCancelSearchButton() {
        WaitUtils.click(cancelsearchbtn);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspections']"));
    }


    //todo rewrite!!!
    public void clearSearchField() {
        if (cancelsearchbtn.isDisplayed()) {
            tap(clearsearchicon);
            clickCancelSearchButton();
        }
        if (searchbtn.findElement(By.xpath(".//span[contains(@class, 'icon-has-query')]")).isDisplayed()) {
            BaseUtils.waitABit(500);
            tap(searchbtn);
            if (searchfld.getAttribute("value").length() > 1) {
                tap(clearsearchicon);
                WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspectiontypes']"));
            }
            clickCancelSearchButton();
        }
    }

    //todo rewrite!!!
    public void clickSearchButtonAndClear() {
        tap(searchbtn);
        if (searchfld.getAttribute("value").length() > 1) {
            tap(clearsearchicon);
            WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspectiontypes']"));
        }
        clickCancelSearchButton();
    }
}
