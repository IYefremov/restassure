package com.cyberiansoft.test.inhouse.pageObject;

import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.inhouse.utils.MailChecker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamPortalClientQuotesDetailPage extends BasePage {

    private String userName;
    private String userPassword;

    @FindBy(className = "agreement-statuses")
    WebElement agreementStatuses;

    @FindBy(className = "btn-select-edition-discount")
    WebElement discountBTN;

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-finalize-agreement']")
    List<WebElement> finalizeAgreementBTN;

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-send-notification']")
    List<WebElement> sendNotificationBTN;

    @FindBy(className = "form-control")
    WebElement discountList;

    @FindBy(xpath = "//button[@class='submit btn-save-select-edition-discount']")
    WebElement submitDiscountBTN;

    @FindBy(xpath = "//tbody[@data-tbody-feature-group-id]")
    List<WebElement> clientSupportTables;

    @FindBy(xpath = "//a[@class='btn-add-ischecked-addon-edition-feature addon-checked-value']")
    WebElement yesAddItemToAgreement;

    public TeamPortalClientQuotesDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        userName = BOConfigInfo.getInstance().getUserName();
        userPassword = BOConfigInfo.getInstance().getUserPassword();
    }

    public boolean checkAgreementStatuses(String aNew, String no, String no1, String no2) throws InterruptedException {
        Thread.sleep(5000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("agreement-statuses")));
        Thread.sleep(5000);
        if (!agreementStatuses.findElement(By.tagName("b")).getText().equals(aNew)) {
            return false;
        }
        if (!agreementStatuses.findElements(By.tagName("span")).get(2).getText().equals(no)) {
            return false;
        }
        if (!agreementStatuses.findElements(By.tagName("span")).get(4).getText().equals(no1)) {
            return false;
        }

        if (!agreementStatuses.findElements(By.tagName("span")).get(6).getText().equals(no2)) {
            return false;
        }
        return true;
    }

    public void clickDiscountBTN() throws InterruptedException {
        discountBTN.click();
        Thread.sleep(2000);
    }

    public void clickFinalizeAgreementBTN() throws InterruptedException {
        Thread.sleep(1000);
        finalizeAgreementBTN.get(0).click();
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
    }

    public void clickSendNotificationButton() throws InterruptedException {
        Thread.sleep(4000);
        sendNotificationBTN.get(0).click();
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
        Thread.sleep(1000 * 60 * 6);
    }

    public boolean checkEmails(String title) throws InterruptedException {
        boolean flag1 = false;
        Thread.sleep(30000);
        for (int i = 0; i < 5; i++) {
            try {
                if (!MailChecker.searchEmailAndGetMailMessageFromSpam(userName, userPassword, title,
                        "noreply@repair360.net").isEmpty()) {
                    flag1 = true;
                    break;
                }
            } catch (NullPointerException e) {
            }
            Thread.sleep(40000);
        }
        return flag1;
    }

    public ArrayList<String> getLinks() throws IOException {
        String mailContent = MailChecker.getUserMailContentFromSpam();
        Pattern linkPattern = Pattern.compile("(<a[^>]+>.+?<\\/a>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(mailContent);
        ArrayList<String> links = new ArrayList<String>();
        while (pageMatcher.find()) {
            links.add(pageMatcher.group());
        }
        return links;
    }

    public String getAgreementApproveLink() throws IOException {
        String mailContent = MailChecker.getUserMailContentFromSpam();
        Document doc = Jsoup.parse(mailContent);
        String text = doc.body().text();
        System.out.println(text);
        String[] allTexts = text.split(" ");
        String result = "";
        for (String str : allTexts) {
            if (str.contains("https://goo.gl")) {
                result = str;
            }
        }
        return result;
    }

    public String getMailContentFromSpam() throws IOException {
        return MailChecker.getUserMailContentFromSpam();
    }

    public void selectDiscount(String discount) throws InterruptedException {
//        discountList.click();
        driver.switchTo().defaultContent();
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(discountList));
        discountList.click();
        new Actions(driver).moveToElement(discountList).click(discountList).click().build().perform();
        Thread.sleep(500);
        driver.findElements(By.tagName("option")).stream().filter(e -> e.getText().equals(discount)).findFirst().get().click();
        submitDiscountBTN.click();
        Thread.sleep(1000);
    }

    public boolean checkNewPrice(String price) throws InterruptedException {
        Thread.sleep(1500);
        System.out.println(driver.findElement(By.xpath("//table[@class='text-center table-price']")).findElements(By.tagName("td")).get(1).findElement(By.tagName("p")).getText());
        return driver.findElement(By.xpath("//table[@class='text-center table-price']")).findElements(By.tagName("td")).get(1).findElement(By.tagName("p")).getText().equals(price);
    }

    public boolean checkSetupFee(String fee) throws InterruptedException {
        Thread.sleep(1500);
        System.out.println(driver.findElement(By.tagName("tfoot")).findElements(By.tagName("td")).get(2).getText());
        return driver.findElement(By.tagName("tfoot")).findElements(By.tagName("td")).get(2).getText().contains(fee);
    }

    public boolean checkPricePerMonth(String price) {
        return driver.findElement(By.tagName("tfoot")).findElements(By.tagName("td")).get(1).getText().contains(price);
    }

    public void clickAddClientSupportItem(String s) throws InterruptedException {
        System.out.println(clientSupportTables.size());
        clientSupportTables.get(0).findElement(By.xpath("//span[text()='" + s + "']")).
                findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath("..")).
                findElements(By.tagName("td")).get(1).
                findElement(By.xpath("//div[@class='btn-actions dropdown-toggle chk add-on-feature-chk btn-add-on-feature-chk ']")).
                click();
        clickYesToAddItemTOAgreement();
    }

    public void clickYesToAddItemTOAgreement() throws InterruptedException {
        yesAddItemToAgreement.click();
        Thread.sleep(3000);
    }
}
