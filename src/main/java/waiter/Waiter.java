package waiter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A class that holds only Selenium wait methods
 */
public class Waiter {
    //hardcoded timeout value; up to how much to wait for a condition to take place
    private final int TIMEOUT = 30;

    //GET METHODS

    /**
     * Open a specified url and wait for the page to load completely.
     * Should replace driver.get().
     * Will wait for the TIMEOUT constant value amount of seconds for the page to load.
     *
     * @param url    - the url to open in the browser
     * @param driver - the driver instance
     */
    public void get(String url, WebDriver driver) {
        get(url, driver, TIMEOUT);
    }

    /**
     * Open a specified url and wait for the page to load completely.
     * Should replace driver.get().
     * Will wait for the specified number of seconds.
     *
     * @param url              - the url to open in the browser
     * @param driver           - the driver instance
     * @param specifiedTimeout - number of seconds to wait for
     */
    public void get(String url, WebDriver driver, int specifiedTimeout) {
        driver.get(url);
        waitForPageLoadComplete(driver, specifiedTimeout);
    }

    /**
     * Method that opens a url and waits for page load complete and for a specified WebElement to be displayed.
     * Uses Selenium's isDisplayed() method to check for element.
     * Wait for up to TIMEOUT.
     *
     * @param url     - url of the page that needs to open
     * @param element - the element to wait for
     * @param driver  - the WebDriver instance
     */
    public void getAndWaitForElementToBeDisplayed(String url, WebElement element, WebDriver driver) {
        getAndWaitForElementToBeDisplayed(url, element, driver, TIMEOUT);
    }

    /**
     * Method that opens a url and waits for page load complete and for a specified WebElement to be displayed.
     * Uses Selenium's isDisplayed() method to check for element.
     * Wait for up to the specified amount of seconds.
     *
     * @param url              - url of the page that needs to open
     * @param element          - the element to wait for
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - number of seconds to wait for
     */
    public void getAndWaitForElementToBeDisplayed(String url, WebElement element, WebDriver driver, int
            specifiedTimeout) {
        get(url, driver, specifiedTimeout);
        waitForElementToBeDisplayed(element, driver, specifiedTimeout);
    }

    /**
     * Method that opens a URL in the browser and waits for another URL to be loaded.
     * Also waits for page to load completely after expected URL is loaded in the browser.
     * Useful when a URL you want to open performs a redirect to another page.
     * Wait for TIMEOUT number of seconds.
     *
     * @param urlToGet     - the URL to open initially
     * @param urlToWaitFor - the URL you expect to be redirected to
     * @param driver       - the WebDriver instance
     */
    public void getUrlAndWaitForUrl(String urlToGet, String urlToWaitFor, WebDriver driver) {
        getUrlAndWaitForUrl(urlToGet, urlToWaitFor, driver, TIMEOUT);
    }

    /**
     * Method that opens a URL in the browser and waits for another URL to be loaded.
     * Also waits for page to load completely after expected URL is loaded in the browser.
     * Useful when a URL you want to open performs a redirect to another page.
     * Wait for a specifiedTimeout number of seconds.
     *
     * @param urlToGet         - the URL to open initially
     * @param urlToWaitFor     - the URL you expect to be redirected to
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - number of seconds to wait for
     */
    public void getUrlAndWaitForUrl(String urlToGet, String urlToWaitFor, WebDriver driver, int specifiedTimeout) {
        driver.get(urlToGet);
        waitForUrl(urlToWaitFor, driver, specifiedTimeout);
    }

    //PAGE LOAD METHODS

    /**
     * Wait for a page to load completely for up to TIMEOUT seconds.
     *
     * @param driver - the WebDriver instance
     */
    public void waitForPageLoadComplete(WebDriver driver) {
        waitForPageLoadComplete(driver, TIMEOUT);
    }

    /**
     * Wait for a page to load completely for the specified number of seconds.
     *
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForPageLoadComplete(WebDriver driver, int specifiedTimeout) {
        Wait<WebDriver> wait = new WebDriverWait(driver, specifiedTimeout);
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));
    }

    //ELEMENT WAIT METHODS

    /**
     * Method that waits for the Selenium isDisplayed() method to return true.
     * Hence waits for an element to be displayed.
     * Will wait for up to TIMEOUT seconds.
     *
     * @param element - the WebElement to be displayed
     * @param driver  - the WebDriver instance
     */
    public void waitForElementToBeDisplayed(WebElement element, WebDriver driver) {
        waitForElementToBeDisplayed(element, driver, TIMEOUT);
    }

    /**
     * Method that waits for the Selenium isDisplayed() method to return true.
     * Hence waits for an element to be displayed.
     * Will wait for up to the specified amount of seconds.
     *
     * @param element          - the WebElement to be displayed
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementToBeDisplayed(WebElement element, WebDriver driver, int specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
        wait.until(elementIsDisplayed);
    }

    // URL wait methods

    /**
     * Wait for a URL to open in the browser and for the page to load completely.
     * Wait for TIMEOUT number of seconds.
     *
     * @param url    - the URL to open
     * @param driver - the WebDriver instance
     */
    public void waitForUrl(String url, WebDriver driver) {
        waitForUrl(url, driver, TIMEOUT);
    }

    /**
     * Wait for a URL to open in the browser and for the page to load completely.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param url              - the URL to open
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForUrl(String url, WebDriver driver, int specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().equals(url);
        wait.until(urlIsCorrect);
        waitForPageLoadComplete(driver, specifiedTimeout);
    }
}
