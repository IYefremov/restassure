package com.cyberiansoft.test.vnext.screens;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.RemoteTouchScreen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.HasNetworkConnection;
import io.appium.java_client.screenrecording.CanRecordScreen;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SwipeableWebDriver extends AppiumDriver implements HasTouchScreen, PressesKeyCode, HasNetworkConnection, CanRecordScreen {
	public RemoteTouchScreen touch;
	
	public SwipeableWebDriver(URL url, Capabilities caps) {
		super(url, caps);
		touch = new RemoteTouchScreen(getExecuteMethod());
	}

	public TouchScreen getTouch() {
		return touch;
	}

	//@Override
	public MobileElement scrollTo(String text) {
	// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public MobileElement scrollToExact(String text) {
	// TODO Auto-generated method stub
		return null;
	}
}