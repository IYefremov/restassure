package com.cyberiansoft.test.baseutils;

import com.automation.remarks.video.recorder.VideoRecorder;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.google.common.io.Files.toByteArray;
import static org.awaitility.Awaitility.await;

public class AllureUtils {

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] attachScreenshot() {
        byte[] screenshotAs = null;
        try {
            WebDriver augmentedDriver = new Augmenter().augment(DriverBuilder.getInstance().getDriver());
            screenshotAs = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            failToSaveScreenshot(e);
        }
        return screenshotAs;
    }

    @Attachment(value = "Unable to save screenshot")
    public static String failToSaveScreenshot(Exception e) {
        return String.format("%s\n%s\n%s", "Failed to save screenshot",
                e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @Attachment(value = "Unable to save video")
    static String failToSaveVideo(Exception e) {
        return String.format("%s\n%s\n%s", "Failed to save video",
                e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @Attachment(value = "Video record", type = "video/avi")
    public static byte[] attachVideo() {
        try {
            File video = VideoRecorder.getLastRecording();
            await().atMost(5, TimeUnit.SECONDS)
                    .pollDelay(1, TimeUnit.SECONDS)
                    .ignoreExceptions()
                    .until(() -> video != null);
            return toByteArray(video);
        } catch (Exception e) {
            failToSaveVideo(e);
            return new byte[0];
        }
    }

    @Attachment(value = "Log", type = "text/html")
    public static String attachLog(String text) {
        return String.format("%s\n", text);
    }
}