/*
 * Creation : 20 déc. 2016
 */
package excel;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;

public class PaymentGateway {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test(description = "Get the data from the external source file")
    public void TestCase1() throws Exception {
        FileInputStream fi = new FileInputStream("C:\\SpeedTest.xls");
        Workbook w = Workbook.getWorkbook(fi);
        Sheet s = w.getSheet(0);
        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");
        driver.findElement(By.linkText("Animation commerciale")).click();
        driver.findElement(By.linkText("Suivi des ventes Filiale")).click();
        driver.findElement(By.linkText("Ventes par axe client")).click();

        try {
            for (int i = 1; i < s.getRows(); i++) {
                // Read data from excel sheet
                // String s1 = s.getCell(0, i).getContents();
                // String s2 = s.getCell(1, i).getContents();
                // String s3 = s.getCell(2, i).getContents();
                String s4 = s.getCell(0, i).getContents();
                // String s5 = s.getCell(4, i).getContents();
                // String s6 = s.getCell(5, i).getContents();
                // String s7 = s.getCell(6, i).getContents();
                // driver.getTitle();
                // System.out.println(driver.getTitle());
                // driver.findElement(By.xpath(".//*[@id='uname']")).sendKeys(s1);
                // driver.findElement(By.xpath(".//*[@id='addr1']")).sendKeys(s2);
                // driver.findElement(By.xpath(".//*[@id='addr2']")).sendKeys(s3);
                new Select(driver.findElement(By.xpath("//*[@id='bodyFiltre']/fieldset/form/fieldset[1]/table/tbody/tr[3]/td[2]/select")))
                        .selectByValue(s4);
                // new Select(driver.findElement(By.id("state"))).selectByValue(s5);
                // new Select(driver.findElement(By.id("city"))).selectByValue(s6);
                // driver.findElement(By.xpath(".//*[@id='zip']")).sendKeys(s7);
                // driver.findElement(By.xpath(".//*[@id='save']")).click();

                // .//*[@id='bodyFiltre']/fieldset/form/fieldset[1]/table/tbody/tr[3]/td[2]/select

                // driver.getCurrentUrl();
                // System.out.println(driver.getCurrentUrl());
                // if (driver.getTitle().equals("Billing Details - Make Payment")) {
                // System.out.println("Home page Displayed successfully");

                // File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                // FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\src\\Screenshots\\" + "PaymentHome" + ".png"));

                // System.out.println("PaymentHome.png is generated go to directory to check");

                // }
                // else {
                // System.out.println("Do not Displayed");
                // }

                // driver.findElement(By.linkText("Logout"));
                // new Select(driver.findElement(By.id("country"))).selectByVisibleText("India");

                // driver.findElement(By.xpath("html/body/div[1]/div[3]/ul[2]/li[5]")).click();
                // driver.findElement(By.xpath(".//*[@id='imageform']/div/div/div[3]/a/img")).click();
            }
        } catch (Exception e) {
            System.out.println(e);

        }
    }
    /*
     * @AfterClass public void teardown(){ driver.quit(); }
     */

}
