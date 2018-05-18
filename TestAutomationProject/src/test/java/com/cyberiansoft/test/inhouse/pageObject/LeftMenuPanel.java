package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LeftMenuPanel extends BasePage {

    /** Main Menu */
    @FindBy(xpath = "//span[text()='Financial Mapping']")
    private WebElement financialMapping;

    @FindBy(xpath = "//span[text()='Clients']")
    private WebElement clients;

    @FindBy(xpath = "//span[text()='Client Management']")
    private WebElement clientManagement;


    /** Submenu */
    @FindBy(xpath = "//ul[@class='treeview-menu menu-open']")
    private WebElement submenuOpened;

    @FindBy(xpath = "//span[text()='Client Quotes']")
    private WebElement clientQuotes;

    @FindBy(xpath = "//span[text()='Active Opportunities']")
    private WebElement activeOpportunities;

    @FindBy(xpath = "//span[text()='Categories']")
    private WebElement categories;

    @FindBy(xpath = "//span[text()='Client Segments']")
    private WebElement clientSegments;

    @FindBy(xpath = "//span[text()='Organizations Rules']")
    private WebElement organizationsRules;

    @FindBy(xpath = "//span[text()='Accounts Rules']")
    private WebElement accountsRules;

    public LeftMenuPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    private void clickMenu(WebElement menu) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menu)).click();
        } catch (Exception e) {
            Assert.fail("The menu has not been displayed!", e);
        }
    }

    private void clickSubMenu(WebElement submenu) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submenu)).click();
        } catch (Exception e) {
            Assert.fail("The submenu has not been clicked!", e);
        }
    }

    /**
     * MenuLinks
     * @return
     */
    public LeftMenuPanel clickClientManagement() {
        clickMenu(clientManagement);
        return this;
    }

    public LeftMenuPanel clickClients() {
        clickMenu(clients);
        return this;
    }

    public LeftMenuPanel clickFinancialMapping() {
        clickMenu(financialMapping);
        return this;
    }

    /**
     * SubmenuLinks
     * @return
     */
    public ClientQuotesPage clickClientQuotesSubmenu() {
        clickSubMenu(clientQuotes);
        waitForLoading();
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public ClientSegmentsPage clickClientSegmentsSubmenu() {
        clickSubMenu(clientSegments);
        waitForLoading();
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    public CategoriesPage clickCategoriesSubmenu() {
        clickSubMenu(categories);
        waitForLoading();
        return PageFactory.initElements(driver, CategoriesPage.class);
    }

    public OrganizationsRulesPage clickOrganizationsRulesSubmenu() {
        clickSubMenu(organizationsRules);
        waitForLoading();
        return PageFactory.initElements(driver, OrganizationsRulesPage.class);
    }

    public AccountsRulesPage clickAccountsRulesSubmenu() {
        clickSubMenu(accountsRules);
        waitForLoading();
        return PageFactory.initElements(driver, AccountsRulesPage.class);
    }



    public BasePage clickOnMenu(String menuName) throws InterruptedException {
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + menuName + "']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='" + menuName + "']"))).click();
        switch (menuName){ //todo delete the method after the necessary clickMethods will be created
            case "Client Quotes":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchString")));
                    return PageFactory.initElements(driver,
                            ClientQuotesPage.class);
                }catch(TimeoutException ex){}
                break;
            case "Categories":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-toggle btn-add-category']")));
                    return PageFactory.initElements(driver,
                            CategoriesPage.class);
                }catch(TimeoutException ex){}
                break;
            case "Client Segments":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchClient")));
                    return PageFactory.initElements(driver,
                            ClientSegmentsPage.class);
                }catch(TimeoutException ex){}
                break;
            case "Organizations Rules":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[text()='Organization Name']")));
                    return PageFactory.initElements(driver,
                            OrganizationsRulesPage.class);
                }catch(TimeoutException ex){}
                break;
            case "Accounts Rules":
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Accounts Rules')]")));
                    return PageFactory.initElements(driver,
                            AccountsRulesPage.class);
                }catch(TimeoutException ex){}
                break;
        }
        return  null;
    }
}
