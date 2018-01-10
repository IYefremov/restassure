package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class OrganizationsRulesPage extends BasePage {
    @FindBy(xpath = "//button[text()='Add New']")
    WebElement addNewRuleBTN;

    @FindBy(xpath = "//input[@placeholder='Enter organization name...']")
    WebElement newRuleOrganizationField;

    @FindBy(id = "Condition")
    List<WebElement> newRuleConditionField;

    @FindBy(id = "OrderNo")
    List<WebElement>  newRuleOrderNoField;

    @FindBy(id = "Description")
    List<WebElement>  newRuleDescriptionField;

    @FindBy(xpath = "(//button[text()='Add New'])[2]")
    WebElement submitNewRuleBTN;

    @FindBy(xpath = "//div[@class='ms-trigger']")
    List<WebElement> newRuleOrganisationListExpandArrow;

    @FindBy(xpath = "//div[contains(@class, 'ms-res-item')]")
    List<WebElement> organisationsList;

    @FindBy(xpath = "//button[text()='Update']")
    WebElement updateRuleButton;

    public OrganizationsRulesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickAddNewButton() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(addNewRuleBTN)).click();
        Thread.sleep(700);
    }

    public void createNewRule(String organization, String condition, String order, String description, boolean dropdown) throws InterruptedException {
        Thread.sleep(1000);
        if (dropdown) {
            newRuleOrganisationListExpandArrow.get(0).click();
            organisationsList.stream().filter(e -> e.getText().equals(organization)).findFirst().get().click();
        } else {
            newRuleOrganizationField.sendKeys(organization);
            newRuleOrganizationField.sendKeys(Keys.ENTER);
        }
        Thread.sleep(500);
        newRuleConditionField.get(0).sendKeys(condition);
        newRuleOrderNoField.get(0).sendKeys(order);
        newRuleDescriptionField.get(0).sendKeys(description);
        clickSubmitNewRuleButton();
        Thread.sleep(3000);
    }

    public void clickSubmitNewRuleButton() {
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

    public void deleteRuleByName(String ruleName) throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("//td[text()='" + ruleName + "']")).findElement(By.xpath(".."))
                .findElement(By.xpath("//i[@class='fa fa-fw fa-remove']")).click();
        driver.switchTo().alert().accept();
    }

    public void clickEditRuleByName(String ruleName) throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("//td[text()='" + ruleName + "']")).findElement(By.xpath(".."))
                .findElement(By.xpath("//i[@class='fa fa-fw fa-edit']")).click();
    }

    public void editExistingRule(String organization, String condition, String order, String description) throws InterruptedException {
        Thread.sleep(3000);
        newRuleOrganisationListExpandArrow.get(1).click();
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
