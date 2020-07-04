package com.cyberiansoft.test.ibs.utils;

import com.cyberiansoft.test.baseutils.AllureUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import com.cyberiansoft.test.inhouse.testcases.BaseTestCase;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.*;
import org.testng.xml.XmlSuite;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestListener extends TestListenerAdapter implements IInvokedMethodListener, IReporter {
    private Object currentClass;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        currentClass = testResult.getInstance();
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }

    @Attachment(value = "Element screenshot", type = "image/png")
    public static byte[] attachScreenshot(Screenshot screenshot) {
        byte[] screenshotAs = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "png", outputStream);
            screenshotAs = outputStream.toByteArray();
        } catch (Exception ignored) { }
        return screenshotAs;
    }

    @Attachment(value = "Marked Image diff", type = "image/png")
    public static byte[] attachScreenshot(ImageDiff screenshot) {
        byte[] screenshotAs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getMarkedImage(), "png", baos);
            screenshotAs = baos.toByteArray();
        } catch (Exception ignored) {
        }
        return screenshotAs;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            AllureUtils.attachScreenshot();
        } catch (Exception e) {
            AllureUtils.failToSaveScreenshot(e);
        }
        try {
            AllureUtils.attachVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DriverBuilder.getInstance().quitDriver();
        DriverBuilder.getInstance().setBrowserType(BaseUtils
                .getBrowserType(InHouseConfigInfo.getInstance().getDefaultBrowser())).setDriver();
        ((BaseTestCase) currentClass).setDriver();
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        try {
            AllureUtils.attachScreenshot();
        } catch (Exception e) {
            AllureUtils.failToSaveScreenshot(e);
        }
        try {
            AllureUtils.attachVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().getDriver().quit();
//            DriverBuilder.getInstance().setDriver(BaseUtils
//                    .getBrowserType(InHouseConfigInfo.getInstance().getDefaultBrowser()));
        }
//        ((BaseTestCase) currentClass).setDriver();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] attachScreenshot() {
        byte[] screenshotAs = null;
        try {
            WebDriver augmentedDriver = new Augmenter().augment(DriverBuilder.getInstance().getDriver());
            screenshotAs = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            fail(e);
        }
        return screenshotAs;
    }

    @Attachment(value = "Unable to save screenshot")
    private String fail(Exception e) {
        return String.format("%s\n%s\n%s", "Failed to save screenshot", e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("SUCCESS: " + getTestMethodName(iTestResult));
        super.onTestSuccess(iTestResult);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("onTestFailedButWithinSuccessPercentage" + getTestMethodName(iTestResult));
        super.onTestFailedButWithinSuccessPercentage(iTestResult);
    }

    private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        File allureResults = new File("./allure-results");
        if (allureResults.exists()) {
            try {
                FileUtils.deleteDirectory(allureResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        super.onFinish(iTestContext);
    }

    @Override
    public void generateReport(List<XmlSuite> list, List<ISuite> suites, String s) {
        for (ISuite suite : suites) {
            String suiteName = suite.getName();

            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext tc = sr.getTestContext();
                System.out.println("Suite: " + suiteName);
                System.out.println("Passed tests: " + tc.getPassedTests().getAllResults().size());
                System.out.println("Failed tests: " + tc.getFailedTests().getAllResults().size());
                System.out.println("Skipped tests: " + tc.getSkippedTests().getAllResults().size());
            }
        }
    }
}