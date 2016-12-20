/*
 * Creation : 27 sept. 2016
 */
package demo;

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//
//public class TestDemo {
//
//    public static void main(String args[]) {
//
//        System.out.println("Hello World");
//        System.setProperty("webdriver.firefox.marionette", "D:\\Users\\gamirtha\\Downloads\\geckodriver-v0.9.0-win64\\geckodriver.exe");
//
//        // Setup Firefox driver
//        WebDriver driver = new FirefoxDriver();

import static org.testng.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Test {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://optics.citroen.preprod.inetpsa.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @org.testng.annotations.Test
    public void testUntitled() throws Exception {
        driver.get(baseUrl + "/login.do");
        // Animation Commerciale
        driver.findElement(By.linkText("Animation commerciale")).click();
        // Step 1 Click on the Suivi des ventes Filiale
        driver.findElement(By.id("2#1#0")).click();
        // Step 2 Slect Ventes par axe client
        driver.findElement(By.linkText("Ventes par axe client")).click();
        driver.findElement(By.name("cdPays")).click();
        // Step 4 Select Pays "Bélux " (there will be multiple selections availble for Pays)
        new Select(driver.findElement(By.name("cdPays"))).selectByVisibleText("Bélux");
        driver.findElement(By.cssSelector("option[value=\"BE\"]")).click();
        List<WebElement> abc = driver.findElements(By.xpath("//select[@name='cdMarque']"));
        System.out.println(abc.size());

        // Step 5 From "Période d'analyse" select P - Du & Au
        driver.findElement(By.id("data1")).sendKeys("01/11/2016");
        driver.findElement(By.id("data2")).sendKeys("31/12/2016");
        // Step 6 select PVP
        // Step 7 Click on Rechercher
        driver.findElement(By.cssSelector("#trbody > td")).click();
        driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
        // Step 7 results
        driver.findElement(By.xpath("//tr[@id='trbody']/td/div[2]")).click();
        String a = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[3]")).getText();
        System.out.println(a);
        driver.findElement(By.xpath("//tr[@id='trbody']/td/div[4]")).click();
        String b = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[4]")).getText();
        System.out.println(b);

        driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[5]")).click();
        String c = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[5]")).getText();
        System.out.println(c);
        driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[6]")).click();
        String d = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[6]")).getText();
        System.out.println(d);
        driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[7]")).click();
        String e = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[7]")).getText();
        System.out.println(e);

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
    // }

}

// }
