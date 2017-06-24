package com.cyberiansoft.test.bo.utils;
import org.openqa.selenium.WebDriver;

public class WebDriverExt {
	
	   public static void goTo(WebDriver driver, String url) {
	       driver.get(url);
	     }
}
