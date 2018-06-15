package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
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

    @FindBy(className="shirma-dialog")
    private WebElement shirmaDialog;

    @FindBy(xpath="//h4[contains(text(), 'New category successfully added')]/../button[@class='close']")
    private WebElement categoryAddedCloseButton;

    @FindBy(xpath="//h4[contains(text(), 'New attribute successfully added')]/../button[@class='close']")
    private WebElement attributeAddedCloseButton;

    public CategoriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public CategoriesPage clickAddCategoryButton() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addCategoryButton));
            clickWithJS(addCategoryButton);
        } catch (Exception e) {
            Assert.fail("The \"Add category\" button has not been displayed.", e);
        }
        return this;
    }

    public CategoriesPage setCategory(String category) {
        wait.until(ExpectedConditions.visibilityOf(inputFields.get(0))).sendKeys(category);
        return this;
    }

    public CategoriesPage clickSubmitCategoryButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitCategoryBTN)).click();
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(categoryAddedCloseButton)).click();
        return this;
    }

    public CategoriesPage deleteCategory(String name) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(categoriesNames
                    .findElements(By.xpath("//span[contains(text(),'" + name + "')]/following::a[2]")).get(0))).click();
        } catch (Exception ignored) {}
        try {
            driver.switchTo().alert().accept();
        } catch(Exception ignored){}
        return this;
    }

    public CategoriesPage verifyCategoriesDoNotExist(String category) {
        while (true) {
            try {
                if (wait.until(ExpectedConditions
                        .visibilityOfAllElements(categoriesNames)).get(0).getText().equals(category)) {
                    deleteCategory(category);
                } else {
                    break;
                }
            } catch (Exception ignored) {}
        }
        return this;
    }

    public boolean isCategoryDisplayed(String category) {
        try {
            try {
                wait.until(ExpectedConditions.not(ExpectedConditions
                        .stalenessOf(categoriesNames.findElement(By.xpath("//span[contains(text(),'" + category + "')]")))));
                wait.until(ExpectedConditions
                        .visibilityOf(categoriesNames.findElement(By.xpath("//span[contains(text(),'" + category + "')]"))));
                return true;
            } catch (Exception e) {
                System.out.println("In the first catch");
                refreshPage();
                wait.until(ExpectedConditions
                        .visibilityOf(categoriesNames.findElement(By.xpath("//span[contains(text(),'" + category + "')]"))));
                return true;
            }
        } catch (Exception e) {
            System.out.println("In the second catch");
            e.printStackTrace();
            return false;
        }
    }

    public CategoriesPage clickAddAttributeButtonForCategory(String name) {
//        try {
//            wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(driver
//                    .findElement(By.xpath("//span[contains(text(),'" + name + "')]/following::a[1]")))));
//            wait.until(ExpectedConditions.elementToBeClickable(driver
//                    .findElement(By.xpath("//span[contains(text(),'" + name + "')]/following::a[1]")))).click();
//        } catch (Exception e) {
//            refreshPage();
            wait.until(ExpectedConditions.elementToBeClickable(driver
                    .findElement(By.xpath("//span[contains(text(),'" + name + "')]/following::a[1]"))))
                    .click();
//        }
        wait.until(ExpectedConditions.visibilityOf(addAttributeDialog));
        return this;
    }

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

    public CategoriesPage fillAutomatedAttributeFields(String name, String isAutomated, String procedureName) {
        wait.until(ExpectedConditions.visibilityOf(addAttributeDialog));
        wait.until(ExpectedConditions.visibilityOf(createAttributeName)).sendKeys(name);
        new Select(addAttributeIsAutomatedSelection).selectByVisibleText(isAutomated);
        wait.until(ExpectedConditions.attributeToBe(storedProcedureNameField, "value required", ""));
        storedProcedureNameField.sendKeys(procedureName);
        return this;
    }

    public CategoriesPage clickAddAttributeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addAttributeInCreationWindow)).click();
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(attributeAddedCloseButton)).click();
        return this;
    }

    public boolean checkAttributeByName(String categoryName, String attName) {
        expandAttributesList(categoryName);
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + attName + "']")));
            return true;
        }catch(TimeoutException e){
            return false;
        }
    }

    private void expandAttributesList(String category) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'" + category + "')]")));
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions
                    .stalenessOf(categoriesNames.findElement(By.xpath("//span[contains(text(), '" +
                            category + "')]/preceding::td/i[@class='fa fa-chevron-right']")))));
            wait.until(ExpectedConditions.elementToBeClickable(categoriesNames
                    .findElement(By.xpath("//span[contains(text(), '" + category +
                            "')]/preceding::td/i[@class='fa fa-chevron-right']")))).click();
        } catch (Exception e) {
            refreshPage();
            wait.until(ExpectedConditions.elementToBeClickable(categoriesNames.findElement(By
                    .xpath("//span[contains(text(), '" + category +
                            "')]/preceding::td/i[@class='fa fa-chevron-right']")))).click();
        }
        wait.until(ExpectedConditions.visibilityOfAllElements(categoriesNames
                .findElements(By.xpath("//span[contains(text(),'" + category + "')]/following::th[text()]"))));
        }

    public CategoriesPage addCategory(String category) {
        clickAddCategoryButton();
        setCategory(category);
        clickSubmitCategoryButton();
        return this;
    }
}
