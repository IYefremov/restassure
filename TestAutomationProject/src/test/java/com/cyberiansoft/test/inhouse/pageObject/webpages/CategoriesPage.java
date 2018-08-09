package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class CategoriesPage extends BasePage {

    @FindBy(xpath = "//div[@class='dropdown-toggle btn-add-category']")
    private WebElement addCategoryButton;

    @FindBy(className = "form-control")
    private List<WebElement> inputFields;

    @FindBy(xpath = "//button[@class='submit btn-save-add-category']")
    private WebElement submitCategoryBTN;

    @FindBy(id ="AttributeName")
    private WebElement createAttributeName;

    @FindBy(xpath="//div[@class='form-dialog add-attribute-dialog active']//select[@id='IsAutomated']")
    private WebElement addAttributeIsAutomatedSelection;

    @FindBy(xpath ="//div[@class='form-dialog add-attribute-dialog active']//select[@id='AttributeDataTypeID']")
    private WebElement createElementAttributeDataType;

    @FindBy(xpath="//div[@class='form-dialog add-attribute-dialog active']//button[@class='btn btn-outline btn-submit']")
    private WebElement addAttributeInCreationWindow;

    @FindBy(xpath ="//div[@class='form-dialog add-attribute-dialog active']//input[@id='ProcName']")
    private WebElement storedProcedureNameField;

    @FindBy(xpath="//div[@class='form-dialog add-attribute-dialog active']")
    private WebElement addAttributeDialog;

    @FindBy(xpath="//div[@class='form-dialog add-attribute-dialog']")
    private WebElement addAttributeDialogDisappearance;

    @FindBy(id="table-categories")
    private WebElement categoriesTable;

    @FindBy(xpath="//td[@class='sorting_1']")
    private WebElement categoriesNames;

    @FindBy(xpath="//td[@class='sorting_1']//span")
    private List<WebElement> categoriesNamesList;

    @FindBy(className="shirma-dialog")
    private WebElement shirmaDialog;

    @FindBy(xpath="//h4[contains(text(), 'New category successfully added')]/../button[@class='close']")
    private WebElement categoryAddedCloseButton;

    @FindBy(xpath="//td[@class=' details-control']")
    private List<WebElement> categoryDetailsControlList;

    public CategoriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public CategoriesPage clickAddCategoryButton() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addCategoryButton));
            clickWithJS(addCategoryButton);
        } catch (Exception e) {
            Assert.fail("The \"Add category\" button has not been displayed.", e);
        }
        return this;
    }

    @Step
    public CategoriesPage setCategory(String category) {
        wait.until(ExpectedConditions.visibilityOf(inputFields.get(0))).sendKeys(category);
        return this;
    }

    @Step
    public CategoriesPage clickSubmitCategoryButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitCategoryBTN)).click();
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(categoryAddedCloseButton)).click();
        return this;
    }

    @Step
    public CategoriesPage deleteCategory(String name) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(categoriesNames
                    .findElements(By.xpath("//span[contains(text(),'" + name + "')]/following::a[2]")).get(0))).click();
        } catch (Exception ignored) {}
        try {
            driver.switchTo().alert().accept();
        } catch(Exception ignored){}
        closeNotification();
        return this;
    }

    @Step
    public CategoriesPage verifyCategoriesDoNotExist(String category) {
        try {
            while (isCategoryDisplayed(category)) {
                deleteCategory(category);
            }
        } catch (Exception ignored) {}
        return this;
    }

    @Step
    public boolean isCategoryDisplayed(String category) {
        try {
            waitABit(1000);
            wait.until(ExpectedConditions
                    .visibilityOf(categoriesNames.findElement(By.xpath("//span[contains(text(),'" + category + "')]"))));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Step
    public CategoriesPage clickAddAttributeButtonForCategory(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//span[contains(text(),'" + name + "')]/following::a[1]"))))
                .click();
        wait.until(ExpectedConditions.visibilityOf(addAttributeDialog));
        return this;
    }

    @Step
    public CategoriesPage fillNotAutomatedAttributeFields(String name, String dataType) {
        wait.until(ExpectedConditions.visibilityOf(createAttributeName)).sendKeys(name);

        createElementAttributeDataType.click();
        createElementAttributeDataType.findElements(By.tagName("option")).stream().
                filter(e -> e.getText().equals(dataType)).findFirst().ifPresent(WebElement::click);

        wait.until(ExpectedConditions.elementToBeClickable(addAttributeIsAutomatedSelection)).click();
        addAttributeIsAutomatedSelection.findElements(By.tagName("option")).stream().
                filter(e -> e.getText().equals("No")).findFirst().ifPresent(WebElement::click);
        return this;
    }

    @Step
    public CategoriesPage fillAutomatedAttributeFields(String name, String isAutomated, String procedureName) {
        wait.until(ExpectedConditions.visibilityOf(addAttributeDialog));
        wait.until(ExpectedConditions.visibilityOf(createAttributeName)).sendKeys(name);
        new Select(addAttributeIsAutomatedSelection).selectByVisibleText(isAutomated);
        wait.until(ExpectedConditions.attributeToBe(storedProcedureNameField, "value required", ""));
        storedProcedureNameField.sendKeys(procedureName);
        return this;
    }

    @Step
    public CategoriesPage clickAddAttributeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addAttributeInCreationWindow)).click();
        waitForLoading();
        closeNotification();
        return this;
    }

    @Step
    public boolean checkAttributeByName(String categoryName, String attName) {
        expandAttributesList(categoryName);
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + attName + "']")));
            return true;
        }catch(TimeoutException e){
            return false;
        }
    }

    @Step
    private void expandAttributesList(String category) {
        try {
            waitABit(500);
            wait.until(ExpectedConditions.visibilityOfAllElements(categoryDetailsControlList));
            for (WebElement control : categoryDetailsControlList) {
                if (control.findElement(By.xpath("./following::span")).getText().contains(category)) {
                    control.click();
                }
            }
        } catch (Exception e) {
            Assert.fail("The attributes list is not opened for category " + category, e);
        }
    }

    @Step
    public CategoriesPage addCategory(String category) {
        clickAddCategoryButton();
        setCategory(category);
        clickSubmitCategoryButton();
        return this;
    }
}
