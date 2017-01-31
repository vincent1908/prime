/*
 * Creation : 29 déc. 2016
 */
package demo;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class DoPr extends Filiale {
    WebDriver driver;

    @Test(priority = 1)
    public void Filiale() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        // driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");
        driver.get("http://optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(5000);

        // Animation Commerciale

        driver.findElement(By.linkText("Animation commerciale")).click();

        // Step 1 Click on the Suivi des ventes Filiale

        driver.findElement(By.id("2#1#0")).click();

        // Step 2 Select Ventes par axe client

        driver.findElement(By.linkText("Ventes par axe client")).click();

        // Step 3 Select Marque " CITROEN"

        try {
            // WebElement pays = driver.findElement(By.name("cdMarque"));
            // List<WebElement> paysList = pays.findElements(By.tagName("option"));
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
                // WebElement pays2 = driver.findElement(By.name("cdPays"));
                // List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
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

                    String resultsOne = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();

                    String stringOne[] = resultsOne.split(" ");

                    for (int i2 = 0; i2 < stringOne.length; i2++) {
                        System.out.println(stringOne[i2]);
                    }

                }
            }
        } catch (

        StaleElementReferenceException e) {
            e.printStackTrace();
            driver.close();
        }
    }

    @Test(priority = 2)
    // public static void main(String[] args) throws InterruptedException, Exception {
    public static void DoPr1() throws Exception {
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

        String resultsTwo = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();

        String stringTwo[] = resultsTwo.split(" ");

        for (int i2 = 0; i2 < stringTwo.length; i2++) {
            System.out.println(stringTwo[i2]);
        }

    }

    @Test(priority = 3)
    public void compResults() {
        System.out.println("-----------------------------------------------------------------");

        String resultsOne = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();

        String stringOne[] = resultsOne.split(" ");

        for (int i2 = 0; i2 < stringOne.length; i2++) {
            System.out.println(stringOne[i2]);
        }

        System.out.println("******************************************************************");

        String resultsTwo = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
        String stringTwo[] = resultsTwo.split(" ");

        for (int i2 = 0; i2 < stringTwo.length; i2++) {
            System.out.println(stringTwo[i2]);
        }

        int[] even = { 2, 4, 6 };
        int[] meEvenToo = { 2, 4, 6 };
        int[] odd = { 3, 5, 7 };
        boolean result = Arrays.equals(even, meEvenToo);
        System.out.printf("Comparing two int arrays %s and %s, are they Equal? %s %n ", Arrays.toString(even), Arrays.toString(meEvenToo), result);

        result = Arrays.equals(even, odd);
        System.out.printf("Comparing even and odd int arrays %s and %s, are they Equal? %s %n", Arrays.toString(even), Arrays.toString(odd), result);
    }
}
