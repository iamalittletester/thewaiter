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
    public void get(String url, WebDriver driver, int... specifiedTimeout) {
        driver.get(url);
        waitForPageLoadComplete(driver, checkTimeoutUpdate(specifiedTimeout));
    }

    private int checkTimeoutUpdate(int... specifiedTimeout){
        if (specifiedTimeout.length > 0) return specifiedTimeout[0];
        return TIMEOUT;
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
        get(url, driver, checkTimeoutUpdate(specifiedTimeout));
        waitForElementToBeDisplayed(element, driver, checkTimeoutUpdate(specifiedTimeout));
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
    public void getUrlAndWaitForUrl(String urlToGet, String urlToWaitFor, WebDriver driver, int... specifiedTimeout) {
        driver.get(urlToGet);
        waitForUrl(urlToWaitFor, driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Wait for a page to load completely for the specified number of seconds.
     *
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForPageLoadComplete(WebDriver driver, int... specifiedTimeout) {
        Wait<WebDriver> wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));
    }

    //ELEMENT WAIT METHODS

    /**
     * Method that waits for the Selenium isDisplayed() method to return true.
     * Hence waits for an element to be displayed.
     * Will wait for up to the specified amount of seconds.
     *
     * @param element          - the WebElement to be displayed
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementToBeDisplayed(WebElement element, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
        wait.until(elementIsDisplayed);
    }


    /**
     * Try to click on an element during the specifiedTimeout number of seconds.
     *
     * @param element          - the element to click on
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void click(WebElement element, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementIsClickable = arg0 -> {
            try {
                element.click();
                return true;
            } catch (Exception e) {
                return false;
            }
        };
        wait.until(elementIsClickable);
    }


    /**
     * Method that waits for the text on the element to equal an expected String.
     * Compares the value resulted from getText() on the element with the expected String.
     * Will wait for up to the specifiedTimeout number of seconds for the text on the element to be the expected one.
     *
     * @param element          - the WebElement whose text will be compared to an expected value
     * @param expectedString   - the expected value of the WebElement's text
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementTextEqualsString(WebElement element, String expectedString, WebDriver driver, int
            specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementTextEqualsString = arg0 -> element.getText().equals(expectedString);
        wait.until(elementTextEqualsString);
    }


    /**
     * Method that waits for the text on the element to equal an
     * expected String but ignoring the case of the two.
     * Compares the value resulted from getText() on the element with the expected String, but without taking into
     * account the case of the two values.
     * Therefore, for example 'tHis' and 'This' will be equal when calling this method.
     * Will wait for up to the specifiedTimeout number of seconds for the text on the element to be the expected one.
     *
     * @param element          - the WebElement whose text will be compared to an expected value
     * @param expectedString   - the expected value of the WebElement's text
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementTextEqualsString_IgnoreCase(WebElement element, String expectedString, WebDriver driver,
                                                          int
                                                                  specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementTextEqualsStringIgnoreCase = arg0 -> element.getText().equalsIgnoreCase
                (expectedString);
        wait.until(elementTextEqualsStringIgnoreCase);
    }

    /**
     * Method that waits for the text on the element (whose
     * whitespaces are removed) to equal an expected String (whose whitespaces are also removed).
     * Basically, does a getText() on the WebElement, removes all whitespaces from this resulting String, then compares
     * this value to another String that contains no whitespaces.
     * Whitespaces include: space, new line, tab.
     * When calling the method, the expectedString can contain whitespaces, as they are removed inside this method.
     * Therefore, 'this      string  here' will equal 'this string here'.
     * Will wait for up to the specifiedTimeout number of seconds for the text on the element to be the expected one.
     *
     * @param element          - the WebElement whose text will be compared to an expected value after removing the
     *                         whitespaces on this text
     * @param expectedString   - the expected value of the WebElement's text on which a whitespace removal is also done
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementTextEqualsString_IgnoreWhitespaces(WebElement element, String expectedString, WebDriver
            driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementTextEqualsString = arg0 -> element.getText().replaceAll("\\s", "").equals
                (expectedString.replaceAll("\\s", ""));
        wait.until(elementTextEqualsString);
    }

    /**
     * Method that waits for the text on the element to contain an expected String.
     * Checks whether the value resulted from getText() on the element contains the expected String.
     * Will wait for up to the specifiedTimeout number of seconds for the text on the element to contain the expected one.
     *
     * @param element          - the WebElement whose text will be checked
     * @param expectedString   - the value expected to be contained in the WebElement's text
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementTextContainsString(WebElement element, String expectedString, WebDriver driver, int
            specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementTextContainsString = arg0 -> element.getText().contains(expectedString);
        wait.until(elementTextContainsString);
    }

    /**
     * Method that then waits for the text on the element to contain an
     * expected String but ignoring the case of the two.
     * Checks whether the value resulted from getText() on the element contains the expected String, but without taking
     * into account the case of the two values.
     * Therefore, for example 'tHis' will contain 'his'.
     * Will wait for up to the specifiedTimeout number of seconds for the text on the element to be the expected one.
     *
     * @param element          - the WebElement whose text will be checked
     * @param expectedString   - the value expected to be part of the WebElement's text, ignoring the case
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementTextContainsString_IgnoreCase(WebElement element, String expectedString, WebDriver driver,
                                                            int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementTextContainsString = arg0 -> element.getText().toLowerCase().contains
                (expectedString.toLowerCase());
        wait.until(elementTextContainsString);
    }

    /**
     * Method that waits for the text on the element (whose
     * whitespaces are removed) to contain an expected String (whose whitespaces are also removed).
     * Basically, does a getText() on the WebElement, removes all whitespaces from this resulting String, then checks
     * that this value contains another String that has no whitespaces.
     * Whitespaces include: space, new line, tab.
     * When calling the method, the expectedString can contain whitespaces, as they are removed inside this method.
     * Therefore, 'this      string  here' will contain 'str  ing here'.
     * Will wait for up to the specifiedTimeout number of seconds for the text on the element to be the expected one.
     *
     * @param element          - the WebElement whose text will be compared to an expected value after removing the
     *                         whitespaces on this text
     * @param expectedString   - the value expected to be part of the WebElement's text on which a whitespace removal is
     *                         also done
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementTextContainsString_IgnoreWhitespaces(WebElement element, String expectedString, WebDriver
            driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementTextContainsString = arg0 -> element.getText().replaceAll("\\s", "").contains
                (expectedString.replaceAll("\\s", ""));
        wait.until(elementTextContainsString);
    }

    // Element attribute methods

    /**
     * Method that waits for an element's specified attribute's value to equal another specified String.
     * Compares the value resulted from getAttribute(nameOfAttribute) on the element with the expected String.
     * Will wait for up to the specified number of seconds for an element's attribute value to equal an expected String.
     *
     * @param element          - the WebElement whose attribute we are interested in
     * @param attribute        - the attribute whose value needs to be compared to another value
     * @param expectedString   - the expected value of the WebElement's attribute
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementAttributeEqualsString(WebElement element, String attribute, String expectedString,
                                                    WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementAttributeEqualsString = arg0 -> element.getAttribute(attribute).equals
                (expectedString);
        wait.until(elementAttributeEqualsString);
    }

    /**
     * Method that waits for an element's specified attribute's value to equal another specified String, no matter
     * the case of the actual or expected value.
     * Compares the value resulted from getAttribute(nameOfAttribute) on the element with the expected String,
     * ignoring the cases.
     * Will wait for up to the TIMEOUT number of seconds for an element's attribute value to equal an expected String.
     *
     * @param element          - the WebElement whose attribute we are interested in
     * @param attribute        - the attribute whose value needs to be compared to another value
     * @param expectedString   - the expected value of the WebElement's attribute, case insensitive
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementAttributeEqualsString_IgnoreCase(WebElement element, String attribute, String
            expectedString, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementAttributeEqualsStringIgnoreCase = arg0 -> element.getAttribute(attribute)
                .equalsIgnoreCase
                        (expectedString);
        wait.until(elementAttributeEqualsStringIgnoreCase);
    }

    /**
     * Method that waits for an element's attribute value (whose whitespaces are removed) to equal an expected
     * String (whose whitespaces are also removed).
     * Basically, does a getAttribute(nameOfAttribute) on the WebElement, removes all whitespaces from this resulting
     * String, then compares this value to another String that contains no whitespaces.
     * When calling the method, the expectedString can contain whitespaces, as they are removed inside this method.
     * Whitespaces include: space, new line, tab.
     * Having said that, only the non whitespace characters are compared.
     * Therefore, 'this      string  here' will equal 'this string here'.
     * Will wait for up to the specified timeout number of seconds for expected condition to occur.
     *
     * @param element          - the WebElement whose attribute will be verified
     * @param attribute        - the attribute whose value will be verified
     * @param expectedString   - the expected value of the WebElement's attribute on which a whitespace removal is also
     *                         done
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - the amount of seconds to wait for the condition to occur
     */
    public void waitForElementAttributeEqualsString_IgnoreWhitespaces(WebElement element, String attribute, String
            expectedString, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementAttributeEqualsStringIW = arg0 -> element.getAttribute(attribute)
                .replaceAll("\\s", "").equals(expectedString.replaceAll("\\s", ""));
        wait.until(elementAttributeEqualsStringIW);
    }

    /**
     * Method that waits for an element's specified attribute's value to contain another specified String.
     * Compares the value resulted from getAttribute(nameOfAttribute) on the element with the expected String.
     * Will wait for up to the specified number of seconds for the condition to occur.
     *
     * @param element          - the WebElement whose attribute we are interested in
     * @param attribute        - the attribute whose value needs to be compared to another value
     * @param expectedString   - the expected value of the WebElement's attribute
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementAttributeContainsString(WebElement element, String attribute, String expectedString,
                                                      WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementAttributeContainsString = arg0 -> element.getAttribute(attribute).contains
                (expectedString);
        wait.until(elementAttributeContainsString);
    }

    /**
     * Method that waits for an element's specified attribute's value to contain another specified String, no matter
     * the case of the actual or expected value.
     * Compares the value resulted from getAttribute(nameOfAttribute) on the element with the expected String,
     * ignoring the cases.
     * Will wait for up to the specifiedTimeout number of seconds for the expected condition to occur.
     *
     * @param element          - the WebElement whose attribute we are interested in
     * @param attribute        - the attribute whose value needs to be compared to another value
     * @param expectedString   - the expected value of the WebElement's attribute, case insensitive
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForElementAttributeContainsString_IgnoreCase(WebElement element, String attribute, String
            expectedString, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementAttributeContainsStringIC = arg0 -> element.getAttribute(attribute)
                .toLowerCase()
                .contains(expectedString.toLowerCase());
        wait.until(elementAttributeContainsStringIC);
    }

    /**
     * Method that waits for an element's attribute value (whose whitespaces are removed) to contain an expected
     * String (whose whitespaces are also removed).
     * Basically, does a getAttribute(nameOfAttribute) on the WebElement, removes all whitespaces from this resulting
     * String, then compares this value to another String that contains no whitespaces.
     * When calling the method, the expectedString can contain whitespaces, as they are removed inside this method.
     * Whitespaces include: space, new line, tab.
     * Having said that, only the non whitespace characters are compared.
     * Will wait for up to the specified timeout number of seconds for expected condition to occur.
     *
     * @param element          - the WebElement whose attribute will be verified
     * @param attribute        - the attribute whose value will be verified
     * @param expectedString   - the expected value of the WebElement's attribute on which a whitespace removal is also
     *                         done
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - the amount of seconds to wait for the condition to occur
     */
    public void waitForElementAttributeContainsString_IgnoreWhitespaces(WebElement element, String attribute, String
            expectedString, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> elementAttributeContainsStringIW = arg0 -> element.getAttribute(attribute)
                .replaceAll("\\s", "")
                .contains(expectedString.replaceAll("\\s", ""));
        wait.until(elementAttributeContainsStringIW);
    }


    // URL wait methods

    /**
     * Wait for a URL to open in the browser and for the page to load completely.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param url              - the URL to open
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForUrl(String url, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().equals(url);
        wait.until(urlIsCorrect);
        waitForPageLoadComplete(driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Wait for a URL containing a specified String to open in the browser.
     * The URL will not equal the specified String. Just contain it.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param expectedString   - the String that needs to be included in the URL
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForUrlContains(String expectedString, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().contains(expectedString);
        wait.until(urlIsCorrect);
        waitForPageLoadComplete(driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Wait for an expected URL to load in the browser, but ignore the case of the url.
     * Compares a lower case value of the actual url in the browser with the lower case value of the expected url.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param url              - the url expected to load in the browser, ignoring its case
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForUrl_IgnoreCase(String url, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().toLowerCase().equals(url.toLowerCase
                ());
        wait.until(urlIsCorrect);
        waitForPageLoadComplete(driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Wait for a URL containing a specified String to open in the browser, ignoring the case of the url.
     * Checks whether a lower case value of the actual URL contains a lower case value of the expected String.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param expectedString   - the String that needs to be included in the URL
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForUrlContains_IgnoreCase(String expectedString, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().toLowerCase().contains(expectedString
                .toLowerCase());
        wait.until(urlIsCorrect);
        waitForPageLoadComplete(driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Wait for the URL in the browser to start with a specified String.
     * Please see the startsWith method from the String class for details on how this method determines whether a
     * String starts with another one.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param expectedString   - the expected String to be found at the start of the URL loaded in the browser
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void waitForUrlStartsWith(String expectedString, WebDriver driver, int... specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, checkTimeoutUpdate(specifiedTimeout));
        ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().startsWith(expectedString);
        wait.until(urlIsCorrect);
        waitForPageLoadComplete(driver, checkTimeoutUpdate(specifiedTimeout));
    }

    // CLICK AND WAIT METHODS


    /**
     * Click on a WebElement and than wait for a specified URL to load in the browser.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param element          - the WebElement to click on
     * @param url              - the URL you need are waiting to load
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void clickElementAndWaitForUrl(WebElement element, String url, WebDriver driver, int... specifiedTimeout) {
        click(element, driver);
        waitForUrl(url, driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Click on a WebElement and than wait for the URL that loads in the browser to contain a specified String.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param element             - the WebElement to click on
     * @param expectedStringInUrl - the String you expect to be contained in the URL that loads after clicking the
     *                            WebElement
     * @param driver              - the WebDriver instance
     * @param specifiedTimeout    - amount of seconds you want to wait for
     */
    public void clickElementAndWaitForUrlContains(WebElement element, String expectedStringInUrl, WebDriver driver, int
            specifiedTimeout) {
        click(element, driver);
        waitForUrlContains(expectedStringInUrl, driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Click on a WebElement and than wait for a specified URL to load in the browser, ignoring the case.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param element          - the WebElement to click on
     * @param url              - the URL you need are waiting to load
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void clickElementAndWaitForUrl_IgnoreCase(WebElement element, String url, WebDriver driver, int
            specifiedTimeout) {
        click(element, driver);
        waitForUrl_IgnoreCase(url, driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Click on a WebElement and than wait for the URL that loads in the browser to contain a specified String,
     * ignoring the case.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param element             - the WebElement to click on
     * @param expectedStringInUrl - the String you expect to be contained in the URL that loads after clicking the
     *                            WebElement
     * @param driver              - the WebDriver instance
     * @param specifiedTimeout    - amount of seconds you want to wait for
     */
    public void clickElementAndWaitForUrlContains_IgnoreCase(WebElement element, String expectedStringInUrl, WebDriver
            driver, int... specifiedTimeout) {
        click(element, driver);
        waitForUrlContains_IgnoreCase(expectedStringInUrl, driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Click on a WebElement and wait for the URL in the browser to start with an expected String.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param element          - the WebElement to click on
     * @param expectedString   - the String you expected the URL in the browser to start with
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void clickElementAndWaitForUrlStartsWith(WebElement element, String expectedString, WebDriver
            driver, int... specifiedTimeout) {
        click(element, driver);
        waitForUrlStartsWith(expectedString, driver, checkTimeoutUpdate(specifiedTimeout));
    }

    /**
     * Click on an element and wait for a page refresh. To be used when you want the same page to reload after
     * clicking an element. For redirect to other pages, use wait for url methods.
     * Wait for the specifiedTimeout number of seconds.
     *
     * @param element          - the WebElement to click on
     * @param driver           - the WebDriver instance
     * @param specifiedTimeout - amount of seconds you want to wait for
     */
    public void clickElementAndWaitForPageLoadComplete(WebElement element, WebDriver driver, int... specifiedTimeout) {
        click(element, driver, checkTimeoutUpdate(specifiedTimeout));
        waitForPageLoadComplete(driver, checkTimeoutUpdate(specifiedTimeout));
    }

}
