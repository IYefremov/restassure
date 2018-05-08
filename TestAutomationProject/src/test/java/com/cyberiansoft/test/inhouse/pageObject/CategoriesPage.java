package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class CategoriesPage extends BasePage {

    @FindBy(xpath = "//div[@class='dropdown-toggle btn-add-category']")
    WebElement addCategoryBTN;

    @FindBy(className = "form-control")
    List<WebElement> inputFields;

    @FindBy(xpath = "//button[@class='submit btn-save-add-category']")
    WebElement submitCategoryBTN;

    @FindBy(id ="AttributeName")
    WebElement createAttributeName;

    @FindBy(id="IsAutomated")
    WebElement createAttributeIsAutomated;

    @FindBy(id ="AttributeDataTypeID")
    WebElement createElementAttributeDataType;

    @FindBy(xpath="//button[@class='btn btn-outline btn-submit']")
    WebElement addAttributeInCreationWindow;

    @FindBy(id ="ProcName")
    WebElement storedProcedureNameField;

    @FindBy(xpath="//div[@class='form-dialog add-attribute-dialog active']")
    private WebElement addAttributeDialog;

    @FindBy(id="table-categories")
    private WebElement categoriesTable;

    public CategoriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void clickAddCategoryButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-toggle btn-add-category']")));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addCategoryBTN)).click();
        } catch (WebDriverException e) {
            waitABit(1000);
            try {
                addCategoryBTN.click();
            } catch (Exception ex) {
                Assert.fail("The add category button has not been displayed.", ex);
            }
        }
    }

    public void setCategory(String category) {
        wait.until(ExpectedConditions.visibilityOf(inputFields.get(0))).sendKeys(category);
    }

    public void clickSubmitCategoryButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitCategoryBTN)).click();
        waitForLoading();
    }

    public void deleteCategory(String name) {
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'" + name + "')]")));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver
                    .findElements(By.xpath("//span[contains(text(),'" + name + "')]/following::a[2]")).get(0))).click();
        } catch (WebDriverException e) {
            waitABit(2000);
            driver.findElements(By.xpath("//span[contains(text(),'" + name + "')]/following::a[2]")).get(0).click();
        }


//        driver.findElement(By.xpath("//span[contains(text(),'"+name+"')]")).findElement(By.xpath("..")).findElement(By.xpath(".."))
//                .findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath(".."))
//                .findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-delete']")).click();
        try {
            driver.switchTo().alert().accept();
        } catch(Exception ignored){}
    }

    public void verifyThatCategoriesDoNoExist(String name) {
        List<WebElement> categories = null;
        try {
            wait.until(ExpectedConditions.visibilityOf(categoriesTable));
            categories = driver.findElements(By.xpath("//span[contains(text(),'" + name + "')]"));
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        if ((categories != null) && !categories.isEmpty()) {
            for (int i = 0; i < categories.size(); i++) {
                deleteCategory(name);
            }
        }
    }

    public void clickAddAttributeButton(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'"+name+"')]")));
        driver.findElement(By.xpath("//span[contains(text(),'"+name+"')]")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-add']")).click();
        wait.until(ExpectedConditions.visibilityOf(addAttributeDialog));
    }

    public void fillNotAutomatedAttributeFields(String name, String dataType) {
        wait.until(ExpectedConditions.visibilityOf(createAttributeName)).sendKeys(name);

        createElementAttributeDataType.click();
        createElementAttributeDataType.findElements(By.tagName("option")).stream().
                filter(e -> e.getText().equals(dataType)).findFirst().get().click();

        wait.until(ExpectedConditions.elementToBeClickable(createAttributeIsAutomated)).click();
        createAttributeIsAutomated.findElements(By.tagName("option")).stream().
                filter(e -> e.getText().equals("No")).findFirst().get().click();
    }

    public void fillAutomatedAttributeFields(String name, String procedureName) throws InterruptedException {
        Thread.sleep(2000);
        createAttributeName.sendKeys(name);

        createAttributeIsAutomated.click();
        createAttributeIsAutomated.findElements(By.tagName("option")).stream().
                filter(e -> e.getText().equals("Yes")).findFirst().get().click();
        Thread.sleep(1000);

        storedProcedureNameField.sendKeys(procedureName);
    }

    public void clickAddAttributeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addAttributeInCreationWindow)).click();
    }

    public boolean checkAttributeByName(String categoryName ,String attName) {
        expandAttributesList(categoryName);
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='"+attName+"']")));
            return true;
        }catch(TimeoutException e){
            return false;
        }
    }

    public void expandAttributesList(String categoryName) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'"+categoryName+"')]")));
        driver.findElement(By.xpath("//span[contains(text(),'"+categoryName+"')]")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("//td[@class=' details-control']")).click();
        waitABit(3000);
    }
}
