package com.cyberiansoft.test.bo.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	private int counter = 0;
	private int retries = 2;
	
	@Override
	public boolean retry(ITestResult arg0) {

		if(counter < retries) {
			counter++;
			return true;
		}
		return false;
	}
}
