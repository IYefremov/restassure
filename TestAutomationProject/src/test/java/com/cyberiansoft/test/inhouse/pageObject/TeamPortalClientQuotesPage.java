package com.cyberiansoft.test.inhouse.pageObject;

import com.cyberiansoft.test.inhouse.utils.MailChecker;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TeamPortalClientQuotesPage extends BasePage {

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-add-potential-client']")
    WebElement addClientBTN;

    @FindBy(xpath = "//input[@id='ClientName']")
    List<WebElement> newClientName;

    @FindBy(id = "ClientNickname")
    WebElement newClientNickName;

    @FindBy(id = "ClientAddress")
    WebElement newClientAddress;

    @FindBy(id = "ClientAddress2")
    WebElement newClientAddress2;

    @FindBy(id = "ClientZip")
    WebElement newClientZip;

    @FindBy(id = "ClientCountry")
    WebElement newClientCountry;

    @FindBy(id = "ClientState")
    WebElement newClientState;

    @FindBy(id = "ClientCity")
    WebElement newClientCity;

    @FindBy(id = "ClientPhone")
    WebElement newClientBusinessPhone;

    @FindBy(id = "ContactPhone")
    WebElement newClientCellPhone;

    @FindBy(id = "ContactFirstName")
    WebElement newClientFirstName;

    @FindBy(id = "ContactLastName")
    WebElement newClientLastName;

    @FindBy(id = "ContactTitle")
    WebElement newClientTitle;

    @FindBy(id = "ContactEmail")
    WebElement newClientEmail;

    @FindBy(xpath = "//button[@class='btn btn-outline btn-submit']")
    WebElement confirmNewClient;

    @FindBy(id = "searchString")
    WebElement searchField;

    @FindBy(id = "btnSearch")
    WebElement searchBTN;

    @FindBy(id = "table-potential-client_processing")
    WebElement processingBar;

    @FindBy(xpath = "//button[@class='btn btn-outline btn-submit']")
    List<WebElement> updateClientBTN;

    @FindBy(id = "ProposalName")
    List<WebElement> agreementName;

    @FindBy(id = "EditionID")
    WebElement editionMenu;

    @FindBy(xpath = "//button[@class='btn btn-outline btn-submit']")
    List<WebElement> addClientProposalBTN;

    public TeamPortalClientQuotesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void clickAddClientBTN() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='btn btn-sm blue btn-add-potential-client']")));
        addClientBTN.click();
    }

    public void setNewClientName(String name) {
        newClientName.get(0).sendKeys(name);
    }

    public void clearAndSetNewClientName(String name) throws InterruptedException {
        Thread.sleep(1500);
        newClientName.get(1).clear();
        newClientName.get(1).sendKeys(name);
    }

    public void setNewClientNickName(String name) {
        newClientNickName.sendKeys(name);
    }

    public void setNewClientAddress(String address) {
        newClientAddress.sendKeys(address);
    }

    public void setNewClientAddress2(String address2) {
        newClientAddress2.sendKeys(address2);
    }

    public void setNewClientZip(String zip) {
        newClientZip.sendKeys(zip);
    }

    public void setNewClientCountry(String country) {
        newClientCountry.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[text()='" + country + "']"))).click();
    }

    public void setNewClientState(String state) {
        newClientState.sendKeys(state);
    }

    public void setNewClientCity(String city) {
        newClientCity.sendKeys(city);
    }

    public void setNewClientBusinessPhone(String businessPhone) {
        newClientBusinessPhone.sendKeys(businessPhone);
    }

    public void setNewClientCellPhone(String cellPhone) {
        newClientCellPhone.sendKeys(cellPhone);
    }

    public void setNewClientFirstName(String firstName) {
        newClientFirstName.sendKeys(firstName);
    }

    public void setNewClientLastName(String lastName) {
        newClientLastName.sendKeys(lastName);
    }

    public void setNewClientTitle(String title) {
        newClientTitle.sendKeys(title);
    }

    public void setNewClientEmail(String email) {
        newClientEmail.sendKeys(email);
    }

    public void fillNewClientProfile(String name, String nickname, String address, String address2, String zip,
                                     String country, String state, String city, String businessPhone, String cellPhone, String firstName,
                                     String lastName, String title, String email) {
        setNewClientName(name);
        setNewClientNickName(nickname);
        setNewClientAddress(address);
        setNewClientAddress2(address2);
        setNewClientZip(zip);
        setNewClientCountry(country);
        // setNewClientState(state);
        setNewClientCity(city);
        setNewClientBusinessPhone(businessPhone);
        setNewClientCellPhone(cellPhone);
        setNewClientFirstName(firstName);
        setNewClientLastName(lastName);
        setNewClientTitle(title);
        setNewClientEmail(email);

    }


    public void clickConfirmNewClientBTN() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='90%';");
        Thread.sleep(1000);
        js.executeScript("arguments[0].click();", confirmNewClient);
        js.executeScript("document.body.style.zoom='100%';");
    }


    public boolean verifyUserWasCreated(String verifyParameter) throws InterruptedException {
        searchUser(verifyParameter);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + verifyParameter + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void searchUser(String searchValue) throws InterruptedException {
        Thread.sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchString")));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("searchString")));
        searchField.clear();
        searchField.sendKeys(searchValue);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(searchBTN)).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOf(processingBar));
    }

    public void deleteUser(String deleteParameter) {
//        while(true) {
//            try {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + deleteParameter + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-delete btn-delete-potential-client']")).click();
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
//            }catch (TimeoutException ex){
//                break;
//            }
//        }
    }

    public void editClient(String editParemeter) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + editParemeter + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-update btn-update-potential-client']")).click();
    }

    public void clickUpdateClientBTN() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='90%';");
        Thread.sleep(1000);
        js.executeScript("arguments[0].click();", updateClientBTN.get(1));
        js.executeScript("document.body.style.zoom='100%';");
    }

    public void clickAddAgreementBTN(String agreementIdentefier) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + agreementIdentefier + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-add btn-add-client-proposal']")).click();
    }

    public void setAgreementName(String name) {
        agreementName.get(0).sendKeys(name);
    }

    public void selectEdition(String edition) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        editionMenu.click();
        Thread.sleep(100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[text()='" + edition + "']"))).click();
        js.executeScript("arguments[0].click();", updateClientBTN.get(2));
        Thread.sleep(500);
    }

    public void setAgreement(String s, String team) throws InterruptedException {
        setAgreementName(s);
        selectEdition(team);
        saveAgreement();
    }

    public void saveAgreement() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", updateClientBTN.get(2));
        Thread.sleep(500);
    }

    public void expandAgreementList(String identifier) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + identifier + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//td[@class=' details-control']")).click();
    }

    public void clickEditAgreement(String s) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + s + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-update-client-proposal']")).click();
    }

    public boolean abilityToChangeAgreementEdition(String newName) throws InterruptedException {
        try {
            selectEdition(newName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean abilityToChangeAgreementName(String s) {
        try {
            Thread.sleep(1000);
            agreementName.get(1).clear();
            agreementName.get(1).sendKeys(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkAgreementByName(String s) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + s + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }

    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void updateAgreement() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", updateClientBTN.get(3));
        Thread.sleep(500);
    }

    public BasePage clickSetupAgreementBTN(String agreementIdentifier) throws InterruptedException {
        Thread.sleep(1500);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-potential-client_processing")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + agreementIdentifier + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-setup-client-proposal']")).click();
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='agreement-statuses']")));
            return PageFactory.initElements(driver,
                    TeamPortalClientQuotesDetailPage.class);
        } catch (TimeoutException e) {
            return null;
        }
    }
}
