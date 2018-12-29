# thewaiter - A waiter library for Selenium tests.

### Travis CI: [![Build Status](https://travis-ci.org/iamalittletester/thewaiter.svg?branch=master)](https://travis-ci.org/iamalittletester/thewaiter) Javadoc: [![Javadocs](http://javadoc.io/badge/com.imalittletester/thewaiter.svg)](http://javadoc.io/doc/com.imalittletester/thewaiter)


## Purpose
The purpose for this library is to help write reliable Selenium tests by using WebDriverWait based methods to wait for a wide range of page events.
It contains a class called waiter, which holds all the wait based methods. Some examples of what you can wait for by using this library:
  * page load complete
  * URLs to load in browser, with variations: the URL to wait for equals, contains an expected String; ignores or cares about the case of the URL and expected String
  * opening a URL in the browser and waiting for: the page load complete, a URL to load or a webElement to be displayed
  * an element to become clickable and the actual click to happen
  * a WebElement's text: to equal or contain an expected String, taking into account or ignoring the case and whitespaces
  * a WebElement's attribute: to equal or contain an expected String, taking into account or ignoring the case and whitespaces
  * clicking on a WebElement and waiting for: a URL to load in the browser, which equals, contains or starts with a specified String, ignoring or taking into account the case
  * clicking on a WebElement and waiting for the page to load completely
  
 New releases of the library will add even more useful wait methods.

## Where to find the release versions
The library was uploaded to the official Maven repository and can be found here: https://mvnrepository.com/artifact/com.imalittletester/thewaiter 


## Importing into your TestWaiter project
In order to import the library into your Maven project, in the `<dependencies>` section, add the following dependency:
```java
 <dependency>
     <groupId>com.imalittletester</groupId>
     <artifactId>thewaiter</artifactId>
     <version>1.0</version>
     <exclusions>
            <exclusion>
                    <groupId>org.seleniumhq.selenium</groupId>
                    <artifactId>selenium-java</artifactId>
            </exclusion>
     </exclusions>
 </dependency>
  ```
  The waiter library has Selenium as a dependency, so in order to avoid dependency management issues, the "exclusion" section needs to be added. This way, you can control from your own dependency the Selenium version you want to import into your project.
  
## Using waiter in your tests
After you imported the library as a dependency, you can start creating the tests that will use it. In the class where you will use it, you first need to import the waiter class, as follows:
```java
import waiter.waiter;
```
Then, you need to instantiate it:
```java
private waiter waiter = new waiter();
```
In the TestWaiter itself, just call the method you need from the waiter class. Some examples:
```java
 waiter.get(theUrl, theDriverInstance);

 waiter.waitForElementTextEqualsString(webElement, expectedString, theDriverInstance);

 waiter.waitForElementAttributeContainsString(webElement, theAttribute, expectedString, theDriverInstance, 10);

 waiter.click(webElement, theDriverInstance);
 
 waiter.clickElementAndWaitForUrlContains(webElement, expectedString, theDriverInstance);
```
When the method you use from the library requires a WebElement to be passed to the method call, make sure you define the WebElement in a PageObject class, not as "driver.findElement()" directly in the method call, as the latter will not work. 

Make sure to read the Javadoc of the methods you want to use: http://javadoc.io/doc/com.imalittletester/thewaiter.

## Further reading
I will write some posts with examples of how to use the library on my blog (https://imalittletester.com/category/waiter/).
There will also be TestWaiter examples in another GitHub repo i am working on (https://github.com/iamalittletester/learning-project/tree/master/src/TestWaiter/java/waitertests).
