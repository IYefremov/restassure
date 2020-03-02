package com.cyberiansoft.test.vnext.listeners;

import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Carl Cocchiaro
 *
 * TestNG Listener Utility Class
 *
 */
public class TestNG_ConsoleRunner extends TestListenerAdapter {
    private static String logFile = null;

    /**
     * onStart method
     *
     * @param testContext
     */
    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
    }

    /**
     * onFinish method
     *
     * @param testContext
     */
    @Override
    public void onFinish(ITestContext testContext) {
        log("\nTotal Passed = " + getPassedTests().size() + ", Total Failed = " + getFailedTests().size() + ", Total Skipped = " + getSkippedTests().size() + "\n");

        super.onFinish(testContext);
    }

    /**
     * onTestStart method
     *
     * @param tr
     */
    @Override
    public void onTestStart(ITestResult tr) {
        if ( logFile == null ) {
            logFile = "report/"+ "VNext" + "-" + new SimpleDateFormat("MM.dd.yy.HH.mm.ss").format(new Date()) + ".log";
        }

        log("\n---------------------------------- Test '" + tr.getName() + getTestDescription(tr) + "' ----------------------------------\n");
        log(tr.getStartMillis(),"START-> " + tr.getName() + "\n");
        log("    ***Test Parameters = " + getTestParams(tr) + "\n");

        super.onTestStart(tr);
    }

    /**
     * onTestSuccess method
     *
     * @param tr
     */
    @Override
    public void onTestSuccess(ITestResult tr) {
        log("    ***Result = PASSED\n");
        log(tr.getEndMillis(),"END  -> " + tr.getName());
        log("\n---\n");

        super.onTestSuccess(tr);
    }

    /**
     * onTestFailure method
     *
     * @param tr
     */
    @Override
    public void onTestFailure(ITestResult tr) {
        if ( !getTestMessage(tr).equals("") ) {
            log(getTestMessage(tr) + "\n");
        }

        log("    ***Result = FAILED\n");
        log(tr.getEndMillis(),"END  -> " + tr.getInstanceName() + "." + tr.getName());
        log("\n---\n");

        super.onTestFailure(tr);
    }

    /**
     * onTestSkipped method
     *
     * @param tr
     */
    @Override
    public void onTestSkipped(ITestResult tr) {
        if ( !getTestMessage(tr).equals("") ) {
            log(getTestMessage(tr) + "\n");
        }

        log("    ***Result = SKIPPED\n");
        log(tr.getEndMillis(),"END  -> " + tr.getInstanceName() + "." + tr.getName());
        log("\n---\n");

        super.onTestSkipped(tr);
    }

    /**
     * onConfigurationSuccess method
     *
     * @param itr
     */
    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        super.onConfigurationSuccess(itr);
    }

    /**
     * onConfigurationFailure method
     *
     * @param tr
     */
    @Override
    public void onConfigurationFailure(ITestResult tr) {
        if ( !getTestMessage(tr).equals("") ) {
            log(getTestMessage(tr) + "\n");
        }

        log("    ***Result = CONFIGURATION FAILED\n");
        log(tr.getEndMillis(),"END CONFIG -> " + tr.getInstanceName() + "." + tr.getName());
        log("\n---\n");

        super.onConfigurationFailure(tr);
    }

    /**
     * onConfigurationSkip method
     *
     * @param tr
     */
    @Override
    public void onConfigurationSkip(ITestResult tr) {
        log(getTestMessage(tr));
        log("    ***Result = CONFIGURATION SKIPPED\n");
        log(tr.getEndMillis(),"END CONFIG -> " + tr.getInstanceName() + "." + tr.getName());
        log("\n---\n");

        super.onConfigurationSkip(tr);
    }

    /**
     * log method
     *
     * @param dateMillis
     * @param line
     */
    public void log(long dateMillis,String line) {
        System.out.format("%s: %s%n", new Date(dateMillis),line);

        if ( logFile != null ) {
            writeTestngLog(logFile, line);
        }
    }

    /**
     * log method
     *
     * @param line
     */
    public void log(String line) {
        System.out.format("%s%n", line);

        if ( logFile != null ) {
            writeTestngLog(logFile, line);
        }
    }

    /**
     * getTestMessage method
     *
     * @param tr
     * @return String
     */
    public String getTestMessage(ITestResult tr) {
        boolean found = false;

        if ( tr != null && tr.getThrowable() != null ) {
            found = true;
        }

        if (found) {
            return tr.getThrowable().getMessage() == null ? "" : tr.getThrowable().getMessage();
        }

        else {
            return "";
        }
    }

    /**
     * getTestParams method
     *
     * @param tr
     * @return String
     */
    public String getTestParams(ITestResult tr) {
        int iLength = tr.getParameters().length;
        StringBuilder message = new StringBuilder();

        try {
            if ( tr.getParameters().length > 0 ) {
                message = new StringBuilder(tr.getParameters()[0].toString());

                for ( int iCount = 0; iCount < iLength; iCount++ ) {
                    if ( iCount == 0 ) {
                        message = new StringBuilder(tr.getParameters()[0].toString());
                    }
                    else {
                        message.append(", ").append(tr.getParameters()[iCount].toString());
                    }
                }
            }
        }

        catch(Exception e) {
            // do nothing...
        }

        return message.toString();
    }

    /**
     * getTestDescription method
     *
     * @param tr
     * @return String
     */
    public String getTestDescription(ITestResult tr) {
        String message = "";

        try {
            if ( tr.getParameters().length > 0 ) {
                message = ": " + tr.getParameters()[1].toString();
            }
        }

        catch(Exception e) {
            // do nothing...
        }

        return message;
    }

    /**
     * writeTestngLog method
     *
     * @param logFile
     * @param line
     */
    public void writeTestngLog(String logFile,String line) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        File directory = new File(VNextEnvironmentInfo.getInstance().getReportFolderPath());
        File file = new File(logFile);

        try {
            if ( !directory.exists() ) {
                directory.mkdirs();
            }

            else if ( !file.exists() ) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));

            if ( line.contains("START") || line.contains("END") ) {
                writer.append("[").append(dateFormat.format(date)).append("] ").append(line);

            }

            else {
                writer.append(line);
            }

            writer.newLine();
            writer.close();
        }

        catch(IOException e) {
            // do nothing...
        }
    }

}
