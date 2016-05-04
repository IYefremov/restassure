package com.cyberiansoft.test.bo.utils;

import org.openqa.selenium.WebDriver;

public class WebDriverInstansiator {

	private static InheritableThreadLocal<WebDriver> webDriver = new InheritableThreadLocal<WebDriver>();
    private static WebDriverFactory factory;

    public static void setDriver(String browserName){
        factory = new WebDriverFactory();
        webDriver.set(factory.getDriver(browserName));
    }

    public static WebDriver getDriver(){
        return webDriver.get();
    }


}
