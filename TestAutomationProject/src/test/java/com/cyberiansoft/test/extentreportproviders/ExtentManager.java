package com.cyberiansoft.test.extentreportproviders;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {
    
	private static ThreadLocal<ExtentReports> extent = new ThreadLocal<>();
    
    public static ExtentReports getInstance() {
    	if (extent == null)
    		createInstance("test-output/extent.html");
    	
        return extent.get();
    }
    
    public static ExtentReports createInstance(String filePath) {
    	ExtentReports extentmy = new ExtentReports();
    	extentmy.attachReporter(getHtmlReporter(filePath));
    	/*extentmy.setSystemInfo("Browser", "Chrome");
    	extentmy.setSystemInfo("Environment", "Windows 10");
    	extentmy.setSystemInfo("Platform", "Android");
    	extentmy.setSystemInfo("OS Version", System.getProperty("os.version"));
    	extentmy.setSystemInfo("Java Version", System.getProperty("java.version"));
    	extentmy.setSystemInfo("Selenium Version", "3.11.0");*/
        
    	extentmy.setReportUsesManualConfiguration(true);
    	extent.set(extentmy);
     
        return extent.get();
    }
    
    private static ExtentHtmlReporter getHtmlReporter(String filePath) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);

        //htmlReporter.config().setDocumentTitle("VNext");
        htmlReporter.config().setReportName(filePath);
        htmlReporter.config().setChartVisibilityOnOpen(false);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setTimeStampFormat("MMM-dd-yyyy HH:mm:ss a");
        htmlReporter.loadXMLConfig(new File("src/test/java/com/cyberiansoft/test/extentreportproviders/extent-config.xml"));

        return htmlReporter;
    }

}
