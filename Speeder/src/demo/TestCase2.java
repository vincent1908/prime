/*
 * Creation : 6 janv. 2017
 */
package demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestCase2 {

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
        long l;

        // Step 3 Select Marque " CITROEN"
        // try {
        try {
            WebElement pays = driver.findElement(By.name("cdMarque"));
            List<WebElement> paysList = pays.findElements(By.tagName("option"));
            Thread.sleep(2000);
            int column, row;
            long temp1[] = new long[6];
            long temp2[] = new long[6];
            long result[] = new long[6];

            // for (int i = 0; i < paysList.size(); i++) {
            for (int i = 0; i < 1; i++) {
                WebElement pays1 = driver.findElement(By.name("cdMarque"));

                List<WebElement> paysList1 = pays1.findElements(By.tagName("option"));
                Thread.sleep(2000);
                String paysListValue = paysList1.get(i).getText();
                System.out.println(paysListValue);

                Select dropdown1 = new Select(pays1);
                Thread.sleep(2000);
                dropdown1.selectByIndex(i);
                // Step 4 Select Pays "Bélux " (there will be multiple selections availble for Pays)
                WebElement pays2 = driver.findElement(By.name("cdPays"));
                List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
                Thread.sleep(2000);
                // for (int j = 0; j < paysList2.size(); j++) {
                for (int j = 0; j < 2; j++) {
                    // for (int j = 0; j < 1; j++) {
                    WebElement pays3 = driver.findElement(By.name("cdPays"));

                    List<WebElement> paysList3 = pays3.findElements(By.tagName("option"));
                    Thread.sleep(2000);
                    String paysListValue2 = paysList3.get(j).getText();
                    System.out.println(paysListValue2);

                    Select dropdown2 = new Select(pays3);
                    Thread.sleep(2000);
                    dropdown2.selectByIndex(j);

                    // Step 5 From "Période d'analyse" select P - Du & Au
                    driver.findElement(By.id("data1")).clear();
                    driver.findElement(By.id("data1")).sendKeys("01/11/2016");
                    driver.findElement(By.id("data2")).clear();
                    driver.findElement(By.id("data2")).sendKeys("31/12/2016");

                    // Step 6 select PVP

                    // Step 7 Click on Rechercher
                    driver.findElement(By.cssSelector("#trbody > td")).click();
                    driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
                    System.out.println("Result fetching");
                    String resultsOne = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
                    resultsOne = resultsOne.substring(4, resultsOne.length());
                    String[] items = resultsOne.replace(",", "").split(" ");
                    String[] results = new String[items.length];
                    column = results.length;
                    row = 2; // List<WebElement> paysList3 = pays3.findElements(By.tagName("option"));
                    System.out.println(row);
                    System.out.println("Column count is :" + column);
                    for (int i3 = 1; i3 < items.length; i3++) {

                        results[i3] = items[i3];
                    }

                    System.out.println("Arary declared");
                    if (j == 0) {
                        for (int i4 = 1; i4 < results.length; i4++) {
                            System.out.println("int value temp1:" + i4);
                            l = Double.valueOf(results[i4]).longValue();

                            temp1[i4] = l;
                            System.out.println(l);
                            System.out.println("Values of temp1 :" + i4 + "is :" + temp1[i4]);

                        }

                    }

                    if (j == 1) {
                        for (int i4 = 1; i4 < results.length; i4++) {
                            System.out.println("int value temp2:" + i4);
                            l = Double.valueOf(results[i4]).longValue();

                            temp2[i4] = l;
                            System.out.println(l);
                            System.out.println("Values of temp1 :" + i4 + "is :" + temp2[i4]);

                        }

                    }

                }

            }

            System.out.println("----------- Temp1 RESULT----");
            for (int res = 1; res < 6; res++) {
                System.out.println(temp1[res]);

            }
            System.out.println("----------- Temp2 RESULT----");
            for (int res = 1; res < 6; res++) {
                System.out.println(temp2[res]);

            }
            System.out.println("----------- RESULT----");
            for (int res = 1; res < 6; res++) {
                result[res] = temp1[res] + temp2[res];
                System.out.println(result[res]);
            }

        } catch (

        Exception e) {

            e.printStackTrace();
        }
    }

}
