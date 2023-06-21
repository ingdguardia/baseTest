package com.serviceNow.util;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.netty.handler.timeout.TimeoutException;

public class Wait extends ReportHelper {

    /**
     * The method waits till element is Clickable
     * 
     * @param logger
     * @param driver
     * @param object
     */
    public void elementIsClickable(ExtentTest logger, WebDriver driver, WebElement object) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
            logger.log(Status.INFO, "Wait upTo <b>60 Seconds </b>");
            wait.until(ExpectedConditions.elementToBeClickable(object));
            logger.log(Status.PASS, "Wait till <b>" + object + " </b>[Object] clickable is Success");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Status.FAIL, "Failed: Wait till element is clickable <b>" + object
                    + "</b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
        }
    }

    // Wait Till Element becomes visible on refreshing page
    public void elementIsVisible(ExtentTest logger, WebDriver driver, WebElement object) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
            logger.log(Status.INFO, "Wait upTo <b>60 Seconds </b>");
            wait.until(ExpectedConditions.visibilityOf(object));
            logger.log(Status.PASS, "Wait till <b>" + object + " </b>[Object] Visible is Success");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Status.FAIL, "Failed: Wait till element is visible <b>" + object
                    + "</b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
        }
    }

    // Wait till page is loaded
    public void pageLoad(ExtentTest logger, WebDriver driver) {
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Status.FAIL, "Page not loaded in 1000 seconds");
        }
    }

    // Wait till page is loaded completely
    public void forPageToLoadCompletely(ExtentTest logger, final WebDriver driver) {
        try {
            logger.log(Status.INFO, "Waiting for page to load completely");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver wdriver) {
                    return ((JavascriptExecutor) driver).executeScript(
                            "var frames = window.frames; var frameNo=(frames.length)-1;" + "if(frames.length<=1){ "
                                    + "return document.readyState;}"
                                    + "return frames[frameNo].document.readyState")
                            .equals("complete");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Status.FAIL, "Unable to wait ForPageToLoadCompletely  due to <b style='color:red'>"
                    + e.getClass() + "<br>" + e.getMessage() + "</b>");
        }
    }

    /**
     * This method wait until given seconds for visibility of given object
     * 
     * @param logger     {@code ExtentTest} log status for the reports
     * @param driver     {@code WebDriver} Driver
     * @param objectName {@code String} Name of the object
     * @param object     {@code WebElement} or {@code By} or {@code xpath} Locator
     *                   of the object
     * @param maxTime    {@code int} Max time in seconds to wait
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     */
    public void visibilityOf(ExtentTest logger, WebDriver driver, String objectName, WebElement object, int maxTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTime));
        wait.until(ExpectedConditions.visibilityOf(object));
        loggerMessage(logger, "pass", objectName + " is visible -> CORRECT");
    }

    public void visibilityOf(ExtentTest logger, WebDriver driver, String objectName, By object, int maxTime) {
        WebElement e = driver.findElement(object);
        visibilityOf(logger, driver, objectName, e, maxTime);
    }

    public void visibilityOf(ExtentTest logger, WebDriver driver, String objectName, String object, int maxTime) {
        WebElement e = driver.findElement(By.xpath(object));
        visibilityOf(logger, driver, objectName, e, maxTime);
    }

    /**
     * This method wait until given seconds for the given object being clickable
     * 
     * @param logger     {@code ExtentTest} log status for the reports
     * @param driver     {@code WebDriver} Driver
     * @param objectName {@code String} Name of the object
     * @param object     {@code WebElement} or {@code By} or {@code xpath} Locator
     *                   of the object
     * @param maxTime    {@code int} Max time in seconds to wait
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     */
    public void elementToBeClickable(ExtentTest logger, WebDriver driver, String objectName, WebElement object,
            int maxTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTime));
        wait.until(ExpectedConditions.elementToBeClickable(object));
        loggerMessage(logger, "pass", objectName + " is clickable");
    }

    public void elementToBeClickable(ExtentTest logger, WebDriver driver, String objectName, By object, int maxTime) {
        WebElement e = driver.findElement(object);
        elementToBeClickable(logger, driver, objectName, e, maxTime);
    }

    public void elementToBeClickable(ExtentTest logger, WebDriver driver, String objectName, String object,
            int maxTime) {
        WebElement e = driver.findElement(By.xpath(object));
        elementToBeClickable(logger, driver, objectName, e, maxTime);
    }

    /**
     * This method wait until 10 seconds for visibility of given object list
     * 
     * @param driver     {@code WebDriver} driver
     * @param objectName {@code String} Name of the object
     * @param objectList {@code List<WebElement>} List of objects
     * @param maxTime    {@code int} Max time to wait in seconds
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     */
    public void visibilityOfList(ExtentTest logger, WebDriver driver, String objectName, List<WebElement> objectList,
            int maxTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(objectList));
        loggerMessage(logger, "pass", objectName + " list is visible");
    }

    /**
     * This method waits a maximum of 5 seconds until the element not be visible.
     * (isDisplayed() = false)
     * 
     * @param logger     {@code ExtentTest} log status for the reports
     * @param driver     {@code WebDriver} driver
     * @param objectName {@code String} Name of the field
     * @param object     {@code WebElement} locator of the element
     * @param maxTime    {@code int} Max time in seconds to wait
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     */
    public void invisibilityOfElement(ExtentTest logger, WebDriver driver, String objectName, WebElement object,
            int maxTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTime));
            wait.until(ExpectedConditions.invisibilityOf(object));
            loggerMessage(logger, "pass", objectName + " is not visible -> CORRECT");
        } catch (Exception e) {
            catchError(logger, driver, e, "verify if element is not longer visible");
        }
    }

    public void invisibilityOfElement(ExtentTest logger, WebDriver driver, String objectName, By object, int maxTime) {
        WebElement e = driver.findElement(object);
        invisibilityOfElement(logger, driver, objectName, e, maxTime);
    }

    /**
     * This method wait until given seconds that the given attribute
     * have the expected value
     * 
     * @param logger            {@code ExtentTest} log status for the reports
     * @param driver            {@code WebDriver} driver
     * @param objectName        {@code String} Name of the element
     * @param object            {@code WebElement} {@code By} {@code xpath} locator
     *                          of the element
     * @param attributeExpected {@code String} Name of the Attribute's element
     *                          expected
     * @param expectedValue     {@code String} Value expected of given attribute
     * @param maxTime           {@code int} Max time in seconds to wait
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     *
     */
    public void attributeToBe(ExtentTest logger, WebDriver driver, String objectName, WebElement object,
            String expectedAttribute, String expectedValue, int maxTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTime));
            wait.until(ExpectedConditions.attributeToBe(object, expectedAttribute, expectedValue));
            loggerMessage(logger, "pass",
                    "Element: " + objectName + " has value: " + expectedValue + " on attribute: " + expectedAttribute);
        } catch (Exception e) {
            catchError(logger, driver, e,
                    "verify if attribute " + expectedAttribute + " has the value: " + expectedValue + " expected");
        }
    }

    public void attributeToBe(ExtentTest logger, WebDriver driver, String objectName, By object,
            String expectedAttribute, String expectedValue, int maxTime) {
        WebElement e = driver.findElement(object);
        attributeToBe(logger, driver, objectName, e, expectedAttribute, expectedValue, maxTime);
    }

    public void attributeToBe(ExtentTest logger, WebDriver driver, String objectName, String object,
            String expectedAttribute, String expectedValue, int maxTime) {
        WebElement e = driver.findElement(By.xpath(object));
        attributeToBe(logger, driver, objectName, e, expectedAttribute, expectedValue, maxTime);
    }

    /**
     * This method wait until given seconds that the
     * attribute of the given element is not empty
     * 
     * @param logger            {@code ExtentTest} log status for the reports
     * @param driver            {@code WebDriver} driver
     * @param objectName        {@code String} Name of the element
     * @param object            {@code WebElement} locator of the element
     * @param attributeExpected {@code String} Name of the Attribute's element
     *                          expected
     * @param maxTime           {@code int} Max time in seconds to wait
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     * 
     */
    public void attributeBeNotEmpty(ExtentTest logger, WebDriver driver, String objectName, WebElement object,
            String attributeExpected, int maxTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTime));
            wait.until(ExpectedConditions.attributeToBeNotEmpty(object, attributeExpected));
            loggerMessage(logger, "pass", attributeExpected + " attribute is not empty in " + objectName);
        } catch (Exception e) {
            catchError(logger, driver, e, "verifies if attribute " + attributeExpected + " is not empty");
        }
    }

    public void attributeBeNotEmpty(ExtentTest logger, WebDriver driver, String objectName, By object,
            String attributeExpected, int maxTime) {
        WebElement e = driver.findElement(object);
        attributeBeNotEmpty(logger, driver, objectName, e, attributeExpected, maxTime);
    }

    public void attributeBeNotEmpty(ExtentTest logger, WebDriver driver, String objectName, String object,
            String attributeExpected, int maxTime) {
        WebElement e = driver.findElement(By.xpath(object));
        attributeBeNotEmpty(logger, driver, objectName, e, attributeExpected, maxTime);
    }

    /**
     * This method wait until given seconds that the given object contains
     * the expected value
     * 
     * @param logger            {@code ExtentTest} log status for the reports
     * @param driver            {@code WebDriver} driver
     * @param objectName        {@code String} Name of the object
     * @param object            {@code WebElement} Locator of the element
     * @param expectedAttribute {@code String} Name of the attribute expected
     * @param expectedValue     {@code String} Value expected
     * @param maxTime           {@code int} Max time in seconds to wait
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     */
    public void attributeContains(ExtentTest logger, WebDriver driver, String objectName, By object,
            String expectedAttribute, String expectedValue, int maxTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTime));
            wait.until(ExpectedConditions.presenceOfElementLocated(object));
            wait.until(ExpectedConditions.attributeContains(object, expectedAttribute, expectedValue));
            loggerMessage(logger, "pass", "Element: " + objectName + " contains value: " + expectedValue
                    + " on attribute: " + expectedAttribute);
        } catch (Exception e) {
            catchError(logger, driver, e,
                    "verify if attribute " + expectedAttribute + " contains the value: " + expectedValue + " expected");
        }
    }

    /**
     * This method wait until 10 seconds for frame and switch to it
     * 
     * @param logger {@code ExtentTest} log status for the reports
     * @param driver {@code WebDriver} driver
     * @param iframe {@code WebElement} locator of the iframe
     *
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     */
    public void iframeAndSwitch(ExtentTest logger, WebDriver driver, WebElement iframe) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
        loggerMessage(logger, "pass", "Switching iframe...");
    }

    /**
     * This method wait until alert is present and perform given action
     * 
     * @param logger          {@code ExtentTest} log status for the reports
     * @param driver          {@code WebDriver} driver
     * @param acceptOrDismiss {@code String} 'Accept' or 'Dismiss' alert
     * 
     * @throws TimeoutException - If the timeout expires.
     * @author Emiliano.molina
     */
    public void alertIsPresent(ExtentTest logger, WebDriver driver, String acceptOrDismiss) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.alertIsPresent());
            if (acceptOrDismiss.equalsIgnoreCase("Accept")) {
                driver.switchTo().alert().accept();
                loggerMessage(logger, "pass", "Alert message accepted");
            } else if (acceptOrDismiss.equalsIgnoreCase("Dismiss")) {
                driver.switchTo().alert().dismiss();
                loggerMessage(logger, "pass", "Alert message dismissed");
            }
        } catch (Exception e) {
            catchError(logger, driver, e, "accept or dismiss alert message");
        }
    }

    // Xpath
    public String macroponent_CssPath = "macroponent-f51912f4c700201072b211d4d8c26010";
    public String iframe_main_CssPath = "iframe#gsft_main";

    /**
     * This method waits a maximum of 50 seconds until the page is fully loaded.
     * 
     * @param logger {@code ExtentTest} log status for the reports
     * @param driver {@code WebDriver} driver
     * 
     * @see #switchToMainIframe(ExtentTest logger, WebDriver driver)
     */
    public void nowPageIsLoaded(ExtentTest logger, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(macroponent_CssPath)));
            switchToMainIframe(logger, driver);

        } catch (Exception e) {
            catchError(logger, driver, e, "Wait for page load completely");
        }
        loggerMessage(logger, true, "Page is fully loaded");
    }

    /**
     * Switches to the main iframe.
     *
     * @param logger the logger to use for logging
     * @param driver the WebDriver instance to use
     * @throws NoSuchElementException if the main iframe cannot be found
     * @throws TimeoutException       if the iframe fails to load within the
     *                                specified time
     */
    public void switchToMainIframe(ExtentTest logger, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            // Find the main iframe and switch to it
            WebElement frame = (WebElement) jse.executeScript(
                    "return document.querySelector('" + macroponent_CssPath + "').shadowRoot.querySelector('"
                            + iframe_main_CssPath + "')");
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
            loggerMessage(logger, true, "Moved to main iframe :" + frame);
            // Wait for the iframe to load completely
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
        } catch (NoSuchElementException | TimeoutException e) {
            catchError(logger, driver, e, "Failed to switch to main iframe:");
        }
    }

}
