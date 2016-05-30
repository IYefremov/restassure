package com.cyberiansoft.test.reporting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReportFactory {
	
	public static ExtentReports reporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
	public static String reporttime;
	
	public static String getCurrentTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		reporttime = dateFormat.format(now);
		return dateFormat.format(now);
	}
	
	private synchronized static ExtentReports getExtentReport() {
		if (reporter == null) {
			reporter = new ExtentReports("reportvnext/" + getCurrentTime() + "/ComplexReport.html", true, DisplayOrder.NEWEST_FIRST);
		}
		return reporter;
	}
	
	public synchronized static ExtentTest getTest(String testName, String testDescription) {

		if (!nameToTestMap.containsKey(testName)) {
			Long threadID = Thread.currentThread().getId();
			ExtentTest test = getExtentReport().startTest(testName, testDescription);
			nameToTestMap.put(testName, test);
			threadToExtentTestMap.put(threadID, testName);
		}
		return nameToTestMap.get(testName);
	}
	
	public synchronized static ExtentTest getTest() {
		Long threadID = Thread.currentThread().getId();

		if (threadToExtentTestMap.containsKey(threadID)) {
			String testName = threadToExtentTestMap.get(threadID);
			return nameToTestMap.get(testName);
		}	
		return null;
	}
	
	public synchronized static ExtentTest getTest(String testName) {
		return getTest(testName, "");
	}
	
	public synchronized static void closeTest(String testName) {

		if (!testName.isEmpty()) {
			ExtentTest test = getTest(testName);
			getExtentReport().endTest(test);
		}
	}

	public synchronized static void closeTest(ExtentTest test) {
		if (test != null) {
			getExtentReport().endTest(test);
		}
	}

	public synchronized static void closeTest()  {
		ExtentTest test = getTest();
		closeTest(test);
	}
	
	public synchronized static void closeReport() {
		if (reporter != null) {
			reporter.flush();
			reporter.close();
		}
	}

}
