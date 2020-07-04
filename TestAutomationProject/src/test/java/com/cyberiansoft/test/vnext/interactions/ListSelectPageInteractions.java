package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.ListSelectPage;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ListSelectPageInteractions {
    public static WebElement getItemElementByText(String text) {
        ListSelectPage listPage = new ListSelectPage();
        return WaitUtils.getGeneralFluentWait().until(driver -> listPage.getItemList().stream()
                .filter(elem -> elem.getText().contains(text))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("List element not found " + text)));
    }

    public static void waitListPageReady(String listpageTitle) {
        ListSelectPage listPage = new ListSelectPage();
        WaitUtils.getGeneralFluentWait().until(driver -> listPage.getPageTitle().getText().equals(listpageTitle));
    }

    public static void waitForList(String listName) {
        ListSelectPage listPage = new ListSelectPage();
        WaitUtils.getGeneralFluentWait().until(driver ->
                listPage.getRootElement().findElement(By.xpath(".//*[@data-autotests-id='" + listName + "']")));
    }

    public static void selectItem(String itemName) {
        WaitUtils.click(ListSelectPageInteractions.getItemElementByText(itemName));
    }

    public static void saveListPage() {
        WaitUtils.click(new ListSelectPage().getSaveButton());
    }

    public static void switchToSelectedView() {
        ListSelectPage listPage = new ListSelectPage();
        WaitUtils.click(listPage.getSelectedViewButton());
    }
}
