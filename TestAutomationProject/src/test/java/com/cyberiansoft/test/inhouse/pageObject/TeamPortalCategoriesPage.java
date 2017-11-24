package com.cyberiansoft.test.inhouse.pageObject;

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

    public TeamPortalCategoriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void clickAddCategoryButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addCategoryBTN)).click();
    }

    public void setCategory(String category) throws InterruptedException {
        Thread.sleep(1000);
        inputFields.get(0).sendKeys(category);
    }

    public void clickSubmitCategoryButton(){
        submitCategoryBTN.click();
    }
}
