package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class OrganizationsRulesPage extends BasePage {
    @FindBy(xpath = "//button[text()='Add New']")
    private WebElement addNewRuleBTN;

    @FindBy(xpath = "//input[@placeholder='Enter organization name...']")
    private WebElement newRuleOrganizationField;

    @FindBy(id = "Condition")
    private List<WebElement> newRuleConditionField;

    @FindBy(id = "OrderNo")
    private List<WebElement> newRuleOrderNoField;

    @FindBy(id = "Description")
    private List<WebElement> newRuleDescriptionField;

    @FindBy(xpath = "//div[@class='form-dialog active']//button[text()='Add New']")
    private WebElement submitNewRuleBTN;

    @FindBy(xpath = "//div[@class='form-dialog active']//div[@class='ms-trigger']")
    private WebElement newRuleOrganisationListExpandArrow;

    @FindBy(xpath = "//div[contains(@class, 'ms-res-item')]")
    private List<WebElement> organisationsList;

    @FindBy(xpath = "//button[text()='Update']")
    private WebElement updateRuleButton;

    @FindBy(xpath = "//div[@class='form-dialog active']")
    private WebElement newFormDialog;

    @FindBy(xpath = "//div[@class='shirma-dialog' and contains(@style, 'display: block')]")
    private WebElement shirmaDialog;

    public OrganizationsRulesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickAddNewButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addNewRuleBTN)).click();
            wait.until(ExpectedConditions.visibilityOf(newFormDialog));
        } catch (Exception e) {
            Assert.fail("The \"Add Rule\" button has not been clicked.", e);
            }
        }

    public void createNewRule(String ruleName, String condition, String order, String description, boolean dropdown) {
        if (dropdown) {
            wait.until(ExpectedConditions.elementToBeClickable(newRuleOrganisationListExpandArrow)).click();
            organisationsList.stream().filter(e -> e.getText().equals(ruleName)).findFirst().ifPresent(WebElement::click);
        } else {
            wait.until(ExpectedConditions.visibilityOf(newRuleOrganizationField)).sendKeys(ruleName);
            wait.until(ExpectedConditions.visibilityOf(newRuleOrganizationField)).sendKeys(Keys.ENTER);
        }
        wait.until(ExpectedConditions.visibilityOf(newRuleConditionField.get(0))).sendKeys(condition);
        wait.until(ExpectedConditions.visibilityOf(newRuleOrderNoField.get(0))).sendKeys(order);
        wait.until(ExpectedConditions.visibilityOf(newRuleDescriptionField.get(0))).sendKeys(description);
        clickSubmitNewRuleButton();
        waitForOverflowToDisappear();
        waitABit(1200);
    }

    private void clickSubmitNewRuleButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitNewRuleBTN)).click();
    }

    public boolean checkRuleByName(String ruleName) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + ruleName + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void deleteRuleByName(String ruleName) {
        waitABit(1000);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[text()='" + ruleName + "']"))
                .findElement(By.xpath(".."))
                .findElement(By.xpath("//i[@class='fa fa-fw fa-remove']")))).click();
        driver.switchTo().alert().accept();
        waitForLoading();
    }

    public void verifyRuleDoesntExist(String ruleName) {
        try {
            while (wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//td[text()='" + ruleName + "']")))))) {
                deleteRuleByName(ruleName);
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    public void clickEditRuleByName(String ruleName) throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("//td[text()='" + ruleName + "']")).findElement(By.xpath(".."))
                .findElement(By.xpath("//i[@class='fa fa-fw fa-edit']")).click();
    }

    public void editExistingRule(String organization, String condition, String order, String description) throws InterruptedException {
        Thread.sleep(3000);
//        newRuleOrganisationListExpandArrow.get(1).click(); //todo the List<WebElement> locator //div[@class='ms-trigger'] has been changed
        //wait.until(ExpectedConditions.visibilityOf(newRuleOrganisationListExpandArrow)).click();
        organisationsList.stream().filter(e -> e.getText().equals(organization)).findFirst().get().click();
        Thread.sleep(2000);
        newRuleConditionField.get(1).clear();
        newRuleConditionField.get(1).sendKeys(condition);
        newRuleOrderNoField.get(1).clear();
        newRuleOrderNoField.get(1).sendKeys(order);
        newRuleDescriptionField.get(1).clear();
        newRuleDescriptionField.get(1).sendKeys(description);
        updateRuleButton.click();
        Thread.sleep(3000);
    }
}
