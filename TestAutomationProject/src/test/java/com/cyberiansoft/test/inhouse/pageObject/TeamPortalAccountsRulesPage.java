package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TeamPortalAccountsRulesPage extends BasePage {

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-add-entity']")
    private WebElement addNewAccountRule;

    @FindBy(xpath = "//input[@placeholder='Enter account name...']")
    private WebElement newRuleAccountField;

    @FindBy(id = "Condition")
    private List<WebElement> newRuleConditionField;

    @FindBy(id = "OrderNo")
    private List<WebElement>  newRuleOrderNoField;

    @FindBy(id = "Description")
    private List<WebElement>  newRuleDescriptionField;

    @FindBy(xpath = "(//button[text()='Add New'])[2]")
    private WebElement submitNewRuleBTN;

    @FindBy(xpath = "//div[contains(@class, 'ms-res-item')]")
    private List<WebElement> accountsList;

    @FindBy(xpath = "//div[@class='ms-trigger']")
    private List<WebElement> newRuleAccountsListExpandArrow;

    @FindBy(xpath = "//div[@class='ms-trigger']")
    private List<WebElement> newRuleOrganisationListExpandArrow;

    @FindBy(xpath = "//div[contains(@class, 'ms-res-item')]")
    private List<WebElement> organisationsList;

    @FindBy(xpath = "//button[text()='Update']")
    private WebElement updateRuleButton;

    public TeamPortalAccountsRulesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickAddNewAccountRuleButton() throws InterruptedException {
    	if (driver.findElements(By.xpath("//*[@class='fa fa-refresh fa-spin']")).size() > 0)
    		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//*[@class='fa fa-refresh fa-spin']"))));   	
    	wait.until(ExpectedConditions.elementToBeClickable(addNewAccountRule)).click();
    }

    public void createNewRule(String organization, String condition, String order, String description, boolean dropdown) throws InterruptedException {
        Thread.sleep(1000);
        if (dropdown) {
            newRuleAccountsListExpandArrow.get(0).click();
            accountsList.stream().filter(e -> e.getText().equals(organization)).findFirst().get().click();
        } else {
            newRuleAccountField.sendKeys(organization);
            newRuleAccountField.sendKeys(Keys.ENTER);
        }
        Thread.sleep(500);
        newRuleConditionField.get(0).sendKeys(condition);
        newRuleOrderNoField.get(0).sendKeys(order);
        newRuleDescriptionField.get(0).sendKeys(description);
        clickSubmitNewRuleButton();
        Thread.sleep(3000);
    }

    public void clickSubmitNewRuleButton() throws InterruptedException {
        Thread.sleep(1500);
        wait.until(ExpectedConditions.elementToBeClickable(submitNewRuleBTN)).click();
        Thread.sleep(2000);
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
