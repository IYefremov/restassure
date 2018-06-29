package com.cyberiansoft.test.inhouse.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class RulesPage extends BasePage {

    @FindBy(xpath = "//button[text()='Add New']")
    private WebElement addNewRuleButton;

    @FindBy(xpath = "//div[@class='form-dialog active']//button[text()='Add New']")
    private WebElement submitRuleButton;

    @FindBy(xpath = "//div[@class='form-dialog active']//button[text()='Close']")
    private WebElement closeRuleButton;

    @FindBy(xpath = "//div[@class='form-dialog active']//button[text()='Update']")
    private WebElement updateRuleButton;

    @FindBy(xpath = "//div[@class='form-dialog active']")
    private WebElement newFormDialog;

    @FindBy(xpath = "//div[@class='form-dialog active']//div[contains(@class, 'ms-sel-ctn')]/input")
    private WebElement ruleOrganisationField;

    @FindBy(xpath = "//div[@class='form-dialog active']//input[@id='Condition']")
    private WebElement ruleConditionField;

    @FindBy(xpath = "//div[@class='form-dialog active']//input[@id='OrderNo']")
    private WebElement ruleOrderField;

    @FindBy(xpath = "//div[@class='form-dialog active']//input[@id='Description']")
    private WebElement ruleDescriptionField;

    @FindBy(xpath = "//div[@class='form-dialog active']//input[@id='Condition']")
    private WebElement ruleConditionFieldValidation;

    @FindBy(xpath = "//div[@class='form-dialog active']//input[@id='OrderNo']")
    private WebElement ruleOrderFieldValidation;

    @FindBy(xpath = "//div[@class='form-dialog active']//input[@id='Description']")
    private WebElement ruleDescriptionFieldValidation;

    @FindBy(xpath = "//div[@class='form-dialog active']//div[@class='ms-trigger']")
    private WebElement ruleOrganisationListExpandArrow;

    @FindBy(xpath = "//div[contains(@class, 'ms-res-item')]")
    private List<WebElement> organisationsList;

    @FindBy(xpath = "//div[@class='form-dialog active']//span[@class='ms-close-btn']")
    private WebElement ruleOrganisationFieldCloseButton;

    @FindBy(xpath = "//div[@id='update-entity-dialog']/div[@class='modal modal-primary']")
    private WebElement updateRuleDialog;

    @FindBy(className = "dataTables_empty")
    private WebElement emptyRulesTable;


    public RulesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public RulesPage clickAddNewRuleButton() {
        try {
            waitABit(3000);
            wait.until(ExpectedConditions.elementToBeClickable(addNewRuleButton)).click();
            wait.until(ExpectedConditions.visibilityOf(newFormDialog));
        } catch (Exception e) {
            Assert.fail("The \"Add Rule\" button has not been clicked.", e);
        }
        return this;
    }

    @Step
    public void clickSubmitRuleButton() {
        clickRuleButton(submitRuleButton);
    }

    @Step
    public void clickCloseRuleButton() {
        clickRuleButton(closeRuleButton);
    }

    @Step
    public void clickUpdateRuleButton() {
        clickRuleButton(updateRuleButton);
    }

    @Step
    private void clickRuleButton(WebElement button) {
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        waitForLoading();
    }

    @Step
    public RulesPage verifyRuleDoesNotExist(String ruleName) {
            try {
                if (!isRulesTableEmpty()) {
                    deleteRuleIfExists(ruleName);
                }
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {}
        return this;
    }

    @Step
    private boolean isRulesTableEmpty() {
        try {
            new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(emptyRulesTable));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Step
    private void deleteRuleIfExists(String ruleName) {
        while (wait.until(ExpectedConditions.not(ExpectedConditions
                .stalenessOf(driver.findElement(By.xpath("//td[text()='" + ruleName + "']")))))) {
            deleteRuleByName(ruleName);
        }
    }

    @Step
    public RulesPage deleteRuleByName(String ruleName) {
        try {
            waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[text()='" + ruleName + "']"))
                    .findElement(By.xpath(".."))
                    .findElement(By.xpath("//i[@class='fa fa-fw fa-remove']")))).click();
            driver.switchTo().alert().accept();
            waitForLoading();
        } catch (WebDriverException ignored) {}
        closeNotification();
        return this;
    }

    @Step
    public RulesPage createNewRule(String ruleName, String condition, String order, String description) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(ruleName);
            wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(Keys.ENTER);
            fillRuleFields(condition, order, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clickSubmitRuleButton();
        closeNotification();
        return this;
    }

    @Step
    public RulesPage createNewRuleWithDropDown(String ruleName, String condition, String order, String description) {
        try {
            selectRuleFromDropDown(ruleName);
            fillRuleFields(condition, order, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clickSubmitRuleButton();
        return this;
    }

    @Step
    public void fillRuleFields(String condition, String order, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(ruleConditionField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(ruleConditionField)).sendKeys(condition);
        wait.until(ExpectedConditions.visibilityOf(ruleConditionFieldValidation));
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrderField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrderField)).sendKeys(order);
        wait.until(ExpectedConditions.visibilityOf(ruleOrderFieldValidation));
        wait.until(ExpectedConditions.elementToBeClickable(ruleDescriptionField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(ruleDescriptionField)).sendKeys(description);
        wait.until(ExpectedConditions.visibilityOf(ruleDescriptionFieldValidation));
    }

    @Step
    public RulesPage editExistingRule(String organization, String condition, String order, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(organization);
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(Keys.ENTER);
        fillRuleFields(condition, order, description);
        clickUpdateRuleButton();
        return this;
    }

    @Step
    public boolean checkRuleByName(String ruleName) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + ruleName + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step
    public RulesPage selectRuleFromDropDown(String ruleName) {
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationListExpandArrow)).click();
        organisationsList.stream().filter(e -> e.getText().equals(ruleName)).findFirst().ifPresent(WebElement::click);
        return this;
    }

    @Step
    public RulesPage clickEditRuleByName(String ruleName) {
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//td[text()='" + ruleName + "']"))
                .findElement(By.xpath(".."))
                .findElement(By.xpath("//i[@class='fa fa-fw fa-edit']")))).click();
        wait.until(ExpectedConditions.attributeContains(updateRuleDialog, "style", "display: block;"));
        return this;
    }

    @Step
    public RulesPage deleteRuleOrganisation() {
        try {
            wait.until(ExpectedConditions.visibilityOf(ruleOrganisationFieldCloseButton));
            wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationFieldCloseButton)).click();
            waitABit(500);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return this;
    }
}