package com.cyberiansoft.test.ios_client.utils;

import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LogAssertions {
	
	public static String stepMessage = "";
	
	static public void assertTrue(boolean condition, String stepDescription, ExtentTest testlogger) {
		stepMessage = stepDescription;
		try
        {
            Assert.assertTrue(condition);
            testlogger.log(LogStatus.PASS, stepDescription, "");
        }
        catch (AssertionError e)
        {
        	//testlogger.log(LogStatus.FAIL, "<pre>" + e.toString() + "</pre>");
        	Assert.fail();
            throw e;
            
        }
	 }
	
	static public void assertFalse(boolean condition, String stepDescription, ExtentTest testlogger) {
		stepMessage = stepDescription;
		try
        {
            Assert.assertFalse(condition);
            testlogger.log(LogStatus.PASS, stepDescription, "");
        }
        catch (AssertionError e)
        {
        	//testlogger.log(LogStatus.FAIL, "<pre>" + e.toString() + "</pre>");
        	Assert.fail();
            throw e;
            
        }
	 }
	
	static public void assertEquals(String actual, String expected, String stepDescription, ExtentTest testlogger) {
		stepMessage = stepDescription;
		try
        {
			Assert.assertEquals(actual, expected);
            testlogger.log(LogStatus.PASS, stepDescription, "");
        }
        catch (AssertionError e)
        {
        	//testlogger.log(LogStatus.FAIL, "<pre>" + e.toString() + "</pre>");
        	Assert.fail();
            throw e;
            
        }
	 }
	
	static public void assertEquals(int actual, int expected, String stepDescription, ExtentTest testlogger) {
		stepMessage = stepDescription;
		try
        {
			Assert.assertEquals(actual, expected);
            testlogger.log(LogStatus.PASS, stepDescription, "");
        }
        catch (AssertionError e)
        {
        	//testlogger.log(LogStatus.FAIL, "<pre>" + e.toString() + "</pre>");
        	Assert.fail();
            throw e;
            
        }
	 }
}