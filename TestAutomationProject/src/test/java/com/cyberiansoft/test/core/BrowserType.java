package com.cyberiansoft.test.core;

public enum BrowserType {
	
	CHROME("chrome"),
	FIREFOX("firefox"),
	IE("ie"),
	EDGE("edge"),
	SAFARI("safari"),
	SELENOID_CHROME("selenoid_chrome");

	private final String browserType; 
	
	BrowserType(final String browserType) { 
        this.browserType = browserType; 
    } 
	
	public String getBrowserTypeString() { 
        return browserType; 
    } 
}