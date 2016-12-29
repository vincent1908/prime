/*
 * Creation : 29 déc. 2016
 */
package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DoPr {
    public static void main(String[] args) throws InterruptedException, Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(5000);

        driver.findElement(By.linkText("Animation commerciale")).click();
        // Step 8 Go to Suivi des ventes DoPR
        driver.findElement(By.linkText("Suivi des ventes DoPR")).click();
        // Step 9 Select Ventes par axe client
        driver.findElement(By.xpath("(//a[contains(text(),'Ventes par axe client')])[2]")).click();
        driver.findElement(By.cssSelector("select[name=\"cdMarque\"]")).click();
        // Step 10 Select Marque " CITROEN"
        new Select(driver.findElement(By.cssSelector("select[name=\"cdMarque\"]"))).selectByVisibleText("CITROEN");
        driver.findElement(By.cssSelector("option[value=\"C\"]")).click();
        driver.findElement(By.cssSelector("select[name=\"cdPays\"]")).click();
        // Step 11 Select Pays "Bélux "
        new Select(driver.findElement(By.cssSelector("select[name=\"cdPays\"]"))).selectByVisibleText("Bélux");
        driver.findElement(By.cssSelector("option[value=\"BE\"]")).click();
        driver.findElement(By.cssSelector("select[name=\"cdDr\"]")).click();
        // Step 12 Select DR "AC BELUX "
        new Select(driver.findElement(By.cssSelector("select[name=\"cdDr\"]"))).selectByVisibleText("AC BELUX");
        driver.findElement(By.cssSelector("option[value=\"341\"]")).click();
        driver.findElement(By.cssSelector("select[name=\"cdSecteur\"]")).click();
        // Step 13 Select Secteur "DSP 03" (there will be multiple selections availble for sectur)
        new Select(driver.findElement(By.cssSelector("select[name=\"cdSecteur\"]"))).selectByVisibleText("DSP 03");
        driver.findElement(By.cssSelector("option[value=\"495\"]")).click();
        driver.findElement(By.id("cdClientSelect")).click();
        // Step 14 Select Code DoPR from the drop down (there will be multiple selections availble for DoPR)
        new Select(driver.findElement(By.id("cdClientSelect"))).selectByVisibleText("000058D");
        driver.findElement(By.cssSelector("option[value=\"000058D\"]")).click();
        // Step 15 Select Raison sociale du DoPR from drop down (there will be multiple selections availble for Raison social)
        driver.findElement(By.id("lbClientSelect")).click();
        driver.findElement(By.cssSelector("option[value=\"N.V.GASPAR MOTORS\"]")).click();
        // Step 16 From "Période d'analyse" select P - Du & Au
        driver.findElement(By.id("data1")).sendKeys("01/11/2016");
        driver.findElement(By.id("data2")).sendKeys("31/12/2016");

        // Step 17 select PVP

        // Step 18 Click on Rechercher
        driver.findElement(By.cssSelector("#trbody > td")).click();
        driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
        Thread.sleep(5000);

        // Step 18 results

        String resultsTotal = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();

        String stringArr[] = resultsTotal.split(" ");

        for (int i2 = 0; i2 < stringArr.length; i2++) {
            System.out.println(stringArr[i2]);
        }

    }

}
