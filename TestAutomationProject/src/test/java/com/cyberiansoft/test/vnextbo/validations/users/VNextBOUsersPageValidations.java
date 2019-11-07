package com.cyberiansoft.test.vnextbo.validations.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOUsersPageValidations extends VNextBOBaseWebPageValidations {

    public static void isAddNewUserBtnDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.addNewUserBtn),
                "\"Add New User\" button hasn't been displayed.");
    }

    public static void isUsersTableDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.usersTable.getWrappedElement()),
                "\"Add New User\" button hasn't been displayed.");
    }

    public static void isPageNavigationButtonsDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.nextPageBtn) &
                        Utils.isElementDisplayed(vNexBOUsersWebPage.previousPageBtn),
                "Navigation buttons haven't been displayed.");
    }

    public static void isOpenedPageNumberCorrect(String expectedPageNumber)
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertEquals(VNextBOUsersPageSteps.getActivePageNumberFromTopPagingElement(), expectedPageNumber,
                "Page hasn't been changed.");
    }

    public static void verifyTopAndBottomPagingElementsHaveSamePageNumber()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertEquals(VNextBOUsersPageSteps.getActivePageNumberFromBottomPagingElement(),
                VNextBOUsersPageSteps.getActivePageNumberFromTopPagingElement(),
                "Top and bottom paging elements haven't been synchronized.");
    }

    public static boolean isLastPageButtonClickable()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.isElementClickable(vNexBOUsersWebPage.lastPageBtn);
    }

    public static boolean isFirstPageButtonClickable()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.isElementClickable(vNexBOUsersWebPage.firstPageBtn);
    }

    public static void isItemsPerPageNumberCorrect(String expectedItemsNumber)
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(VNextBOUsersPageSteps.getItemsPageNumberFromTopElement().contains(expectedItemsNumber),
                "Paging box has had incorrect items per page number.");
    }

    public static void verifyTopAndBottomPagingPagingBoxesHaveSameItemsPerPageNumber()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertEquals(VNextBOUsersPageSteps.getItemsPageNumberFromTopElement(),
                VNextBOUsersPageSteps.getItemsPageNumberFromBottomElement(),
                "Top and bottom paging boxes elements haven't been synchronized.");
    }

    public static void isSearchFilterTextCorrect(String text)
    {
        Assert.assertTrue(VNextBOUsersPageSteps.getSearchFilterText().contains(text),
                "Search option under Search field hasn't been correct");
    }

    public static void isUsersNotFoundMessageDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersNotFoundMessage(), "No items to show",
                "\"No items to show\" message hasn't been displayed.");
    }

    public static boolean isRedTriangleWarningIconDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.isElementDisplayed(vNexBOUsersWebPage.redTriangleWarningIcon);
    }

    public static boolean isRedTriangleWarningIconNotDisplayed(VNexBOUsersWebPage vNexBOUsersWebPage)
    {
        return Utils.isElementNotDisplayed(vNexBOUsersWebPage.redTriangleWarningIcon);
    }

    public static void isReSendButtonDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.reSendButton),
                "Re-send button hasn't been displayed.");
    }

    public static boolean isReSendButtonNotDisplayed(VNexBOUsersWebPage vNexBOUsersWebPage)
    {
        return Utils.isElementNotDisplayed(vNexBOUsersWebPage.reSendButton);
    }

    public static boolean isUserPresentOnCurrentPageByText(String userMail) {
        try {
            VNextBOUsersPageSteps.getTableRowWithText(userMail);
        } catch (NullPointerException ignored) {}
        WebElement row = VNextBOUsersPageSteps.getTableRowWithText(userMail);
        return row != null;
    }

    public static boolean isUserPresentInTableByText(String userMail) {
        boolean found = false;
        boolean nextPage = true;
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        while (nextPage) {
            if (vNexBOUsersWebPage.nextPageBtn.getAttribute("disabled") != null) {
                found = isUserPresentOnCurrentPageByText(userMail);
                nextPage = false;
            } else if (!isUserPresentOnCurrentPageByText(userMail)) {
                Utils.clickWithJS(vNexBOUsersWebPage.nextPageBtn);
                WaitUtilsWebDriver.waitForLoading();
            } else {
                found = true;
                break;
            }
        }
        return found;
    }
}