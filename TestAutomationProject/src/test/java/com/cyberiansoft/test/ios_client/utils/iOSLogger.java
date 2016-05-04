package com.cyberiansoft.test.ios_client.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class iOSLogger {
    private static ExtentReports extent;
    public static String loggerdir;

    public static ExtentTest testlogger;
    
    public static ExtentReports getInstance() {
    	String time = "";
        if (extent == null) {
        	if (loggerdir == null) {
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
				time = dateFormat.format(now);
				loggerdir = "report/" + time;
				File dir = new File(loggerdir);
				dir.mkdirs();
			}
            extent = new ExtentReports("report/" + time + "/iOSTestReport.html", false, DisplayOrder.OLDEST_FIRST);
            
            // optional
            extent.config()
                .documentTitle("Automation Report")
                .reportName("Regression")
                .reportHeadline("");
               
            // optional
            extent
                .addSystemInfo("Appium Version", "1.40")
                .addSystemInfo("Environment", "QA");
        }
        return extent;
    }
    
    public static void initTestLogger(String testName, String testDesc) {
    	testlogger = extent.startTest(testName, testDesc);   	
    }
    
    public static void initTestLogger(String testName) {
    	testlogger = extent.startTest(testName);    	
    }
    
    public static ExtentTest getTestLogerInstance() {
    	return testlogger;    	
    }
}
