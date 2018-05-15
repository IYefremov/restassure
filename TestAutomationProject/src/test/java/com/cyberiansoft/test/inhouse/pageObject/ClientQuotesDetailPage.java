package com.cyberiansoft.test.inhouse.pageObject;

import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import com.cyberiansoft.test.inhouse.utils.MailChecker;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientQuotesDetailPage extends BasePage {

    private String userName;
    private String userPassword;

    @FindBy(className = "agreement-statuses")
    private WebElement agreementStatuses;

    @FindBy(className = "btn-select-edition-discount")
    private WebElement discountBTN;

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-finalize-agreement']")
    private WebElement finalizeAgreementBTN;

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-send-notification']")
    private WebElement sendNotificationBTN;

    @FindBy(className = "form-control")
    private WebElement formControlList;

    @FindBy(xpath = "//button[@class='submit btn-save-select-edition-discount']")
    private WebElement submitDiscountBTN;

    @FindBy(xpath = "//button[@class='submit btn-save-select-edition-feature-setup-fee']")
    private WebElement submitSetupFeeBTN;

    @FindBy(xpath = "//tbody[@data-tbody-feature-group-id]")
    private List<WebElement> clientSupportTables;

    @FindBy(xpath = "//a[@class='btn-add-ischecked-addon-edition-feature addon-checked-value']")
    private WebElement yesAddItemToAgreement;

    @FindBy(className = "price")
    private WebElement repair360Free;

    @FindBy(xpath = "//table[@class='text-center table-price']//td[@data-price-per-month]")
    private WebElement pricePerMonth;

    @FindBy(xpath = "//table[@class='text-center table-price']//td[@data-setup-fee]")
    private WebElement setUpFee;

    @FindBy(xpath = "//div[@id='finalize-validation-error-dialog']/div[@class='modal modal-primary']")
    private WebElement modalDialog;

    @FindBy(xpath = "//span[@class='notification-status']")
    private WebElement notificationStatus;

    public ClientQuotesDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        userName = InHouseConfigInfo.getInstance().getUserName();
        userPassword = InHouseConfigInfo.getInstance().getUserPassword();
    }

    public boolean checkAgreementStatuses(String aNew, String no, String no1, String no2) {
        waitABit(5000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("agreement-statuses")));
        waitABit(5000);
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

    public void clickDiscountBTN() {
        discountBTN.click();
        waitABit(2000);
    }

    public void clickFinalizeAgreementBTN() {
        wait.until(ExpectedConditions.elementToBeClickable(finalizeAgreementBTN)).click();
        try {
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {}
    }

    public void handleAlertForFinalizeAgreementBTN() {
        try {
            wait.until(ExpectedConditions.attributeToBe(modalDialog, "display", "block"));
            wait.until(ExpectedConditions.elementToBeClickable(modalDialog.findElement(By.className("close")))).click();
            wait.until(ExpectedConditions.attributeToBe(modalDialog, "display", "none"));
        } catch (Exception ignored) {}
    }

    public void clickSendNotificationButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(sendNotificationBTN)).click();
        } catch (Exception e) {
            clickWithJS(sendNotificationBTN);
        }
        try {
            driver.switchTo().alert().accept();
            waitForLoading();
            //todo add waitForLoading method?
        } catch (Exception e) {
            handleAlertForFinalizeAgreementBTN();
            Assert.fail("The modal dialog has been displayed after clicking the \"Send Notification\" button." + e);
        }
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(notificationStatus, "Sending..."));
            waitABit(10000);
            wait.until(ExpectedConditions.textToBePresentInElement(notificationStatus, "Notification successfully sent."));
        } catch (TimeoutException e) {
            waitABit(20000);
            try {
                wait.until(ExpectedConditions.textToBePresentInElement(notificationStatus, "Notification successfully sent."));
            } catch (TimeoutException ex) {
                Assert.fail("The \"Notification successfully sent\" has not been displayed!");
            }
        }
    }

    public boolean checkEmails(String title) {
        boolean flag = false;
        waitABit(30000);
        for (int i = 0; i < 5; i++) {
            try {
                if (!MailChecker.searchSpamEmailAndGetMailMessage(userName, userPassword, title,
                        "noreply@repair360.net").isEmpty()) {
                    flag = true;
                    break;
                }
            } catch (NullPointerException ignored) {}
            waitABit(40000);
        }
        return flag;
    }

    public ArrayList<String> getLinks() throws IOException {
        String mailContent = MailChecker.getUserMailContentFromSpam();
        Pattern linkPattern = Pattern.compile("(<a[^>]+>.+?<\\/a>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(mailContent);
        ArrayList<String> links = new ArrayList<>();
        while (pageMatcher.find()) {
            links.add(pageMatcher.group());
        }
        return links;
    }

    public String getAgreementApproveLink() {
//        String mailContent = null;
//        try {
//            mailContent = MailChecker.getUserMailContentFromSpam();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Document doc = Jsoup.parse(mailContent);
//        String text = doc.body().text();
//        System.out.println(text);
//        String[] allTexts = text.split(" ");
//        String result = "";
//        for (String str : allTexts) {
//            if (str.contains("https://goo.gl")) {
//                result = str;
//            }
//        }
//        return result;


        final String usermail = "test.cyberiansoft@gmail.com";
        final String usermailpsw = "ZZzz11!!";
        final String usermailtitle = "ReconPro vNext Dev: REGISTRATION";
        final String sendermail = "Repair360-qc@cyberianconcepts.com";
        final String mailcontainstext = "complete the registration process";

        String mailmessage = "";
        for (int i = 0; i < 4; i++) {
            if (!com.cyberiansoft.test.ios_client.utils.MailChecker.searchSpamEmail(userName, userPassword, "Agreement", "noreply@repair360.net", "https://goo.gl")) {
                waitABit(60 * 500);
            } else {
                mailmessage = com.cyberiansoft.test.ios_client.utils.MailChecker.searchEmailAndGetMailMessage(userName, userPassword, "Agreement", "noreply@repair360.net");
                break;
            }
        }
        return mailmessage;
    }

    public String getMailContentFromSpam() throws IOException {
        return MailChecker.getUserMailContentFromSpam();
    }

    public void selectDiscount(String discount) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(formControlList));
        new Select(formControlList).selectByVisibleText(discount);
        submitDiscountBTN.click();
        waitForLoading();
    }

    public boolean checkNewPrice(String price) {
        waitABit(3000);
        System.out.println("PRICE:");
        System.out.println(pricePerMonth.getText());
        System.out.println(price);
        return pricePerMonth.getText().equals(price);
    }

    public boolean checkSetupFee(String fee) {
        waitABit(3000);
        System.out.println("SetUpFee:");
        System.out.println(setUpFee.getText());
        System.out.println(fee);
        return setUpFee.getText().contains(fee);
    }

    public boolean checkPricePerMonth(String price) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(pricePerMonth, price));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddClientSupportItem(String clientSupportItem) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[text()='" +
                            clientSupportItem + "']/following::td[1]//i[@class='icon cb-icon-check-empty']")))).click();
        } catch (Exception e) {
            clickWithJS(driver.findElement(By.xpath("//span[text()='" +
                            clientSupportItem + "']/following::td[1]//i[@class='icon cb-icon-check-empty']")));
        }
        clickYesToAddItemTOAgreement();
    }

    private void clickSelectSetupFee(String clientSupportItem, String option) {
        waitABit(3000);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver
                    .findElement(By.xpath("//span[text()='" + clientSupportItem + "']/following::td[2]//span")))).click();
            wait.until(ExpectedConditions.elementToBeClickable(formControlList));
            new Select(formControlList).selectByVisibleText(option);
        } catch (Exception e) {
            try {
                new Select(formControlList).selectByVisibleText(option);
            } catch (Exception e1) {
                Assert.fail("The Setup fee for \"" + clientSupportItem + "\" has not been selected!" + e);
            }
        }
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitSetupFeeBTN)).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitForLoading();
    }

    public void selectSetupFeeForAllClients() {
        clickSelectSetupFee("testFeature2_1 test mike", "setupfee1_1");
        clickSelectSetupFee("testFeature3 test mike", "setupfee3_1");
        clickSelectSetupFee("tf22222", "1_1");
        clickSelectSetupFee("Test private", "test name");
        clickSelectSetupFee("Create Invoices (single or multiple vehicles) (edit)", "1");
        clickSelectSetupFee("Copy to Agreement settings page only features with \"Publice view\" State (when creating Client Agreement).", "test_r_1");
    }

    private void clickYesToAddItemTOAgreement() {
        yesAddItemToAgreement.click();
        waitForLoading();
    }
}
