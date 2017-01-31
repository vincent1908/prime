package autoIt;

import static org.testng.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class dffd {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://e495371:Poi12uyt@www.torkusa.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testUntitled() throws Exception {
        driver.get(baseUrl + "/product-list?q=1");
        driver.findElement(By.cssSelector("div.navItem > a")).click();
        driver.findElement(By.cssSelector("div.navItem > a")).click();
        Thread.sleep(10000);

        driver.findElement(By.linkText("Hand towels")).click();
        Thread.sleep(10000);

        driver.findElement(By.linkText("Add to list")).click();
        Thread.sleep(10000);

        driver.findElement(By.linkText("Add to list")).click();
        Thread.sleep(10000);

        driver.findElement(By.id("productsTabLink")).click();
        Thread.sleep(10000);

        driver.findElement(By.id("shoppingListLink")).click();

        Thread.sleep(10000);
        // driver.findElement(By.xpath("//input[@id='selAll']")).click();
        // driver.findElement(By.id("selAll")).click();
        driver.findElement(By.xpath(".//*[@id='shoppingList']/div/div/section/div[3]/div[1]/div/div/label[1]"));

        // driver.findElement(By.xpath("//section[@id='shoppingList']/div/div/section/div[3]/div/div/div/label[2]")).click();
        // driver.findElement(By.id("selAll")).click();
        // driver.findElement(By.xpath("//section[@id='shoppingList']/div/div/section/div[3]/div/div/div/label[2]")).click();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
