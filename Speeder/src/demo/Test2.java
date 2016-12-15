/*
 * Creation : 14 déc. 2016
 */
package demo;

import static org.testng.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test2 {
    private WebDriver driver;
    private String baseUrl;
    // private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://optics.citroen.preprod.inetpsa.com/";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testUntitled2() throws Exception {
        driver.get(baseUrl + "/login.do");
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(By.id("clqLangues"));
        action.moveToElement(we).build().perform();
        WebElement we1 = driver.findElement(By.linkText("Langues"));
        action.moveToElement(we1).build().perform();
        WebElement we2 = driver.findElement(By.linkText("English/Great Britain"));
        action.moveToElement(we2).click().build().perform();
        // Thread.sleep(5000);

        // WebElement ele = driver.findElement(this.getObject(p, objectName, objectType));
        // Actions action = new Actions(driver);
        // action.moveToElement(ele).build().perform();
        //
        // driver.findElement(By.id("clqLangues")).click();
        // driver.findElement(By.id("clqLangues")).click();
        // driver.findElement(By.linkText("Langues")).click();
        // driver.findElement(By.linkText("English/Great Britain")).click();
        driver.findElement(By.linkText("Sales Promotion")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//td[@id='tdMenuGauche']/table/tbody/tr[6]/td[3]")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("2#1#0")).click();
        Thread.sleep(5000);
        driver.findElement(By.linkText("Sales by customer type")).click();
        Thread.sleep(5000);
        driver.findElement(By.name("cdPays")).click();
        Thread.sleep(5000);
        new Select(driver.findElement(By.name("cdPays"))).selectByVisibleText("Belux");
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("option[value=\"BE\"]")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("trigger1")).click();
        Thread.sleep(5000);
        // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
        driver.findElement(By.id("trigger2")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//div[2]/table/tbody/tr[5]/td[8]")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//tr[@id='trbody']/td/div[4]/a/img")).click();
        Thread.sleep(5000);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    // private boolean isElementPresent(By by) {
    // try {
    // driver.findElement(by);
    // return true;
    // } catch (NoSuchElementException e) {
    // return false;
    // }
    // }

    // private boolean isAlertPresent() {
    // try {
    // driver.switchTo().alert();
    // return true;
    // } catch (NoAlertPresentException e) {
    // return false;
    // }
    // }

    // private String closeAlertAndGetItsText() {
    // try {
    // Alert alert = driver.switchTo().alert();
    // String alertText = alert.getText();
    // if (acceptNextAlert) {
    // alert.accept();
    // } else {
    // alert.dismiss();
    // }
    // return alertText;
    // } finally {
    // acceptNextAlert = true;
    // }
    // }
}
