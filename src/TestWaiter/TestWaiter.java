import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import waiter.Waiter;

public class TestWaiter {
    private Waiter waiter;
    private WebDriver driver;

    @Before
    public void setup(){
        driver = new ChromeDriver();
        waiter = new Waiter(driver,50);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(){
        driver.close();
    }

    @Test
    public void waitForSite(){
        waiter.get("http://www.github.com/theyuvalraz/");
        waiter.waitForPageLoadComplete();
        WebElement linkedinLink = driver.findElement(By.partialLinkText("yuval-raz-3b52016a"));
        waiter.clickElementAndWaitForPageLoadComplete(linkedinLink, 10);
    }
}
