/**
 * https://www.extentreports.com/docs/versions/5/java/
 */

package com.serviceNow.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import com.serviceNow.pages.NavPage;
import com.serviceNow.pages.PortalPage;

public class ReportHelper {

    String screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\";
    public String screenShotFileName;
    public DateFormat dateFormat;
    public Date date;
    public FrameHelper frameHelper;
    public PortalPage portal;
    public NavPage nav;

    public ReportHelper() {
        screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\";
        dateFormat = new SimpleDateFormat("ddMMyyyy-HH_mm_ss");
        date = new Date();
    }

    /**
     *
     * This method is useful to short logger and console message
     * 
     * @param logger      {@code ExtentTest} log status for the reports
     * @param test_Status {@code String} status for the logger. (PASS, FAIL, INFO)
     * @param test_Desc   {@code String} Description of test case
     * 
     */
    public void loggerMessage(ExtentTest logger, String test_Status, String test_Desc) {

        if (test_Desc == null || test_Desc.isEmpty()) {
            test_Desc = " **Test Description non available** ";
        } else {
            test_Desc = " --> " + test_Desc;
        }

        if (test_Status.equalsIgnoreCase("PASS")) {

            System.out.println("TEST PASS " + test_Desc);
            if (logger != null) {
                logger.log(Status.PASS, "TEST PASS " + test_Desc);
            }

        } else if (test_Status.equalsIgnoreCase("FAIL")) {

            System.out.println("TEST FAIL " + test_Desc);
            if (logger != null) {
                logger.log(Status.FAIL, "TEST FAIL " + test_Desc);
            }

        } else if (test_Status.equalsIgnoreCase("INFO")) {

            System.out.println("TEST INFO " + test_Desc);
            if (logger != null) {
                logger.log(Status.INFO, "TEST INFO " + test_Desc);
            }
        }
    }

    public void loggerMessage(ExtentTest logger, boolean test_Status, String test_Desc) {
        if (test_Status) {
            loggerMessage(logger, "PASS", test_Desc);
        } else {
            loggerMessage(logger, "FAIL", test_Desc);
        }
    }

    public void loggerMessage(ExtentTest logger, String test_Desc) {
        loggerMessage(logger, "INFO", test_Desc);
    }

    public void loggerMessage(ExtentTest logger, boolean test_Status) {
        loggerMessage(logger, test_Status, "");
    }

    /**
     * 
     * This method is useful to throws error message when
     * catch error exception is triggered.
     * 
     * @param e       {@code Exception} Catch error exception
     * @param message {@code String} Text message that will
     *                showed on console, logger and screenshot.
     * 
     * @author Molina Emiliano
     * 
     */
    public void catchError(ExtentTest logger, WebDriver driver, Exception e, String message) {
        loggerMessage(logger, false, "Something went wrong... could NOT " + message);
        screenShotAndErrorMsg(logger, e, driver, "Screenshot");
        Assert.assertTrue(false, "Something went wrong... could NOT " + message);
        // Assert.fail();
    }

    // import com.aventstack.extentreports.MediaEntityBuilder;
    // TODO TRY TO IMPROVE SCREENSHOTS
    // getLogger().pass(MediaEntityBuilder.createScreenCaptureFromPath("img.png").build());
    public void screenShotAndErrorMsg(ExtentTest logger, Exception exception, WebDriver driver, String fileName) {
        exception.printStackTrace();
        screenShotFileName = screenShotPath + fileName + "_" + dateFormat.format(date) + ".jpg";
        captureScreenShot(logger, driver, screenShotFileName);
        if (logger != null) {
            logger.log(Status.FAIL, "Failed due to <b style='color:red'> " + exception.getClass() + "<br>"
                    + exception.getMessage() + " </b>");
            logger.log(Status.INFO,
                    "For reference Snapshot below: " + logger.addScreenCaptureFromPath(screenShotFileName));
        }

    }

    public void screenShot(ExtentTest logger, WebDriver driver, String fileName) {
        screenShotFileName = screenShotPath + fileName + "_" + dateFormat.format(date) + ".jpg";
        captureScreenShot(logger, driver, screenShotFileName);
        loggerMessage(logger, "For reference Snapshot below: " + logger.addScreenCaptureFromPath(screenShotFileName));
    }

    /**
     * This method will take the screen shot
     * 
     * @param logger
     * @param driver
     * @param fileName
     */
    public void captureScreenShot(ExtentTest logger, WebDriver driver, String fileName) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            loggerMessage(logger, "Screen shot taken");
            FileUtils.copyFile(scrFile, new File(fileName));
            loggerMessage(logger, "Screen shot Path : <b>" + fileName + "</b>");
        } catch (Exception e) {
            e.printStackTrace();
            loggerMessage(logger, "Failed To take Screen shot due to <b style='color:red'>" + e.getClass() + "<br>"
                    + e.getMessage() + "</b>");
        }
    }
}
