package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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


    public RulesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public RulesPage clickAddNewRuleButton() {
        try {
            waitABit(1500);
            wait.until(ExpectedConditions.elementToBeClickable(addNewRuleButton)).click();
            wait.until(ExpectedConditions.visibilityOf(newFormDialog));
        } catch (Exception e) {
            Assert.fail("The \"Add Rule\" button has not been clicked.", e);
        }
        return this;
    }

    public void clickSubmitRuleButton() {
        clickRuleButton(submitRuleButton);
    }

    public void clickCloseRuleButton() {
        clickRuleButton(closeRuleButton);
    }

    public void clickUpdateRuleButton() {
        clickRuleButton(updateRuleButton);
    }

    private void clickRuleButton(WebElement button) {
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        waitForLoading();
    }

    public RulesPage verifyRuleDoesNotExist(String ruleName) {
        try {
            while (wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//td[text()='" + ruleName + "']")))))) {
                deleteRuleByName(ruleName);
            }
        } catch (NoSuchElementException | StaleElementReferenceException ignored) {}
        return this;
    }

    public RulesPage deleteRuleByName(String ruleName) {
        try {
            waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[text()='" + ruleName + "']"))
                    .findElement(By.xpath(".."))
                    .findElement(By.xpath("//i[@class='fa fa-fw fa-remove']")))).click();
            driver.switchTo().alert().accept();
            waitForLoading();
        } catch (WebDriverException ignored) {}
        return this;
    }

    public RulesPage createNewRule(String ruleName, String condition, String order, String description) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(ruleName);
            wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(Keys.ENTER);
            fillRuleFields(condition, order, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clickSubmitRuleButton();
        return this;
    }

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

    public RulesPage editExistingRule(String organization, String condition, String order, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(organization);
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationField)).sendKeys(Keys.ENTER);
        fillRuleFields(condition, order, description);
        clickUpdateRuleButton();
        return this;
    }

    public boolean checkRuleByName(String ruleName) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + ruleName + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public RulesPage selectRuleFromDropDown(String ruleName) {
        wait.until(ExpectedConditions.elementToBeClickable(ruleOrganisationListExpandArrow)).click();
        organisationsList.stream().filter(e -> e.getText().equals(ruleName)).findFirst().ifPresent(WebElement::click);
        return this;
    }

    public RulesPage clickEditRuleByName(String ruleName) {
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//td[text()='" + ruleName + "']"))
                .findElement(By.xpath(".."))
                .findElement(By.xpath("//i[@class='fa fa-fw fa-edit']")))).click();
        wait.until(ExpectedConditions.attributeContains(updateRuleDialog, "style", "display: block;"));
        return this;
    }

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
