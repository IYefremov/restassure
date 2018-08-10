package com.cyberiansoft.test.inhouse.pageObject.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PricingPage extends BasePage {

    @FindBy(xpath = "//a[@class='btn btn-sm btn-edit-mode']")
    private WebElement editModeButton;

    @FindBy(xpath = "//div[@class='box-body edit-mode-enabled']")
    private WebElement editModeEnabled;

    @FindBy(xpath = "//div[@class='box-body']")
    private WebElement editModeDisabled;

    @FindBy(xpath = "//span[@class='btn-add-addition btn-add-edition']")
    private WebElement addEditionButton;

    public PricingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public PricingPage enableEditMode() {
        if (isEditModeDisabled()) {
            clickButton(editModeButton);
        }
        return this;
    }

    public boolean isEditModeEnabled() {
        try {
            waitShortly.until(ExpectedConditions.visibilityOf(editModeEnabled));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEditModeDisabled() {
        try {
            waitShortly.until(ExpectedConditions.visibilityOf(editModeDisabled));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AddEditionDialog clickAddEditionButton() {
        clickButton(addEditionButton);
        waitForLoading();
        return PageFactory.initElements(driver, AddEditionDialog.class);
    }

    public boolean isRecommendedEditionDisplayed(String editionName) {
        By edition = By.xpath("//p[@class='recommended']/following-sibling::h3[text()='" + editionName + "']");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(edition));
            return true;
        } catch (Exception ignored) {
            try {
                wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(driver.findElement(edition))));
                wait.until(ExpectedConditions.visibilityOfElementLocated(edition));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public PricingPage deleteEditionIfDisplayed(String editionName) {
        try {
            WebElement edition = driver.findElement(By.xpath(".//h3[text()='" +
                    editionName + "']/preceding-sibling::div/a[@class='btn-delete-edition']"));
            if (isEditionDisplayed(edition)) {
                deleteEdition(edition);
            }
        } catch (Exception ignored) {}
        return this;
    }

    private boolean isEditionDisplayed(WebElement edition) {
        try {
            return wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(edition))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void deleteEdition(WebElement edition) {
        wait.until(ExpectedConditions.elementToBeClickable(edition)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        waitForLoading();
        waitABit(2000);
    }
}