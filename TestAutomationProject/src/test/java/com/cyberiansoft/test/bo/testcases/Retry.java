package com.cyberiansoft.test.bo.testcases;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	int counter = 0;
	int retrys = 4;
	
	@Override
	public boolean retry(ITestResult arg0) {

		if(counter < retrys)
		{
			counter++;
			return true;
		}
		return false;
	}

}
