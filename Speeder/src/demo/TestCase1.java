/*
 * Creation : 5 janv. 2017
 */
package demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestCase1 {
    public static void main(String[] args) throws Exception {
        // pull values to string array from appln .....
        // convert string array to long array
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");
        driver.findElement(By.cssSelector("form[name=\"RechercherMessageForm\"] > table > tbody > tr > td")).click();
        driver.findElement(By.linkText("Animation commerciale")).click();
        driver.findElement(By.linkText("Suivi des ventes Filiale")).click();
        driver.findElement(By.linkText("Ventes par axe client")).click();
        driver.findElement(By.name("cdMarque")).click();
        driver.findElement(By.cssSelector("option[value=\"C\"]")).click();
        driver.findElement(By.name("cdPays")).click();
        new Select(driver.findElement(By.name("cdPays"))).selectByVisibleText("Bélux");
        // Step 5 From "Période d'analyse" select P - Du & Au
        driver.findElement(By.id("data1")).clear();
        driver.findElement(By.id("data1")).sendKeys("01/11/2016");
        driver.findElement(By.id("data2")).clear();
        driver.findElement(By.id("data2")).sendKeys("31/12/2016");
        // Step 7 Click on Rechercher
        driver.findElement(By.cssSelector("#trbody > td")).click();
        driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
        System.out.println("Result fetching");
        String resultsOne = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
        resultsOne = resultsOne.substring(4, resultsOne.length());
        String[] items = resultsOne.replace(",", "").split(" ");
        String[] results = new String[items.length];
        for (int i = 1; i < items.length; i++) {
            System.out.println(items[i]);
            results[i] = items[i];
        }
        // double[] numbers = new double[results.length];
        for (int i = 1; i < results.length; i++) {
            // numbers[i] = Double.parseDouble(results[i]);
            long l = Double.valueOf(results[i]).longValue();
            System.out.println(l);
        }
        Thread.sleep(3000);
        driver.quit();
    }
}
