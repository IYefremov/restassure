package com.cyberiansoft.test.baseutils;

import org.apache.commons.lang3.StringUtils;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;

public class BaseUtils {
	
	public static BrowserType getBrowserType(String browserString) {
		BrowserType browsertype = null;
		for (BrowserType browserTypeEnum : BrowserType.values()) { 
            if (StringUtils.equalsIgnoreCase(browserTypeEnum.getBrowserTypeString(), browserString)) { 
                browsertype = browserTypeEnum; 
                break; 
            } 
        } 
		return browsertype;
	}
	
	public static MobilePlatform getMobilePlatform(String mobilePlatform) {
		MobilePlatform mobileplatform = null;
		for (MobilePlatform mobilePlatformEnum : MobilePlatform.values()) { 
            if (StringUtils.equalsIgnoreCase(mobilePlatformEnum.getMobilePlatformString(), mobilePlatform)) { 
            	mobileplatform = mobilePlatformEnum; 
                break; 
            } 
        } 
		return mobileplatform;
	}

}
