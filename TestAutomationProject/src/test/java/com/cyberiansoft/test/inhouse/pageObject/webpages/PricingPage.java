package com.cyberiansoft.test.inhouse.pageObject.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PricingPage extends BasePage {

    @FindBy(xpath = "//a[@class='btn btn-sm btn-edit-mode']")
    private WebElement editModeButton;

    @FindBy(xpath = "//div[@class='box-body edit-mode-enabled']")
    private WebElement editModeEnabled;

    @FindBy(xpath = "//span[@class='btn-add-addition btn-add-edition']")
    private WebElement addEditionButton;

    public PricingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public PricingPage clickEditModeButton() {
        clickButton(editModeButton);
        return this;
    }

    public boolean isEditModeEnabled() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(editModeEnabled));
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

    public PricingPage verifyEditionIsNotDisplayed(String editionName) {
        try {
            List<WebElement> editions = driver.findElements(By.xpath("//h3[text()='" +
                    editionName + "']/preceding-sibling::div/a[@class='btn-delete-edition']"));
            int size = wait.until(ExpectedConditions.visibilityOfAllElements(editions)).size();
            for (int i = 0; i < size; i++) {
                wait.until(ExpectedConditions.elementToBeClickable(editions.get(i))).click();
                driver.switchTo().alert().accept();
                waitForLoading();
                waitABit(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(driver.findElement(edition))));
//                wait.until(ExpectedConditions.visibilityOfElementLocated(edition));
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
        }
        return this;
    }
}
