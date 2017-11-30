package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TeamPortalLeftMenuPanel extends BasePage {
    public TeamPortalLeftMenuPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BasePage clickOnMenu(String menuName) throws InterruptedException {
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+menuName+"']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+menuName+"']"))).click();
        switch (menuName){
            case "Client Quotes":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchString")));
                    return PageFactory.initElements(driver,
                            TeamPortalClientQuotesPage.class);
                }catch(TimeoutException ex){}
                break;
            case "Categories":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-toggle btn-add-category']")));
                    return PageFactory.initElements(driver,
                            TeamPortalCategoriesPage.class);
                }catch(TimeoutException ex){}
                break;
            case "Client Segments":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchClient")));
                    return PageFactory.initElements(driver,
                            TeamPortalClientSegmentsPage.class);
                }catch(TimeoutException ex){}
                break;
        }
        return  null;
    }
}
