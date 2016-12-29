/*
 * Creation : 27 sept. 2016
 */
package demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Filiale {

    public static void main(String[] args) throws InterruptedException, Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Animation Commerciale

        driver.findElement(By.linkText("Animation commerciale")).click();

        // Step 1 Click on the Suivi des ventes Filiale

        driver.findElement(By.id("2#1#0")).click();

        // Step 2 Select Ventes par axe client

        driver.findElement(By.linkText("Ventes par axe client")).click();

        // Step 3 Select Marque " CITROEN"

        try {
            WebElement pays = driver.findElement(By.name("cdMarque"));
            List<WebElement> paysList = pays.findElements(By.tagName("option"));
            Thread.sleep(5000);
            // for (int i = 0; i < paysList.size(); i++) {
            for (int i = 0; i < 1; i++) {
                WebElement pays1 = driver.findElement(By.name("cdMarque"));

                List<WebElement> paysList1 = pays1.findElements(By.tagName("option"));
                Thread.sleep(5000);
                String paysListValue = paysList1.get(i).getText();
                System.out.println(paysListValue);

                Select dropdown1 = new Select(pays1);
                Thread.sleep(5000);
                dropdown1.selectByIndex(i);
                // Step 4 Select Pays "Bélux " (there will be multiple selections availble for Pays)
                WebElement pays2 = driver.findElement(By.name("cdPays"));
                List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
                Thread.sleep(5000);
                // for (int j = 0; j < paysList2.size(); j++) {
                for (int j = 0; j < 1; j++) {
                    WebElement pays3 = driver.findElement(By.name("cdPays"));

                    List<WebElement> paysList3 = pays3.findElements(By.tagName("option"));
                    Thread.sleep(5000);
                    String paysListValue2 = paysList3.get(j).getText();
                    System.out.println(paysListValue2);

                    Select dropdown2 = new Select(pays3);
                    Thread.sleep(5000);
                    dropdown2.selectByIndex(j);

                    // Step 5 From "Période d'analyse" select P - Du & Au
                    driver.findElement(By.id("data1")).sendKeys("01/11/2016");
                    driver.findElement(By.id("data2")).sendKeys("31/12/2016");

                    // Step 6 select PVP

                    // Step 7 Click on Rechercher
                    driver.findElement(By.cssSelector("#trbody > td")).click();
                    driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
                    Thread.sleep(5000);

                    // Step 7 results

                    String resultsTotal = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();

                    String stringArr[] = resultsTotal.split(" ");

                    for (int i2 = 0; i2 < stringArr.length; i2++) {
                        System.out.println(stringArr[i2]);
                    }

                }
            }
        } catch (

        StaleElementReferenceException e) {
            e.printStackTrace();
            driver.close();
            ;
        }

    }

}
