package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.ControlUtils;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class VNextLoginScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='employees']")
    private WebElement loginscreen;

    @FindBy(xpath = "//*[@data-autotests-id='employees-list']")
    private WebElement employeeslist;

    @FindBy(xpath = "//*[@action='main-db']/i")
    private WebElement updatemaindbbtn;

    @FindBy(xpath = "//*[@action='vin-db']/i")
    private WebElement updatevindbbtn;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordfld;

    @FindBy(xpath = "//span[@class='modal-button ' and text()='Login']")
    private WebElement loginbtn;

    @FindBy(xpath = "//span[@class='modal-button ' and text()='Cancel']")
    private WebElement cancelbtn;

    @FindBy(xpath = "//*[@data-autotests-id='search-icon']")
    private WebElement searchicon;

    @FindBy(xpath = "//*[@data-autotests-id='search-input']")
    private WebElement searchfld;

    @FindBy(xpath = "//*[@data-autotests-id='search-cancel']")
    private WebElement cancelsearchbtn;

    public VNextLoginScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), this);
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='employees']")));
        WaitUtils.elementShouldBeVisible(ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@data-page='employees']")), true);
    }

    public void userLogin(String username, String userpsw) {
        selectEmployee(username);
        if (!StringUtils.isEmpty(userpsw)) {
            setUserLoginPassword(userpsw);
            tapLoginButton();
        }
    }

    public VNextLoginScreen incorrectUserLogin(String username, String userpsw) {
        selectEmployee(username);
        setUserLoginPassword(userpsw);
        tapLoginButton();
        BaseUtils.waitABit(300);
        VNextInformationDialog infrmdialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = infrmdialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.ENTERED_PASSWORD_IS_INCORRECT);
        waitUserListVisibility();
        return new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
    }

    public void setUserLoginPassword(String userpsw) {
        ControlUtils.setValue(passwordfld, userpsw);
    }

    public boolean isUserLoginPasswordDialogVisible() {
        return WaitUtils.isElementPresent(By.xpath("//input[@type='password']"));
    }

    public void selectEmployee(String username) {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 25);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), '" + username + "')]")));
        tapListElement(employeeslist, username);
    }

    public void tapLoginButton() {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 15);
        wait.until(ExpectedConditions.elementToBeClickable(loginbtn));
        //tap(loginbtn);
        loginbtn.click();
    }

    public void tapLoginDialogCancelButton() {
        tap(cancelbtn);
        waitUserListVisibility();
    }

    public void waitUserListVisibility() {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(employeeslist));
    }

    public void updateMainDB() {
        tap(updatemaindbbtn);
        BaseUtils.waitABit(10000);
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationdlg.clickInformationDialogOKButton();
    }

    public void searchEmployee(String searchText) {
        if (!searchfld.isDisplayed())
            searchicon.click();
        BaseUtils.waitABit(1000);
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 15);
        wait.until(ExpectedConditions.elementToBeClickable(searchfld)).clear();
        searchfld.sendKeys(searchText + "\n");
        BaseUtils.waitABit(1000);
    }

    public int getNumberOfEmployeesInTheList() {
        return employeeslist.findElements(By.xpath(".//*[@class='employee-list-item']")).size();
    }

    public boolean isEmployeepresentInTheList(String employeeName) {
        return employeeslist.findElements(By.xpath(".//*[@class='item-title' and text()='" +
                employeeName + "']")).size() > 0;
    }

    public boolean isNothingFoundTextDisplayed() {
        return loginscreen.findElement(By.xpath(".//*[text()='Nothing found']")).isDisplayed();
    }

    public ArrayList<String> getEmployeeList() {
        ArrayList<String> employeesList = new ArrayList<>();
        List<WebElement> employees = employeeslist.findElements(By.xpath(".//*[@class='employee-list-item']"));
        for (WebElement employeeItem : employees)
            employeesList.add(employeeItem.getText().trim());
        return employeesList;
    }

    public boolean isEmployeeListSorted() {
        ArrayList<String> employeesListSorted = getEmployeeList();
        employeesListSorted = employeesListSorted.stream()
                .sorted(Comparator.comparing((String e) -> e.split(" ")[1]))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> employeesList = getEmployeeList();

        boolean sorted = true;
        for (int i = 1; i < employeesListSorted.size(); i++) {
            if (!employeesListSorted.get(i).equals(employeesList.get(i))) {
                sorted = false;
                break;
            }
        }

        return sorted;
    }
}
