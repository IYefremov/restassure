package com.cyberiansoft.test.ios10_client.utils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

public class SwipeUtils {

    public static void swipeToElement(String elementName) {
        boolean swipe = true;
        while (swipe) {
            if (DriverBuilder.getInstance().getAppiumDriver().findElementByAccessibilityId(elementName).isDisplayed()) {
                break;
            } else {
                JavascriptExecutor js1 = (JavascriptExecutor) DriverBuilder.getInstance().getAppiumDriver();
                HashMap<String, String> scrollObject1 = new HashMap<>();
                scrollObject1.put("direction", "up");
                js1.executeScript("mobile: swipe", scrollObject1);
            }
        }
    }

    public static void swipeToElement(WebElement elementtoswipe) {
        boolean swipe = true;
        int screenheight = (int) (DriverBuilder.getInstance().getAppiumDriver().manage().window().getSize().getHeight()*0.90);

        while (swipe) {
            if (elementtoswipe.isDisplayed()) {
                break;
            } else if ((elementtoswipe.getLocation().getY() > screenheight)) {

                JavascriptExecutor js1 = (JavascriptExecutor) DriverBuilder.getInstance().getAppiumDriver();
                HashMap<String, String> scrollObject1 = new HashMap<>();
                scrollObject1.put("direction", "up");
                js1.executeScript("mobile: swipe", scrollObject1);

            }
            else
                swipe = false;
        }
    }
}
