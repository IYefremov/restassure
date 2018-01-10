package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TeamPortalCategoriesPage extends BasePage {

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

    public TeamPortalCategoriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void clickAddCategoryButton() throws InterruptedException {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-toggle btn-add-category']")));
        wait.until(ExpectedConditions.elementToBeClickable(addCategoryBTN)).click();
    }

    public void setCategory(String category) throws InterruptedException {
        Thread.sleep(1000);
        inputFields.get(0).sendKeys(category);

    }

    public void clickSubmitCategoryButton() throws InterruptedException {
        submitCategoryBTN.click();
        Thread.sleep(2000);
    }

    public void deleteCategory(String name) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'"+name+"')]")));
        driver.findElement(By.xpath("//span[contains(text(),'"+name+"')]")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-delete']")).click();
        try{
            driver.switchTo().alert().accept();
        }catch(Exception e){}
        Thread.sleep(3000);
    }

    public void clickAddAttributeButton(String name) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'"+name+"')]")));
        driver.findElement(By.xpath("//span[contains(text(),'"+name+"')]")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-add']")).click();
        Thread.sleep(3000);
    }

    public void fillNotAutomatedAttributeFields(String name, String dataType) throws InterruptedException {
        Thread.sleep(2000);
        createAttributeName.sendKeys(name);

        createElementAttributeDataType.click();
        createElementAttributeDataType.findElements(By.tagName("option")).stream().
                filter(e -> e.getText().equals(dataType)).findFirst().get().click();
        Thread.sleep(1000);

        createAttributeIsAutomated.click();
        createAttributeIsAutomated.findElements(By.tagName("option")).stream().
                filter(e -> e.getText().equals("No")).findFirst().get().click();
        Thread.sleep(1000);
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

    public void clickAddAttributeButton() throws InterruptedException {
        addAttributeInCreationWindow.click();
        Thread.sleep(3000);

    }

    public boolean checkAttributeByName(String categoryName ,String attName) throws InterruptedException {
        expandAttributesList(categoryName);
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='"+attName+"']")));
            return true;
        }catch(TimeoutException e){
            return false;
        }
    }

    public void expandAttributesList(String categoryName) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'"+categoryName+"')]")));
        driver.findElement(By.xpath("//span[contains(text(),'"+categoryName+"')]")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath(".."))
                .findElement(By.xpath("..")).findElement(By.xpath("//td[@class=' details-control']")).click();
        Thread.sleep(3000);
    }
}
